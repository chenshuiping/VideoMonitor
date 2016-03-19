package net.jhdt.videomonitor.Login;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import net.jhdt.videomonitor.R;
import net.jhdt.videomonitor.Socket.SocketManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
// 112.2.6.77  5680
public class LoginActivity extends Activity {
	private static final String LOG_TAG = "LoginActivity";
	private EditText tv_userName;
	private EditText tv_pass;
	private SocketChannel channel; //连接服务的通道
	
	public Handler handler = new Handler(new Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case 1: //connect连接成功
				Toast.makeText(LoginActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
				//验证用户的合法性
				break;
			case 2: //连接超时
				try {
					Toast.makeText(LoginActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
					channel.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case 3: //端口不存在，或者网络不通
				Toast.makeText(LoginActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
			return false;
		}
	});
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity_layout);
	}

	/**
	 * 登录按钮处理
	 * @param v
	 */
	public void onClickBtnLogin(View v) {
//		tv_userName = (EditText) findViewById(R.id.userName);
//		if (TextUtils.isEmpty(tv_userName.getText())) {
//			Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
//			return;
//		}
//		tv_pass = (EditText) findViewById(R.id.password);
//		if (TextUtils.isEmpty(tv_pass.getText())) {
//			Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
//			return;
//		}

		try {
			
			if (channel != null && channel.isOpen()) { 
				if (channel.isConnectionPending()) {
					Log.v(LOG_TAG, "正在连接中。。。");
					return;
				} else {
					channel.close();
				}
			}
			channel = SocketChannel.open();
			SocketManager sm = new SocketManager(handler, channel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		Intent intent = new Intent(this, MainActivity.class);
//		startActivity(intent);
//		int requestCode = 1;
//		startActivityForResult(intent, requestCode);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.v(LOG_TAG, "requestCode = " + requestCode);
	}
}
