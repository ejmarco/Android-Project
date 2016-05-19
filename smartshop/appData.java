package com.example.erikk.smartshop;

import android.content.Context;
import android.os.Environment;
import android.text.format.Time;

import org.w3c.dom.Attr;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

import weka.classifiers.Evaluation;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.trees.RandomForest;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.filters.Filter;
import weka.filters.supervised.attribute.NominalToBinary;
import weka.filters.unsupervised.attribute.NumericToNominal;

/**
 * Created by Erikk on 21/02/2016.
 *
 * Clase que guardara todos los datos de la aplicacion (y sera singlenton) para que asi todas tengan la MISMA informacion
 */

//MIRAR DE IR AÑADIENDO DINAMICAMENTE ATRIBUTOS EN LA INSTANCIA (O MODIFICARLO)
public class appData implements Serializable{
    private RandomForest rf = null;
    private  int id = 1;
    private ArrayList<Integer> pinsUsados = new ArrayList<>();
    private ArrayList<Local> locales;
    private static appData ourInstance;// = new appData();
    public static String fileName = "appData5.data"; //Ficheros de los datos de persianas botones etc..
    public static String fileUserData = "userData5.data";//Fichero de los datos de pulsaciones arboles etc..
    private static ArrayList<Integer> botonesPulsados; //Lista de los botones pulsados durante la session
    private Instances datosPulsaciones;
    private static Learning datosUsuario;
    private Instances data;

    public Instances getData(){
        return data;
    }

    public ArrayList<Integer> getBotonesPulsados(){
        return botonesPulsados;
    }

    public void addPinUsado(int id){
        this.pinsUsados.add(id);
    }

    public int getId(){
        return this.id++;
    }

