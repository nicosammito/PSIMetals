package com.uroria.gameapi.bossbar;

public enum GameBossBarIcons implements GameBossBarIcon {

    CLOCK("\uE058"),
    COMPASS("\uE057"),
    CREATOR("\uE0AC"),
    MAP("\uE0B0"),
    SKULL("\uE0B2"),
    SWORD("\uE0B3"),
    PLAYER_HEAD("\uE0AE"),
    GOLD_TROPHY("\uE0AD"),
    SILVER_TROPHY("\uE0B1"),
    BRONZE_TROPHY("\uE0AB");

    private final String unicodeCharacter;

    GameBossBarIcons(final String unicodeCharacter) {
        this.unicodeCharacter = unicodeCharacter;
    }

    @Override
    public String icon() {
        return this.unicodeCharacter;
    }
}
