package com.sheershakx.goldsilvercom;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class appliationForm extends AppCompatActivity {

    TextView givendate, maincontent,footertext;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliation_form);

        givendate = findViewById(R.id.dateForm);
        maincontent = findViewById(R.id.maintextForm);
        footertext = findViewById(R.id.footertext);
        givendate.setText(login.nepalidate);
        button=findViewById(R.id.button);
        button.setVisibility(View.GONE);


        maincontent.setText("       उपरोक्त सम्बन्धमा हाम्रो "+login.firmname+" फर्मका लागि सुन आवस्यक परेको हुनाले १ केजी सुन खरिद गर्नका लागि सिफारिस गरिदिनुहुन स-निबेदन हाम्रो फर्मको दर्ता नबिकरण" +
                " अध्यावधिक भएको कागजातहरु सिफारिस पत्र लिदाका बखत पेश गर्ने गरि यो Online निबेदन पेश गरेको छौ | " +
                "सिफारिस पत्र लिने बेला अध्यावधिक कागजात पेश नगरेमा वा गर्न नसकेको खण्डमा मेरो सिफारिस रद्ध भएमा मन्जुर छ |");

        footertext.setText("   भवदीय   "+"\n "+login.proname+"\n"+login.mymobile);

    }
}
