package com.example.bottomnavigationwithdrawermenu.Promotor.Adapters;
import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationwithdrawermenu.Promotor.Entities.Frescos;
import com.example.bottomnavigationwithdrawermenu.Promotor.Entities.prices;
import com.example.bottomnavigationwithdrawermenu.R;
import java.util.ArrayList;
import java.util.List;
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
                    String precioText = "Precio: " + pri.getPrecioPri();
                    //Toast.makeText(context, precioText, Toast.LENGTH_SHORT).show();
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

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.txt_store_id_price);
            tvName = itemView.findViewById(R.id.txt_store_name_price);
            precio = itemView.findViewById(R.id.value_ventas_price);
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
}