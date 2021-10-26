package com.example.eleonetech;

import android.content.Context;
import android.graphics.Paint;
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

public class CertificatAdapter extends RecyclerView.Adapter<CertificatAdapter.CertificatViewHolder> {
    private List<ItemCertificat> itemCertificatList;
    private Context context;
    private OnClick onSelectedCertif;
    private List<ItemCertificat> filterCertificatList;

    public CertificatAdapter( Context context, List<ItemCertificat> item, OnClick onSelectedCertif) {
        this.itemCertificatList = item;
        this.context = context;
        this.onSelectedCertif = onSelectedCertif;
        this.filterCertificatList = itemCertificatList;
    }

    @Override
    public CertificatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new CertificatAdapter.CertificatViewHolder(LayoutInflater.from(context).inflate(R.layout.activity_item_certif, null));
    }

    @Override
    public void onBindViewHolder(CertificatViewHolder holder, int position) {
        holder.setDataCertif(itemCertificatList.get(position));
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.down_to_up);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return itemCertificatList.size();
    }

    public class CertificatViewHolder extends RecyclerView.ViewHolder{
        private TextView code, date, certif;

        public CertificatViewHolder(View itemView){
            super(itemView);
            code = itemView.findViewById(R.id.code_equipement);
            date = itemView.findViewById(R.id.date_certif);
            certif = itemView.findViewById(R.id.certificat);
            certif.setPaintFlags(certif.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSelectedCertif.onClickSelectedCertif(itemCertificatList.get(getAdapterPosition()));
                }
            });
        }

        public void setDataCertif(ItemCertificat item){
            code.setText(item.getCode());
            date.setText(stylingDateFinValidite(item.getDate()));
            String str = item.getCertif();
            certif.setText(str.replace("certifs//", ""));
        }

        public String stylingDateFinValidite(String dateFinValidite){
            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
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
                    filterResults.count = filterCertificatList.size();
                    filterResults.values = filterCertificatList;
                }

                else {
                    String chercher = constraint.toString().toLowerCase();
                    List<ItemCertificat> resultData = new ArrayList<>();
                    for(ItemCertificat row : filterCertificatList){
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
                itemCertificatList = (List<ItemCertificat>) results.values;
                if(getItemCount() == 0){
                    ((CertificatActivity)context).setAucunCertif("Vous n'avez pas encore des certificats à afficher..");
                    notifyDataSetChanged();
                }

                else{
                    ((CertificatActivity)context).setExisteCertif();
                    notifyDataSetChanged();
                }
            }
        };
    }
}
