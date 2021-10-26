<?php
    function connexionDataBase(){
        return (new PDO("mysql:host=localhost;dbname=eleonetech", "root", "", array(PDO::ATTR_PERSISTENT => true)));
    }

    function creerRapport($description, $emplacement, $numeroSerie, $technicien, $societe, $remarque, $codeEquipement, $categorie, $exigence, $lieu){
        $connect = connexionDataBase();
        $etat = "V";
        $date = date('Y-m-d H:i');

        $requete = "INSERT INTO rapport(description, emplacement, num_serie, technicien, societe, remarque, etat, date_visite, code_equipement, categorie, exigence, lieu)
        VALUES ('$description', '$emplacement', '$numeroSerie', '$technicien', '$societe', '$remarque', '$etat', '$date', '$codeEquipement', '$categorie', '$exigence', '$lieu')";

        if($connect->exec($requete) == 1){
            echo 'Rapport crée';
        }

        else{
            echo 'Rapport non crée';
        }
    }

    creerRapport($_POST['description'], $_POST['emplacement'], $_POST['numeroSerie'], $_POST['technicien'], $_POST['societe'], $_POST['remarque'], $_POST['codeEquipement'], $_POST['categorie'], $_POST['exigence'], $_POST['lieu']);
?>