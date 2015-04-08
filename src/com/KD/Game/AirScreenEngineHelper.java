package com.KD.Game;

import static org.bytedeco.javacpp.opencv_core.cvSize;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.cocos2dx.lib.Cocos2dxGLSurfaceView;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.SurfaceView;

import com.diwit.airscreenengine.engine.ASEngine;
import com.diwit.airscreenengine.engine.ASEngine.ASEngineState;
import com.diwit.airscreenengine.engine.ASError;
import com.diwit.airscreenengine.engine.ASError.ErrorCode;

public class AirScreenEngineHelper {
	public static final String TAG = "com.diwit.kineticdefender.airscreenenginehelper";
//	private native void imagePickerFinishedNative(byte[] data, int width, int height, ASError error);
	private native void imagePickerFinishedNative(String path, ASError error);
	private native void imagePickerCancelledNative();

	private static AirScreenEngineHelper _instance = null;
	
	protected ASEngine _asEngine = null;
	protected Context _context = null;
	CameraUIPreview _cameraPreview = null;
	Cocos2dxGLSurfaceView _glSurfaceView = null;
	boolean _wasCapturing = false;
	
	public void set_asEngine(ASEngine _asEngine) {
		this._asEngine = _asEngine;
	}
    
    public ASEngine get_asEngine() {
		return _asEngine;
	}
    
	public void set_context(Context _context) {
		this._context = _context;
	}
    
    public Context get_context() {
		return _context;
	}
    
    public void set_cameraPreview(CameraUIPreview _cameraPreview) {
		this._cameraPreview = _cameraPreview;
	}
    
    public CameraUIPreview get_cameraPreview() {
		return _cameraPreview;
	}
    
    public void set_glSurfaceView(Cocos2dxGLSurfaceView _glSurfaceView) {
		this._glSurfaceView = _glSurfaceView;
	}
    
    public Cocos2dxGLSurfaceView get_glSurfaceView() {
		return _glSurfaceView;
	}
    
	protected AirScreenEngineHelper() {
		_asEngine = ASEngine.getInstance();
	}
	
	public static AirScreenEngineHelper getInstance() {
		if (_instance == null) {
			_instance = new AirScreenEngineHelper();
		}
		
		return _instance;
	}
	
	public boolean setupAirScreenEngine(ASError error) {
    	boolean ret = true;
    	String errorMsg = "";
    	
    	try {
    		_asEngine = ASEngine.getInstance();
        	
        	this._setupAirScreenEngine();

    	} catch (Exception e) {
    		errorMsg = String.format("An error occurred. Error description: %s", e.toString());

    		ret = false;
    	}
    	
    	if (ret == false) {
    		if (error != null) {
    			error.put(TAG, ErrorCode.kASErr_DefaultError, errorMsg);
    		}
    	}
    	
    	return ret;
    }
	
	public void _setupAirScreenEngine() throws Exception {
	    KineticDefenderSettings settings; 
	    DisplayMetrics displayMetrics = null;
	    boolean isCameraAvailable;
	    boolean isFrontCameraAvailable;
	    
	    assert(_context == null);
	    assert(_asEngine == null);
	    assert(_cameraPreview == null);
	    
    	// Cargar la configuracion del motor de seguimiento
	    settings = new KineticDefenderSettings(_context);
	    settings.loadSettings();
	    
	    // Establecer el tamaño de la salida por pantalla
	    displayMetrics = _context.getResources().getDisplayMetrics();
	    settings._outputViewSize = cvSize(displayMetrics.widthPixels, displayMetrics.heightPixels);
		
	    // Establecer la orientacion del dispositivo
	    settings._interfaceOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
	    
	    // Comprobar disponibilidad de camara
	    isCameraAvailable = ASEngine.isCameraAvailable();
	    
	    if (!isCameraAvailable)
	    {
	    	throw new Exception("No camera available.");
	    }
	    settings._useVideoFile = false;
	    
	    // Comprobar disponibilidad de camara de enfrente
	    isFrontCameraAvailable = ASEngine.isFrontCameraAvailable();
	    settings._useFrontFacingCamera = isFrontCameraAvailable;

    	// Si no fue inicializado el engine
 		if (!_asEngine.isStateActive(ASEngineState.ENG_STATE_INITIAL)) {
 		    _asEngine.initWithSettings(settings);
 		    _asEngine.set_context(_context);
 		}
	}
	
