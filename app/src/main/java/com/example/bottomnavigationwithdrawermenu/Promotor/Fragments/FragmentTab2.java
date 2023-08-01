package com.example.bottomnavigationwithdrawermenu.Promotor.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bottomnavigationwithdrawermenu.Promotor.Adapters.PfAdapter;
import com.example.bottomnavigationwithdrawermenu.Promotor.Adapters.PriceAdapter;
import com.example.bottomnavigationwithdrawermenu.Promotor.Entities.Frescos;
import com.example.bottomnavigationwithdrawermenu.Promotor.Entities.prices;
import com.example.bottomnavigationwithdrawermenu.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentTab2 extends Fragment {

    String[] marcas = {
            "SIVARITA",
            "BATAN",
            "LOPESA",
            "BADIA"};

    String[] categoria = {
            "TRADICIONAL",
            "NO TRADICIONAL",
            "OTROS"};

    String[] marcasarr = {"------SELECCIONE------",
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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    AutoCompleteTextView autoCompleteTxtMarca;
    ArrayAdapter<String> adapterMarca;
    AutoCompleteTextView autoCompleteTxtCategoria;
    ArrayAdapter<String> adapterCategoria;
    prices pri;

    public static ArrayList<prices> pricesArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PriceAdapter adapter;
    EditText direccion;


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

        for (int i = 1; i < marcasarr.length; i++) {
            pri = new prices(Integer.toHexString(i), marcasarr[i]," "," "," "," ");
            pricesArrayList.add(pri);
        }


        autoCompleteTxtMarca = rootView.findViewById(R.id.marca_txt);
        adapterMarca = new ArrayAdapter<>(requireContext(), R.layout.distrib_item, marcas);
        autoCompleteTxtMarca.setAdapter(adapterMarca);

        direccion = rootView.findViewById(R.id.direccion_txt);



        requestQueue = Volley.newRequestQueue(getContext());

        ImageButton  imgbtn = rootView.findViewById(R.id.location_btn);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                realizarPeticion(-16.3926338,-71.5431883);
            }
        });

        recyclerView = rootView.findViewById(R.id.recycler_price_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PriceAdapter(getContext(), pricesArrayList,pricesArrayList);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private void realizarPeticion(double latitud, double longitud) {
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
}