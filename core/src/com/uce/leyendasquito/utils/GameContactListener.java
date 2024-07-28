package com.uce.leyendasquito.utils;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.uce.leyendasquito.ventanas.MyGame;

public class GameContactListener implements ContactListener {
	private boolean playerInSensor;
	private String message;
	private String currentSensor;
	
	@Override
	public void beginContact(Contact contact) {
		// Este método se llama cuando comienza un contacto
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		// Aquí puedes manejar lo que sucede cuando dos objetos comienzan a colisionar
		if (fixtureA.isSensor() || fixtureB.isSensor()) {
			playerInSensor = true;
			if (fixtureA.isSensor()) {
				currentSensor = (String) fixtureA.getUserData();
			} else {
				currentSensor = (String) fixtureB.getUserData();
			}
			message = "Inside sensor! Press E to interact with " + currentSensor + ".";
			System.out.println(message);
		}
	}

	@Override
	public void endContact(Contact contact) {
		// Este método se llama cuando termina un contacto
		Fixture fixtureA = contact.getFixtureA();
		Fixture fixtureB = contact.getFixtureB();

		// Aquí puedes manejar lo que sucede cuando dos objetos dejan de colisionar
		if (fixtureA.isSensor() || fixtureB.isSensor()) {
			playerInSensor = false;
			message = null;
			System.out.println("Left sensor");
		}
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// Este método se llama antes de resolver el contacto, puedes modificar la
		// colisión aquí
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// Este método se llama después de resolver el contacto, puedes obtener
		// información sobre el impulso
	}

	public boolean isPlayerInSensor() {
		return playerInSensor;
	}

	public String getMessage() {
		return message;
	}

	public String getCurrentSensor() {
		return currentSensor;
	}
}
