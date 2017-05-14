package com.pctrs.unitynotification;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;


import com.unity3d.player.UnityPlayer;

public class UnityNotifincationsActivity extends BroadcastReceiver {


    public static void GetNotification(int id,long delaytime, String title, String message, String ticker, int sound, int vibration, int lights, String SIconImage,String LIconImage, int bgColor, int executeMode, String UnityClassCallBack)
    {

        Activity UnityCurrentActivity = UnityPlayer.currentActivity;

        AlarmManager am = (AlarmManager)UnityCurrentActivity.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(UnityCurrentActivity,UnityNotifincationsActivity.class );
        intent.putExtra("ticker",ticker);
        intent.putExtra("id",id);
        intent.putExtra("UnityClassCallBack",UnityClassCallBack);
        intent.putExtra("title", title);
        intent.putExtra("message",message);
        intent.putExtra("sound",sound == 1);
        intent.putExtra("vibration",vibration == 1);
        intent.putExtra("lights",lights == 1);
        intent.putExtra("SIconImage",SIconImage);
        intent.putExtra("LIconImage",LIconImage);
        intent.putExtra("bgColor",bgColor);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if(executeMode == 2)
            {
                am.setExactAndAllowWhileIdle(0, System.currentTimeMillis() + delaytime, PendingIntent.getBroadcast(UnityCurrentActivity, id, intent, 0));
            }
            else if(executeMode == 1)
            {
                am.setExact(0, System.currentTimeMillis() + delaytime, PendingIntent.getBroadcast(UnityCurrentActivity, id, intent, 0));
            }
            else
            {
                am.set(0, System.currentTimeMillis() + delaytime, PendingIntent.getBroadcast(UnityCurrentActivity, id, intent, 0));
            }
        }
        else
        {
            am.set(0, System.currentTimeMillis() + delaytime, PendingIntent.getBroadcast(UnityCurrentActivity, id, intent, 0));
        }



    }

    public void onReceive(Context context, Intent intent)
    {

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        String ticker= intent.getStringExtra("ticker");
        String SIconImage= intent.getStringExtra("SIconImage");
        String LIconImage= intent.getStringExtra("LIconImage");
        int id = intent.getIntExtra("id",0);
        int bgColor = intent.getIntExtra("bgColor",0);
        String UnityClassCallBack = intent.getStringExtra("UnityClassCallBack");
        String title= intent.getStringExtra("title");
        String message = intent.getStringExtra("message");
        Boolean sound = intent.getBooleanExtra("sound", false);
        Boolean vibration = intent.getBooleanExtra("vibration", false);
        Boolean lights = intent.getBooleanExtra("lights", false);

        Resources res = context.getResources();
        Class<?> UnityClassActivity = null;
        try
        {
            UnityClassActivity = Class.forName(UnityClassCallBack);
        }
        catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        Intent notificationIntent = new Intent(context, UnityClassActivity);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);


        PendingIntent contentIntent = PendingIntent.getActivity(context, 0 ,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(contentIntent)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentText(message);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setColor(bgColor);
        }

        if(ticker != null && ticker.length() > 0)
        {
            builder.setTicker(ticker);
        }

        if(SIconImage != null && SIconImage.length() > 0)
        {
            builder.setSmallIcon(res.getIdentifier(SIconImage, "drawable", context.getPackageName()));
        }

        if(LIconImage != null && LIconImage.length() > 0)
        {
            builder.setLargeIcon(BitmapFactory.decodeResource(res,res.getIdentifier(LIconImage,"drawable",context.getPackageName())));
        }

        if(sound)
        {
            builder.setSound(RingtoneManager.getDefaultUri(2));
        }

        if(vibration)
        {
            builder.setVibrate(new long[]{1000L,1000L});
        }

        if(lights)
        {
            builder.setLights(Color.GREEN, 3000, 3000);
        }

        Notification notification = builder.build();
        notificationManager.notify(id,notification);


    }

}
