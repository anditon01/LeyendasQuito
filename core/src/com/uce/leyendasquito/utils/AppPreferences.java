package com.uce.leyendasquito.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class AppPreferences {
	private static final String PREF_MUSIC_VOLUME = "volume";
	private static final String PREF_MUSIC_ENABLED = "music.enabled";
	private static final String PREF_SOUND_ENABLED = "sound.enabled";
	private static final String PREF_SOUND_VOL = "sound";
	private static final String PREFS_NAME = "configuracion";
	private static final String RESOLUTION_WIDTH_KEY = "resolution_width";
    private static final String RESOLUTION_HEIGHT_KEY = "resolution_height";
	//
    private static AppPreferences instance;

    private AppPreferences() {}

    public static AppPreferences getInstance() {
        if (instance == null) {
            instance = new AppPreferences();
        }
        return instance;
    }
 //
	protected Preferences getPrefs() {
		return Gdx.app.getPreferences(PREFS_NAME);
	}
	public void saveResolution(int width, int height) {
        getPrefs().putInteger(RESOLUTION_WIDTH_KEY, width);
        getPrefs().putInteger(RESOLUTION_HEIGHT_KEY, height);
        getPrefs().flush();
    }

    public int getResolutionWidth() {
        return getPrefs().getInteger(RESOLUTION_WIDTH_KEY, 640); // Valor por defecto
    }

    public int getResolutionHeight() {
        return getPrefs().getInteger(RESOLUTION_WIDTH_KEY, 800); // Valor por defecto
    }
	public boolean isSoundEffectsEnabled() {
		return getPrefs().getBoolean(PREF_SOUND_ENABLED, true);
	}
 
	public void setSoundEffectsEnabled(boolean soundEffectsEnabled) {
		getPrefs().putBoolean(PREF_SOUND_ENABLED, soundEffectsEnabled);
		getPrefs().flush();
	}
 
	public boolean isMusicEnabled() {
		return getPrefs().getBoolean(PREF_MUSIC_ENABLED, true);
	}
 
	public void setMusicEnabled(boolean musicEnabled) {
		getPrefs().putBoolean(PREF_MUSIC_ENABLED, musicEnabled);
		getPrefs().flush();
	}
 
	public float getMusicVolume() {
		return getPrefs().getFloat(PREF_MUSIC_VOLUME, 1f);
	}
 
	public void setMusicVolume(float volume) {
		getPrefs().putFloat(PREF_MUSIC_VOLUME, volume);
		getPrefs().flush();
	}
	
	public float getSoundVolume() {
		return getPrefs().getFloat(PREF_SOUND_VOL, 1f);
	}
 
	public void setSoundVolume(float volume) {
		getPrefs().putFloat(PREF_SOUND_VOL, volume);
		getPrefs().flush();
	}

}
