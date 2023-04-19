package com.uroria.gameapi.player;

import com.uroria.gameapi.store.Flag;
import org.bukkit.scoreboard.Team;

import java.util.Optional;

@FunctionalInterface
public interface GamePlayerFlag extends Flag {

    default Optional<Team> getTeam() {
        return Optional.empty();
    };

}
