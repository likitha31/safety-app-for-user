package latrobesafety.mad.latrobesafety;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class emergency_information extends AppCompatActivity implements View.OnClickListener {

    TextView code1;
    TextView code2;
    TextView code3;
    TextView code4;
    TextView code5;
    TextView code6;
    TextView code7;
    TextView code8;
    TextView code9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_information);

        Button back = findViewById(R.id.exit);
        code1 = findViewById(R.id.code1);
        code2 = findViewById(R.id.code2);
        code3 = findViewById(R.id.code3);
        code4 = findViewById(R.id.code4);
        code5 = findViewById(R.id.code5);
        code6 = findViewById(R.id.code6);
        code7 = findViewById(R.id.code7);
        code8 = findViewById(R.id.code8);
        code9 = findViewById(R.id.code9);

        back.setOnClickListener(this);
        code1.setOnClickListener(this);
        code2.setOnClickListener(this);
        code3.setOnClickListener(this);
        code4.setOnClickListener(this);
        code5.setOnClickListener(this);
        code6.setOnClickListener(this);
        code7.setOnClickListener(this);
        code8.setOnClickListener(this);
        code9.setOnClickListener(this);

    }

    @Override
    public void onClick(View v){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW);

        switch(v.getId()) {

            case R.id.exit:
                Intent intent = new Intent(emergency_information.this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.code1:
                browserIntent.setData(Uri.parse("https://www.latrobe.edu.au/emergency/procedures/code-brown-external-emergency"));
                startActivity(browserIntent);
                break;
            case R.id.code2:
                browserIntent.setData(Uri.parse("https://www.latrobe.edu.au/emergency/procedures/code-red-firesmoke"));
                startActivity(browserIntent);
                break;
            case R.id.code3:
                browserIntent.setData(Uri.parse("https://www.latrobe.edu.au/emergency/procedures/code-blue-medical-emergency-first-aid"));
                startActivity(browserIntent);
                break;
            case R.id.code4:
                browserIntent.setData(Uri.parse("https://www.latrobe.edu.au/emergency/procedures/code-yellow-gas-leak-or-chemical-spill"));
                startActivity(browserIntent);
                break;
            case R.id.code5:
                browserIntent.setData(Uri.parse("https://www.latrobe.edu.au/emergency/procedures/code-orange-evacuation"));
                startActivity(browserIntent);
                break;
            case R.id.code6:
                browserIntent.setData(Uri.parse("https://www.latrobe.edu.au/emergency/procedures/code-purple-bombchemical-or-biological-threat"));
                startActivity(browserIntent);
                break;
            case R.id.code7:
                browserIntent.setData(Uri.parse("https://www.latrobe.edu.au/emergency/procedures/code-black-personal-threat"));
                startActivity(browserIntent);
                break;
            case R.id.code8:
                browserIntent.setData(Uri.parse("https://www.latrobe.edu.au/emergency/procedures/code-white-major-disruption-or-outage-incident"));
                startActivity(browserIntent);
                break;
            case R.id.code9:
                browserIntent.setData(Uri.parse("https://www.latrobe.edu.au/emergency/procedures/code-green-data-breachcyber-incident"));
                startActivity(browserIntent);
                break;
        }
    }
}
