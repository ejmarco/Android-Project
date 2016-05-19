package com.example.erikk.smartshop;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Erikk on 26/02/2016.
 */
public class addPersianas extends AppCompatActivity implements addButtonDialog.addButtonDialogListener {
    Toolbar toolbar;
    private appData data;
    private DialogFragment dialog1 = new addButtonDialog();
    private ArrayList<Boton> botonesPersiana = new ArrayList<>();
    private ArrayList<TextView> tvCreadas = new ArrayList<>();
    private Local local;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addpersiana);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //Obtenemos el local que nos llega de la activity add local
        Intent i = getIntent();
        this.local = (Local) i.getSerializableExtra("local_a_añadir");

        Button btn = (Button) findViewById(R.id.btnNext);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(botonesPersiana.size() < 1){
                    AlertDialog alertDialog = new AlertDialog.Builder(addPersianas.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("No se puede crear una persiana sin botones");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }else {
                    data = appData.getInstance();
                    guardarDatos();//guardamos todo lo que se ha introducido por pantalla
                    data.addLocal(local);//añadimos el local creado
                    //llamamos a la activity principal
                    Intent intent = new Intent(addPersianas.this, MainActivity.class)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    //Evitamos que una vez hecho click en next se pueda volver atras
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }


            }
        });
        Button btnAddBtn = (Button) findViewById(R.id.btnAddButton);
        btnAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog1.show(getSupportFragmentManager(), "addButtonDialog");
            }
        });
        Button btnAddMasPersianas = (Button)findViewById(R.id.btnAddMasPersianas);
        btnAddMasPersianas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(botonesPersiana.size() < 1){
                    AlertDialog alertDialog = new AlertDialog.Builder(addPersianas.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("No se puede crear una persiana sin botones");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }else {
                    guardarDatos();
                    //Borramos la pagina
                    LinearLayout ll = (LinearLayout) findViewById(R.id.addPersianaLayout);
                    for (int i = 0; i < tvCreadas.size(); i++) {
                        ll.removeView(tvCreadas.get(i));
                    }
                    EditText etNombrePersiana = (EditText) findViewById(R.id.etNombrePersiana);
                    etNombrePersiana.setText("");
                }

            }
        });
    }
    @Override
    public void onDialogPositiveClick(DialogFragment dialog,Boton btn) {
        // User touched the dialog's positive button
       this.botonesPersiana.add(btn);
       TextView myButton = new TextView(this);
       String a = "Boton: " + String.valueOf(botonesPersiana.size()) + " Pin: " + String.valueOf(btn.getPin() + " Tipo: " + btn.getTypeString());// + " Pin: " + String.valueOf(btn.getPin());
       myButton.setText(a);
       LinearLayout ll = (LinearLayout)findViewById(R.id.addPersianaLayout);
       LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
       this.tvCreadas.add(myButton);
       ll.addView(myButton, lp);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        // User touched the dialog's negative button

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu); //se puede hacer otro layout de menu distinto, pero voy a dejar demomento el mismo
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void guardarDatos(){
        EditText etNombrePersiana = (EditText) findViewById(R.id.etNombrePersiana);
        Persiana persiana = new Persiana(etNombrePersiana.getText().toString(),botonesPersiana,local);
        for (int i = 0; i<botonesPersiana.size();i++){
            this.botonesPersiana.get(i).setPersiana(persiana);
            data.addPinUsado(this.botonesPersiana.get(i).getPin());
        }
        persiana.setBotones(this.botonesPersiana);
        this.local.addPersiana(persiana);
    }
}
