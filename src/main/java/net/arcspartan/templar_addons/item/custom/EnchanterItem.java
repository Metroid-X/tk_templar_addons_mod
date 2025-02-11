package net.arcspartan.templar_addons.item.custom;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import net.arcspartan.templar_addons.block.ModBlocks;
import net.minecraft.client.multiplayer.chat.ChatLog;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.SignableCommand;
import net.minecraft.server.commands.SayCommand;
import net.minecraft.server.commands.TellRawCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.w3c.dom.events.UIEvent;

import java.util.Map;



public class EnchanterItem extends Item {
    private static final Map<Block, Block> ENCHANT_MAP =
            Map.of(
                    ModBlocks.ELECTRUM_BLOCK.get(), ModBlocks.INFUSED_ELECTRUM_BLOCK.get()
            );


    public EnchanterItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();
        Block clickedBlock = level.getBlockState(pContext.getClickedPos()).getBlock();
        Player player = pContext.getPlayer();

        if(ENCHANT_MAP.containsKey(clickedBlock) && !pContext.getItemInHand().nextDamageWillBreak()) {



            if(!level.isClientSide()) {
                level.setBlockAndUpdate(pContext.getClickedPos(), ENCHANT_MAP.get(clickedBlock).defaultBlockState());

                pContext.getItemInHand().hurtAndBreak(16, ((ServerLevel) level), ((ServerPlayer) pContext.getPlayer()),
                        item -> pContext.getPlayer().onEquippedItemBroken(item, EquipmentSlot.MAINHAND)
                );

            }   level.playSound(null, pContext.getClickedPos(), SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS);
        } else {
            player.displayClientMessage(Component.nullToEmpty("It's out of charge..."),true);
            level.playSound(null, pContext.getClickedPos(), SoundEvents.UI_TOAST_IN, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        return InteractionResult.SUCCESS;
    }
}
