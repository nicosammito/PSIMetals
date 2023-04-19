package com.uroria.gameapi.store;

public interface FlagManager<T extends Flag> {

    void addFlags(final T... flag);

    void removeFlags(final T... flag);

    boolean hasFlags(final T... flag);

    boolean hasAllFlags(final T... flag);

}
