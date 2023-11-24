package com.example.sistemahospitalesandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import Cifrar.Contrasena_MD5;

public class MainActivity extends AppCompatActivity {

    EditText CAMPO_USUARIO;
    EditText CAMPO_CONTRASENA;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CAMPO_USUARIO = (EditText) findViewById(R.id.txtUsuario);
        CAMPO_CONTRASENA = (EditText) findViewById(R.id.txtContrasena);
    }

    public void onClickIngresar(View v)
    {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "http://192.168.1.72/WebServiceHospitales/Verifica_Credenciales.php";
        String Usuario = CAMPO_USUARIO.getText().toString();
        String Contrasena = CAMPO_CONTRASENA.getText().toString();

        Contrasena_MD5 MD5 = new Contrasena_MD5();
        String Pass_Cifrada = MD5.getMD5(Contrasena);

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                int N_Afiliacion = Integer.parseInt(response);
                if(N_Afiliacion != 0)
                {
                    progressDialog.dismiss();
                    Intent intent = new Intent(MainActivity.this, Preregistro.class);
                    intent.putExtra("usuario", Usuario);
                    intent.putExtra("afiliacion", response);
                    startActivity(intent);
                }
                else
                {
                    progressDialog.dismiss();
                    Toast toast = Toast.makeText(MainActivity.this, "Usuario y/o contraseña érroneos", Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast toast = Toast.makeText(MainActivity.this, "Algo salió mal", Toast.LENGTH_SHORT);
                toast.show();
                System.out.println(error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<String, String>();
                params.put("USUARIO", Usuario);
                params.put("PASSWORD", Pass_Cifrada);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }

    public void onClickCrearCuenta(View view)
    {
        Intent intent = new Intent(MainActivity.this, Crear_Cuenta.class);
        startActivity(intent);
    }
}