package zer0n1.multiversion.events.init;

import zer0n1.multiversion.events.ingame.ExamplePlayerHandler;
import net.modificationstation.stationapi.api.common.event.EventListener;
import net.modificationstation.stationapi.api.common.event.entity.player.PlayerHandlerRegister;

public class PlayerHandlerListener {

    @EventListener
    public void registerPlayerHandlers(PlayerHandlerRegister event) {
        event.playerHandlers.add(new ExamplePlayerHandler());
    }
}
