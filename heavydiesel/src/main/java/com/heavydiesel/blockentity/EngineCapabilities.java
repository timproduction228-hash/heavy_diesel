package com.heavydiesel.blockentity;

import com.heavydiesel.registry.ModBlocks;

import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;

public class EngineCapabilities {

    public static void register(RegisterCapabilitiesEvent event) {
        // Back half: its own tank.
        event.registerBlock(Capabilities.FluidHandler.BLOCK, (level, pos, state, be, side) -> {
            if (be instanceof DieselEngineBlockEntity engine)
                return engine.getFluidHandler();
            return null;
        }, ModBlocks.DIESEL_ENGINE.get());

        // Front half: forwards to the back half, so pipes can connect to either end.
        event.registerBlock(Capabilities.FluidHandler.BLOCK, (level, pos, state, be, side) -> {
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            if (level.getBlockEntity(pos.relative(facing.getOpposite())) instanceof DieselEngineBlockEntity engine)
                return engine.getFluidHandler();
            return null;
        }, ModBlocks.DIESEL_ENGINE_FRONT.get());
    }
}
