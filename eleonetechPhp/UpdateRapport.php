<?php
    function connexionDataBase(){
        return (new PDO("mysql:host=localhost;dbname=eleonetech", "root", "", array(PDO::ATTR_PERSISTENT => true)));
    }

    function updateRapport($code, $etat, $dateVisite){
        $connect = connexionDataBase();

        $requete = "UPDATE equipement SET Etat = '$etat', Date_Fin_Validite = '$dateVisite' WHERE Code_equipement = '$code'";

        if($connect->exec($requete) == 1){
            echo "Equipement modifié";
        }

        else{
            echo "Equipement non modifié";
        }
    }

    updateRapport($_POST['code'], $_POST['etat'], $_POST['date']);
?>