package com.example.erikk.smartshop;


import java.io.Serializable;
import java.net.URL;

/**
 * Created by Erikk on 18/02/2016.
 */
public class Boton implements  Const,Serializable {
    private int type;//Tipo de boton que sera, pulsador,interruptor..
    private int pin;//Pin de la placa arduino
    private int color; //atributo opcional
    private Persiana persiana;
    private int id;

    public Boton (int tipo,int pin,int color,Persiana persiana){
        this.type = tipo;
        this.pin = pin;
        this.color = color;
        this.persiana = persiana;
        this.id=appData.getInstance().getId();
    }
    public Boton (int tipo,int pin,Persiana persiana){
        this.type = tipo;
        this.pin = pin;
        this.persiana = persiana;
        this.id=appData.getInstance().getId();
    }
    public Boton (int tipo,int pin){
        this.type = tipo;
        this.pin = pin;
        this.id=appData.getInstance().getId();
    }
    public boolean pulsar(){
        try {
            String url = (this.persiana.getUrl() != "") ? this.persiana.getUrl() + "?" + this.pin : "";
            URL urlFinal = (url != "") ? new URL(url) : null;
            appData datos = appData.getInstance();
            switch (this.type) {
                case TYPE_PULSADOR:
                        new HttpControl().execute(urlFinal, urlFinal); //se envia 2 veces misma url (pulsar y despulsar)
                        datos.pulsarBoton(this.id);//guardamos el boton pulsado
                    break;
                case TYPE_INTERRUPTOR:
                    new HttpControl().execute(urlFinal); //se envia solo una vez pulsar o despulsar
                    datos.pulsarBoton(this.id);//guardamos el boton pulsado
                    break;
                default:
                    return false;
            }
        }catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
        return true;
    }
    public void setPersiana(Persiana persiana){
        this.persiana = persiana;
    }
    public int getType (){
        return this.type;
    }
    public Persiana getPersiana(){
        return this.persiana;
    }
    public String getTypeString(){
        if(this.type == TYPE_PULSADOR){
            return "Pulsador";
        }
        else if(this.type == TYPE_INTERRUPTOR){
            return "Interruptor";
        }
        return "null";
    }
    public int getPin(){
        return this.pin;
    }
    public int getId(){return this.id;}
}
