package com.uce.leyendasquito.estados;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.uce.leyendasquito.ventanas.MyGame;

public class CantuniaState implements GameState{

	MyGame game;
	private Stage stg;
	private TiledMap tiledMap;
	private OrthogonalTiledMapRenderer mapRenderer;
	
	public CantuniaState(MyGame game) {
		this.game = game;
		stg = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stg);
        tiledMap = new TmxMapLoader().load("tiles/cantunia.tmx");
		mapRenderer = new OrthogonalTiledMapRenderer(tiledMap, 1 / 32f);
        // Agrega elementos a la stage, por ejemplo un Label
        Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu/pixthulhu-ui.json"));
        Label label = new Label("Menu Screen", skin);
        label.setPosition(100, 150);
        stg.addActor(label);
	}

	@Override
	public void iniciar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(float delta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render() {
		 Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void salir() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		stg.getViewport().update(width, height, true);
	}

}
