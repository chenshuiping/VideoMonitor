package net.jhdt.videomonitor;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import net.jhdt.videomonitor.util.BufferUtil;
import android.opengl.GLU;

public class MyTriangleConeRender extends AbstractMyRender {

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		//启用定点缓冲区
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//启用颜色缓冲区
		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		//设置着色模式
		gl.glShadeModel(GL10.GL_FLAT);
		//启用深度测试
		gl.glEnable(GL10.GL_DEPTH_TEST);
		//启用表面剔除
		gl.glEnable(GL10.GL_CULL_FACE);
		//指定前面
		gl.glFrontFace(GL10.GL_CCW);

		super.onSurfaceCreated(gl, config);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		//清除颜色缓冲区和深度缓冲区
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
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
		float r = 0.5f;//半径
		float x = 0f, y = 0f, z = -0.5f;
		/**************************锥面***********************/
		//锥面
		List<Float> coordsList = new ArrayList<Float>();
		//添加锥定点
		coordsList.add(0f);
		coordsList.add(0f);
		coordsList.add(0.5f);
		//添加锥顶点颜色
		List<Float> colorList = new ArrayList<Float>();
		colorList.add(1f);
		colorList.add(0f);
		colorList.add(0f);
		colorList.add(1f);
		/**************************锥底***************************/
		//锥底
		List<Float> coordsConeBottom  = new ArrayList<Float>();
		coordsConeBottom.add(0f);
		coordsConeBottom.add(0f);
		coordsConeBottom.add(-0.5f);

		boolean flag = false;
		for (float alpha = 0f; alpha < Math.PI * 6; alpha = (float) (alpha + Math.PI / 8)) {
			x = (float) (r * Math.cos(alpha));
			y = (float) (r * Math.sin(alpha));
			//锥面
			coordsList.add(x);
			coordsList.add(y);
			coordsList.add(z);
			//锥底
			coordsConeBottom.add(x);
			coordsConeBottom.add(y);
			coordsConeBottom.add(z);

			if(flag = !flag) {
				//黄色
				colorList.add(1f);
				colorList.add(1f);
				colorList.add(0f);
				colorList.add(1f);
			} else {
				//红色
				colorList.add(1f);
				colorList.add(0f);
				colorList.add(0f);
				colorList.add(1f);
			}
		}

		if(flag = !flag) {
			//黄色
			colorList.add(1f);
			colorList.add(1f);
			colorList.add(0f);
			colorList.add(1f);
		} else {
			//红色
			colorList.add(1f);
			colorList.add(0f);
			colorList.add(0f);
			colorList.add(1f);
		}

		//颜色缓冲区
		ByteBuffer colorBuffer = BufferUtil.list2ByteBuffer(colorList);

		//剔除背面
		gl.glCullFace(GL10.GL_BACK);
		//绘制锥面
		colorBuffer.position(0);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.list2ByteBuffer(coordsList));
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, coordsList.size() / 3);

		//剔除前面
		gl.glCullFace(GL10.GL_FRONT);
		//绘制锥底
		colorBuffer.position(16);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.list2ByteBuffer(coordsConeBottom));
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, coordsConeBottom.size() / 3);
	}

}
