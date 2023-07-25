package com.example.bottomnavigationwithdrawermenu.Promotor.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.bottomnavigationwithdrawermenu.R;
public class FragmentTab2 extends Fragment {

    String[] TipoEnvase = {"Display","Taper","Paquete","Bolsa","Frasco","Otros"};
    String[] TipoProducto = {"Polvo","Fresco","Entero","Agranel","Otros"};
    String[] TipoSobre = {"Gigante","Pequeño","Sachet","Sobre Económico"};
    String[] ClasiProduct = { "Tradicional","No tradicinal","Otros"};

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    AutoCompleteTextView tipoenvase;
    ArrayAdapter<String> adaptertipoenvase;

    AutoCompleteTextView tipoproducto;
    ArrayAdapter<String> adaptertipoproducto;

    AutoCompleteTextView tiposobre;
    ArrayAdapter<String> adaptertiposobre;

    AutoCompleteTextView clasificacion;
    ArrayAdapter<String> adapterclasificacion;


    private String mParam1;
    private String mParam2;

    public FragmentTab2() {
        // Required empty public constructor
    }

    public static FragmentTab2 newInstance(String param1, String param2) {
        FragmentTab2 fragment = new FragmentTab2();
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

        View rootView = inflater.inflate(R.layout.fragment_tab2, container, false);

        tipoenvase = rootView.findViewById(R.id.tipo_envase_txt);
        adaptertipoenvase = new ArrayAdapter<>(requireContext(), R.layout.distrib_item, TipoEnvase);
        tipoenvase.setAdapter(adaptertipoenvase);

        tipoproducto = rootView.findViewById(R.id.tipo_producto_txt);
        adaptertipoproducto = new ArrayAdapter<>(requireContext(), R.layout.distrib_item, TipoProducto);
        tipoproducto.setAdapter(adaptertipoproducto);

        tiposobre = rootView.findViewById(R.id.tipo_sobre_txt);
        adaptertiposobre = new ArrayAdapter<>(requireContext(), R.layout.distrib_item, TipoSobre);
        tiposobre.setAdapter(adaptertiposobre);

        clasificacion = rootView.findViewById(R.id.clasifiacion_txt);
        adapterclasificacion = new ArrayAdapter<>(requireContext(), R.layout.distrib_item, ClasiProduct);
        clasificacion.setAdapter(adapterclasificacion);

        return rootView;
    }
}