package com.KD.Game;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

import com.diwit.airscreenengine.engine.Settings;

public class KineticDefenderSettings extends Settings {

	public static final float kDefaultKDAsteroidScaleMult = 2.10f;
	public static final float kDefaultKDRocketScaleMult = 1.30f;
	public static final float kDefaultKDRocket2ScaleMult = 1.20f;
	public static final float kDefaultKDUfoScaleMult = 1.30f;
	public static final float kDefaultKDPowerUpScaleMult = 1.80f;

	public static final float kDefaultKDAsteroidExplosionScaleMult = 0.5f;
	public static final float kDefaultKDRocketExplosionScaleMult = 0.5f;
	public static final float kDefaultKDRocket2ExplosionScaleMult = 0.5f;
	public static final float kDefaultKDUfoExplosionScaleMult = 0.5f;

	public static final float kDefaultKDMaxAsteroidsMult = 2.6f;
	public static final float kDefaultKDAsteroidFireScaleMult = 2.10f;

	public static final float kDefaultKDAsteroidPeriodMult = 1.1f;
	public static final float kDefaultKDRocketPeriodMult = 1.1f;
	public static final float kDefaultKDRocket2PeriodMult = 1.1f;
	public static final float kDefaultKDUfoPeriodMult = 1.1f;
	public static final float kDefaultKDPowerUpPerdiodMult = 1.0f;

	public static final float kDefaultKDAsteroidDurationMult = 1.0f;
	public static final float kDefaultKDRocketDurationMult = 1.35f;
	public static final float kDefaultKDRocket2DurationMult = 1.35f;
	public static final float kDefaultKDUfoDurationMult = 1.25f;
	public static final float kDefaultKDPowerUpDurationdMult = 2.5f;

	public static final float kDefaultKDMaxEnemiesMult = 1.56f;

	public static final boolean kDefaultKDTouchEnabled = false;
	public static final boolean kDefaultKDAirScreenEnabled = true;

	public static final int kDefaultKDPowerUpTimeToTakeMs = 1000;
	public static final boolean kDefaultKDAutomaticPauseActive = true;
	
	public float _KDAsteroidScaleMult;
	public float _KDRocketScaleMult;
	public float _KDRocket2ScaleMult;
	public float _KDUfoScaleMult;
	public float _KDPowerUpScaleMult;
	public float _KDAsteroidExplosionScaleMult;
	public float _KDRocketExplosionScaleMult;
	public float _KDRocket2ExplosionScaleMult;
	public float _KDUfoExplosionScaleMult;
	public float _KDMaxAsteroidsMult;
	public float _KDAsteroidFireScaleMult;
	public float _KDAsteroidPeriodMult;
	public float _KDRocketPeriodMult;
	public float _KDRocket2PeriodMult;
	public float _KDUfoPeriodMult;
	public float _KDPowerUpPerdiodMult;
	public float _KDAsteroidDurationMult;
	public float _KDRocketDurationMult;
	public float _KDRocket2DurationMult;
	public float _KDUfoDurationMult;
	public float _KDPowerUpDurationdMult;
	public float _KDMaxEnemiesMult;
	public boolean _KDTouchEnabled;
	public boolean _KDAirScreenEnabled;
	public int _KDPowerUpTimeToTakeMs;
	public boolean _KDAutomaticPauseActive;
	
