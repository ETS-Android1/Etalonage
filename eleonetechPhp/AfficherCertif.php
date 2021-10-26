<?php
    function connexionDataBase(){
        return (new PDO("mysql:host=localhost;dbname=eleonetech", "root", "", array(PDO::ATTR_PERSISTENT => true)));
    }

    function getCertif($code){
        $connect = connexionDataBase();
        $requete = "SELECT * FROM certif WHERE code_equipement = '$code'";
        $statement = $connect->prepare($requete);
        $statement->execute();
        $nbr = $statement->rowCount();
        $result = $statement->fetchAll();
        $certif = array();
        $resultat = "";
        
        if($nbr > 0){
            foreach($result as $row){
                $certif['certificat'] = $row['certificat'];
            }
            header('Content-Type: application/json');
            $resultat = json_encode($certif);
        }
        
        else{
            $resultat = "La certificat est indisponible";
        }

        echo $resultat;
    }

    getCertif($_GET['code']);
?>