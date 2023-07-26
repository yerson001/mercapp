package com.example.bottomnavigationwithdrawermenu.Promotor.Adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bottomnavigationwithdrawermenu.Promotor.Entities.prices;
import com.example.bottomnavigationwithdrawermenu.R;
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

    public List<prices> getPfList() {
        return pfList;
    }
    public List<prices> getPfList_r() {
        return pfList_r;
    }

    // Métodos del adaptador

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.price_report, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        prices prices = pfList.get(position);
        holder.tvID.setText(prices.getId());
        holder.tvName.setText(prices.getProducto());

        // Configurar un OnClickListener para el EditText
        holder.precio_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.precio_c.setText(""); // Eliminar el valor actual del EditText
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
        EditText precio_c;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.txt_id_pre);
            tvName = itemView.findViewById(R.id.txt_producto_pre);
            precio_c = itemView.findViewById(R.id.txt_precio_pre);
        }
    }

    public interface PriceAdapterCallback {
        void onValuesUpdated(double accumulatedValue);
    }

    private PriceAdapterCallback callback;

    public void setCallback(PriceAdapterCallback callback) {
        this.callback = callback;
    }

}
