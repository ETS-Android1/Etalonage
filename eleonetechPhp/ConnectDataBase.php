<?php
    function connexionDataBase(){
        return (new PDO("mysql:host=localhost;dbname=eleonetech", "root", "", array(PDO::ATTR_PERSISTENT => true)));
    }

	function verifConnexion(){
		$connect = connexionDataBase();

		if($connect){
			echo "connected";
		}

		else{
			echo $connect;
		}
	}
	verifConnexion();
?>