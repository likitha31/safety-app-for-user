package latrobesafety.mad.latrobesafety;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;


public class confirmation extends AppCompatActivity {

    private Button cancelBtn = null;
    private Button okBtn = null;
    private TextView info = null;
    private TextView time = null;
    private TextView building = null;
    private TextView name = null;
    private TextView message = null;
    private String minute;
    private String hour;


    PickUp request = null;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);
        //Get the Request object
        request = (PickUp) getIntent().getSerializableExtra("PickUp_REQ");
        minute = minToString(request);
        hour = hourToString(request);


        building = findViewById(R.id.building);
        building.setText("Building   : "+ request.getBuilding());
        name = findViewById(R.id.name);
        name.setText("Name       : "+ request.getName());
        time = findViewById(R.id.time);
        time.setText("Time         : "+ hour + "." + minute);
        message = findViewById(R.id.msg);
        message.setText(request.getMessage());


        okBtn = findViewById(R.id.OkBtn);

        cancelBtn = findViewById(R.id.cancelBtn);

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(confirmation.this, requestForm.class);
                startActivity(intent);
            }
        });

        //Firebase initialization
        db = FirebaseFirestore.getInstance();

       okBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               db.collection("Request").add(request).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                   @Override
                   public void onSuccess(DocumentReference documentReference) {
                       Log.d("SEND_MESSAGE", "DocumentSnapshot added with ID: " + documentReference.getId());
                       Toast.makeText(confirmation.this, "Successful", Toast.LENGTH_LONG).show();

                       Intent intent = new Intent(confirmation.this, Immediate_Confirmation.class);
                       intent.putExtra("ID",documentReference.getId());
                       startActivity(intent);
                   }
               });

               okBtn.setBackground(ContextCompat.getDrawable(confirmation.this,R.drawable.buttondisabled));
               okBtn.setEnabled(false);

               cancelBtn.setBackground(ContextCompat.getDrawable(confirmation.this,R.drawable.buttondisabled));
               cancelBtn.setEnabled(false);
           }

       });
    }

    public String minToString(PickUp request)
    {
        String minute;
        if (request.getMinute()<10){
            minute = "0" + request.getMinute();
        }
        else
        {
            minute = "" + request.getMinute();
        }

        return minute;
    }

    public String hourToString(PickUp request)
    {
        String hour;
        if (request.getHour()<10){
            hour = "0" + request.getHour();
        }
        else
        {
            hour = "" + request.getHour();
        }
        return hour;
    }
}
