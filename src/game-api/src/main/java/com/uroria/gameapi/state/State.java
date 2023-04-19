package com.uroria.gameapi.state;

import com.uroria.gameapi.GameAPI;

public interface State {

    default void start(GameAPI gameAPI) {

    };

    default void end() {

    };

}
