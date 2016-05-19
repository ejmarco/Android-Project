package com.example.erikk.smartshop;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Erikk on 18/02/2016.
 */
public class Local implements Serializable{
    //Clase especifica para los locales, donde cada local puede tener su propia persiana (y IP)
    private String nombre;
    private String ip;
    private ArrayList<Persiana> persianas;
    private int id;

    public Local(String nombre,String ip){
        this.nombre = nombre;
        this.ip = ip;
        this.persianas = new ArrayList();
        this.id=idGenerator.getInstance().getId();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public ArrayList<Persiana> getPersianas(){
        return this.persianas;
    }

    public void addPersiana(Persiana persiana){
        this.persianas.add(persiana);
    }

    public ArrayList<Boton> getBotones() {
        ArrayList<Boton> btn = new ArrayList<>();
        for(int i = 0;i<this.persianas.size();i++){
            ArrayList<Boton> aux = this.persianas.get(i).getBotones();
            for(int j = 0; j<aux.size(); j++){
                btn.add(aux.get(j));
            }
        }
        return btn;
    }





}
