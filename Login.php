<?php
require_once ("./LoginClass.php");
$Login = new Login();

if($Login->getConsumer()){

    echo json_encode($Login->getConsumer());

}else{

    echo json_encode($Login->getResult());
}
?>