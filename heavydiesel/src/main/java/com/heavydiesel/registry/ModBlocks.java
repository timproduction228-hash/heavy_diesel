package com.heavydiesel.registry;

import com.heavydiesel.HeavyDiesel;
import com.heavydiesel.block.DieselEngineBlock;
import com.heavydiesel.block.DieselEngineFrontBlock;
import com.heavydiesel.blockentity.DieselEngineBlockEntity;
import com.simibubi.create.api.stress.BlockStressValues;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.material.MapColor;

public class ModBlocks {

    /** Back half of the engine: holds the block entity, the fuel tank and the shaft. */
    public static final BlockEntry<DieselEngineBlock> DIESEL_ENGINE = HeavyDiesel.REGISTRATE
        .block("diesel_engine", DieselEngineBlock::new)
        .initialProperties(SharedProperties::softMetal)
        .properties(p -> p.mapColor(MapColor.METAL).noOcclusion())
        .tag(BlockTags.MINEABLE_WITH_PICKAXE)
        .onRegister(BlockStressValues.setGeneratorSpeed(DieselEngineBlockEntity.GENERATED_RPM))
        .onRegister(block -> BlockStressValues.CAPACITIES.register(block, () -> DieselEngineBlockEntity.STRESS_CAPACITY))
        .item()
        .build()
        .register();

    /** Front half of the engine: placed automatically, no item and no drops. */
    public static final BlockEntry<DieselEngineFrontBlock> DIESEL_ENGINE_FRONT = HeavyDiesel.REGISTRATE
        .block("diesel_engine_front", DieselEngineFrontBlock::new)
        .initialProperties(SharedProperties::softMetal)
        .properties(p -> p.mapColor(MapColor.METAL).noOcclusion().noLootTable())
        .tag(BlockTags.MINEABLE_WITH_PICKAXE)
        .register();

    public static void register() {
    }
}
