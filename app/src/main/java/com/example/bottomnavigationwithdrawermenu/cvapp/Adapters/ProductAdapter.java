package com.example.bottomnavigationwithdrawermenu.cvapp.Adapters;
import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.example.bottomnavigationwithdrawermenu.cvapp.Entity.Product;
import com.example.bottomnavigationwithdrawermenu.R;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<Product> productoArrayList;
    private final ArrayList<Product> OriginList;
    private itemClickListener itemClickListener;
    private TextInputListener textInputListener; // Agregado el campo TextInputListener

    public ProductAdapter(Context context, ArrayList<Product> productoArrayList, itemClickListener itemClickListener, TextInputListener textInputListener) {
        this.context = context;
        this.productoArrayList = productoArrayList;
        this.itemClickListener = itemClickListener;
        this.textInputListener = textInputListener; // Asignado el TextInputListener
        OriginList = new ArrayList<>(productoArrayList);
        OriginList.addAll(productoArrayList);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list, parent, false);
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_product_list, parent, false);
        return new ViewHolder(view, itemClickListener, textInputListener); // Pasado el TextInputListener al constructor del ViewHolder
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product producto = productoArrayList.get(position);

        holder.txtid.setText(producto.getId());
        //holder.txtCodRef.setText(producto.getCod_ref());
        holder.txtCodBarras.setText(producto.getCod_barras());
        holder.txtNombre.setText(producto.getNombre());
        holder.txtInventario.setText(producto.getInventario());
        holder.txtPedido.setText(producto.getPedido());
        Glide.with(context)
                .load(producto.getImg())
                .into(holder.imageView);

        holder.txtPedido.setText("0");

        holder.txtInventario.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    holder.txtInventario.setText("");
                }
            }
        });

        holder.txtPedido.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    holder.txtPedido.setText("");
                }
            }
        });
    }

    public void filtrado(String txt){

        int longitud = txt.length();
        if(longitud == 0){
            productoArrayList.clear();
            productoArrayList.addAll(OriginList);
        }else{
            if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                List<Product> collect = productoArrayList.stream().filter(i -> i.getNombre().toLowerCase().
                        contains(txt.toLowerCase())).collect(Collectors.toList());
                productoArrayList.clear();
                productoArrayList.addAll(collect);
            }else{
                productoArrayList.clear();
                for(Product i : OriginList){
                    if(i.getNombre().toLowerCase().contains(txt.toLowerCase())){
                        productoArrayList.add(i);
                    }
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return productoArrayList.size();
    }

    public void removeItem(int position) {
        productoArrayList.remove(position);
        notifyItemRemoved(position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtid;
        TextView txtCodRef;
        TextView txtCodBarras;
        TextView txtNombre;
        TextView txtInventario;
        TextView txtPedido;
        ImageView imageView;
        Button button;
        itemClickListener itemClickListener;
        TextInputListener textInputListener;

        public ViewHolder(@NonNull View itemView, itemClickListener itemClickListener, TextInputListener textInputListener) {
            super(itemView);

            txtid = itemView.findViewById(R.id.id);
            txtCodRef = itemView.findViewById(R.id.cod_ref);
            txtCodBarras = itemView.findViewById(R.id.cod_barras);
            txtNombre = itemView.findViewById(R.id.nombre);
            txtInventario = itemView.findViewById(R.id.inventario);
            txtPedido = itemView.findViewById(R.id.pedido);


            imageView = itemView.findViewById(R.id.imageView);
            button = itemView.findViewById(R.id.button);

            this.itemClickListener = itemClickListener;
            this.textInputListener = textInputListener;

            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            String nombre = txtNombre.getText().toString();
            String inventario = txtInventario.getText().toString();
            String pedido = txtPedido.getText().toString();
            String img = imageView.toString();
            String id = txtid.getText().toString();
            String cod_ref = txtCodRef.getText().toString(); // Obtener el valor del TextView txtCodRef


            boolean isInternetAvailable = isInternetAvailable(); // Verificar disponibilidad de internet
            boolean isGpsEnabled = isGpsEnabled(); // Verificar si el GPS está activado

            if (isInternetAvailable && isGpsEnabled) {
                if (!nombre.isEmpty() && !inventario.isEmpty() && !pedido.isEmpty()) {
                    itemClickListener.onItemClick(getAdapterPosition());
                    textInputListener.onTextInputClicked(cod_ref,id, nombre, inventario, pedido, img);
                    ProductAdapter.this.removeItem(getAdapterPosition());
                } else {
                    if (nombre.isEmpty()) {
                        Toast.makeText(context, "Ingrese producto", Toast.LENGTH_SHORT).show();
                    } else if (inventario.isEmpty()) {
                        Toast.makeText(context, "Ingrese inventario", Toast.LENGTH_SHORT).show();
                    } else if (pedido.isEmpty()) {
                        Toast.makeText(context, "Ingrese pedido", Toast.LENGTH_SHORT).show();
                    }
                }
            } else {
                if (!isInternetAvailable) {
                    Toast.makeText(context, "No hay conexión a internet", Toast.LENGTH_SHORT).show();
                }
                if (!isGpsEnabled) {
                    Toast.makeText(context, "El GPS no está activado", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public interface itemClickListener {
        void onItemClick(int position);
    }

    public interface TextInputListener {
        void onTextInputClicked(String cod_ref,String id,String nombre, String inventario, String pedido, String img);
    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
        return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR));
    }

    private boolean isGpsEnabled() {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

}