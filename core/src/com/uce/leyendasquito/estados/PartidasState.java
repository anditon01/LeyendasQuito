package com.uce.leyendasquito.estados;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.uce.leyendasquito.ventanas.MyGame;

public class PartidasState implements GameState {

	private MyGame game;
	private Stage stg;
	private Skin skin;
	private Music titleTheme;

	public PartidasState(MyGame game) {
		this.game = game;
		stg = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stg);

		skin = new Skin(Gdx.files.internal("skin/pixthulhu/pixthulhu-ui.json"));
	}

	@Override
	public void iniciar() {
		stg.clear();
		Gdx.input.setInputProcessor(stg);
		Table table = new Table();
		// table.setFillParent(true);
		table.setDebug(true);
		// table.setSize(00, 200);
		table.align(Align.bottomLeft);

		stg.addActor(table);

		TextButton newGame = new TextButton("Nueva Partida", skin);
		TextButton load = new TextButton("Cargar Partida", skin);
		TextButton back = new TextButton("Regresar", skin);

		table.add(newGame).fill();
		table.row().pad(10, 0, 10, 0);
		table.add(load);
		table.row();
		table.add(back).fillX().uniformX();

		newGame.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				StateManager.getInstance().changeState(new PlazaState(game));
				// parent.changeScreen(Box2DTutorial.APPLICATION);
			}
		});

		load.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				StateManager.getInstance().changeState(new CargarState(game));
			}
		});

		back.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				StateManager.getInstance().revertToPreviousState();
			}
		});
	}

	@Override
	public void update(float delta) {
		stg.act(delta);
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		stg.draw();
	}

	@Override
	public void resize(int width, int height) {
		stg.getViewport().update(width, height, true);
	}

	@Override
	public void salir() {
		// TODO Auto-generated method stub

	}

	@Override
	public String getLevelName() {
		// TODO Auto-generated method stub
		return "Partidas";
	}

	@Override
	public Vector2 getPlayerPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPlayerPosition(float x, float y) {
	}

}
