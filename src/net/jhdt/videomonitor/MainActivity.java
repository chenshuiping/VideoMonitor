package net.jhdt.videomonitor;

import java.util.ArrayList;
import java.util.List;

import net.jhdt.videomonitor.AdapterModel.SimpleTreeAdapter;
import net.jhdt.videomonitor.customview.JHGLFrameRender;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Point;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector.OnDoubleTapListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import cn.jhdt.treelistwidget.Annotation.FileBean;
import cn.jhdt.treelistwidget.Model.TreeListViewAdapter;


public class MainActivity extends Activity {
	private final static String LOG_TAG = "MainActivity";
//	private MySurfaceView view;
	private AbstractMyRender render;
	private float x0;
	private float y0;
	
	private boolean bFlag = true; //true -- 4分屏		false -- 单分屏
	private LinearLayout layout;
	
	private GLSurfaceView view1;
	private GLSurfaceView view2;
	private GLSurfaceView view3;
	private GLSurfaceView view4;
	private JHGLFrameRender mGLFrameRender;
	//手势
	private GestureDetectorCompat mDetector;
	private MyGestureListener mGesturelistener;
	//屏幕宽高
	private int screenW;
	private int screenH;
	//
	private ListView listView;
	private List<FileBean> mDatas = new ArrayList<FileBean>();
	private TreeListViewAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		//
		WindowManager wm = this.getWindowManager();
		Point p = new Point();
		wm.getDefaultDisplay().getSize(p);
		screenW = p.x;
		screenH = p.y;
		
		////////////////////////////
		mGLFrameRender = new JHGLFrameRender(view2);
		setupContentView();
		
		//单击、双击
		mGesturelistener = new MyGestureListener();
		mDetector = new GestureDetectorCompat(this, mGesturelistener);
		mDetector.setOnDoubleTapListener(mGesturelistener);
		
		
//		view = new MySurfaceView(getApplicationContext());
//		view.setEGLContextClientVersion(1);
	
//		render = new MyTextureRender(this.getResources());
//		view.setRenderer(render);
//		view.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		
//		setContentView(view);
	}
	
	private void setupContentView() {
		if (bFlag) {
			layout = (LinearLayout) getLayoutInflater().inflate(R.layout.split_four_screen_layout, null);
			setContentView(layout);
			view1 = (GLSurfaceView) findViewById(R.id.surface1);
			view2 = (GLSurfaceView) findViewById(R.id.surface2);
			view3 = (GLSurfaceView) findViewById(R.id.surface3);
			view4 = (GLSurfaceView) findViewById(R.id.surface4);
			view1.setRenderer(mGLFrameRender);
			view2.setRenderer(mGLFrameRender);
			view3.setRenderer(mGLFrameRender);
			view4.setRenderer(mGLFrameRender);
		} else {
			layout = (LinearLayout) getLayoutInflater().inflate(R.layout.full_screen_layout, null);
			setContentView(layout);
			view1 = (GLSurfaceView) findViewById(R.id.surface);
			view1.setRenderer(mGLFrameRender);
		}
		//
		listView = (ListView) findViewById(R.id.listView);
		initDatas();
		try {
			mAdapter = new SimpleTreeAdapter<FileBean>(listView, this, mDatas, 10);
			listView.setAdapter(mAdapter);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private void initDatas() {
		mDatas.clear();
		mDatas.add(new FileBean(1, 0, "文件管理系统"));
		mDatas.add(new FileBean(2, 1, "游戏"));
		mDatas.add(new FileBean(3, 1, "文档"));
		mDatas.add(new FileBean(4, 1, "程序"));
		mDatas.add(new FileBean(5, 2, "war3"));
		mDatas.add(new FileBean(6, 2, "刀塔传奇"));
		mDatas.add(new FileBean(7, 4, "面向对象"));
		mDatas.add(new FileBean(8, 4, "非面向对象"));
		mDatas.add(new FileBean(9, 7, "C++"));
		mDatas.add(new FileBean(10, 7, "Java"));
		mDatas.add(new FileBean(11, 7, "JavaScript"));
		mDatas.add(new FileBean(12, 8, "C"));
		mDatas.add(new FileBean(13, 8, "asm"));
		mDatas.add(new FileBean(14, 8, "a"));
		mDatas.add(new FileBean(15, 8, "b"));
		mDatas.add(new FileBean(16, 8, "ccc"));
	}
	
	/**
	 * 注销按钮处理
	 */
	public void onClickBtnLogout(View v) {
		Log.v(LOG_TAG, "注销。。。");
	}
	
	/**
	 * 单击及双击事件
	 */
	private class MyGestureListener extends SimpleOnGestureListener implements OnDoubleTapListener {
		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			//单击
			if (bFlag == false) {
				LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);
				if (View.VISIBLE == layout.getVisibility()) {
					int m = screenW - layout.getWidth();
					if (e.getX() <= m) {
						layout.setVisibility(View.GONE);
					}
				} else {
					layout.setVisibility(View.VISIBLE);
				}
			}
			return false;
		}

		@Override
		public boolean onDoubleTap(MotionEvent e) {
			//双击
			LinearLayout layout;
			if (bFlag) {
				layout = (LinearLayout) findViewById(R.id.linearLayout3);
			} else {
				layout = (LinearLayout) findViewById(R.id.linearLayout);
			}
			int m = screenW - layout.getWidth();
			if (e.getX() <= m) {
				bFlag = !bFlag;
				setupContentView();	
			}
			return false;
		}

		@Override
		public boolean onDoubleTapEvent(MotionEvent e) {
			return false;
		}		
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mDetector.onTouchEvent(event);
//		if (event.getAction() == MotionEvent.ACTION_DOWN) {
//			x0 = event.getX();
//			y0 = event.getY();
//		} else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_MOVE) {
//			float x1 = event.getX();
//			float y1 = event.getY();
//			float deltaX = Math.abs(x0 - x1);
//			float deltaY = Math.abs(y0 - y1);
//			if (deltaX == 0 && deltaY == 0) {
//				return super.onTouchEvent(event);
//			}
//			float step = 5f;
//			if (deltaX >= deltaY) {
//				if (x0 > x1) 
//					render.yrotate -= step;
//				else
//					render.yrotate += step;
//			} else {
//				if (y0 > y1)
//					render.xrotate -= step;
//				else
//					render.xrotate += step;
//			}
//			// 请求渲染，和脏渲染配合使用
//			view.requestRender();
//			x0 = x1;
//			y0 = y1;
//		}
		return super.onTouchEvent(event);
	}
	
