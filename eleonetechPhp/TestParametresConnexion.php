<?php
    function connexionDataBase(){
        return (new PDO("mysql:host=localhost;dbname=eleonetech", "root", "", array(PDO::ATTR_PERSISTENT => true)));
    }

    function verifCompte($email, $password){
        $connect = connexionDataBase();
        $hashedPassword = md5($password);
        $requete = "SELECT * FROM utilisateur WHERE email = '$email' AND password = '$hashedPassword'";
        $statement = $connect->prepare($requete);
        $statement->execute();
        $nbr = $statement->rowCount();
    
        if ($nbr > 0){
            echo "Compte trouvé";
        }
    
        else {
            echo "Compte non trouvé";
        }
    }
    verifCompte($_POST['email'], $_POST['password']);
?>