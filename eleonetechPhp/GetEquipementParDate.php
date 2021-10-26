<?php
    function connexionDataBase(){
        return (new PDO("mysql:host=localhost;dbname=eleonetech", "root", "", array(PDO::ATTR_PERSISTENT => true)));
    }

    function getEquipement($date){
        $connect = connexionDataBase();
        $requete = "SELECT * FROM equipement WHERE Date_fin_validite LIKE '%-".$date."-%' AND Etat != 'v' ORDER BY Date_fin_validite ASC";
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

    getEquipement($_GET['date']);
?>