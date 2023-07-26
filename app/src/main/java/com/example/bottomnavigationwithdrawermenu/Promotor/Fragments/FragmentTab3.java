package com.example.bottomnavigationwithdrawermenu.Promotor.Fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bottomnavigationwithdrawermenu.MainActivity;
import com.example.bottomnavigationwithdrawermenu.Promotor.PromotorActivity;
import com.example.bottomnavigationwithdrawermenu.R;

public class FragmentTab3 extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    String[] TipoEnvase = {"Display","Taper","Paquete","Bolsa","Frasco","Otros"};
    String[] TipoProducto = {"Polvo","Fresco","Entero","Agranel","Otros"};
    String[] TipoSobre = {"Gigante","Pequeño","Sachet","Sobre Económico"};
    String[] ClasiProduct = { "Tradicional","No tradicinal","Otros"};

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
    public FragmentTab3() {
        // Required empty public constructor
    }

    public static FragmentTab3 newInstance(String param1, String param2) {
        FragmentTab3 fragment = new FragmentTab3();
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
        View rootView = inflater.inflate(R.layout.fragment_tab3, container, false);

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



        Button myButton = rootView.findViewById(R.id.btn_guardar);

        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPromattendance();
            }
        });

        return rootView;
    }

    private void showPromattendance() {

        final Dialog dialogp = new Dialog(getContext());
        dialogp.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialogp.setContentView(R.layout.bottomsheetlayoutmarca);

        ImageView cancelButton = dialogp.findViewById(R.id.cancelButton);
        ImageView cancelButtonp = dialogp.findViewById(R.id.cancelButtonp);

        TextView txtmotivop = dialogp.findViewById(R.id.txtmotivop);
        Button btnLogh = dialogp.findViewById(R.id.btnLoginh);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogp.dismiss();
            }
        });

        cancelButtonp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogp.dismiss();
            }
        });

        dialogp.show();
        dialogp.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialogp.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialogp.getWindow().setGravity(Gravity.BOTTOM);
    }
}