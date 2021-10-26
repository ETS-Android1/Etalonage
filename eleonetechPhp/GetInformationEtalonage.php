<?php
    function connexionDataBase(){
        return (new PDO("mysql:host=localhost;dbname=eleonetech", "root", "", array(PDO::ATTR_PERSISTENT => true)));
    }

    function getCodeEquipement(){
        $connect = connexionDataBase();
        $requete = "SELECT code_equipement FROM rapport";
        $statement = $connect->prepare($requete);
        $statement->execute(); 
        $nbr = $statement->rowCount();
        $resultat = "";

        if($nbr > 0){
            while($row = $statement->fetch(PDO::FETCH_ASSOC)){
                $rapport[] = $row; 
            }
    
            $resultat = json_encode(array("codes" => $rapport));
        }

        else{
            $resultat = "Aucun code trouvé";
        }
        
        echo $resultat;
    }

    getCodeEquipement();
?>