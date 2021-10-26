package com.example.eleonetech;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AideAdapter extends RecyclerView.Adapter<AideAdapter.AideViewHolder>{
    private List<ItemAide> itemAideList;

    public AideAdapter(List<ItemAide> item) {
        this.itemAideList = item;
    }

    @NonNull
    @Override
    public AideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AideViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.activity_item_aide,parent,false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull AideViewHolder holder, int position) {
        holder.setDataAide(itemAideList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemAideList.size();
    }

    public class AideViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageAide;
        private TextView titreAide, descriptionAide;

        public AideViewHolder(@NonNull View itemView) {
            super(itemView);
            imageAide = itemView.findViewById(R.id.image_item_aide);
            titreAide = itemView.findViewById(R.id.titre_item_aide);
            descriptionAide = itemView.findViewById(R.id.description_item_aide);
        }

        public void setDataAide(ItemAide item){
            imageAide.setImageResource(item.getImage());
            titreAide.setText(item.getTitre());
            descriptionAide.setText(item.getDescription());
        }
    }
}
