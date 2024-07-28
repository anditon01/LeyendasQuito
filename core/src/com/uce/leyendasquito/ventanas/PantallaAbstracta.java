package com.uce.leyendasquito.ventanas;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public abstract class PantallaAbstracta extends Stage implements Screen {
	
	protected  PantallaAbstracta() {
		super(new StretchViewport(640.0f,480.0f, new OrthographicCamera()) );
	}
	
	public abstract void buildStage();

	@Override
	public void show() {
		Gdx.input.setInputProcessor(this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		super.act(delta);
		super.draw();
	}

	@Override
	public void resize(int width, int height) {
		getViewport().update(width, height,true);
	}

	@Override
	public void pause() {
		
	}

	@Override
	public void resume() {
		
	}

	@Override
	public void hide() {
		
	}
	
	
	
}
