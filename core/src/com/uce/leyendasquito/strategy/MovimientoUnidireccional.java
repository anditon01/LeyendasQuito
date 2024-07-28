package com.uce.leyendasquito.strategy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.uce.leyendasquito.utils.KeyboardController;

public class MovimientoUnidireccional implements MovimientoEstrategia {
	private KeyboardController controller;
	private float movSpeed;

	public MovimientoUnidireccional(KeyboardController controller, float movSpeed) {
		this.controller = controller;
		this.movSpeed = movSpeed;
	}

	@Override
	public void mover(Body personaje, float delta) {
		float movx = 0;
		float movy = 0;
		if (controller.left) {
			System.out.println("L");
			movx = -1;
			// player.applyForceToCenter(-10, 0, true);
		} else if (controller.right) {
			movx = 1;
			// player.a<<pplyForceToCenter(10, 0, true);
		}
		if (controller.up) {
			movy = 1;
			// player.applyForceToCenter(0, 10, true);
		} else if (controller.down) {
			movy = -1;
			// player.applyForceToCenter(0, -10, true);
		}

		Vector2 movement = new Vector2(movx, movy);
		if (movement.len2() > 0) {
			movement.nor().scl(movSpeed);
			personaje.setLinearVelocity(movement);
		} else {
			personaje.setLinearVelocity(Vector2.Zero); // Detener el movimiento si no hay entrada
		}
	}

}
