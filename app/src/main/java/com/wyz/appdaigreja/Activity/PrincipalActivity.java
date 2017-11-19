package com.wyz.appdaigreja.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wyz.appdaigreja.R;

public class PrincipalActivity extends Activity {

    private Button btnAddLocal, btnAddMembro, btnVerMembros, btnVerLocais, toJson;
    private TextView tvNomeUsuarioLogado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btnAddLocal = (Button) findViewById(R.id.btnAddLocal);
        btnAddMembro = (Button) findViewById(R.id.btnAddMembro);
        btnVerMembros = (Button) findViewById(R.id.btnVerMembros);
        btnVerLocais = (Button) findViewById(R.id.btnVerLocais);
        tvNomeUsuarioLogado = (TextView) findViewById(R.id.tvNomeUsuarioLogado);
        toJson = (Button) findViewById(R.id.toJson);

        btnAddLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreCadastroLocal();
            }
        });
        btnAddMembro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreCadastroMembro();
            }
        });
        btnVerMembros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreMembros();
            }
        });
        btnVerLocais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreLocais();
            }
        });
        toJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreJson();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            String name = user.getDisplayName();
            tvNomeUsuarioLogado.setText(name);

        }


    }


    public void abreCadastroMembro() {
        Intent intent = new Intent(PrincipalActivity.this, CadastroMembros.class);
        startActivity(intent);
    }
    public void abreCadastroLocal() {
        Intent intent = new Intent(PrincipalActivity.this, CadastroLocais.class);
        startActivity(intent);
    }
    public void abreMembros() {
        Intent intent = new Intent(PrincipalActivity.this, MembrosActivity.class);
        startActivity(intent);
    }
    public void abreLocais() {
        Intent intent = new Intent(PrincipalActivity.this, LocaisActivity.class);
        startActivity(intent);
    }
    public void abreJson() {
        Intent intent = new Intent(PrincipalActivity.this, JsonActivity.class);
        startActivity(intent);
    }

}
