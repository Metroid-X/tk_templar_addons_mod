package net.arcspartan.templar_addons.item;

import net.arcspartan.templar_addons.TemplarAddonsMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TemplarAddonsMod.MOD_ID);

    public static final RegistryObject<Item> RAWMANACRYSTAL = ITEMS.register("raw_mana_crystal",
            () -> new Item(new Item.Properties()
                    .setId(ITEMS.key("raw_mana_crystal"))
                    .stacksTo(64)
            )
    );
    public static final RegistryObject<Item> MANACRYSTAL = ITEMS.register("mana_crystal",
            () -> new Item(new Item.Properties()
                    .setId(ITEMS.key("mana_crystal"))
                    .stacksTo(16)
            )
    );






    public static void register(IEventBus eventBus) { ITEMS.register(eventBus); }
}
