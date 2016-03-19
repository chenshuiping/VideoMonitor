package net.jhdt.videomonitor;

import javax.microedition.khronos.opengles.GL10;

import net.jhdt.videomonitor.util.BufferUtil;

import android.opengl.GLU;

public class MyTriangleRender extends AbstractMyRender {

	@Override
	public void onDrawFrame(GL10 gl) {
		//�����ɫ������
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//���û�ͼ��ɫ
		gl.glColor4f(1, 0, 0, 1);
		//����ģ����ͼ����
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		//�����������
		GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);
		//������ת�Ƕ�
		gl.glRotatef(xrotate, 1, 0, 0);
		gl.glRotatef(yrotate, 0, 1, 0);
		//���������
		float r = 0.5f;
		float[] coords = {
			-r, r, 0,
			-r, -r, 0,
			r, r, 0,
			r, -r, 0,
		};
		//ָ������ָ�벢����
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.arr2ByteBuffer(coords));
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, coords.length / 3);
	}

}
