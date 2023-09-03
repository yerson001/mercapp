package com.example.bottomnavigationwithdrawermenu.cvapp.Adapters;


import android.app.ProgressDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bottomnavigationwithdrawermenu.R;
import com.example.bottomnavigationwithdrawermenu.cvapp.Entity.prices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.ViewHolder> {

    private Context context;
    private List<prices> pfList;

    public PriceAdapter(Context context, List<prices> pfList) {
        this.context = context;
        this.pfList = pfList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.price_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        prices pri = pfList.get(position);
        holder.tvID.setText(pri.getId());
        holder.tvName.setText(pri.getProducto());
        holder.textView.setText(pri.getPrecioPri());

        holder.checkBoxValidado.setOnCheckedChangeListener(null); // Importante para evitar cambios indeseados

        holder.checkBoxValidado.setChecked(pri.isChecked());

        // Configurar el listener para el CheckBox
        holder.checkBoxValidado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && (pri.getPrecioPri() == null || pri.getPrecioPri().isEmpty())) {
                    Toast.makeText(context, "Debes llenar el campo de precio primero", Toast.LENGTH_SHORT).show();
                    holder.checkBoxValidado.setChecked(false);
                    return;
                }

                pri.setChecked(isChecked);

                if (isChecked && pri.getPrecioPri() != null && !pri.getPrecioPri().isEmpty()) {
                    String precioText = "ID: " + pri.getId();
                    //Toast.makeText(context, precioText, Toast.LENGTH_SHORT).show();
                    updateData(pri.getId());
                }
            }
        });

        // Agregar un TextWatcher al EditText (precio)
        holder.precio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No es necesario implementar esto
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // No es necesario implementar esto
            }

            @Override
            public void afterTextChanged(Editable s) {
                String precioValue = s.toString();
                pri.setPrecioPri(precioValue); // Actualizar el valor del precio en el objeto prices
                // Mostrar el valor actualizado del precio en un Toast
                if (pri.isChecked()) {
                    String precioText = "Precio actualizado: " + precioValue;
                    //Toast.makeText(context, precioText, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pfList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvID;
        TextView tvName;
        EditText precio;
        CheckBox checkBoxValidado;

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.txt_store_id_price);
            tvName = itemView.findViewById(R.id.txt_store_name_price);
            precio = itemView.findViewById(R.id.value_ventas_price);
            textView = itemView.findViewById(R.id.ranking);
            checkBoxValidado = itemView.findViewById(R.id.validado_price_this);
        }
    }

    public List<prices> getCheckedItemsWithPrices() {
        List<prices> checkedItemsWithPrices = new ArrayList<>();

        for (prices pri : pfList) {
            if (pri.isChecked() && pri.getPrecioPri() != null && !pri.getPrecioPri().isEmpty()) {
                checkedItemsWithPrices.add(pri);
            }
        }
        return checkedItemsWithPrices;
    }

    private void updateData(String id) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "https://emaransac.com/mercapp/merchant/email.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.equalsIgnoreCase("Se guardó correctamente.")) {
                            // La operación se realizó correctamente
                            progressDialog.dismiss();
                            // Puedes realizar alguna acción adicional aquí si es necesario
                        } else {
                            Toast.makeText(context, response, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}