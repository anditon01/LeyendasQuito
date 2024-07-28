package com.uce.leyendasquito.estados;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.uce.leyendasquito.strategy.Cantunia;
import com.uce.leyendasquito.strategy.MovimientoEstrategia;
import com.uce.leyendasquito.strategy.MovimientoUnidireccional;
import com.uce.leyendasquito.strategy.Personaje;
import com.uce.leyendasquito.utils.BodyFactory;
import com.uce.leyendasquito.utils.GameContactListener;
import com.uce.leyendasquito.utils.KeyboardController;
import com.uce.leyendasquito.ventanas.MyGame;
import com.uce.leyendasquito.utils.AudioManager;

public class PlazaState implements GameState {
	private World world;
	private MyGame game;
	private Stage stg;
	private Body body;
	private Body entrada;
	private Body entrada1;
	private OrthographicCamera camera;
	private KeyboardController controller;
	private Music plazaTheme;
	private boolean paused;
	private Personaje personaje;
	private MovimientoEstrategia movimientoEstrategia;
	private Box2DDebugRenderer debugRenderer;
	private GameContactListener contactListener;
	private boolean isPlayerInSensor = false;
	private Label messageLabel;

	public PlazaState(MyGame game) {
		world = new World(new Vector2(0, -9.8f), true);		
		contactListener = new GameContactListener();
		world.setContactListener(contactListener);
		
		this.game = game;		
		controller = new KeyboardController();
		stg = new Stage(new ScreenViewport());
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera(48, 48);
		
		Gdx.input.setInputProcessor(controller);
		//Gdx.input.setInputProcessor(stg);
		plazaTheme = AudioManager.getInstance().playMusic("music/Plaza.mp3", true);
		
		BodyFactory bodyFactory = BodyFactory.getInstance(world);
		body = bodyFactory.makeBoxPolyBody(1, 1, 2, 2, BodyFactory.RUBBER, BodyType.DynamicBody, true);
		bodyFactory.makeBoxPolyBody(0, -10, 50, 10, BodyFactory.RUBBER, BodyType.StaticBody, false);
		entrada = bodyFactory.makeBoxPolyBody(10, 10, 4, 2, BodyFactory.RUBBER, BodyType.KinematicBody, false);
		entrada1 = bodyFactory.makeBoxPolyBody(5, 10, 4, 2, BodyFactory.RUBBER, BodyType.KinematicBody, false);
		bodyFactory.makeConeSensor(entrada, 5, "sensorCantunia");
		bodyFactory.makeConeSensor(entrada1, 5, "sensorAlmeida");
		
		movimientoEstrategia = new MovimientoUnidireccional(controller, 15);
		personaje = new Cantunia(body, movimientoEstrategia);

		body.setUserData("player");

		Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu/pixthulhu-ui.json"));
		messageLabel = new Label("", skin);
		messageLabel.setPosition(10, 20);
		stg.addActor(messageLabel);
	}

	@Override
	public void iniciar() {
		Gdx.input.setInputProcessor(controller);
	}

	@Override
	public void update(float delta) {
		//movimientoEstrategia.mover(body, delta);
		personaje.mover(delta);
		world.step(1 / 60f, 6, 2);
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		debugRenderer.render(world, camera.combined);
		
		if (contactListener.isPlayerInSensor()) {
			messageLabel.setText(contactListener.getMessage());
			if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
				switch (contactListener.getCurrentSensor()) {
				case "sensorCantunia":
					StateManager.getInstance().changeState(new CantuniaState(game));
					break;
				case "sensorAlmeida":
					StateManager.getInstance().changeState(new AlmeidaState(game));
					break;
				}
			}
		} else {
			messageLabel.setText("");
		}
		stg.draw();

		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			paused = !paused;
			togglePause();
			paused = !paused;
		}
	}

	@Override
	public void resize(int width, int height) {
		stg.getViewport().update(width, height, true);
	}

	@Override
	public void salir() {
		plazaTheme.stop();
        plazaTheme.dispose();  
		// Limpieza de recursos si es necesario
	}

	private void togglePause() {
		if (paused) {
			StateManager.getInstance().changeState(new PauseState(game));
		} else {
			plazaTheme.play();
		}
	}

	public boolean isPlayerInSensor() {
		return isPlayerInSensor;
	}

	public void setPlayerInSensor(boolean isPlayerInSensor) {
		this.isPlayerInSensor = isPlayerInSensor;
	}
}
