package hu.home.skot92.smartlamp;

import android.app.Activity;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.widget.RemoteViews;
import android.widget.TextView;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.onsets.OnsetHandler;
import be.tarsos.dsp.onsets.PercussionOnsetDetector;
import be.tarsos.dsp.pitch.PitchDetectionHandler;
import be.tarsos.dsp.pitch.PitchDetectionResult;
import be.tarsos.dsp.pitch.PitchProcessor;


public class MainActivity extends Activity {

    String message;
    String ip;
    SocketClient socketClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AudioDispatcher dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(22050,1024,0);
        final RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.simple_widget);

        double threshold = 8;
        double sensitivity = 20;

        PercussionOnsetDetector mPercussionDetector = new PercussionOnsetDetector(22050, 1024,
                new OnsetHandler() {

                    @Override
                    public void handleOnset(double time, double salience) {
                        Log.d("sound", "Clap detect" );
                        socketClient = new SocketClient();
                        String number =  socketClient.read_message_button();
                        if(number.equals("on")) {
                            socketClient.send_message("on");
                            remoteViews.setTextViewText(R.id.actionButton, "on");

                        }
                        else {
                            socketClient.send_message("off");
                            remoteViews.setTextViewText(R.id.actionButton, "off");
                        }
                        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getApplicationContext());
                        int[] ids = appWidgetManager.getAppWidgetIds(new ComponentName(getApplicationContext(), SimpleWidgetProvider.class));

                        Intent intent = new Intent(getApplicationContext(),SimpleWidgetProvider.class);
                        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

                        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ids);
                        sendBroadcast(intent);

                    }
                }, sensitivity, threshold);

        dispatcher.addAudioProcessor(mPercussionDetector);
        new Thread(dispatcher,"Audio Dispatcher").start();



    }

}
