<?php
    function connexionDataBase(){
        return (new PDO("mysql:host=localhost;dbname=eleonetech", "root", "", array(PDO::ATTR_PERSISTENT => true)));
    }

    function updateCompte($email, $nom, $prenom, $poste, $numero, $adresse, $naissance){
        $connect = connexionDataBase();

        $requete = "UPDATE utilisateur SET nom = '$nom', prenom = '$prenom', poste = '$poste', mobile = '$numero', adresse = '$adresse', naissance = '$naissance' WHERE email = '$email'";

        if($connect->exec($requete) == 1){
            echo "Profil modifié";
        }

        else{
            echo "Profil non modifié";
        }
    }

    updateCompte($_POST['email'], $_POST['nom'], $_POST['prenom'], $_POST['poste'], $_POST['numero'], $_POST['adresse'], $_POST['naissance']);
?>