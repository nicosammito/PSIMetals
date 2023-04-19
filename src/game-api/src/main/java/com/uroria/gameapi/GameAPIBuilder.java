package com.uroria.gameapi;

import com.uroria.gameapi.player.GamePlayerManager;
import com.uroria.gameapi.state.GameState;
import com.uroria.gameapi.state.GameStateManagerImpl;
import com.uroria.gameapi.task.Scheduling;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @since 0.0.1
 */
public final class GameAPIBuilder {

    private final JavaPlugin plugin;
    private GameStateManagerImpl gameStateManager;
    private GamePlayerManager playerManager;

    GameAPIBuilder(JavaPlugin plugin) {
        this.plugin = plugin;
        this.playerManager = new GamePlayerManager(null, null);
        this.gameStateManager = new GameStateManagerImpl();
    }

    public GameAPIBuilder withGamestates(GameState... states) {
        this.gameStateManager = new GameStateManagerImpl(states);
        return this;
    }

    public GameAPIBuilder withSpectatorSystem(Location enterSpectateLocation, Location leaveSpectateLocation) {
        this.playerManager = new GamePlayerManager(enterSpectateLocation, leaveSpectateLocation);
        return this;
    }

    public GameAPI build() {

        Scheduling.initialize(plugin);

        var api = new GameAPI(
                plugin,
                gameStateManager,
                playerManager
        );

        if (gameStateManager != null) gameStateManager.setGameAPI(api);

        api.registerListener();

        if (gameStateManager != null && gameStateManager.getCurrentState() != null) {
            gameStateManager.getCurrentState().start(api);
        }

        return api;
    }
}
