package com.example.erikk.smartshop;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Erikk on 18/02/2016.
 */
public class Persiana implements Serializable {
    private String nombre; //nombre para reconocer a la persiana
    private ArrayList<Boton> botones; //una persiana puede tener varios pulsadores
    private Local local; //local al que pertenece la persiana
    private int id;

    public Persiana(String nombre,ArrayList<Boton> botones,Local local){
        this.nombre = nombre;
        this.botones = botones;
        this.local = local;
        this.id=appData.getInstance().getId();
    }
    public Persiana(String nombre,Boton boton,Local local){
        this.nombre = nombre;
        this.botones.add(boton);
        this.local = local;
        this.id=appData.getInstance().getId();
    }
    public int getId(){return  this.id;}
    public String getNombre(){
        return this.nombre;
    }
    public void setNombre(String nombre){
        this.nombre = nombre;
    }
    public void addBoton(Boton boton){
        this.botones.add(boton);
    }
    public String getUrl(){
        //(this.local.getIp()!="") ? this.local.getIp() : ""
        return this.local.getIp();
    }
    public Local getLocal(){
        return this.local;
    }
    public ArrayList<Boton> getBotones(){
        return  this.botones;
    }
    public void setBotones (ArrayList<Boton> botones){
        this.botones = botones;
    }

}
