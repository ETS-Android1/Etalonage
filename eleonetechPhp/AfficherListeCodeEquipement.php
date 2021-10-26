<?php
    function connexionDataBase(){
        return (new PDO("mysql:host=localhost;dbname=eleonetech", "root", "", array(PDO::ATTR_PERSISTENT => true)));
    }

    function getCodeEquipement(){
        $connect = connexionDataBase();
        $requete = "SELECT Code_equipement, Etat FROM equipement ORDER BY Code_equipement ASC";
        $statement = $connect->prepare($requete);
        $statement->execute(); 
        $nbr = $statement->rowCount();
        $resultat = "";

        if($nbr > 0){
            while($row = $statement->fetch(PDO::FETCH_ASSOC)){
                $equipement[] = $row; 
            }
    
            $resultat = json_encode(array("equipements" => $equipement));
        }

        else{
            $resultat = "Aucun équipement trouvé";
        }
        
        echo $resultat;
    }

    getCodeEquipement();
?>