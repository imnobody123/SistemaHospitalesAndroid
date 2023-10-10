package com.example.sistemahospitalesandroid;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Crear_Cuenta extends AppCompatActivity {

    EditText CAMPO_NOMBRE;
    EditText CAMPO_APE_P;
    EditText CAMPO_APE_M;
    EditText CAMPO_CORREO;
    EditText CAMPO_USUARIO;
    EditText CAMPO_CONTRASENA;
    EditText CAMPO_CONTRASENA2;
    EditText CAMPO_N_AFILIACION;
    EditText TextDate;
    RadioButton R_MASCULINO;
    RadioButton R_FEMENINO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_cuenta);

        CAMPO_NOMBRE = (EditText) findViewById(R.id.txtNombre);
        CAMPO_APE_P = (EditText) findViewById(R.id.txtApeP);
        CAMPO_APE_M = (EditText) findViewById(R.id.txtApeM);
        CAMPO_N_AFILIACION = (EditText) findViewById(R.id.txtNumA);
        R_MASCULINO = (RadioButton) findViewById(R.id.radioM);
        R_FEMENINO = (RadioButton) findViewById(R.id.radioF);
        CAMPO_CORREO = (EditText) findViewById(R.id.txtCorreo);
        CAMPO_USUARIO = (EditText) findViewById(R.id.txtUsuarioN);
        CAMPO_CONTRASENA = (EditText) findViewById(R.id.txtPass);
        CAMPO_CONTRASENA2 = (EditText) findViewById(R.id.txtPass2);
    }

    public void onClickGuardarUsuario(View view)
    {
        String URL = "http://192.168.1.72/WebServiceHospitales/Insertar_Pacientes.php";
        String Nombre = CAMPO_NOMBRE.getText().toString();
        String Apellido_P = CAMPO_APE_P.getText().toString();
        String Apellido_M = CAMPO_APE_M.getText().toString();
        String Fecha_N = TextDate.getText().toString();
        String N_Afiliacion = CAMPO_N_AFILIACION.getText().toString();
        String Sexo = "";
        if (R_FEMENINO.isChecked())
        {
            Sexo = "Femenino";
        }
        else
        {
            Sexo = "Masculino";
        }

        String Correo = CAMPO_CORREO.getText().toString();
        String Usuario = CAMPO_USUARIO.getText().toString();
        String Pass1 = CAMPO_CONTRASENA.getText().toString();
        String Pass2 = CAMPO_CONTRASENA2.getText().toString();

        String finalSexo = Sexo;

        if(Pass1.equals(Pass2))
        {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.show();
            StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response)
                {
                    if (response.contains("Datos Ingresados Correctamente"))
                    {
                        progressDialog.dismiss();
                        Toast toast = Toast.makeText(Crear_Cuenta.this, "Datos Igresados Correctamente", Toast.LENGTH_SHORT);
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
                    Toast toast = Toast.makeText(Crear_Cuenta.this, "Algo salió mal", Toast.LENGTH_SHORT);
                    toast.show();
                    System.out.println(error.getMessage());
                }
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String>params = new HashMap<String, String>();
                    params.put("ID_PACIENTE", N_Afiliacion);
                    params.put("NOMBRES", Nombre);
                    params.put("APELLIDO_P", Apellido_P);
                    params.put("APELLIDO_M", Apellido_M);
                    params.put("FECHA_N", Fecha_N);
                    params.put("SEXO", finalSexo);
                    params.put("CORREO", Correo);
                    params.put("USUARIO", Usuario);
                    params.put("CONTRASENA", Pass1);
                    params.put("TIPO_USUARIO", "PACIENTE");
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(Crear_Cuenta.this);
            requestQueue.add(request);
        }
        else
        {
            Toast toast = Toast.makeText(Crear_Cuenta.this, "¡Las contraseñas no son iguales!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void ShowDate(View v)
    {
        int dia, mes, ano;
        TextDate = (EditText) findViewById(R.id.txtFechaN);
        final Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        ano = c.get(Calendar.YEAR);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int Ano, int Mes, int Dia) {
                    if(Mes+1 < 9)
                    {
                        TextDate.setText(Ano + "-0" + (Mes+1) + "-" + Dia);
                    }
                    else
                    {
                        TextDate.setText(Ano + "-" + (Mes+1) + "-" + Dia);
                    }
                }
            }, ano, mes, ano);
            datePickerDialog.show();
        }
    }
}