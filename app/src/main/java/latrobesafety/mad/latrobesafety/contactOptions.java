package latrobesafety.mad.latrobesafety;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
//import android.os.AsyncTask;
import android.content.pm.PackageManager;
import android.net.Uri;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class contactOptions extends AppCompatActivity {

    private Button messageBtn = null;
    private Button callBtn = null;
    String longitude;
    String latitude;
    final int CALL_REQUEST=1;
    private ImageView arrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_options);
        callBtn = findViewById(R.id.callBtn);
        messageBtn = findViewById(R.id.messageBtn);
        longitude = getIntent().getStringExtra("CURRENT_LONGITUDE");
        latitude = getIntent().getStringExtra("CURRENT_LATITUDE");
        arrow = findViewById(R.id.arrow);

        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(contactOptions.this, MainActivity.class);
                startActivity(intent);
            }
        });
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        if (ActivityCompat.checkSelfPermission(contactOptions.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(contactOptions.this,
                                    new String[]{Manifest.permission.CALL_PHONE},
                                    CALL_REQUEST);

                        }
                        else
                        {
                            Intent callIntent = new Intent(Intent.ACTION_DIAL);
                            callIntent.setData(Uri.parse("tel: 9448177238"));
                            startActivity(callIntent);
                        }
                    }
                });


                messageBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent(contactOptions.this, sendMessage.class);
                        intent.putExtra("CURRENT_LONGITUDE", longitude);
                        intent.putExtra("CURRENT_LATITUDE", latitude);
                        startActivity(intent);
                    }
                });


    }

}
