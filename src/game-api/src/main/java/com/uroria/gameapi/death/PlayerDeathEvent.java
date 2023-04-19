package com.uroria.gameapi.death;

import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class PlayerDeathEvent extends org.bukkit.event.entity.PlayerDeathEvent {
    public PlayerDeathEvent(@NotNull Player player, @NotNull List<ItemStack> drops, int droppedExp, @Nullable Component adventuredeathMessage) {
        super(player, drops, droppedExp, adventuredeathMessage);
    }
}
