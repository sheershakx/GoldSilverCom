package com.sheershakx.goldsilvercom;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class monthlyLebhi extends AppCompatActivity {
    ProgressDialog progressDialog;
    String year, month, date, amount, rashid;

    String queryadmin, queryuser;
    ArrayList<String> Year = new ArrayList<String>();
    ArrayList<String> Month = new ArrayList<String>();
    ArrayList<String> Date = new ArrayList<String>();
    ArrayList<String> Amount = new ArrayList<String>();
    ArrayList<String> Rashid = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthly_lebhi);

        queryadmin = "SELECT year,month,paymentDate,amount,rashid from lebhi ";
        queryuser = "SELECT year,month,paymentDate,amount,rashid from lebhi where pan like '" + login.pan + "'";
        if (login.usertype.equals("1")) {
            new getMonthlyLebhi().execute(queryadmin);
        } else {
            new getMonthlyLebhi().execute(queryuser);
        }

    }

    public class getMonthlyLebhi extends AsyncTask<String, String, String> {
        String db_url;


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(monthlyLebhi.this, "", "Loading..", true);
            //   Toast.makeText(newsFeed.this, "Loading........", Toast.LENGTH_SHORT).show();
            db_url = "http://gsa123.000webhostapp.com/getLebhi.php";




        }

        @Override
        protected String doInBackground(String... args) {
            String query;
            query=args[0];

            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(query, "UTF-8");
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
                String data = stringBuilder.toString().trim();

                String json;

                InputStream stream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
                int size = stream.available();
                byte[] buffer1 = new byte[size];
                stream.read(buffer1);
                stream.close();

                json = new String(buffer1, "UTF-8");
                JSONArray jsonArray = new JSONArray(json);

                for (int i = 0; i <= jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    if (jsonObject.getString("year") != null) {

                        date = jsonObject.getString("paymentDate");
                        year = jsonObject.getString("year");
                        month = jsonObject.getString("month");
                        amount = jsonObject.getString("amount");
                        rashid = jsonObject.getString("rashid");

                        Date.add(date);
                        Year.add(year);
                        Month.add(month);
                        Amount.add(amount);
                        Rashid.add(rashid);


                    }
                }


                return null;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
//
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            RecyclerView recyclerView = findViewById(R.id.recyclerview_lebhi);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(new adapterLebhi(Date, Year, Month, Amount, Rashid));

        }
    }
}
