package com.example.bottomnavigationwithdrawermenu.cvapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
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
import com.example.bottomnavigationwithdrawermenu.cvapp.Adapters.ProductAdapter;
import com.example.bottomnavigationwithdrawermenu.cvapp.Entity.Product;
import com.example.bottomnavigationwithdrawermenu.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class ProductListActivity extends AppCompatActivity implements ProductAdapter.itemClickListener, ProductAdapter.TextInputListener , SearchView.OnQueryTextListener{

    private RecyclerView recyclerView;

    private ProductAdapter adapter;
    private Product producto;

    private SearchView searchView;
    public static ArrayList<Product> productArrayList = new ArrayList<>();
    String sucursal_name;
    String tienda_name;
    ArrayList<String> sucursales = new ArrayList<>();
    ArrayList<String> ImageList = new ArrayList<>();
    TextView txtTienda,txt_count,txt_total;
    int suma;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);



        try { //Request Permission if not permitted
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        // supermercados franco productos-sucursales 0 - 3
        sucursales.add("https://emaransac.com/android/productos_emmel.php");
        sucursales.add("https://emaransac.com/android/productos_lambramani.php");
        sucursales.add("https://emaransac.com/android/productos_ktristan.php");
        sucursales.add("https://emaransac.com/android/productos_kmayorista.php");
        // supermercados peruanos plazavea 4
        sucursales.add("https://emaransac.com/android/productos_plazavea.php");
        // tottus suppermercados 5
        sucursales.add("https://emaransac.com/android/productos_tottus.php");
        // metros uspermercados 6
        sucursales.add("https://emaransac.com/android/productos_metro.php");
        // metro 7
        sucursales.add("https://emaransac.com/android/productos_super.php");

        txtTienda = findViewById(R.id.title);
        txt_count = findViewById(R.id.counter);
        txt_total = findViewById(R.id.counter_now);

        searchView = findViewById(R.id.search_view);
        searchView.clearFocus();

        // get all products

        Bundle bundle = getIntent().getExtras();
        if(bundle !=null){
            //retrieveProduct(url2);
            String tienda = bundle.getString("tienda");
            String sucursal = bundle.getString("sucursal");
            String sucursal_ = sucursal.replace("»", "");
            //Toast.makeText(ProductListActivity.this,sucursal_,Toast.LENGTH_LONG).show();
            String strSinEspacios = sucursal_.replaceAll("\\s+", "");
            sucursal_name = strSinEspacios;
            tienda_name = tienda;
            //productArrayList.clear();
            if(strSinEspacios.equals("Emmel")){
                retrieveData(sucursales.get(0));
            }
            if(strSinEspacios.equals("Lambramani")){
                retrieveData(sucursales.get(1));
            }
            if(strSinEspacios.equals("KostoTritan")){
                retrieveData(sucursales.get(2));
            }
            if(strSinEspacios.equals("KostoMayorista")){
                retrieveData(sucursales.get(3));
            }
            if(strSinEspacios.equals("PlazaVea-Ejército")){
                retrieveData(sucursales.get(4));
            }
            if(strSinEspacios.equals("PlazaVea-LaMarina")){
                retrieveData(sucursales.get(4));
            }
            if(strSinEspacios.equals("Tottus-Ejército")){
                retrieveData(sucursales.get(5));
            }
            if(strSinEspacios.equals("Tottus-Porrongoche")){
                retrieveData(sucursales.get(5));
            }
            if(strSinEspacios.equals("Tottus-Parra")){
                retrieveData(sucursales.get(5));
            }
            if(strSinEspacios.equals("Metro-Aviación")){
                retrieveData(sucursales.get(6));
            }
            if(strSinEspacios.equals("Metro-Ejército")){
                retrieveData(sucursales.get(6));
            }
            if(strSinEspacios.equals("Metro-Lambramani")){
                retrieveData(sucursales.get(6));
            }
            if(strSinEspacios.equals("Metro-Hunter")){
                retrieveData(sucursales.get(6));
            }
            if(strSinEspacios.equals("Super-Pierola")){
                retrieveData(sucursales.get(7));
            }
            if(strSinEspacios.equals("Super-Portal")){
                retrieveData(sucursales.get(7));
            }
            else {
                Log.d("Error el seleccionar la tienda ",strSinEspacios);
            }
            txtTienda.setText("Productos "+sucursal_);

            recyclerView = findViewById(R.id.recycler_view);
            adapter = new ProductAdapter(this,productArrayList,this,this);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }else{
            Toast.makeText(ProductListActivity.this,"It is empty",Toast.LENGTH_LONG).show();
        }

        Button btnMostrarDialogo = findViewById(R.id.btnMostrarDialogo);
        btnMostrarDialogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ProductListActivity.this);
                builder.setTitle("Activos Empresa");

// Crear un contenedor para el EditText y el CheckBox
                LinearLayout container = new LinearLayout(ProductListActivity.this);
                container.setOrientation(LinearLayout.VERTICAL);

