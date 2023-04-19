package com.uroria.gameapi.bossbar;

@FunctionalInterface
public interface GameBossBarIcon {

    String icon();

    default Integer width() {
        return 10;
    }

}
