package net.jhdt.videomonitor;

import javax.microedition.khronos.opengles.GL10;

import net.jhdt.videomonitor.util.BufferUtil;

import android.opengl.GLU;

public class MyScissorRender extends AbstractMyRender {
	private int width;
	private int height;
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		this.width = width;
		this.height = height;
		gl.glViewport(0, 0, width, height);
		ratio = (float)width / height;
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustumf(-ratio, ratio, -1, 1, 3f, 7f);
	}
	@Override
	public void onDrawFrame(GL10 gl) {
		//�����ɫ������
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//���û�����ɫ
		gl.glColor4f(1, 0, 0, 1);
		//����ģ����ͼ����
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		//�����������
		GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);
		//��ת�Ƕ�
		gl.glRotatef(xrotate, 1, 0, 0);
		gl.glRotatef(yrotate, 0, 1, 0);
		//���ü���
		gl.glEnable(GL10.GL_SCISSOR_TEST);
		
		float[] coords = {
			-ratio, 1, 2, 
			-ratio, -1, 2,
			ratio, 1, 2,
			ratio, -1, 2,
		};
		
		//��ɫ����
		float[][] colors = {
				{1f,0f,0f,1f},
				{0f,1f,0f,1f},
				{0f,0f,1f,1f},
				{1f,1f,0f,1f},
				{0f,1f,1f,1f},
				{1f,0f,1f,1f},
		};
		int step = 20;
		for (int i = 0; i < 6; i++) {
			gl.glScissor(i * step, i * step, width - i * step * 2, height - i * step * 2);
			gl.glColor4f(colors[i][0], colors[i][1], colors[i][2], colors[i][3]);
			gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.arr2ByteBuffer(coords));
			gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, coords.length / 3);
		}
	}

}
