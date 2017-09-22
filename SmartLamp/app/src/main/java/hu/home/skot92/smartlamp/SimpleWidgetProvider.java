package hu.home.skot92.smartlamp;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.RemoteViews;
import android.widget.ToggleButton;

import java.util.Random;

public class SimpleWidgetProvider extends AppWidgetProvider {


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        final int count = appWidgetIds.length;

        String message;
        String ip;
        SocketClient socketClient;

        socketClient = new SocketClient();


        for (int i = 0; i < count; i++) {
            int widgetId = appWidgetIds[i];
            String number =  socketClient.read_message_button();

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.simple_widget);

            Log.d("diget",number);

            remoteViews.setTextViewText(R.id.actionButton, number);


            if(number.equals("on")) {
                socketClient.send_message("on");

            }
            else {
                socketClient.send_message("off");
            }




            Intent intent = new Intent(context, SimpleWidgetProvider.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, appWidgetIds);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                    0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setOnClickPendingIntent(R.id.actionButton, pendingIntent);
            appWidgetManager.updateAppWidget(widgetId, remoteViews);
        }
    }
}
