package com.uce.leyendasquito.estados;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.uce.leyendasquito.utils.AppPreferences;
import com.uce.leyendasquito.utils.AudioManager;
import com.uce.leyendasquito.ventanas.MyGame;

public class OpcionesState implements GameState {
	private MyGame game;
	private Stage stg;
	private Label titleLabel;
	private Label volumeMusicLabel;
	private Label resolutionLabel;
	private Label musicOnOffLabel;
	private Label soundOnOffLabel;
	private SelectBox<String> resolutionSelectBox;
	private Skin skin;


	public OpcionesState(MyGame game) {
		this.game = game;
		stg = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(stg);

		// Agrega elementos a la stage, por ejemplo un Label
		skin = new Skin(Gdx.files.internal("skin/pixthulhu/pixthulhu-ui.json"));
	}

	@Override
	public void iniciar() {
		stg.clear();

		Gdx.input.setInputProcessor(stg);
		Table table = new Table();
		table.setFillParent(true);
		table.setDebug(true);
		stg.addActor(table);
		
		resolutionSelectBox = new SelectBox<>(skin);
		resolutionSelectBox.setItems("1280x720", "1920x1080", "800x600");

		String resolution = Gdx.graphics.getWidth() + "x" + Gdx.graphics.getHeight();
		resolutionSelectBox.setSelected(resolution);
		resolutionSelectBox.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				String selected = resolutionSelectBox.getSelected();
				String[] dimensions = selected.split("x");
				int width = Integer.parseInt(dimensions[0]);
				int height = Integer.parseInt(dimensions[1]);
				AppPreferences.getInstance().saveResolution(width, height);
				Gdx.graphics.setWindowedMode(width, height);		
			}
		});

		final Slider volumeMusicSlider = new Slider(0f, 2f, 0.1f, false, skin);
		 volumeMusicSlider.setValue(AppPreferences.getInstance().getMusicVolume());
		volumeMusicSlider.addListener(event -> {
			if (volumeMusicSlider.isDragging()) {
				float volume = volumeMusicSlider.getValue();
				//AppPreferences.getInstance().setMusicVolume(volume);
				AudioManager.getInstance().setMusicVolume(volume);
			}
			return true;
		});

		final Slider volumeSoundSlider = new Slider(0f, 1f, 0.1f, false, skin);
		 volumeSoundSlider.setValue(AppPreferences.getInstance().getSoundVolume());
		volumeSoundSlider.addListener(event -> {
			if (volumeSoundSlider.isDragging() || volumeSoundSlider.isTouchable()) {
				float volume = volumeSoundSlider.getValue();
				 AppPreferences.getInstance().setSoundVolume(volume);
			}
			return true;
		});

		final TextButton backButton = new TextButton("Regresar", skin, "default");
		backButton.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent event, Actor actor) {
				StateManager.getInstance().revertToPreviousState();
			}
		});
		
		titleLabel = new Label("Preferencias", skin);
		volumeMusicLabel = new Label("Volumen Musica", skin);
		resolutionLabel = new Label("Resolucion de Pantalla", skin);
		musicOnOffLabel = new Label("Volumen Sonidos", skin);

		table.add(titleLabel).colspan(2);
		table.row().pad(10, 0, 0, 10);
		table.add(volumeMusicLabel);
		table.add(volumeMusicSlider).fill();
		table.row().pad(10, 0, 0, 10);
		table.add(musicOnOffLabel);
		table.add(volumeSoundSlider).fill();
		table.row();
		table.add(resolutionLabel);
		table.add(resolutionSelectBox);
		table.row().pad(10, 0, 0, 10);
		table.row().pad(10, 0, 0, 10);
		table.add(backButton);
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
		return "Opciones";
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
