package com.heavydiesel.client;

import com.heavydiesel.blockentity.DieselEngineBlockEntity;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.KineticBlockEntityRenderer;

import net.createmod.catnip.render.CachedBuffers;
import net.createmod.catnip.render.SuperByteBuffer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

/** Draws Create's half-shaft sticking out of the BACK of the engine, spinning with the network. */
public class DieselEngineRenderer extends KineticBlockEntityRenderer<DieselEngineBlockEntity> {

    public DieselEngineRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    protected SuperByteBuffer getRotatedModel(DieselEngineBlockEntity be, BlockState state) {
        return CachedBuffers.partialFacing(AllPartialModels.SHAFT_HALF, state,
            state.getValue(BlockStateProperties.HORIZONTAL_FACING).getOpposite());
    }
}
