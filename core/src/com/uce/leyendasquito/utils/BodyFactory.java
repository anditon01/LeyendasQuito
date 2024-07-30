package com.uce.leyendasquito.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class BodyFactory {
	private static BodyFactory thisInstance;
	private World world;
	public static final int STEEL = 0;
	public static final int WOOD = 1;
	public static final int RUBBER = 2;
	public static final int STONE = 3;
	private static final float DEGTORAD = 0.0174533f;

	private BodyFactory(World world) {
		this.world = world;
	}

	public static BodyFactory getInstance(World world) {
		if (thisInstance == null) {
			thisInstance = new BodyFactory(world);
		} else {
			thisInstance.world = world;
		}
		return thisInstance;
	}

	static public FixtureDef makeFixture(int material, Shape shape) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		switch (material) {
		case 0:
			fixtureDef.density = 1f;
			fixtureDef.friction = 0f;
			fixtureDef.restitution = 0.0f;
			break;
		case 1:
			fixtureDef.density = 0.5f;
			fixtureDef.friction = 0.7f;
			fixtureDef.restitution = 0.3f;
			break;
		case 2:
			fixtureDef.density = 1f;
			fixtureDef.friction = 0.4f;
			fixtureDef.restitution = 0.6f;
			break;
		case 3:
			fixtureDef.density = 1f;
			fixtureDef.friction = 0.9f;
			fixtureDef.restitution = 0.01f;
			break;
		default:
			fixtureDef.density = 7f;
			fixtureDef.friction = 0.5f;
			fixtureDef.restitution = 0.3f;
		}
		return fixtureDef;
	}

	public Body makeCirclePolyBody(float posx, float posy, float radius, int material, BodyType bodyType,
			boolean fixedRotation) {
		// create a definition
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		boxBodyDef.position.x = posx;
		boxBodyDef.position.y = posy;
		boxBodyDef.fixedRotation = fixedRotation;

		// create the body to attach said definition
		Body boxBody = world.createBody(boxBodyDef);
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(radius / 2);
		boxBody.createFixture(makeFixture(material, circleShape));
		circleShape.dispose();
		return boxBody;
	}

	public Body makeBoxPolyBody(float posx, float posy, float width, float height, int material, BodyType bodyType,
			boolean fixedRotation) {
		// create a definition
		BodyDef boxBodyDef = new BodyDef();
		boxBodyDef.type = bodyType;
		
		boxBodyDef.position.x = posx;
		boxBodyDef.position.y = posy;
		boxBodyDef.fixedRotation = fixedRotation;

		// create the body to attach said definition
		Body boxBody = world.createBody(boxBodyDef);
		PolygonShape poly = new PolygonShape();
		
		poly.setAsBox(width / 2, height / 2);
		boxBody.createFixture(makeFixture(material, poly));
		poly.dispose();

		return boxBody;
	}
	
	public void makeBoxCollision(Rectangle rect, float PPM) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyDef.BodyType.StaticBody;

		float x = (rect.x + rect.width / 2) / PPM; // PPM: pixels per meter
		float y = (rect.y + rect.height / 2) / PPM;
		bodyDef.position.set(x, y);

		Body body = world.createBody(bodyDef);
		PolygonShape shape = new PolygonShape();
		
		shape.setAsBox(rect.width / 2 / PPM, rect.height / 2 / PPM);
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.density = 1.0f;
		fixtureDef.friction = 0.5f;
		fixtureDef.restitution = 0.3f;

		body.createFixture(fixtureDef);
		shape.dispose();
	}
	
	public void makeConeSensor(Body body, float size, String userData) {

		FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.isSensor = true; // Define que es un sensor

        PolygonShape polygon = new PolygonShape();

        float radius = size;
        Vector2[] vertices = new Vector2[5];
        vertices[0] = new Vector2(0, 0);
        for (int i = 2; i < 6; i++) {
            float angle = (float) ((((i -1)/ 3.0 * 90)+200) * MathUtils.degreesToRadians); // Convierte grados a radianes
            vertices[i - 1] = new Vector2(radius * MathUtils.cos(angle), radius * MathUtils.sin(angle));
        }
        polygon.set(vertices);
        fixtureDef.shape = polygon;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(userData); // Asigna el userData al fixture
        polygon.dispose();
	}

	public void makeAllFixturesSensors(Body bod) {
		for (Fixture fix : bod.getFixtureList()) {
			fix.setSensor(true);
		}
	}
}
