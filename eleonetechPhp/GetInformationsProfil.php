<?php
    function connexionDataBase(){
        return (new PDO("mysql:host=localhost;dbname=eleonetech", "root", "", array(PDO::ATTR_PERSISTENT => true)));
    }

    function getInformations($email){
        $connect = connexionDataBase();
        $requete = "SELECT * FROM utilisateur WHERE email = '$email'";
        $statement = $connect->prepare($requete);
        $statement->execute();
        $nbr = $statement->rowCount();
        $result = $statement->fetchAll();
        $utilisateur = array();
        $resultat = "";
        
        if($nbr > 0){
            foreach($result as $row){
                $utilisateur['nom'] = $row['nom'];
                $utilisateur['prenom'] = $row['prenom'];
                $utilisateur['poste'] = $row['poste'];
                $utilisateur['mobile'] = $row['mobile'];
                $utilisateur['adresse'] = $row['adresse'];
                $utilisateur['naissance'] = $row['naissance'];
                $utilisateur['image'] = $row['image'];
            }
            header('Content-Type: application/json');
            $resultat = json_encode($utilisateur);
        }
        
        else{
            $resultat = "Les informations sont indisponibles";
        }

        echo $resultat;
    }

    getInformations($_GET['email']);
?>