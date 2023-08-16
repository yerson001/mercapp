package com.example.bottomnavigationwithdrawermenu.Promotor.Fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bottomnavigationwithdrawermenu.Mercaderista.Entity.Summary;
import com.example.bottomnavigationwithdrawermenu.Mercaderista.SummaryActivity;
import com.example.bottomnavigationwithdrawermenu.Promotor.Adapters.PfAdapter;
import com.example.bottomnavigationwithdrawermenu.Promotor.Entities.Frescos;
import com.example.bottomnavigationwithdrawermenu.R;
import com.example.bottomnavigationwithdrawermenu.Ubication.GpsTracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentTab1 extends Fragment implements PfAdapter.PfAdapterCallback{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    /*****
     * varibles to simple quiz to promoter this
     * */

    // implement remenber fuction
    String question1,question2,question3;
    private CheckBox checkBoxDiariamente;
    private CheckBox checkBoxSemanalmente;
    private CheckBox checkBoxQuincenalmente;
    private CheckBox checkBoxMensualmente;
    private CheckBox checkBoxRaraVez;
    private CheckBox nunca_check;
    AutoCompleteTextView autoCompleteTxtMarca;
    AutoCompleteTextView autoCompleteTxtCondimentos;

    private int opcion_quiz;
    private static final String SERVER_URL = "https://emaprod.emaransac.com/direccion/init.php";
    private RequestQueue requestQueue;
    ArrayList<String>  marcas_competidores = new ArrayList<>();

    String[] Distribuidores = {"ADRIEL MAMANI-AREQUIPA",
                            "ROMMEL",
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

    ArrayList<String>  polvosarr_pro = new ArrayList<>();
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterDistribuidores;

    AutoCompleteTextView autoCompleteTxtcat;
    ArrayAdapter<String> adapterCategoria;
    public static ArrayList<Frescos> pro_polvos = new ArrayList<>();

    private RecyclerView recyclerView;

    private PfAdapter adapter;
    Frescos frecos;
    Button btn_insert;
    ImageButton preguntas;
    ImageButton summar;
    String Distribuidor;
    String CategoriA;
    EditText txtCliente, txtTelefono, txtDireccion,txtNombreComercial,txtVentas,txtObservaciones;
    EditText txtLocation;

    private CheckBox checkBoxExhibidor;
    private CheckBox checkBoxPop;
    private PfAdapter pfAdapter;

    GpsTracker gpsTracker;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);


//********************INIT SETTING ****************
        pro_polvos.clear();
        marcas_competidores.clear();
        polvosarr_pro.clear();
        Distribuidor="";
        question1="";
        question2="";
        question3="";
        CategoriA="";
        opcion_quiz = 5;
        retrieveData("https://emaransac.com/mercapp/promoter/get_competitor_brands.php");
        retrieveData_pro("https://emaransac.com/mercapp/promoter/get_competitor_product.php","BATAN");
        requestQueue = Volley.newRequestQueue(getContext());
        String a_lati = "0";
        String a_long = "0";
        a_lati = getLocs(1);
        a_long = getLocs(2);


        GeoReversa(Double.parseDouble(a_lati),Double.parseDouble(a_long));


        //********************INIT SETTING ****************


/*
        for (int i = 1; i < polvosarr.length; i++) {
            frecos = new Frescos(Integer.toHexString(i), polvosarr[i]);
            pro_polvos.add(frecos);
        }
        */

        ImageButton  imgbtn = rootView.findViewById(R.id.location_btn_tab1);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a_lati = getLocs(1);
                String a_long = getLocs(2);
                a_lati = getLocs(1);
                a_long = getLocs(2);

                GeoReversa(Double.parseDouble(a_lati),Double.parseDouble(a_long));
            }
        });

        pfAdapter = new PfAdapter(getContext());
        pfAdapter.setCallback(this);


        autoCompleteTxt = rootView.findViewById(R.id.distribuidor_txt);
        adapterDistribuidores = new ArrayAdapter<>(requireContext(), R.layout.distrib_item, Distribuidores);
        autoCompleteTxt.setAdapter(adapterDistribuidores);


        autoCompleteTxtcat = rootView.findViewById(R.id.categoria_txt);
        adapterCategoria = new ArrayAdapter<>(requireContext(), R.layout.distrib_item, categorias);
        autoCompleteTxtcat.setAdapter(adapterCategoria);


        recyclerView = rootView.findViewById(R.id.mypolvos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //adapter = new PfAdapter(getContext(), pro_polvos,pro_polvos);
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

        txtLocation = rootView.findViewById(R.id.location_txt);

        checkBoxExhibidor = rootView.findViewById(R.id.exhibidor);
        checkBoxPop = rootView.findViewById(R.id.pop);



        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                // Acción que se debe mandar a los campos editables
                String item = parent.getItemAtPosition(position).toString();
                Distribuidor = item;
                //Toast.makeText(requireContext(), " " + item, Toast.LENGTH_SHORT).show();
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

        preguntas = rootView.findViewById(R.id.preguntas_txt);
        preguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPreguntas();

                Log.d("question1",question1);
                Log.d("question2",question2);
                Log.d("question3",question3);
            }
        });


        summar = rootView.findViewById(R.id.btnSumar);
        summar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"SUM: "+txtVentas.getText(),Toast.LENGTH_SHORT).show();
                double valor = adapter.calculateTotalSales();
                txtVentas.setText(String.valueOf(valor));

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

        boolean isCheckedEx = checkBoxExhibidor.isChecked();
        boolean isCheckedPop = checkBoxPop.isChecked();


        final String ventas = txtVentas.getText().toString().trim();
        final String observaciones = txtObservaciones.getText().toString().trim();

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("cargando...");

        if(Distribuidor.isEmpty()){
            Toast.makeText(getContext(), "Ingrese Distribuidor", Toast.LENGTH_SHORT).show();
            return;
        }
        if(cliente.isEmpty()){
            Toast.makeText(getContext(), "Ingrese Cliente", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(direccion.isEmpty()){
            Toast.makeText(getContext(), "Ingrese Dirección", Toast.LENGTH_SHORT).show();
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
            JSONArray jsonArray = new JSONArray(Arrays.asList(arraypolvos));

            progressDialog.show();
            String a_lat = "0";
            String a_lon = "0";
            a_lat = getLocs(1);
            a_lon = getLocs(2);


            String finalA_lon = a_lon;
            String finalA_lat = a_lat;
            StringRequest request = new StringRequest(Request.Method.POST, "https://emaransac.com/mercapp/promoter/insert_report.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equalsIgnoreCase("Se guardo correctamente.")){
                                //Toast.makeText(getContext(), "Se guardo correctamente.", Toast.LENGTH_SHORT).show();
                                //mostrarToastPersonalizado();
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

                    //Log.d("------------------TAG------1--------------", jsonArray.toString());
                    //Log.d("------------------TAG-------2-------------", jsonArray2.toString());
                    params.put("distribuidor",distribuidor);
                    params.put("cliente",cliente);
                    params.put("telefono",telefono);
                    params.put("direccion",direccion);
                    params.put("nombrecomercial",nombrecomercial);
                    params.put("categoriasfinal",CategoriA);
                    params.put("polvosarr", jsonArray.toString());
                    //params.put("frescosarr", jsonArray2.toString());
                    params.put("isCheckedEx",valorCadena1);
                    params.put("isCheckedPop",valorCadena2);
                    params.put("ventas",ventas);
                    params.put("observaciones",observaciones);
                    params.put("longitud", finalA_lon);
                    params.put("latitud", finalA_lat);
                    params.put("question1",question1);
                    params.put("question2",question2);
                    params.put("question3",question3);
                    params.put("opcion_quiz", String.valueOf(opcion_quiz));
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

                    txtCliente.setText("");
                    txtTelefono.setText("");
                    txtDireccion.setText("");
                    txtNombreComercial.setText("");
                    checkBoxExhibidor.setChecked(false);
                    checkBoxPop.setChecked(false);
                    Distribuidor = "";
                    CategoriA = "";
                    autoCompleteTxt.setText("");
                    autoCompleteTxtcat.setText("");
                    txtVentas.setText("");
                    txtObservaciones.setText("");
                    pro_polvos.clear();
                    retrieveData_pro("https://emaransac.com/mercapp/promoter/get_competitor_product.php","BATAN");
                    question1="";
                    question2="";
                    question3="";
                    opcion_quiz=5;
                    adapter.notifyDataSetChanged();
                }
            });
        }
