package com.sheershakx.goldsilvercom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class memberdetails extends AppCompatActivity {
    TextView firmname, address, proname, memid, pan, txtview;
    ImageView call, sms, addLebhi;
    String queryuser;
    ProgressDialog progressDialog;
    String Fname, Faddress, Fproname, Fmobile, Fpan, Fmemid;
    String id;
    //
    String year, month, date, amount, rashid;

    ArrayList<String> Year = new ArrayList<String>();
    ArrayList<String> Month = new ArrayList<String>();
    ArrayList<String> Date = new ArrayList<String>();
    ArrayList<String> Amount = new ArrayList<String>();
    ArrayList<String> Rashid = new ArrayList<String>();
    //
    TableLayout tableLayout;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memberdetails);
        fetchIntent();
        firmname = findViewById(R.id.firmname_d);
        address = findViewById(R.id.address_d);
        proname = findViewById(R.id.proname_d);
        memid = findViewById(R.id.memid_d);
        pan = findViewById(R.id.pan_d);

        tableLayout = findViewById(R.id.tbllayout4);
        recyclerView = findViewById(R.id.recyclerview_memdetails);

        tableLayout.setVisibility(View.GONE);
        recyclerView.setVisibility(View.GONE);

        txtview = findViewById(R.id.txtviewlebhi);
        txtview.setVisibility(View.GONE);

        call = findViewById(R.id.callnow);
        sms = findViewById(R.id.sms);
        addLebhi = findViewById(R.id.add_lebhi_icon);
        addLebhi.setVisibility(View.GONE);



        if (login.usertype.equals("1")) {
            addLebhi.setVisibility(View.VISIBLE);
            txtview.setVisibility(View.VISIBLE);
            tableLayout.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Fmobile != null) {
                    String temp = "tel:" + Fmobile;
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(temp));
                    startActivity(intent);
                } else
                    Toast.makeText(memberdetails.this, "Mobile number is empty", Toast.LENGTH_SHORT).show();
            }
        });

        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = Fmobile;  // The number on which you want to send SMS
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", number, null)));
            }
        });

        addLebhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(memberdetails.this, addMontlyLebhi.class);
                intent.putExtra("pan", Fpan.trim());
                memberdetails.this.startActivity(intent);
            }
        });

    }

    public class getMonthlyLebhi extends AsyncTask<String, String, String> {
        String db_url;


        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(memberdetails.this, "", "Loading..", true);
            //   Toast.makeText(newsFeed.this, "Loading........", Toast.LENGTH_SHORT).show();
            db_url = "http://gsa123.000webhostapp.com/getLebhi.php";
            queryuser = "SELECT year,month,paymentDate,amount,rashid from lebhi where pan like '" + Fpan + "'";

        }

        @Override
        protected String doInBackground(String... args) {



            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("query", "UTF-8") + "=" + URLEncoder.encode(queryuser, "UTF-8");
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
            RecyclerView recyclerView = findViewById(R.id.recyclerview_memdetails);
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            recyclerView.setAdapter(new adapterLebhi(Date, Year, Month, Amount, Rashid));

        }
    }

    private void fetchIntent() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        new getmember().execute();
    }


    public class getmember extends AsyncTask<String, String, String> {
        String db_url;

        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(memberdetails.this, "", "Loading details", true);

            db_url = "http://gsa123.000webhostapp.com/getmemberdetail.php";

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("memid", "UTF-8") + "=" + URLEncoder.encode(id, "UTF-8");

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

                JSONObject jsonObject = new JSONObject(data);
                Fname = jsonObject.getString("fname");
                Faddress = jsonObject.getString("faddress");
                Fproname = jsonObject.getString("fproname");
                Fmobile = jsonObject.getString("fmobile");
                Fpan = jsonObject.getString("fpan");
                Fmemid = jsonObject.getString("fmemid");


//
                return data;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }


        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            firmname.setText(Fname);
            address.setText(Faddress);
            proname.setText(Fproname);
            memid.setText(Fmemid);
            pan.setText(Fpan);
            new getMonthlyLebhi().execute();

        }
    }
}
