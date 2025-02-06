package net.arcspartan.templar_addons.item;

import net.arcspartan.templar_addons.TemplarAddonsMod;
import net.arcspartan.templar_addons.block.ModBlocks;
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
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.CRUDE_MANA_CRYSTAL.get()))
                    .title(Component.translatable("creativetab.templar_addons.templar_items"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.RAW_SILVER.get());
                        output.accept(ModItems.CRUSHED_CONDUCTIVE_MIX.get());
                        output.accept(ModItems.RAW_ELECTRUM.get());
                        output.accept(ModItems.SILVER_INGOT.get());
                        output.accept(ModItems.ELECTRUM_INGOT.get());
                        output.accept(ModItems.RAW_MANA_CRYSTAL.get());
                        output.accept(ModItems.CRUDE_MANA_CRYSTAL.get());
                        output.accept(ModItems.MANA_CRYSTAL.get());

                    }).build());


    public static final RegistryObject<CreativeModeTab> TEMPLAR_BLOCKS_TAB = CREATIVE_MODE_TABS.register("templar_blocks_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModBlocks.RAW_MANA_CRYSTAL_BLOCK.get()))
                    .title(Component.translatable("creativetab.templar_addons.templar_blocks"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.MANA_CRYSTAL_ORE_STONE.get());
                        output.accept(ModBlocks.MANA_CRYSTAL_ORE_DEEP.get());
                        output.accept(ModBlocks.SILVER_ORE_STONE.get());
                        output.accept(ModBlocks.SILVER_ORE_DEEP.get());
                        output.accept(ModBlocks.RAW_MANA_CRYSTAL_BLOCK.get());
                        output.accept(ModBlocks.ELECTRUM_BLOCK.get());
                        output.accept(ModBlocks.IMBUED_ELECTRUM_BLOCK.get());

                    }).build());

    public static final RegistryObject<CreativeModeTab> TEMPLAR_MAGIC_TAB = CREATIVE_MODE_TABS.register("templar_magic_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.INFUSION_ROD.get()))
                    .title(Component.translatable("creativetab.templar_addons.templar_magic"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModItems.INFUSION_ROD.get());

                    }).build());



    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
