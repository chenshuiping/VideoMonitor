package net.jhdt.videomonitor.customview;

import java.nio.ByteBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class JHGLFrameRender implements Renderer {
	private final static String LOG_TAG = "JHGLFrameRender";
	private GLSurfaceView mTargetSurface;
	private JHGLProgram prog = new JHGLProgram();
	private int mVideoWidth = -1, mVideoHeight = -1;
	private ByteBuffer y;
	private ByteBuffer u;
	private ByteBuffer v;

	public JHGLFrameRender(GLSurfaceView surface) {
		super();
		mTargetSurface = surface;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		Log.v(LOG_TAG, "onSurfaceCreated");
		if (!prog.isProgramBuild()) {
			prog.buildProgram();
			Log.v(LOG_TAG, "JHGLFrameRender :: onSurfaceCreated");
		}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		Log.v(LOG_TAG, "JHGLFrameRender :: onSurfaceChanged");
		gl.glViewport(0, 0, width, height);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		synchronized (this) {
			if (y != null) {
				y.position(0);
				u.position(0);
				v.position(0);
				prog.buildTextures(y, u, v, mVideoWidth, mVideoHeight);
				GLES20.glClearColor(0, 0, 0, 1);
				GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
				prog.drawFrame();
			}
		}
	}
	
	public void update(int w, int h) {
		Log.v(LOG_TAG, "Init e");
		if (w > 0 && h > 0) {
			if (w != mVideoWidth && h != mVideoHeight) {
				this.mVideoWidth = w;
				this.mVideoHeight = h;
				int yarraySize = w * h;
				int uvarraySize = yarraySize / 4;
				synchronized (this) {
					y = ByteBuffer.allocate(yarraySize);
					u = ByteBuffer.allocate(uvarraySize);
					v = ByteBuffer.allocate(uvarraySize);
				}	
			}
		}
		Log.v(LOG_TAG, "Init x");
	}
	
	public void update(byte[] ydata, byte[] udata, byte[] vdata) {
		synchronized (this) {
			y.clear();
			u.clear();
			v.clear();
			y.put(ydata, 0, ydata.length);
			u.put(udata, 0, udata.length);
			v.put(vdata, 0, vdata.length);
		}
		
		mTargetSurface.requestRender();
	}
}
