package com.uroria.gameapi.spectate;

import com.uroria.gameapi.player.GamePlayerManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;

public record SpectatorInventoryListener(GamePlayerManager playerManager) implements Listener {

    @EventHandler
    public void onSpectatorInventoryMoveItem(InventoryMoveItemEvent event) {

        if (!(event.getSource().getHolder() instanceof Player player)) return;
        final var gamePlayer = this.playerManager.gamePlayer(player);
        if (gamePlayer.isEmpty()) return;
        if (!gamePlayer.orElseThrow().isSpectator()) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onSpectatorInventoryInteract(InventoryInteractEvent event) {

        if (!(event.getWhoClicked() instanceof Player player)) return;
        final var gamePlayer = this.playerManager.gamePlayer(player);
        if (gamePlayer.isEmpty()) return;
        if (!gamePlayer.orElseThrow().isSpectator()) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onSpectatorInventoryDrag(InventoryDragEvent event) {

        if (!(event.getWhoClicked() instanceof Player player)) return;
        final var gamePlayer = this.playerManager.gamePlayer(player);
        if (gamePlayer.isEmpty()) return;
        if (!gamePlayer.orElseThrow().isSpectator()) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onSpectatorInventoryPickupItem(InventoryPickupItemEvent event) {

        if (!(event.getInventory().getHolder() instanceof Player player)) return;
        final var gamePlayer = this.playerManager.gamePlayer(player);
        if (gamePlayer.isEmpty()) return;
        if (!gamePlayer.orElseThrow().isSpectator()) return;

        event.setCancelled(true);

    }

    @EventHandler
    public void onSpectatorInventoryClick(InventoryClickEvent event) {

        if (!(event.getWhoClicked() instanceof Player player)) return;
        final var gamePlayer = this.playerManager.gamePlayer(player);
        if (gamePlayer.isEmpty()) return;
        if (!gamePlayer.orElseThrow().isSpectator()) return;

        event.setCancelled(true);

    }

}
