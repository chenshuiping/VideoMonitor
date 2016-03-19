package net.jhdt.videomonitor;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import net.jhdt.videomonitor.util.BufferUtil;

import android.opengl.GLU;

public class MyLineStripRender extends AbstractMyRender {

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
		//旋转角度
		gl.glRotatef(xrotate, 1, 0, 0);
		gl.glRotatef(yrotate, 0, 1, 0);
		//计算点坐标
		float r = 0.5f;
		List<Float> coordsList = new ArrayList<Float>();
		float x = 0f, y = 0f, z = 0.6f;
		float zstep = 0.005f;
		for (float alpha = 0; alpha < Math.PI * 6; alpha = (float) (alpha + Math.PI / 32)) {
			x = (float) (r * Math.cos(alpha));
			y = (float) (r * Math.sin(alpha));
			z = z - zstep;
			coordsList.add(x);
			coordsList.add(y);
			coordsList.add(z);
		}
		//指定定点指针并绘制
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.list2ByteBuffer(coordsList));
		gl.glDrawArrays(GL10.GL_LINE_STRIP, 0, coordsList.size() / 3);
	}

}
