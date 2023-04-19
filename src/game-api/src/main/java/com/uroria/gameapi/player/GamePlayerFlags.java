package com.uroria.gameapi.player;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Team;

import java.util.Optional;

public enum GamePlayerFlags implements GamePlayerFlag {

    PLAYER("default flag for players", null),
    SPECTATOR("if player is spectator", Bukkit.getScoreboardManager().getMainScoreboard().getTeam("spectator") == null ? Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("spectator") : Bukkit.getScoreboardManager().getMainScoreboard().getTeam("spectator"));

    private final String description;
    private final Team team;

    GamePlayerFlags(final String description, final Team team) {
        this.description = description;
        this.team = team;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public Optional<Team> getTeam() {
        return Optional.ofNullable(this.team);
    }
}
