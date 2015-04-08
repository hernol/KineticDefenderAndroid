package com.KD.Game;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

import org.bytedeco.javacpp.opencv_core.CvPoint;
import org.cocos2dx.lib.Cocos2dxActivity;

import com.diwit.airscreenengine.engine.ASCalibration;
import com.diwit.airscreenengine.engine.ASCalibration.ASCalibrationState;
import com.diwit.airscreenengine.engine.ASCalibrationPoint;
import com.diwit.airscreenengine.engine.ASCalibrationPoint.ASCalibrationPointState;
import com.diwit.airscreenengine.engine.ASError;

//Wrapper Callback C++
public class ASCalibrationCppCallbackWrapper implements ASCalibration.Callback {
	public native void calibrationPointStateUpdatedNative (int index, ASCalibrationPoint.ASCalibrationPointState state);
	public native void calibrationStateUpdatedNative (ASCalibration.ASCalibrationState newState, ASError error);
	public native boolean isCalibrationPointDetectedNative (ASCalibrationPoint point, CvPoint cursorPosition);
	public native boolean isCalibrationPointDetectionAlgorithmImplementedNative();
	
	public long _address = 0;
	Cocos2dxActivity _cocosActivity = null;
	private long _glThreadID = -1;
	
	public ASCalibrationCppCallbackWrapper() {
		super();
		
		_cocosActivity = (Cocos2dxActivity)org.cocos2dx.lib.Cocos2dxActivity.getContext();
		this.getGlThreadID();
	}
	
	public ASCalibrationCppCallbackWrapper(long address) {
		super();
		
		_address = address;
		_cocosActivity = (Cocos2dxActivity)org.cocos2dx.lib.Cocos2dxActivity.getContext();
		this.getGlThreadID();
	}
	
	private void getGlThreadID() {
		_cocosActivity.runOnGLThread(new Runnable() {
    		public void run() {
    			_glThreadID = Thread.currentThread().getId();
    		}
    	});
	}
	
	private boolean isRunningInGLThread() {
		//return Looper.myLooper() == Cocos2dxGLSurfaceView.getInstance().
		
		return _glThreadID == Thread.currentThread().getId();
	}
	
	@Override
	public void calibrationPointStateUpdated(int index, ASCalibrationPointState state) {
		final int finalIndex = index;
		final ASCalibrationPointState finalState = state;
		
		_cocosActivity.runOnGLThread(new Runnable() {
    		public void run() {
    			calibrationPointStateUpdatedNative(finalIndex, finalState);
    		}
    	});
	}
	@Override
	public void calibrationStateUpdated(ASCalibrationState newState, ASError error) {
		final ASCalibrationState finalNewState = newState;
		final ASError finalError = error;
		
		_cocosActivity.runOnGLThread(new Runnable() {
    		public void run() {
    			calibrationStateUpdatedNative(finalNewState, finalError);
    		}
    	});
	}
	@Override
	public boolean isCalibrationPointDetected (ASCalibrationPoint point, CvPoint cursorPosition) {
		final ASCalibrationPoint finalPoint = point;
		final CvPoint finalCursorPosition = cursorPosition;
		boolean ret = false;
		
		if (this.isRunningInGLThread()) {
			return isCalibrationPointDetectedNative(finalPoint, finalCursorPosition);
		} else {
			RunnableFuture<Boolean> run = new FutureTask<Boolean>(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return isCalibrationPointDetectedNative(finalPoint, finalCursorPosition);
				}
			});
			_cocosActivity.runOnGLThread(run);
			
			try {
				ret = run.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return ret;
	}
	@Override
	public boolean isCalibrationPointDetectionAlgorithmImplemented() {
		boolean ret = false;
		
		if (this.isRunningInGLThread()) {
			return isCalibrationPointDetectionAlgorithmImplementedNative();
		} else {
			RunnableFuture<Boolean> run = new FutureTask<Boolean>(new Callable<Boolean>() {
				@Override
				public Boolean call() throws Exception {
					return isCalibrationPointDetectionAlgorithmImplementedNative();
				}
			});
			_cocosActivity.runOnGLThread(run);
			
			try {
				ret = run.get();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return ret;
	}
}