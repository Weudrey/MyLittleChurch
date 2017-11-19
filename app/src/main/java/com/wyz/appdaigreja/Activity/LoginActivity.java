package com.wyz.appdaigreja.Activity;

import android.app.Activity;
import android.app.Notification;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wyz.appdaigreja.DAO.ConfigurationFireBase;
import com.wyz.appdaigreja.Entidades.Usuarios;
import com.wyz.appdaigreja.R;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class LoginActivity extends Activity {

    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnLogar;
    private LoginButton btnLogarFacebook;
    private FirebaseAuth autenticacao;
    private TextView tvAbreCadastro;
    private Usuarios usuarios;

    private CallbackManager callbackManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();
        btnLogarFacebook = (LoginButton) findViewById(R.id.btnLogarFacebook);

        try{
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for(Signature signature : info.signatures){
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }

        }catch (PackageManager.NameNotFoundException e){

        }catch (NoSuchAlgorithmException e){

        }

        btnLogarFacebook.setReadPermissions("email");
        btnLogarFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("Sucesso!", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
                Intent i = new Intent(LoginActivity.this, PrincipalActivity.class);
                startActivity(i);
            }

            @Override
            public void onCancel() {}

            @Override
            public void onError(FacebookException error) {
                Log.d("ERRO", "facebook:onError", error);
            }
        });



        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtSenha);
        btnLogar = (Button) findViewById(R.id.btnLogar);
        tvAbreCadastro = (TextView) findViewById(R.id.tvAbreCadastro);


        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        btnLogar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(!edtEmail.getText().toString().equals("") && !edtSenha.getText().toString().equals("")){

                    usuarios = new Usuarios();
                    usuarios.setEmail(edtEmail.getText().toString());
                    usuarios.setSenha(edtSenha.getText().toString());
                    validarLogin();

                }else{
                    Toast.makeText(LoginActivity.this, "Preencha os campos de e-mail e senha", Toast.LENGTH_SHORT).show();
                }

            }
        });
        tvAbreCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreCadastroUsuario();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void validarLogin(){

        autenticacao = ConfigurationFireBase.getAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuarios.getEmail(), usuarios.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()){
                    abrirTelaPrincipal();
                    Toast.makeText(LoginActivity.this, "Login Efetuado com Sucessoo", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void abrirTelaPrincipal(){
        Intent intentAbrirTelaPrincipal = new Intent(LoginActivity.this, PrincipalActivity.class);
        startActivity(intentAbrirTelaPrincipal);
    }
    public void abreCadastroUsuario() {
        Intent intent = new Intent(LoginActivity.this, CadastroUsuarioActivity.class);
        startActivity(intent);
    }

    private void handleFacebookAccessToken(AccessToken token){
        Log.d("Success", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        autenticacao.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("Success", "signInWithCredential:success");
                            FirebaseUser user = autenticacao.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("Falha", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "A autentiação falhou.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        // ...
                    }
                });
    }




}
