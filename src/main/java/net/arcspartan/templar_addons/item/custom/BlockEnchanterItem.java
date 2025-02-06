package net.arcspartan.templar_addons.item.custom;

import net.arcspartan.templar_addons.block.ModBlocks;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.Map;

public class BlockEnchanterItem extends Item {
    private static final Map<Block, Block> ENCHANT_MAP =
            Map.of(
                    Blocks.GOLD_BLOCK, ModBlocks.ELECTRUM_BLOCK.get()
            );


    public BlockEnchanterItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        Block clickedBlock = level.getBlockState(pContext.getClickedPos()).getBlock();


        return super.useOn(pContext);
    }
}
