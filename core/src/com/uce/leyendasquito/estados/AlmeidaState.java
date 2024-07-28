package com.uce.leyendasquito.estados;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
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
import com.uce.leyendasquito.utils.KeyboardController;
import com.uce.leyendasquito.ventanas.MyGame;

public class AlmeidaState implements GameState {
	private World world;
	private MyGame game;
	private Stage stg;
	private Body body;
	private Body entrada;
	private Body entrada1;
	private Object sensor1;
	private OrthographicCamera camera;
	private KeyboardController controller;
	public Music plazaTheme;
	private boolean paused;
	private Personaje personaje;
	private MovimientoEstrategia movimientoEstrategia;
	private Box2DDebugRenderer debugRenderer;
	private GameContactListener contactListener;
	private boolean isPlayerInSensor = false;
	private Label messageLabel;
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer mapRenderer;
	  private float PPM = 100;
	  
	public AlmeidaState(MyGame game) {

		world = new World(new Vector2(0, -20f), true);
		controller = new KeyboardController();
		contactListener = new GameContactListener();
		stg = new Stage(new ScreenViewport());
		debugRenderer = new Box2DDebugRenderer();
		camera = new OrthographicCamera();
		camera.setToOrtho(false, 32 , 32 ); 
		this.game = game;

		Gdx.input.setInputProcessor(controller);
		world.setContactListener(contactListener);
		plazaTheme = AudioManager.getInstance().playMusic("music/Dr._Wily_Castle.mp3", true);

		tiledMap = new TmxMapLoader().load("tilesmap/iglesiatmx.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / PPM);
		crearCuerposDesdeTileMap();
		
		BodyFactory bodyFactory = BodyFactory.getInstance(world);
		body = bodyFactory.makeBoxPolyBody(5, 5, 2, 2, BodyFactory.STEEL, BodyType.DynamicBody, true);
		//bodyFactory.makeBoxPolyBody(0, -10, 50, 10, BodyFactory.STEEL, BodyType.StaticBody, false);
		//entrada = bodyFactory.makeBoxPolyBody(10, 0, 4, 2, BodyFactory.STEEL, BodyType.StaticBody, false);
		//entrada1 = bodyFactory.makeBoxPolyBody(4, 3, 4, 2, BodyFactory.STEEL, BodyType.StaticBody, false);
		// bodyFactory.makeConeSensor(entrada, 5, "sensorCantunia");
		// bodyFactory.makeConeSensor(entrada1, 5,"sensorAlmeida");
		movimientoEstrategia = new Movimiento2D(controller, 5, 50);
		personaje = new Cantunia(body, movimientoEstrategia);

		body.setUserData("player");

		Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu/pixthulhu-ui.json"));
		messageLabel = new Label("", skin);
		messageLabel.setPosition(10, 20);
		stg.addActor(messageLabel);
	}

	private void crearCuerposDesdeTileMap() {
        MapLayer collisionLayer = tiledMap.getLayers().get("colisiones"); // Nombre de la capa de colisión

        if (collisionLayer == null) {
            throw new IllegalArgumentException("La capa de colisión no existe en el tilemap");
        }

        for (MapObject object : collisionLayer.getObjects()) {
            if (object instanceof RectangleMapObject) {
                RectangleMapObject rectObject = (RectangleMapObject) object;
                Rectangle rect = rectObject.getRectangle();
                crearCuerpoDesdeRectangulo(rect);
            }
        }
    }

    private void crearCuerpoDesdeRectangulo(Rectangle rect) {
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

	
	public void iniciar() {
		Gdx.input.setInputProcessor(controller);
	}

	@Override
	public void update(float delta) {
		// movimientoEstrategia.mover(body, delta);
		personaje.mover(delta);
		world.step(delta, 3, 3);
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		camera.update();
		debugRenderer.render(world, camera.combined);
		mapRenderer.setView(camera);
		mapRenderer.render();
		camera.position.set(personaje.getBody().getPosition().x, personaje.getBody().getPosition().y, 0);
		camera.update();
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
			paused = !paused;
			togglePause();
			paused = !paused;
		}
	}

	@Override
	public void salir() {
		// plazaTheme.stop();
		// plazaTheme.dispose();
	}

	@Override
	public void resize(int width, int height) {
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
}
