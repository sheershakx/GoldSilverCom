package com.sheershakx.goldsilvercom;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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

public class addMontlyLebhi extends AppCompatActivity {
    Spinner year, month;
    String pan;
    EditText date, rashid, amount;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_montly_lebhi);

        //type-casting

        year = findViewById(R.id.spinner_year_lebhi);
        month = findViewById(R.id.spinner_month_lebhi);

        date = findViewById(R.id.date_lebhi);
        rashid = findViewById(R.id.rashidno_lebhi);
        amount = findViewById(R.id.amount_lebhi);

        save = findViewById(R.id.savebtn_lebhi);

        getIncomingIntent();

        //adapter declaration and setting adapter to respective spinners
        ArrayAdapter<String> adapterYear = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.year));
        adapterYear.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        year.setAdapter(adapterYear); // for year spinner

        ArrayAdapter<String> adapterMonth = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.month));
        adapterMonth.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        month.setAdapter(adapterMonth); // for month spinner

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Date = date.getText().toString();
                String Rashid = rashid.getText().toString();
                String Amount = amount.getText().toString();
                String Year = year.getSelectedItem().toString();
                Integer mpos = month.getSelectedItemPosition();

                if (!TextUtils.isEmpty(Date) && !TextUtils.isEmpty(Rashid) && !TextUtils.isEmpty(Amount)) {
                    new monthlyLebhi().execute(Year, Integer.toString(mpos), Amount, Date, Rashid);


                } else
                    Toast.makeText(addMontlyLebhi.this, "Please enter all details.", Toast.LENGTH_SHORT).show();
            }
        });


    }
   public void getIncomingIntent(){
       Intent intent=getIntent();
       if (intent.getStringExtra("pan")!=null){
           pan=intent.getStringExtra("pan");
       }

    }


    public class monthlyLebhi extends AsyncTask<String, String, String> {
        String db_url;

        @Override
        protected void onPreExecute() {
            db_url = "http://gsa123.000webhostapp.com/addLebhi.php";

        }

        @Override
        protected String doInBackground(String... params) {
            String year, month, amount, date, rashid;
            year = params[0];
            month = params[1];
            amount = params[2];
            date = params[3];
            rashid = params[4];


            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("year", "UTF-8") + "=" + URLEncoder.encode(year, "UTF-8") + "&" +
                        URLEncoder.encode("month", "UTF-8") + "=" + URLEncoder.encode(month, "UTF-8") + "&" +
                        URLEncoder.encode("amount", "UTF-8") + "=" + URLEncoder.encode(amount, "UTF-8") + "&" +
                        URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") + "&" +
                        URLEncoder.encode("pan", "UTF-8") + "=" + URLEncoder.encode(pan, "UTF-8") + "&" +
                        URLEncoder.encode("rashid", "UTF-8") + "=" + URLEncoder.encode(rashid, "UTF-8");
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
            Toast.makeText(addMontlyLebhi.this, "Added", Toast.LENGTH_SHORT).show();
            date.setText("");
            amount.setText("");
            rashid.setText("");
            finish();

        }
    }
}
