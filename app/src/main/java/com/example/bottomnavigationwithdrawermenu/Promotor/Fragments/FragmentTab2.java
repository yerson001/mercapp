package com.example.bottomnavigationwithdrawermenu.Promotor.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.bottomnavigationwithdrawermenu.Promotor.Adapters.PfAdapter;
import com.example.bottomnavigationwithdrawermenu.Promotor.Adapters.PriceAdapter;
import com.example.bottomnavigationwithdrawermenu.Promotor.Entities.Frescos;
import com.example.bottomnavigationwithdrawermenu.Promotor.Entities.prices;
import com.example.bottomnavigationwithdrawermenu.R;

import java.util.ArrayList;

public class FragmentTab2 extends Fragment {

    String[] marcas = {
            "SIVARITA",
            "BATAN",
            "LOPESA",
            "BADIA"};

    String[] categoria = {
            "TRADICIONAL",
            "NO TRADICIONAL",
            "OTROS"};

    String[] marcasarr = {"------SELECCIONE------",
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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    AutoCompleteTextView autoCompleteTxtMarca;
    ArrayAdapter<String> adapterMarca;
    AutoCompleteTextView autoCompleteTxtCategoria;
    ArrayAdapter<String> adapterCategoria;
    prices pri;

    public static ArrayList<prices> pricesArrayList = new ArrayList<>();
    private RecyclerView recyclerView;
    private PriceAdapter adapter;


    private String mParam1;
    private String mParam2;

    public FragmentTab2() {
        // Required empty public constructor
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

        View rootView = inflater.inflate(R.layout.fragment_tab2, container, false);

        for (int i = 1; i < marcasarr.length; i++) {
            pri = new prices(Integer.toHexString(i), marcasarr[i]," "," "," "," ");
            pricesArrayList.add(pri);
        }


        autoCompleteTxtMarca = rootView.findViewById(R.id.marca_txt);
        adapterMarca = new ArrayAdapter<>(requireContext(), R.layout.distrib_item, marcas);
        autoCompleteTxtMarca.setAdapter(adapterMarca);

        autoCompleteTxtCategoria = rootView.findViewById(R.id.categoria_txt);
        adapterCategoria = new ArrayAdapter<>(requireContext(), R.layout.distrib_item, categoria);
        autoCompleteTxtCategoria.setAdapter(adapterCategoria);

        recyclerView = rootView.findViewById(R.id.recycler_price_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new PriceAdapter(getContext(), pricesArrayList,pricesArrayList);
        recyclerView.setAdapter(adapter);

        return rootView;
    }
}