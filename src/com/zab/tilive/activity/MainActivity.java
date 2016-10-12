package com.zab.tilive.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.gotye.live.chat.Ack;
import com.gotye.live.chat.ChatObserver;
import com.gotye.live.chat.Code;
import com.gotye.live.chat.GLChatSession;
import com.gotye.live.chat.LoginAck;
import com.gotye.live.chat.Message;
import com.gotye.live.chat.SendMsgAck;
import com.gotye.live.player.GLPlayer;
import com.gotye.live.player.GLPlayer.Listener;
import com.gotye.live.player.GLSurfaceView;
import com.zab.tilive.R;
import com.zab.tilive.base.TILApplication;

@SuppressLint("ShowToast")
public class MainActivity extends Activity implements ChatObserver {
	private TextView tv;
	private Button btn;
	private EditText et;
	private boolean isLogin;
	private GLSurfaceView sv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i("TIL", "initChat");
		tv = (TextView) findViewById(R.id.tv);
		sv = (GLSurfaceView) findViewById(R.id.surface);
		btn = (Button) findViewById(R.id.btn);
		Button btn1 = (Button) findViewById(R.id.button1);
		btn1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this,LiveActivity.class));
			}
		});
		
		et = (EditText) findViewById(R.id.editText1);
		initChat();
		initVadio();

	}

	private void initVadio() {
		GLPlayer player = new GLPlayer(
				"http://live.gotlive.com.cn/share/live/cb98f35da5d92c9de8b8de18dc1aa617");
		player.setSurfaceView(sv);
		player.play();
		player.setListener(new MyListeners());
		// TILApplication.player.setListener(null);
		// TILApplication.player.setSurfaceView(sv);
		// TILApplication.player.play();

	}

	private void toast(String text) {
		Toast.makeText(getApplicationContext(), text, 0).show();
	}

	private void initChat() {
		Log.i("TIL", "initChat初始化");
		TILApplication.im.addObserver(this);
		TILApplication.im.login(new Ack<LoginAck>() {
			public void ack(LoginAck ack) {
				if (ack.getCode() == Code.SUCCESS) {
					toast("聊天室登录成功");
					isLogin = true;
				} else {
					toast("聊天室登录失败" + ack.getCode());
					isLogin = false;
				}
			}
		});
		btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final String text = et.getText().toString();
				if (isLogin) {
					// 发送信息
					TILApplication.im.sendText(text, new Ack<SendMsgAck>() {
						public void ack(SendMsgAck arg0) {
							if (arg0.getCode() == Code.SUCCESS) {
								toast("消息发送成功");
								tv.append("\n" + text);
							} else {
								toast("消息发送失败");

							}
						}
					});

				} else {
					toast("登录失败，不能发送信息");
				}
			}
		});

	}

	@Override
	public void onDisconnected(GLChatSession arg0) {

		toast("onDisconnected");

	}

	@Override
	public void onForceLogout(GLChatSession arg0) {
		toast("onForceLogout");

	}

	@Override
	public void onReceiveMessage(GLChatSession arg0, Message arg1) {
		toast("onReceiveMessage" + arg1.toString());
		tv.append("\n" + arg1.getSenderNickname() + ":" + arg1.getText());
	}

	@Override
	public void onReloginFailed(GLChatSession arg0) {
		// TODO Auto-generated method stub
		toast("onReloginFailed");
	}

	@Override
	public void onReloginSuccess(GLChatSession arg0) {
		toast("onReloginSuccess");
	}

	@Override
	public void onRelogining(GLChatSession arg0) {
		// TODO Auto-generated method stub
		toast("onRelogining");

	}

	@Override
	protected void onDestroy() {
		Log.i("TIL", "onDestroy");
		super.onDestroy();
		TILApplication.im.logout();
	}

	class MyListeners implements Listener {

		@Override
		public void onPlayerConnected(GLPlayer arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPlayerDisconnected(GLPlayer arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPlayerError(GLPlayer arg0, int arg1) {
			// TODO Auto-generated method stub
			Log.i("TIL",arg1+"错误代码");
		}

		@Override
		public void onPlayerReconnecting(GLPlayer arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onPlayerStatusUpdate(GLPlayer arg0) {
			// TODO Auto-generated method stub

		}
	}

}
