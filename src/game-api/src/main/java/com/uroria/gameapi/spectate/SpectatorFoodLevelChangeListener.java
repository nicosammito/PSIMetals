package com.uroria.gameapi.spectate;

import com.uroria.gameapi.player.GamePlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public record SpectatorFoodLevelChangeListener(GamePlayerManager playerManager) implements Listener {

    @EventHandler
    public void onSpectatorFoodLevelChange(FoodLevelChangeEvent event) {

        if (!(event.getEntity() instanceof Player player)) return;
        final var gamePlayer = this.playerManager.gamePlayer(player);
        if (gamePlayer.isEmpty()) return;
        if (!gamePlayer.orElseThrow().isSpectator()) return;

        event.setFoodLevel(20);
        event.setCancelled(true);

    }

}
