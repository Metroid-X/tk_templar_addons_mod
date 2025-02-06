package net.arcspartan.templar_addons.block;

import net.arcspartan.templar_addons.TemplarAddonsMod;
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


    public static final RegistryObject<Block> MANA_CRYSTAL_ORE_STONE = registerBlock("mana_crystal_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .setId(BLOCKS.key("mana_crystal_ore"))
                    .requiresCorrectToolForDrops().strength(4.0f, 4.0f).sound(SoundType.STONE)
            )
    );

    public static final RegistryObject<Block> MANA_CRYSTAL_ORE_DEEP = registerBlock("deepslate_mana_crystal_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .setId(BLOCKS.key("deepslate_mana_crystal_ore"))
                    .requiresCorrectToolForDrops().strength(5.0f).sound(SoundType.DEEPSLATE)
            )
    );

    public static final RegistryObject<Block> SILVER_ORE_STONE = registerBlock("silver_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .setId(BLOCKS.key("silver_ore"))
                    .requiresCorrectToolForDrops().strength(4.0f, 4.0f).sound(SoundType.STONE)
            )
    );

    public static final RegistryObject<Block> SILVER_ORE_DEEP = registerBlock("deepslate_silver_ore",
            () -> new Block(BlockBehaviour.Properties.of()
                    .setId(BLOCKS.key("deepslate_silver_ore"))
                    .requiresCorrectToolForDrops().strength(5.0f).sound(SoundType.DEEPSLATE)
            )
    );

    public static final RegistryObject<Block> RAW_MANA_CRYSTAL_BLOCK = registerBlock("raw_mana_crystal_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .setId(BLOCKS.key("raw_mana_crystal_block"))
                    .requiresCorrectToolForDrops().strength(3.0f).sound(SoundType.DEEPSLATE_TILES)
            )
    );

    public static final RegistryObject<Block> RAW_SILVER_BLOCK = registerBlock("raw_silver_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .setId(BLOCKS.key("raw_silver_block"))
                    .requiresCorrectToolForDrops().strength(3.0f).sound(SoundType.STONE)
            )
    );

    public static final RegistryObject<Block> RAW_ELECTRUM_BLOCK = registerBlock("raw_electrum_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .setId(BLOCKS.key("raw_electrum_block"))
                    .requiresCorrectToolForDrops().strength(3.0f).sound(SoundType.STONE)
            )
    );

    public static final RegistryObject<Block> SILVER_BLOCK = registerBlock("silver_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .setId(BLOCKS.key("silver_block"))
                    .requiresCorrectToolForDrops().strength(4.0f).sound(SoundType.METAL)
            )
    );

    public static final RegistryObject<Block> ELECTRUM_BLOCK = registerBlock("electrum_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .setId(BLOCKS.key("electrum_block"))
                    .requiresCorrectToolForDrops().strength(4.0f).sound(SoundType.METAL)
            )
    );

    public static final RegistryObject<Block> IMBUED_ELECTRUM_BLOCK = registerBlock("imbued_electrum_block",
            () -> new Block(BlockBehaviour.Properties.of()
                    .setId(BLOCKS.key("imbued_electrum_block"))
                    .requiresCorrectToolForDrops().strength(4.0f).sound(SoundType.COPPER)
            )
    );



    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name , toReturn);
        return toReturn;
    }


    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()
                .setId(ITEMS.key(name))
                .stacksTo(64)
            )
        );
    }



    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
