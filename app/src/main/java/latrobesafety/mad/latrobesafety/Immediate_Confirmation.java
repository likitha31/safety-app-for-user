package latrobesafety.mad.latrobesafety;


import androidx.annotation.Nullable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;


public class Immediate_Confirmation extends AppCompatActivity {

    TextView confirmation = null;
    FirebaseFirestore db;
    Request.STATUS status;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.immediate__confirmation);

        String docId = getIntent().getStringExtra("ID");
        confirmation = findViewById(R.id.immConfirm);

        db = FirebaseFirestore.getInstance();
        final DocumentReference docRef = db.collection("Request").document(docId);
        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w("FIRESTORE", "Listen failed.", e);
                    return;
                }

                if (snapshot != null && snapshot.exists()) {
                    Log.d("FIRESTORE-----------", "Current data: " + snapshot.getData());

                        status = Request.STATUS.valueOf(snapshot.getString("status"));
                        if(status == Request.STATUS.ON_GOING)
                        {
                            confirmation.setText("[UPDATE]SECURITY HAS BEEN DISPATCHED TO YOUR LOCATION");
                        }
                        else if(status == Request.STATUS.ACTIVE)
                        {
                            confirmation.setText("PLEASE WAIT FOR UPDATE");
                        }
                        else
                        {
                            confirmation.setText("[REQUEST COMPLETE]YOU ARE SAFE");
                        }
                } else {
                    Log.d("FIRESTORE-----------","Current data: null");
                }
            }
        });


    }
}
