package com.example.bottomnavigationwithdrawermenu;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
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
import android.widget.RelativeLayout;
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
import com.example.bottomnavigationwithdrawermenu.Notification.Receiver;
import com.example.bottomnavigationwithdrawermenu.Promotor.PromotorActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

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

    private final static String CHANNEL_ID = "1";
    private final static String CHANNEL_NAME = "Notification Procedures";
    private final static int NOTIFICATION_ID = 1;
    NotificationCompat.Builder builder;
    NotificationManagerCompat compat;
    DrawerLayout drawer;
    String username="";




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

        username = getIntent().getStringExtra("username");

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


        TextView TXT_user = findViewById(R.id.text_user);
        TXT_user.setText(username);


        fab = findViewById(R.id.fab);

/*

*/

        // Toolbar
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
        RelativeLayout cancel = dialog.findViewById(R.id.cal);


        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);
        ImageView cancelButton2 = dialog.findViewById(R.id.cancelButton2);

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

        cancelButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                    textView.setText("\n           ⚠️ Ingrese Motivo        \n");
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
                    textView.setText("\n           ⚠️ Ingrese Local        \n");
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
                    textView.setText("\n           ⚠️️ Ingrese Sucursal        \n");
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
                        //createNotificationChannel();
                        //showNotification();
                        sendNotification("23");
                        dialog.dismiss();
                    }else{
                        Intent intent = new Intent(MainActivity.this, ProductListActivity.class);
                        intent.putExtra("tienda", Txtloc);
                        intent.putExtra("sucursal", Txtsuc);
                        intent.putExtra("id", Integer.toString(numdb));
                        //Crete a notification
                        //createNotificationChannel();
                        //showNotification();
                        sendNotification("23");
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
        //crea la notificacion

    }

    private void showPromattendance() {

        final Dialog dialogp = new Dialog(this);
        dialogp.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogp.setContentView(R.layout.bottomsheetlayoutpro);

        RelativeLayout txtincio = dialogp.findViewById(R.id.txt_inicio);
        RelativeLayout txtdurante = dialogp.findViewById(R.id.txt_durante);
        RelativeLayout txtfin = dialogp.findViewById(R.id.txt_fin);

        ImageView cancelButton = dialogp.findViewById(R.id.cancelButton);
        ImageView cancelButtonp = dialogp.findViewById(R.id.cancelButtonp);

        TextView txtmotivop = dialogp.findViewById(R.id.txtmotivop);
        Button btnLogh = dialogp.findViewById(R.id.btnLoginh);

        txtincio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {txtmotivop.setText("Inicio");}
        });

        txtdurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {txtmotivop.setText("Durante el día");}
        });

        txtfin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {txtmotivop.setText("Fin");}
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


        btnLogh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,"funciona",Toast.LENGTH_SHORT).show();
                final String Txtmot = txtmotivop.getText().toString();
                if (Txtmot.isEmpty() || Txtmot.equals("######")) {
                    //Toast.makeText(MainActivity.this, "Ingrese Motivo", Toast.LENGTH_SHORT).show();
                    //Toasty.warning(getApplicationContext(), "Mensaje de advertencia", Toast.LENGTH_SHORT).show();
                    // -----   CUSTOM ALERTOAS
                    LayoutInflater inflater = getLayoutInflater();
                    View toastLayout = inflater.inflate(R.layout.toast_customr, findViewById(R.id.toast_layout_root));
                    // Configurar el texto del Toast
                    TextView textView = toastLayout.findViewById(R.id.text_view);
                    textView.setText("\n           ⚠️ Ingrese Motivo        \n");
                    // Crear y mostrar el Toast personalizado
                    Toast toast = new Toast(getApplicationContext());
                    toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 40)
                    ; // Establecer la posición en la parte superior y centrada
                    toast.setDuration(Toast.LENGTH_SHORT);
                    toast.setView(toastLayout);
                    toast.show();
                    return;
                }else{
                    Intent intent = new Intent(MainActivity.this, PromotorActivity.class);
                    sendNotification("23");
                    //InsertarRegistro();
                    startActivity(intent);
                    return;
                }
            }
        });


        dialogp.show();
        dialogp.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogp.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogp.getWindow().setGravity(Gravity.BOTTOM);
    }


    public void sendNotification(String id) {
        // Action button
        Intent actionIntent = new Intent(MainActivity.this, Receiver.class);
        actionIntent.putExtra("toast", id);
        PendingIntent actionPending = PendingIntent.getBroadcast(MainActivity.this, 1, actionIntent, PendingIntent.FLAG_IMMUTABLE);

        // Crear un PendingIntent vacío para el botón "Cerrar"
        PendingIntent emptyPendingIntent = PendingIntent.getActivity(MainActivity.this, 0, new Intent(), PendingIntent.FLAG_IMMUTABLE);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.registro);
        String text = getResources().getString(R.string.big_text);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }

        Intent deleteIntent = new Intent(MainActivity.this, Receiver.class);
        PendingIntent deletePendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, deleteIntent, PendingIntent.FLAG_IMMUTABLE);

        builder = new NotificationCompat.Builder(MainActivity.this, CHANNEL_ID)
                .setSmallIcon(R.drawable.notifications_icn)
                .setContentTitle("Notificación Registro")
                .setContentText("Haga clic en finalizar para guardar su registro.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .addAction(R.drawable.baseline_notifications_off_24, "Finalizar", actionPending)
                .setDeleteIntent(deletePendingIntent)
                .setLargeIcon(bitmap)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null))
                .setOngoing(true); // Evitar que la notificación sea deslizable

        compat = NotificationManagerCompat.from(MainActivity.this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                    Snackbar.make(drawer, "Please allow the permission to take notification", Snackbar.LENGTH_LONG)
                            .setAction("Allow", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 3);
                                }
                            })
                            .show();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.POST_NOTIFICATIONS}, 3);
                }
            } else {
                compat.notify(NOTIFICATION_ID, builder.build());
            }
        } else {
            compat.notify(NOTIFICATION_ID, builder.build());
        }
    }






    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 3 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

            compat.notify(NOTIFICATION_ID,builder.build());

        }else {

            Snackbar.make(
                    drawer,
                    "Please allow the permission to take notification",
                    Snackbar.LENGTH_LONG).setAction("Allow", new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
                @Override
                public void onClick(View v) {
                    ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.POST_NOTIFICATIONS},3);
                }
            }).show();

        }

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
}