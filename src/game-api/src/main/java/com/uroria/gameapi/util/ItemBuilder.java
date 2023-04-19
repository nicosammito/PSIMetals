package com.uroria.gameapi.util;

import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;
import java.util.stream.Collectors;

public final class ItemBuilder {

    private final ItemStack itemStack;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemBuilder(Material material) {
        this(new ItemStack(material));
    }

    //setting the display name of an ItemStack
    public ItemBuilder setDisplayName(String displayName) {
        var meta = itemStack.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName(displayName);
        itemStack.setItemMeta(meta);
        return this;
    }

    //setting the amount of an ItemStack

    public ItemBuilder setAmount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    //setting the owner of a player head

    public ItemBuilder setOwner(OfflinePlayer player) {
        var skullMeta = (SkullMeta) itemStack.getItemMeta();
        skullMeta.setOwningPlayer(player);
        itemStack.setItemMeta(skullMeta);
        return this;
    }

    //setting a lore to the item [problem: only one lore at the moment]

    public ItemBuilder setLore(String lore) {
        var meta = itemStack.getItemMeta();
        Objects.requireNonNull(meta).setLore(Collections.singletonList(lore));
        itemStack.setItemMeta(meta);
        return this;
    }

    //setting a List of Strings to a lore

    public ItemBuilder setLore(List<String> stringList) {
        var meta = itemStack.getItemMeta();
        Objects.requireNonNull(meta).setLore(stringList);
        itemStack.setItemMeta(meta);
        return this;
    }

    //setting a List of Strings to a lore


    public ItemBuilder setLore(String... name) {
        var meta = itemStack.getItemMeta();
        Objects.requireNonNull(meta).lore(Arrays.stream(name).map(Component::text).collect(Collectors.toList()));
        itemStack.setItemMeta(meta);
        return this;
    }

    //setting the color of Leather Armor

    public ItemBuilder setColor(Color color) {
        var leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
        Objects.requireNonNull(leatherArmorMeta).setColor(color);
        itemStack.setItemMeta(leatherArmorMeta);
        return this;
    }

    //adding a page to the Book [problem setting only 1 site of a book]

    public ItemBuilder addPage(String string) {
        var bookMeta = (BookMeta) itemStack.getItemMeta();
        Objects.requireNonNull(bookMeta).addPage(string);
        itemStack.setItemMeta(bookMeta);
        return this;
    }

    //setting a specific page by their chosen number

    public ItemBuilder setPage(int page, String data) {
        var bookMeta = (BookMeta) itemStack.getItemMeta();
        Objects.requireNonNull(bookMeta).setPage(page, data);
        itemStack.setItemMeta(bookMeta);
        return this;
    }

    //setting a title of the written book

    public ItemBuilder setTitle(String title) {
        var bookMeta = (BookMeta) itemStack.getItemMeta();
        Objects.requireNonNull(bookMeta).setTitle(title);
        itemStack.setItemMeta(bookMeta);
        return this;
    }

    //setting the author of the written book

    public ItemBuilder setAuthor(String author) {
        var bookMeta = (BookMeta) itemStack.getItemMeta();
        Objects.requireNonNull(bookMeta).setAuthor(author);
        itemStack.setItemMeta(bookMeta);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }

    public ItemStack itemStack() {
        return itemStack;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ItemBuilder) obj;
        return Objects.equals(this.itemStack, that.itemStack);
    }

    @Override
    public int hashCode() {
        return Objects.hash(itemStack);
    }

    @Override
    public String toString() {
        return "ItemBuilder[" +
                "itemStack=" + itemStack + ']';
    }

}

