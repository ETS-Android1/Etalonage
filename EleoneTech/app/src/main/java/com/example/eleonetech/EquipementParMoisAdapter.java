package com.example.eleonetech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class EquipementParMoisAdapter extends RecyclerView.Adapter<EquipementParMoisAdapter.EquipementViewHolder>{
    private List<ItemEquipementParDate> itemEquipementsList;
    private Context context;
    private OnClick onSelectedEquipement;

    public EquipementParMoisAdapter( Context context, List<ItemEquipementParDate> itemEquipementsList, OnClick onSelectedEquipement) {
        this.itemEquipementsList = itemEquipementsList;
        this.context = context;
        this.onSelectedEquipement = onSelectedEquipement;
    }

    @NonNull
    @Override
    public EquipementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new EquipementParMoisAdapter.EquipementViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_item_equipement_par_mois, null));
    }

    @Override
    public void onBindViewHolder(@NonNull EquipementParMoisAdapter.EquipementViewHolder holder, int position) {
        holder.setDataEquipement(itemEquipementsList.get(position));
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.down_to_up);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return itemEquipementsList.size();
    }

    public class EquipementViewHolder extends RecyclerView.ViewHolder{
        private TextView code, description, dateFinValidite;

        public EquipementViewHolder(@NonNull View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.code_equipement);
            description = itemView.findViewById(R.id.description_equipement);
            dateFinValidite = itemView.findViewById(R.id.date_fin_validite_equipement);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectedEquipement.onClickSelectedEquipementParMois(itemEquipementsList.get(getAdapterPosition()));
                }
            });
        }

        public void setDataEquipement(ItemEquipementParDate item){
            code.setText(item.getCode());
            description.setText(item.getDescription());
            dateFinValidite.setText(item.getDateFinValidite());

        }
    }
}
