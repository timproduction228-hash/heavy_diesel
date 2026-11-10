package com.heavydiesel.registry;

import com.heavydiesel.HeavyDiesel;
import com.heavydiesel.blockentity.DieselEngineBlockEntity;
import com.heavydiesel.client.DieselEngineRenderer;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.content.kinetics.base.OrientedRotatingVisual;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

public class ModBlockEntities {

    public static final BlockEntityEntry<DieselEngineBlockEntity> DIESEL_ENGINE = HeavyDiesel.REGISTRATE
        .blockEntity("diesel_engine", DieselEngineBlockEntity::new)
        .visual(() -> OrientedRotatingVisual.backHorizontal(AllPartialModels.SHAFT_HALF), false)
        .validBlocks(ModBlocks.DIESEL_ENGINE)
        .renderer(() -> DieselEngineRenderer::new)
        .register();

    public static void register() {
    }
}
