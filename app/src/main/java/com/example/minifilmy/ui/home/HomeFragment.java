package com.example.minifilmy.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.minifilmy.LoginSignUp.MainActivity;
import com.example.minifilmy.LoginSignUp.OtpVer;
import com.example.minifilmy.PlanSelection;
import com.example.minifilmy.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private ImageSlider imageSlider;
    Button projector1,projector2;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        return root;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageSlider=getView().findViewById(R.id.slider);
        List<SlideModel> slideModels =new ArrayList<>();
        slideModels.add(new SlideModel(R.drawable.slide1));
        slideModels.add(new SlideModel(R.drawable.slide2));
        slideModels.add(new SlideModel(R.drawable.slide3));
        imageSlider.setImageList(slideModels,true);
        projector1=getView().findViewById(R.id.Piq);
        projector2=getView().findViewById(R.id.nebula);

        projector1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PlanSelection.class);
                intent.putExtra("projectorselected", "Piq");
                startActivity(intent);

            }
        });
        projector2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PlanSelection.class);
                intent.putExtra("projectorselected", "Nebula");
                startActivity(intent);
            }
        });
    }






}