//	public class MySurfaceView extends GLSurfaceView  {
//
//		public MySurfaceView(Context context) {
//			super(context);
//			// TODO Auto-generated constructor stub
//		}
//
//		public MySurfaceView(Context context, AttributeSet attrs) {
//			super(context, attrs);
//			// TODO Auto-generated constructor stub
//		}
//
//	}
}


//private String title[][] = new String[][]{
//{"学号", "姓名", "专业"},
//{"001", "张三", "多媒体"},
//{"002", "李四", "网络"}
//};
//TableLayout layout = new TableLayout(this);
//TableLayout.LayoutParams params = new TableLayout.LayoutParams(
//ViewGroup.LayoutParams.MATCH_PARENT, 
//ViewGroup.LayoutParams.MATCH_PARENT);
//for (int i = 0; i < title.length; i++) {
//TableRow row = new TableRow(this);
//for (int n = 0; n < title[i].length; n++) {
//TextView text = new TextView(this);
//text.setText(title[i][n]);
//row.addView(text);
//}
//layout.addView(row);
//}
//this.addContentView(layout, params);

/////////////////////////////
//EditTextTest ed = new EditTextTest(this);
//ed.setPadding(140, 0, 0, 0);
//RelativeLayout relativeLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_main, null);
//RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
//ViewGroup.LayoutParams.MATCH_PARENT, 
//ViewGroup.LayoutParams.MATCH_PARENT);
//layoutParams.addRule(RelativeLayout.BELOW, R.id.button1);
//relativeLayout.addView(ed, layoutParams);
//setContentView(relativeLayout);

///////////////////////////
//RelativeLayout relativelayout = new RelativeLayout(this);
//relativelayout.setBackgroundColor(Color.RED);
//setContentView(relativelayout);
//GLSurfaceView surfaceView1 = new GLSurfaceView(this);
//RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
//ViewGroup.LayoutParams.WRAP_CONTENT, 
//ViewGroup.LayoutParams.WRAP_CONTENT);
