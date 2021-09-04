package zer0n1.multiversion.events.ingame;

import zer0n1.multiversion.events.init.KeyBindingListener;
import net.modificationstation.stationapi.api.client.event.option.KeyBindingRegister;
import net.modificationstation.stationapi.api.common.event.EventListener;

public class KeyPressedListener {

    @EventListener
    public void keyPressed(KeyBindingRegister event) {
        if (event.keyBindings.get(0).key == KeyBindingListener.keyBinding.key) {

            //((Minecraft) FabricLoader.getInstance().getGameInstance()).player.increaseStat(AchievementListener.achievement, 1);
        }
        if (event.keyBindings.get(0).key == KeyBindingListener.keyBinding2.key) {

            //((Minecraft)FabricLoader.getInstance().getGameInstance()).player.increaseStat(AchievementListener.achievement2, 1);
        }
        if (event.keyBindings.get(0).key == KeyBindingListener.keyBinding3.key) {

            //((Minecraft) FabricLoader.getInstance().getGameInstance()).player.damage(null, 1000000);
        }
    }
}
