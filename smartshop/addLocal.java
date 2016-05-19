package com.example.erikk.smartshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Erikk on 21/02/2016.
 */
public class addLocal extends AppCompatActivity {
    Toolbar toolbar;
    private appData data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tiendas_addtiendas);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button btn = (Button) findViewById(R.id.btnNext);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data = appData.getInstance();
                TextView tvTienda = (TextView) findViewById(R.id.etNombreTienda);
                String tienda = tvTienda.getText().toString();
                TextView tvIp = (TextView) findViewById(R.id.etAdress);
                String ip = tvIp.getText().toString();

                data = appData.getInstance();
                //data.addLocal(new Local(tienda,ip));
                Intent intent = new Intent(addLocal.this,addPersianas.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //Local aux = new Local (tienda,ip);
                Local aux = new Local (tienda,"http://192.168.1.177");
                intent.putExtra("local_a_añadir",aux);//Enviamos el objeto a la otra actividad.
                startActivity(intent);
            }
        });
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
}

