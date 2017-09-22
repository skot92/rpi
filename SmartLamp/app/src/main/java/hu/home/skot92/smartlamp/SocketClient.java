package hu.home.skot92.smartlamp;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.widget.Button;
import android.widget.ToggleButton;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import static android.R.id.toggle;

/**
 * Created by skot9 on 2017. 09. 06..
 */

public class SocketClient {

    private String ip = "192.168.1.3";
    private int port = 9999;


    public String read_message_button() {
        final String[] res = {"network error"};
        Log.d("in","read method");
        Thread t_socket = new Thread() {

            @Override
            public void run() {
                try {

                    java.net.Socket s = new java.net.Socket(ip, port);

                    OutputStream os = s.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os);
                    BufferedWriter bw = new BufferedWriter(osw);
                    bw.write("read");
                    bw.flush();


                    DataInputStream dis2 = new DataInputStream(s.getInputStream());
                    Log.d("din","is");
                    InputStreamReader disR2 = new InputStreamReader(dis2);
                    Log.d("in","is");
                    BufferedReader br = new BufferedReader(disR2);
                    final String tmp = br.readLine();
                    res[0] = tmp;
                    Log.d("read",tmp);




                    dis2.close();
                    s.close();


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        t_socket.start();
        try {
            t_socket.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return res[0];
    }

    public String read_message(final ToggleButton toggle, final Activity activity) {
        String res = "";
        Log.d("in","read method");
        Thread t_socket = new Thread() {

            @Override
            public void run() {
                try {

                    java.net.Socket s = new java.net.Socket(ip, port);

                    OutputStream os = s.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os);
                    BufferedWriter bw = new BufferedWriter(osw);
                    bw.write("read");
                    bw.flush();


                    DataInputStream dis2 = new DataInputStream(s.getInputStream());
                    Log.d("din","is");
                    InputStreamReader disR2 = new InputStreamReader(dis2);
                    Log.d("in","is");
                    BufferedReader br = new BufferedReader(disR2);
                    final String tmp = br.readLine();
                    Log.d("read",tmp);

                    dis2.close();
                    s.close();
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if(tmp.equals("on")) {
                                toggle.setChecked(true);
                            }
                            else{
                                toggle.setChecked(false);
                            }
                        }
                    });

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        t_socket.start();
        return res;
    }

    public void send_message(final String message) {

        Thread t_socket = new Thread() {

            @Override
            public void run() {
                try {


                    java.net.Socket s = new java.net.Socket(ip, 9999);

                    OutputStream os = s.getOutputStream();
                    OutputStreamWriter osw = new OutputStreamWriter(os);
                    BufferedWriter bw = new BufferedWriter(osw);
                    bw.write(message);
                    bw.flush();

                    s.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        t_socket.start();
    }
}
