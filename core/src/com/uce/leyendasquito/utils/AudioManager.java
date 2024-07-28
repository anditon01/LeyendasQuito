package com.uce.leyendasquito.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class AudioManager {
	private static AudioManager instance;
	private Music currentMusic;
	private float musicVolume;
	private float soundVolume;

	private AudioManager() {
		// Inicializar volúmenes con valores desde las preferencias
		musicVolume = AppPreferences.getInstance().getMusicVolume();
		soundVolume = AppPreferences.getInstance().getSoundVolume();
	}

	public static AudioManager getInstance() {
		if (instance == null) {
			instance = new AudioManager();
		}
		return instance;
	}

	public Music playMusic(String filePath, boolean looping) {
		if (currentMusic != null) {
			currentMusic.stop();
			currentMusic.dispose();
		}
		currentMusic = Gdx.audio.newMusic(Gdx.files.internal(filePath));
		currentMusic.setLooping(looping);
		currentMusic.setVolume(musicVolume);
		currentMusic.play();
		return currentMusic;
	}

	public void playMusic(Music music, boolean looping) {
		if (currentMusic != null) {
			currentMusic.stop();
		}
		currentMusic = music;
		currentMusic.setLooping(looping);
		currentMusic.setVolume(musicVolume);
		currentMusic.play();
	}

	public void setMusicVolume(float volume) {
		musicVolume = volume;
        if (currentMusic != null) {
            currentMusic.setVolume(volume);
        }
        AppPreferences.getInstance().setMusicVolume(volume);
	}

	public void playSound(String filePath) {
		Sound sound = Gdx.audio.newSound(Gdx.files.internal(filePath));
		sound.play(soundVolume);
	}

	public void setSoundVolume(float volume) {
		soundVolume = volume;
		AppPreferences.getInstance().setSoundVolume(volume);
	}

	public float getMusicVolume() {
		return musicVolume;
	}

	public float getSoundVolume() {
		return soundVolume;
	}

}
