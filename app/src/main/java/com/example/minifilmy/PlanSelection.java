package com.example.minifilmy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PlanSelection extends AppCompatActivity {
    PlansAdapter plansAdapter;
    ListView listView;
    FirebaseAuth fAuth;
    ArrayList<String> finaltitle=new ArrayList<String>();
    ArrayList<String> finaldescription=new ArrayList<String>();
    ArrayList<Integer> finalprice=new ArrayList<Integer>();
    RelativeLayout datePicker;
    CalendarView toDate,fromDate;
    String current[];
    Date date;
    Calendar c,d,start,end;
    Integer totalamount,days;
    String planSelected,projectorselected;
    TextView todateText,headline;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_selection);
        fAuth = FirebaseAuth.getInstance();
        listView=findViewById(R.id.list);
        toDate=findViewById(R.id.toDate);
        fromDate=findViewById(R.id.fromDate);
        projectorselected = getIntent().getStringExtra("projectorselected");
        datePicker=findViewById(R.id.datePicker);
        button=findViewById(R.id.proceed);
        headline=findViewById(R.id.textView14);
        c = Calendar.getInstance();
        //fromDate.setDate(c.getTimeInMillis());
        fromDate.setMinDate(c.getTimeInMillis()+86400000);
        toDate.setMinDate(c.getTimeInMillis()+2*86400000);
        fromDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                if(planSelected=="custom") {
                    d = Calendar.getInstance();
                    d.set(i, i1, i2);
                    //toDate.setDate(c.getTimeInMillis()+86400000);
                    toDate.setMinDate(d.getTimeInMillis() + 86400000);
                }
            }
        });


        toDate.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {

            }
        });
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference day = database1.getReference("plans/day");
        DatabaseReference week = database1.getReference("plans/week");
        DatabaseReference month = database1.getReference("plans/month");
        day.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String temp[];
                temp=snapshot.getValue(String.class).split("#");
                finaltitle.add(temp[0]);
                finaldescription.add(temp[1]);
                finalprice.add(Integer.parseInt(temp[2]));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        week.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String temp[];
                temp=snapshot.getValue(String.class).split("#");
                finaltitle.add(temp[0]);
                finaldescription.add(temp[1]);
                finalprice.add(Integer.parseInt(temp[2]));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        month.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String temp[];
                temp=snapshot.getValue(String.class).split("#");
                finaltitle.add(temp[0]);
                finaldescription.add(temp[1]);
                finalprice.add(Integer.parseInt(temp[2]));
                finaltitle.add("Choose your custom plan");
                finaldescription.add("Choose a plan of your desired duration");
                finalprice.add(null);
                setAdapter();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (finaltitle.get(i).contains("Day")){
                    headline.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.INVISIBLE);
                    datePicker.setVisibility(View.VISIBLE);
                    todateText=findViewById(R.id.textView16);
                    toDate.setVisibility(View.GONE);
                    todateText.setVisibility(View.GONE);
                    planSelected="Day";
                    days=1;

                }
                else if (finaltitle.get(i).contains("30")){
                    planSelected="30 days";
                    headline.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.INVISIBLE);
                    datePicker.setVisibility(View.VISIBLE);
                    todateText=findViewById(R.id.textView16);
                    toDate.setVisibility(View.GONE);
                    todateText.setVisibility(View.GONE);
                    days=30;

                }
                else if (finaltitle.get(i).contains("Week")){
                    planSelected="week";

                    listView.setVisibility(View.INVISIBLE);
                    datePicker.setVisibility(View.VISIBLE);
                    todateText=findViewById(R.id.textView16);
                    toDate.setVisibility(View.GONE);
                    todateText.setVisibility(View.GONE);
                    days=7;

                }
                else if (finaltitle.get(i).contains("custom")){
                    headline.setVisibility(View.INVISIBLE);
                    listView.setVisibility(View.INVISIBLE);
                    datePicker.setVisibility(View.VISIBLE);
                    planSelected="custom";


                }

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


    }
    public void setAdapter (){
        plansAdapter = new PlansAdapter(this, finaltitle, finaldescription,finalprice);
        listView.setAdapter(plansAdapter);
    }

}