    public static appData getInstance() {
        if(ourInstance == null){
            ourInstance = new appData();
        }
        return ourInstance;
    }
    public void pulsarBoton(int idBoton){
        this.botonesPulsados.add(idBoton);
        int numBotonesPulsados = this.botonesPulsados.size();


        Calendar c = Calendar.getInstance();
        Instance iExample = new DenseInstance(8);//(4.0,instanceValue1);
        iExample.setDataset(data);
        iExample.setValue((Attribute) data.attribute(0), (c.get(Calendar.MONTH) + 1));//mes pulsacion (los meses van de 0 a 11)
        iExample.setValue((Attribute) data.attribute(1), c.get(Calendar.DAY_OF_MONTH));//dia pulsacion
        iExample.setValue((Attribute) data.attribute(2), c.get(Calendar.HOUR));//hora pulsacion
        iExample.setValue((Attribute) data.attribute(3), (Integer)this.botonesPulsados.get(numBotonesPulsados - 2));//boton anterior 1 (MAS RECIENTE)
        iExample.setValue((Attribute) data.attribute(4), (Integer) this.botonesPulsados.get(numBotonesPulsados - 3));//boton anterior 2
        iExample.setValue((Attribute) data.attribute(5), (Integer) this.botonesPulsados.get(numBotonesPulsados - 4));//boton anterior 3
        iExample.setValue((Attribute) data.attribute(6), (Integer) this.botonesPulsados.get(numBotonesPulsados - 5));//boton anterior 4
        iExample.setValue((Attribute) data.attribute(7), Integer.toString(idBoton));//Boton Pulsado
        //iExample.setValue((Attribute) data.attribute(8), "yes");//Boton Pulsado
        this.data.add(iExample);
        System.out.println("INSTANCIA BOTON PULSADO " + iExample.toString());
    }
    private appData() {
        this.locales = new ArrayList<>();
        botonesPulsados = new ArrayList<>(); //Agregamos los 4 iniciales que no representan a ninguno para establecer un caso base.
        botonesPulsados.add(0);
        botonesPulsados.add(0);
        botonesPulsados.add(0);
        botonesPulsados.add(0);
        createDataSet();
    }
    public void addLocal(Local local){
        this.locales.add(local);
    }
    public ArrayList<Local> getLocales(){
        return this.locales;
    }
    public void setLocales(ArrayList<Local> locales){
        this.locales = locales;
    }
    public static appData leerDatos (Context context) throws Exception{
        FileInputStream fips = null;
        if(ourInstance == null) {
            try {
                fips = context.openFileInput(fileName);
                ObjectInputStream in = new ObjectInputStream(fips);
                ourInstance = (appData) in.readObject();
                botonesPulsados = new ArrayList<>(); //Agregamos los 4 iniciales que no representan a ninguno para establecer un caso base.
                botonesPulsados.add(0);
                botonesPulsados.add(0);
                botonesPulsados.add(0);
                botonesPulsados.add(0);
                ourInstance.trainData();
                in.close();
                // "Arbre llegit d'arxiu"
            }catch (FileNotFoundException fnf){
                ourInstance = new appData();
            }
            catch (Exception e) {
                System.out.println("ERROR EN LECTURA DE DATOS " + e.toString());
            } /*finally {
                try {
                    if (fips != null) {
                        if (ourInstance != null) {
                            if(ourInstance.botonesPulsados == null){
                                botonesPulsados = new ArrayList<>(); //Agregamos los 4 iniciales que no representan a ninguno para establecer un caso base.
                                botonesPulsados.add(0);
                                botonesPulsados.add(0);
                                botonesPulsados.add(0);
                                botonesPulsados.add(0);
                            }
                            if (ourInstance.getLocales() == null) {
                                ourInstance = new appData();
                                System.out.println("INTENTO CREAR LEARNING1");
                                }
                            else if(ourInstance.datosUsuario == null){
                                System.out.println("INTENTO CREAR LEARNING2");
                                botonesPulsados = new ArrayList<>(); //Agregamos los 4 iniciales que no representan a ninguno para establecer un caso base.
                                botonesPulsados.add(0);
                                botonesPulsados.add(0);
                                botonesPulsados.add(0);
                                botonesPulsados.add(0);
                                ourInstance.createDataSet();
                            }
                        } else {
                            System.out.println("INTENTO CREAR LEARNING3");
                            ourInstance = new appData();
                            ourInstance.createDataSet();
                        }
                        fips.close();
                    } else {
                        System.out.println("INTENTO CREAR LEARNING4");
                        ourInstance = new appData();
                        ourInstance.createDataSet();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }*/

        }
        else{
            botonesPulsados = new ArrayList<>(); //Agregamos los 4 iniciales que no representan a ninguno para establecer un caso base.
            botonesPulsados.add(0);
            botonesPulsados.add(0);
            botonesPulsados.add(0);
            botonesPulsados.add(0);
        }
        return ourInstance;
    }
    private void trainData(){
        if(data != null){
            if(data.size()>10) {
                try {
                    /*NumericToNominal convert = new NumericToNominal();
                    String[] options= new String[2];
                    options[0]="-R";
                    options[1]="1-8";  //range of variables to make numeric
                    convert.setOptions(options);
                    convert.setInputFormat(data);

                    Instances newData= Filter.useFilter(data, convert);

                    System.out.println("Before");
                    for(int i=0; i<9; i=i+1)
                    {
                        System.out.println("Nominal? "+data.attribute(i).isNominal());
                    }

                    System.out.println("After");
                    for(int i=0; i<9; i=i+1)
                    {
                        System.out.println("Nominal? "+newData.attribute(i).isNominal());
                    }*/
                    /*System.out.println(data.toString());
                    data.setClassIndex(data.numAttributes() - 1);
                    LinearRegression model = new LinearRegression();
                    model.buildClassifier(data);
                    System.out.println(model);*/

                    Calendar c = Calendar.getInstance();
                    Instance iExample = new DenseInstance(8);//(4.0,instanceValue1);
                    iExample.setDataset(data);
                    iExample.setValue((Attribute) data.attribute(0), (c.get(Calendar.MONTH) + 1));//mes pulsacion (los meses van de 0 a 11)
                    iExample.setValue((Attribute) data.attribute(1), c.get(Calendar.DAY_OF_MONTH));//dia pulsacion
                    iExample.setValue((Attribute) data.attribute(2), c.get(Calendar.HOUR));//hora pulsacion
                    iExample.setValue((Attribute) data.attribute(3), 2);//boton anterior 1 (MAS RECIENTE)
                    iExample.setValue((Attribute) data.attribute(4), 3);//boton anterior 2
                    iExample.setValue((Attribute) data.attribute(5), 2);//boton anterior 3
                    iExample.setValue((Attribute) data.attribute(6), 2);//boton anterior 4


                    /*Instance myHouse = iExample;//data.lastInstance();
                    double price = model.classifyInstance(myHouse);
                    System.out.println("My house ("+myHouse+"): "+price);*/





                    //cuidado con esto no sea que no asignemos bien
                    data.setClassIndex(data.numAttributes() - 1); //Seteamos el atributo por el que clasificaremos

                    int numFolds = (data.numInstances() > 0) ? 5 : 0;
                    rf = new RandomForest();
                    rf.setNumTrees((data.numInstances() > 0) ? 5 : 0);
                    rf.buildClassifier(data);
                    Evaluation evaluation = new Evaluation(data);
                    evaluation.crossValidateModel(rf, data, numFolds, new Random(1));
                    //System.out.println("IMPRIMO DATASEEEEEEEEEEEEEEETTT ---");
                    double i = rf.classifyInstance(iExample);
                    /*System.out.println("-----------------PRINTOOOO I: " + i);
                    System.out.println(", predicted: " + data.classAttribute().value((int) i));
                    System.out.println(data.toString());*/
                } catch (Exception e) {
                    System.out.println("ERRRORR EN TRAINDATAAA " + e.toString());
                }
            }
        }
    }
    //Classe que nos retorna el boton predicho
    public int classificarInstancia(Instance inst){
        double i = -1;
        try {
             i = rf.classifyInstance(inst);
        }
        catch(Exception e){
            System.out.println("Error en clasificacion de la instancia" + e.toString());
        }
        return Integer.parseInt(data.classAttribute().value((int)i));

    }
    //clase que mira si se ha creado el random forest para clasificar.
    public boolean canClassify(){
        if(rf == null){
            return false;
        }
        else{
            return true;
        }
    }
    /*public void loadData(String file,Context context) throws Exception{
        System.out.println("file " + file);
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

        }
    }*/

