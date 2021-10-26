<?php
    function connexionDataBase(){
        return (new PDO("mysql:host=localhost;dbname=eleonetech", "root", "", array(PDO::ATTR_PERSISTENT => true)));
    }

    function getRapports(){
        $connect = connexionDataBase();
        $requete = "SELECT id, code_equipement, certificat, date_certificat FROM certif ORDER BY id ASC ";
        $statement = $connect->prepare($requete);
        $statement->execute(); 
        $nbr = $statement->rowCount();
        $resultat = "";

        if($nbr > 0){
            while($row = $statement->fetch(PDO::FETCH_ASSOC)){
                $certifs[] = $row; 
            }
    
            $resultat = json_encode(array("certifs" => $certifs));
        }

        else{
            $resultat = "Aucun certificat trouvé";
        }
        
        echo $resultat;
    }

    getRapports();
?>