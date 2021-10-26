<?php
    function connexionDataBase(){
        return (new PDO("mysql:host=localhost;dbname=eleonetech", "root", "", array(PDO::ATTR_PERSISTENT => true)));
    }
    
    function creerCompte($email, $password, $nom, $prenom, $poste, $numero, $adresse, $naissance){
        $connect = connexionDataBase();
        $passwordHashed = md5($password);
        
        $requete = "INSERT INTO utilisateur(email, password, nom, prenom, poste, mobile, adresse, naissance)
                    VALUES ('$email', '$passwordHashed', '$nom', '$prenom', '$poste', '$numero', '$adresse', '$naissance')";
       
       if($connect->exec($requete) == 1){
            echo 'Compte crée';
        }

        else{
            echo 'Compte non crée';
        }
    }
    creerCompte($_POST['email'], $_POST['password'], $_POST['nom'], $_POST['prenom'], $_POST['poste'], $_POST['numero'], $_POST['adresse'], $_POST['naissance']);
?>