package com.KD.Game;

import android.content.Context;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup.LayoutParams;

import com.diwit.airscreenengine.engine.videocapture.VideoCaptureDevice;

public class CameraUIPreview extends SurfaceView {

//	WebCamCaptureDevice _device = null;
	private VideoCaptureDevice _device = null;
	private SurfaceHolder _holder;
    
    public VideoCaptureDevice get_device() {
		return _device;
	}

	public void set_device(VideoCaptureDevice _device) {
		this._device = _device;
	}
	
	CameraUIPreview(Context context, VideoCaptureDevice device) {
        super(context);

        _device = device;

        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        _holder = this.getHolder();
        
        this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }
	
    CameraUIPreview(Context context) {
    	super(context);
    	
    	_holder = this.getHolder();
    	
    	this.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    }
    
    CameraUIPreview(Context context, AttributeSet attrs) {
    	super(context, attrs);
    }
    
    CameraUIPreview(Context context, AttributeSet attrs, int defstyle) {
    	super(context, attrs, defstyle);
    }
    
    public void setupOutput() {
    	try {
	    	this.stopSession();
	        
	        if (_device != null) {
	            
            	// Inicializar el VideoCaptureDevice, formato de captura
            	_device.setupVideoCapture();
	        }
    	} catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
	public void setVisibility(int visibility) {
    	if (visibility == SurfaceView.GONE) {
    		visibility = SurfaceView.VISIBLE;
    		this.setAlpha(0.0f);
    	} else {
    		this.setAlpha(100.0f);
    	}
		super.setVisibility(visibility);
	}

	public void startSession() throws Exception {
    	if (_device != null) {
    		_device.startCapture();
    	}
    }
    
    public void stopSession() {
    	if (_device != null) {
    		_device.stopCapture();
    	}
    }
}