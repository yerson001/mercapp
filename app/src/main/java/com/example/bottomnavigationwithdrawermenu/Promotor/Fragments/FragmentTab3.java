package com.example.bottomnavigationwithdrawermenu.Promotor.Fragments;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
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
import com.example.bottomnavigationwithdrawermenu.Promotor.Entities.Frescos;
import com.example.bottomnavigationwithdrawermenu.Promotor.UploadActivity;
import com.example.bottomnavigationwithdrawermenu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class FragmentTab3 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String url="https://emaransac.com/android/dev/upload.php";

    String[] TipoEnvase = {"Display","Taper","Paquete","Bolsa","Frasco","Otros"};
    String[] TipoProducto = {"Polvo","Fresco","Entero","A granel","Otros"};
    String[] TipoSobre = {"Gigante","Pequeño","Sachet","Sobre Económico"};
    String[] ClasiProduct = { "Tradicional","No tradicinal","Otros"};

    AutoCompleteTextView tipoenvase;
    ArrayAdapter<String> adaptertipoenvase;

    AutoCompleteTextView tipoproducto;
    ArrayAdapter<String> adaptertipoproducto;

    AutoCompleteTextView tiposobre;
    ArrayAdapter<String> adaptertiposobre;

    AutoCompleteTextView clasificacion;
    ArrayAdapter<String> adapterclasificacion;
    ArrayList<String> marcas_competidores = new ArrayList<>();
    private String mParam1;
    private String mParam2;

    EditText nombre_pro;
    EditText gramaje;
    EditText observaciones;
    TextView code;
    Button agregar;



    // variables to upload image
    ImageButton browse;
    Button upload;
    ImageView img;
    Bitmap bitmap;
    String encodeImageString;

    String tabmarca,tabtipoenvase,tabtipoproducto,tabtiposobre,tabclasificacion,tabgramage,tabobservaciones;

    AutoCompleteTextView autoCompleteTxtMarca;
    public FragmentTab3() {
        // Required empty public constructor
    }

    public static FragmentTab3 newInstance(String param1, String param2) {
        FragmentTab3 fragment = new FragmentTab3();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        View rootView = inflater.inflate(R.layout.fragment_tab3, container, false);
        marcas_competidores.clear();
        retrieveData("https://emaransac.com/mercapp/promoter/get_competitor_brands.php");
        ArrayAdapter<String> adapterMarca;
        //***********************
        autoCompleteTxtMarca = rootView.findViewById(R.id.marca_txt_tab3);
        adapterMarca = new ArrayAdapter<>(requireContext(), R.layout.distrib_item, marcas_competidores);
        autoCompleteTxtMarca.setAdapter(adapterMarca);


        //*******************************

        tipoenvase = rootView.findViewById(R.id.tipo_envase_txt);
        adaptertipoenvase = new ArrayAdapter<>(requireContext(), R.layout.distrib_item, TipoEnvase);
        tipoenvase.setAdapter(adaptertipoenvase);

        tipoproducto = rootView.findViewById(R.id.tipo_producto_txt);
        adaptertipoproducto = new ArrayAdapter<>(requireContext(), R.layout.distrib_item, TipoProducto);
        tipoproducto.setAdapter(adaptertipoproducto);

        tiposobre = rootView.findViewById(R.id.tipo_sobre_txt);
        adaptertiposobre = new ArrayAdapter<>(requireContext(), R.layout.distrib_item, TipoSobre);
        tiposobre.setAdapter(adaptertiposobre);

        clasificacion = rootView.findViewById(R.id.clasifiacion_txt);
        adapterclasificacion = new ArrayAdapter<>(requireContext(), R.layout.distrib_item, ClasiProduct);
        clasificacion.setAdapter(adapterclasificacion);

        autoCompleteTxtMarca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tabmarca = (String) parent.getItemAtPosition(position);
                Toast.makeText(getContext(),tabmarca,Toast.LENGTH_SHORT).show();
                retrieveData("https://emaransac.com/mercapp/promoter/get_competitor_brands.php");
            }
        });

        tipoenvase.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 tabtipoenvase = (String) parent.getItemAtPosition(position);
            }
        });

        tiposobre.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 tabtiposobre = (String) parent.getItemAtPosition(position);
            }
        });


        tipoproducto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 tabtipoproducto = (String) parent.getItemAtPosition(position);
            }
        });

        clasificacion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tabclasificacion = (String) parent.getItemAtPosition(position);
            }
        });



         nombre_pro = rootView.findViewById(R.id.nombre_txt_tab3);
         gramaje = rootView.findViewById(R.id.gramage_txt_tab3);
         observaciones = rootView.findViewById(R.id.observaciones_txt_tab3);
         agregar = rootView.findViewById(R.id.agregar_producto_tab3);



        ImageButton product_img = rootView.findViewById(R.id.browser_io);
        code = rootView.findViewById(R.id.codeimg);
        product_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String currentDate = sdf.format(new Date());

                // Generar un número aleatorio entre 0 y 9999
                Random random = new Random();
                int randomNumber = random.nextInt(10000);

                // Concatenar el número aleatorio al valor de la fecha
                String dateWithRandom = "PRODUCT_IMG"+currentDate + randomNumber;
                code.setText(dateWithRandom);
                Intent intent = new Intent(getActivity(), UploadActivity.class);
                intent.putExtra("NAME_KEY", dateWithRandom);
                startActivity(intent);
            }
        });
        ImageButton myButton = rootView.findViewById(R.id.btn_guardar);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPromattendance();
            }
        });

        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nombre = nombre_pro.getText().toString().trim();
                final String grammaje = gramaje.getText().toString().trim();
                final String obs = observaciones.getText().toString().trim();
                final String cod = "https://emaransac.com/mercapp/images/"+code.getText().toString().trim()+".jpg";

                insertProduct(
                        tabmarca,
                        nombre,
                        tabtipoenvase,
                        tabtipoproducto ,
                        tabtiposobre,
                        tabclasificacion,
                        grammaje,
                        cod,
                        obs
                        );
            }
        });


        return rootView;
    }

    private void showPromattendance() {

        final Dialog dialogp = new Dialog(getContext());
        dialogp.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogp.setContentView(R.layout.bottomsheetlayoutmarca);

        ImageView cancelButton = dialogp.findViewById(R.id.cancelButton);
        ImageView cancelButtonp = dialogp.findViewById(R.id.cancelButtonp);


        EditText nombre_marca = dialogp.findViewById(R.id.nombre_marca_txt);
        EditText ruc_marca = dialogp.findViewById(R.id.marca_ruc_txt);
        EditText razon_marca = dialogp.findViewById(R.id.marca_razon_social_txt);
        TextView code = dialogp.findViewById(R.id.code);
        EditText obsercaciones = dialogp.findViewById(R.id.observaciones_marca_txt);

        //******************************************************************
        Button guardar_marca = dialogp.findViewById(R.id.add_marca_txt);





        img=(ImageView)dialogp.findViewById(R.id.img);
        browse=(ImageButton)dialogp.findViewById(R.id.browse);


        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener la fecha actual
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                String currentDate = sdf.format(new Date());

                // Generar un número aleatorio entre 0 y 9999
                Random random = new Random();
                int randomNumber = random.nextInt(10000);

                // Concatenar el número aleatorio al valor de la fecha
                String dateWithRandom = "BRAND_IMG"+currentDate + randomNumber;

                code.setText(dateWithRandom);

                Intent intent = new Intent(getActivity(), UploadActivity.class);
                intent.putExtra("NAME_KEY", dateWithRandom);
                startActivity(intent);
            }
        });





        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogp.dismiss();
            }
        });

        cancelButtonp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogp.dismiss();
            }
        });


        guardar_marca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String nombre = nombre_marca.getText().toString().trim();
                final String ruc_ = ruc_marca.getText().toString().trim();
                final String razon_ = razon_marca.getText().toString().trim();
                final String codigo_ = "https://emaransac.com/mercapp/images/"+code.getText().toString().trim()+".jpg";
                final String obs = obsercaciones.getText().toString().trim();
               insertMarca(nombre,ruc_,razon_,obs,codigo_);
               dialogp.dismiss();
            }
        });



        dialogp.show();
        dialogp.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogp.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogp.getWindow().setGravity(Gravity.BOTTOM);
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


    private void insertMarca(String nombre,String ruc,String nombrecomercial,String obs,String ruta) {
        try { //Request Permission if not permitted
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("cargando...");

        if(nombre.isEmpty()){
            Toast.makeText(getContext(), "Ingrese Nombre ", Toast.LENGTH_SHORT).show();
            return;
        }
        else{
            StringRequest request = new StringRequest(Request.Method.POST, "https://emaransac.com/mercapp/promoter/insert_brand.php",
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
                    params.put("brand_name",nombre);
                    params.put("ruc",ruc);
                    params.put("business_name",nombrecomercial);
                    params.put("observation",obs);
                    params.put("image_path",ruta);
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
    }


    private void insertProduct(String marca,String nombre,String tipo_envase,
                               String tipo_producto, String tipo_sobre,
                               String tab_clasificacion,String tab_gramaje,
                               String imagen ,String obser) {
        try { //Request Permission if not permitted
            if (ContextCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        if(marca.isEmpty()){
            Toast.makeText(getContext(), "Ingrese Marca ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(nombre.isEmpty()){
            Toast.makeText(getContext(), "Ingrese Nombre Producto", Toast.LENGTH_SHORT).show();
            return;
        }
        if(tipo_envase.isEmpty()){
            Toast.makeText(getContext(), "Ingrese Tipo Envase", Toast.LENGTH_SHORT).show();
            return;
        }
        if(tipo_producto.isEmpty()){
            Toast.makeText(getContext(), "Ingrese Tipo Producto", Toast.LENGTH_SHORT).show();
            return;
        }
        else{

            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("cargando...");

            StringRequest request = new StringRequest(Request.Method.POST, "https://emaransac.com/mercapp/promoter/insert_product.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if(response.equalsIgnoreCase("Se guardo correctamente.")){


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
                    params.put("brand",marca);
                    params.put("name_product",nombre);
                    params.put("container_type",tipo_envase);
                    params.put("product_type",tipo_producto);
                    params.put("envelope_type",tipo_sobre);
                    params.put("classification",tab_clasificacion);
                    params.put("grammage",tab_gramaje);
                    params.put("image_path",imagen);
                    params.put("observations",obser);

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
                    autoCompleteTxtMarca.setText("");
                    nombre_pro.setText("");
                    tipoenvase.setText("");
                    tipoproducto.setText("");
                    tiposobre.setText("");
                    clasificacion.setText("");
                    gramaje.setText("");
                    observaciones.setText("");
                    code.setText("");
                }
            });
        }
    }
}