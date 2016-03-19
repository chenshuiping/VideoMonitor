package net.jhdt.videomonitor.customview;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import android.opengl.GLES20;
import android.util.Log;

public class JHGLProgram {
	private static final String LOG_TAG = "JHGLProgram";
	private boolean isProgBuilt;
	private ByteBuffer _vertice_buffer;
	private ByteBuffer _coord_buffer;
	private int _program;
	
	private int _video_width;
	private int _video_height;
	
	private int _ytid;
	private int _utid;
	private int _vtid;

	public boolean isProgramBuild() {
		return isProgBuilt;
	}
	
	public void buildProgram() {
		createBuffers(squareVertices, coordVertices);
		if (_program <= 0) {
			_program = createProgram(VERTEX_SHADER, FRAGMENT_SHADER);
		}
		Log.v(LOG_TAG, "_program = " + _program);
		
		_positionHandle = GLES20.glGetAttribLocation(_program, "vPosition");
		Log.v(LOG_TAG, "_positionHandle = " + _positionHandle);
		checkGLError("glGetAttribLocation vPosition");
		if (_positionHandle == -1) {
			throw new RuntimeException("could not get attribute location for vPosition");
		}
		
		_coordHandle = GLES20.glGetAttribLocation(_program, "a_texCoord");
		Log.v(LOG_TAG, "_coordHandle = " + _coordHandle);
		checkGLError("glGetAttribLocation a_texCoord");
		if (_coordHandle == -1) {
			throw new RuntimeException("could not get attribute location for a_texCoord");
		}
		
		_yHandle = GLES20.glGetUniformLocation(_program, "tex_y");
		Log.v(LOG_TAG, "_yHandle = " + _yHandle);
		checkGLError("glGetUniformLocation tex_y");
		if (_yHandle == -1) {
			throw new RuntimeException("could not get uniform location for tex_y");
		}
		
		_uHandle = GLES20.glGetUniformLocation(_program, "tex_u");
		Log.v(LOG_TAG, "_uHandle = " + _uHandle);
		checkGLError("glGetUniformLocation tex_u");
		if (_uHandle == -1) {
			throw new RuntimeException("could not get uniform location for tex_u");
		}
		
		_vHandle = GLES20.glGetUniformLocation(_program, "tex_v");
		Log.v(LOG_TAG, "_vHandle = " + _vHandle);
		checkGLError("glGetUniformLocation tex_v");
		if (_vHandle == -1) {
			throw new RuntimeException("could not get uniform location for tex_v");
		}
		
		isProgBuilt = true;
	}
	
