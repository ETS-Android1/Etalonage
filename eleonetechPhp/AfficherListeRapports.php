<?php
    function connexionDataBase(){
        return (new PDO("mysql:host=localhost;dbname=eleonetech", "root", "", array(PDO::ATTR_PERSISTENT => true)));
    }

    function getRapports(){
        $connect = connexionDataBase();
        $requete = "SELECT code_equipement, date_visite FROM rapport WHERE etat = 'V' ORDER BY code_equipement ASC ";
        $statement = $connect->prepare($requete);
        $statement->execute(); 
        $nbr = $statement->rowCount();
        $resultat = "";

        if($nbr > 0){
            while($row = $statement->fetch(PDO::FETCH_ASSOC)){
                $rapport[] = $row; 
            }
    
            $resultat = json_encode(array("rapports" => $rapport));
        }

        else{
            $resultat = "Aucun rapport trouvé";
        }
        
        echo $resultat;
    }

    getRapports();
?>