package com.uroria.gameapi.spectate;

import com.uroria.gameapi.player.GamePlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public record EntityDamageBySpectatorListener(GamePlayerManager playerManager) implements Listener {

    @EventHandler
    public void onPlayerDamageBySpectator(final EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player damager)) return;

        final var gamePlayer = this.playerManager.gamePlayer(damager);
        if (gamePlayer.isEmpty()) return;
        if (!gamePlayer.orElseThrow().isSpectator()) return;

        event.setCancelled(true);
    }

}
