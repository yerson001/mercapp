package com.example.bottomnavigationwithdrawermenu.Promotor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.bottomnavigationwithdrawermenu.Promotor.Entities.detalle;
import com.example.bottomnavigationwithdrawermenu.R;

import java.util.List;

public class DetalleAdapter  extends ArrayAdapter<detalle>
{
    Context context;
    List<detalle> detalleList;


    public DetalleAdapter(@NonNull Context context, List<detalle> detalleList) {
        super(context, R.layout.list_reporte,detalleList);
        this.context =context;
        this.detalleList =detalleList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_reporte,null,true);

        TextView tvID = view.findViewById(R.id.id_txt_pro);
        TextView tvFecha = view.findViewById(R.id.fecha_txt_pro);
        TextView tvDistribuidor = view.findViewById(R.id.distribuidor_txt_pro);
        TextView tvCliente = view.findViewById(R.id.cliente_txt_pro);
        TextView tvTelefono = view.findViewById(R.id.telefono_txt_pro);
        TextView tvDireccion = view.findViewById(R.id.direccion_txt_pro);

        tvID.setText(detalleList.get(position).getId());
        tvFecha.setText(detalleList.get(position).getFecha());
        tvDistribuidor.setText(detalleList.get(position).getDistribuidor());
        tvCliente.setText(detalleList.get(position).getCliente());
        tvTelefono.setText(detalleList.get(position).getTelefono());
        tvDireccion.setText(detalleList.get(position).getDirrecion());
        return view;
    }


}