// Crear la casilla de verificación (CheckBox)
                final CheckBox rejilla = new CheckBox(ProductListActivity.this);
                rejilla.setText("Rejilla Frasco");
                container.addView(rejilla);

// Crear el campo de entrada (EditText)
                final EditText rejillatext = new EditText(ProductListActivity.this);
                rejillatext.setHint("Num Rejilla");
                rejillatext.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                rejillatext.setInputType(InputType.TYPE_CLASS_NUMBER);
                container.addView(rejillatext);

// Crear la casilla de verificación (CheckBox)
                final CheckBox checkBox = new CheckBox(ProductListActivity.this);
                checkBox.setText("Exh. Acrilico Sobres");
                container.addView(checkBox);

// Crear el campo de entrada (EditText)
                final EditText editText = new EditText(ProductListActivity.this);
                editText.setHint("Num Exh Acrilico Sobres");
                editText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
                container.addView(editText);

// Crear la casilla de verificación (CheckBox)
                final CheckBox ExhFrasco = new CheckBox(ProductListActivity.this);
                ExhFrasco.setText("Exh. Acrilico Frasco");
                container.addView(ExhFrasco);

// Crear el campo de entrada (EditText)
                final EditText ExhFrascoTxt = new EditText(ProductListActivity.this);
                ExhFrascoTxt.setHint("Num Exh acrilico Frascos");
                ExhFrascoTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                ExhFrascoTxt.setInputType(InputType.TYPE_CLASS_NUMBER);
                container.addView(ExhFrascoTxt);

// Crear la casilla de verificación (CheckBox)
                final CheckBox ExhSales = new CheckBox(ProductListActivity.this);
                ExhSales.setText("Exh. 4 Nvl Sales");
                container.addView(ExhSales);

// Crear el campo de entrada (EditText)
                final EditText ExhSalesTxt = new EditText(ProductListActivity.this);
                ExhSalesTxt.setHint("Num Exh. 4 Nvl Sales");
                ExhSalesTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                ExhSalesTxt.setInputType(InputType.TYPE_CLASS_NUMBER);
                container.addView(ExhSalesTxt);

// Crear la casilla de verificación (CheckBox)
                final CheckBox Modulo = new CheckBox(ProductListActivity.this);
                Modulo.setText("Módulo MDF");
                container.addView(Modulo);

// Crear el campo de entrada (EditText)
                final EditText ModuloTxt = new EditText(ProductListActivity.this);
                ModuloTxt.setHint("Num Modulo MDF");
                ModuloTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
                ModuloTxt.setInputType(InputType.TYPE_CLASS_NUMBER);
                container.addView(ModuloTxt);

