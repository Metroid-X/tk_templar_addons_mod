package net.arcspartan.templar_addons.item;

import net.arcspartan.templar_addons.TemplarAddonsMod;
import net.arcspartan.templar_addons.item.custom.EnchanterItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TemplarAddonsMod.MOD_ID);

    public static final RegistryObject<Item> RAW_MANA_CRYSTAL = ITEMS.register("raw_mana_crystal",
            () -> new Item(new Item.Properties()
                    .setId(ITEMS.key("raw_mana_crystal"))
                    .stacksTo(64)
            )
    );

    public static final RegistryObject<Item> RAW_SILVER = ITEMS.register("raw_silver",
            () -> new Item(new Item.Properties()
                    .setId(ITEMS.key("raw_silver"))
                    .stacksTo(64)
            )
    );

    public static final RegistryObject<Item> SILVER_INGOT = ITEMS.register("silver_ingot",
            () -> new Item(new Item.Properties()
                    .setId(ITEMS.key("silver_ingot"))
                    .stacksTo(64)
            )
    );

    public static final RegistryObject<Item> ELECTRUM_INGOT = ITEMS.register("electrum_ingot",
            () -> new Item(new Item.Properties()
                    .setId(ITEMS.key("electrum_ingot"))
                    .stacksTo(64)
            )
    );

    public static final RegistryObject<Item> CRUDE_MANA_CRYSTAL = ITEMS.register("crude_mana_crystal",
            () -> new Item(new Item.Properties()
                    .setId(ITEMS.key("crude_mana_crystal"))
                    .stacksTo(32)
            )
    );

    public static final RegistryObject<Item> MANA_CRYSTAL = ITEMS.register("mana_crystal",
            () -> new Item(new Item.Properties()
                    .setId(ITEMS.key("mana_crystal"))
                    .stacksTo(16)
            )
    );


    public static final RegistryObject<Item> INFUSION_ROD = ITEMS.register("infusion_rod",
            () -> new EnchanterItem(new Item.Properties().setId(ITEMS.key("infusion_rod"))
                    .durability(32))
            );



    public static void register(IEventBus eventBus) { ITEMS.register(eventBus); }
}
