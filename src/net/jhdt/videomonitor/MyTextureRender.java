package net.jhdt.videomonitor;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import net.jhdt.videomonitor.util.BufferUtil;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLU;
import android.opengl.GLUtils;

public class MyTextureRender extends AbstractMyRender {

	private Resources r;
	public MyTextureRender(Resources r) {
		this.r = r;
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		gl.glClearColor(0f, 0f, 0f, 1f);
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		//Adreno-ES20	<gl_draw_error_checks:608>:GL_INVALID_OPERATION
		//����취�������  gl.glEnableClientState(GL10.GL_COLOR_ARRAY);  ȥ��
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glEnable(GL10.GL_DEPTH_TEST);
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		gl.glViewport(0, 0, width, height);
		ratio = (float) width / height;
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity();
		gl.glFrustumf(-ratio, ratio, -1, 1, 3, 7);
	}

	@Override
	public void onDrawFrame(GL10 gl) {
		//�����ɫ������
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		//���û�����ɫ
//		gl.glColor4f(1, 0, 0, 1);
		//����ģ����ͼ����
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity();
		//�����������
		GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);
		//������ת
		gl.glRotatef(xrotate, 1, 0, 0);
		gl.glRotatef(yrotate, 0, 1, 0);

		//��������
		gl.glEnable(GL10.GL_TEXTURE_2D);
		int[] textids = new int[1];
		gl.glGenTextures(1, textids, 0);//��������ID
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textids[0]);//������ID
		Bitmap bmp = BitmapFactory.decodeResource(r, R.drawable.i0);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
		bmp.recycle();
		//
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		//
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

		//
		float[] texCoord = {
				0f, 1f,
				1f, 1f,
				0f, 0f,
				1f, 0f,
		};
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, BufferUtil.arr2FloatBuffer(texCoord));

		//
		float[] rectCoords = {
				-1f, -1f, 0,
				1f, -1f, 0,
				-1f, 1f, 0,
				1f, 1f, 0,
		};
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, BufferUtil.arr2FloatBuffer(rectCoords));
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, rectCoords.length / 3);
//		BufferUtil.drawRect(gl, rectCoords);
	}

	//	private float ratio ;
	//
	//	private Resources r ;
	//	public MyTextureRender(Resources r){
	//		this.r = r ;
	//	}
	//	public void onDrawFrame(GL10 gl) {
	//		// �����ɫ,���,ģ�建����
	//		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	//		gl.glMatrixMode(GL10.GL_MODELVIEW);
	//		gl.glLoadIdentity();
	//		GLU.gluLookAt(gl, 0, 0, 5, 0, 0, 0, 0, 1, 0);
	//
	//		/****************** �������� ************************/
	//		gl.glEnable(GL10.GL_TEXTURE_2D);
	//		int[] textids = new int[1];
	//		gl.glGenTextures(1, textids, 0);// ��������id
	//		gl.glBindTexture(GL10.GL_TEXTURE_2D, textids[0]);// ������������ֵ
	//		Bitmap bmp = BitmapFactory.decodeResource(r, R.drawable.i0);
	//		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
	//
	//		//������ģʽ.
	//
	//		//���Թ���,��ͼ�ڷŴ����Сʱ���ú��ַ�ʽ���д�������
	//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR); 
	//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR); 
	//		//������ģʽ,�����������û������0-1֮��Ļ�,���������������ϲ��ú��ַ�ʽ����.
	//		//�������1�Ļ�,ֻ��ʾ������ͼ.
	//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
	//		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
	//		bmp.recycle();
	//
	//		float[] texCoord = {
	//				0f,2f,
	//				2f,2f,
	//				0f,0f,
	//				2f,0f
	//		};
	//		ByteBuffer vbb = ByteBuffer.allocateDirect(texCoord.length * 4);
	//		vbb.order(ByteOrder.nativeOrder());
	//		FloatBuffer fbb = vbb.asFloatBuffer();
	//		fbb.put(texCoord);
	//		fbb.position(0);// ��ͬc,��ָ���Ƶ����������׵�ַ��
	//		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, fbb);
	//		
	//		float[] rectCoords = {
	//				-1f,-1f,0,
	//				1f,-1f,0,
	//				-1f,1f,0,
	//				1f,1f,0,
	//		};
	//		ByteBuffer vbb2 = ByteBuffer.allocateDirect(rectCoords.length * 4);
	//		vbb2.order(ByteOrder.nativeOrder());
	//		FloatBuffer fbb2 = vbb2.asFloatBuffer();
	//		fbb2.put(rectCoords);
	//		fbb2.position(0);// ��ͬc,��ָ���Ƶ����������׵�ַ��
	//		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, fbb2);
	//		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, rectCoords.length / 3);
	//	}
	//
	//	// �����ӿ�
	//	public void onSurfaceChanged(GL10 gl, int w, int h) {
	//		gl.glViewport(0, 0, w, h);
	//		ratio = (float) w / h;
	//		gl.glMatrixMode(GL10.GL_PROJECTION);
	//		gl.glLoadIdentity();
	//		gl.glFrustumf(-ratio, ratio, -1, 1, 3, 200);
	//	}
	//
	//	public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
	//		gl.glClearColor(0f, 0f, 0f, 1.0f);// ����ɫ
	//		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	//		gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
	//		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);// vertex array
	//		gl.glEnable(GL10.GL_DEPTH_TEST);// depth test
	//	}

}
