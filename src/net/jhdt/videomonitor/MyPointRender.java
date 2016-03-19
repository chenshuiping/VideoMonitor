package net.jhdt.videomonitor;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import net.jhdt.videomonitor.util.BufferUtil;

import android.opengl.GLU;
import android.opengl.GLUtils;

public class MyPointRender extends AbstractMyRender {

	@Override
	public void onDrawFrame(GL10 gl) {
		//�����ɫ������
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		//���û�ͼ��ɫ
		gl.glColor4f(1f, 0f, 0f, 1f);
		//ģ����ͼ����
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		//�����������
		GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);
		//��ת�Ƕ�
		gl.glRotatef(xrotate, 1, 0, 0);
		gl.glRotatef(yrotate, 0, 1, 0);
		//���������
		float r = 0.5f;
		List<Float> coordsList = new ArrayList<Float>();
		float x = 0f, y = 0f, z = 1f;
		float zstep = 0.01f;
		for (float alpha = 0; alpha < Math.PI * 6; alpha = (float)(alpha + Math.PI / 16)) {
			x = (float) (r * Math.cos(alpha));
			y = (float) (r * Math.sin(alpha));
			z = z - zstep;
			coordsList.add(x);
			coordsList.add(y);
			coordsList.add(z);
		}
		//ת����Ϊ������
		ByteBuffer ibb = BufferUtil.list2ByteBuffer(coordsList);
		//ָ������ָ��
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, ibb);
		gl.glDrawArrays(GL10.GL_POINTS, 0, coordsList.size() / 3);
	}

}
