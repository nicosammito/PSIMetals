package com.uroria.gameapi;

import com.uroria.gameapi.death.EntityDamageListener;
import com.uroria.gameapi.player.GamePlayerConnectionListener;
import com.uroria.gameapi.player.GamePlayerManager;
import com.uroria.gameapi.spectate.EntityDamageBySpectatorListener;
import com.uroria.gameapi.spectate.SpectatorBlockBreakListener;
import com.uroria.gameapi.spectate.SpectatorDamageListener;
import com.uroria.gameapi.spectate.SpectatorEnterTargetListener;
import com.uroria.gameapi.spectate.SpectatorFlagUpdateListener;
import com.uroria.gameapi.spectate.SpectatorFoodLevelChangeListener;
import com.uroria.gameapi.spectate.SpectatorInteractListener;
import com.uroria.gameapi.spectate.SpectatorInventoryListener;
import com.uroria.gameapi.spectate.SpectatorItemListener;
import com.uroria.gameapi.spectate.SpectatorTargetListener;
import com.uroria.gameapi.state.GameStateManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @since 0.0.1
 */
public final class GameAPI {

    /**
     * Plugin instance which implemented the GameAPI
     */
    private final JavaPlugin javaPlugin;
    private final GameStateManager gameStateManager;
    private final GamePlayerManager gamePlayerManager;

    GameAPI(JavaPlugin javaPlugin, GameStateManager gameStateManager, GamePlayerManager playerManager) {
        this.javaPlugin = javaPlugin;
        this.gameStateManager = gameStateManager;
        this.gamePlayerManager = playerManager;
    }

    void registerListener() {

        final var pluginManager = this.javaPlugin.getServer().getPluginManager();

        //listener for playermanager
        pluginManager.registerEvents(new GamePlayerConnectionListener(gamePlayerManager), javaPlugin);

        //listener for own PlayerDeathEvent
        pluginManager.registerEvents(new EntityDamageListener(), javaPlugin);

        //spectator Listeners
        pluginManager.registerEvents(new SpectatorInteractListener(javaPlugin, gamePlayerManager), javaPlugin);
        pluginManager.registerEvents(new EntityDamageBySpectatorListener(gamePlayerManager), javaPlugin);
        pluginManager.registerEvents(new SpectatorFlagUpdateListener(javaPlugin), javaPlugin);
        pluginManager.registerEvents(new SpectatorBlockBreakListener(gamePlayerManager), javaPlugin);
        pluginManager.registerEvents(new SpectatorInventoryListener(gamePlayerManager), javaPlugin);
        pluginManager.registerEvents(new SpectatorItemListener(gamePlayerManager), javaPlugin);
        pluginManager.registerEvents(new SpectatorTargetListener(gamePlayerManager), javaPlugin);
        pluginManager.registerEvents(new SpectatorDamageListener(gamePlayerManager), javaPlugin);
        pluginManager.registerEvents(new SpectatorFoodLevelChangeListener(gamePlayerManager), javaPlugin);
        pluginManager.registerEvents(new SpectatorEnterTargetListener(gamePlayerManager, javaPlugin), javaPlugin);

    }

    public JavaPlugin getPlugin() {
        return javaPlugin;
    }

    public String getPrefix() {
        return "§b§l▍ ɢᴀᴍᴇᴀᴘɪ §b§l▏ §7";
    }

    public GameStateManager getGameStateManager() {
        return gameStateManager;
    }

    public GamePlayerManager getGamePlayerManager() {
        return gamePlayerManager;
    }

    /**
     * @param javaPlugin Plugin instance which implemented the GameAPI
     * @return GameAPIBuilder
     */
    public static GameAPIBuilder create(JavaPlugin javaPlugin) {
        return new GameAPIBuilder(javaPlugin);
    }

}
