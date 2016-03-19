package net.jhdt.videomonitor.Login;

import net.jhdt.videomonitor.MainActivity;
import net.jhdt.videomonitor.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
// 112.2.6.77  5680
public class LoginActivity extends Activity {
	private static final String LOG_TAG = "LoginActivity";
	private EditText tv_userName;
	private EditText tv_pass;
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
		tv_userName = (EditText) findViewById(R.id.userName);
		if (TextUtils.isEmpty(tv_userName.getText())) {
			Toast.makeText(this, "请输入用户名", Toast.LENGTH_SHORT).show();
			return;
		}
		tv_pass = (EditText) findViewById(R.id.password);
		if (TextUtils.isEmpty(tv_pass.getText())) {
			Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		int requestCode = 1;
		startActivityForResult(intent, requestCode);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.v(LOG_TAG, "requestCode = " + requestCode);
	}
	
}
