package com.uce.leyendasquito.strategy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.uce.leyendasquito.utils.KeyboardController;

public class Movimiento2D implements MovimientoEstrategia {

	private KeyboardController controller;
	private float movSpeed;
	private float jumpForce;
	private boolean canJump;

	public Movimiento2D(KeyboardController controller, float movSpeed, float jumpForce) {
		super();
		this.controller = controller;
		this.movSpeed = movSpeed;
		this.jumpForce = jumpForce;
		this.canJump = true;
	}

	@Override
	public void mover(Body personaje, float delta) {
		 float movx = 0;
		 if (controller.left) {
	            movx = -movSpeed;
	        } else if (controller.right) {
	            movx = movSpeed;
	        }

	        // Salto
	        if (controller.up && canJump) {
	            personaje.applyLinearImpulse(new Vector2(0, jumpForce), personaje.getWorldCenter(), true);
	            canJump = false; // Solo permitir un salto hasta que toque el suelo nuevamente
	        }

	        // Limitar la velocidad vertical
	        Vector2 vel = personaje.getLinearVelocity();
	        if (vel.y > 20) { // Ajusta el límite según sea necesario
	            vel.y = 40;
	        } else if (vel.y < -20) { // Ajusta el límite según sea necesario
	            vel.y = -10;
	        }
	        personaje.setLinearVelocity(movx, vel.y);
	        // Actualizar la lógica de salto basado en el contacto con el suelo
	        if (personaje.getLinearVelocity().y == 0) {
	            canJump = true;
	        }
	}

}
