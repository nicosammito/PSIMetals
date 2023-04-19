package com.uroria.gameapi.state;

import com.uroria.gameapi.GameAPI;

import java.util.Arrays;
import java.util.stream.IntStream;

public class GameStateManagerImpl implements GameStateManager{

    private final GameState[] gameStates;
    private int currentStateId;
    private GameAPI gameAPI;

    public GameStateManagerImpl(GameState... gameStates) {
        this.gameStates = gameStates;
    }

    @Override
    public GameState getCurrentState() {
        if (currentStateId < 0 || currentStateId >= gameStates.length) {
            return null;
        }

        return gameStates[currentStateId];
    }

    @Override
    public GameState nextState() {
        if (currentStateId + 1 == gameStates.length) {
            throw new IllegalStateException("No states remaining");
        }
        var oldState = getCurrentState();
        currentStateId++;
        var newState = getCurrentState();

        oldState.end();
        oldState.unregisterAllListener();
        newState.start(gameAPI);

        return newState;
    }

    @Override
    public GameState forceState(Class<? extends GameState> gameState) {
        final var newStateIndex = IntStream.range(0, gameStates.length).filter(i -> gameState.isInstance(gameStates[i])).findFirst().orElse(-1);

        if (newStateIndex == -1 || newStateIndex > gameStates.length - 1) {
            throw new IllegalStateException("State does not exist or is not in range");
        }

        final var oldState = this.getCurrentState();
        this.currentStateId = newStateIndex;
        final var newState = getCurrentState();

        oldState.end();
        oldState.unregisterAllListener();
        newState.start(gameAPI);

        return newState;
    }

    public void setGameAPI(GameAPI gameAPI) {
        this.gameAPI = gameAPI;
    }

    @Override
    @SafeVarargs
    public final boolean isCurrentGameState(Class<? extends GameState>... states) {
        return Arrays.stream(states).anyMatch(state -> state.isInstance(getCurrentState()));
    }
}
