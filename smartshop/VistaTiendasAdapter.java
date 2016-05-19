package com.example.erikk.smartshop;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Erikk on 21/02/2016.
 */
public class VistaTiendasAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<Local> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<Local, List<Persiana>> _listDataChild;

    public VistaTiendasAdapter(Context context,List<Local> listDataHeader,
                                 HashMap<Local, List<Persiana>> listChildData) {
        //
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    public void startAdd(){
        Intent intent = new Intent(this._context,addLocal.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this._context.startActivity(intent);
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final Persiana persianaLocal = (Persiana) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.tiendas_persianas_local, null);
            ArrayList<Button> botones = new ArrayList<>();
            for (int i = 0; i < persianaLocal.getBotones().size(); i++){
                final Button btn = new Button(this._context);
                btn.setId((i+1));

                final Boton botonPersiana=  persianaLocal.getBotones().get(i);
                btn.setText("ID Boton " + String.valueOf(persianaLocal.getBotones().get(i).getId()));

                //PONER LOS PARAMETROS DEL BOTON
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        botonPersiana.pulsar();
                    }
                });
                RelativeLayout ll = (RelativeLayout)convertView.findViewById(R.id.tiendas_Persianas_local);
                RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                //lp.setMargins(600,0,0,0);
                //lp.addRule(RelativeLayout.ALIGN_RIGHT);
                if(botones.size()>0) {
                    //lp.addRule(RelativeLayout.ALIGN_START,);
                    lp.addRule(RelativeLayout.LEFT_OF, btn.getId()-1);
                }else{
                    lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                }
                botones.add(btn);
                //btn.setGravity(Gravity.RIGHT);
                btn.setLayoutParams(lp);

                ll.addView(btn);
            }



        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItemPersianas);
        /*if(childText == "bbbab"){
            Button btn = (Button) convertView.findViewById(R.id.button);
            btn.setTextColor(Color.BLUE);


            this.botonesPersiana.add(btn);
        TextView myButton = new TextView(this);
        String a = "Boton: " + String.valueOf(botonesPersiana.size()) + " Pin: " + String.valueOf(btn.getPin() + " Tipo: " + btn.getTypeString());// + " Pin: " + String.valueOf(btn.getPin());
        myButton.setText(a);
        LinearLayout ll = (LinearLayout)findViewById(R.id.addPersianaLayout);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.tvCreadas.add(myButton);
        ll.addView(myButton, lp);
         LinearLayout ll = (LinearLayout)findViewById(R.id.addPersianaLayout);
        LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.tvCreadas.add(myButton);
        ll.addView(myButton, lp);

    final View vistaItems = inflater.inflate(R.layout.addpersianas_dialog, null);
        }*/



        txtListChild.setText(persianaLocal.getNombre());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        Local local = (Local) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.tiendas_local, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeaderLocal);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(local.getNombre());

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
