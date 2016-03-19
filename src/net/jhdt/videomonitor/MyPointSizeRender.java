package net.jhdt.videomonitor;

import javax.microedition.khronos.opengles.GL10;

import net.jhdt.videomonitor.util.BufferUtil;
import android.opengl.GLU;

public class MyPointSizeRender extends AbstractMyRender {

	@Override
	public void onDrawFrame(GL10 gl) {
		//清除颜色缓冲区
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//设置绘图颜色
		gl.glColor4f(1f, 0f, 0f, 1f);
		//模型视图矩阵
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		//设置眼球参数
		GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);
		//旋转角度
		gl.glRotatef(xrotate, 1, 0, 0);
		gl.glRotatef(yrotate, 0, 1, 0);
		//计算点坐标
		float r = 0.5f;
		float x = 0f, y = 0f, z = 1.0f;
		float zstep = 0.01f;
		float psize = 1.0f;
		float pstep = 0.5f;
		for (float alpha = 0; alpha < Math.PI * 6; alpha = (float)(alpha + Math.PI / 16)) {
			x = (float) (r * Math.cos(alpha));
			y = (float) (r * Math.sin(alpha));
			z = z - zstep;
			gl.glPointSize(psize = psize + pstep);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.arr2ByteBuffer(new float[]{x, y, z}));
			gl.glDrawArrays(GL10.GL_POINTS, 0, 1);
		}

	}
}
