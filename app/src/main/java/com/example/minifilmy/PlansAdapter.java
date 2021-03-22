package com.example.minifilmy;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class PlansAdapter extends ArrayAdapter<String> {
    Context context;
    static ArrayList<String> plantitle=new ArrayList<String>();
    static ArrayList<String> plandescription=new ArrayList<String>();
    static ArrayList<Integer> price=new ArrayList<Integer>();


    PlansAdapter(Context c, ArrayList<String> finaltitle, ArrayList<String> finaldescription, ArrayList<Integer> finalprice) {
        super(c, R.layout.row, R.id.textView1, finaltitle);
        this.context = c;
        this.plantitle = finaltitle;
        this.plandescription = finaldescription;
        this.price=finalprice;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row, parent, false);
        TextView myTitle = row.findViewById(R.id.textView1);
        TextView myDescription = row.findViewById(R.id.textView2);
        TextView cost = row.findViewById(R.id.Price);
        myTitle.setText(plantitle.get(position));
        myDescription.setText(plandescription.get(position));
        if(price.get(position)!=null) {
            cost.setText("Rs: " + price.get(position));
        }
        return row;
    }
}
