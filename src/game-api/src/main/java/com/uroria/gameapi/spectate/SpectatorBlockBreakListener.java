package com.uroria.gameapi.spectate;

import com.uroria.gameapi.player.GamePlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public record SpectatorBlockBreakListener(GamePlayerManager playerManager) implements Listener {

    @EventHandler
    public void onSpectatorBlockBreak(BlockBreakEvent event) {

        final var gamePlayer = this.playerManager.gamePlayer(event.getPlayer());
        if (gamePlayer.isEmpty()) return;
        if (!gamePlayer.orElseThrow().isSpectator()) return;
        event.setCancelled(true);


    }

}
