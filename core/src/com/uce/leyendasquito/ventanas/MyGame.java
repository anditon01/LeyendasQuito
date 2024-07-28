package com.uce.leyendasquito.ventanas;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uce.leyendasquito.estados.*;

public class MyGame extends Game {

	 @Override
	    public void create() {
	        StateManager stateManager = StateManager.getInstance();


	        // Inicialización del estado inicial
	        stateManager.changeState(new AlmeidaState(this));
	    }

	    @Override
	    public void render() {
	        //super.render();
	    	float delta = Gdx.graphics.getDeltaTime();
	    	StateManager.getInstance().update(delta);
	        StateManager.getInstance().render();
	    }

	    @Override
	    public void resize(int width, int height) {
	        //super.resize(width, height);
	        StateManager.getInstance().resize(width, height);
	    }

	    @Override
	    public void dispose() {
	        super.dispose();
	        // Limpieza de recursos si es necesario
	    }

	    @Override
	    public void pause() {
	       // super.pause();
	        // Manejo de pausa si es necesario
	    }

	    @Override
	    public void resume() {
	        //super.resume();
	        // Manejo de reanudación si es necesario
	    }
}
