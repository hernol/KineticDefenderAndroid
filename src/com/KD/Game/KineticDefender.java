/****************************************************************************
Copyright (c) 2010-2011 cocos2d-x.org

http://www.cocos2d-x.org

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
****************************************************************************/
package com.KD.Game;

import java.util.Calendar;

import org.cocos2dx.lib.Cocos2dxActivity;
import org.cocos2dx.lib.Cocos2dxGLSurfaceView;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.KD.Game.RestClient.RequestMethod;
import com.diwit.airscreenengine.engine.ASEngine;
import com.diwit.airscreenengine.engine.videocapture.VideoCaptureDevice;
import com.diwit.airscreenengine.engine.videocapture.WebCamCaptureDevice;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class KineticDefender extends Cocos2dxActivity{
	
    final String ADMOB_ID="pub-9277460339875541";
    static InterstitialAd interstitial;
    
	AirScreenEngineHelper _asHelper = null;
	
	private String FLURRY_APP_KEY = "V557PFPDRSMQYSFYVPQM";
    private int NOTIFICATION_DAYS = 10; 
	public String KINETIC_CURRENT_VERSION = "android-1.0";
	private String KINETIC_NEW_VERSION_DOWNLOAD_URL = "http://liha.com.ar/kinetic/download/";
	private String KINETIC_LAST_VERSION_URL = "http://liha.com.ar/kinetic/services/version.php";
	
    static Activity _me = null;
    static Activity me = null;
	static Cocos2dxGLSurfaceView _glSurfaceView = null;
	
	static {
        System.loadLibrary("airscreenengine");
        System.loadLibrary("cocos2dcpp");
	}

    protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);	
		me=this;
		try {
    		this.ASEngineInitialSetup();
    		interstitial = new InterstitialAd(this);
    	    interstitial.setAdUnitId(ADMOB_ID);
    	    AdRequest adRequest = new AdRequest.Builder().build();
    	    // Begin loading your interstitial.
    	    interstitial.loadAd(adRequest);
    		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        } catch(Exception e) {
        	Log.d(getLocalClassName(), "Se produjo un error: " + e.getMessage(), e);
        	e.printStackTrace();
        	getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
	}
    
    // Invoke displayInterstitial() when you are ready to display an interstitial.
    public void displayInterstitial() {
        if (interstitial.isLoaded()) {
            interstitial.show();
        }
    }
    
    private boolean isOnline() {
        ConnectivityManager cm =
            (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }
    
    protected void onStart() {
        createScheduledNotification(NOTIFICATION_DAYS);
		super.onStart();
    	FlurryAgent.onStartSession(this, FLURRY_APP_KEY);
    	if (isOnline()) {
    		RestClient rest = new RestClient(KINETIC_LAST_VERSION_URL);
    		rest.AddHeader("Content-Type", "application/json");
    		rest.AddParam("version", KINETIC_CURRENT_VERSION);
    		try {
				rest.Execute(RequestMethod.POST);
			} catch (Exception e) {
				//Log.e("KD", e.getMessage());
				return;
			}
    		if (!rest.getResponse().equals(200)) {
    		    //Log.e("HERNOL", "Response Code <> 200");
    		} else {
        		String version = rest.getResponse();
        		if (version == null) {
    				//Log.e("HERNOL", "Version null");
        		} else {
        			if (!version.toString().trim().equals(KINETIC_CURRENT_VERSION)) {
        				//Log.e("HERNOL", "Versiones distintas "+version.trim().toString()+" != "+KINETIC_CURRENT_VERSION);
        				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(KINETIC_NEW_VERSION_DOWNLOAD_URL+version));
        				startActivity(browserIntent);
        				finish();
        				// Mostrar cartel con url para descargar
        			} else {
        				//Log.e("HERNOL", "Versiones iguales "+version.toString()+" != "+KINETIC_CURRENT_VERSION);
        			}
        		}
    		}
    	} else {
    		//Log.e("HERNOL", "Sin internet");
    	}
	}
    
    protected void onStop() {
		super.onStop();
    	FlurryAgent.onEndSession(this);
	}
    
	@Override
	protected void onResume() {
		 // TODO Auto-generated method stub
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		super.onPause();
	}
    
    public Cocos2dxGLSurfaceView onCreateView() {
    	try {
	    	_glSurfaceView = new Cocos2dxGLSurfaceView(this);
	    	// KineticDefender should create stencil buffer
	    	_glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
	    	if (_glSurfaceView.getHolder() != null)
	    	{
	    		_glSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);
	    	}
        }
        catch(Exception e) {
        	Log.d(getLocalClassName(), "Se produjo un error: " + e.getMessage(), e);
        	e.printStackTrace();
        }
    	
    	return _glSurfaceView;
    }
    
    private void ASEngineInitialSetup() throws Exception {
    	VideoCaptureDevice device = WebCamCaptureDevice.getInstance();
    	CameraUIPreview cameraPreview = new CameraUIPreview(this, device);
 		((WebCamCaptureDevice)device).set_SurfaceView(cameraPreview);
 		
		cameraPreview.setVisibility(SurfaceView.GONE);
 		
    	_asHelper = AirScreenEngineHelper.getInstance();
    	_asHelper.set_context(this);
    	_asHelper.set_cameraPreview(cameraPreview);
    	_asHelper.set_glSurfaceView(_glSurfaceView);
    	
    	FrameLayout layout = (FrameLayout)_glSurfaceView.getParent();
		this.setContentView(cameraPreview, new LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		this.addContentView(layout, new LayoutParams(
                LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
    	
    	_glSurfaceView.setZOrderMediaOverlay(true);
    }
    
    /**
     * 
     * Testing Insterticial ad
     * 
     */
    
    static void showAdmobJNI(){
        _me.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                interstitial.show();
            }
        });
    }

//    static void hideAdmobJNI(){
//        me.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
////                adView.setVisibility(View.INVISIBLE);
//            }
//        });
//    }

    static void setAdmobVisibleJNI(final int number){
        _me.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(number==0){
                    //adView.setVisibility(View.INVISIBLE);
                } else {
                    //adView.setVisibility(View.VISIBLE);
                    interstitial.show();
                }
            }
        });

    }

    static void setVisibleAdmobJNI(final boolean visible){

        _me.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(visible){
                    //adView.setVisibility(View.VISIBLE);
                    interstitial.show();
                } else {
                    //adView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    static void setAdmobVisibilityJNI(final String name){
        _me.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(name.equals("show")||name=="show"){
                    //adView.setVisibility(View.VISIBLE);
                    interstitial.show();
                } else {
                    //adView.setVisibility(View.INVISIBLE);
                }
            }
        });
    }
    private void createScheduledNotification(int days) {
        // Get new calendar object and set the date to now
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        // Add defined amount of days to the date
        calendar.add(Calendar.HOUR_OF_DAY, days * 24);
        //calendar.add(Calendar.SECOND, days);

        // Retrieve alarm manager from the system
        AlarmManager alarmManager = (AlarmManager) getApplicationContext()
                .getSystemService(getBaseContext().ALARM_SERVICE);
        // Every scheduled intent needs a different ID, else it is just executed
        // once
        int id = (int) System.currentTimeMillis();

        // Prepare the intent which should be launched at the date
        Intent intent = new Intent(this, KineticNotification.class);

        // Prepare the pending intent
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getApplicationContext(), id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        // Register the alert in the system. You have the option to define if
        // the device has to wake up on the alert or not
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                pendingIntent);
    }


    
}
