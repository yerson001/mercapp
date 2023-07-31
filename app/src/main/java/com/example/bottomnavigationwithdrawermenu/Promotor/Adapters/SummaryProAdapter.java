package com.example.bottomnavigationwithdrawermenu.Promotor.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.bottomnavigationwithdrawermenu.Promotor.Entities.detalle;
import com.example.bottomnavigationwithdrawermenu.R;

import java.util.ArrayList;

public class SummaryProAdapter extends RecyclerView.Adapter<SummaryProAdapter.ViewHolder> {
    private final Context context;
    private final ArrayList<detalle> summaryArrayList;
    private int selectedItem = RecyclerView.NO_POSITION;
    private OnItemClickListener onItemClickListener;

    public SummaryProAdapter(Context context, ArrayList<detalle> summaryArrayList) {
        this.context = context;
        this.summaryArrayList = summaryArrayList;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public int getSelectedPosition() {
        return selectedItem;
    }

    public void setSelectedPosition(int position) {
        selectedItem = position;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_reporte, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        detalle summary = summaryArrayList.get(position);

        holder.txtid.setText(summary.getId());
        holder.fecha.setText(summary.getFecha());
        holder.txtDistribuidor.setText(summary.getDistribuidor());
        holder.txtCliente.setText(summary.getCliente());
        holder.txtDireccion.setText(summary.getDirrecion());
        holder.txtTelefono.setText(summary.getTelefono());

/*
        Glide.with(context)
                .load(summary.getImg())
                .into(holder.imageView);
*/
        if (selectedItem == position) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.selected_color));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    int previousSelectedItem = selectedItem;
                    selectedItem = position;
                    notifyItemChanged(previousSelectedItem);
                    notifyItemChanged(position);
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return summaryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtid;
        TextView fecha;
        TextView txtDistribuidor;
        TextView txtCliente;
        TextView txtDireccion;
        TextView txtTelefono;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtid = itemView.findViewById(R.id.id_txt_pro);
            txtDistribuidor = itemView.findViewById(R.id.distribuidor_txt_pro);
            txtCliente = itemView.findViewById(R.id.cliente_txt_pro);
            txtDireccion = itemView.findViewById(R.id.direccion_txt_pro);
            txtTelefono = itemView.findViewById(R.id.telefono_txt_pro);
            fecha = itemView.findViewById(R.id.fecha_txt_pro);
           // imageView = itemView.findViewById(R.id.imageView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}