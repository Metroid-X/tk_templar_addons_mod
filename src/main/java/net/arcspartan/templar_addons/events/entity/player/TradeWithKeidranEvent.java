package net.arcspartan.templar_addons.events.entity.player;

import net.arcspartan.templar_addons.entity.npc.AbstractKeidranEntity;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.Cancelable;
import org.jetbrains.annotations.ApiStatus;

/**
 * Fired when a player trades with an {@link AbstractKeidranEntity}.
 *
 * <p>This event is not {@linkplain Cancelable cancellable}, and does not {@linkplain Event.HasResult have a result}.</p>
 *
 * <p>This event is fired on the {@linkplain MinecraftForge#EVENT_BUS main Forge event bus},
 * only on the {@linkplain LogicalSide#SERVER logical server}.</p>
 */
public class TradeWithKeidranEvent extends PlayerEvent {
    private final MerchantOffer offer;
    private final AbstractKeidranEntity abstractKeidran;

    @ApiStatus.Internal
    public TradeWithKeidranEvent(Player player, MerchantOffer offer, AbstractKeidranEntity abstractKeidran)
    {
        super(player);
        this.offer = offer;
        this.abstractKeidran = abstractKeidran;
    }

    /**
     * {@return the {@link MerchantOffer} selected by the player to trade with}
     */
    public MerchantOffer getMerchantOffer()
    {
        return offer;
    }

    /**
     * {@return the keidran the player traded with}
     */
    public AbstractKeidranEntity getAbstractKeidranEntity()
    {
        return abstractKeidran;
    }
}
