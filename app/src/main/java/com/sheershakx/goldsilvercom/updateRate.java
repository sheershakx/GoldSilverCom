package com.sheershakx.goldsilvercom;

import android.content.Intent;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;

public class updateRate extends AppCompatActivity {
    Button updatebtn;
    EditText finegold, tejgold, silver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_rate);

        updatebtn = findViewById(R.id.update_liverate);
        finegold = findViewById(R.id.update_finegold);
        tejgold = findViewById(R.id.update_tejgold);
        silver = findViewById(R.id.update_silver);

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fine = finegold.getText().toString();
                String tej = tejgold.getText().toString();
                String Silver = silver.getText().toString();

                if (!TextUtils.isEmpty(fine) && !TextUtils.isEmpty(tej) && !TextUtils.isEmpty(Silver)) {
                    new updaterate().execute(fine,tej, Silver);

                } else
                    Toast.makeText(updateRate.this, "Please enter values in fields", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public class updaterate extends AsyncTask<String, String, String> {
        String db_url;

        @Override
        protected void onPreExecute() {
            Toast.makeText(updateRate.this, "Loading........", Toast.LENGTH_SHORT).show();
            db_url = "http://gsa123.000webhostapp.com/update_rate.php";

        }

        @Override
        protected String doInBackground(String... params) {
            String finegold, tejgold, silver;
            finegold = params[0];
            tejgold = params[1];
            silver = params[2];



            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("finegold", "UTF-8") + "=" + URLEncoder.encode(finegold, "UTF-8") + "&" +
                        URLEncoder.encode("tejgold", "UTF-8") + "=" + URLEncoder.encode(tejgold, "UTF-8") + "&" +
                        URLEncoder.encode("silver", "UTF-8") + "=" + URLEncoder.encode(silver, "UTF-8") + "&" +
                        URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(login.nepalidate, "UTF-8");
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
            Toast.makeText(updateRate.this, "Rate Updated", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(updateRate.this, dashboard.class);
            finish();
            overridePendingTransition(0, 0);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            overridePendingTransition(0, 0);

        }
    }

}
