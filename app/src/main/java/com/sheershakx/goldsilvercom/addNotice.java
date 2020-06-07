package com.sheershakx.goldsilvercom;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class addNotice extends AppCompatActivity {
    EditText noticeinput;
    Button savenotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_notice);


        noticeinput = findViewById(R.id.inputNotice);
        savenotice = findViewById(R.id.saveNotice);

        savenotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String noticeString = noticeinput.getText().toString();
                if (noticeString != null && !TextUtils.isEmpty(noticeString)) {
                    new addnotice().execute(noticeString);
                } else
                    Toast.makeText(addNotice.this, "Notice cannot be left blank", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class addnotice extends AsyncTask<String, String, String> {
        String db_url;

        @Override
        protected void onPreExecute() {
            Toast.makeText(addNotice.this, "Loading........", Toast.LENGTH_SHORT).show();
            db_url = "http://gsa123.000webhostapp.com/addnotice.php";

        }

        @Override
        protected String doInBackground(String... params) {
            String notice;
            notice = params[0];

            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("notice", "UTF-8") + "=" + URLEncoder.encode(notice, "UTF-8") + "&" +
                        URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(login.nepalidate, "UTF-8") + "&" +
                        URLEncoder.encode("uid", "UTF-8") + "=" + URLEncoder.encode(login.userid, "UTF-8");
                bufferedWriter.write(data_string);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                StringBuffer buffer = new StringBuffer();
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);

                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

//
                return null;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//

            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(addNotice.this, "Notice added", Toast.LENGTH_SHORT).show();
            finish();

        }
    }
}
