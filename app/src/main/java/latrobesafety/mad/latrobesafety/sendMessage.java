package latrobesafety.mad.latrobesafety;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import java.text.SimpleDateFormat;
import java.util.Date;

public class sendMessage extends AppCompatActivity {
    private Button sendBtn = null;
    private Button cancelBtn = null;
    private TextView desc = null;
    private EditText msg = null;
    private EditText name = null;
    private String message; //emergency message
    private String nm; // name of the sender
    private Emergency request;
    int refNum = 0;
    FusedLocationProviderClient mFusedLocationClient;
    Location mCurrentLocation;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_message);
        name = (EditText) findViewById(R.id.name);
        msg = (EditText) findViewById(R.id.messageTf);
        msg.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        sendBtn = findViewById(R.id.sendbtn);
        cancelBtn = findViewById(R.id.cancelBtn);
        desc = findViewById(R.id.desc);

       cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(sendMessage.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //Getting currentLocation
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            mCurrentLocation = location;
                        }
                    }
                });

    //Firebase initialization

        db = FirebaseFirestore.getInstance();

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Date date = new Date();
                message = msg.getText().toString();
                nm = name.getText().toString();
                String lat = mCurrentLocation.getLatitude()+"";
                String lon = mCurrentLocation.getLongitude()+"";
                request = new Emergency(nm,message,lat,lon,date);

                db.collection("Request").add(request).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("SEND_MESSAGE", "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(sendMessage.this, "Successful", Toast.LENGTH_LONG).show();

                        Intent serviceIntent = new Intent(sendMessage.this, LocationService.class);

                        serviceIntent.putExtra("ID",documentReference.getId());

                        startService(serviceIntent);

                        Intent intent = new Intent(sendMessage.this, Immediate_Confirmation.class);
                        intent.putExtra("ID",documentReference.getId());
                        startActivity(intent);

                    }
                }) ;

                sendBtn.setBackground(ContextCompat.getDrawable(sendMessage.this,R.drawable.buttondisabled));
                sendBtn.setEnabled(false);

                cancelBtn.setBackground(ContextCompat.getDrawable(sendMessage.this,R.drawable.buttondisabled));
                cancelBtn.setEnabled(false);

            }
        });
    }

}




