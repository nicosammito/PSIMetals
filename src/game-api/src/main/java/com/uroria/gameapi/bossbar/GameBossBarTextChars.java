package com.uroria.gameapi.bossbar;

import java.util.LinkedList;

public enum GameBossBarTextChars implements GameBossBarTextChar {

    A("A", "\uE076", 6),
    B("B", "\uE077", 6),
    C("C", "\uE078", 6),
    D("D", "\uE079", 6),
    E("E", "\uE07A", 6),
    F("F", "\uE07B", 6),
    G("G", "\uE07C", 6),
    H("H", "\uE07D", 6),
    I("I", "\uE07E", 4),
    J("J", "\uE07F", 6),
    K("K", "\uE080", 6),
    L("L", "\uE081", 6),
    M("M", "\uE082", 6),
    N("N", "\uE083", 6),
    O("O", "\uE084", 6),
    P("P", "\uE085", 6),
    Q("Q", "\uE086", 6),
    R("R", "\uE087", 6),
    S("S", "\uE088", 6),
    T("T", "\uE089", 6),
    U("U", "\uE08A", 6),
    V("V", "\uE08B", 6),
    W("W", "\uE08C", 6),
    X("X", "\uE08D", 6),
    Y("Y", "\uE08E", 6),
    Z("Z", "\uE08F", 6),
    AE("Ä", "\uE090", 6),
    OE("Ö", "\uE091", 6),
    UE("Ü", "\uE091", 6),
    a("a", "\uE059", 6),
    b("b", "\uE05A", 6),
    c("c", "\uE05B", 6),
    d("d", "\uE05C", 6),
    e("e", "\uE05D", 6),
    f("f", "\uE05E", 5),
    g("g", "\uE05F", 6),
    h("h", "\uE060", 6),
    i("i", "\uE061", 2),
    j("j", "\uE062", 6),
    k("k", "\uE063", 5),
    l("l", "\uE064", 3),
    m("m", "\uE065", 6),
    n("n", "\uE066", 6),
    o("o", "\uE067", 6),
    p("p", "\uE068", 6),
    q("q", "\uE069", 6),
    r("r", "\uE06A", 6),
    s("s", "\uE06B", 6),
    t("t", "\uE06C", 4),
    u("u", "\uE06D", 6),
    v("v", "\uE06E", 6),
    w("w", "\uE06F", 6),
    x("x", "\uE070", 6),
    y("y", "\uE071", 6),
    z("z", "\uE072", 6),
    ae("ä", "\uE073", 6),
    oe("ö", "\uE074", 6),
    ue("ü", "\uE075", 6),
    zero("0", "\uE0AA", 6),
    one("1", "\uE092", 6),
    two("2", "\uE093", 6),
    three("3", "\uE094", 6),
    four("4", "\uE095", 6),
    five("5", "\uE096", 6),
    six("6", "\uE097", 6),
    seven("7", "\uE098", 6),
    eight("8", "\uE099", 6),
    nine("9", "\uE09A", 6),
    and("&", "\uE09B", 6),
    bracket("(", "\uE09C", 4),
    bracket_reversed(")", "\uE09D", 4),
    colon(":", "\uE09E", 2),
    dollar("$", "\uE09F", 6),
    exclamation_mark("!", "\uE0A0", 2),
    hyphen("-", "\uE0A1", 6),
    percent("%", "\uE0A2", 6),
    plus("+", "\uE0A3", 6),
    question_mark("?", "\uE0A4", 6),
    quotation_mark("\"", "\uE0A5", 4),
    slash("/", "\uE0A6", 6),
    dot(".", "\uE111", 2),
    space(" ", " ", 4);

    private final String character;
    private final String unicodeCharacter;
    private final Integer width;

    GameBossBarTextChars(String character, String unicodeCharacter, Integer width) {
        this.character = character;
        this.unicodeCharacter = unicodeCharacter;
        this.width = width;
    }

    public String character() {
        return character;
    }

    @Override
    public Integer width() {
        return width;
    }

    @Override
    public String unicodeCharacter() {
        return unicodeCharacter;
    }

    public static GameBossBarTextChars[] textToChar(CharSequence text) {

        final var charList = new LinkedList<GameBossBarTextChars>();

        for (var i = 0; i < text.length(); i++) {
            for (final var textChar : values()) {

                if (String.valueOf(text.charAt(i)).equals(textChar.character())) {
                    charList.add(textChar);
                }
            }
        }

        return charList.toArray(GameBossBarTextChars[]::new);

    }
}
