package net.arcspartan.templar_addons.item;

import net.arcspartan.templar_addons.TemplarAddonsMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TemplarAddonsMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> TEMPLAR_ITEMS_TAB = CREATIVE_MODE_TABS.register("templar_items_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MANACRYSTAL.get()))
                    .title(Component.translatable("creativetab.templar_addons.templar_items"))
                    .build());



    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
