package com.example.bottomnavigationwithdrawermenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bottomnavigationwithdrawermenu.Fragment.BlockDiversionFragment;
import com.example.bottomnavigationwithdrawermenu.Fragment.FAQFragment;
import com.example.bottomnavigationwithdrawermenu.Fragment.NotificationFragment;
import com.example.bottomnavigationwithdrawermenu.Fragment.PremiumFragment;
import com.example.bottomnavigationwithdrawermenu.Fragment.SettingsFragment;
import com.example.bottomnavigationwithdrawermenu.Fragment.SupportFragment;
import com.example.bottomnavigationwithdrawermenu.cvapp.Adapters.DetailAdapter;
import com.example.bottomnavigationwithdrawermenu.cvapp.DetailActivity;
import com.example.bottomnavigationwithdrawermenu.cvapp.Entity.Register;
import com.example.bottomnavigationwithdrawermenu.cvapp.SummaryActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    public static ArrayList<Register> RegisterList = new ArrayList<>();
    private RecyclerView recyclerView;
    Register reg;
    private DetailAdapter adapter_;
    String urlmercad = "https://emaransac.com/mercapp/merchant/trabajo.php";
    String user="";

    FloatingActionButton fab;

    private final static String CHANNEL_ID = "1";
    private final static String CHANNEL_NAME = "Notification Procedures";
    private final static int NOTIFICATION_ID = 1;
    NotificationCompat.Builder builder;
    NotificationManagerCompat compat;
    DrawerLayout drawer;
    String username="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //username = getIntent().getStringExtra("username");


        username = "promotor";

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
                progressDialog.setMessage("Cargando...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Intent intent = new Intent(MainActivity.this, SummaryActivity.class);
                startActivity(intent);
            }
        });

        retrieveData(urlmercad,user);

        DetailAdapter adapter = new DetailAdapter(MainActivity.this, RegisterList);
        adapter.setOnItemClickListener(new DetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Maneja el clic en el elemento aquí, por ejemplo:
                Register clickedItem = RegisterList.get(position);
                Toast.makeText(MainActivity.this, "Clic en el elemento: " + clickedItem.getId(), Toast.LENGTH_SHORT).show();

                // Crea una nueva Intent para abrir la actividad DetailActivity (ajusta el nombre de la actividad según tu proyecto)
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                // Puedes pasar datos adicionales a la nueva actividad si es necesario, por ejemplo, el ID del elemento
                intent.putExtra("itemId", clickedItem.getId());

                // Inicia la nueva actividad
                startActivity(intent);
            }
        });
        adapter_ = adapter;

        recyclerView = findViewById(R.id.myregister);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(adapter_);
        retrieveData(urlmercad,user);




        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // DrawerLayout
        drawer = findViewById(R.id.drawer_layout);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // NavigationView
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void retrieveData(String url,String user){

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        RegisterList.clear();
                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            String exito = jsonObject.getString("exito");
                            JSONArray jsonArray = jsonObject.getJSONArray("datos");
                            if(exito.equals("1")){
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    String inicio = object.getString("fecha_de_emisión");
                                    String local = object.getString("area_de_empresa");
                                    String motivo = object.getString("cabezera");
                                    String fin = object.getString("fecha_de_finalización");
                                    String tiempo = object.getString("num_participantes");
                                    String estado = object.getString("estado");
                                    reg = new Register(id,inicio,local,motivo,fin,tiempo,estado,user);
                                    RegisterList.add(reg);
                                    adapter_.notifyDataSetChanged();
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
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }


    // clickListener for drawer menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);

        MenuItemEnum menuItemEnum = null;

        if (item.getItemId() == R.id.nav_dis) {
            menuItemEnum = MenuItemEnum.BLOCK_DIVERSION;
        }else if (item.getItemId() == R.id.nav_noti) {
            menuItemEnum = MenuItemEnum.NOTIFICATION;
        }else if (item.getItemId() == R.id.nav_support) {
            menuItemEnum = MenuItemEnum.SUPPORT;
        }else if (item.getItemId() == R.id.nav_Premium) {
            menuItemEnum = MenuItemEnum.PREMIUM;
        }else if (item.getItemId() == R.id.nav_faq) {
            menuItemEnum = MenuItemEnum.FAQ;
        }else if (item.getItemId() == R.id.nav_setting) {
            menuItemEnum = MenuItemEnum.SETTINGS;
        }

        if (menuItemEnum != null) {
            Fragment fragment = null;

            switch (menuItemEnum) {
                case BLOCK_DIVERSION:
                    fragment = new BlockDiversionFragment();
                    break;
                case NOTIFICATION:
                    fragment = new NotificationFragment();
                    break;
                case SUPPORT:
                    fragment = new SupportFragment();
                    break;
                case PREMIUM:
                    fragment = new PremiumFragment();
                    break;
                case FAQ:
                    fragment = new FAQFragment();
                    break;
                case SETTINGS:
                    fragment = new SettingsFragment();
                    break;
            }
            if (fragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                return true;
            }
        }

        return false;

    }


    // enum for drawer menu
    private enum MenuItemEnum {
        BLOCK_DIVERSION,
        NOTIFICATION,
        SUPPORT,
        PREMIUM,
        FAQ,
        SETTINGS,

    }
}