	public KineticDefenderSettings(Context context) {
		super(context);
		
		_KDAsteroidScaleMult = kDefaultKDAsteroidScaleMult;
		_KDRocketScaleMult = kDefaultKDRocketScaleMult;
		_KDRocket2ScaleMult = kDefaultKDRocket2ScaleMult;
		_KDUfoScaleMult = kDefaultKDUfoScaleMult;
		_KDPowerUpScaleMult = kDefaultKDPowerUpScaleMult;
		_KDAsteroidExplosionScaleMult = kDefaultKDAsteroidExplosionScaleMult;
		_KDRocketExplosionScaleMult = kDefaultKDRocketExplosionScaleMult;
		_KDRocket2ExplosionScaleMult = kDefaultKDRocket2ExplosionScaleMult;
		_KDUfoExplosionScaleMult = kDefaultKDUfoExplosionScaleMult;
		_KDMaxAsteroidsMult = kDefaultKDMaxAsteroidsMult;
		_KDAsteroidFireScaleMult = kDefaultKDAsteroidFireScaleMult;
		_KDAsteroidPeriodMult = kDefaultKDAsteroidPeriodMult;
		_KDRocketPeriodMult = kDefaultKDRocketPeriodMult;
		_KDRocket2PeriodMult = kDefaultKDRocket2PeriodMult;
		_KDUfoPeriodMult = kDefaultKDUfoPeriodMult;
		_KDPowerUpPerdiodMult = kDefaultKDPowerUpPerdiodMult;
		_KDAsteroidDurationMult = kDefaultKDAsteroidDurationMult;
		_KDRocketDurationMult = kDefaultKDRocketDurationMult;
		_KDRocket2DurationMult = kDefaultKDRocket2DurationMult;
		_KDUfoDurationMult = kDefaultKDUfoDurationMult;
		_KDPowerUpDurationdMult = kDefaultKDPowerUpDurationdMult;
		_KDMaxEnemiesMult = kDefaultKDMaxEnemiesMult;
		_KDTouchEnabled = kDefaultKDTouchEnabled;
		_KDAirScreenEnabled = kDefaultKDAirScreenEnabled;
		_KDPowerUpTimeToTakeMs = kDefaultKDPowerUpTimeToTakeMs;
		_KDAutomaticPauseActive = kDefaultKDAutomaticPauseActive;
	}
	
	public void loadSettings() {
		
		super.loadSettings();
		
		if (_context != null) {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(_context);
			
			_KDAsteroidScaleMult = (float)((int)(prefs.getFloat("htrack_KDAsteroidScaleMult", kDefaultKDAsteroidScaleMult) * 100)) / 100.0f;
			_KDRocketScaleMult = (float)((int)(prefs.getFloat("htrack_KDRocketScaleMult", kDefaultKDRocketScaleMult) * 100)) / 100.0f;
			_KDRocket2ScaleMult = (float)((int)(prefs.getFloat("htrack_KDRocket2ScaleMult", kDefaultKDRocket2ScaleMult) * 100)) / 100.0f;
			_KDUfoScaleMult = (float)((int)(prefs.getFloat("htrack_KDUfoScaleMult", kDefaultKDUfoScaleMult) * 100)) / 100.0f;
			_KDPowerUpScaleMult = (float)((int)(prefs.getFloat("htrack_KDPowerUpScaleMult", kDefaultKDPowerUpScaleMult) * 100)) / 100.0f;
			_KDAsteroidExplosionScaleMult = (float)((int)(prefs.getFloat("htrack_KDAsteroidExplosionScaleMult", kDefaultKDAsteroidExplosionScaleMult) * 100)) / 100.0f;
			_KDRocketExplosionScaleMult = (float)((int)(prefs.getFloat("htrack_KDRocketExplosionScaleMult", kDefaultKDRocketExplosionScaleMult) * 100)) / 100.0f;
			_KDRocket2ExplosionScaleMult = (float)((int)(prefs.getFloat("htrack_KDRocket2ExplosionScaleMult", kDefaultKDRocket2ExplosionScaleMult) * 100)) / 100.0f;
			_KDUfoExplosionScaleMult = (float)((int)(prefs.getFloat("htrack_KDUfoExplosionScaleMult", kDefaultKDUfoExplosionScaleMult) * 100)) / 100.0f;
			_KDMaxAsteroidsMult = (float)((int)(prefs.getFloat("htrack_KDMaxAsteroidsMult", kDefaultKDMaxAsteroidsMult) * 100)) / 100.0f;
			_KDAsteroidFireScaleMult = (float)((int)(prefs.getFloat("htrack_KDAsteroidFireScaleMult", kDefaultKDAsteroidFireScaleMult) * 100)) / 100.0f;
			_KDAsteroidPeriodMult = (float)((int)(prefs.getFloat("htrack_KDAsteroidPeriodMult", kDefaultKDAsteroidPeriodMult) * 100)) / 100.0f;
			_KDRocketPeriodMult = (float)((int)(prefs.getFloat("htrack_KDRocketPeriodMult", kDefaultKDRocketPeriodMult) * 100)) / 100.0f;
			_KDRocket2PeriodMult = (float)((int)(prefs.getFloat("htrack_KDRocket2PeriodMult", kDefaultKDRocket2PeriodMult) * 100)) / 100.0f;
			_KDUfoPeriodMult = (float)((int)(prefs.getFloat("htrack_KDUfoPeriodMult", kDefaultKDUfoPeriodMult) * 100)) / 100.0f;
			_KDPowerUpPerdiodMult = (float)((int)(prefs.getFloat("htrack_KDPowerUpPerdiodMult", kDefaultKDPowerUpPerdiodMult) * 100)) / 100.0f;
			_KDAsteroidDurationMult = (float)((int)(prefs.getFloat("htrack_KDAsteroidDurationMult", kDefaultKDAsteroidDurationMult) * 100)) / 100.0f;
			_KDRocketDurationMult = (float)((int)(prefs.getFloat("htrack_KDRocketDurationMult", kDefaultKDRocketDurationMult) * 100)) / 100.0f;
			_KDRocket2DurationMult = (float)((int)(prefs.getFloat("htrack_KDRocket2DurationMult", kDefaultKDRocket2DurationMult) * 100)) / 100.0f;
			_KDUfoDurationMult = (float)((int)(prefs.getFloat("htrack_KDUfoDurationMult", kDefaultKDUfoDurationMult) * 100)) / 100.0f;
			_KDPowerUpDurationdMult = (float)((int)(prefs.getFloat("htrack_KDPowerUpDurationdMult", kDefaultKDPowerUpDurationdMult) * 100)) / 100.0f;
			_KDMaxEnemiesMult = (float)((int)(prefs.getFloat("htrack_KDMaxEnemiesMult", kDefaultKDMaxEnemiesMult) * 100)) / 100.0f;
			_KDTouchEnabled = prefs.getBoolean("htrack_KDTouchEnabled", kDefaultKDTouchEnabled);
			_KDAirScreenEnabled = prefs.getBoolean("htrack_KDAirScreenEnabled", kDefaultKDAirScreenEnabled);
			_KDPowerUpTimeToTakeMs = prefs.getInt("htrack_KDPowerUpTimeToTakeMs", kDefaultKDPowerUpTimeToTakeMs);
			_KDAutomaticPauseActive = prefs.getBoolean("htrack_KDAutomaticPauseActive", kDefaultKDAutomaticPauseActive);
			
			// Chequear si los parametros ya fueron almacenados previamente
			if (this.isDefaultDataPresent() == false) {
				// No se encontraron, entonces almacenar los actuales que deberian ser los default
				this.saveSettings();
			}
		}
	}
	
