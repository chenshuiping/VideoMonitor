package net.jhdt.videomonitor;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

public abstract class AbstractMyRender implements Renderer {

	public float ratio;
	public float xrotate = 0;//围绕x轴旋转角度
	public float yrotate = 0;//围绕y轴旋转角度

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		//清屏色
		gl.glClearColor(0, 0, 0, 1);
		//启动定点缓冲区数组
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		//设置视口
		gl.glViewport(0, 0, width, height);
		ratio = (float)width / (float)height;
		//投影矩阵
		gl.glMatrixMode(GL10.GL_PROJECTION);
		//加载单位矩阵
		gl.glLoadIdentity();
		//设置平截头体
		gl.glFrustumf(-ratio, ratio, -1, 1, 3f, 7f);
	}

	@Override
	public abstract void onDrawFrame(GL10 gl);

}
