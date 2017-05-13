<?php
require_once ("./DBCONNECT.php");
Class Register{

    private $firstName;
    private $lastName;
    private $email;
    private $pass;
    private $con;
    private $error;

    function __construct()
    {
        $this->con = DBCONNECT::CONNECT();
    }
    function __destruct()
    {
       $this->con = null;
    }

    public function registerConsumer(){

        if($this->checkConsumerPost()){

            $stmt = $this->con->prepare("INSERT INTO user(FName, LName, email, password) VALUES(?,?,?,?)");
            $stmt->execute(array($this->firstName,$this->lastName,$this->email,$this->pass));

            if($stmt->errorInfo()[1] == null){
                $this->error = array("success" => true, "error" => null);
                return true;
            }else{
                $this->error = array("success" => false, "error" => $stmt->errorInfo());
                return false;
            }
        }else{
            return $this->error = array("succes" => false, "error" => "Invalid Post");
        }

    }
    
    public function getError(){
        return $this->error;
    }
    private function checkConsumerPost(){
        if(isset($_POST["email"]) & isset($_POST["pass"]) && isset($_POST["firstName"]) && isset($_POST["lastName"])){

            $this->firstName = $_POST["firstName"];
            $this->lastName = $_POST["lastName"];
            $this->email = $_POST["email"];
            $this->pass = md5($_POST["pass"]);

            return true;
        }else{
            return false;
        }
    }
}
?>