package com.example.erikk.smartshop;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import weka.associations.Item;

/**
 * Created by Erikk on 21/02/2016.
 */
public class VistaAllAdapter extends ArrayAdapter<Boton> {
    private Context context;
    private ArrayList<Boton> botones = null;

    public VistaAllAdapter(Context context, int resource, ArrayList<Boton> items) {
        super(context, resource, items);
        this.context = context;
        this.botones = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.all_layout_items, null);
        }

        Boton p = getItem(position);
        if(p!= null){
            TextView tvBoton = (TextView) v.findViewById(R.id.tvBoton);
            if(tvBoton != null) {
                tvBoton.setText("Tienda " +p.getPersiana().getLocal().getNombre() +" Id Boton " + Integer.toString(p.getId()));
            }
        }

        return v;
    }
    @Override
    public Boton getItem(int position){
        return this.botones.get(position);
    }

}
