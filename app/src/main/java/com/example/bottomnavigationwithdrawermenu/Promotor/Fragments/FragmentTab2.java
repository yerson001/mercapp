package com.example.bottomnavigationwithdrawermenu.Promotor.Fragments;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bottomnavigationwithdrawermenu.Promotor.Adapters.PriceAdapter;
import com.example.bottomnavigationwithdrawermenu.Promotor.Entities.prices;
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

public class FragmentTab2 extends Fragment {


    ArrayList<String> marcas_competidores = new ArrayList<>();
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    GpsTracker gpsTracker;
    AutoCompleteTextView autoCompleteTxtMarca;

    String product_marca;
    EditText direccion;
    EditText cliente;
    EditText obser;


    ArrayAdapter<String> adapterMarca;
    prices pri;

    public static ArrayList<prices> pricesArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PriceAdapter adapter;


    private String mParam1;
    private String mParam2;

    private static final String SERVER_URL = "https://emaprod.emaransac.com/direccion/init.php";
    //private static final String SERVER_URL = "https://emaransac.com/mercapp/init.php";
    private RequestQueue requestQueue;

    public FragmentTab2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tab2, container, false);

        pricesArrayList.clear();
        marcas_competidores.clear();
        product_marca = "";
        requestQueue = Volley.newRequestQueue(getContext());
        String a_lati = "0";
        String a_long = "0";
        a_lati = getLocs(1);
        a_long = getLocs(2);
        GeoReversa(Double.parseDouble(a_lati), Double.parseDouble(a_long));
        retrieveData("https://emaransac.com/mercapp/promoter/get_competitor_brands.php");


        autoCompleteTxtMarca = rootView.findViewById(R.id.marca_tab_txt);
        adapterMarca = new ArrayAdapter<>(requireContext(), R.layout.distrib_item, marcas_competidores);
        autoCompleteTxtMarca.setAdapter(adapterMarca);

        autoCompleteTxtMarca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                product_marca = (String) parent.getItemAtPosition(position);
                Toast.makeText(getContext(), product_marca, Toast.LENGTH_SHORT).show();
                retrieveData_pro("https://emaransac.com/mercapp/promoter/get_competitor_product.php", product_marca);
                retrieveData("https://emaransac.com/mercapp/promoter/get_competitor_brands.php");
            }
        });

        direccion = rootView.findViewById(R.id.ubicacion_txt);
        cliente = rootView.findViewById(R.id.cliente_tab_txt);
        obser = rootView.findViewById(R.id.observaciones_tab_txt);
        Button save = rootView.findViewById(R.id.btn_guardar_ty);


        requestQueue = Volley.newRequestQueue(getContext());

        ImageButton imgbtn = rootView.findViewById(R.id.location_btn);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String a_lati = getLocs(1);
                String a_long = getLocs(2);
                a_lati = getLocs(1);
                a_long = getLocs(2);
                GeoReversa(Double.parseDouble(a_lati), Double.parseDouble(a_long));
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    //Toast.makeText(getContext()," buttom",Toast.LENGTH_SHORT).show();
                insertData_prod();
            }
        });

        recyclerView = rootView.findViewById(R.id.recycler_price_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PriceAdapter(getContext(), pricesArrayList);
        recyclerView.setAdapter(adapter);

        return rootView;
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
                        direccion.setText(response.toUpperCase());
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
                        pricesArrayList.clear();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String exito = jsonObject.getString("exito");
                            JSONArray jsonArray = jsonObject.getJSONArray("datos");
                            if (exito.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    String name_product = object.getString("name_product");
                                    Log.d("Retrival 2 ", id + "  " + name_product);
                                    pri = new prices(id, name_product,"0.0");
                                    pricesArrayList.add(pri);
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


    private void insertData_prod() {
        try { //Request Permission if not permitted
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        final String marca = product_marca;
        final String ubicacion = direccion.getText().toString().trim();//obligatorio
        final String cliente_ = cliente.getText().toString().trim();//opcional
        final String observaciones = obser.getText().toString().trim();//opcional


        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("cargando...");

        if (ubicacion.isEmpty()) {
            Toast.makeText(getContext(), "Ingrese Ubicación", Toast.LENGTH_SHORT).show();
            return;
        }
        if (marca.isEmpty()) {
            Toast.makeText(getContext(), "Ingrese Marca", Toast.LENGTH_SHORT).show();
            return;
        } else {
            List<prices> lista = adapter.getCheckedItemsWithPrices();
            String[] arraypolvos = new String[lista.size()];
            String[] arraypolvos1 = new String[lista.size()];


            List<prices> checkedItemsWithPrices = adapter.getCheckedItemsWithPrices();
            int i = 0;
            for (prices item : checkedItemsWithPrices) {
                Log.d("CheckedItemWithPrice", "ID: " + item.getId() + ", Name: " + item.getProducto() + ", Price: " + item.getPrecioPri());
                arraypolvos[i] = item.getProducto();
                arraypolvos1[i] = item.getPrecioPri();
                i++;
            }

            String a_lat = "0";
            String a_lon = "0";
            a_lat = getLocs(1);
            a_lon = getLocs(2);
            JSONArray jsonArray = new JSONArray(Arrays.asList(arraypolvos));

            JSONArray jsonArray1 = new JSONArray(Arrays.asList(arraypolvos1));


            String finalA_lon = a_lon;
            String finalA_lat = a_lat;
            StringRequest request = new StringRequest(Request.Method.POST, "https://emaransac.com/mercapp/promoter/insert_price_report.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (response.equalsIgnoreCase("Se guardo correctamente.")) {
                                Toast.makeText(getContext(), "Se guardo correctamente.", Toast.LENGTH_SHORT).show();
                                //mostrarToastPersonalizado();
                                progressDialog.dismiss();
                                //********************************* NO INTENT MAIN *************************************************
                                //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                //finish();
                            } else {
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
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("location", ubicacion);
                    params.put("client", cliente_);
                    params.put("brand", marca);
                    params.put("name", jsonArray.toString());
                    params.put("price", jsonArray1.toString());
                    params.put("observation", observaciones);
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

                    //direccion.setText("");
                    product_marca = "";
                    cliente.setText("");
                    obser.setText("");
                    pricesArrayList.clear();
                    retrieveData_pro("https://emaransac.com/mercapp/promoter/get_competitor_product.php", product_marca);
				adapter.notifyDataSetChanged();
                }
            });
        }
    }
}