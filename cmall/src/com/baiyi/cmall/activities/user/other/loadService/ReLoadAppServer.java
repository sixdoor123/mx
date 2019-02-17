package com.baiyi.cmall.activities.user.other.loadService;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.baiyi.cmall.activities.main.home_pager.MainActivity;
import com.baiyi.cmall.R;

/**
 * 
 * @author sunxy
 * @label You can go as far as you want to go.
 * @date 2016-3-8 ����5:46:46
 */
public class ReLoadAppServer extends Service {
	// ��ȡ��Ϣ�߳�
	private MessageThread messageThread = null;

	// ����鿴
	private Intent messageIntent = null;
	private PendingIntent messagePendingIntent = null;

	// ֪ͨ����Ϣ
	private int messageNotificationID = 1000;
	private Notification messageNotification = null;
	private NotificationManager messageNotificatioManager = null;

	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// ��ʼ��
		messageNotification = new Notification();
		messageNotification.icon = R.drawable.app_icon;
		messageNotification.tickerText = "����-Dmall";
		messageNotification.defaults = Notification.DEFAULT_SOUND;
		messageNotification.flags |= Notification.FLAG_AUTO_CANCEL;
		messageNotificatioManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

		messageIntent = new Intent(this, MainActivity.class);
		messagePendingIntent = PendingIntent.getActivity(this, 0,
				messageIntent, 0);

		// �����߳�
		messageThread = new MessageThread();
		messageThread.isRunning = true;
		messageThread.start();

		return super.onStartCommand(intent, flags, startId);
	}

	/**
	 * �ӷ������˻�ȡ��Ϣ
	 * 
	 */
	class MessageThread extends Thread {
		// �����Ƿ�ѭ������
		public boolean isRunning = true;

		@SuppressWarnings("deprecation")
		public void run() {
			// while (isRunning) {
			try {
				// ���ʱ��
				Thread.sleep(1000);
				// ��ȡ��������Ϣ
				String serverMessage = getServerMessage();
				if (serverMessage != null && !"".equals(serverMessage)) {
					// ����֪ͨ��
					messageNotification.setLatestEventInfo(
							getApplicationContext(), "����Ϣ", "��������CMALL_App"
									+ serverMessage, messagePendingIntent);
					messageNotificatioManager.notify(messageNotificationID,
							messageNotification);

					// ÿ��֪ͨ�֪꣬ͨID����һ�£�������Ϣ���ǵ�
					messageNotificationID++;
					ReLoadAppServer.this.stopSelf();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// }
		}
	}

	@Override
	public void onDestroy() {
		// System.exit(0);
		messageThread.isRunning = false;
		super.onDestroy();
	}

	/**
	 * ģ�ⷢ����Ϣ
	 * 
	 * @return ���ط�����Ҫ���͵���Ϣ���������Ϊ�յĻ���������
	 */
	public String getServerMessage() {
		return " ";
	}
}
