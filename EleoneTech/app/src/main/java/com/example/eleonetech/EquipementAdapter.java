package com.example.eleonetech;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class EquipementAdapter extends RecyclerView.Adapter<EquipementAdapter.EquipementViewHolder>{
    private List<ItemEquipements> itemEquipementsList;
    private Context context;
    private OnClick onSelectedEquipement;
    private List<ItemEquipements> filterEquipementsList;

    public EquipementAdapter(Context context, List<ItemEquipements> item, OnClick selectedEquipement) {
        this.itemEquipementsList = item;
        this.context = context;
        this.onSelectedEquipement = selectedEquipement;
        this.filterEquipementsList = itemEquipementsList;
    }

    @Override
    public EquipementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new EquipementViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_item_liste_equipements, null));
    }

    @Override
    public void onBindViewHolder(EquipementViewHolder holder, int position) {
        holder.setDataEquipement(itemEquipementsList.get(position));
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.down_to_up);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return itemEquipementsList.size();
    }

    public class EquipementViewHolder extends RecyclerView.ViewHolder{
        private TextView code, etat;

        public EquipementViewHolder(View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.code_equipement);
            etat = itemView.findViewById(R.id.etat_equipement);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectedEquipement.onClickSelectedEquipement(itemEquipementsList.get(getAdapterPosition()));
                }
            });
        }

        public void setDataEquipement(ItemEquipements item){
            code.setText(item.getCode());
            if(item.getEtat().equals("V")){
                etat.setText("Visité");
            }

            else{
                etat.setText("Non visité");
            }
        }
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if(constraint == null | constraint.length() == 0){
                    filterResults.count = filterEquipementsList.size();
                    filterResults.values = filterEquipementsList;
                }

                else {
                    String chercher = constraint.toString().toLowerCase();
                    List<ItemEquipements> resultData = new ArrayList<>();
                    for(ItemEquipements row : filterEquipementsList){
                        if(row.getCode().toLowerCase().contains(chercher.trim())){
                            resultData.add(row);
                        }
                    }
                    filterResults.count = resultData.size();
                    filterResults.values = resultData;
                }

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                itemEquipementsList = (List<ItemEquipements>) results.values;
                if(getItemCount() == 0){
                    ((ListeEquipementsActivity)context).setAucunEquipement("Vous n'avez pas encore des équipements à afficher..");
                    notifyDataSetChanged();
                }

                else{
                    ((ListeEquipementsActivity)context).setEquipementInformationVide();
                    notifyDataSetChanged();
                }
            }
        };
    }
}
