package hu.home.skot92.smartlamp;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutionException;

/**
 * Created by skot92 on 2017.11.14..
 */

public class Pi {


    public String getLampStatus(){
        String status = null;
        try {
            status = new HttpRequestTask("http://192.168.1.3:8080/pi/getpinstate").execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        status = status.substring(1);
        status = status.substring(0,status.length()-1);
        return status;

    }

    public String switchLamp(){
        String status = null;
        try {
            status = new HttpRequestTask("http://192.168.1.3:8080/pi/lampswitch").execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return status;
    }


    private class HttpRequestTask extends AsyncTask<Void, Void, String> {

        private String url;

        public HttpRequestTask(String url) {
            super();
            this.url = url;
        }
        @Override
        protected String doInBackground(Void... params) {
            try {
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                String greeting = restTemplate.getForObject(url, String.class);
                return greeting;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

    }
}