	public boolean refreshSettings (KineticDefenderSettings settings, ASError error) {
	    ASError errorRet = new ASError();
	    boolean ret = true;
	    boolean retFunc;
	    String errorMsg = "";
	    
	    if (_asEngine != null)
	    {
	    	// TODO: ver si hay estas lineas son necesarias
//	        // Actualizar tamaño de salida
//	        CvSize wSize = [[CCDirector sharedDirector] view].frame.size;
////	        wSize.width = wSize.width * settings.outputSizeMult;
////	        wSize.height = wSize.height * settings.outputSizeMult;
//	        settings.outputViewSize = wSize;
	        
	        // Refrescamos la configuracion del motor
	        retFunc = _asEngine.refreshSettings(settings, errorRet);
	        if (retFunc == false)
	        {
	            if (errorRet.get_returnCode() != ErrorCode.kASErr_NoError)
	            {
	                errorMsg = String.format("An error occurred updating settings. Error description: %s", errorRet.get_returnMsg());
	            }
	            else
	            {
	                errorMsg = "An error occurred updating settings.";
	            }
	            if (error != null)
	            {
	                error.put(TAG, ErrorCode.kASErr_DefaultError, errorMsg);
	            }
	            ret = false;
	        }
	    }
	    
	    return ret;
	}
	
	public boolean saveTrackingInfo(ASError error) {
	    boolean ret = true;
	    ASError errorRet = new ASError();
	    String errorMsg = "";
	    String dataExp = "";
	    
	    if (_asEngine.isTrackingColorSelected() == false) {
	    	errorMsg = "Tracking Color has not been selected";
	    	
	        ret = false;
	    }
	    
	    if (ret == true) {
	        // Obtener informacion de los parametros de seguimiento actuales
	        dataExp = _asEngine.getTrackingInfo(errorRet);
	        
	        if (dataExp == null || dataExp.isEmpty()) {
	            if (errorRet.get_returnCode() != ErrorCode.kASErr_NoError) {
	                errorMsg = String.format("An error occurred saving Tracking Info. Error description: %s", errorRet.get_returnMsg());
	            } else {
	                errorMsg = "An error occurred saving Tracking Info.";
	            }

	            ret = false;
	        }
	    }
	    
	    if (ret == true) {
	        // Almacenar la informacion en el archivo myfile.txt
	        FileOutputStream outputStream;

	        try {
	          outputStream = _context.openFileOutput("myfile.txt", Context.MODE_PRIVATE);
	          outputStream.write(dataExp.getBytes());
	          outputStream.close();
	        } catch (Exception e) {
	        	errorMsg = String.format("An error occurred saving Tracking Info File. Error description: %s", e.toString());
	        	
	        	ret = false;
	        }
	    }

	    if (ret == false) {
	    	if (error != null) {
	    		error.put(TAG, ErrorCode.kASErr_DefaultError, errorMsg);
	    	}
	    }
	    
	    return ret;
	}

	private boolean privateFileExists(String path) {
    	boolean ret = false;
    	
    	try {
    		InputStream inputStream = _context.openFileInput(path);
    		inputStream.close();
    		
    		ret = true;
    	}
    	catch (Exception ex) {
    	}
    	return ret;
    }
	
