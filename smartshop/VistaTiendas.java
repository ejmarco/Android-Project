package com.example.erikk.smartshop;

/**
 * Created by Erikk on 19/02/2016.
 */
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VistaTiendas extends Fragment{
    //PRUEBAS
    VistaTiendasAdapter listAdapter;
    ExpandableListView expListView;
    List<Local> listDataHeader;
    HashMap<Local, List<Persiana>> listDataChild;
    private final appData datos = appData.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        prepareListData2();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tiendas_layout, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final View view1 = view;
        super.onViewCreated(view, savedInstanceState);
        expListView = (ExpandableListView) view.findViewById(R.id.lvExpTiendas); //Recogemos la lista para llenarla
        listAdapter = new VistaTiendasAdapter(getActivity(),listDataHeader, listDataChild);//necesitamos un adaptador de lista que coloque los valores en la expandable list
        expListView.setAdapter(listAdapter);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Snackbar.make(view1, "Replace with your own action", Snackbar.LENGTH_LONG)
                //  .setAction("Action", null).show();
                listAdapter.startAdd();
            }
        });


    }
    private void prepareListData2() {
        listDataHeader = new ArrayList<Local>();
        listDataChild = new HashMap<Local, List<Persiana>>();
        ArrayList<Local> locals = new ArrayList<>();
        locals = this.datos.getLocales();
        for(int i = 0;i<locals.size();i++){
            listDataHeader.add(locals.get(i));
            List<Persiana> persianas =locals.get(i).getPersianas();
            listDataChild.put(listDataHeader.get(i), persianas);
        }


    }
    /*
     * Preparing the list data, podria leer de la clase singlenton y montar la lista correctamente.
     */
    private void prepareListData() {
        /*listDataHeader = new ArrayList<Local>();
        listDataChild = new HashMap<Local, List<String>>();

        // Adding child data
        //listDataHeader.add("Top 250");
        if(datos.getLocales().size() > 0){
            listDataHeader.add(datos.getLocales().get(0).getNombre());
        }else {
            listDataHeader.add("Top 250");
        }
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);*/
    }
}
