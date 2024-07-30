package com.uce.leyendasquito.estados;

import com.badlogic.gdx.math.Vector2;

public interface GameState {
	void iniciar();

	void update(float delta);

	void render();

	void resize(int width, int height);

	void salir();

	String getLevelName();

	void setPlayerPosition(float x, float y);

	Vector2 getPlayerPosition();

}
