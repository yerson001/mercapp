package com.example.bottomnavigationwithdrawermenu.cvapp;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bottomnavigationwithdrawermenu.R;
import com.example.bottomnavigationwithdrawermenu.cvapp.Adapters.PriceAdapter;
import com.example.bottomnavigationwithdrawermenu.cvapp.Entity.Summary;
import com.example.bottomnavigationwithdrawermenu.cvapp.Entity.prices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    prices pri;
    public static ArrayList<prices> pricesArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PriceAdapter adapter;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        requestQueue = Volley.newRequestQueue(DetailActivity.this);

        //retrieveData_pro("https://emaransac.com/mercapp/promoter/get_competitor_product.php", "BATAN");
        retrieveData("https://emaransac.com/mercapp/merchant/listar.php");


        recyclerView = findViewById(R.id.recycler_price_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(DetailActivity.this));
        adapter = new PriceAdapter(DetailActivity.this, pricesArrayList);
        recyclerView.setAdapter(adapter);


    }



    public void retrieveData(String url) {
        StringRequest request = new StringRequest(Request.Method.POST, url,
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
                                    String img = "0";
                                    String id = object.getString("id");
                                    String tienda = object.getString("email");
                                    String sucursal = object.getString("plataforma");
                                    String producto = object.getString("nombre");
                                    String inventario = object.getString("ranking");
                                    String pedido = "0";
                                    String fecha_string = object.getString("fecha");

                                    //Log.d("Retrival ", img + " " + id + " " + tienda + " " + producto + " " + inventario + " " + pedido + " " + fecha_string);
                                    pri = new prices(id,producto,inventario);
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
                Toast.makeText(DetailActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }




}

