package com.sheershakx.goldsilvercom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

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

public class memberdetails extends AppCompatActivity {
    TextView firmname, address, proname, memid, pan;
    ImageView call, sms;
    ProgressDialog progressDialog;
    String Fname, Faddress, Fproname, Fmobile, Fpan, Fmemid;
    String id;

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

        call = findViewById(R.id.callnow);
        sms = findViewById(R.id.sms);

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


        }
    }
}
