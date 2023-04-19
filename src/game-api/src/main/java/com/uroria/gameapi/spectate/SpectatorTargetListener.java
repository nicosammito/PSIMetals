package com.uroria.gameapi.spectate;

import com.uroria.gameapi.player.GamePlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetEvent;

public record SpectatorTargetListener(GamePlayerManager playerManager) implements Listener {

    @EventHandler
    public void onSpectatorTarget(EntityTargetEvent event) {

        if (event.getTarget() == null) return;
        if (!(event.getTarget() instanceof Player player)) return;

        final var gamePlayer = this.playerManager.gamePlayer(player);
        if (gamePlayer.isEmpty()) return;
        if (!gamePlayer.orElseThrow().isSpectator()) return;

        event.setCancelled(true);

    }

}
