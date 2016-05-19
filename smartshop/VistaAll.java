package com.example.erikk.smartshop;

/**
 * Created by Erikk on 19/02/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Calendar;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

public class VistaAll extends Fragment {
    private ListView listView;
    private ArrayList<Boton> botones = new ArrayList<>();
    private VistaAllAdapter listAdapter;
    private static VistaAll vistaAll;


    public static VistaAll getInstance(){
        if(null == vistaAll){
            vistaAll = new VistaAll();
        }
        return vistaAll;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //antes de mostrar la vista llenamos los datos con los botones
        this.obtenerBotones();
        return inflater.inflate(R.layout.all_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        final View view1 = view;
        super.onViewCreated(view, savedInstanceState);
        //Creamos la vista y le seteamos el adapter
        listView = (ListView) view.findViewById(R.id.lvSmartButtons);
        listAdapter = new VistaAllAdapter(getActivity(),R.layout.all_layout_items,this.botones);
        listView.setAdapter(listAdapter);
        //Accion de cuando se pulse el boton de la lista.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Boton item = (Boton)parent.getItemAtPosition(position);
                item.pulsar();
                //Refrescamos los datos para que aparezca el que quiere pulsar
                obtenerBotones();
                listAdapter.notifyDataSetChanged();
            }
        });
    }
    private void obtenerBotones(){
        //Vaciamos el ArrayList (Que esta directamente coonexionado con el adaptador
        botones.clear();
        appData datos = appData.getInstance();
        //Obtenemos todos los botones de todos los locales
        ArrayList<Local> locales = datos.getLocales();
        for (int i = 0; i < locales.size(); i++) {
            ArrayList<Boton> aux = locales.get(i).getBotones();
            for (int j = 0; j < aux.size(); j++) {
                botones.add(aux.get(j));
            }
        }
        //En caso de que el clasificador este ya preparado para clasificar ordenamos segun el Arbol
        if(datos.canClassify()){
            Calendar c = Calendar.getInstance();
            int month = (c.get(Calendar.MONTH) + 1);
            int day = c.get(Calendar.DAY_OF_MONTH);
            int hour = c.get(Calendar.HOUR);
            Instances data = datos.getData();
            ArrayList<Boton> aux = new ArrayList<>();
            ArrayList<Boton> aux2 = new ArrayList<>(botones);
            int sizeAux = botones.size();
            ArrayList<Integer> botonesClasificados = new ArrayList<>();
            boolean continuar = true;
            ArrayList<Integer> botonesAnterioresData = datos.getBotonesPulsados();
            int sizeDatasBotonesPulsados = botonesAnterioresData.size();
            int anterior1 = botonesAnterioresData.get(sizeDatasBotonesPulsados - 1);
            int anterior2 = botonesAnterioresData.get(sizeDatasBotonesPulsados - 2);
            int anterior3 = botonesAnterioresData.get(sizeDatasBotonesPulsados - 3);
            int anterior4 = botonesAnterioresData.get(sizeDatasBotonesPulsados - 4);
            int counter = 0;
            while(continuar){
                int botonClasificado;
                Instance iExample = new DenseInstance(8);//(4.0,instanceValue1);
                iExample.setDataset(data);
                iExample.setValue((Attribute) data.attribute(0), month);//mes pulsacion (los meses van de 0 a 11)
                iExample.setValue((Attribute) data.attribute(1), day);//dia pulsacion
                iExample.setValue((Attribute) data.attribute(2), hour);//hora pulsacion
                iExample.setValue((Attribute) data.attribute(3), anterior1);//boton anterior 1 (MAS RECIENTE)
                iExample.setValue((Attribute) data.attribute(4), anterior2);//boton anterior 2
                iExample.setValue((Attribute) data.attribute(5), anterior3);//boton anterior 3
                iExample.setValue((Attribute) data.attribute(6), anterior4);//boton anterior 4
                botonClasificado = datos.classificarInstancia(iExample);
                if(!botonesClasificados.contains(botonClasificado)){
                    botonesClasificados.add(botonClasificado);
                    for(int j = 0; j< aux2.size(); j++){
                        Boton btn = aux2.get(j);
                        if(btn.getId() == botonClasificado){
                            counter = 0;
                            aux.add(btn);
                            aux2.remove(j);
                            break;
                        }
                    }
                }
                else{
                    counter++;
                }
                if(counter > 3){
                    continuar = false;
                }
                anterior4 = anterior3;
                anterior3 = anterior2;
                anterior2 = anterior1;
                anterior1 = botonClasificado;
            }
            //En caso de que no se hayan clasificado todas las instancias las añadimos al final
            if(aux.size() < aux2.size()){//si la lista ordenada no tiene los mismos items que la original hemos de tratarlos
                aux.addAll(aux2);
            }
            botones.clear();
            botones.addAll(aux);
            }


    }
}
