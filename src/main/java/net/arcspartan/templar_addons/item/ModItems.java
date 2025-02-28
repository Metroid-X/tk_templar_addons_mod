package net.arcspartan.templar_addons.item;

import net.arcspartan.templar_addons.TemplarAddonsMod;
import net.arcspartan.templar_addons.entity.ModEntities;
import net.arcspartan.templar_addons.item.custom.EnchanterItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.commands.data.DataCommands;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

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

    public static final RegistryObject<Item> CRUSHED_CONDUCTIVE_MIX = ITEMS.register("crushed_conductive_mix",
            () -> new Item(new Item.Properties()
                    .setId(ITEMS.key("crushed_conductive_mix"))
                    .stacksTo(64)
            )
    );

    public static final RegistryObject<Item> RAW_ELECTRUM = ITEMS.register("raw_electrum",
            () -> new Item(new Item.Properties()
                    .setId(ITEMS.key("raw_electrum"))
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
            () -> new EnchanterItem(new Item.Properties()
                    .setId(ITEMS.key("infusion_rod"))
                    .durability(33)
            )
    );


    public static final RegistryObject<Item> INFUSED_SILVER_INGOT = ITEMS.register("infused_silver_ingot",
            () -> new Item(new Item.Properties()
                    .setId(ITEMS.key("infused_silver_ingot"))
                    .stacksTo(64)
            )
    );

    public static final RegistryObject<Item> INFUSED_ELECTRUM_INGOT = ITEMS.register("infused_electrum_ingot",
            () -> new Item(new Item.Properties()
                    .setId(ITEMS.key("infused_electrum_ingot"))
                    .stacksTo(64)
            )
    );

    public static final RegistryObject<Item> INFUSED_LAPIS = ITEMS.register("infused_lapis_lazuli",
            () -> new Item(new Item.Properties()
                    .setId(ITEMS.key("infused_lapis_lazuli"))
                    .stacksTo(64)
            )
    );







    public static final RegistryObject<Item> TIGER_KEIDRAN_SPAWN_EGG = ITEMS.register("tiger_keidran_spawn_egg",
            () -> new SpawnEggItem(ModEntities.KEIDRAN_TIGER.get(), new Item.Properties()
                    .setId(ITEMS.key("tiger_keidran_spawn_egg"))
                    .stacksTo(64)

            )
    );



    public static void register(IEventBus eventBus) { ITEMS.register(eventBus); }
}
