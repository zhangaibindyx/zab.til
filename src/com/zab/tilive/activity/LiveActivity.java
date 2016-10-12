package com.zab.tilive.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.gotye.live.publisher.Code;
import com.gotye.live.publisher.GLPublisher;
import com.gotye.live.publisher.GLRoomPublisher;
import com.gotye.live.publisher.GLVideoView;
import com.zab.tilive.R;
import com.zab.tilive.base.TILApplication;

public class LiveActivity extends Activity {
	private GLVideoView vv;
	private Button btnCra, btnLive, btnExit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_live);

		initView();
		initListener();

	}

	private void initView() {
		vv = (GLVideoView) findViewById(R.id.videoView1);
		btnCra = (Button) findViewById(R.id.button1);
		btnLive = (Button) findViewById(R.id.button2);
		btnExit = (Button) findViewById(R.id.button3);
		initLive();

	}

	private void initLive() {
		TILApplication.publisher.startPreview(vv, true,
				new GLPublisher.PreviewCallback() {
					public void onCameraOpen(boolean arg0, boolean arg1) {
						Log.i("TIL", "oncameropen==" + arg0);
					}
				});
	}

	private void initListener() {
		btnCra.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				initLive();
			}
		});
		btnLive.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TILApplication.publisher.login(true,
						new GLRoomPublisher.Callback<Void>() {
							public void onCallback(int arg0, Void arg1) {
								if (arg0 == Code.OCCUPIED) {
								} else if (arg0 == Code.SUCCESS) {
									Log.i("TIL", "SUCCESS==" + arg0);
									TILApplication.publisher.publish();
								} else {
									Log.i("TIL", "Ê§°Ü==" + arg0);

								}
							}
						});
			}
		});
		
		
		btnExit.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				TILApplication.publisher.stop();
				Log.i("TIL", "ÍË³öÖ±²¥");
				
			}
		});
	}

}
