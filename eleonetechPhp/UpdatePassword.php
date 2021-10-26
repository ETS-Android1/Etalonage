<?php
    function connexionDataBase(){
        return (new PDO("mysql:host=localhost;dbname=eleonetech", "root", "", array(PDO::ATTR_PERSISTENT => true)));
    }

    function updatePassword($email, $password){
        $connect = connexionDataBase();
        $passwordHashed = md5($password);

        $requete = "UPDATE utilisateur SET password = '$passwordHashed' WHERE email = '$email'";

        if($connect->exec($requete) == 1){
            echo "Password modifié";
        }

        else{
            echo "Password non modifié";
        }
    }

    updatePassword($_POST['email'], $_POST['password']);
?>