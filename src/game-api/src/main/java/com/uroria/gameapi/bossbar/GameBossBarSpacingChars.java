package com.uroria.gameapi.bossbar;

enum GameBossBarSpacingChars {

    PIXEL_FORWORD("\uE0A9"),
    PIXEL_BACKWORDS("\uE0A8");

    private final String character;

    GameBossBarSpacingChars(String character) {
        this.character = character;
    }

    String unicodeCharacter() {
        return this.character;
    }

}
