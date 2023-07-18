package com.example.bottomnavigationwithdrawermenu.Mercaderista.Adapters;


import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationwithdrawermenu.Mercaderista.Entity.Register;
import com.example.bottomnavigationwithdrawermenu.R;

import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {
    private Context context;
    private List<Register> detalleList;


    private ButtonClickListener buttonClickListener;

    public DetailAdapter(Context context, List<Register> detalleList) {
        this.context = context;
        this.detalleList = detalleList;
    }

    @NonNull
    @Override
    public DetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_register, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailViewHolder holder, final int position) {
        Register detalle = detalleList.get(position);
        holder.tvID.setText(detalle.getId());
        holder.tvFecha.setText(detalle.getFecha());
        holder.tvLocal.setText(detalle.getLocal());
        holder.tvFechafin.setText(detalle.getFechafin());
        holder.tvtiempo.setText(detalle.getTiempo());
        holder.tvMotivo.setText(detalle.getMotivo());

        final int currentPosition = position; // Declarar una variable final adicional

        // Verificar si el botón ya está en estado "Finalizado"
        if (detalle.isFinalizado()) {
            holder.btnFinalizar.setBackgroundColor(Color.parseColor("#FF01579B"));
            holder.btnFinalizar.setTextColor(Color.WHITE);
            holder.btnFinalizar.setText("FINALIZADO");
            holder.btnFinalizar.setEnabled(false); // Deshabilitar el botón
        } else {
            //holder.btnFinalizar.setBackgroundColor(/*Color de fondo original*/);
            //holder.btnFinalizar.setTextColor(/*Color de texto original*/);
            //holder.btnFinalizar.setText(/*Texto original del botón*/);
            holder.btnFinalizar.setEnabled(true); // Habilitar el botón
        }

        holder.btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btnFinalizar.setBackgroundColor(Color.parseColor("#FF01579B"));
                holder.btnFinalizar.setTextColor(Color.WHITE);
                holder.btnFinalizar.setText("TERMINADO");

                // Obtener el valor de tvID
                String tvIDValue = detalle.getId();
                String tvIDLocal = detalle.getLocal();
                String tvIDMotivo = detalle.getMotivo();

                // Llamar al método onButtonClick de la interfaz
                if (buttonClickListener != null) {
                    buttonClickListener.onButtonClick(tvIDValue,tvIDLocal,tvIDMotivo);
                }

                // Actualizar el estado de isFinalizado en el modelo
                detalle.setFinalizado(true);

                // Deshabilitar el botón después de hacer clic
                holder.btnFinalizar.setEnabled(false);
            }
        });
    }


    @Override
    public int getItemCount() {
        return detalleList.size();
    }

    static class DetailViewHolder extends RecyclerView.ViewHolder {
        TextView tvID;
        TextView tvFecha;
        TextView tvLocal;
        TextView tvMotivo;
        TextView tvFechafin;
        TextView tvtiempo;
        Button btnFinalizar; // Add the button reference

        DetailViewHolder(View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.id_txt_r);
            tvFecha = itemView.findViewById(R.id.fecha_txt_r);
            tvLocal = itemView.findViewById(R.id.local_txt_r);
            tvMotivo = itemView.findViewById(R.id.motivo_txt_r);
            tvFechafin = itemView.findViewById(R.id.fecha_txt_rn);
            btnFinalizar = itemView.findViewById(R.id.btn_finalizar); // Initialize the button reference
            tvtiempo = itemView.findViewById(R.id.horas_txt);
        }
    }
    public void setButtonClickListener(ButtonClickListener listener) {
        this.buttonClickListener = listener;
    }

    public interface ButtonClickListener {
        void onButtonClick(String tvID,String local,String motivo);
    }
}