	public boolean readTrackingInfo(ASError error) {
	    boolean retFunc;
	    boolean ret = true;
	    ASError errorRet = new ASError();
	    String errorMsg = "";
	    String dataExp = "";
	    
	    if (ret == true)
	    {
	        // Invalidar el color seleccionado previamente
	        retFunc = _asEngine.cancelTrackingColorSelection(errorRet);
	        if (retFunc == false)
	        {
	            if (errorRet.get_returnCode() != ErrorCode.kASErr_NoError)
	            {
	                errorMsg = String.format("An error occurred setting Tracking Info. Cannot cancel Tracking Color Selection. Error description: %s", errorRet.get_returnMsg());
	            }
	            else
	            {
	                errorMsg = "An error occurred setting Tracking Info. Cannot cancel Tracking Color Selection.";
	            }
	            
	            ret = false;
	        }
	    }
	    
	    if (ret == true)
	    {
	        // Leer la informacion de los parametros de seguimiento del motor
	    	// Leer el archivo de informacion de tracking si es que existe
			if (this.privateFileExists("myfile.txt")) {
				InputStream inputStream = null;
				BufferedReader r = null;
				try {
					inputStream = _context.openFileInput("myfile.txt");
					r = new BufferedReader(new InputStreamReader(inputStream));
					StringBuilder total = new StringBuilder(); 
					String line; 

					while ((line = r.readLine()) != null) {
					   total.append(line);
					}
					dataExp = total.toString();
					
					
				} catch (FileNotFoundException e) {
					errorMsg = "An error occurred reading Tracking Info File. Error description: Tracking info file not found.";
	    			
	    			ret = false;
	    		} catch (Exception e) {
	    			errorMsg = String.format("An error occurred reading Tracking Info File. Error description: %s", e.getMessage());
	    			
	    			ret = false;
	    		} finally {
	    			try {
	    				if (r != null) {
		    				r.close();
		    			}
						if (inputStream != null) {
							inputStream.close();
						}
	    			} catch (Exception ex) {
	    				ex.printStackTrace();
	    			}
				}
			}
	    }
	    
	    if (ret == true)
	    {
	        // Establecer los parametros de seguimiento
	        retFunc = _asEngine.setTrackingInfo(dataExp, errorRet);
	        
	        if (retFunc == false)
	        {
	            if (errorRet.get_returnCode() != ErrorCode.kASErr_NoError)
	            {
	                errorMsg = String.format("An error occurred setting Tracking Info. Error description: %s", errorRet.get_returnMsg());
	            }
	            else
	            {
	                errorMsg = "An error occurred setting Tracking Info.";
	            }

	            ret = false;
	        }
	    }
	    
	    if (ret == false) {
	    	if (error != null) {
	    		error.put(TAG, ErrorCode.kASErr_DefaultError, errorMsg);
	    	}
	    }
	    
	    return ret;
	}
	
	public boolean startAirScreenEngine(ASError error) {
	    ASError errorRet = new ASError();
	    boolean ret = true;
	    boolean retFunc;
	    String errorMsg = "";
	    
	    // Iniciar el motor
	    retFunc = _asEngine.start(errorRet);
	    if (retFunc == false)
	    {
	        if (errorRet.get_returnCode() != ErrorCode.kASErr_NoError)
	        {
	            errorMsg = String.format("An error occurred: %s", errorRet.get_returnMsg());
	            Log.w(TAG, errorMsg);
	        }
	        else
	        {
	            errorMsg = "An error occurred";
	            Log.w(TAG, errorMsg);
	        }
	        if (error != null)
	        {
	            error.put(TAG, ErrorCode.kASErr_DefaultError, errorMsg);
	        }
	        
	        ret = false;
	    }
	    
	    return ret;
	}
	
	public boolean stopAirScreenEngine(ASError error) {
	    ASError errorRet = new ASError();
	    boolean ret = true;
	    boolean retFunc;
	    String errorMsg = "";
	    
	    // Stoppear el motor
	    retFunc = _asEngine.stop(errorRet);
	    if (retFunc == false)
	    {
	        if (errorRet.get_returnCode() != ErrorCode.kASErr_NoError)
	        {
	            errorMsg = String.format("An error occurred: %s", errorRet.get_returnMsg());
	            Log.w(TAG, errorMsg);
	        }
	        else
	        {
	            errorMsg = "An error occurred";
	            Log.w(TAG, errorMsg);
	        }
	        if (error != null)
	        {
	            error.put(TAG, ErrorCode.kASErr_DefaultError, errorMsg);
	        }
	        
	        ret = false;
	    }
	    
	    return ret;
	}
	
	public void showCameraOutput(boolean show) {
		final boolean finalShow = show;
		
		((KineticDefender)_context).runOnUiThread(new Runnable() {
    		public void run() {
    			if (_cameraPreview != null) {
    				if (finalShow == true) {
    					_cameraPreview.setVisibility(SurfaceView.VISIBLE);
    				} else {
    					_cameraPreview.setVisibility(SurfaceView.GONE);
//    					_cameraPreview.clearSurface();
    				}
    			}
    		}
    	});
		
//		Handler _handler = new Handler();
//    	_handler.post(new Runnable() {
//    		public void run() {
//    			if (_cameraPreview != null) {
//    				if (finalShow == true) {
//    					_cameraPreview.setVisibility(SurfaceView.VISIBLE);
//    				} else {
//    					_cameraPreview.setVisibility(SurfaceView.GONE);
//    				}
//    			}
//    		}
//    	});

	}
}
