package com.example.bottomnavigationwithdrawermenu.Mercaderista.Adapters;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.bottomnavigationwithdrawermenu.Mercaderista.Entity.Register;
import com.example.bottomnavigationwithdrawermenu.R;
import com.example.bottomnavigationwithdrawermenu.Ubication.GpsTracker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder> {
    private Context context;
    private List<Register> detalleList;

    GpsTracker gpsTracker;


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

                //Toast.makeText(context,tvIDValue,Toast.LENGTH_SHORT).show();
                updateData(tvIDValue);
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

        DetailViewHolder(View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.id_txt_r);
            tvFecha = itemView.findViewById(R.id.fecha_txt_r);
            tvLocal = itemView.findViewById(R.id.local_txt_r);
            tvMotivo = itemView.findViewById(R.id.motivo_txt_r);
            tvFechafin = itemView.findViewById(R.id.fecha_txt_rn);
            btnFinalizar = itemView.findViewById(R.id.btn_finalizar); // Initialize the button reference
            tvtiempo = itemView.findViewById(R.id.horas_txt);
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


        //Toast.makeText(Register_Activity.this,mylocal+" "+mymotivo,Toast.LENGTH_LONG).show();

        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("cargando...");


        progressDialog.show();

        String a_lat = "0";
        String a_lon = "0";
        a_lat = getLocs(1);
        a_lon = getLocs(2);


        String finalA_lon = a_lon;
        String finalA_lat = a_lat;
        StringRequest request = new StringRequest(Request.Method.POST, "https://emaransac.com/mercapp/merchant/update_visit_record.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.equalsIgnoreCase("Se guardo correctamente.")){
                            //Toast.makeText(Register_Activity.this, "Se guardo correctamente.", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            //finish();
                        }
                        else{
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
        }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();

                //Log.d("---REPORTE--DE--PRECIOS---", observacines_+">>>>>>>>>>>> ");
                params.put("id",id);
                params.put("flog", finalA_lon);
                params.put("flat", finalA_lat);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);

        requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {

            @Override
            public void onRequestFinished(Request<Object> request) {
                //por ahora
                //RegisterList.clear();
                //retrieveData();
            }
        });
    }

    public String getLocs(int ID) { //Get Current Lat and Lon 1=lat, 2=lon
        String asd_lat = "";
        String asd_lon = "";
        gpsTracker = new GpsTracker(context);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            asd_lat = String.valueOf(latitude);
            asd_lon = String.valueOf(longitude);
        } else {
            gpsTracker.showSettingsAlert();
        }
        if (ID == 1) {
            return asd_lat;
        } else if (ID == 2) {
            return asd_lon;
        } else {
            return "0";
        }
    }
}
