package com.example.bottomnavigationwithdrawermenu.Promotor;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bottomnavigationwithdrawermenu.MainActivity;
import com.example.bottomnavigationwithdrawermenu.Promotor.Adapters.SummaryProAdapter;
import com.example.bottomnavigationwithdrawermenu.Promotor.Entities.detalle;
import com.example.bottomnavigationwithdrawermenu.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SummaryProActivity extends AppCompatActivity {

    private detalle detalle_;
    private SummaryProAdapter summaryAdapter;
    private TextView selectedDateTV;

    public static ArrayList<detalle> SumaryArrayList = new ArrayList<>();

    //https://emaransac.com/mercapp/promoter/summary_report.php
    String url = "https://emaransac.com/mercapp/promoter/summary_report.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary_pro);
        retrieveData(url);


        SummaryProAdapter adapter = new SummaryProAdapter(this, SumaryArrayList);
        adapter.setOnItemClickListener(new SummaryProAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Acciones a realizar cuando se hace clic en un elemento
                // Acciones a realizar cuando se hace clic en un elemento
                showAlertDialog(
                        SumaryArrayList.get(position).getId(),
                        SumaryArrayList.get(position).getDistribuidor(),
                        SumaryArrayList.get(position).getCliente(),
                        SumaryArrayList.get(position).getTelefono(),
                        SumaryArrayList.get(position).getDirrecion(),
                        SumaryArrayList.get(position).getNonCommercial(),
                        SumaryArrayList.get(position).getVentas(),
                        SumaryArrayList.get(position).getObservaciones()
                );
            }
        });

        summaryAdapter = adapter; // Asignar el adaptador a la variable summaryAdapter

        RecyclerView recyclerView = findViewById(R.id.recycler_view_summary);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        FloatingActionButton btn1 = findViewById(R.id.update_btn);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SummaryProActivity.this, "Actualizando...", Toast.LENGTH_SHORT).show();
                recreate();
            }
        });

        selectedDateTV = findViewById(R.id.idTVSelectedDate);

        // Agrega el listener al botón

        FloatingActionButton btn2 = findViewById(R.id.btn1_cal);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene la instancia del calendario
                final Calendar c = Calendar.getInstance();

                // Obtiene el día, mes y año actual
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // Crea el cuadro de diálogo de selección de fecha
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SummaryProActivity.this, // Reemplaza "YourActivity" con el nombre de tu actividad
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Establece la fecha seleccionada en el TextView
                                // selectedDateTV.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                String selectedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
                                Toast.makeText(getApplicationContext(), selectedDate, Toast.LENGTH_SHORT).show();
                            }
                        },
                        year, month, day);
                // Muestra el cuadro de diálogo de selección de fecha
                datePickerDialog.show();
            }
        });
    }

    private void showAlertDialog(String id, String distrib,String client,String telef,String direccion,
                                 String Nombre_comercial,String ventas,String Observacines) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SummaryProActivity.this);
        //Toast.makeText(SummaryProActivity.this, client, Toast.LENGTH_SHORT).show();


        SpannableStringBuilder spannableTitle = new SpannableStringBuilder(id + " - "+client);
        spannableTitle.setSpan(new RelativeSizeSpan(0.7f), 0, spannableTitle.length(), 0);

        builder.setTitle("   "+spannableTitle);
        // Agrega los componentes necesarios al cuadro de diálogo (EditTexts, botones, etc.)
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        EditText distribuidor = new EditText(this);
        EditText cliente = new EditText(this);
        EditText telefono = new EditText(this);
        EditText direcion = new EditText(this);
        EditText Nom_comercial = new EditText(this);
        EditText venta = new EditText(this);
        EditText obs = new EditText(this);

        //distribuidor.setText(ped);
        //cliente.setText(inv);

        // Establece la propiedad singleLine en true
        distribuidor.setSingleLine(true);
        cliente.setSingleLine(true);
        telefono.setSingleLine(true);
        direcion.setSingleLine(true);
        Nom_comercial.setSingleLine(true);
        venta.setSingleLine(true);
        obs.setSingleLine(true);


        final TextView dist = new TextView(SummaryProActivity.this);
        dist.setText("Distribuidor");
        dist.setTextColor(getResources().getColor(android.R.color.black));
        dist.setTypeface(null, Typeface.BOLD);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMarginStart(20);
        dist.setLayoutParams(params);
        linearLayout.addView(dist);
        //distribuidor.setHint("   "+distrib);
        distribuidor.setText(distrib);
        distribuidor.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        linearLayout.addView(distribuidor);
        distribuidor.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);





        final TextView cli = new TextView(SummaryProActivity.this);
        cli.setText("Cliente");
        cli.setTextColor(getResources().getColor(android.R.color.black));
        cli.setTypeface(null, Typeface.BOLD);
        LinearLayout.LayoutParams paramscli = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramscli.setMarginStart(20);
        cli.setLayoutParams(paramscli);
        linearLayout.addView(cli);
        cliente.setText(client);
        cliente.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        linearLayout.addView(cliente);




        final TextView tele = new TextView(SummaryProActivity.this);
        tele.setText("Teléfono");
        tele.setTextColor(getResources().getColor(android.R.color.black));
        tele.setTypeface(null, Typeface.BOLD);
        LinearLayout.LayoutParams paramstele = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramstele.setMarginStart(20);
        tele.setLayoutParams(paramstele);
        linearLayout.addView(tele);
        telefono.setText(telef);
        telefono.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        linearLayout.addView(telefono);


        // Configura el tipo de entrada de texto como numérico

        cliente.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

        telefono.setInputType(InputType.TYPE_CLASS_NUMBER);
        direcion.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        Nom_comercial.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);

        venta.setInputType(InputType.TYPE_CLASS_NUMBER);
        obs.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);




        final TextView dire = new TextView(SummaryProActivity.this);
        dire.setText("Dirección");
        dire.setTextColor(getResources().getColor(android.R.color.black));
        dire.setTypeface(null, Typeface.BOLD);
        LinearLayout.LayoutParams paramdire = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramdire.setMarginStart(20);
        dire.setLayoutParams(paramdire);
        linearLayout.addView(dire);
        direcion.setText(direccion);
        direcion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        linearLayout.addView(direcion);



        final TextView nomcom = new TextView(SummaryProActivity.this);
        nomcom.setText("Nombre Comercial");
        nomcom.setTextColor(getResources().getColor(android.R.color.black));
        nomcom.setTypeface(null, Typeface.BOLD);
        LinearLayout.LayoutParams paramcom = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramcom.setMarginStart(20);
        nomcom.setLayoutParams(paramcom);
        linearLayout.addView(nomcom);
        Nom_comercial.setText(Nombre_comercial);
        Nom_comercial.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        linearLayout.addView(Nom_comercial);


        final TextView vent = new TextView(SummaryProActivity.this);
        vent.setText("Venta");
        vent.setTextColor(getResources().getColor(android.R.color.black));
        vent.setTypeface(null, Typeface.BOLD);
        LinearLayout.LayoutParams paramvent = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramvent.setMarginStart(20);
        vent.setLayoutParams(paramvent);
        linearLayout.addView(vent);
        venta.setText(ventas);
        venta.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        linearLayout.addView(venta);



        final TextView obss = new TextView(SummaryProActivity.this);
        obss.setText("Observaciones");
        obss.setTextColor(getResources().getColor(android.R.color.black));
        obss.setTypeface(null, Typeface.BOLD);
        LinearLayout.LayoutParams paramobss = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramobss.setMarginStart(20);
        obss.setLayoutParams(paramobss);
        linearLayout.addView(obss);
        obs.setText(Observacines);
        obs.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        linearLayout.addView(obs);

        builder.setView(linearLayout);

        builder.setPositiveButton("Actualizar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String distribuidorvalue = distribuidor.getText().toString();
                String clientevalue = cliente.getText().toString();
                String telefonovalue = telefono.getText().toString();
                String direcionvalue = direcion.getText().toString();
                String Nom_comercialvalue = Nom_comercial.getText().toString();
                String ventacalue = venta.getText().toString();
                String obsvalue = obs.getText().toString();


                // Lógica para guardar los cambios realizados en el elemento seleccionado
                //String id, String distrib,String client,String telef,String direccion,
                //                                 String Nombre_comercial,String ventas,String Observacines

                if(distribuidorvalue.isEmpty()){
                    distribuidorvalue = distrib;
                }
                if(clientevalue.isEmpty()){
                    clientevalue = client;
                }

                if(telefonovalue.isEmpty()){
                    telefonovalue = telef;
                }
                if(direcionvalue.isEmpty()){
                    direcionvalue = direccion;
                }

                if(Nom_comercialvalue.isEmpty()){
                    Nom_comercialvalue = Nombre_comercial;
                }
                if(ventacalue.isEmpty()){
                    ventacalue = ventas;
                }
                if(obsvalue.isEmpty()){
                    obsvalue = Observacines;
                }

                //actualizar_reporte(id,distribuidorvalue,clientevalue,direcionvalue,Nom_comercialvalue,ventacalue,obsvalue);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lógica para cancelar la edición del elemento seleccionado
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void retrieveData(String url) {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        SumaryArrayList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String exito = jsonObject.getString("exito");
                            JSONArray jsonArray = jsonObject.getJSONArray("datos");
                            if (exito.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    String fecha = object.getString("fecha");
                                    String distribuidor = object.getString("distribuidor");
                                    String cliente = object.getString("cliente");
                                    String telefono = object.getString("telefono");
                                    String direccion = object.getString("direccion");
                                    String nombre_comercial = object.getString("nombre_comercial");
                                    String ventas = object.getString("ventas");
                                    String observaciones = object.getString("observaciones");



                                    //Log.d("Retrival ", img + " " + id + " " + tienda + " " + producto + " " + inventario + " " + pedido + " " + fecha_string);
                                    detalle_ = new detalle(id,fecha,distribuidor,cliente,telefono,direccion,nombre_comercial,ventas,observaciones);
                                    SumaryArrayList.add(detalle_);
                                }
                                summaryAdapter.notifyDataSetChanged(); // Notificar cambios en el adaptador después de agregar los elementos
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SummaryProActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }


    public void actualizar_reporte(String id,String Distribuidor, String Cliente,String Direccion,String NombreComercial, String Ventas,String Observaciones) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Actualizando....");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "https://emaransac.com/android/actualizar_reporte_promotor_ventas.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SummaryProActivity.this, response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(SummaryProActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                params.put("distribuidor", Distribuidor);
                params.put("cliente", Cliente);
                params.put("direccion", Direccion);
                params.put("nombrecomercial", NombreComercial);
                params.put("ventas", Ventas);
                params.put("observaciones", Observaciones);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(SummaryProActivity.this);
        requestQueue.add(request);
        retrieveData(url);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", "promotor");// In this part our send the username to mainactivity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        // Finalizar la actividad actual
        finish();
    }
}