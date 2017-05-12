<?php
require_once ("./DBCONNECT.php");
class Login{

    private $con;
    private $email;
    private $password;
    private $result;


    function __construct(){
        //Connect to the database
        $this->con = DBCONNECT::CONNECT();
    }

    function __destruct(){

        $this->con = null;
    }

    public function getResult()
    {
        return $this->result;
    }

    //
    function getConsumer(){

        if($this->consumerPostSet()){

            /**PREVIOUS
            $statement = $this->con->prepare("SELECT * FROM user WHERE email = 'this->email' AND password = md5('$this->password')");
             */

            //Updated: use ? marks instead for preapre statement
            $statement = $this->con->prepare("SELECT * FROM user WHERE email = ? AND password = ?");

            //Then bind parameters
            $statement->execute(array($this->email,$this->password));

            //Then fetch rows
            $row = $statement->fetchAll(PDO::FETCH_ASSOC);

            if(count($row) == 1){
                //They match, so here we would pull all the profile information for the user
                return $this->result = array("success" => true, "error_message" => "", "user" => $row);
            }else{
                //They don't match just return null
                return $this->result = array("success" => false, "error_message" => "No Match", "user" => $row);
            }

        }else{
            $this->result = array("sucess" => false, "errorMessage" => "Invalid Post Variables, Should be: email and pass");
            return false;
        }
    }
    
    //Check if the post variables for a consumer login were set
    private function consumerPostSet(){
        if(isset($_POST["email"]) && isset($_POST["pass"])){

          
            $this->email = $_POST["email"];
            $this->password = $_POST["pass"];
            $this->password = md5($this->password);

            return true;
        }else{
            return false;
        }
    }
}
?>