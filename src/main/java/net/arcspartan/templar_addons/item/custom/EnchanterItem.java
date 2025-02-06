package net.arcspartan.templar_addons.item.custom;

import net.arcspartan.templar_addons.block.ModBlocks;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.Map;



public class EnchanterItem extends Item {
    private static final Map<Block, Block> ENCHANT_MAP =
            Map.of(
                    ModBlocks.ELECTRUM_BLOCK.get(), ModBlocks.IMBUED_ELECTRUM_BLOCK.get()
            );


    public EnchanterItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        Block clickedBlock = level.getBlockState(pContext.getClickedPos()).getBlock();

        if(ENCHANT_MAP.containsKey(clickedBlock)) {



            if(!level.isClientSide()) {
                level.setBlockAndUpdate(pContext.getClickedPos(), ENCHANT_MAP.get(clickedBlock).defaultBlockState());

                pContext.getItemInHand().hurtAndBreak(1, ((ServerLevel) level), ((ServerPlayer) pContext.getPlayer()),
                        item -> pContext.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND)
                        );

            }   level.playSound(null, pContext.getClickedPos(), SoundEvents.ALLAY_AMBIENT_WITH_ITEM, SoundSource.BLOCKS);
        }

        return InteractionResult.SUCCESS;
    }
}
