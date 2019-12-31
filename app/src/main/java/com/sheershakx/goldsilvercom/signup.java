package com.sheershakx.goldsilvercom;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class signup extends AppCompatActivity {
    String pagecontent;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        textView = findViewById(R.id.textview);
        new getcontent().execute();


    }

    public class getcontent extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://www.fenegosida.org");
            ResponseHandler<String> resHandler = new BasicResponseHandler();
            try {
                pagecontent = httpClient.execute(httpGet, resHandler);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (pagecontent != null) {
              // textView.setText(pagecontent);
                Pattern pattern = Pattern.compile("<p>TEJABI GOLD<br><span>per 10 grm<br><br>Nrs</span> <b>(.*?)</b>/-</p>", Pattern.DOTALL);
                Matcher matcher = pattern.matcher(pagecontent);
                while (matcher.find()) {
                    String newstring = matcher.group(1);
                    if (newstring != null) textView.setText(newstring);
                }
            }
        }
    }
}