	/**
	 * build a set of textures, one for Y, one for U, and one for V.
	 * @param y
	 * @param u
	 * @param v
	 * @param width
	 * @param height
	 */
	public void buildTextures(Buffer y, Buffer u, Buffer v, int width, int height) {
		boolean videoSizeChanged = (width != _video_width || height != _video_height);
		if (videoSizeChanged) {
			_video_width = width;
			_video_height = height;
			Log.v(LOG_TAG, "buildTextures videoSizeChanged: w="	+ _video_width + " h=" + _video_height);
		}
		
		//building texture for Y data
		if (_ytid < 0 || videoSizeChanged) {
			if (_ytid >= 0) {
				Log.v(LOG_TAG, "glDeleteTextures");
				GLES20.glDeleteTextures(1, new int[]{_ytid}, 0);
				checkGLError("glDeleteTextures");
			}
			int[] textures = new int[1];
			GLES20.glGenTextures(1, textures, 0);
			checkGLError("glGenTextures");
			_ytid = textures[0];
			Log.v(LOG_TAG, "glGenTextures Y = " + _ytid);
		}
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _ytid);
		checkGLError("glBindTexture");
		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE, _video_width, _video_height, 0,
				GLES20.GL_LUMINANCE, GLES20.GL_UNSIGNED_BYTE, y);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
		
		//building texture for U data
		if (_utid < 0 || videoSizeChanged) {
			if (_utid >= 0) {
				Log.v(LOG_TAG, "glDeleteTextures");
				GLES20.glDeleteTextures(1, new int[]{_utid}, 0);
				checkGLError("glDeleteTextures");
			}
			int[] textures = new int[1];
			GLES20.glGenTextures(1, textures, 0);
			checkGLError("glGenTextures");
			_utid = textures[0];
			Log.v(LOG_TAG, "glGenTextures U = " + _utid);
		}
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _utid);
		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE, _video_width / 2, _video_height / 2, 0,
				GLES20.GL_LUMINANCE, GLES20.GL_UNSIGNED_BYTE, u);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
		
		//building texture for V data
		if (_vtid < 0 || videoSizeChanged) {
			if (_vtid >= 0) {
				Log.v(LOG_TAG, "glDeleteTextures V");
				GLES20.glDeleteTextures(1, new int[]{_vtid}, 0);
				checkGLError("glDeleteTextures");
			}
			int[] textures = new int[1];
			GLES20.glGenTextures(1, textures, 0);
			checkGLError("glGenTextures");
			_vtid = textures[0];
			Log.v(LOG_TAG, "glGenTextures V = " + _vtid);
		}
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _vtid);
		GLES20.glTexImage2D(GLES20.GL_TEXTURE_2D, 0, GLES20.GL_LUMINANCE, _video_width / 2, _video_height /2, 0,
				GLES20.GL_LUMINANCE, GLES20.GL_UNSIGNED_BYTE, v);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_NEAREST);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
		GLES20.glTexParameterf(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);
	}
	
	/**
	 * render the frame
	 * the YUV data will be converted to RGB by shader.
	 */
	public void drawFrame() {
		GLES20.glUseProgram(_program);
		checkGLError("glUseProgram");
		
		GLES20.glVertexAttribPointer(_positionHandle, 2, GLES20.GL_FLOAT, false, 8, _vertice_buffer);
		checkGLError("glVertexAttribPointer mPositionHandle");
		GLES20.glEnableVertexAttribArray(_positionHandle);
		
		GLES20.glVertexAttribPointer(_coordHandle, 2, GLES20.GL_FLOAT, false, 8, _coord_buffer);
		checkGLError("glVertexAttribPointer maTextureHandle");
		GLES20.glEnableVertexAttribArray(_coordHandle);
		
		//bind textures
		GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _ytid);
		GLES20.glUniform1i(_yHandle, 0);
		
		GLES20.glActiveTexture(GLES20.GL_TEXTURE1);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _utid);
		GLES20.glUniform1i(_uHandle, 1);
		
		GLES20.glActiveTexture(GLES20.GL_TEXTURE2);
		GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, _vtid);
		GLES20.glUniform1i(_vHandle, 2);
		
		GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4);
		GLES20.glFinish();
		
		GLES20.glDisableVertexAttribArray(_positionHandle);
		GLES20.glDisableVertexAttribArray(_coordHandle);
	}
	
	/**
	 * these two buffers are used for holding vertices, screen vertices and texture vertices
	 * @param vert
	 * @param coord
	 */
	private void createBuffers(float[] vert, float[] coord) {
		_vertice_buffer = ByteBuffer.allocateDirect(vert.length * 4);
		_vertice_buffer.order(ByteOrder.nativeOrder());
		_vertice_buffer.asFloatBuffer().put(vert);
		_vertice_buffer.position(0);
		if (_coord_buffer == null) {
			_coord_buffer = ByteBuffer.allocateDirect(coord.length * 4);
			_coord_buffer.order(ByteOrder.nativeOrder());
			_coord_buffer.asFloatBuffer().put(coord);
			_coord_buffer.position(0);
		}
	}
	/**
	 * create program and load shaders, fragment shader is very importent
	 * @param vertexSource
	 * @param fragmentSource
	 * @return
	 */
	private int createProgram(String vertexSource, String fragmentSource) {
		int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, VERTEX_SHADER);
		int pixelShader = loadShader(GLES20.GL_FRAGMENT_SHADER, FRAGMENT_SHADER);
		Log.v(LOG_TAG, "vertexShader = " + vertexShader);
		Log.v(LOG_TAG, "pixelShader = " + pixelShader);
		int program = GLES20.glCreateProgram();
		if (program != 0) {
			GLES20.glAttachShader(program, vertexShader);
			checkGLError("glAttachShader");
			GLES20.glAttachShader(program, pixelShader);
			checkGLError("glAttachShader");
			GLES20.glLinkProgram(program);
			int[] linkStatus = new int[1];
			GLES20.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
			if (linkStatus[0] != GLES20.GL_TRUE) {
				Log.v(LOG_TAG, "Could not link program: ", null);
				Log.v(LOG_TAG, GLES20.glGetProgramInfoLog(program));
				GLES20.glDeleteProgram(program);
				program = 0;
			}
		}
		
		return program;
	}
	
	/**
	 * create shader with given source
	 */
	private int loadShader(int shaderType, String source) {
		int shader = GLES20.glCreateShader(shaderType);
		if (shader != 0) {
			GLES20.glShaderSource(shader, source);
			GLES20.glCompileShader(shader);
			int[] compiled = new int[1];
			GLES20.glGetShaderiv(shader, GLES20.GL_COMPILE_STATUS, compiled, 0);
			if (compiled[0] == 0) {
				Log.v(LOG_TAG, "Could not compile shader " + shaderType);
				Log.v(LOG_TAG, GLES20.glGetShaderInfoLog(shader));
				GLES20.glDeleteShader(shader);
				shader = 0;
			}
		}
		return shader;
	}
	
	private void checkGLError(String op) {
		int error;
		while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
			Log.v(LOG_TAG, "***** " + op + ":glError " + error);
			throw new RuntimeException(op + ":glError " + error);
		}
	}
	
	////////////////////////////////////////////////
	private static float[] squareVertices = {
		-1f, -1f,
		1f, -1f,
		-1f, 1f,
		1f, 1f
	};
	private static float[] coordVertices = {
		0f, 1f,
		1f, 1f,
		0f, 0f,
		1f, 0f
	};
	private static final String VERTEX_SHADER = 
			"attribute vec4 vPosition;\n" +
			"attribute vec2 v a_texCoord;\n" +
			"varying vec2 tc;\n" +
			"void main() {\n" +
			"gl_Position = vPosition;\n" +
			"tc = a_texCoord;\n" +
			"}\n";
	private static final String FRAGMENT_SHADER = 
			"precision mediump float;\n" +
			"uniform sampler2D tex_y;\n" +
			"uniform sampler2D tex_u;\n" +
			"uniform sampler2D tex_v;\n" +
			"varying vec2 tc;\n" +
			"void main() {\n" +
			"vec4 c = vec4((texture2D(tex_y, tc).r - 16./255.) * 1.164);\n" +
			"vec4 U = vec4(texture2D(tex_u, tc).r - 128./255.);\n" +
			"vec4 V = vec4(texture2D(tex_v, tc).r - 128./255.);\n" +
			"c += V * vec4(1.596, -0.813, 0, 0);\n" +
			"c += U * vec4(0, -0.392, 2.017, 0);\n" +
			"c.a = 1.0" +
			"gl_FragColor = c;\n" +
			"}\n";
	private int _positionHandle;
	private int _coordHandle;
	private int _yHandle;
	private int _uHandle;
	private int _vHandle;
}
