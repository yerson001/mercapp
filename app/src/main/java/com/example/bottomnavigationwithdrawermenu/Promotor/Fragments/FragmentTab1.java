package com.example.bottomnavigationwithdrawermenu.Promotor.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomnavigationwithdrawermenu.Promotor.Adapters.PfAdapter;
import com.example.bottomnavigationwithdrawermenu.Promotor.Entities.Frescos;
import com.example.bottomnavigationwithdrawermenu.R;

import java.util.ArrayList;

public class FragmentTab1 extends Fragment  {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String[] Distribuidores = {"ADRIEL MAMANI-AREQUIPA",
                               "EMUNAH PERU S.A.C",
                                "FERNANDO FLORES RAMOS",
                                "JOSE LUIS CARRASCO-CAMANA",
                                "DORA MAMANI QUISPE-TACNA",
                                "DISTRIBUCIONES Y REP DELFÍN Y ROSA",
                                "T&C COMERCIALIZADORA",
                                "CORPORACION VALLESUR S.A.C",
                                "DISTRIBUCIONES PRODESUR S.A.C",
                                "CACERES PH DISTRIBUCIONES S.A.C",
                                "DILSOR",
                                "RG DIGO E.I.R.L",
                                "GLOMISEB",
                                "COMERSUCHI",
                                "INCAMONT",
                                "ALM Y DIS HUAMANGA",
                                "TULIP MART"
    };

    String[] categorias = {"Bodega",
                            "Minimarket",
                            "Supermercado",
                            "Especiería",
                            "Puesto de Mercado",
                            "Tienda de Abarrotes",
                            "Food Truck","Puesto de Comida",
                            "Snack",
                            "Carniceria",
                            "Pizzeria",
                            "Otros"};
    String[] polvosarr = {"------SELECCIONE------",
            "SAZONADOR COMPLETO  GIGANTE X 42 SBS",
            "COMINO MOLIDO GIGANTE X 42 SBS",
            "PIMIENTA BATAN GIGANTE X 42 SBS",
            "PALILLO BATAN  GIGANTE X 42 SBS",
            "TUCO SAZON SALSA BATAN GIGANTE X 42 SBS",
            "AJO BATAN GIGANTE X 42 SBS",
            "CANELA MOLIDA GIGANTE X 42 SBS",
            "EL VERDE BATAN GIGANTE X 42 SBS",
            "KION MOLIDO BATAN GIGANTE X 42 SBS"
            ,"OREGANO SELECTO BATAN X 42 SBS",
            "EL VERDE BATAN GIGANTE x 27 SBS",
            "AJI PANCA FRESCO BATAN x 24 SBS",
            "AJI AMARILLO FRESCO BATAN x24 SBS",
            "AJO FRESCO BATAN x 24 SBS",
            "CULANTRO FRESCO BATAN x 24 SBS"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterDistribuidores;

    AutoCompleteTextView autoCompleteTxtcat;
    ArrayAdapter<String> adapterCategoria;
    public static ArrayList<Frescos> pro_polvos = new ArrayList<>();

    private RecyclerView recyclerView, recyclerView2;

    private PfAdapter adapter, adapter2;

    private String mParam1;
    private String mParam2;

    public FragmentTab1() {
        // Required empty public constructor
    }

    public static FragmentTab1 newInstance(String param1, String param2) {
        FragmentTab1 fragment = new FragmentTab1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_tab1, container, false);
        Frescos frecos;
        pro_polvos.clear();


        for (int i = 1; i < polvosarr.length; i++) {
            frecos = new Frescos(Integer.toHexString(i), polvosarr[i]);
            pro_polvos.add(frecos);
        }


        autoCompleteTxt = rootView.findViewById(R.id.distribuidor_txt);
        adapterDistribuidores = new ArrayAdapter<>(requireContext(), R.layout.distrib_item, Distribuidores);
        autoCompleteTxt.setAdapter(adapterDistribuidores);


        autoCompleteTxtcat = rootView.findViewById(R.id.categoria_txt);
        adapterCategoria = new ArrayAdapter<>(requireContext(), R.layout.distrib_item, categorias);
        autoCompleteTxtcat.setAdapter(adapterCategoria);


        recyclerView = rootView.findViewById(R.id.mypolvos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PfAdapter(getContext(), pro_polvos,pro_polvos);
        recyclerView.setAdapter(adapter);
        //adapter.setCallback(getContext());

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                // Acción que se debe mandar a los campos editables
                String item = parent.getItemAtPosition(position).toString();

                Toast.makeText(requireContext(), "Tienda: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        autoCompleteTxtcat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                // Acción que se debe mandar a los campos editables
                String item = parent.getItemAtPosition(position).toString();

                Toast.makeText(requireContext(), "Tienda: " + item, Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }
}