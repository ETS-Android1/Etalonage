<?php
    function connexionDataBase(){
        return (new PDO("mysql:host=localhost;dbname=eleonetech", "root", "", array(PDO::ATTR_PERSISTENT => true)));
    }

    function getRapports($codeEquipement){
        $connect = connexionDataBase();
        $requete = "SELECT * FROM rapport WHERE code_equipement = '$codeEquipement'";
        $statement = $connect->prepare($requete);
        $statement->execute(); 
        $nbr = $statement->rowCount();
        $result = $statement->fetchAll();
        $rapport = array();
        $resultat = "";

        if($nbr > 0){
            foreach($result as $row){
                $rapport['description'] = $row['description']; 
                $rapport['emplcacement'] = $row['emplacement'];
                $rapport['num_serie'] = $row['num_serie'];
                $rapport['technicien'] = $row['technicien'];
                $rapport['societe'] = $row['societe'];
                $rapport['remarque'] = $row['remarque'];
                $rapport['etat'] = $row['etat'];
                $rapport['date_visite'] = $row['date_visite'];
                $rapport['categorie'] = $row['categorie'];
                $rapport['exigence'] = $row['exigence'];
                $rapport['lieu'] = $row['lieu'];
            }

            header('Content-Type: application/json');
            $resultat = json_encode($rapport);
        }

        else{
            $resultat = "Aucun rapport trouvé";
        }
        
        echo $resultat;
    }

    getRapports($_GET['code_equipement']);
?>