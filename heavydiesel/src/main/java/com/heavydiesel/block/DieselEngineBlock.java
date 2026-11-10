package com.heavydiesel.block;

import com.heavydiesel.blockentity.DieselEngineBlockEntity;
import com.heavydiesel.registry.ModBlockEntities;
import com.heavydiesel.registry.ModBlocks;
import com.simibubi.create.content.kinetics.base.HorizontalKineticBlock;
import com.simibubi.create.foundation.block.IBE;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.fluids.FluidUtil;

/**
 * The BACK half of the engine (the engine is 2 blocks long). It owns the block entity.
 * "facing" = the direction the engine extends in, i.e. where the FRONT half is placed.
 * The output shaft is on the back face (opposite of "facing").
 */
public class DieselEngineBlock extends HorizontalKineticBlock implements IBE<DieselEngineBlockEntity> {

    public DieselEngineBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Direction facing = context.getHorizontalDirection();
        BlockState frontState = context.getLevel().getBlockState(context.getClickedPos().relative(facing));
        if (!frontState.canBeReplaced())
            return null; // no room for the second half
        return defaultBlockState().setValue(BlockStateProperties.HORIZONTAL_FACING, facing);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        super.onPlace(state, level, pos, oldState, isMoving);
        if (level.isClientSide || oldState.is(this))
            return;
        Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
        BlockPos frontPos = pos.relative(facing);
        BlockState existing = level.getBlockState(frontPos);
        if (existing.is(ModBlocks.DIESEL_ENGINE_FRONT.get()) || !existing.canBeReplaced())
            return;
        level.setBlock(frontPos, ModBlocks.DIESEL_ENGINE_FRONT.get().defaultBlockState()
            .setValue(BlockStateProperties.HORIZONTAL_FACING, facing), Block.UPDATE_ALL);
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            BlockPos frontPos = pos.relative(facing);
            BlockState front = level.getBlockState(frontPos);
            if (front.is(ModBlocks.DIESEL_ENGINE_FRONT.get())
                && front.getValue(BlockStateProperties.HORIZONTAL_FACING) == facing)
                level.setBlock(frontPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_ALL | Block.UPDATE_SUPPRESS_DROPS);
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                           Player player, InteractionHand hand, BlockHitResult hit) {
        if (FluidUtil.interactWithFluidHandler(player, hand, level, pos, hit.getDirection()))
            return ItemInteractionResult.sidedSuccess(level.isClientSide);
        return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return EngineShapes.BACK.get(state.getValue(BlockStateProperties.HORIZONTAL_FACING));
    }

    // ---- Create kinetics ----

    @Override
    public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
        return face == state.getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite();
    }

    @Override
    public Axis getRotationAxis(BlockState state) {
        return state.getValue(BlockStateProperties.HORIZONTAL_FACING).getAxis();
    }

    @Override
    public boolean hideStressImpact() {
        return true;
    }

    @Override
    protected boolean isPathfindable(BlockState state, PathComputationType type) {
        return false;
    }

    @Override
    public Class<DieselEngineBlockEntity> getBlockEntityClass() {
        return DieselEngineBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends DieselEngineBlockEntity> getBlockEntityType() {
        return ModBlockEntities.DIESEL_ENGINE.get();
    }
}
