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
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.uce.leyendasquito.utils.AudioManager;
import com.uce.leyendasquito.ventanas.MyGame;

public class MenuState implements GameState {
	
	private MyGame game;
	private Stage stg;
	private Skin skin;
	private Music titleTheme;
	
	public MenuState(MyGame game) {
		this.game = game;
		stg = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stg);
		// parent.assetManager.queueAddMusic();
		skin = new Skin(Gdx.files.internal("skin/pixthulhu/pixthulhu-ui.json"));
		// game.assetManager.queueAddSkin();
		// game.assetManager.manager.finishLoading();

		 titleTheme = AudioManager.getInstance().playMusic("music/Menu.mp3", true);
	
		// titleTheme = parent.assetManager.manager.get("music/title_theme.mp3");
		// titleTheme.setVolume(parent.getPreferences().getMusicVolume());
		// titleTheme.play();
		// skin = parent.assetManager.manager.get("skin/pixthulhu/pixthulhu-ui.json");
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

		TextButton start = new TextButton("Iniciar", skin);
		TextButton preferences = new TextButton("Configuracion", skin);
		TextButton exit = new TextButton("Salir", skin);

		table.add(start).fill();
		table.row().pad(10, 0, 10, 0);
		table.add(preferences);
		table.row();
		table.add(exit).fillX().uniformX();

		start.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				StateManager.getInstance().changeState(new PartidasState(game));
				// parent.changeScreen(Box2DTutorial.APPLICATION);
			}
		});

		preferences.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				StateManager.getInstance().changeState(new OpcionesState(game));
			}
		});
		
		exit.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				Gdx.app.exit();
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
	public void salir() {
		
	}

	@Override
	public void resize(int width, int height) {
		stg.getViewport().update(width, height, true);

	}

	@Override
	public String getLevelName() {
		// TODO Auto-generated method stub
		return "Menu";
	}

	@Override
	public Vector2 getPlayerPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPlayerPosition(float x, float y) {
		// TODO Auto-generated method stub
		
	}

}
