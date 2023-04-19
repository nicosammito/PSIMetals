package com.uroria.gameapi.bossbar;

enum GameBossBarBackgroundChars {

    START("\uE050", "\uE119", "\uE126", "\uE112"),
    END("\uE051", "\uE120", "\uE127", "\uE113"),
    ONE_PIXEL("\uE052", "\uE121", "\uE128", "\uE114"),
    TWO_PIXEL("\uE053", "\uE122", "\uE129", "\uE115"),
    THREE_PIXEL("\uE054", "\uE123", "\uE130", "\uE116"),
    FOUR_PIXEL("\uE055", "\uE124", "\uE131", "\uE117"),
    FIVE_PIXEL("\uE056", "\uE125", "\uE132", "\uE118");

    private final String unicodeCharacterBlack;
    private final String unicodeCharacterRed;
    private final String unicodeCharacterYellow;
    private final String unicodeCharacterGreen;

    GameBossBarBackgroundChars(final String uCB, final String uCR, final String uCY, final String uCG) {
        this.unicodeCharacterBlack = uCB;
        this.unicodeCharacterRed = uCR;
        this.unicodeCharacterYellow = uCY;
        this.unicodeCharacterGreen = uCG;
    }

    String unicodeBackgroundCharacterFromColor(GameBossBarBackgroundColors color) {

        return switch (color) {
            case RED -> this.unicodeCharacterRed;
            case YELLOW -> this.unicodeCharacterYellow;
            case GREEN -> this.unicodeCharacterGreen;
            default -> this.unicodeCharacterBlack;
        };

    }

    static String backgroundFromWidth(Integer width, final GameBossBarBackgroundColors color) {

        final var backgroundChars = new StringBuilder();

        backgroundChars.append(START.unicodeBackgroundCharacterFromColor(color));

        for (int i = width; i > 5; i = i - 5) {
            backgroundChars.append(GameBossBarSpacingChars.PIXEL_BACKWORDS.unicodeCharacter());
            backgroundChars.append(FIVE_PIXEL.unicodeBackgroundCharacterFromColor(color));
            width = width - 5;
        }

        backgroundChars.append(GameBossBarSpacingChars.PIXEL_BACKWORDS.unicodeCharacter());

        var unicodeCharacter = switch (width) {
            case 5 -> FIVE_PIXEL.unicodeBackgroundCharacterFromColor(color);
            case 4 -> FOUR_PIXEL.unicodeBackgroundCharacterFromColor(color);
            case 3 -> THREE_PIXEL.unicodeBackgroundCharacterFromColor(color);
            case 2 -> TWO_PIXEL.unicodeBackgroundCharacterFromColor(color);
            case 1 -> ONE_PIXEL.unicodeBackgroundCharacterFromColor(color);
            default -> "";
        };

        backgroundChars.append(unicodeCharacter);
        backgroundChars.append(GameBossBarSpacingChars.PIXEL_BACKWORDS.unicodeCharacter());
        backgroundChars.append(END.unicodeBackgroundCharacterFromColor(color));

        return backgroundChars.toString();
    }

}