	final String PREFS_NAME = "KineticDefenderFirstTime";
	public void saveSettings () {
		super.saveSettings();
		
		if (_context != null) {
			SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(_context);
			
			Editor edit = prefs.edit();

			
			if (prefs.getBoolean("my_first_time", true)) {
			    //the app is being launched for first time, do something        
			    // first time task
			    // record the fact that the app has been started at least once
			    edit.putBoolean("my_first_time", false); 
			}
			_KDAsteroidScaleMult = (float)((int)(_KDAsteroidScaleMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDAsteroidScaleMult", _KDAsteroidScaleMult);
			_KDRocketScaleMult = (float)((int)(_KDRocketScaleMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDRocketScaleMult", _KDRocketScaleMult);
			_KDRocket2ScaleMult = (float)((int)(_KDRocket2ScaleMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDRocket2ScaleMult", _KDRocket2ScaleMult);
			_KDUfoScaleMult = (float)((int)(_KDUfoScaleMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDUfoScaleMult", _KDUfoScaleMult);
			_KDPowerUpScaleMult = (float)((int)(_KDPowerUpScaleMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDPowerUpScaleMult", _KDPowerUpScaleMult);
			_KDAsteroidExplosionScaleMult = (float)((int)(_KDAsteroidExplosionScaleMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDAsteroidExplosionScaleMult", _KDAsteroidExplosionScaleMult);
			_KDRocketExplosionScaleMult = (float)((int)(_KDRocketExplosionScaleMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDRocketExplosionScaleMult", _KDRocketExplosionScaleMult);
			_KDRocket2ExplosionScaleMult = (float)((int)(_KDRocket2ExplosionScaleMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDRocket2ExplosionScaleMult", _KDRocket2ExplosionScaleMult);
			_KDUfoExplosionScaleMult = (float)((int)(_KDUfoExplosionScaleMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDUfoExplosionScaleMult", _KDUfoExplosionScaleMult);
			_KDMaxAsteroidsMult = (float)((int)(_KDMaxAsteroidsMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDMaxAsteroidsMult", _KDMaxAsteroidsMult);
			_KDAsteroidFireScaleMult = (float)((int)(_KDAsteroidFireScaleMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDAsteroidFireScaleMult", _KDAsteroidFireScaleMult);
			_KDAsteroidPeriodMult = (float)((int)(_KDAsteroidPeriodMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDAsteroidPeriodMult", _KDAsteroidPeriodMult);
			_KDRocketPeriodMult = (float)((int)(_KDRocketPeriodMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDRocketPeriodMult", _KDRocketPeriodMult);
			_KDRocket2PeriodMult = (float)((int)(_KDRocket2PeriodMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDRocket2PeriodMult", _KDRocket2PeriodMult);
			_KDUfoPeriodMult = (float)((int)(_KDUfoPeriodMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDUfoPeriodMult", _KDUfoPeriodMult);
			_KDPowerUpPerdiodMult = (float)((int)(_KDPowerUpPerdiodMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDPowerUpPerdiodMult", _KDPowerUpPerdiodMult);
			_KDAsteroidDurationMult = (float)((int)(_KDAsteroidDurationMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDAsteroidDurationMult", _KDAsteroidDurationMult);
			_KDRocketDurationMult = (float)((int)(_KDRocketDurationMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDRocketDurationMult", _KDRocketDurationMult);
			_KDRocket2DurationMult = (float)((int)(_KDRocket2DurationMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDRocket2DurationMult", _KDRocket2DurationMult);
			_KDUfoDurationMult = (float)((int)(_KDUfoDurationMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDUfoDurationMult", _KDUfoDurationMult);
			_KDPowerUpDurationdMult = (float)((int)(_KDPowerUpDurationdMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDPowerUpDurationdMult", _KDPowerUpDurationdMult);
			_KDMaxEnemiesMult = (float)((int)(_KDMaxEnemiesMult * 100)) / 100.0f;
			edit.putFloat("htrack_KDMaxEnemiesMult", _KDMaxEnemiesMult);
			edit.putBoolean("htrack_KDTouchEnabled", _KDTouchEnabled);
			edit.putBoolean("htrack_KDAirScreenEnabled", _KDAirScreenEnabled);
			edit.putInt("htrack_KDPowerUpTimeToTakeMs", _KDPowerUpTimeToTakeMs);
			edit.putBoolean("htrack_KDAutomaticPauseActive", _KDAutomaticPauseActive);
			
			edit.commit();
		}
	}
	
//	public Object copy () {
//		KineticDefenderSettings copy = (KineticDefenderSettings)super.copy();
//	    
//		copy._KDAsteroidScaleMult = kDefaultKDAsteroidScaleMult;
//		copy._KDRocketScaleMult = _KDRocketScaleMult;
//		copy._KDRocket2ScaleMult = _KDRocket2ScaleMult;
//		copy._KDUfoScaleMult = _KDUfoScaleMult;
//		copy._KDPowerUpScaleMult = _KDPowerUpScaleMult;
//		copy._KDAsteroidExplosionScaleMult = _KDAsteroidExplosionScaleMult;
//		copy._KDRocketExplosionScaleMult = _KDRocketExplosionScaleMult;
//		copy._KDRocket2ExplosionScaleMult = _KDRocket2ExplosionScaleMult;
//		copy._KDUfoExplosionScaleMult = _KDUfoExplosionScaleMult;
//		copy._KDMaxAsteroidsMult = _KDMaxAsteroidsMult;
//		copy._KDAsteroidFireScaleMult = _KDAsteroidFireScaleMult;
//		copy._KDAsteroidPeriodMult = _KDAsteroidPeriodMult;
//		copy._KDRocketPeriodMult = _KDRocketPeriodMult;
//		copy._KDRocket2PeriodMult = _KDRocket2PeriodMult;
//		copy._KDUfoPeriodMult = _KDUfoPeriodMult;
//		copy._KDPowerUpPerdiodMult = _KDPowerUpPerdiodMult;
//		copy._KDAsteroidDurationMult = _KDAsteroidDurationMult;
//		copy._KDRocketDurationMult = _KDRocketDurationMult;
//		copy._KDRocket2DurationMult = _KDRocket2DurationMult;
//		copy._KDUfoDurationMult = _KDUfoDurationMult;
//		copy._KDPowerUpDurationdMult = _KDPowerUpDurationdMult;
//		copy._KDMaxEnemiesMult = _KDMaxEnemiesMult;
//		copy._KDTouchEnabled = _KDTouchEnabled;
//		copy._KDAirScreenEnabled = _KDAirScreenEnabled;
//		copy._KDPowerUpTimeToTakeMs = _KDPowerUpTimeToTakeMs;
//		copy._KDAutomaticPauseActive = _KDAutomaticPauseActive;
//	    
//	    return copy;
//	}
}
