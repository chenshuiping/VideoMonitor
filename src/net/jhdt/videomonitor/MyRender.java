package net.jhdt.videomonitor;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import net.jhdt.videomonitor.util.BufferUtil;

import android.opengl.GLU;

public class MyRender extends AbstractMyRender {

	@Override
	public void onDrawFrame(GL10 gl) {
		//清除颜色缓冲区
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//模型视图矩阵
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);
		//旋转角度
		gl.glRotatef(xrotate, 1, 0, 0);
		gl.glRotatef(yrotate, 0, 1, 0);
		//画三角形
		//三角形坐标
		float[] coords = {
			0, 1, 2,
			-ratio, -1, 2,
			ratio, -1, 2
		};

		ByteBuffer ibb = BufferUtil.arr2ByteBuffer(coords);
		//设置绘图颜色
		gl.glColor4f(1f, 0f, 0f, 1f);
		//
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, ibb);
		//
		gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
	}

}
