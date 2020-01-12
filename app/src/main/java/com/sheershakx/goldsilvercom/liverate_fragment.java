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
import java.util.Locale;

public class liverate_fragment extends Fragment {
    TextView marqueetext;
    TextView currDate;
    String finegold, tejgold, silver;
    TextView fineview, tejview, silview;

    //  FloatingActionButton floatingActionButton;
    ImageView aboutus;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_liverate_fragment, container, false);

        fineview = view.findViewById(R.id.finegold_view);
        tejview = view.findViewById(R.id.tejgold_view);
        silview = view.findViewById(R.id.silver_view);
        new getRate().execute();
        marqueetext = view.findViewById(R.id.marqueetextview);
        Toolbar toolbar = view.findViewById(R.id.toolbar_livefragment);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        setHasOptionsMenu(true);

        marqueetext.setSelected(true);

        currDate = view.findViewById(R.id.currdate);
        LocalDateTime date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            date = LocalDateTime.now();
        }
        String Date = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            Date = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(date);
        }
        if (Date != null) {
            currDate.setText(Date);
        }
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

                default:
                    return super.onOptionsItemSelected(item);
            }
        }

        if (login.usertype!=null && login.usertype.equals("0")) {
            switch (item.getItemId()) {
                case R.id.lebhi_status:
                    startActivity(new Intent(getContext(), monthlyLebhi.class));

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
            LocalDateTime date = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                date = LocalDateTime.now();
            }
            String Date = "2222-22-22";
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                Date = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH).format(date);
            }

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
}
