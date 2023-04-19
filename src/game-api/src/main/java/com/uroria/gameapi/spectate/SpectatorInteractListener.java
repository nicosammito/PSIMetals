package com.uroria.gameapi.spectate;

import com.uroria.gameapi.player.GamePlayerFlags;
import com.uroria.gameapi.player.GamePlayerManager;
import com.uroria.gameapi.util.ItemBuilder;
import com.uroria.gameapi.util.inventory.InventoryBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.atomic.AtomicInteger;

public record SpectatorInteractListener(Plugin plugin, GamePlayerManager playerManager) implements Listener {

    @EventHandler
    public void onItemsInteract(final PlayerInteractEvent event) {

        final var gamePlayer = this.playerManager.gamePlayer(event.getPlayer());
        if (gamePlayer.isEmpty()) return;
        if (!gamePlayer.orElseThrow().isSpectator()) return;
        if (event.getItem() == null) return;

        event.setCancelled(true);

        if (event.getAction().isLeftClick()) return;

        //create invetory and get held item slot
        final var inventory = this.createInvetory();
        final var heldItemSlot = event.getPlayer().getInventory().getHeldItemSlot();

        if (heldItemSlot == 3) {
            event.getPlayer().openInventory(inventory.build());
        } else if (heldItemSlot == 5) {
            event.getPlayer().kick();
            //TODO: more robust logic to kick player. Maybe over UroriaAPI
        }

    }

    private InventoryBuilder createInvetory() {

        final var inventory = InventoryBuilder.create(plugin).withSize(9 * 6).withTitle("");
        final var players = this.playerManager.bukkitPlayersWithoutFlag(GamePlayerFlags.SPECTATOR);
        final var counter = new AtomicInteger(0);

        players.forEach((player) -> {
            final var item = new ItemBuilder(Material.PLAYER_HEAD).setOwner(player).build();
            inventory.withItem(item, counter.getAndIncrement(), event -> this.onInventoryClick(event, player));
        });

        return inventory;
    }

    private void onInventoryClick(final InventoryClickEvent event, final Entity targetPlayer) {
        if (!(event.getWhoClicked() instanceof Player player)) return;
        player.teleport(targetPlayer.getLocation());
        event.setCancelled(true);
        event.getInventory().close();
    }

}
