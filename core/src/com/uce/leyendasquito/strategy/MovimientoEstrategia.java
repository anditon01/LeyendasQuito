package com.uce.leyendasquito.strategy;

import com.badlogic.gdx.physics.box2d.Body;

public interface MovimientoEstrategia {
	void mover(Body personaje, float delta);

}
