package com.example.bottomnavigationwithdrawermenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.ViewHolder> {
    private Context context;
    private List<itemMenu> optionList;
    private OnItemClickListener listener; // Agregar variable para el listener

    public MenuAdapter(Context context, List<itemMenu> optionList) {
        this.context = context;
        this.optionList = optionList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        itemMenu option = optionList.get(position);
        holder.bindData(option);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onItemClick(option);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return optionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.icon_image_view);
            textView = itemView.findViewById(R.id.option_text_view);
        }

        public void bindData(itemMenu option) {
            imageView.setImageResource(option.getIcon()); // Establece el icono o imagen en la ImageView
            textView.setText(option.getText()); // Establece el texto en el TextView
        }
    }

    // Interfaz para manejar el evento de clic
    public interface OnItemClickListener {
        void onItemClick(itemMenu option);
    }

    // Método para establecer el listener desde el fragmento
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}