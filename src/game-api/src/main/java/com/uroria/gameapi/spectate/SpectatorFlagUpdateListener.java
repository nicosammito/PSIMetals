package com.uroria.gameapi.spectate;

import com.uroria.gameapi.player.GamePlayer;
import com.uroria.gameapi.player.GamePlayerFlagUpdateEvent;
import com.uroria.gameapi.player.GamePlayerFlags;
import com.uroria.gameapi.util.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public record SpectatorFlagUpdateListener(JavaPlugin javaPlugin) implements Listener {

    static final ItemStack PLAYERS_ITEM = new ItemBuilder(Material.ENDER_EYE).setDisplayName("Ach gönn dir").build();
    static final ItemStack LEAVE_ITEM = new ItemBuilder(Material.OAK_DOOR).setDisplayName("Hauste rein").build();

    @EventHandler
    public void onSpectatorFlagUpdate(GamePlayerFlagUpdateEvent event) {

        if (event.getPreviousFlags().contains(GamePlayerFlags.SPECTATOR) && !event.getNewFlags().contains(GamePlayerFlags.SPECTATOR)) {
            this.removeSpectator(event.getPlayer());
        }

        if (!event.getPreviousFlags().contains(GamePlayerFlags.SPECTATOR) && event.getNewFlags().contains(GamePlayerFlags.SPECTATOR)) {
            this.addSpectator(event.getPlayer());
        }

    }

    private void addSpectator(final GamePlayer player) {

        final var bukkitPlayer = player.getBukkitPlayer();
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.hidePlayer(this.javaPlugin, bukkitPlayer));

        bukkitPlayer.setGameMode(GameMode.ADVENTURE);
        bukkitPlayer.setAllowFlight(true);
        bukkitPlayer.setCollidable(false);
        bukkitPlayer.setCanPickupItems(false);
        bukkitPlayer.setFlying(true);
        bukkitPlayer.setHealth(20);
        bukkitPlayer.setFoodLevel(20);
        bukkitPlayer.setLevel(0);
        bukkitPlayer.setExp(0);

        if (player.getEnterSpectateLocation() != null && player.getEnterSpectateLocation().getWorld() != null) {
            bukkitPlayer.teleport(player.getEnterSpectateLocation());
        }

        bukkitPlayer.getInventory().clear();
        bukkitPlayer.getInventory().setItem(3, PLAYERS_ITEM);
        bukkitPlayer.getInventory().setItem(5, LEAVE_ITEM);
    }

    private void removeSpectator(final GamePlayer player) {

        final var bukkitPlayer = player.getBukkitPlayer();
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.showPlayer(this.javaPlugin, bukkitPlayer));

        bukkitPlayer.setGameMode(GameMode.ADVENTURE);
        bukkitPlayer.setHealth(20);
        bukkitPlayer.setFoodLevel(20);
        bukkitPlayer.setLevel(0);
        bukkitPlayer.setExp(0);
        bukkitPlayer.setCollidable(true);
        bukkitPlayer.setCanPickupItems(true);
        bukkitPlayer.setFlying(false);
        bukkitPlayer.setAllowFlight(false);

        if (player.getLeaveSpectateLocation() != null && player.getLeaveSpectateLocation().getWorld() != null) {
            bukkitPlayer.teleport(player.getLeaveSpectateLocation());
        }

        bukkitPlayer.getInventory().clear();
    }

}
