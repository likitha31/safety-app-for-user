package latrobesafety.mad.latrobesafety;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;


public class requestForm extends AppCompatActivity {


    private Button confirmBtn = null;
    private Spinner buildList = null;
    private TimePicker timePicker = null;
    private EditText nameET = null;
    private EditText descET = null;
    private String msg;
    private String name;
    private String location;
    int hour;
    int minute;
    private String[] building = {"Agora East","Agora Theatre","Agora West", "AgriBio", "BG","BS1","BS2","Library","Chisolm College"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.request_form);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        timePicker.setIs24HourView(true);
        nameET = (EditText)findViewById(R.id.nameET);
        descET = (EditText)findViewById(R.id.msgET);
        descET.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
        buildList = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter= new ArrayAdapter<String>(this,android.
                R.layout.simple_spinner_dropdown_item ,building);

        buildList.setAdapter(adapter);

        confirmBtn = findViewById(R.id.confirmBtn);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                int sid = buildList.getSelectedItemPosition();
                location = building[sid];
                hour = timePicker.getHour();
                minute= timePicker.getMinute();
                msg = descET.getText().toString();
                name = nameET.getText().toString();

                Date currentDateandTime = new Date();

                PickUp request = new PickUp(msg,hour,minute,name,location, currentDateandTime);


                Intent intent = new Intent(requestForm.this, confirmation.class );
                intent.putExtra("PickUp_REQ", request);
                startActivity(intent);
            }
        });
    }
}
