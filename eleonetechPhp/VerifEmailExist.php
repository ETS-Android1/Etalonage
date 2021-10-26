<?php
    function connexionDataBase(){
        return (new PDO("mysql:host=localhost;dbname=eleonetech", "root", "", array(PDO::ATTR_PERSISTENT => true)));
    }
    
    function verifEmail($email){
        $connect = connexionDataBase();
        $requete = "SELECT * FROM utilisateur WHERE email = '$email'";
        $statement = $connect->prepare($requete);
        $statement->execute();
        $nbr = $statement->rowCount();
    
        if ($nbr >0){
            echo "Email trouvé";
        }
    
        else {
            echo "Email non trouvé";
        }
    }
    verifEmail($_GET['email']);    
?>