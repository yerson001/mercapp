package com.example.bottomnavigationwithdrawermenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomnavigationwithdrawermenu.Fragment.BlockDiversionFragment;
import com.example.bottomnavigationwithdrawermenu.Fragment.BlockListFragment;
import com.example.bottomnavigationwithdrawermenu.Fragment.FAQFragment;
import com.example.bottomnavigationwithdrawermenu.Fragment.HelpFragment;
import com.example.bottomnavigationwithdrawermenu.Fragment.HomeFragment;
import com.example.bottomnavigationwithdrawermenu.Fragment.NotificationFragment;
import com.example.bottomnavigationwithdrawermenu.Fragment.PremiumFragment;
import com.example.bottomnavigationwithdrawermenu.Fragment.SettingsFragment;
import com.example.bottomnavigationwithdrawermenu.Fragment.SupportFragment;
import com.example.bottomnavigationwithdrawermenu.Mercaderista.ProductListActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavigationView;
    ArrayList<String> itemsList = new ArrayList<>();
    int numdb;
    //Stores stores;
    //StoreAdapter adapter;
    String url = "https://emaransac.com/mercapp/get_stores.php";
    FloatingActionButton fab;




    // bottom navigation
    private enum BottomNavigationFragment {
        HOME(R.id.nav_home, new HomeFragment()),
        BLOCK_LIST(R.id.nav_block_list, new BlockListFragment()),
        HELP(R.id.nav_help, new HelpFragment()),
        PREMIUM(R.id.nav_bottom_premium, new PremiumFragment());

        private final int itemId;
        private final Fragment fragment;

        BottomNavigationFragment(int itemId, Fragment fragment) {
            this.itemId = itemId;
            this.fragment = fragment;
        }


        public int getItemId() {
            return itemId;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public static BottomNavigationFragment fromItemId(int itemId) {
            for (BottomNavigationFragment fragment : values()) {
                if (fragment.itemId == itemId) {
                    return fragment;
                }
            }
            throw new IllegalArgumentException("Invalid navigation item id");
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String username = getIntent().getStringExtra("username");

        //Toast.makeText(MainActivity.this,username,Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Reemplaza "container" con el ID del contenedor donde se muestra el fragmento HomeFragment
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new HomeFragment())
                        .commit();
            }
        }, 1000); // Delay de 2 segundos (ajústalo según tus necesidades)

        fab = findViewById(R.id.fab);

