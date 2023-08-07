package com.example.bottomnavigationwithdrawermenu.Promotor.Adapters;
import android.content.Context;
import android.text.TextUtils;
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
public class PriceAdapter extends RecyclerView.Adapter<PriceAdapter.ViewHolder>  {
    private Context context;
    private List<prices> pfList;
    private List<prices> pfList_r;
    private double accumulatedValue = 0.0;
    public PriceAdapter(Context context, List<prices> pfList,List<prices> pfList_r) {
        this.context = context;
        this.pfList = pfList;
        this.pfList_r = pfList_r;
    }
    public PriceAdapter(Context context) {
        this.context = context;
    }

    public List<prices> getPfList() {
        return pfList;
    }
    public List<prices> getPfList_r() {
        return pfList_r;
    }

    // Métodos del adaptador

    @NonNull
    @Override
    public PriceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.price_report, parent, false);
        return new PriceAdapter.ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull PriceAdapter.ViewHolder holder, int position) {
        prices frescos = pfList.get(position);
        holder.tvID.setText(frescos.getId());
        holder.tvName.setText(frescos.getProducto());

        final PriceAdapter adapter = this;


        // Configurar un Listener para el botón "-"


        // Listener para el botón "+"





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Imprimir el texto deseado cada vez que se haga clic en el elemento
                String toastText = "Se hizo clic en el elemento " + frescos.getProducto();
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
                    prices frescos = adapter.getPfList_r().get(adapterPosition);
                    frescos.setChecked(isChecked);
                    String valueCount = holder.valueCount.getText().toString();
                    String valueSale = holder.valueSale.getText().toString();
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
        EditText valueCount;
        EditText valueSale;
        CheckBox checkBoxValidado;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.txt_store_id_price);
            tvName = itemView.findViewById(R.id.txt_store_name_price);
            valueSale = itemView.findViewById(R.id.value_ventas_price);
            checkBoxValidado = itemView.findViewById(R.id.validado_price_this);
        }
    }

    public List<prices> getCheckedItems() {
        List<prices> checkedItems = new ArrayList<>();

        for (prices frescos : pfList) {
            if (frescos.isChecked()) {
                checkedItems.add(frescos);
            }
        }
        return checkedItems;
    }

    public interface PriceAdapterCallback {
        void onValuesUpdated(double accumulatedValue);
    }

    private PriceAdapter.PriceAdapterCallback callback;

    public void setCallback(PriceAdapter.PriceAdapterCallback callback) {
        this.callback = callback;
    }

}
