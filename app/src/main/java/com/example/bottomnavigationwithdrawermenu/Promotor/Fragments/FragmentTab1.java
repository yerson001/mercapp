package com.example.bottomnavigationwithdrawermenu.Promotor.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bottomnavigationwithdrawermenu.Promotor.Adapters.PfAdapter;
import com.example.bottomnavigationwithdrawermenu.Promotor.Entities.Frescos;
import com.example.bottomnavigationwithdrawermenu.R;
import com.example.bottomnavigationwithdrawermenu.Ubication.GpsTracker;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentTab1 extends Fragment  {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String[] Distribuidores = {"ADRIEL MAMANI-AREQUIPA",
                               "EMUNAH PERU S.A.C",
                                "FERNANDO FLORES RAMOS",
                                "JOSE LUIS CARRASCO-CAMANA",
                                "DORA MAMANI QUISPE-TACNA",
                                "DISTRIBUCIONES Y REP DELFÍN Y ROSA",
                                "T&C COMERCIALIZADORA",
                                "CORPORACION VALLESUR S.A.C",
                                "DISTRIBUCIONES PRODESUR S.A.C",
                                "CACERES PH DISTRIBUCIONES S.A.C",
                                "DILSOR",
                                "RG DIGO E.I.R.L",
                                "GLOMISEB",
                                "COMERSUCHI",
                                "INCAMONT",
                                "ALM Y DIS HUAMANGA",
                                "TULIP MART"
    };

    String[] categorias = {"Bodega",
                            "Minimarket",
                            "Supermercado",
                            "Especiería",
                            "Puesto de Mercado",
                            "Tienda de Abarrotes",
                            "Food Truck","Puesto de Comida",
                            "Snack",
                            "Carniceria",
                            "Pizzeria",
                            "Otros"};
    String[] polvosarr = {"------SELECCIONE------",
            "SAZONADOR COMPLETO  GIGANTE X 42 SBS",
            "COMINO MOLIDO GIGANTE X 42 SBS",
            "PIMIENTA BATAN GIGANTE X 42 SBS",
            "PALILLO BATAN  GIGANTE X 42 SBS",
            "TUCO SAZON SALSA BATAN GIGANTE X 42 SBS",
            "AJO BATAN GIGANTE X 42 SBS",
            "CANELA MOLIDA GIGANTE X 42 SBS",
            "EL VERDE BATAN GIGANTE X 42 SBS",
            "KION MOLIDO BATAN GIGANTE X 42 SBS"
            ,"OREGANO SELECTO BATAN X 42 SBS",
            "EL VERDE BATAN GIGANTE x 27 SBS",
            "AJI PANCA FRESCO BATAN x 24 SBS",
            "AJI AMARILLO FRESCO BATAN x24 SBS",
            "AJO FRESCO BATAN x 24 SBS",
            "CULANTRO FRESCO BATAN x 24 SBS"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterDistribuidores;

    AutoCompleteTextView autoCompleteTxtcat;
    ArrayAdapter<String> adapterCategoria;
    public static ArrayList<Frescos> pro_polvos = new ArrayList<>();

    private RecyclerView recyclerView, recyclerView2;

    private PfAdapter adapter;


    Button btn_insert;
    String Distribuidor;
    String CategoriA;
    EditText txtCliente, txtTelefono, txtDireccion,txtNombreComercial,txtVentas,txtObservaciones;

    private CheckBox checkBoxExhibidor;
    private CheckBox checkBoxPop;

    GpsTracker gpsTracker;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);
        Frescos frecos;
        pro_polvos.clear();


        for (int i = 1; i < polvosarr.length; i++) {
            frecos = new Frescos(Integer.toHexString(i), polvosarr[i]);
            pro_polvos.add(frecos);
        }


        autoCompleteTxt = rootView.findViewById(R.id.distribuidor_txt);
        adapterDistribuidores = new ArrayAdapter<>(requireContext(), R.layout.distrib_item, Distribuidores);
        autoCompleteTxt.setAdapter(adapterDistribuidores);


        autoCompleteTxtcat = rootView.findViewById(R.id.categoria_txt);
        adapterCategoria = new ArrayAdapter<>(requireContext(), R.layout.distrib_item, categorias);
        autoCompleteTxtcat.setAdapter(adapterCategoria);


        recyclerView = rootView.findViewById(R.id.mypolvos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PfAdapter(getContext(), pro_polvos,pro_polvos);
        recyclerView.setAdapter(adapter);
        //adapter.setCallback(getContext());

        //************SET ALL ITEMS TO FORM ******************
        txtCliente = rootView.findViewById(R.id.cliente_txt);
        txtTelefono = rootView.findViewById(R.id.telefono_txt);
        txtDireccion = rootView.findViewById(R.id.direccion_txt);
        txtNombreComercial = rootView.findViewById(R.id.nombre_co);
        txtVentas = rootView.findViewById(R.id.ventas_txt);
        txtObservaciones = rootView.findViewById(R.id.observaciones_txt);

        checkBoxExhibidor = rootView.findViewById(R.id.exhibidor);
        checkBoxPop = rootView.findViewById(R.id.pop);



        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                // Acción que se debe mandar a los campos editables
                String item = parent.getItemAtPosition(position).toString();
                Distribuidor = item;
                Toast.makeText(requireContext(), " " + item, Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTxtcat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                // Acción que se debe mandar a los campos editables
                String item = parent.getItemAtPosition(position).toString();
                CategoriA = item;
                Toast.makeText(requireContext(), " " + item, Toast.LENGTH_SHORT).show();
            }
        });

        btn_insert = rootView.findViewById(R.id.btn_guardar_acompa);
        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertData();
            }
        });

        return rootView;
    }

    private void insertData() {
        try { //Request Permission if not permitted
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String distribuidor = Distribuidor;
        final String cliente = txtCliente.getText().toString().trim();//obligatorio
        final String telefono = txtTelefono.getText().toString().trim();//opcional
        final String direccion = txtDireccion.getText().toString().trim();//obligatorio
        final String nombrecomercial = txtNombreComercial.getText().toString().trim();//opcional
        final String categoria = CategoriA;


        //final String polvos = polvos_list.getText().toString().trim();
        //final String frescos = frescos_list.getText().toString().trim();

        boolean isCheckedEx = checkBoxExhibidor.isChecked();
        boolean isCheckedPop = checkBoxPop.isChecked();


        final String ventas = txtVentas.getText().toString().trim();
        final String observaciones = txtObservaciones.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("cargando...");

        if(cliente.isEmpty()){
            Toast.makeText(getContext(), "Ingrese Cliente", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(direccion.isEmpty()){
            Toast.makeText(getContext(), "Ingrese Teléfono", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(categoria.isEmpty()){
            Toast.makeText(getContext(), "Ingrese Categoria", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(ventas.isEmpty()){
            Toast.makeText(getContext(), "Ingrese Ventas", Toast.LENGTH_SHORT).show();
            return;
        }
        else{

            //List<Frescos> lista = adapter.getPfList_r();
            List<Frescos> lista = adapter.getCheckedItems();
            String[] arraypolvos = new String[lista.size()];
            int i = 0;
            for (Frescos f : lista) {
                arraypolvos[i] = f.getName();
                i++;
            }


            List<Frescos> lista2 = adapter.getCheckedItems();
            String[] arraypolvos2 = new String[lista2.size()];
            int i2 = 0;
            for (Frescos f2 : lista2) {
                arraypolvos2[i2] = f2.getName();
                i2++;
            }

            JSONArray jsonArray = new JSONArray(Arrays.asList(arraypolvos));
            JSONArray jsonArray2 = new JSONArray(Arrays.asList(arraypolvos2));


            progressDialog.show();
            String a_lat = "0";
            String a_lon = "0";
            a_lat = getLocs(1);
            a_lon = getLocs(2);


            String finalA_lon = a_lon;
            String finalA_lat = a_lat;
            StringRequest request = new StringRequest(Request.Method.POST, "https://emaransac.com/android/insertar_reporte_promotor.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equalsIgnoreCase("Se guardo correctamente.")){
                                Toast.makeText(getContext(), "Se guardo correctamente.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                                //********************************* NO INTENT MAIN *************************************************
                                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                //finish();
                            }
                            else{
                                Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
            ){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<String,String>();

                    String valorCadena1 = String.valueOf(isCheckedEx);
                    String valorCadena2 = String.valueOf(isCheckedPop);

                    Log.d("------------------TAG------1--------------", jsonArray.toString());
                    Log.d("------------------TAG-------2-------------", jsonArray2.toString());





                    params.put("distribuidor",distribuidor);
                    params.put("cliente",cliente);
                    params.put("telefono",telefono);
                    params.put("direccion",direccion);
                    params.put("nombrecomercial",nombrecomercial);
                    params.put("categoriasfinal",CategoriA);
                    params.put("polvosarr", jsonArray.toString());
                    params.put("frescosarr", jsonArray2.toString());
                    params.put("isCheckedEx",valorCadena1);
                    params.put("isCheckedPop",valorCadena2);
                    params.put("ventas",ventas);
                    params.put("observaciones",observaciones);
                    params.put("longitud", finalA_lon);
                    params.put("latitud", finalA_lat);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            requestQueue.add(request);

            requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
                @Override
                public void onRequestFinished(Request<Object> request) {
                    // Redirigir a PromotorActivity después de la inserción exitosa
                    //***********************************we need try *********************************************************
                    //Intent intent = new Intent(ProVMainActivity.this, getContext());
                    //startActivity(intent);
                    //finish();
                }
            });
        }

        Log.d("TAG--------------------", "distrinuidor: " + distribuidor +
                "  cliente: " + cliente +
                "  Telefono: " + telefono +
                "  Direccion: " + direccion +
                "  NombreCom: " + nombrecomercial +
                "  Categoria: " + CategoriA +
                //"  Polvos: " + polvos +
                //"  Frescos: " + frescos +
                "  Exhibidor: " + isCheckedEx +
                "  Pop: " + isCheckedPop +
                "  Ventas" + ventas +
                "  Observaciones: " + observaciones+
                "  Long: " + ventas +
                "  Latitud: " + observaciones
        );

    }

    public String getLocs(int ID) { //Get Current Lat and Lon 1=lat, 2=lon
        String asd_lat = "";
        String asd_lon = "";
        gpsTracker = new GpsTracker(getContext());
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            asd_lat = String.valueOf(latitude);
            asd_lon = String.valueOf(longitude);
        } else {
            gpsTracker.showSettingsAlert();
        }
        if (ID == 1) {
            return asd_lat;
        } else if (ID == 2) {
            return asd_lon;
        } else {
            return "0";
        }
    }

}