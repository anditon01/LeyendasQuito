package com.uce.leyendasquito;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.ScreenUtils;

public class Principal extends Game {
	SpriteBatch batch;
	Texture img;
	Label label;
	// CREATE se ejecuta cuando se abre la ventana de juego por primera
	// vez o la escena de nuestro proyecto. Este método debe contener el código que
	// creará la
	// escena.
	@Override
	public void create() {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		label.setPosition(5, 5, 0);
	}

	// render: Es un método que se ejecuta para renderizar la escena. Se llama
	// dentro del
	// ciclo de vida del videojuego, por tanto, se llamará aproximadamente 60 veces
	// por segundo,
	// depende del dispositivo y del estado de la escena
	@Override
	public void render() {
		ScreenUtils.clear(0, 0, 0, 1);
		
		batch.begin();
		label.draw(batch, 0);
		batch.draw(img, 0, 4);
		batch.end();
	}

	// dispose: Es el método que se llamará al cerrar la escena, aquí debemos eliminar todos
	// los objetos creados en la escena para liberar toda la memoria, utilizandodispose(). En el caso
	// de un objeto Textura, si no se hiciera el dispose(), el objeto textura
	// desaparecerá porque lo	eliminará el recolector de basura de java, pero la textura que está cargada en GPU no la 
	//	eliminará. Por tanto, hay que eliminar todos los objetos creados en la escena dentro de este 
	//	método, si no se acumularán texturas en la GPU que no se podrán usar y estarán ocupando	espacio
	@Override
	public void dispose() {
		batch.dispose();
		img.dispose();
	}
}
