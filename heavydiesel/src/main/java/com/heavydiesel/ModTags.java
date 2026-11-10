package com.heavydiesel;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

public class ModTags {
    /** Fluids the engine accepts as fuel. Filled in data/heavydiesel/tags/fluid/engine_fuel.json (TFMG diesel by default). */
    public static final TagKey<Fluid> ENGINE_FUEL =
        TagKey.create(Registries.FLUID, ResourceLocation.fromNamespaceAndPath(HeavyDiesel.MOD_ID, "engine_fuel"));
}
