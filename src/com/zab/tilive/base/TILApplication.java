package com.zab.tilive.base;

import android.app.Application;
import android.util.DisplayMetrics;
import android.util.Log;

import com.gotye.live.chat.GLChatSession;
import com.gotye.live.core.Code;
import com.gotye.live.core.GLCore;
import com.gotye.live.core.GLRoomSession;
import com.gotye.live.core.GLRoomSession.Callback;
import com.gotye.live.core.model.AuthToken;
import com.gotye.live.core.model.RoomIdType;
import com.gotye.live.player.GLRoomPlayer;
import com.gotye.live.publisher.GLRoomPublisher;

public class TILApplication extends Application {
	/** 聊天回话 **/
	public static GLChatSession im;
	/** 房间会话 */
	public static GLRoomSession roomSession;
	/** 房间发起人 */
	public static GLRoomPublisher publisher;
	/*** 显示指标 */
	public static DisplayMetrics metric;
	/** 房间播放者 */
	public static GLRoomPlayer player;
	private static final String ACCESS_KEY = "eeb7894a90544178bcb829225c73dd46";
	private static final String APPKEY = "b50ed22d34d14a3893565cdaba93f7b9";
	private static final String TNL_CODE = "1c839df4e6da4971ad00407dcac264dc";
	/** 房间号 */
	private static final String ROOM_ID = "220163";
	/** 直播昵称 */
	private static final String NICK_NAME = "tarena_zab";
	/** 直播密码 */
	private static final String PWD = "JE7InY";

	@Override
	public void onCreate() {
		super.onCreate();

		// 初始化直播SDK
		try {

			GLCore.registerApp(this, APPKEY, ACCESS_KEY, TNL_CODE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		initSession();
	}

	/***
	 * 初始化会话机制并连接服务器
	 */
	public static void initSession() {

		roomSession = new GLRoomSession();
		roomSession.setRoomId(ROOM_ID);
		roomSession.setNickName(NICK_NAME);
		roomSession.setPwd(PWD);
		roomSession.setRoomIdType(RoomIdType.GOTYE);
		roomSession.auth(new Callback<AuthToken>() {
			public void onFinish(int arg0, AuthToken arg1) {
				if(arg0==Code.SUCCESS){
					Log.i("TIL", "SUCCESS");
				}else{
					
					Log.i("TIL", "失败"+arg0+arg1.toString());
				}
			}
		});
		im = new GLChatSession(roomSession);
		publisher = new GLRoomPublisher(roomSession);
		player = new GLRoomPlayer(roomSession);

	}

	/***
	 * 退出直播間
	 */
	public static void exitRoom() {
		if (roomSession != null) {
			GLCore.destroySession(roomSession);
		}
	}
}
