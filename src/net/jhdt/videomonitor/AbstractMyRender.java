package net.jhdt.videomonitor;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

public abstract class AbstractMyRender implements Renderer {

	public float ratio;
	public float xrotate = 0;//Χ��x����ת�Ƕ�
	public float yrotate = 0;//Χ��y����ת�Ƕ�

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		//����ɫ
		gl.glClearColor(0, 0, 0, 1);
		//�������㻺��������
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		//�����ӿ�
		gl.glViewport(0, 0, width, height);
		ratio = (float)width / (float)height;
		//ͶӰ����
		gl.glMatrixMode(GL10.GL_PROJECTION);
		//���ص�λ����
		gl.glLoadIdentity();
		//����ƽ��ͷ��
		gl.glFrustumf(-ratio, ratio, -1, 1, 3f, 7f);
	}

	@Override
	public abstract void onDrawFrame(GL10 gl);

}
