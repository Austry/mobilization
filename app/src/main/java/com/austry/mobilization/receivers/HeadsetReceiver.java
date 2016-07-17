package com.austry.mobilization.receivers;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.RemoteViews;

import com.austry.mobilization.R;

public class HeadsetReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = "HeadsetReceiver";

    private static final String MUSIC_PACKAGE = "ru.yandex.music";
    private static final String RADIO_PACKAGE = "ru.yandex.radio";
    private static final String MARKET_DETAILS_URI_ROOT = "market://details?id=";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (headsetPluggedIn(intent)) {
            RemoteViews customNotification = new RemoteViews(context.getPackageName(), R.layout.notification_music);

            initButtonListener(context, customNotification, R.id.btnYaMusic, MUSIC_PACKAGE);
            initButtonListener(context, customNotification, R.id.btnYaRadio, RADIO_PACKAGE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

            builder.setSmallIcon(R.drawable.rock_centered)
                    .setAutoCancel(true)
                    .setTicker(context.getString(R.string.notification_ticker));

            builder.setContent(customNotification);

            NotificationManagerCompat manager = NotificationManagerCompat.from(context);

            manager.notify(1, builder.build());
        }

    }

    private void initButtonListener(Context context, RemoteViews views, int buttonId, String packageName) {
        Intent buttonClickIntent = null;
        if (isInstalled(context, packageName)) {
            buttonClickIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        } else {
            buttonClickIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_DETAILS_URI_ROOT + packageName));
        }

        views.setOnClickPendingIntent(buttonId,
                PendingIntent.getActivity(context, 0, buttonClickIntent, PendingIntent.FLAG_CANCEL_CURRENT));
    }

    private boolean isInstalled(Context context, String packageName) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException ignored) {
        }
        return info != null;
    }

    private boolean headsetPluggedIn(Intent intent) {
        return intent.getIntExtra("state", -1) == 1;
    }
}
