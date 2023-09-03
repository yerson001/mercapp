package com.example.bottomnavigationwithdrawermenu.cvapp.Adapters;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
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
import com.example.bottomnavigationwithdrawermenu.cvapp.Entity.Register;
import com.example.bottomnavigationwithdrawermenu.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {
    private Context context;
    private List<Register> detalleList;
    private OnItemClickListener listener;




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
        holder.tvestado.setText(detalle.getEstado());

        final int currentPosition = position; // Declarar una variable final adicional


        holder.btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btnFinalizar.setBackgroundColor(Color.parseColor("#FF01579B"));
                holder.btnFinalizar.setTextColor(Color.WHITE);
                holder.btnFinalizar.setText("TERMINADO");

                // Obtener el valor de tvID
                String tvIDValue = detalle.getId();
                updateData(tvIDValue);
            }
        });

        holder.btnInciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.btnFinalizar.setBackgroundColor(Color.parseColor("#FF01579B"));
                holder.btnFinalizar.setTextColor(Color.RED);
                holder.btnFinalizar.setText("SE INICIO");

                // Obtener el valor de tvID
                String tvIDValue = detalle.getId();
                updateDatael(tvIDValue);
            }
        });



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(position);
                }
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
        TextView tvestado;
        Button btnFinalizar; // Add the button reference
        Button btnInciar; // Add the button reference
        ProgressBar progressBar;

        DetailViewHolder(View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.id_txt_r);
            tvFecha = itemView.findViewById(R.id.fecha_txt_r);
            tvLocal = itemView.findViewById(R.id.local_txt_r);
            tvMotivo = itemView.findViewById(R.id.motivo_txt_r);
            tvFechafin = itemView.findViewById(R.id.fecha_txt_rn);
            btnFinalizar = itemView.findViewById(R.id.btn_finalizar); // Initialize the button reference
            btnInciar = itemView.findViewById(R.id.btn_iniciar);
            tvtiempo = itemView.findViewById(R.id.horas_txt);
            progressBar = itemView.findViewById(R.id.progressBar);
            tvestado = itemView.findViewById(R.id.estado_txt);
        }
    }
    public void setButtonClickListener(ButtonClickListener listener) {
        this.buttonClickListener = listener;
    }

    public interface ButtonClickListener {
        void onButtonClick(String tvID,String local,String motivo);
    }
    private void updateData(String id) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "https://emaransac.com/mercapp/merchant/actualizar.php",
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

    private void updateDatael(String id) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando...");
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, "https://emaransac.com/mercapp/merchant/Iniciar.php",
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






    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

