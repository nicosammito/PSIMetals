package com.uroria.gameapi.state;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class GameState implements State {

    private final Set<Listener> listeners;
    private final JavaPlugin javaPlugin;

    public GameState(JavaPlugin javaPlugin) {
        this.javaPlugin = javaPlugin;
        this.listeners = new HashSet<>();
    }

    protected void registerListener(Listener listener) {
        this.listeners.add(listener);
        javaPlugin.getServer().getPluginManager().registerEvents(listener, javaPlugin);
    }

    protected <T extends Event> void registerListener(Consumer<? super T> eventConsumer, Class<T> eventClass, EventPriority eventPriority) {

        final var tempListener = new Listener() {};
        this.listeners.add(tempListener);
        javaPlugin.getServer().getPluginManager()
                .registerEvent(eventClass, tempListener, eventPriority, (listener, event) -> eventConsumer.accept(eventClass.cast(event)), javaPlugin);
    }

    protected void unregisterListener(Listener listener) {
        if (!listeners.contains(listener)) return;
        HandlerList.unregisterAll(listener);
    }

    protected void unregisterAllListener() {
        listeners.forEach(HandlerList::unregisterAll);
    }
}
