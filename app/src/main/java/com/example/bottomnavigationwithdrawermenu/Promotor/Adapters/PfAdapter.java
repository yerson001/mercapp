package com.example.bottomnavigationwithdrawermenu.Promotor.Adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationwithdrawermenu.Promotor.Entities.Frescos;
import com.example.bottomnavigationwithdrawermenu.R;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;
import java.util.ArrayList;
import java.util.List;

public class PfAdapter extends RecyclerView.Adapter<PfAdapter.ViewHolder> {
    private Context context;
    private List<Frescos> pfList;
    private List<Frescos> pfList_r;

    private double accumulatedValue = 0.0;

    public PfAdapter(Context context, List<Frescos> pfList,List<Frescos> pfList_r) {
        this.context = context;
        this.pfList = pfList;
        this.pfList_r = pfList_r;
    }
    public PfAdapter(Context context) {
        this.context = context;
    }

    public List<Frescos> getPfList() {
        return pfList;
    }
    public List<Frescos> getPfList_r() {
        return pfList_r;
    }

    // Métodos del adaptador

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.frescos, parent, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Frescos frescos = pfList.get(position);
        holder.tvID.setText(frescos.getId());
        holder.tvName.setText(frescos.getName());

        final PfAdapter adapter = this;

        // Obtener el valor actual del EditText
        String editTextValue = holder.valueCount.getText().toString();
        if (!TextUtils.isEmpty(editTextValue)) {
            holder.counterValue = Integer.parseInt(editTextValue);
        } else {
            holder.counterValue = 0;
        }
        holder.valueCount.setText(String.valueOf(holder.counterValue));


        // Obtener el valor actual del EditText
        String editTextSale = holder.valueSale.getText().toString();
        if (!TextUtils.isEmpty(editTextSale)) {
            holder.counterSale = Integer.parseInt(editTextValue);
        } else {
            holder.counterSale = 0;
        }
        holder.valueSale.setText(String.valueOf(holder.counterSale));




        // Configurar un OnClickListener para el EditText
        holder.valueCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.valueCount.setText(""); // Eliminar el valor actual del EditText
            }
        });


        holder.valueSale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.valueSale.setText(""); // Eliminar el valor actual del EditText
            }
        });

        // Configurar un Listener para el botón "-"
        holder.btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.counterValue > 0) {
                    holder.counterValue--;
                    holder.valueCount.setText(String.valueOf(holder.counterValue));
                }
                String editTextValue = holder.valueCount.getText().toString();
                // Utilizar editTextValue según tus necesidades
            }
        });

        // Listener para el botón "+"
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.counterValue++;
                holder.valueCount.setText(String.valueOf(holder.counterValue));
                String editTextValue = holder.valueCount.getText().toString();
                // Utilizar editTextValue según tus necesidades
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Imprimir el texto deseado cada vez que se haga clic en el elemento
                String toastText = "Se hizo clic en el elemento " + frescos.getName();
                Toast.makeText(context, toastText, Toast.LENGTH_SHORT).show();
                // Puedes cambiar el texto que deseas imprimir según tus necesidades.
            }
        });


        // Configurar un Listener para el CheckBox
        holder.checkBoxValidado.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Frescos frescos = adapter.getPfList_r().get(adapterPosition);
                    frescos.setChecked(isChecked);
                    String valueCount = holder.valueCount.getText().toString();
                    String valueSale = holder.valueSale.getText().toString();

                    if (!TextUtils.isEmpty(valueCount) && !TextUtils.isEmpty(valueSale)) {
                        int count = Integer.parseInt(valueCount);
                        double sale = Double.parseDouble(valueSale);
                        double result = count * sale;
                        holder.venta.setText(String.valueOf(result));

                        if (isChecked) {
                            accumulatedValue += result;
                        } else {
                            accumulatedValue -= result;
                        }

                        if (callback != null) {
                            callback.onValuesUpdated(accumulatedValue);
                        }
                    } else {
                        holder.venta.setText("0");
                    }
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
        int counterValue = 0;
        double counterSale = 0.0;
        ImageButton btnMenos;
        EditText valueCount;
        ImageButton btnAdd;
        EditText valueSale;
        CheckBox checkBoxValidado;
        TextView venta;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.txt_store_id);
            tvName = itemView.findViewById(R.id.txt_store_name);
            btnMenos = itemView.findViewById(R.id.btn_menos);
            valueCount = itemView.findViewById(R.id.value_count);
            btnAdd = itemView.findViewById(R.id.btn_add);
            valueSale = itemView.findViewById(R.id.value_ventas);
            venta = itemView.findViewById(R.id.venta);
            checkBoxValidado = itemView.findViewById(R.id.validado);
        }
    }

    public List<Frescos> getCheckedItems() {
        List<Frescos> checkedItems = new ArrayList<>();

        for (Frescos frescos : pfList) {
            if (frescos.isChecked()) {
                checkedItems.add(frescos);
            }
        }

        return checkedItems;
    }

    public interface PfAdapterCallback {
        void onValuesUpdated(double accumulatedValue);
    }

    private PfAdapterCallback callback;

    public void setCallback(PfAdapterCallback callback) {
        this.callback = callback;
    }

}
