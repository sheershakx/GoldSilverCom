package com.sheershakx.goldsilvercom;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
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
       // new getcontent().execute();
      //  Date currentDate = new Date(System.currentTimeMillis());
      //  Date date= Calendar.getInstance().getTime();
        String currentDateTimeString = (String) android.text.format.DateFormat.
                format("yyyy-MM-dd",Calendar.getInstance().getTime());
        textView.setText(currentDateTimeString);


    }

    public class getcontent extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {

            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet("http://fenegosida.org/");
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
             //  textView.setText(pagecontent);
                //for gold tola chapawal
                String trimmed=pagecontent.replace("(","");
                String trimmedagain=trimmed.replace(")","");
                Pattern pattern = Pattern.compile("<p>FINE GOLD 9999<br><span>per 1 tola<br><br>Nrs</span> <b>(.*?)</b>/-</p>", Pattern.DOTALL);
                Matcher matcher = pattern.matcher(trimmedagain);

////////////////////////////////////// Tejabi gold tola//////////////////////////////////////////////////
                Pattern pattern1 = Pattern.compile("<p>TEJABI GOLD<br><span>per 1 tola<br><br>Nrs</span> <b>(.*?)</b>/-</p>", Pattern.DOTALL);
                Matcher matcher1 = pattern1.matcher(pagecontent);
////////////////////////////////////Silver tola/////////////////////////////////////////////////
                Pattern pattern2 = Pattern.compile("<p>SILVER<br><span>per 1 tola<br><br>Nrs</span> <b>(.*?)</b>/-</p>", Pattern.DOTALL);
                Matcher matcher2 = pattern2.matcher(pagecontent);
//////////////////////////////////////////////////////////////////////////////////////



                while (matcher.find()) {
                    String newstring = matcher.group(1);
                    if (newstring != null) textView.setText(newstring);
                }

                while (matcher1.find()) {
                    String newstring = matcher1.group(1);
                    if (newstring != null) textView.setText(newstring);
                }

                while (matcher2.find()) {
                    String newstring = matcher2.group(1);
                    if (newstring != null) textView.setText(newstring);
                }
            }
        }
    }
}






