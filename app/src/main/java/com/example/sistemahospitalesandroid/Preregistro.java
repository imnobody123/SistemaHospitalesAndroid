package com.example.sistemahospitalesandroid;

import androidx.appcompat.app.AppCompatActivity;

import androidx.annotation.Nullable;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Preregistro extends AppCompatActivity {

    EditText CAMPO_PESO;
    EditText CAMPO_ESTATURA;
    EditText CAMPO_TEMP;
    EditText CAMPO_FREC_RESP;
    EditText CAMPO_PRESION_A;
    EditText CAMPO_DESCRIPCION;
    TextView TEXTUSUARIO;
    TextView TEXTUAFILIACION;
    Spinner BLOODTYPES;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preregistro);

        CAMPO_PESO = (EditText) findViewById(R.id.txtPeso);
        CAMPO_ESTATURA = (EditText) findViewById(R.id.txtEstatura);
        CAMPO_TEMP = (EditText) findViewById(R.id.txtTemp);
        CAMPO_FREC_RESP = (EditText) findViewById(R.id.txtFrecResp);
        CAMPO_PRESION_A = (EditText) findViewById(R.id.txtPresionA);
        CAMPO_DESCRIPCION = (EditText) findViewById(R.id.txtDescripcion);
        TEXTUSUARIO = (TextView) findViewById(R.id.lblUsuario);
        TEXTUAFILIACION = (TextView) findViewById(R.id.lblAfil);
        TEXTUSUARIO.setText(getIntent().getStringExtra("usuario"));
        TEXTUAFILIACION.setText(getIntent().getStringExtra("afiliacion"));

        BLOODTYPES = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.blod_types_array, R.layout.spinner_style);
        BLOODTYPES.setAdapter(adapter);
    }

    public void onClickEnviarPreregistro(View view)
    {
        String URL = "http://192.168.1.66/WebServiceHospitales/Insertar_Urgencias.php";
        String N_Afiliacion = TEXTUAFILIACION.getText().toString();
        String Peso = CAMPO_PESO.getText().toString();
        String Estatura = CAMPO_ESTATURA.getText().toString();
        String Temperatura = CAMPO_TEMP.getText().toString();
        String Frec_Resp = CAMPO_FREC_RESP.getText().toString();
        String Presion_A = CAMPO_PRESION_A.getText().toString();
        String Tipo_Sangre = BLOODTYPES.getSelectedItem().toString();
        String Descripcion = CAMPO_DESCRIPCION.getText().toString();

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                if (response.contains("Datos Ingresados Correctamente"))
                {
                    progressDialog.dismiss();
                    Toast toast = Toast.makeText(Preregistro.this, "Datos Igresados Correctamente", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else
                {
                    progressDialog.dismiss();
                    System.out.println(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast toast = Toast.makeText(Preregistro.this, "Algo salió mal", Toast.LENGTH_SHORT);
                toast.show();
                System.out.println(error.getMessage());
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String>params = new HashMap<String, String>();
                params.put("ID_PACIENTE", N_Afiliacion);
                params.put("PESO", Peso);
                params.put("ESTATURA", Estatura);
                params.put("TEMPERATURA", Temperatura);
                params.put("FREC_RESP", Frec_Resp);
                params.put("PRESION_A", Presion_A);
                params.put("DESCRIPCION", Descripcion);
                params.put("TIPO_SANGRE", Tipo_Sangre);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Preregistro.this);
        requestQueue.add(request);
    }
}