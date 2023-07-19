package com.example.bottomnavigationwithdrawermenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText inputUsername;
    private TextInputEditText inputPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);

        try { //Request Permission if not permitted
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextInputLayout txtUsernameLayout = findViewById(R.id.txtUsername);
        TextInputLayout txtPasswordLayout = findViewById(R.id.txtPassword);


        inputUsername = (TextInputEditText) txtUsernameLayout.getEditText();
        inputPassword = (TextInputEditText) txtPasswordLayout.getEditText();

        Button btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {login();}


        });




    }

    private void login() {
        final String username = inputUsername.getText().toString().trim();
        final String password = inputPassword.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");

        if (username.isEmpty()) {
            //Toast.makeText(this, "Ingrese su Usuario", Toast.LENGTH_SHORT).show();

            LayoutInflater inflater = getLayoutInflater();
            View toastLayout = inflater.inflate(R.layout.toast_customr, findViewById(R.id.toast_layout_root));
            // Configurar el texto del Toast
            TextView textView = toastLayout.findViewById(R.id.text_view);
            textView.setText("\n           ⚠️ Ingrese su Usuario        \n");
            // Crear y mostrar el Toast personalizado
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 40)
            ; // Establecer la posición en la parte superior y centrada
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(toastLayout);
            toast.show();

            return;
        } else if (password.isEmpty()) {
            //Toast.makeText(this, "Ingrese su Contraseña", Toast.LENGTH_SHORT).show();
            LayoutInflater inflater = getLayoutInflater();
            View toastLayout = inflater.inflate(R.layout.toast_customr, findViewById(R.id.toast_layout_root));
            // Configurar el texto del Toast
            TextView textView = toastLayout.findViewById(R.id.text_view);
            textView.setText("\n           ⚠️ Ingrese su Contraseña       \n");
            // Crear y mostrar el Toast personalizado
            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 40)
            ; // Establecer la posición en la parte superior y centrada
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(toastLayout);
            toast.show();

            return;
        } else {
            progressDialog.show();

            StringRequest request = new StringRequest(Request.Method.POST, "https://emaransac.com/mercapp/login.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equalsIgnoreCase("Usuario y contraseña válidos.")) {

                                LayoutInflater inflater = getLayoutInflater();
                                View toastLayout = inflater.inflate(R.layout.toast_customg, findViewById(R.id.toast_layout_rootg));
                                // Configurar el texto del Toast
                                TextView textView = toastLayout.findViewById(R.id.text_view);
                                textView.setText("\n           ✅ Sesión Iniciada        \n");
                                // Crear y mostrar el Toast personalizado
                                Toast toast = new Toast(getApplicationContext());
                                toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 40)
                                ; // Establecer la posición en la parte superior y centrada
                                toast.setDuration(Toast.LENGTH_SHORT);
                                toast.setView(toastLayout);
                                toast.show();

                                //Toast.makeText(LoginActivity.this, "Sesión Iniciada", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra("username", username);// In this part our send the username to mainactivity
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
            requestQueue.add(request);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}