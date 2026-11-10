package com.heavydiesel;

import com.heavydiesel.blockentity.EngineCapabilities;
import com.heavydiesel.registry.ModBlockEntities;
import com.heavydiesel.registry.ModBlocks;
import com.simibubi.create.foundation.data.CreateRegistrate;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@Mod(HeavyDiesel.MOD_ID)
public class HeavyDiesel {
    public static final String MOD_ID = "heavydiesel";
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(MOD_ID);

    public HeavyDiesel(IEventBus modEventBus) {
        REGISTRATE.registerEventListeners(modEventBus);
        ModBlocks.register();
        ModBlockEntities.register();
        modEventBus.addListener(this::registerCapabilities);
        modEventBus.addListener(this::addToCreativeTab);
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        EngineCapabilities.register(event);
    }

    private void addToCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(ModBlocks.DIESEL_ENGINE.get());
        }
    }
}
