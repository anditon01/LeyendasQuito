package com.uce.leyendasquito.strategy;

import com.badlogic.gdx.physics.box2d.Body;

public interface Personaje {
	void mover(float deltaTime);

	Body getBody();
}
