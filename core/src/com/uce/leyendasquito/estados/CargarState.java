package com.uce.leyendasquito.estados;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.uce.leyendasquito.bd.DatabaseManager;
import com.uce.leyendasquito.ventanas.MyGame;

public class CargarState implements GameState{

	private MyGame game;
    private Stage stg;
    private Table table;
    private Skin skin;
    private Label gameInfoLabel;

    public CargarState(MyGame game) {
        this.game = game;
        stg = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stg);

        skin = new Skin(Gdx.files.internal("skin/pixthulhu/pixthulhu-ui.json"));
        table = new Table();
        table.setFillParent(true);
        stg.addActor(table);

        setupUI();
    }

    private void setupUI() {
        TextButton backButton = new TextButton("Volver", skin);
        TextButton loadGameButton = new TextButton("Cargar Partida", skin);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            	StateManager.getInstance().revertToPreviousState(); // Volver al menú principal
            }
        });

        loadGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                GameState loadedState = DatabaseManager.getInstance().loadGameState(game);
                if (loadedState != null) {
                    StateManager.getInstance().changeState(loadedState);
                }
            }
        });

        gameInfoLabel = new Label(getSavedGameInfo(), skin);

        table.add(gameInfoLabel).colspan(2).center();
        table.row().pad(10, 0, 10, 0);
        table.add(loadGameButton).fillX().uniformX();
        table.row().pad(10, 0, 10, 0);
        table.add(backButton).fillX().uniformX();
    }

    private String getSavedGameInfo() {
        // Consulta la base de datos para obtener información sobre la partida guardada
        return DatabaseManager.getInstance().getSavedGameInfo();
    }

    @Override
    public void iniciar() {
        Gdx.input.setInputProcessor(stg);
    }

    @Override
    public void update(float delta) {
        stg.act(delta);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stg.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stg.draw();
    }

    @Override
    public void resize(int width, int height) {
        stg.getViewport().update(width, height, true);
    }

    @Override
    public void salir() {
        // Código de limpieza, si es necesario
    }

    @Override
    public String getLevelName() {
        return "LoadGame";
    }

    @Override
    public Vector2 getPlayerPosition() {
        return new Vector2(); // No hay posición de jugador en la pantalla de carga de partida
    }

	@Override
	public void setPlayerPosition(float x, float y) {
		// TODO Auto-generated method stub
		
	}

}
