package com.uroria.gameapi.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class GamePlayerManager {

    private final Set<GamePlayer> gamePlayers;
    private final Location enterSpectateLocation;
    private final Location leaveSpectateLocation;

    public GamePlayerManager(Location enterSpectateLocation, Location leaveSpectateLocation) {
        this.gamePlayers = new HashSet<>();
        this.enterSpectateLocation = enterSpectateLocation;
        this.leaveSpectateLocation = leaveSpectateLocation;
    }

    @ApiStatus.Internal
    void addPlayer(org.bukkit.entity.Player bukkitPlayer) {

        //set default flag to PlayerFlags.PLAYER
        var player = new GamePlayer(bukkitPlayer, this.enterSpectateLocation, this.leaveSpectateLocation);
        player.addFlags(GamePlayerFlags.PLAYER);

        this.gamePlayers.add(player);
    }

    @ApiStatus.Internal
    void removePlayer(Player bukkitPlayer) {
        this.gamePlayers.remove(this.getPlayer(bukkitPlayer));
    }

    /**
     * Please use {@link GamePlayerManager#gamePlayer(Player)} instead.
     * This method will be removed in later versions.
     *
     * @param bukkitPlayer the asosiated {@link Player} with the {@link GamePlayer}
     * @return a {@link GamePlayer} which coresponds to the given {@link Player}
     */
    @Deprecated
    public GamePlayer getPlayer(Player bukkitPlayer) {
        for (final var player : this.gamePlayers) {
            if (player.getBukkitPlayer() == bukkitPlayer) return player;
        }

        return null;
    }

    /**
     * @param bukkitPlayer the assosiated {@link Player} with the {@link GamePlayer}
     * @return a {@link Optional<GamePlayer>} which coresponds to the given {@link Player}
     */
    public Optional<GamePlayer> gamePlayer(Player bukkitPlayer) {
        return this.gamePlayers.stream()
                .filter(gamePlayer -> gamePlayer.getBukkitPlayer() == bukkitPlayer)
                .findFirst();
    }

    public Set<GamePlayer> players() {
        return Collections.unmodifiableSet(this.gamePlayers);
    }

    public Set<GamePlayer> playersWithFlag(GamePlayerFlag... flags) {
        return this.gamePlayers.stream()
                .filter(player -> player.hasFlags(flags)).collect(Collectors.toUnmodifiableSet());
    }

    public Set<org.bukkit.entity.Player> bukkitPlayersWithFlag(GamePlayerFlag... flags) {
        return this.gamePlayers.stream()
                .filter(player -> player.hasFlags(flags))
                .map(GamePlayer::getBukkitPlayer).collect(Collectors.toUnmodifiableSet());
    }

    public Set<GamePlayer> playersWithoutFlag(GamePlayerFlag... flags) {
        return this.gamePlayers.stream()
                .filter(player -> !player.hasFlags(flags)).collect(Collectors.toUnmodifiableSet());
    }

    public Set<org.bukkit.entity.Player> bukkitPlayersWithoutFlag(GamePlayerFlag... flags) {
        return this.gamePlayers.stream()
                .filter(player -> !player.hasFlags(flags))
                .map(GamePlayer::getBukkitPlayer).collect(Collectors.toUnmodifiableSet());
    }
}
