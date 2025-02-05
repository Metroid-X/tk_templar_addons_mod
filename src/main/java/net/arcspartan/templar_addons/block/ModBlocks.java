package net.arcspartan.templar_addons.block;

import net.arcspartan.templar_addons.TemplarAddonsMod;
import net.arcspartan.templar_addons.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

import static net.arcspartan.templar_addons.item.ModItems.ITEMS;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, TemplarAddonsMod.MOD_ID);


    public static final RegistryObject<Block> MANA_CRYSTAL_ORE_STONE = registerBlock("mana_crystal_vein",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.STONE)
            )
    );

    public static final RegistryObject<Block> MANA_CRYSTAL_ORE_DEEP = registerBlock("deepslate_mana_crystal_vein",
            () -> new Block(BlockBehaviour.Properties.of()
                    .strength(4f).requiresCorrectToolForDrops().sound(SoundType.STONE)
            )
    );



    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name , toReturn);
        return toReturn;
    }


    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()

            )
        );
    }



    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
