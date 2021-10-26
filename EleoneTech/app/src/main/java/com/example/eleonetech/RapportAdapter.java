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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RapportAdapter extends RecyclerView.Adapter<RapportAdapter.RapportViewHolder>{
    private List<ItemRapport> itemRapportList;
    private Context context;
    private OnClick onSelectedRapport;
    private List<ItemRapport> filterItemRapportList;

    public RapportAdapter(Context context, List<ItemRapport> item, OnClick selectedRapport) {
        this.itemRapportList = item;
        this.context = context;
        this.onSelectedRapport = selectedRapport;
        this.filterItemRapportList = itemRapportList;
    }

    @Override
    public RapportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new RapportAdapter.RapportViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_item_rapport, null));
    }

    @Override
    public void onBindViewHolder(RapportViewHolder holder, int position) {
        holder.setDataRapport(itemRapportList.get(position));
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.down_to_up);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return itemRapportList.size();
    }

    public class RapportViewHolder extends RecyclerView.ViewHolder{
        private TextView code, dateFin;

        public RapportViewHolder(View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.code_equipement);
            dateFin = itemView.findViewById(R.id.date_fin_validite_equipement);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectedRapport.onClickSelectedRapport(itemRapportList.get(getAdapterPosition()));
                }
            });
        }

        public void setDataRapport(ItemRapport item){
            code.setText(item.getCodeEquipement());
            dateFin.setText(stylingDateFinValidite(item.getDateFinValidite()));
        }

        public String stylingDateFinValidite(String dateFinValidite){
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            SimpleDateFormat formatter2 = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            Date date = null;

            try {
                date = formatter1.parse(dateFinValidite);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return formatter2.format(date);
        }
    }

    public Filter getFilter(){
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if(constraint == null | constraint.length() == 0){
                    filterResults.count = filterItemRapportList.size();
                    filterResults.values = filterItemRapportList;
                }

                else {
                    String chercher = constraint.toString().toLowerCase();
                    List<ItemRapport> resultData = new ArrayList<>();
                    for(ItemRapport row : filterItemRapportList){
                        if(row.getCodeEquipement().toLowerCase().contains(chercher.trim())){
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
                itemRapportList = (List<ItemRapport>) results.values;
                if(getItemCount() == 0){
                    ((ChercherRapportActivity)context).setAucunRapport("Vous n'avez pas encore des rappors à afficher..");
                    notifyDataSetChanged();
                }

                else{
                    ((ChercherRapportActivity)context).setExisteRapport();
                    notifyDataSetChanged();
                }
            }
        };
    }
}