    private void createDataSet(){//Aqui entraremos si no existe el archivo y hay que crearlo por primera vez
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

        ArrayList<String> sinoBotones = new ArrayList<>();
        for(int i = 0; i<20; i++){
            sinoBotones.add(Integer.toString(i));
        }
        Attribute botonPulsadoSiNo = new Attribute("botonPulsadoSiNo",sinoBotones);
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
        //instanciasAtributos.add(botonPulsado);
        instanciasAtributos.add(botonPulsadoSiNo);

        data = new Instances("UserLearning",instanciasAtributos,0);
    }

    public void guardarDatos(Context context){
        try {
            FileOutputStream fops = context.openFileOutput(fileName,
                    Context.MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fops);
            out.writeObject(this);
            out.close();
        } catch (FileNotFoundException e) {
            System.out.println("L'arxiu no es troba");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("ALGUN FALLO");
            e.printStackTrace();
        }
    }
    public static void prueba(Context context) throws Exception {
        BufferedReader br = null;
        int numFolds = 5;
        pruebaGuardarArffNuevo(context);



        //InputStream fips = context.openFileInput("weatherBueno.arff");
        InputStream fips = context.openFileInput("prueba3.arff");
        ObjectInputStream in = new ObjectInputStream(fips);
        //InputStreamReader inputStreamReader = new InputStreamReader(fips);
        //br = new BufferedReader(inputStreamReader);

        Instances trainData = (Instances)in.readObject();//new Instances(br);
        in.close();
        trainData.setClassIndex(trainData.numAttributes() - 1);
        System.out.println(trainData.toString());

        RandomForest rf = new RandomForest();
        rf.setNumTrees(5);
        rf.buildClassifier(trainData);
        Evaluation evaluation = new Evaluation(trainData);
        evaluation.crossValidateModel(rf, trainData, numFolds, new Random(1));
        System.out.println(trainData.toString());

        //PRUEBA DE GUARDADO FUNCIONA
        /*FileOutputStream fops = context.openFileOutput("prueba2.arff",
                Context.MODE_PRIVATE);
        ObjectOutputStream out = new ObjectOutputStream(fops);
        out.writeObject(trainData);
        out.close();*/
        //FIN PRUEBA GUARDADO
        /*PRUEBA CREAR ATRIBUTO
        ArrayList<String> outlookItems = new ArrayList<>();
        outlookItems.add("sunny");
        outlookItems.add("overcast");
        outlookItems.add("rainy");
        Attribute outlook = new Attribute("outlook",outlookItems);
        Attribute temperatura = new Attribute("real");
        Attribute humidity = new Attribute("real");
        ArrayList<String> windyItems = new ArrayList<>();
        outlookItems.add("TRUE");
        outlookItems.add("FALSE");
        Attribute windy = new Attribute("windy",windyItems);
        ArrayList<String> playItems = new ArrayList<>();
        outlookItems.add("yes");
        outlookItems.add("no");
        Attribute play = new Attribute("play",outlookItems);
        ArrayList<Attribute> instanciasAtributos = new ArrayList<>();
        instanciasAtributos.add(outlook);
        instanciasAtributos.add(temperatura);
        instanciasAtributos.add(humidity);
        instanciasAtributos.add(windy);
        instanciasAtributos.add(play);
        double[] instanceValue1 = new double[trainData.numAttributes()];
        instanceValue1[1] = trainData.attribute(0).addStringValue("sunny");
        instanceValue1[2] = 87;
        instanceValue1[3] = 85;
        instanceValue1[4] = trainData.attribute(3).addStringValue("TRUE");
        //creamos la instancia de ejemplo
        Instance iExample = new DenseInstance(4);//(4.0,instanceValue1);
        iExample.setDataset(trainData);
        iExample.setValue(0, "overcast");
        iExample.setValue(1, 0);
        iExample.setValue(2, 89);
        iExample.setValue(3, "FALSE");

        System.out.println("-----------------AQUI LLEGOOOO " + iExample.toString());

        double i = rf.classifyInstance(iExample);
        System.out.println("-----------------PRINTOOOO I: " + i);
        System.out.println(", predicted: " + trainData.classAttribute().value((int) i));
        //FIN PRUEBA CREAR ATRIBUTO*/




        /*System.out.println(evaluation.toSummaryString("\nResults\n======\n", true));
        System.out.println(evaluation.toClassDetailsString());
        System.out.println("Results For Class -1- ");
        System.out.println("Precision=  " + evaluation.precision(0));
        System.out.println("Recall=  " + evaluation.recall(0));
        System.out.println("F-measure=  " + evaluation.fMeasure(0));
        System.out.println("Results For Class -2- ");
        System.out.println("Precision=  " + evaluation.precision(1));
        System.out.println("Recall=  " + evaluation.recall(1));
        System.out.println("F-measure=  " + evaluation.fMeasure(1));*/


    }
    public static void pruebaGuardarArffNuevo(Context context) throws Exception{
        //Creamos el tipo de Atributos y sus posibles valores
        ArrayList<String> outlookItems = new ArrayList<>();
        outlookItems.add("sunny");
        outlookItems.add("overcast");
        outlookItems.add("rainy");
        Attribute outlook = new Attribute("outlook",outlookItems);

        Attribute temperatura = new Attribute("temperatura");

        Attribute humidity = new Attribute("humidity");

        ArrayList<String> windyItems = new ArrayList<>();
        windyItems.add("TRUE");
        windyItems.add("FALSE");
        Attribute windy = new Attribute("windy",windyItems);

        ArrayList<String> playItems = new ArrayList<>();
        playItems.add("yes");
        playItems.add("no");
        Attribute play = new Attribute("play",playItems);

        ArrayList<Attribute> instanciasAtributos = new ArrayList<>();
        instanciasAtributos.add(outlook);
        instanciasAtributos.add(temperatura);
        instanciasAtributos.add(humidity);
        instanciasAtributos.add(windy);
        instanciasAtributos.add(play);
        Instances trainData = new Instances("tipoDataset",instanciasAtributos,0);
        //creamos una instancia concreta
       /* Instance iExample = new DenseInstance(5);//(4.0,instanceValue1);
        iExample.setDataset(trainData);
        iExample.setValue((Attribute) trainData.attribute(0), "overcast");
        System.out.println("Set Overcast");
        iExample.setValue((Attribute) trainData.attribute(1), 0);
        System.out.println("Set Overcast");
        iExample.setValue((Attribute) trainData.attribute(2), 89);
        System.out.println("Set Overcast");
        iExample.setValue((Attribute) trainData.attribute(3), "TRUE");
        System.out.println("Set Overcast");
        iExample.setValue((Attribute) trainData.attribute(4), "no");
        System.out.println("Set Overcast");

        trainData.add(iExample);
        trainData.add(iExample);
        trainData.add(iExample);
        trainData.add(iExample);
        trainData.add(iExample);*/

        //Guardamops como nuevo archivo

        //PRUEBA DE GUARDADO FUNCIONA
        FileOutputStream fops = context.openFileOutput("prueba3.arff",
                Context.MODE_PRIVATE);
        ObjectOutputStream out = new ObjectOutputStream(fops);
        out.writeObject(trainData);
        out.close();
        //FIN PRUEBA GUARDADO

    }
}
