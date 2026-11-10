package com.heavydiesel.blockentity;

import java.util.List;

import com.heavydiesel.ModTags;
import com.heavydiesel.block.DieselEngineBlock;
import com.simibubi.create.content.kinetics.base.GeneratingKineticBlockEntity;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandler.FluidAction;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;

/**
 * Burns fuel (fluids in the heavydiesel:engine_fuel tag, by default TFMG diesel) and
 * generates rotation for the Create kinetic network.
 */
public class DieselEngineBlockEntity extends GeneratingKineticBlockEntity {

    // ---- Tuning: change these numbers to rebalance the engine ----
    /** Speed the engine produces, in RPM. */
    public static final int GENERATED_RPM = 128;
    /** Stress capacity per 1 RPM (total = this x RPM = 32 768 su at 128 RPM). */
    public static final double STRESS_CAPACITY = 256.0;
    /** Tank size in millibuckets. */
    public static final int TANK_CAPACITY = 4000;
    /** Fuel burned every tick while running, in millibuckets (1 = 20 mB per second). */
    public static final int FUEL_PER_TICK = 1;

    private final FluidTank tank = new FluidTank(TANK_CAPACITY, fluid -> fluid.is(ModTags.ENGINE_FUEL)) {
        @Override
        protected void onContentsChanged() {
            setChanged();
        }
    };
    private final IFluidHandler inputOnly = new FuelInputHandler(tank);
    private boolean running;

    public DieselEngineBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public IFluidHandler getFluidHandler() {
        return inputOnly;
    }

    @Override
    public void initialize() {
        super.initialize();
        if (!hasSource() || getGeneratedSpeed() > getTheoreticalSpeed())
            updateGeneratedRotation();
    }

    @Override
    public void tick() {
        super.tick();
        if (level == null || level.isClientSide)
            return;

        boolean canRun = tank.getFluidAmount() >= FUEL_PER_TICK;
        if (canRun)
            tank.drain(FUEL_PER_TICK, FluidAction.EXECUTE);

        if (canRun != running) {
            running = canRun;
            updateGeneratedRotation();
            sendData();
        } else if (running && level.getGameTime() % 20 == 0) {
            sendData(); // keeps the goggles' fuel readout up to date
        }
    }

    @Override
    public float getGeneratedSpeed() {
        BlockState state = getBlockState();
        if (!(state.getBlock() instanceof DieselEngineBlock))
            return 0;
        if (!running)
            return 0;
        return convertToDirection(GENERATED_RPM, state.getValue(BlockStateProperties.HORIZONTAL_FACING));
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip, isPlayerSneaking);
        tooltip.add(Component.literal("    ").append(
            Component.translatable("heavydiesel.goggles.fuel", tank.getFluidAmount(), tank.getCapacity())
                .withStyle(ChatFormatting.GOLD)));
        return true;
    }

    @Override
    protected void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.write(compound, registries, clientPacket);
        compound.put("Fuel", tank.writeToNBT(registries, new CompoundTag()));
        compound.putBoolean("Running", running);
    }

    @Override
    protected void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        super.read(compound, registries, clientPacket);
        tank.readFromNBT(registries, compound.getCompound("Fuel"));
        running = compound.getBoolean("Running");
    }
}
