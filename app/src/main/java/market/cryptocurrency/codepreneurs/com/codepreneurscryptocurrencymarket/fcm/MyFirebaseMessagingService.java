package market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.fcm;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import market.cryptocurrency.codepreneurs.com.codepreneurscryptocurrencymarket.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String notificationText = "";
        if(remoteMessage.getData().containsKey("notification"))
        notificationText = remoteMessage.getData().get("notification");


        Log.d(TAG, "Notification: " + notificationText);
        String notificationBody = "";
        if (remoteMessage.getNotification() != null)
            notificationBody = remoteMessage.getNotification().getBody();

        Intent i = new Intent("android.intent.action.MAIN").
                putExtra("notification", notificationText).
                putExtra("notificationBody", notificationBody);
        this.sendBroadcast(i);
        Log.d("remotemessage", notificationBody);
    }
}