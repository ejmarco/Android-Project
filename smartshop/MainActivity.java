package com.example.erikk.smartshop;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

//antes extends AppCompatActivity
public class MainActivity extends AppCompatActivity {
    private ArrayList<Local> locales = new ArrayList<>();



//NO GUARDO IDGENERATOORR
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //WekaTest wk = new WekaTest();
        //try{wk.prueba();}catch(Exception e){System.out.println("ERRROOORRR" + e.getMessage().toString());}
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        try {
            appData datos = appData.leerDatos(this); //LO ORIGINAAL
            //datos.pulsarBoton(1,1);

            //VALORES DATASETTT

            //Podria ser geolocalizacion, franja horaria (AM/PM)
        }catch(Exception e){
            System.out.println("ERRRORRRRRRRRRRRRRR-----------------------------------------------");
            System.out.println(e.toString());
        }
        //idGenerator ids = idGenerator.getInstance();

        /*try {
            appData.prueba(this);
        }catch(Exception e){
            System.out.println("ERRRORRRRRRRRRRRRRR-----------------------------------------------");
            System.out.println(e.toString());
        }*/
        //datos.pulsarBoton(1,1);





        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Tienda"));
        tabLayout.addTab(tabLayout.newTab().setText("Smart"));
        tabLayout.addTab(tabLayout.newTab().setText("Settings"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        /* Boton flotante usado para añadir y demas (quitado el 19/02)
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setImageResource(R.mipmap.ic_launcher);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        /*Button btn = (Button) findViewById(R.id.btnLed);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    URL url = new URL("http://192.168.1.177?3");
                    new HttpControl().execute(url);


                }catch(Exception e){
                    System.out.println(e.toString());
                }
            }
        });*/
    }









    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            //System.out.println("PULSO SETTINGS");
            return true;
        }
        else if(id == R.id.addTienda){
           // System.out.println("PULSO ADDLOCAL");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onPause(){
        super.onPause();
        System.out.println("ON PAUSEEEEEEEEEEEEEEEEEEE");
        appData data = appData.getInstance();
        data.guardarDatos(this);

    }

    @Override
    public void onStop(){
        super.onStop();
        System.out.println("ON STOPPPPPPPPPPP");
        appData data = appData.getInstance();
        data.guardarDatos(this);

    }

}
