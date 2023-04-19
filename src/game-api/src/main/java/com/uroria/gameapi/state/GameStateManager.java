package com.uroria.gameapi.state;

public interface GameStateManager {

    GameState getCurrentState();
    GameState nextState();
    GameState forceState(Class<? extends GameState> gameState);
    @SuppressWarnings("unchecked")
    boolean isCurrentGameState(Class<? extends GameState>... states);

}
