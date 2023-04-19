package com.uroria.gameapi.bossbar;

import com.uroria.gameapi.player.GamePlayer;
import com.uroria.gameapi.util.Pair;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;

public final class GameBossBar {

    private static final Integer COLUMN_PADDING = 4;
    private final LinkedHashMap<Object, Pair<GameBossBarTextChar[], GameBossBarBackgroundColors>> bossBarColumns;
    private final BossBar bossBarBackground;
    private final BossBar bossBarText;

    public GameBossBar() {
        this.bossBarColumns = new LinkedHashMap<>();
        this.bossBarBackground = Bukkit.createBossBar("", BarColor.YELLOW, BarStyle.SOLID);
        this.bossBarText = Bukkit.createBossBar("", BarColor.YELLOW, BarStyle.SOLID);
    }

    public void addColumn(final GameBossBarIcon icon, final GameBossBarTextChar... text) {
        this.addColumn(icon, GameBossBarBackgroundColors.BLACK, text);
    }

    public void addColumn(final Integer index, final GameBossBarTextChar... text) {
        this.addColumn(index, GameBossBarBackgroundColors.BLACK, text);
    }

    public void addColumn(final Integer index, final Function<? super List<GameBossBarTextChar>, GameBossBarTextChar[]> function) {
        this.addColumn(index, GameBossBarBackgroundColors.BLACK, function);
    }

    public void addColumn(final GameBossBarIcon icon, final GameBossBarBackgroundColors color, final GameBossBarTextChar... text) {
        this.bossBarColumns.put(icon, new Pair<>(text, color));
        this.generateBackgroundBossBar();
        this.generateTextBossBar();
    }

    public void addColumn(final Integer index, final GameBossBarBackgroundColors color, final GameBossBarTextChar... text) {
        this.bossBarColumns.put(index, new Pair<>(text, color));
        this.generateBackgroundBossBar();
        this.generateTextBossBar();
    }

    public void addColumn(final Integer index, final GameBossBarBackgroundColors color, final Function<? super List<GameBossBarTextChar>, GameBossBarTextChar[]> function) {
        var text = function.apply(new ArrayList<>());
        this.bossBarColumns.put(index, new Pair<>(text, color));
        this.generateBackgroundBossBar();
        this.generateTextBossBar();
    }

    public void editColumn(final GameBossBarIcon icon, final GameBossBarTextChar... text) {
        this.editColumn(icon, GameBossBarBackgroundColors.BLACK, text);
    }

    public void editColumn(final Integer index, final GameBossBarTextChar... text) {
        this.editColumn(index, GameBossBarBackgroundColors.BLACK, text);
    }

    public void editColumn(final Integer index, final Function<? super List<GameBossBarTextChar>, GameBossBarTextChar[]> function) {
        this.editColumn(index, GameBossBarBackgroundColors.BLACK, function);
    }

    public void editColumn(final GameBossBarIcon icon, final GameBossBarBackgroundColors color, final GameBossBarTextChar... text) {
        this.bossBarColumns.replace(icon, new Pair<>(text, color));
        this.generateBackgroundBossBar();
        this.generateTextBossBar();
    }

    public void editColumn(final Integer index, final GameBossBarBackgroundColors color, final GameBossBarTextChar... text) {
        this.bossBarColumns.replace(index, new Pair<>(text, color));
        this.generateBackgroundBossBar();
        this.generateTextBossBar();
    }

    public void editColumn(final Integer index, final GameBossBarBackgroundColors color, final Function<? super List<GameBossBarTextChar>, GameBossBarTextChar[]> function) {
        var text = function.apply(new ArrayList<>());
        this.bossBarColumns.replace(index, new Pair<>(text, color));
        this.generateBackgroundBossBar();
        this.generateTextBossBar();
    }

    public void removeColumn(final GameBossBarIcon index) {
        this.bossBarColumns.remove(index);
        this.generateBackgroundBossBar();
        this.generateTextBossBar();
    }

    public void removeColumn(final Integer index) {
        this.bossBarColumns.remove(index);
        this.generateBackgroundBossBar();
        this.generateTextBossBar();
    }

    public void addPlayer(GamePlayer... gamePlayers) {
        Arrays.stream(gamePlayers).forEach(gamePlayer -> {
            bossBarBackground.addPlayer(gamePlayer.getBukkitPlayer());
            bossBarText.addPlayer(gamePlayer.getBukkitPlayer());
        });
    }

