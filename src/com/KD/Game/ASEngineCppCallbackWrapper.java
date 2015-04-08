package com.KD.Game;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import org.cocos2dx.lib.Cocos2dxActivity;

import android.graphics.Bitmap;

import com.diwit.airscreenengine.engine.ASEngine;
import com.diwit.airscreenengine.engine.ASEngine.ColorLearningState;
import com.diwit.airscreenengine.engine.ASError;
import com.diwit.airscreenengine.engine.ASMovementData;

//Wrapper Callback C++
public class ASEngineCppCallbackWrapper implements ASEngine.Callback {
	private native void cursorMovedNative(ASMovementData movementData);
	private native void fpsUpdatedNative(float newFPS);
	private native void frameUpdatedNative(byte[] data, int width, int height);
	private native void photoTakenNative(Bitmap photo, ASError error);
	private native void trackingColorLearningUpdatedNative(ColorLearningState learningState, Object learningStateInfo, ASError error);
	private native void lostTrackingAlgorithmExecutedNative(int accLostTrackingAlgorithmExecCount, long accTimeInMsLost, int lostFramesCount, long timeSinceLastAlgorithmInMs);
	
	public long _address = 0;
	private Cocos2dxActivity _cocosActivity = null;
	private long _glThreadID = -1;
	
	public ASEngineCppCallbackWrapper() {
		super();
		
		_cocosActivity = (Cocos2dxActivity)org.cocos2dx.lib.Cocos2dxActivity.getContext();
		this.getGlThreadID();
	}
	
	public ASEngineCppCallbackWrapper(long address) {
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
	
	@Override
	public void cursorMoved(ASMovementData movementData) {
		final ASMovementData finalMovementData = movementData;
		
		_cocosActivity.runOnGLThread(new Runnable() {
    		public void run() {
    			cursorMovedNative(finalMovementData);
    		}
    	});
	}

	@Override
	public void fpsUpdated(float newFPS) {
		final float finalNewFPS = newFPS;
		
		_cocosActivity.runOnGLThread(new Runnable() {
    		public void run() {
    			fpsUpdatedNative(finalNewFPS);
    		}
    	});			
	}

	@Override
	public void frameUpdated(Bitmap frameBmp) {
		final Bitmap finalFrameBmp = frameBmp;
		
		_cocosActivity.runOnGLThread(new Runnable() {
    		public void run() {
    			byte[] data = null;
    			int bytes = finalFrameBmp.getByteCount();
    			ByteBuffer buffer = ByteBuffer.allocate(bytes);
    			
    			finalFrameBmp.copyPixelsToBuffer(buffer);
    			data = buffer.array();

    			frameUpdatedNative(data, finalFrameBmp.getWidth(), finalFrameBmp.getHeight());
    		}
    	});	
	}

	@Override
	public void photoTaken(Bitmap photo, ASError error) {
		final Bitmap finalPhoto = photo;
		final ASError finalError = error;
		
		_cocosActivity.runOnGLThread(new Runnable() {
    		public void run() {
    			photoTakenNative(finalPhoto, finalError);
    		}
    	});
	}

	@Override
	public void trackingColorLearningUpdated(ColorLearningState learningState, Object learningStateInfo, ASError error) {
		final ColorLearningState finalLearningState = learningState;
		final Object finalLearningStateInfo = learningStateInfo;
		final ASError finalError = error;
		
		_cocosActivity.runOnGLThread(new Runnable() {
    		public void run() {
    			trackingColorLearningUpdatedNative(finalLearningState, finalLearningStateInfo, finalError);
    		}
    	});
	}

	@Override
	public void lostTrackingAlgorithmExecuted(int accLostTrackingAlgorithmExecCount, long accTimeInMsLost, int lostFramesCount, long timeSinceLastAlgorithmInMs) {
		final int finalAccLostTrackingAlgorithmExecCount = accLostTrackingAlgorithmExecCount;
		final long finalAccTimeInMsLost = accTimeInMsLost;
		final int finalLostFramesCount = lostFramesCount;
		final long finalTimeSinceLastAlgorithmInMs = timeSinceLastAlgorithmInMs;
		
		_cocosActivity.runOnGLThread(new Runnable() {
    		public void run() {
    			lostTrackingAlgorithmExecutedNative(finalAccLostTrackingAlgorithmExecCount, finalAccTimeInMsLost, finalLostFramesCount, 
    					finalTimeSinceLastAlgorithmInMs);
    		}
    	});
	}
}