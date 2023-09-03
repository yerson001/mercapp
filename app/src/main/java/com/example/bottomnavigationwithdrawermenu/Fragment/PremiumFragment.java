package com.example.bottomnavigationwithdrawermenu.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bottomnavigationwithdrawermenu.MainActivity;
import com.example.bottomnavigationwithdrawermenu.cvapp.Adapters.DetailAdapter;
import com.example.bottomnavigationwithdrawermenu.cvapp.Entity.Register;
import com.example.bottomnavigationwithdrawermenu.cvapp.SummaryActivity;
import com.example.bottomnavigationwithdrawermenu.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PremiumFragment extends Fragment implements DetailAdapter.ButtonClickListener {
    public static ArrayList<Register> RegisterList = new ArrayList<>();
    private RecyclerView recyclerView;
    Register reg;
    DetailAdapter adapter_;
    String urlmercad = "https://emaransac.com/mercapp/merchant/trabajo.php";
    String user="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_premium, container, false);

        Bundle args = getArguments();
        if (args != null) {
            String param1 = args.getString("param1");
            user = param1;

            Toast.makeText(getContext(),param1,Toast.LENGTH_LONG).show();
        }

        MainActivity activity = (MainActivity) getActivity();
        user = "mercaderista";

        FloatingActionButton fab = rootView.findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ProgressDialog progressDialog = new ProgressDialog(requireContext());
                progressDialog.setMessage("Cargando...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Intent intent = new Intent(requireContext(), SummaryActivity.class);
                startActivity(intent);
            }
        });



        retrieveData(urlmercad,user);

        recyclerView = rootView.findViewById(R.id.myregister);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter_ = new DetailAdapter(requireContext(), RegisterList);
        recyclerView.setAdapter(adapter_);
        //adapter_.setButtonClickListener(this);
        retrieveData(urlmercad,user);


        return rootView;
    }

    public void retrieveData(String url,String user){

        StringRequest request = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        RegisterList.clear();
                        try{

                            JSONObject jsonObject = new JSONObject(response);
                            String exito = jsonObject.getString("exito");
                            JSONArray jsonArray = jsonObject.getJSONArray("datos");
                            if(exito.equals("1")){
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String id = object.getString("id");
                                    String inicio = object.getString("start_time");
                                    String local = object.getString("store");
                                    String motivo = object.getString("reason");
                                    String fin = object.getString("end_time");
                                    String tiempo = object.getString("duration");
                                    String estado = object.getString("status");
                                    int horas = Integer.parseInt(tiempo) / 60; // Obtener la cantidad de horas
                                    int minutos = Integer.parseInt(tiempo) % 60; // Obtener la cantidad de minutos restantes
                                    reg = new Register(id,inicio,local,motivo,fin,horas + " hrs " + minutos + " min",estado,user);
                                    RegisterList.add(reg);
                                    adapter_.notifyDataSetChanged();
                                }
                            }
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }


    @Override
    public void onButtonClick(String tvID,String local,String motivo) {
        // Aquí puedes manejar la acción del botón
        Toast.makeText(getContext(), "premiun: -> " + tvID, Toast.LENGTH_SHORT).show();
        //updateData(tvID,local,motivo);
    }
}