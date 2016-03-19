package net.jhdt.videomonitor;

import javax.microedition.khronos.opengles.GL10;

import net.jhdt.videomonitor.util.BufferUtil;

import android.opengl.GLU;

public class MyTriangleRender extends AbstractMyRender {

	@Override
	public void onDrawFrame(GL10 gl) {
		//清除颜色缓冲区
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//设置绘图颜色
		gl.glColor4f(1, 0, 0, 1);
		//操作模型视图矩阵
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		//设置眼球参数
		GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);
		//设置旋转角度
		gl.glRotatef(xrotate, 1, 0, 0);
		gl.glRotatef(yrotate, 0, 1, 0);
		//计算点坐标
		float r = 0.5f;
		float[] coords = {
			-r, r, 0,
			-r, -r, 0,
			r, r, 0,
			r, -r, 0,
		};
		//指定定点指针并绘制
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.arr2ByteBuffer(coords));
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, coords.length / 3);
	}

}
