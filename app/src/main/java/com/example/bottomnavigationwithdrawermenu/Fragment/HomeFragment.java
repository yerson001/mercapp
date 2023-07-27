package com.example.bottomnavigationwithdrawermenu.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottomnavigationwithdrawermenu.MenuAdapter;
import com.example.bottomnavigationwithdrawermenu.Mercaderista.SummaryActivity;
import com.example.bottomnavigationwithdrawermenu.Promotor.PromotorActivity;
import com.example.bottomnavigationwithdrawermenu.Promotor.SummaryProActivity;
import com.example.bottomnavigationwithdrawermenu.R;
import com.example.bottomnavigationwithdrawermenu.itemMenu;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements MenuAdapter.OnItemClickListener {

    private MenuAdapter menu;
    private RecyclerView recyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);




        recyclerView = rootView.findViewById(R.id.grid_view_r_ms);
        List<itemMenu> optionList = createOptionList(); // Método que crea la lista de opciones

        // Configurar el RecyclerView con un GridLayoutManager
        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(), 2); // Muestra 2 columnas en la cuadrícula
        recyclerView.setLayoutManager(layoutManager);

        menu = new MenuAdapter(requireContext(), optionList);

        // Establecer el listener del adaptador
        menu.setOnItemClickListener(this);

        recyclerView.setAdapter(menu);


        return rootView;
    }

    private List<itemMenu> createOptionList() {
        List<itemMenu> options = new ArrayList<>();
        options.add(new itemMenu(R.drawable.baseline_diversity_3_24, "PROMOTOR VENTAS"));
        options.add(new itemMenu(R.drawable.baseline_add_business_24, "ACOMPAÑAMIENTO"));
        options.add(new itemMenu(R.drawable.baseline_monetization_on_24, "REPORTE PRECIOS"));
        options.add(new itemMenu(R.drawable.baseline_content_paste_go_24, "INVENTARIO"));
        options.add(new itemMenu(R.drawable.settings_icn, "AJUSTES"));
        // Agrega más elementos según tus necesidades
        return options;
    }

    @Override
    public void onItemClick(itemMenu option) {

        if (option.getText().equals("PROMOTOR VENTAS")) {
            ProgressDialog progressDialog = new ProgressDialog(requireContext());
            progressDialog.setMessage("Cargando...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            // Aquí puedes abrir una nueva actividad si se selecciona la opción "PROMOTOR VENTAS"
            Intent intent = new Intent(requireContext(), PromotorActivity.class);
            startActivity(intent);
        } else if (option.getText().equals("INVENTARIO")) {
            // Aquí puedes abrir una nueva actividad si se selecciona la opción "INVENTARIO"
            ProgressDialog progressDialog = new ProgressDialog(requireContext());
            progressDialog.setMessage("Cargando...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            Intent intent = new Intent(requireContext(), SummaryActivity.class);
            startActivity(intent);
        } else if (option.getText().equals("ACOMPAÑAMIENTO")) {
            ProgressDialog progressDialog = new ProgressDialog(requireContext());
            progressDialog.setMessage("Cargando...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            Intent intent = new Intent(requireContext(), SummaryProActivity.class);
            startActivity(intent);
        } else if (option.getText().equals("AJUSTES")) {
            Toast.makeText(requireContext(), "opciones!", Toast.LENGTH_SHORT).show();
        }
    }
}

