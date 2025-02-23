package net.arcspartan.templar_addons.util;

import net.arcspartan.templar_addons.TemplarAddonsMod;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import java.util.Map;


public class ModTags {

    public static final Map<TagKey<Item>,Integer> MANA_COST =
            Map.of(
                    Items.MANA_1, 1,
                    Items.MANA_2, 2,
                    Items.MANA_4, 4,
                    Items.MANA_8, 8,
                    Items.MANA_16, 16,
                    Items.MANA_32, 32,
                    Items.MANA_64, 64
            );

    public static class Blocks {

        public static final TagKey<Block> INFUSION_BLOCKS = createTag("infusion_blocks");



        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(TemplarAddonsMod.MOD_ID, name));
        }
    }

    public static class Items {
        public static final TagKey<Item> MANA_TYPES = createTag("mana_types");
        public static final TagKey<Item> INFUSION_ITEMS = createTag("infusion_items");
        public static final TagKey<Item> INFUSED_ITEMS = createTag("infused_items");
        public static final TagKey<Item> MANA_1 = createTag("mana_1");
        public static final TagKey<Item> MANA_2 = createTag("mana_2");
        public static final TagKey<Item> MANA_4 = createTag("mana_4");
        public static final TagKey<Item> MANA_8 = createTag("mana_8");
        public static final TagKey<Item> MANA_16 = createTag("mana_16");
        public static final TagKey<Item> MANA_32 = createTag("mana_32");
        public static final TagKey<Item> MANA_64 = createTag("mana_64");
        public static final TagKey<Item> INGOTS_SILVER = createForgeTag("ingots/silver");
        public static final TagKey<Item> INGOTS_ELECTRUM = createForgeTag("ingots/electrum");
        public static final TagKey<Item> MANA_GEM_CENTER = createTag("mana_gem_center");
        public static final TagKey<Item> KEIDRAN_PICKS_UP = createTag("keidran_picks_up");
        public static final TagKey<Item> KEIDRAN_CAN_EAT = createTag("keidran_can_eat");



        private static  TagKey<Item> createForgeTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("forge", name));
        }

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath("templar_addons", name));
        }
    }


}
