<?php
/**
 * Created by PhpStorm.
 * User: Kurt
 * Date: 5/5/2017
 * Time: 9:26 PMM
 *
 */
error_reporting(E_ALL);
ini_set('display_errors', 1);
require_once("./DBCONNECT.php");
require_once("./RegisterClass.php");

$Register = new Register();
$result = $Register->registerConsumer();

if($result){

    echo json_encode(array("success" => true));

}else{
    echo json_encode($Register->getError());
}

?>