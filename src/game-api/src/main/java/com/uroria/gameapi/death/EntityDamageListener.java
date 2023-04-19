package com.uroria.gameapi.death;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Arrays;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageEvent event) {

        if (!(event.getEntity() instanceof Player hittedPlayer)) return;
        if ((hittedPlayer.getHealth() - event.getFinalDamage()) <= 0) {

            //exp calculated same as in EntityHuman class by NMS
            final var playerDeathEvent = new PlayerDeathEvent(
                    hittedPlayer,
                    Arrays.asList(hittedPlayer.getInventory().getContents()),
                    (int) Math.min(hittedPlayer.getExp() * 7, 100),
                    Component.text(hittedPlayer.displayName() + " was killed from " + event.getCause().name())
            );

            Bukkit.getServer().getPluginManager().callEvent(playerDeathEvent);
            event.setCancelled(playerDeathEvent.isCancelled());
        }
    }


}
