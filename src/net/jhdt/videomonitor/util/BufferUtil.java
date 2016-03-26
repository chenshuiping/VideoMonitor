package net.jhdt.videomonitor.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

public class BufferUtil {

	public static ByteBuffer int2ByteBuffer(int data) {
		ByteBuffer bb = ByteBuffer.allocateDirect(Integer.SIZE / 8);
		bb.order(ByteOrder.nativeOrder());
		IntBuffer ib = bb.asIntBuffer();
		ib.put(data);
		ib.position(0);
		return bb;
	}
	
/////////////////////////////////////////////
	
	public static ByteBuffer arr2ByteBuffer(float[] arr) {
		ByteBuffer ibb = ByteBuffer.allocateDirect(arr.length * 4);
		ibb.order(ByteOrder.nativeOrder());
		FloatBuffer fbb = ibb.asFloatBuffer();
		fbb.put(arr);
		fbb.position(0);
		return ibb;
	}
	
	public static FloatBuffer arr2FloatBuffer(float[] arr) {
		ByteBuffer ibb = ByteBuffer.allocateDirect(arr.length * 4);
		ibb.order(ByteOrder.nativeOrder());
		FloatBuffer fbb = ibb.asFloatBuffer();
		fbb.put(arr);
		fbb.position(0);
		return fbb;
	}

	public static ByteBuffer list2ByteBuffer(List<Float> list) {
		ByteBuffer ibb = ByteBuffer.allocateDirect(list.size() * 4);
		ibb.order(ByteOrder.nativeOrder());
		FloatBuffer fbb = ibb.asFloatBuffer();
		for (Float f : list) {
			fbb.put(f);
		}
		fbb.position(0);
		return ibb;
	}
	
	public static FloatBuffer list2FloatBuffer(List<Float> list) {
		ByteBuffer ibb = ByteBuffer.allocateDirect(list.size() * 4);
		ibb.order(ByteOrder.nativeOrder());
		FloatBuffer fbb = ibb.asFloatBuffer();
		for (Float f : list) {
			fbb.put(f);
		}
		fbb.position(0);
		return fbb;
	}
	
	public static void drawRect(GL10 gl, float[] vertexCoords) {
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, arr2FloatBuffer(vertexCoords));
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertexCoords.length / 3);
	}
}