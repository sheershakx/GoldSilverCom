package com.sheershakx.goldsilvercom;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class liverate_fragment extends Fragment {
    TextView marqueetext;
    TextView currDate;
    String finegold, tejgold, silver;
    TextView fineview, tejview, silview;
    TextView finefene, tejabifene, silverfene;
    String pagecontent;
    //  FloatingActionButton floatingActionButton;
    ImageView aboutus;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_liverate_fragment, container, false);

        fineview = view.findViewById(R.id.finegold_view);
        tejview = view.findViewById(R.id.tejgold_view);
        silview = view.findViewById(R.id.silver_view);

        finefene = view.findViewById(R.id.finegold_fene);
        tejabifene = view.findViewById(R.id.tejabligold_fene);
        silverfene = view.findViewById(R.id.silver_fene);


        new getRate().execute();
        new getcontent().execute();
        marqueetext = view.findViewById(R.id.marqueetextview);
        Toolbar toolbar = view.findViewById(R.id.toolbar_livefragment);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);

        marqueetext.setSelected(true);

        currDate = view.findViewById(R.id.currdate);


            currDate.setText(login.nepalidate);

        aboutus = view.findViewById(R.id.aboutus);
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), aboutUs.class));

            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuInflater inflatera = ((AppCompatActivity) getActivity()).getMenuInflater();
        if (login.usertype!=null && login.usertype.equals("1")) {
            inflatera.inflate(R.menu.toolbar_menu, menu);
        } else {
            inflatera.inflate(R.menu.toolbar_usermenu, menu);
        }
        return;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        if (login.usertype!=null && login.usertype.equals("1")) {
            switch (item.getItemId()) {
                case R.id.update_rate:
                    startActivity(new Intent(getContext(), updateRate.class));

                    return true;
                case R.id.jyala_darti:
                    startActivity(new Intent(getContext(), dartiRate.class));

                    return true;

                case R.id.lebhi_report:
                    startActivity(new Intent(getContext(), monthlyLebhi.class));

                    return true;
                case R.id.webview:
                    startActivity(new Intent(getContext(), appliationForm.class));

                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        if (login.usertype!=null && login.usertype.equals("0")) {
            switch (item.getItemId()) {
                case R.id.lebhi_status:
                    startActivity(new Intent(getContext(), monthlyLebhi.class));

                    return true;

                case R.id.webview1:
                    startActivity(new Intent(getContext(),appliationForm.class));

                    return true;

                default:
                    return super.onOptionsItemSelected(item);
            }
        }







        return true;
    }

    public class getRate extends AsyncTask<String, String, String> {
        String db_url;


        @Override
        protected void onPreExecute() {
            //   Toast.makeText(newsFeed.this, "Loading........", Toast.LENGTH_SHORT).show();
            db_url = "http://gsa123.000webhostapp.com/getRate.php";

        }

        @Override
        protected String doInBackground(String... args) {
            String Date = (String) android.text.format.DateFormat.
                    format("yyyy-MM-dd",Calendar.getInstance().getTime());

            try {
                URL url = new URL(db_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data_string = URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(Date, "UTF-8");
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
                    if (jsonObject.getString("id") != null) {

                        finegold = jsonObject.getString("finegold");
                        tejgold = jsonObject.getString("tejgold");
                        silver = jsonObject.getString("silver");
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
            if (finegold != null) {
                fineview.setText(finegold);
            }
            if (tejgold != null) {
                tejview.setText(tejgold);
            }
            if (silver != null) {
                silview.setText(silver);
            }


        }
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
                    if (newstring != null) finefene.setText(newstring);
                }

                while (matcher1.find()) {
                    String newstring = matcher1.group(1);
                    if (newstring != null) tejabifene.setText(newstring);
                }

                while (matcher2.find()) {
                    String newstring = matcher2.group(1);
                    if (newstring != null) silverfene.setText(newstring);
                }
            }
        }
    }

}
