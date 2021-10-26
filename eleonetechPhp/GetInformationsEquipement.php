<?php
    function connexionDataBase(){
        return (new PDO("mysql:host=localhost;dbname=eleonetech", "root", "", array(PDO::ATTR_PERSISTENT => true)));
    }

    function getEquipement($code){
        $connect = connexionDataBase();
        $requete = "SELECT * FROM equipement WHERE Code_equipement = '$code'";
        $statement = $connect->prepare($requete);
        $statement->execute();
        $nbr = $statement->rowCount();
        $result = $statement->fetchAll();
        $equipement = array();
        $resultat = "";

        if($nbr > 0){
            foreach($result as $row){
                $equipement['description'] = $row['Description_equipement'];
                $equipement['date_fin'] = $row['Date_fin_validite'];
                $equipement['emplacement'] = $row['Emplacement'];
                $equipement['etat'] = $row['Etat'];
                $equipement['numero_serie'] = $row['Num_serie'];
            }
    
            header('Content-Type: application/json');
            $resultat =  json_encode($equipement);
        }

        else{
            $resultat = "Aucun équipement trouvé";
        }

        echo $resultat;
    }

    getEquipement($_GET['code']);
?>