/*
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
        );*/

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
    private void mostrarToastPersonalizado() {
        LayoutInflater inflater = getLayoutInflater();
        View toastLayout = inflater.inflate(R.layout.toast_customg, null);

        // Configurar el texto del Toast personalizado
        TextView textView = toastLayout.findViewById(R.id.text_view);
        textView.setText("✅ Registro Guardado");

        // Crear y mostrar el Toast personalizado
        Toast toast = new Toast(getContext());
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 40); // Establecer la posición en la parte superior y centrada
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(toastLayout);
        toast.show();
    }

    private void showPreguntas() {

        final Dialog dialogp = new Dialog(getContext());
        dialogp.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogp.setContentView(R.layout.bottomsheetlayoutquiz);

        ImageView cancelButton = dialogp.findViewById(R.id.cancelButton);
        ImageView cancelButtonp = dialogp.findViewById(R.id.cancelButtonp);

        checkBoxDiariamente = dialogp.findViewById(R.id.checkBoxDiariamente);
        checkBoxSemanalmente = dialogp.findViewById(R.id.checkBoxSemanalmente);
        checkBoxQuincenalmente = dialogp.findViewById(R.id.checkBoxQuincenalmente);
        checkBoxMensualmente = dialogp.findViewById(R.id.checkBoxMensualmente);
        checkBoxRaraVez = dialogp.findViewById(R.id.checkBoxRaraVez);
        nunca_check = dialogp.findViewById(R.id.nunca);

        CheckBox[] checkBoxesArray = new CheckBox[6];
        checkBoxesArray[0] = checkBoxDiariamente;
        checkBoxesArray[1] = checkBoxSemanalmente;
        checkBoxesArray[2] = checkBoxQuincenalmente;
        checkBoxesArray[3] = checkBoxMensualmente;
        checkBoxesArray[4] = checkBoxRaraVez;
        checkBoxesArray[5] = nunca_check;

        // Agregar el listener a todos los CheckBox
        checkBoxDiariamente.setOnCheckedChangeListener(listener);
        checkBoxSemanalmente.setOnCheckedChangeListener(listener);
        checkBoxQuincenalmente.setOnCheckedChangeListener(listener);
        checkBoxMensualmente.setOnCheckedChangeListener(listener);
        checkBoxRaraVez.setOnCheckedChangeListener(listener);

        // the best option is create a function but i dont have time for this



        EditText q1 = dialogp.findViewById(R.id.question1);
        EditText q2 = dialogp.findViewById(R.id.question2);
        EditText q3 = dialogp.findViewById(R.id.question3);
        checkBoxesArray[opcion_quiz].setChecked(true);


        q1.setText(question1);
        q2.setText(question2);
        q3.setText(question3);

        ArrayAdapter<String> adapterMarca;
        ArrayAdapter<String> condimentos;

        //***********************
        autoCompleteTxtMarca = dialogp.findViewById(R.id.marca_quiz_txt);
        adapterMarca = new ArrayAdapter<>(requireContext(), R.layout.distrib_item, marcas_competidores);
        autoCompleteTxtMarca.setAdapter(adapterMarca);
        autoCompleteTxtMarca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCondimento = (String) parent.getItemAtPosition(position);
                Toast.makeText(getContext(),selectedCondimento,Toast.LENGTH_SHORT).show();

                // Obtener el contenido actual del EditText
                String contenidoActual = q2.getText().toString();

                // Concatenar el contenido nuevo al contenido actual y agregar una coma
                String contenidoFinal = contenidoActual + selectedCondimento + " - ";

                // Establecer el contenido final en el EditText
                q2.setText(contenidoFinal);
            }
        });
        //*******************************

        autoCompleteTxtCondimentos = dialogp.findViewById(R.id.condimentos);
        condimentos = new ArrayAdapter<>(requireContext(), R.layout.product_item, polvosarr_pro);
        autoCompleteTxtCondimentos.setAdapter(condimentos);

        autoCompleteTxtCondimentos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedCondimento = (String) parent.getItemAtPosition(position);
                Toast.makeText(getContext(),selectedCondimento,Toast.LENGTH_SHORT).show();

                // Obtener el contenido actual del EditText
                String contenidoActual = q3.getText().toString();

                // Concatenar el contenido nuevo al contenido actual y agregar una coma
                String contenidoFinal = contenidoActual + selectedCondimento + " - ";

                // Establecer el contenido final en el EditText
                q3.setText(contenidoFinal);
            }
        });




        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question1 = q1.getText().toString();
                question2 = q2.getText().toString();
                question3 = q3.getText().toString();
                dialogp.dismiss();
            }
        });

        cancelButtonp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogp.dismiss();
                question1 = q1.getText().toString();
                question2 = q2.getText().toString();
                question3 = q3.getText().toString();
            }
        });

        dialogp.show();
        dialogp.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogp.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogp.getWindow().setGravity(Gravity.BOTTOM);
    }



    @Override
    public void onValuesUpdated(double accumulatedValue) {
        double ventas_totales=0;
        ventas_totales+=accumulatedValue;
        txtVentas.setText(ventas_totales+"");
        Toast.makeText(getContext(),"accumulate: "+ventas_totales+" ",Toast.LENGTH_SHORT).show();
    }

    private final CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            // Desactivar todos los CheckBox
            checkBoxDiariamente.setChecked(false);
            checkBoxSemanalmente.setChecked(false);
            checkBoxQuincenalmente.setChecked(false);
            checkBoxMensualmente.setChecked(false);
            checkBoxRaraVez.setChecked(false);

            // Activar solo el CheckBox que fue seleccionado
            buttonView.setChecked(isChecked);

            if(checkBoxDiariamente.isChecked()){
                opcion_quiz = 0;
            } else if (checkBoxSemanalmente.isChecked()) {
                opcion_quiz = 1;
            } else if (checkBoxQuincenalmente.isChecked()) {
                opcion_quiz = 2;
            }else if(checkBoxMensualmente.isChecked()){
                opcion_quiz = 3;
            }else{
                opcion_quiz = 4;
            }
            //Toast.makeText(getContext(),"num: "+opcion_quiz,Toast.LENGTH_LONG).show();
        }
    };

    public void retrieveData(String url) {
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        marcas_competidores.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String exito = jsonObject.getString("exito");
                            JSONArray jsonArray = jsonObject.getJSONArray("datos");
                            if (exito.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    String marcas = object.getString("brand_name");

                                    Log.d("Retrival ", marcas);
                                    marcas_competidores.add(marcas);
                                }
                                //summaryAdapter.notifyDataSetChanged(); // Notificar cambios en el adaptador después de agregar los elementos
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    public void retrieveData_pro(String apiUrl, String brand) {
        String url = apiUrl + "?marca=" + URLEncoder.encode(brand);

        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        polvosarr_pro.clear();
                        pro_polvos.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String exito = jsonObject.getString("exito");
                            JSONArray jsonArray = jsonObject.getJSONArray("datos");
                            if (exito.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    String name_product = object.getString("name_product");
                                    String reference_code = object.getString("reference_code");
                                    String barcode = object.getString("barcode");
                                    Log.d("Retrival 2 ",id+"  "+ name_product);
                                    polvosarr_pro.add(name_product);
                                    frecos = new Frescos(id,name_product);
                                    pro_polvos.add(frecos);
                                }
                                adapter.notifyDataSetChanged(); // Notificar cambios en el adaptador después de agregar los elementos
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }

    private void GeoReversa(double latitud, double longitud) {
        // Construye la URL completa con los parámetros de latitud y longitud
        String urlWithParams = SERVER_URL + "?latitud=" + latitud + "&longitud=" + longitud;

        StringRequest request = new StringRequest(Request.Method.GET, urlWithParams,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Aquí puedes imprimir la respuesta recibida desde el servidor
                        Log.d("RESPONSE", "Respuesta del servidor: " + response);
                        txtLocation.setText("    "+response.toUpperCase());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Aquí puedes manejar los errores en caso de que la petición falle
                        Log.e("ERROR", "Error en la petición: " + error.getMessage());
                    }
                });

        requestQueue.add(request);
    }

}