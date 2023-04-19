package com.uroria.gameapi.spectate;

import com.uroria.gameapi.player.GamePlayer;
import com.uroria.gameapi.player.GamePlayerManager;
import com.uroria.gameapi.task.Scheduling;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

public record SpectatorEnterTargetListener(GamePlayerManager playerManager, JavaPlugin javaPlugin) implements Listener {

    private static final Collection<String> SPECTATORS_IN_TARGET_MODE = new ArrayList<>();

    @EventHandler
    public void onPlayerDamageBySpectator(final EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player damager)) return;
        if (!(event.getEntity() instanceof Player hittedPlayer)) return;

        final var gamePlayer = this.playerManager.gamePlayer(damager);
        if (gamePlayer.isEmpty()) return;
        if (!gamePlayer.orElseThrow().isSpectator()) return;

        SPECTATORS_IN_TARGET_MODE.add(damager.getUniqueId().toString());
        damager.setGameMode(GameMode.SPECTATOR);
        damager.setSpectatorTarget(hittedPlayer);

        Scheduling.runDefaultTaskTimer((ignored) -> {
            damager.sendActionBar(this.getHealthAsString(hittedPlayer.getHealth()));
            return !SPECTATORS_IN_TARGET_MODE.contains(damager.getUniqueId().toString());
        }, 1, TimeUnit.SECONDS);
    }

    @EventHandler
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {

        final var gamePlayer = this.playerManager.gamePlayer(event.getPlayer());
        if (gamePlayer.isEmpty()) return;
        if (!gamePlayer.orElseThrow().isSpectator()) return;
        if (!event.isSneaking()) return;
        if (!SPECTATORS_IN_TARGET_MODE.contains(event.getPlayer().getUniqueId().toString())) return;

        event.getPlayer().setSpectatorTarget(null);
        SPECTATORS_IN_TARGET_MODE.remove(event.getPlayer().getUniqueId().toString());
        this.resetSpectator(gamePlayer.orElseThrow());

    }

    private void resetSpectator(final GamePlayer player) {

        final var bukkitPlayer = player.getBukkitPlayer();
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> onlinePlayer.hidePlayer(javaPlugin, bukkitPlayer));

        bukkitPlayer.setGameMode(GameMode.ADVENTURE);
        bukkitPlayer.setAllowFlight(true);
        bukkitPlayer.setCollidable(false);
        bukkitPlayer.setCanPickupItems(false);
        bukkitPlayer.setFlying(true);
        bukkitPlayer.setHealth(20);
        bukkitPlayer.setFoodLevel(20);
        bukkitPlayer.setLevel(0);
        bukkitPlayer.setExp(0);

        bukkitPlayer.getInventory().clear();
        bukkitPlayer.getInventory().setItem(3, SpectatorFlagUpdateListener.PLAYERS_ITEM);
        bukkitPlayer.getInventory().setItem(5, SpectatorFlagUpdateListener.LEAVE_ITEM);

    }

    private Component getHealthAsString(final double health) {

        final var componentList = Component.text();

        for (var i = health; i > 0; i = i - 2) {
            if (i > 1) componentList.append(MiniMessage.miniMessage().deserialize("<dark_red>❤ </dark_red>"));
            else componentList.append(MiniMessage.miniMessage().deserialize("<red>❤ </red>"));
        }

        for (var i = 20.0 - health; i >= 2; i = i - 2) {
            componentList.append(MiniMessage.miniMessage().deserialize("<gray>❤ </gray>"));
        }

        return componentList.asComponent();

    }

}
