package com.example.bottomnavigationwithdrawermenu.Fragment;

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
import com.example.bottomnavigationwithdrawermenu.Mercaderista.Adapters.DetailAdapter;
import com.example.bottomnavigationwithdrawermenu.Mercaderista.Entity.Register;
import com.example.bottomnavigationwithdrawermenu.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PremiumFragment extends Fragment {
    public static ArrayList<Register> RegisterList = new ArrayList<>();
    private RecyclerView recyclerView;
    Register reg;
    DetailAdapter adapter_;
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

        recyclerView = rootView.findViewById(R.id.myregister);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter_ = new DetailAdapter(requireContext(), RegisterList);
        recyclerView.setAdapter(adapter_);
        //adapter_.setButtonClickListener(this);
        if(user.equals("promotor")){

        }else{
            retrieveData();
        }


        return rootView;
    }

    public void retrieveData(){

        StringRequest request = new StringRequest(Request.Method.POST, "https://emaransac.com/android/reporte_visita.php",
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
                                    String inicio = object.getString("inicio");
                                    String local = object.getString("local");
                                    String motivo = object.getString("motivo");
                                    String fin = object.getString("fin");
                                    String tiempo = object.getString("tiempo");
                                    int horas = Integer.parseInt(tiempo) / 60; // Obtener la cantidad de horas
                                    int minutos = Integer.parseInt(tiempo) % 60; // Obtener la cantidad de minutos restantes
                                    reg = new Register(id,inicio,local,motivo,fin,horas + " hrs " + minutos + " min");
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
}