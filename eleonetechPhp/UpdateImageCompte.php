<?php
function connexionDataBase(){
    return (new PDO("mysql:host=localhost;dbname=eleonetech", "root", "", array(PDO::ATTR_PERSISTENT => true)));
}

function updateImage($email, $photo){
    $target_dir = "img/";
    if(!file_exists($target_dir)){
        mkdir($target_dir,0777,true);
    }
    
    $target_dir = $target_dir ."/". rand() ."_". time() . ".jpeg";
    
    if(file_put_contents($target_dir,base64_decode($photo))){
        $connect = connexionDataBase();
        $requete = "UPDATE utilisateur SET image = '$target_dir' WHERE email = '$email'";

        if($connect->exec($requete) == 1){
            echo "Image modifié";
        }

        else{
            echo "Image non modifié";
        }
    }
}

updateImage($_POST['email'], $_POST['image']);
?>