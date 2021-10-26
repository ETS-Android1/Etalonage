<?php
    function connexionDataBase(){
        return (new PDO("mysql:host=localhost;dbname=eleonetech", "root", "", array(PDO::ATTR_PERSISTENT => true)));
    }

    function testAncienPassword($email, $ancienPassword){
        $connect = connexionDataBase();
        $hashedPassword = md5($ancienPassword);
        $requete = "SELECT * FROM utilisateur WHERE email = '$email' AND password = '$hashedPassword'";
        $statement = $connect->prepare($requete);
        $statement->execute();
        $nbr = $statement->rowCount();

        if ($nbr > 0){
            echo "Mot de passe vérifié";
        }
    
        else {
            echo "Mot de passe non vérifié";
        }
    }

    testAncienPassword($_GET['email'], $_GET['ancienPassword']);
?>