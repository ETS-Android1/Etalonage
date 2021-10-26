package com.example.eleonetech;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.List;

public class AideFragement extends Fragment {
    private AideAdapter aideAdapter;
    private ViewPager2 viewPager;
    private LinearLayout linearLayoutIndicateurs;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.activity_aide_home, container, false);
        linearLayoutIndicateurs = (LinearLayout) view.findViewById(R.id.linear_layout_indicateurs_aide_home);
        configurationItemAide();

        viewPager = (ViewPager2) view.findViewById(R.id._view_pager_aide_home);
        viewPager.setAdapter(aideAdapter);
        configurationIndicateurs();
        setCurrentConfigurationIndicateurs(0);

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentConfigurationIndicateurs(position);
            }
        });
        return view;
    }

    public void configurationItemAide(){
        List<ItemAide> ItemAideCreer = new ArrayList<>();
        ItemAide visitesAide = new ItemAide();
        visitesAide.setImage(R.drawable.planning);
        visitesAide.setTitre("Visites");
        visitesAide.setDescription("Evaluation de la conformité des appareils de mesure et consulter le planning des visites .");


        ItemAide equipementsAide = new ItemAide();
        equipementsAide.setImage(R.drawable.equipement_info);
        equipementsAide.setTitre("Equipements");
        equipementsAide.setDescription("Afficher les informations relatives aux équipements trouvés.");

        ItemAide certificatsAide = new ItemAide();
        certificatsAide.setImage(R.drawable.certificat);
        certificatsAide.setTitre("Certificats");
        certificatsAide.setDescription("Afficher les certificats des équipements trouvés dans la sociétés.");

        ItemAide geolocalisationAide = new ItemAide();
        geolocalisationAide.setImage(R.drawable.rapports_png);
        geolocalisationAide.setTitre("Rapports");
        geolocalisationAide.setDescription("Consulter les rapports des équipements visités.");

        ItemAide profilAide = new ItemAide();
        profilAide.setImage(R.drawable.profil_aide);
        profilAide.setTitre("Profil");
        profilAide.setDescription("Votre profil est dans vos mains. Modifier vos informations configurer le compte façilement.");

        ItemAideCreer.add(visitesAide);
        ItemAideCreer.add(equipementsAide);
        ItemAideCreer.add(certificatsAide);
        ItemAideCreer.add(geolocalisationAide);
        ItemAideCreer.add(profilAide);

        aideAdapter = new AideAdapter(ItemAideCreer);
    }

    public void configurationIndicateurs(){
        ImageView[] indicateurs = new ImageView[aideAdapter.getItemCount()];

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT
        );

        layoutParams.setMargins(8,0,8,0);
        for(int i = 0; i<indicateurs.length; i++){
            indicateurs[i] = new ImageView(getActivity());
            indicateurs[i].setImageDrawable(ContextCompat.getDrawable(
                    getActivity(),
                    R.drawable.indicateur_oranger
            ));
            indicateurs[i].setLayoutParams(layoutParams);
            linearLayoutIndicateurs.addView(indicateurs[i]);
        }
    }

    public void setCurrentConfigurationIndicateurs(int index){
        int childCount = linearLayoutIndicateurs.getChildCount();
        for (int i = 0; i<childCount; i++){
            ImageView imageView = (ImageView) linearLayoutIndicateurs.getChildAt(i);
            if(i == index){
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getActivity(),R.drawable.indicateur_bleu)
                );
            }

            else {
                imageView.setImageDrawable(
                        ContextCompat.getDrawable(getActivity(),R.drawable.indicateur_oranger)
                );
            }
        }
    }
}