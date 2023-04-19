package com.uroria.gameapi.player;

import com.uroria.gameapi.store.CacheManager;
import com.uroria.gameapi.store.FlagManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public final class GamePlayer extends CacheManager implements FlagManager<GamePlayerFlag> {

    private static final String FLAG_KEY = "__flag__";
    private final org.bukkit.entity.Player bukkitPlayer;
    private Location enterSpectateLocation;
    private Location leaveSpectateLocation;

    GamePlayer(final org.bukkit.entity.Player bukkitPlayer,
               final Location enterSpectateLocation,
               final Location leaveSpectateLocation) {
        super();
        this.bukkitPlayer = bukkitPlayer;
        this.enterSpectateLocation = enterSpectateLocation;
        this.leaveSpectateLocation = leaveSpectateLocation;
    }


    @Override
    public void addFlags(GamePlayerFlag... flag) {

        final var newFlags = new HashSet<>(this.getCurrentFlags());
        newFlags.addAll(Arrays.asList(flag));

        if (Arrays.stream(newFlags.toArray(GamePlayerFlag[]::new)).filter(playerFlag -> playerFlag.getTeam().isPresent()).count() > 1) {
            throw new IllegalArgumentException("Providing more than one flag with a bukkit team isn't allowed");
        }

        final var event = new GamePlayerFlagUpdateEvent(this, this.getCurrentFlags(), newFlags);
        Bukkit.getServer().getPluginManager().callEvent(event);


        if (!event.isCancelled()) {

            //add player to team for provided flag
            Arrays.stream(flag).filter(playerFlag -> playerFlag.getTeam().isPresent())
                    .findFirst()
                    .ifPresent(playerFlag -> playerFlag.getTeam().orElseThrow().addPlayer(this.bukkitPlayer));

            this.addToCache(FLAG_KEY, newFlags);
        }

    }

    @Override
    public void removeFlags(GamePlayerFlag... flag) {

        if (Arrays.stream(flag).filter(playerFlag -> playerFlag.getTeam().isPresent()).count() > 1) {
            throw new IllegalArgumentException("Providing more than one flag with a bukkit team isn't allowed");
        }

        final var newFlags = new HashSet<>(this.getCurrentFlags());
        Arrays.asList(flag).forEach(newFlags::remove);

        final var event = new GamePlayerFlagUpdateEvent(this, this.getCurrentFlags(), newFlags);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {

            //remove player from team for provided flag
            Arrays.stream(flag).filter(playerFlag -> playerFlag.getTeam().isPresent())
                    .findFirst()
                    .ifPresent(playerFlag -> playerFlag.getTeam().orElseThrow().removePlayer(this.bukkitPlayer));

            this.addToCache(FLAG_KEY, newFlags);
        }

    }

    /**
     * checks weither one of the given {@link GamePlayerFlags} is set on the given {@link GamePlayer}
     *
     * @param flag or flags that need to be checked weither one of them are a
     *             current flag set on the {@link GamePlayer}
     * @return true or false depending on given {@link GamePlayerFlags} that are checked for present
     */
    @Override
    public boolean hasFlags(GamePlayerFlag... flag) {
        return this.getCurrentFlags().stream().anyMatch(Arrays.asList(flag)::contains);
    }

    /**
     * checks weither all of the given {@link GamePlayerFlags} are set on the given {@link GamePlayer}
     *
     * @param flags that need to be checked weither all of them are a
     *             current flags set on the {@link GamePlayer}
     * @return true or false depending on given {@link GamePlayerFlags} that are checked for present
     */
    @Override
    public boolean hasAllFlags(GamePlayerFlag... flags) {
        return this.getCurrentFlags().containsAll(Arrays.asList(flags));
    }

    /**
     * Weither a {@link GamePlayer} is a spectator matters appon the {@link GamePlayerFlags} that are set.
     * Thus makes the method either return true or false
     *
     * @return a boolean weather the given player is a spectator
     * respectively has the {@link GamePlayerFlags#SPECTATOR} flag
     * @implSpec this method calls {@link #hasFlags(GamePlayerFlag...)} inside to check for the
     * needed flags which turnes a player into a spectator
     */
    public boolean isSpectator() {
        return this.hasFlags(GamePlayerFlags.SPECTATOR);
    }

    public org.bukkit.entity.Player getBukkitPlayer() {
        return this.bukkitPlayer;
    }

    public void setEnterSpectateLocation(final Location enterSpectateLocation) {
        this.enterSpectateLocation = enterSpectateLocation;
    }

    public void setLeaveSpectateLocation(final Location leaveSpectateLocation) {
        this.leaveSpectateLocation = leaveSpectateLocation;
    }

    public Location getEnterSpectateLocation() {
        return enterSpectateLocation;
    }

    public Location getLeaveSpectateLocation() {
        return leaveSpectateLocation;
    }

    @SuppressWarnings("unchecked")
    private Set<GamePlayerFlag> getCurrentFlags() {
        return (Set<GamePlayerFlag>) Optional.ofNullable(this.getValueFromCache(FLAG_KEY)).orElse(new HashSet<>());
    }
}
