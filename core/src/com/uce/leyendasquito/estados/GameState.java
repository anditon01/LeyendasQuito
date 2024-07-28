package com.uce.leyendasquito.estados;

public interface GameState {
	void iniciar();

	void update(float delta);

	void render();

	void resize(int width, int height);

	void salir();

}
