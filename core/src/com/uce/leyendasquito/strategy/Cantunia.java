package com.uce.leyendasquito.strategy;

import com.badlogic.gdx.physics.box2d.Body;

public class Cantunia implements Personaje{
	private MovimientoEstrategia estrategiaMovimiento;
    private Body body;

    public Cantunia(Body body, MovimientoEstrategia estrategiaMovimiento) {
        this.body = body;
        this.estrategiaMovimiento = estrategiaMovimiento;
    }

	@Override
	public void mover(float deltaTime) {
		estrategiaMovimiento.mover(body, deltaTime);
	}

	@Override
	public Body getBody() {
        return body;

	}
}