/*

*/

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // DrawerLayout
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // NavigationView
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);


        // Set default fragment to home fragment
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, BottomNavigationFragment.HOME.getFragment())
                .commit();

        // Setup bottom navigation
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            BottomNavigationFragment selectedFragment = BottomNavigationFragment.fromItemId(item.getItemId());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment.getFragment())
                    .commit();
            return true;
        });

        //This part can be provide esAdmin()
        if (esPromotor(username)) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPromattendance();
                }
            });
        } else if(esMercaderista(username)){
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showMercattendance();
                }
            });
        }else{
            fab.setVisibility(View.GONE);
        }
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
                fragmentTransaction.replace(R.id.fragment_container, fragment);
                fragmentTransaction.commit();
                return true;
            }
        }

        return false;

    }


    // clickListener for drawer menu
    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            BottomNavigationFragment navItem = BottomNavigationFragment.fromItemId(item.getItemId());

            if (navItem != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, navItem.getFragment())
                        .commit();
                return true;
            }
            return false;
        }
    };

    private boolean esAdmin(String username) {
        // Lista de nombres de usuario de administrador
        List<String> admins = Arrays.asList("admin", "", "");

        // Verificar si el nombre de usuario está en la lista de administradores
        return admins.contains(username);
    }

    private boolean esPromotor(String username) {
        // Lista de nombres de usuario de administrador
        List<String> admins = Arrays.asList("promotor", "admin2", "admin3");

        // Verificar si el nombre de usuario está en la lista de administradores
        return admins.contains(username);
    }

    private boolean esMercaderista(String username) {
        // Lista de nombres de usuario de administrador
        List<String> admins = Arrays.asList("mercaderista", "", "");

        // Verificar si el nombre de usuario está en la lista de administradores
        return admins.contains(username);
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

    private void showMercattendance() {
        itemsList.clear();
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayout);
        LinearLayout txtcobranza = dialog.findViewById(R.id.txt_cobranza);
        LinearLayout txtinvenpedido = dialog.findViewById(R.id.txt_inve_pedido);
        LinearLayout txtentrega = dialog.findViewById(R.id.txt_entrega_pedido);
        LinearLayout txtdeposito = dialog.findViewById(R.id.txt_deposito);
        TextView txtmotivo = dialog.findViewById(R.id.txtmotivo);
        TextView txtlocal = dialog.findViewById(R.id.txtlocal);
        TextView txtsucursal = dialog.findViewById(R.id.txtsucursal);


        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        LinearLayout suscursales = dialog.findViewById(R.id.layoutVideo);

        ListView listView = dialog.findViewById(R.id.myListView);

        //retrieveStores();
// para que funcoina con el splask screen esta funcion deberia recolectar los datos de la stiendas de la base de datos

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, itemsList){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextSize(16); // Cambiar el tamaño del texto aquí
                return textView;
            }
        };

        listView.setAdapter(adapter);

        txtdeposito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //txtcobranza.setBackgroundResource(R.drawable.rectagle);
                txtcobranza.setSelected(false);
                txtinvenpedido.setSelected(false);
                txtentrega.setSelected(false);
                txtdeposito.setSelected(true);
                itemsList.clear();

                //dialog.dismiss();
                //Toast.makeText(MainActivity.this,"Depósito",Toast.LENGTH_SHORT).show();
                txtmotivo.setText("Depósito");
                txtlocal.setText("##############");
                txtsucursal.setText(" ");
                itemsList.clear();
                itemsList.add("1. Banco de Crédito del Perú");
                itemsList.add("2. Scotiabank");
                adapter.notifyDataSetChanged();
            }
        });



        txtcobranza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtcobranza.setSelected(true);
                txtinvenpedido.setSelected(false);
                txtentrega.setSelected(false);
                txtdeposito.setSelected(false);
                itemsList.clear();
                // Rest of your code...
                txtmotivo.setText("Cobranza");
                txtlocal.setText("##############");
                txtsucursal.setText("");
                itemsList.clear();
                itemsList.add("1. Banco de Crédito del Perú");
                itemsList.add("2. Scotiabank");

                adapter.notifyDataSetChanged();
            }
        });

        txtinvenpedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtcobranza.setSelected(false);
                txtinvenpedido.setSelected(true);
                txtentrega.setSelected(false);
                txtdeposito.setSelected(false);
                //dialog.dismiss();
                //Toast.makeText(MainActivity.this,"Inventario / Pedido",Toast.LENGTH_SHORT).show();
                txtmotivo.setText("Inventario Pedido");
                txtlocal.setText("##############");
                txtsucursal.setText("######");
                itemsList.clear();
                itemsList.add("1. FRANCO SUPERMERCADOS");
                itemsList.add("2. SUPERMERCADOS PERUANOS");
                itemsList.add("3. HIPERMERCADOS TOTTUS");
                itemsList.add("4. CENCOSUD RETAIL");
                itemsList.add("5. R-INTERNACIONALES - EL SUPER");
                adapter.notifyDataSetChanged();
            }
        });

        txtentrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtcobranza.setSelected(false);
                txtinvenpedido.setSelected(false);
                txtentrega.setSelected(true);
                txtdeposito.setSelected(false);

                //dialog.dismiss();
                //Toast.makeText(MainActivity.this,"Entrega",Toast.LENGTH_SHORT).show();
                txtmotivo.setText("Entrega Pedido");
                txtlocal.setText("##############");
                txtsucursal.setText("######");
                itemsList.clear();
                itemsList.add("1. FRANCO SUPERMERCADOS");
                itemsList.add("2. SUPERMERCADOS PERUANOS");
                itemsList.add("3. HIPERMERCADOS TOTTUS");
                itemsList.add("4. CENCOSUD RETAIL");
                itemsList.add("5. R-INTERNACIONALES - EL SUPER");
                adapter.notifyDataSetChanged();
            }
        });




