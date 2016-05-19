package com.example.erikk.smartshop;



import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import weka.classifiers.Evaluation;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Created by Erikk on 10/04/2016.
 */
public class Learning extends AsyncTask<Object,Integer,Boolean> implements Serializable {
    private Instances data;
    private static Learning learning;


    public static Learning getInstance() {
        if(learning == null){
            learning = new Learning();
        }
        return learning;
    }
    private Learning(){System.out.println("CREO LA INSTANCIA LEARNING");}

    @Override
    protected Boolean doInBackground(Object... params){
        System.out.println("ENTRO EN DOINBACKGROUND");
        if(isCancelled()) {
            return true;
        }
        try {
            if (params[0] instanceof java.lang.Integer) { //Si el primer parametro es un Integer
                System.out.println("ENTRO IF");
                switch ((Integer) params[0]) {

                    case 0: //Si es un 0 es que queremos cargar fichero
                        try{
                            this.loadData((String) params[1], (Context) params[2]);
                             return true;//Por lo que los otros parametros seran un nombre de fichero y un context
                        }
                        catch (Exception e) {
                            System.out.println("Ha habido un error con la carga de datos de usuario" + e.getMessage().toString());
                            return false;
                        }
                    case 1: //si es un 1 es que se ha pulsado un boton y queremos añadir la instancia
                        System.out.println("AQUIIII LLEGOOOO ----");
                        this.botonPulsado(params);
                        System.out.println("AQUIIII SALGOO ----");
                        return true;
                }
            }
        }
        catch(Exception e){
            System.out.println("ERROR EN LEARNING" + e.getMessage().toString());
        }
        return true;
    }
    //Metodo que creara una instancia de pulsacion en el dataset
    private void botonPulsado(Object... params){
        Calendar c = Calendar.getInstance();
        Instance iExample = new DenseInstance(8);//(4.0,instanceValue1);
        iExample.setDataset(data);
        System.out.println("MONTh");
        iExample.setValue((Attribute) data.attribute(0), (c.get(Calendar.MONTH) + 1));//mes pulsacion (los meses van de 0 a 11)
        System.out.println("MONTh");
        iExample.setValue((Attribute) data.attribute(1), c.get(Calendar.DAY_OF_MONTH));//dia pulsacion
        iExample.setValue((Attribute) data.attribute(2), c.get(Calendar.HOUR));//hora pulsacion
        iExample.setValue((Attribute) data.attribute(3), (Integer)params[2]);//boton anterior 1 (MAS RECIENTE)
        iExample.setValue((Attribute) data.attribute(4), (Integer)params[3]);//boton anterior 2
        iExample.setValue((Attribute) data.attribute(5), (Integer)params[4]);//boton anterior 3
        iExample.setValue((Attribute) data.attribute(6), (Integer)params[5]);//boton anterior 4
        iExample.setValue((Attribute) data.attribute(7), (Integer)params[1]);//Boton Pulsado
        System.out.println("INSTANCIA BOTON PULSADO " + iExample.toString());
    }
    public void loadData(String file,Context context) throws Exception{
       try {
           InputStream fips = context.openFileInput(file);
           ObjectInputStream in = new ObjectInputStream(fips);
           Instances trainData = (Instances) in.readObject(); //Creamos el DataSet
           in.close();
           trainData.setClassIndex(trainData.numAttributes() - 1); //Seteamos el atributo por el que clasificaremos

           int numFolds = (trainData.numInstances()>0) ? 5 : 0;
           RandomForest rf = new RandomForest();
           rf.setNumTrees((trainData.numInstances()>0) ? 5 : 0);
           rf.buildClassifier(trainData);
           Evaluation evaluation = new Evaluation(trainData);
           evaluation.crossValidateModel(rf, trainData, numFolds, new Random(1));
           System.out.println("IMPRIMO DATASEEEEEEEEEEEEEEETTT ---");
           System.out.println(trainData.toString());
       }catch(FileNotFoundException e){ //Aqui entraremos si el archivo no se ha creado
           System.out.println("CATCHHHHHHHHHHHHHHHHHHHHHH ---");
            this.createDataSet(file);
       }
    }
    private void createDataSet(String file){//Aqui entraremos si no existe el archivo y hay que crearlo por primera vez
        //Creamos el tipo de Atributos y sus posibles valores

            //SE PODRIA PONER TAMBIEN POR LOCALIZACION
        //ATRIBUTO Mes
            Attribute mesPulsacion = new Attribute("mesPulsacion");
        //FIN ATRIBUTO Mes

        //ATRIBUTO Dia de la semana (Lun,Mar,Mier..) de la pulsacion
            Attribute diaSPulsacion = new Attribute("diaPulsacion");
        //FIN ATRIBUTO Mes

        //ATRIBUTO Hora
            Attribute horaPulsacion = new Attribute("horaPulsacion");
        //FIN ATRIBUTO Hora

        //ATRIBUTOS  Boton Anterior (En ID)
            Attribute botonAnterior1 = new Attribute("botonAnterior1");
            Attribute botonAnterior2 = new Attribute("botonAnterior2");
            Attribute botonAnterior3 = new Attribute("botonAnterior3");
            Attribute botonAnterior4 = new Attribute("botonAnterior4");
        //FIN ATRIBUTO Boton Anterior

        //ATRIBUTO DE CLASE
        Attribute botonPulsado = new Attribute("botonPulsado");
        //FIN ATRIBUTO DE CLASE


        //Creamos el Dataset
        ArrayList<Attribute> instanciasAtributos = new ArrayList<>();
        instanciasAtributos.add(mesPulsacion);
        instanciasAtributos.add(diaSPulsacion);
        instanciasAtributos.add(horaPulsacion);
        instanciasAtributos.add(botonAnterior1);
        instanciasAtributos.add(botonAnterior2);
        instanciasAtributos.add(botonAnterior3);
        instanciasAtributos.add(botonAnterior4);
        instanciasAtributos.add(botonPulsado);

        this.data = new Instances("UserLearning",instanciasAtributos,0);
    }


}