// Configurar los botones del diálogo
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String rejillatxt = rejillatext.getText().toString();
                        boolean rejillaChecked = rejilla.isChecked();
                        String editTextValue = editText.getText().toString();
                        boolean checkBoxChecked = checkBox.isChecked();
                        String ExhFrascoTxtValue = ExhFrascoTxt.getText().toString();
                        boolean ExhFrascoChecked = ExhFrasco.isChecked();
                        String ExhSalesTxtValue = ExhSalesTxt.getText().toString();
                        boolean ExhSalesChecked = ExhSales.isChecked();
                        String ModuloTxtValue = ModuloTxt.getText().toString();
                        boolean ModuloChecked = Modulo.isChecked();
                        // Realizar acciones con los datos ingresados y seleccionados

                        String rejillaValue = rejilla.isChecked() ? rejillatxt : "0";
                        String checkBoxValue = checkBox.isChecked() ? editTextValue : "0";
                        String ExhFrascoValue = ExhFrasco.isChecked() ? ExhFrascoTxtValue : "0";
                        String ExhSalesValue = ExhSales.isChecked() ? ExhSalesTxtValue : "0";
                        String ModuloValue = Modulo.isChecked() ? ModuloTxtValue : "0";
                        // ...
                        insertarpop(rejillaChecked,rejillaValue,checkBoxChecked,checkBoxValue,ExhFrascoChecked,ExhFrascoValue,
                                ExhSalesChecked,ExhSalesValue,ModuloChecked,ModuloValue);
                    }
                });

                builder.setNegativeButton("Cancelar", null);

                // Establecer el contenedor como la vista del diálogo
                builder.setView(container);

                // Mostrar el diálogo
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }
    @Override
    public void onItemClick(int position) {
        //Toast.makeText(MainActivity.this,"Item click"+position,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTextInputClicked(String cod_ref,String id,String nombre, String inventario, String pedido, String img) {
        try { //Request Permission if not permitted
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("tienda: ", sucursal_name);
        String a_lat = "0";
        String a_lon = "0";
        String count = suma+1-recyclerView.getAdapter().getItemCount()+"";
        String ipAddress = getDeviceIpAddress();

        //Toast.makeText(this, "Lat: "+a_lat+" Log: "+a_lon, Toast.LENGTH_SHORT).show();
        //Toast.makeText(ProductListActivity.this," I: "+inventario+" P: "+pedido + "count: "+recyclerView.getAdapter().getItemCount()+"Lat: "+a_lat+" Log: "+a_lon,Toast.LENGTH_SHORT).show();
        Log.d("INSERTAR: tienda","cod_ref: "+cod_ref+" tienda:"+tienda_name+" " + "Suc: "+sucursal_name+" "+"Prod: "+nombre+" Inv: "+inventario+" Ped: "+pedido +" Log: "+a_lon+" Lat: "+a_lat+"ip "+ipAddress+" img->"+id);

        insertar(id,"ean",tienda_name,sucursal_name,nombre,inventario,pedido,a_lon,a_lat,ipAddress,id);
        txt_count.setText(count);

    }

    public void mostrarDetalle() {
        String a_lat = "0";
        String a_lon = "0";

        //Toast.makeText(this, "Lat: "+a_lat+" Log: "+a_lon, Toast.LENGTH_SHORT).show();
    }
    public void scanner() {
        Toast.makeText(this, "Este es el scanner", Toast.LENGTH_SHORT).show();
    }



    private String getDeviceIpAddress() {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress.getAddress().length == 4) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void insertar(String cod_ref,String cod_ean, String tienda, String sucursal, String producto, String inventario, String pedido, String lon, String lat, String ip, String img_id) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();


        StringRequest request = new StringRequest(Request.Method.POST, "https://emaransac.com/mercapp/merchant/insert_product_by_store.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("Se guardo correctamente.")) {
                            Toast.makeText(ProductListActivity.this, "Se guardo correctamente.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProductListActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("cod_ref", cod_ref);
                params.put("cod_ean", cod_ean);
                params.put("tienda", tienda);
                params.put("sucursal", sucursal);
                params.put("producto", producto);
                params.put("inventario", inventario);
                params.put("pedido", pedido);
                params.put("long", lon);
                params.put("lat", lat);
                params.put("ip", ip);
                params.put("img_id", img_id);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ProductListActivity.this);
        requestQueue.add(request);
    }






    private void insertarpop(boolean rej,String numrej, boolean exhsobre, String numexhsobre, boolean exhfrasco, String numexhfrasco,
                             boolean exhsales, String numexhsales, boolean mdf, String nummpd) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "https://emaransac.com/mercapp/merchant/pop_inventory_report.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("Se guardo correctamente.")) {
                            Toast.makeText(ProductListActivity.this, "Se guardo correctamente.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProductListActivity.this, response, Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("tienda", tienda_name);
                params.put("sucursal",sucursal_name);
                params.put("rejillafrasco", String.valueOf(rej));
                params.put("numrejfrasco", numrej);
                params.put("exhividorsobres", String.valueOf(exhsobre));
                params.put("numexhsobres", numexhsobre);
                params.put("exhividorfrascos", String.valueOf(exhfrasco));
                params.put("numexhfrascos", numexhfrasco);
                params.put("exhividorsales", String.valueOf(exhsales));
                params.put("numexhsales", numexhsales);
                params.put("exhividormdf", String.valueOf(mdf));
                params.put("numexhmdf", nummpd);

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(ProductListActivity.this);
        requestQueue.add(request);
    }


    public void retrieveData(String url){
        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        productArrayList.clear();
                        ImageList.clear();
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String exito = jsonObject.getString("exito");
                            JSONArray jsonArray = jsonObject.getJSONArray("datos");
                            if(exito.equals("1")){
                                //Log.d("SIZE: ", String.valueOf(jsonArray.length()));
                                txt_total.setText(String.valueOf(jsonArray.length()));
                                suma = jsonArray.length();
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id_producto");
                                    String cod_ref = object.getString("cod_ref");
                                    String cod_ean = object.getString("cod_ean");
                                    String nombre = object.getString("nombre");
                                    String imagen = object.getString("IMAGENES");
                                    Log.d("Retrival ","cod_ref"+cod_ref+" id: "+id+ "EAN "+cod_ean+"Nombre: "+nombre +"img: "+imagen);
                                    ImageList.add(imagen);
                                    if(cod_ean.isEmpty())
                                        cod_ean = "0000000000000";
                                    producto= new Product(cod_ref,id,cod_ean,nombre,"","",imagen);
                                    productArrayList.add(producto);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductListActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        //adapter.filtrado(s);
        //Toast.makeText(this, "En la siguiente version: "+s, Toast.LENGTH_SHORT).show();
        return false;
    }
    @Override
    public void onBackPressed() {
        // Aquí puedes redirigir al MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", "mercaderista");// In this part our send the username to mainactivity
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish(); // Opcional: finaliza la actividad actual si deseas que el fragmento no sea accesible desde el botón "Back"
    }
}