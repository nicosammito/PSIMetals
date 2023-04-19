package com.uroria.gameapi.player;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public final class GamePlayerFlagUpdateEvent extends Event implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final GamePlayer player;
    private final Set<GamePlayerFlag> previousFlags;
    private final Set<GamePlayerFlag> newFlags;
    private boolean cancelled;

    public GamePlayerFlagUpdateEvent(final GamePlayer player, final Set<GamePlayerFlag> previousFlags, final Set<GamePlayerFlag> newFlags) {
        this.player = player;
        this.previousFlags = previousFlags;
        this.newFlags = newFlags;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }

    public GamePlayer getPlayer() {
        return player;
    }

    public Set<GamePlayerFlag> getNewFlags() {
        return newFlags;
    }

    public Set<GamePlayerFlag> getPreviousFlags() {
        return previousFlags;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
