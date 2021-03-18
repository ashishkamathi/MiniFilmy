package com.example.minifilmy.LoginSignUp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.minifilmy.MainFlow;
import com.example.minifilmy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserInfo extends AppCompatActivity {
    Spinner spinner;
    String[] cities = {"City", "Bengaluru", "Pune"};
    EditText name, email,pincode;
    String city,uname,upin,uid,uemail,data;
    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        name=findViewById(R.id.editTextTextPersonName);
        email=findViewById(R.id.editTextTextEmailAddress);
        pincode=findViewById(R.id.pincode);
        fAuth = FirebaseAuth.getInstance();
        uid=fAuth.getUid();
        spinner = findViewById(R.id.spinner);
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, cities));

        findViewById(R.id.buttonContinue1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 city = spinner.getSelectedItem().toString();
                 uemail = email.getText().toString().trim();
                 uname = name.getText().toString().trim();
                 upin=pincode.getText().toString().trim();

                 if(city == "city"){
                     ((TextView)spinner.getSelectedView()).setError("Select a city");
                     return;
                 }

                if (TextUtils.isEmpty(uemail)) {
                    email.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(upin)) {
                    pincode.setError("Pincode id Required.");
                    return;
                }

                if (TextUtils.isEmpty(uname)) {
                    name.setError("Name is Required.");
                    return;
                }

                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference name = database.getReference("user/" + uid + "/name");
                    name.setValue(uname);
                    DatabaseReference city1 = database.getReference("user/" + uid + "/city");
                    city1.setValue(city);
                    DatabaseReference age = database.getReference("user/" + uid + "/pincode");
                    age.setValue(upin);
                    DatabaseReference email = database.getReference("user/" + uid + "/emailid");
                    email.setValue(uemail);
                    startActivity(new Intent(getApplicationContext(), MainFlow.class));


            }
        });
    }
}
