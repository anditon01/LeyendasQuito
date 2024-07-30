package com.uce.leyendasquito.estados;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class StateManager {
	private static StateManager instance;
	private Stack<GameState> stateStack;
	private GameState previousState;
	
	private StateManager() {
		stateStack = new Stack<>();
	}

	public static StateManager getInstance() {
		if (instance == null) {
			instance = new StateManager();
		}
		return instance;
	}

	public void changeState(GameState newState) {
		if (!stateStack.isEmpty()) {
			previousState = stateStack.peek();
			stateStack.peek().salir();
		}
		stateStack.push(newState);
		newState.iniciar();
	}

	public void revertToPreviousState() {
		if (!stateStack.isEmpty()) {
			stateStack.pop().salir();
		}
		if (!stateStack.isEmpty()) {
			stateStack.peek().iniciar();
		}
	}

	public void update(float delta) {
		if (!stateStack.isEmpty()) {
			stateStack.peek().update(delta);
		}
	}

	public void render() {
		if (!stateStack.isEmpty()) {
			stateStack.peek().render();
		}
	}

	public void resize(int width, int height) {
		if (!stateStack.isEmpty()) {
			stateStack.peek().resize(width, height);
		}
	}

	public GameState getCurrentState() {
		if (!stateStack.isEmpty()) {
			return stateStack.peek();
		}
		return null;
	}

	public GameState getPreviousState() {
		return previousState;
	}
	
	
}
