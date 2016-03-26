package net.jhdt.videomonitor.Login;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import net.jhdt.videomonitor.R;
import net.jhdt.videomonitor.Socket.SocketManager;
import net.jhdt.videomonitor.Socket.TCPClient;
import net.jhdt.videomonitor.Socket.Model.CmdName;
import net.jhdt.videomonitor.Socket.Model.FKInfo;
import net.jhdt.videomonitor.Socket.Model.ProtocolHead;
import net.jhdt.videomonitor.Socket.Util.SocketUtil;
import net.jhdt.videomonitor.util.ByteUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
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

	private SocketChannel channel; //连接服务的通道
	private SocketManager sm;

	private TCPClient client;//

	public Handler handler = new Handler(new Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			if (msg.what == -1) { //connect连接有错
				Toast.makeText(getApplicationContext(), (String)msg.obj, Toast.LENGTH_LONG).show();
			} else if (msg.what == 1) { //接收到数据
				ByteBuffer[] obj = (ByteBuffer[]) msg.obj;
				ByteBuffer headBuf = ByteBuffer.allocate(ProtocolHead.length());
				ByteBuffer bodyBuf = ByteBuffer.allocate(1024);
				headBuf.put(obj[0]);
				bodyBuf.put(obj[1]);
				headBuf.flip();
				bodyBuf.flip();
				ProtocolHead headData = ProtocolHead.byte2Data(headBuf);
				if (ProtocolHead.isValidHead(headData.consNID)) {
					if (headData.cmdID == CmdName.ccConnect) {
						//注册
						byte[] subBody = new byte[headData.dataLen];
						bodyBuf.get(subBody, 0, subBody.length);
						ByteUtil.reverseByteOrder(subBody);
						if (ByteUtil.byte2int(subBody) == 1) {
							Log.v(LOG_TAG, "注册成功");
						} else {
							Log.v(LOG_TAG, "注册失败");
						}
					}
				}
			}

			//			switch (msg.what) {
			//			case 1: //connect连接成功
			//				Toast.makeText(LoginActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
			//				//验证用户合法性
			//				//填充头数
			//				//发送
			//				Thread thread = new Thread(new Runnable() {
			//
			//					@Override
			//					public void run() {
			//						ProtocolHead head = SocketUtil.ProtocolHeadUpdata(CmdName.ccConnect, FKInfo.length());
			//						//填充剩余数据
			//						FKInfo fkInfo = SocketUtil.FKInfoUpdata(tv_userName.getText().toString(), tv_pass.getText().toString());
			//						//打印发送的数据
			//						//				Log.v(LOG_TAG, head.toString());
			//						//				Log.v(LOG_TAG, fkInfo.toString());
			//						ByteBuffer bb = ByteBuffer.allocateDirect(ProtocolHead.length() + FKInfo.length());
			//						Log.v(LOG_TAG, "beforePosition = " + bb.position());
			//						bb.put(head.data2Byte());
			//						bb.put(fkInfo.data2Byte());
			//						Log.v(LOG_TAG, "afterPosition = " + bb.position());
			//						try {
			//							bb.flip();
			//							while(bb.hasRemaining())
			//								channel.write(bb);
			//							Log.v(LOG_TAG, "write finish....");
			//							//
			//							Selector selector = Selector.open();
			//							channel.register(selector, SelectionKey.OP_READ);
			//							ByteBuffer bf = ByteBuffer.allocate(1024);
			//							while (true) {
			//								int s = selector.select();
			//								if (s == 0) {
			//									Log.v(LOG_TAG, "continue....");
			//									continue;
			//								} else {
			//									Log.v(LOG_TAG, "s = " + s);
			//								}
			//								Set set = selector.keys();
			//								Iterator keyIterator = set.iterator();
			//								while (keyIterator.hasNext()) {
			//									SelectionKey object = (SelectionKey) keyIterator.next();
			//									if (object.isReadable()) {
			//										Log.v(LOG_TAG, "读到数据了。。。。。。。。。。");
			//										SocketChannel channel = (SocketChannel)object.channel();
			//										channel.read(bf);
			//										Log.v(LOG_TAG, bf.toString());
			//										bf.clear();
			//									}
			//									keyIterator.remove();
			//									
			//								}
			//							}
			//						} catch (IOException e1) {
			//							// TODO Auto-generated catch block
			//							e1.printStackTrace();
			//						}
			//					}
			//				});
			//				break;
			//			case 2: //连接超时
			//				try {
			//					Toast.makeText(LoginActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
			//					channel.close();
			//				} catch (IOException e) {
			//					// TODO Auto-generated catch block
			//					e.printStackTrace();
			//				}
			//				break;
			//			case 3: //端口不存在或者网络不通
			//				Toast.makeText(LoginActivity.this, msg.obj.toString(), Toast.LENGTH_SHORT).show();
			//				break;
			//			default:
			//				break;
			//			}
			return false;
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity_layout);
		Log.v(LOG_TAG, "onCreate");
		client = new TCPClient("112.2.6.77", 5680, handler);
		// 连接中心
		client.ConnectToHost();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.v(LOG_TAG, "onStart");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.v(LOG_TAG, "onResume");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.v(LOG_TAG, "onPause");
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		Log.v(LOG_TAG, "onStop");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		Log.v(LOG_TAG, "onRestart");
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Log.v(LOG_TAG, "onDestroy");
		System.exit(0);
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

		ProtocolHead head = SocketUtil.ProtocolHeadUpdata(CmdName.ccConnect, FKInfo.length());
		ByteBuffer headerBuf = ByteBuffer.allocate(ProtocolHead.length());
		headerBuf.put(head.data2Byte());

		FKInfo body = SocketUtil.FKInfoUpdata(tv_userName.getText().toString(), tv_pass.getText().toString());
		ByteBuffer bodyBuf = ByteBuffer.allocate(FKInfo.length());
		bodyBuf.put(body.data2Byte());

		ByteBuffer[] bufferArray = {headerBuf, bodyBuf};

		headerBuf.flip();
		bodyBuf.flip();

		client.SendDataToHost(bufferArray);


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
