<?php

/**
 * Created by PhpStorm.
 * User: Kurt
 * Date: 5/5/2017
 * Time: 9:04 PM
 */
class DBCONNECT{

    private static $DB_SERVER = "topnhotch.com";
    private static $DB_DATABASE = "item_sniper";
    private static $DB_USER = "sniperteam";
    private static $DB_PASSWORD = "password12345";

    public static function CONNECT(){
        $con = new PDO(
            "mysql:host=".DBCONNECT::$DB_SERVER.";". "dbname=".DBCONNECT::$DB_DATABASE."",
            DBCONNECT::$DB_USER,
            DBCONNECT::$DB_PASSWORD);
        return $con;
    }

}