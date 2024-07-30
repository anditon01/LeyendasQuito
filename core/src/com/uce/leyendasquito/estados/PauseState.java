package com.uce.leyendasquito.estados;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.uce.leyendasquito.bd.DatabaseManager;
import com.uce.leyendasquito.ventanas.MyGame;

public class PauseState implements GameState {
	private static PauseState instance;
	private MyGame game;
	private Stage stg;
	private Table table;
	private Skin skin;

	public PauseState(MyGame game) {
		this.game = game;
		stg = new Stage(new ScreenViewport());

		skin = new Skin(Gdx.files.internal("skin/pixthulhu/pixthulhu-ui.json"));
		table = new Table();
		table.setFillParent(true);
		stg.addActor(table);

		setupUI();
	}

	private void setupUI() {
		TextButton resumeButton = new TextButton("Continuar", skin);
		TextButton optionsButton = new TextButton("Opciones", skin);
		TextButton saveButton = new TextButton("Guardar", skin);
		TextButton quitButton = new TextButton("Salir", skin);

		resumeButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				StateManager.getInstance().revertToPreviousState();
			}
		});

		saveButton.addCaptureListener(new ChangeListener() {

			@Override
			public void changed(ChangeEvent event, Actor actor) {
				GameState currentState = StateManager.getInstance().getPreviousState();
				if (currentState != null && !(currentState instanceof PauseState)) {
		            String level = currentState.getLevelName();
		            Vector2 playerPosition = currentState.getPlayerPosition();
		            DatabaseManager.getInstance().saveGameState(level, playerPosition.x, playerPosition.y);
		        }
				
			}
		});

		optionsButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				StateManager.getInstance().changeState(new OpcionesState(game));
			}
		});

		quitButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				StateManager.getInstance().changeState(new MenuState(game));
			}
		});

		table.add(resumeButton).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(saveButton).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(optionsButton).fillX().uniformX();
		table.row().pad(10, 0, 10, 0);
		table.add(quitButton).fillX().uniformX();
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

	public Stage getStage() {
		return stg;
	}

	@Override
	public String getLevelName() {
		// TODO Auto-generated method stub
		return "Pause";
	}

	@Override
	public Vector2 getPlayerPosition() {
		// TODO Auto-generated method stub
		return new Vector2();
	}

	@Override
	public void setPlayerPosition(float x, float y) {
		// TODO Auto-generated method stub

	}
}