    public void addPlayer(Player... players) {
        Arrays.stream(players).forEach(player -> {
            bossBarBackground.addPlayer(player);
            bossBarText.addPlayer(player);
        });
    }

    public void addPlayer(GamePlayer gamePlayer) {
        bossBarBackground.addPlayer(gamePlayer.getBukkitPlayer());
        bossBarText.addPlayer(gamePlayer.getBukkitPlayer());
    }

    public void addPlayer(Player player) {
        bossBarBackground.addPlayer(player);
        bossBarText.addPlayer(player);
    }

    public void removePlayer(GamePlayer... gamePlayers) {
        Arrays.stream(gamePlayers).forEach(gamePlayer -> {
            bossBarBackground.removePlayer(gamePlayer.getBukkitPlayer());
            bossBarText.removePlayer(gamePlayer.getBukkitPlayer());
        });
    }

    public void removePlayer(Player... players) {
        Arrays.stream(players).forEach(player -> {
            bossBarBackground.removePlayer(player);
            bossBarText.removePlayer(player);
        });
    }

    public void removePlayer(GamePlayer gamePlayer) {
        bossBarBackground.removePlayer(gamePlayer.getBukkitPlayer());
        bossBarText.removePlayer(gamePlayer.getBukkitPlayer());
    }

    public void removePlayer(Player player) {
        bossBarBackground.removePlayer(player);
        bossBarText.removePlayer(player);
    }

    private void generateBackgroundBossBar() {

        final var backgroundBuilder = new StringBuilder();
        final var keySetArray = this.bossBarColumns.keySet().toArray(Object[]::new);
        final var space = GameBossBarSpacingChars.PIXEL_FORWORD.unicodeCharacter().repeat(4);

        for (var i = 0; i < this.bossBarColumns.size(); i++) {

            //calculated width for text with icon and space between text and icon
            var width = (keySetArray[i] instanceof GameBossBarIcon ? ((GameBossBarIcon) keySetArray[i]).width() + COLUMN_PADDING : 0 ) + Arrays.stream(this.bossBarColumns.get(keySetArray[i]).first()).mapToInt(GameBossBarTextChar::width).sum();
            backgroundBuilder.append(GameBossBarBackgroundChars.backgroundFromWidth(width, this.bossBarColumns.get(keySetArray[i]).second()));

            if (i < (this.bossBarColumns.size() - 1)) backgroundBuilder.append(space);

        }

        var serialize = LegacyComponentSerializer.legacySection().serialize(MiniMessage.miniMessage().deserialize("<color:#4e5c24>" + backgroundBuilder + "</color>"));
        bossBarBackground.setTitle(serialize);

    }

    private void generateTextBossBar() {

        final var textBuilder = new StringBuilder();
        final var keySetArray = this.bossBarColumns.keySet().toArray(Object[]::new);
        final var space = GameBossBarSpacingChars.PIXEL_FORWORD.unicodeCharacter().repeat(4);

        for (var i = 0; i < this.bossBarColumns.size(); i++) {

            textBuilder.append(space);
            if (keySetArray[i] instanceof GameBossBarIcon) {
                textBuilder.append(((GameBossBarIcon) keySetArray[i]).icon());
                textBuilder.append(space);
                textBuilder.append(GameBossBarSpacingChars.PIXEL_BACKWORDS.unicodeCharacter());
            }

            for (var z = 0; z < this.bossBarColumns.get(keySetArray[i]).first().length; z++) {

                final var gameBossBarTextChar = this.bossBarColumns.get(keySetArray[i]).first()[z];

                if (gameBossBarTextChar != GameBossBarTextChars.space && z != 0)
                    textBuilder.append(GameBossBarSpacingChars.PIXEL_BACKWORDS.unicodeCharacter());
                textBuilder.append(gameBossBarTextChar.unicodeCharacter());

            }

            textBuilder.append(space);

            if (i < (this.bossBarColumns.size() - 1)) textBuilder.append(space);
        }

        var serialize = LegacyComponentSerializer.legacySection().serialize(MiniMessage.miniMessage().deserialize("<color:#4e5c24>" + textBuilder + "</color>"));
        bossBarText.setTitle(serialize);

    }

}