/*
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, itemsList) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                TextView textView = (TextView) super.getView(position, convertView, parent);
                textView.setTextSize(16); // Cambiar el tamaño del texto aquí
                return textView;
            }
        };*/

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                String selectedItem = itemsList.get(position);
                //Toast.makeText(MainActivity.this, selectedItem, Toast.LENGTH_SHORT).show();
                if (itemsList.get(position).equals("1. FRANCO SUPERMERCADOS")) {

                    String texto = itemsList.get(position).substring(itemsList.get(position).indexOf(".")+2);
                    txtlocal.setText(texto);

                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                    CharSequence[] dialogItem = {"» Emmel", "» Lambramani", "» Kosto Tritan", "» Kosto Mayorista"};
                    builder.setTitle(texto);
                    builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            switch (i) {
                                case 0:
                                    //Toast.makeText(MercAppActivity.this,"Emmel", Toast.LENGTH_SHORT).show();
                                    txtsucursal.setText(dialogItem[0].toString());
                                    numdb = 0;
                                    //startActivity(intent);
                                    break;
                                case 1:
                                    txtsucursal.setText(dialogItem[1].toString());
                                    //startActivity(intent1);
                                    numdb = 1;
                                    break;
                                case 2:
                                    txtsucursal.setText(dialogItem[2].toString());
                                    //startActivity(intent2);
                                    numdb = 2;
                                    break;
                                case 3:
                                    txtsucursal.setText(dialogItem[3].toString());
                                    //startActivity(intent3);
                                    numdb = 3;
                                    break;
                            }
                        }
                    });
                    builder.create().show();

                }
                if (itemsList.get(position).equals("2. SUPERMERCADOS PERUANOS")) {
                    String texto = itemsList.get(position).substring(itemsList.get(position).indexOf(".")+2);
                    txtlocal.setText(texto);
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                    CharSequence[] dialogItem = {"» Plaza Vea - Ejército", "» Plaza Vea - La Marina"};
                    builder.setTitle(texto);
                    builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            switch (i) {
                                case 0:
                                    //Toast.makeText(MercAppActivity.this,"Emmel", Toast.LENGTH_SHORT).show();
                                    txtsucursal.setText(dialogItem[0].toString());
                                    numdb = 4;
                                    break;
                                case 1:
                                    txtsucursal.setText(dialogItem[1].toString());
                                    numdb = 4;
                                    break;
                            }
                        }
                    });
                    builder.create().show();
                }
                if (itemsList.get(position).equals("3. HIPERMERCADOS TOTTUS")) {
                    String texto = itemsList.get(position).substring(itemsList.get(position).indexOf(".")+2);
                    txtlocal.setText(texto);
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                    CharSequence[] dialogItem = {"» Tottus - Ejército", "» Tottus - Porrongoche", "» Tottus - Parra"};
                    builder.setTitle(texto);
                    builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            switch (i) {
                                case 0:
                                    txtsucursal.setText(dialogItem[0].toString());
                                    numdb = 5;
                                    break;
                                case 1:
                                    txtsucursal.setText(dialogItem[1].toString());
                                    numdb = 5;
                                    break;
                                case 2:
                                    txtsucursal.setText(dialogItem[2].toString());
                                    numdb = 5;
                                    break;
                            }
                        }
                    });
                    builder.create().show();
                }
                if (itemsList.get(position).equals("4. CENCOSUD RETAIL")) {
                    String texto = itemsList.get(position).substring(itemsList.get(position).indexOf(".")+2);
                    txtlocal.setText(texto);
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                    CharSequence[] dialogItem = {"» Metro - Aviación", "» Metro - Ejército", "» Metro - Lambramani", "» Metro - Hunter"};
                    builder.setTitle(texto);
                    builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            switch (i) {
                                case 0:
                                    txtsucursal.setText(dialogItem[0].toString());
                                    numdb = 6;
                                    break;
                                case 1:
                                    txtsucursal.setText(dialogItem[1].toString());
                                    numdb = 6;
                                    break;
                                case 2:
                                    txtsucursal.setText(dialogItem[2].toString());
                                    numdb = 6;
                                    break;
                                case 3:
                                    txtsucursal.setText(dialogItem[3].toString());
                                    numdb = 6;
                                    break;
                            }
                        }
                    });
                    builder.create().show();
                }
                if (itemsList.get(position).equals("5. R-INTERNACIONALES - EL SUPER")) {
                    String texto = itemsList.get(position).substring(itemsList.get(position).indexOf(".")+2);
                    txtlocal.setText(texto);
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    ProgressDialog progressDialog = new ProgressDialog(view.getContext());
                    CharSequence[] dialogItem = {"» Super - Pierola", "» Super - Portal"};
                    builder.setTitle(texto);
                    builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int i) {
                            switch (i) {
                                case 0:
                                    txtsucursal.setText(dialogItem[0].toString());
                                    numdb = 7;
                                    break;
                                case 1:
                                    txtsucursal.setText(dialogItem[1].toString());
                                    numdb = 7;
                                    break;
                            }
                        }
                    });
                    builder.create().show();
                }
                if (itemsList.get(position).equals("1. Banco de Crédito del Perú")) {
                    String texto = itemsList.get(position).substring(itemsList.get(position).indexOf(".")+2);
                    txtlocal.setText(texto);
                }
                if (itemsList.get(position).equals("2. Scotiabank")) {
                    String texto = itemsList.get(position).substring(itemsList.get(position).indexOf(".")+2);
                    txtlocal.setText(texto);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });




        Button btnLog = dialog.findViewById(R.id.btn);
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"funciona",Toast.LENGTH_SHORT).show();
                final String Txtmot = txtmotivo.getText().toString();
                final String Txtloc = txtlocal.getText().toString();
                final String Txtsuc = txtsucursal.getText().toString();

                if (Txtmot.isEmpty() || Txtmot.equals("######")) {
                    //Toast.makeText(MainActivity.this, "Ingrese Motivo", Toast.LENGTH_SHORT).show();
                    //Toasty.warning(getApplicationContext(), "Mensaje de advertencia", Toast.LENGTH_SHORT).show();
                    // -----   CUSTOM ALERTOAS
                    LayoutInflater inflater = getLayoutInflater();
                    View toastLayout = inflater.inflate(R.layout.toast_customr, findViewById(R.id.toast_layout_root));
                    // Configurar el texto del Toast
                    TextView textView = toastLayout.findViewById(R.id.text_view);
                    textView.setText("           ✘ Ingrese Motivo        ");
                    // Crear y mostrar el Toast personalizado
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 40)
                    ; // Establecer la posición en la parte superior y centrada
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(toastLayout);
                    toast.show();

                    return;
                } else if (Txtloc.equals("##############")) {
                    //Toast.makeText(MainActivity.this, "Ingrese Local", Toast.LENGTH_SHORT).show();
                    LayoutInflater inflater = getLayoutInflater();
                    View toastLayout = inflater.inflate(R.layout.toast_customr, findViewById(R.id.toast_layout_root));
                    // Configurar el texto del Toast
                    TextView textView = toastLayout.findViewById(R.id.text_view);
                    textView.setText("           ✘ Ingrese Local        ");
                    // Crear y mostrar el Toast personalizado
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 40)
                    ; // Establecer la posición en la parte superior y centrada
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(toastLayout);
                    toast.show();

                    return;
                } else if (Txtsuc.equals("######")) {
                    //Toast.makeText(MainActivity.this, "Ingrese Sucursal", Toast.LENGTH_SHORT).show();
                    LayoutInflater inflater = getLayoutInflater();
                    View toastLayout = inflater.inflate(R.layout.toast_customr, findViewById(R.id.toast_layout_root));
                    // Configurar el texto del Toast
                    TextView textView = toastLayout.findViewById(R.id.text_view);
                    textView.setText("           ✘ Ingrese Sucursal        ");
                    // Crear y mostrar el Toast personalizado
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 40)
                    ; // Establecer la posición en la parte superior y centrada
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(toastLayout);
                    toast.show();
                    return;
                } else {
                    if(Txtmot.equals("Cobranza") || Txtmot.equals("Depósito") ){

                        PremiumFragment fragment = new PremiumFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container, fragment);
                        fragmentTransaction.commit();
                        dialog.dismiss();
                    }else{
                        Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                        intent.putExtra("tienda", Txtloc);
                        intent.putExtra("sucursal", Txtsuc);
                        intent.putExtra("id", Integer.toString(numdb));
                        //InsertarRegistro();
                        startActivity(intent);
                    }

                }


            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

    /*
    public void retrieveStores(){

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        TiendasArraylist.clear();
                        itemsList.clear(); // Limpiar el ArrayList itemsList antes de agregar los nuevos elementos

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String exito = jsonObject.getString("exito");
                            JSONArray jsonArray = jsonObject.getJSONArray("datos");
                            if (exito.equals("1")) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String nombre = object.getString("nombre");

                                    stores = new Stores(Integer.toString(i), nombre);
                                    TiendasArraylist.add(stores);
                                    itemsList.add(nombre); // Agregar el nombre de la tienda a itemsList
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
*/


    private void showPromattendance() {

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheetlayoutpro);

        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }
}