package com.wyz.appdaigreja.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.wyz.appdaigreja.Adapter.LocaisAdapter;
import com.wyz.appdaigreja.Adapter.MembrosAdapter;
import com.wyz.appdaigreja.DAO.ConfigurationFireBase;
import com.wyz.appdaigreja.Entidades.Locais;
import com.wyz.appdaigreja.Entidades.Membros;
import com.wyz.appdaigreja.R;

import java.util.ArrayList;

public class MembrosActivity extends Activity {

    private ListView listViewMembros;
    private ArrayList<Membros> membros;
    private ArrayAdapter<Membros> adapter;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListener;
    private Button btnVoltarDeListaMembros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membros);

        membros = new ArrayList<>();

        listViewMembros = (ListView) findViewById(R.id.listViewMembros);
        adapter =  new MembrosAdapter(this, membros);
        listViewMembros.setAdapter(adapter);

        firebase = ConfigurationFireBase.getFirebase().child("MEMBROS");

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                membros.clear();

                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Membros membrosNovo = dados.getValue(Membros.class);
                    membros.add(membrosNovo);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        btnVoltarDeListaMembros = (Button) findViewById(R.id.btnVoltarDeListaLocais);
        btnVoltarDeListaMembros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltarTelaInicial();
            }
        });
    }
    private void voltarTelaInicial(){
        Intent intentAbrirTelaPrincipal = new Intent(this, PrincipalActivity.class);
        startActivity(intentAbrirTelaPrincipal);
        finish();

    }

    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListener);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListener);
    }
}
