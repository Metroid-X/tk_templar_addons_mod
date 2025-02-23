package net.arcspartan.templar_addons.events;

import net.arcspartan.templar_addons.entity.npc.AbstractKeidranEntity;
import net.arcspartan.templar_addons.events.entity.player.TradeWithKeidranEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.fml.ModLoader;
import net.minecraftforge.fml.event.IModBusEvent;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public final class TemplarEventFactory {
    private static final ModLoader ML = ModLoader.get();

    private TemplarEventFactory() {}

    /**
     * Post an event to the {@link MinecraftForge#EVENT_BUS}
     * @return true if the event is {@link Cancelable} and has been canceled
     */
    private static boolean post(Event e) {
        return MinecraftForge.EVENT_BUS.post(e);
    }

    /**
     * Post an event to the {@link MinecraftForge#EVENT_BUS}, then return the event object
     * @return the event object passed in and possibly modified by listeners
     */
    private static <E extends Event> E fire(E e) {
        post(e);
        return e;
    }

    /**
     * Post an event to the {@link ModLoader#get()} event bus
     */
    private static <T extends Event & IModBusEvent> void postModBus(T e) {
        ML.postEvent(e);
    }


    public static void onPlayerTradeWithKeidran(Player player, MerchantOffer offer, AbstractKeidranEntity keidran) {
        post(new TradeWithKeidranEvent(player, offer, keidran));
    }
}
