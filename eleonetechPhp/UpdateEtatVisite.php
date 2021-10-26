<?php
    function connexionDataBase(){
        return (new PDO("mysql:host=localhost;dbname=eleonetech", "root", "", array(PDO::ATTR_PERSISTENT => true)));
    }

    function updateEtat($code){
        $connect = connexionDataBase();

        $requete = "UPDATE equipement SET Etat = 'V' WHERE Code_equipement = '$code'";

        if($connect->exec($requete) == 1){
            echo "Etat modifié";
        }

        else{
            echo "Etat non modifié";
        }
    }

    updateEtat($_POST['code']);
?>