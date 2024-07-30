package com.uce.leyendasquito.estados;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.uce.leyendasquito.strategy.Cantunia;
import com.uce.leyendasquito.strategy.Movimiento2D;
import com.uce.leyendasquito.strategy.MovimientoEstrategia;
import com.uce.leyendasquito.strategy.MovimientoUnidireccional;
import com.uce.leyendasquito.strategy.Personaje;
import com.uce.leyendasquito.utils.AudioManager;
import com.uce.leyendasquito.utils.BodyFactory;
import com.uce.leyendasquito.utils.GameContactListener;
import com.uce.leyendasquito.utils.HUD;
import com.uce.leyendasquito.utils.KeyboardController;
import com.uce.leyendasquito.ventanas.MyGame;

public class CantuniaState implements GameState {

	private World world;
	private MyGame game;
	private Stage stg;
	private Body body;
	private OrthographicCamera camera;
	private KeyboardController controller;
	public Music plazaTheme;
	private boolean paused;
	private Personaje personaje;
	private MovimientoEstrategia movimientoEstrategia;
	private Box2DDebugRenderer debugRenderer;
	private GameContactListener contactListener;
	//private boolean isPlayerInSensor = false;
	private Label messageLabel;
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer mapRenderer;
	private float PPM = 100;
	private float mapWidth, mapHeight;
	//private HUD hud;
    //private SpriteBatch batch;

	public CantuniaState(MyGame game) {
		world = new World(new Vector2(0, -50f), true);
		controller = new KeyboardController();
		contactListener = new GameContactListener();
		stg = new Stage(new ScreenViewport());
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
		this.game = game;

		Gdx.input.setInputProcessor(controller);
		world.setContactListener(contactListener);
		plazaTheme = AudioManager.getInstance().playMusic("music/title_theme.mp3", true);
		cargarTileMap();
		crearCuerposDesdeTileMap();

		BodyFactory bodyFactory = BodyFactory.getInstance(world);
		body = bodyFactory.makeBoxPolyBody(1, 1, 32 / PPM, 64 / PPM, BodyFactory.STEEL, BodyType.DynamicBody, true);
		movimientoEstrategia = new MovimientoUnidireccional(controller, 3);
		personaje = new Cantunia(body, movimientoEstrategia);

		body.setUserData("player");

		Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu/pixthulhu-ui.json"));
		messageLabel = new Label("", skin);
		messageLabel.setPosition(10, 20);
		stg.addActor(messageLabel);
		
	}

	private void cargarTileMap() {
		tiledMap = new TmxMapLoader().load("tilesmap/cantunia.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / PPM);
		MapProperties prop = tiledMap.getProperties();
		mapWidth = prop.get("width", Integer.class) * prop.get("tilewidth", Integer.class) / PPM;
		mapHeight = prop.get("height", Integer.class) * prop.get("tileheight", Integer.class) / PPM;
	}

	private void crearCuerposDesdeTileMap() {
		MapLayer collisionLayer = tiledMap.getLayers().get("colision"); // Nombre de la capa de colisión

		if (collisionLayer == null) {
			throw new IllegalArgumentException("La capa de colisión no existe en el tilemap");
		}

		for (MapObject object : collisionLayer.getObjects()) {
			if (object instanceof RectangleMapObject) {
				RectangleMapObject rectObject = (RectangleMapObject) object;
				Rectangle rect = rectObject.getRectangle();
				BodyFactory.getInstance(world).makeBoxCollision(rect, PPM);
			}
		}
	}

	@Override
	public void iniciar() {
		Gdx.input.setInputProcessor(controller);
	}

	private void actualizarCamara() {
		Vector2 position = body.getPosition();
		float cameraX = Math.max(camera.viewportWidth / 2, Math.min(position.x, mapWidth - camera.viewportWidth / 2));
		float cameraY = Math.max(camera.viewportHeight / 2,
				Math.min(position.y, mapHeight - camera.viewportHeight / 2));
		camera.position.set(cameraX, cameraY, 0);
		camera.update();
	}

	@Override
	public void update(float delta) {
		personaje.mover(delta);
		world.step(delta, 3, 3);
		actualizarCamara();
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		// camera.position.set(personaje.getBody().getPosition().x,
		// Gdx.graphics.getHeight() / PPM, 0);
		camera.update();

		mapRenderer.setView(camera);
		mapRenderer.render();

		debugRenderer.render(world, camera.combined);
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			paused = !paused;
			togglePause();
			paused = !paused;
		}
	}

	@Override
	public void setPlayerPosition(float x, float y) {
		body.setTransform(x, y, body.getAngle());
	}

	@Override
	public void salir() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resize(int width, int height) {
		camera.setToOrtho(false, width / PPM, height / PPM);
		stg.getViewport().update(width, height, true);
	}

	private void togglePause() {
		if (paused) {
			StateManager.getInstance().changeState(new PauseState(game));
			// plazaTheme.pause();
		} else {
			plazaTheme.play();
		}
	}

	@Override
	public String getLevelName() {
		// TODO Auto-generated method stub
		return "Cantunia";
	}

	@Override
	public Vector2 getPlayerPosition() {
		// TODO Auto-generated method stub
		return body.getPosition();
	}
}
