package com.uroria.gameapi.util.inventory;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Consumer;

public final class InventoryBuilder implements Listener {

    private String title;
    private int size;
    private final Collection<ConnectedItem> itemStackIntegerMap;

    private InventoryBuilder(Plugin plugin) {
        this.title = "";
        this.size = 9;
        this.itemStackIntegerMap = new ArrayList<>();
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public InventoryBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public InventoryBuilder withSize(int size) {
        this.size = size;
        return this;
    }

    public InventoryBuilder withItem(final ItemStack item, final int index, final Consumer<InventoryClickEvent> eventConsumer) {
        this.itemStackIntegerMap.add(new ConnectedItem(index, item, eventConsumer));
        return this;
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        if (event.getCurrentItem() == null) return;

        //call event consumer on clicked item
        this.itemStackIntegerMap.forEach((final var connectedItem) -> {
            if (connectedItem.consumer != null && event.getSlot() == connectedItem.index)
                connectedItem.consumer.accept(event);
        });
    }

    public org.bukkit.inventory.Inventory build() {
        final var inventory = Bukkit.createInventory(null, size, Component.text(title));
        this.itemStackIntegerMap.forEach(connectedItem -> inventory.setItem(connectedItem.index, connectedItem.item));
        return inventory;
    }

    public static InventoryBuilder create(Plugin plugin) {
        return new InventoryBuilder(plugin);
    }

    private record ConnectedItem(int index, ItemStack item, Consumer<InventoryClickEvent> consumer) {}

}
