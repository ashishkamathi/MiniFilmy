package com.example.minifilmy.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.minifilmy.LoginSignUp.MainActivity;
import com.example.minifilmy.MainFlow;
import com.example.minifilmy.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationsFragment extends Fragment {
    private FirebaseAuth fAuth;
    private String uid,uname,uemail,ucity;
    private TextView name;
    private Button logout;
    private ProgressBar progressBar;
    private ArrayList<String> profile = new ArrayList<String>();
    private ListView listView;


    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });
        return root;
        
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fAuth = FirebaseAuth.getInstance();
        name=getView().findViewById(R.id.use_name);
       listView=getView().findViewById(R.id.list);
       progressBar=getView().findViewById(R.id.progressBar);

       logout=getView().findViewById(R.id.button);
        uid=fAuth.getUid();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_activated_1,profile);
        listView.setAdapter(adapter);
        FirebaseDatabase database1 = FirebaseDatabase.getInstance();
        DatabaseReference name = database1.getReference("user/"+uid+"/name");
        DatabaseReference city = database1.getReference("user/"+uid+"/city");
        DatabaseReference email = database1.getReference("user/"+uid+"/emailid");

        name.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uname= snapshot.getValue(String.class);
                textedit();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        city.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ucity=snapshot.getValue(String.class);
                textedit();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        email.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                uemail=snapshot.getValue(String.class);
                profile.add(uemail);
                profile.add(fAuth.getCurrentUser().getPhoneNumber());
                profile.add("Contact Us");
                adapter.notifyDataSetChanged();
                textedit();
                progressBar.setVisibility(View.GONE);
                logout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

    }

    public void textedit(){
        name.setText("Hello, "+uname+"\n"+ucity);
    }


}