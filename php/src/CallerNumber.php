<?php

$db_name = "database3";
$user = "root";
$pass = "";
$server_name = "localhost";

//$file = "err.txt";
//$curr = file_get_contents($file);
$con = mysqli_connect($server_name,$user,$pass,$db_name);

if(!$con)
{
  //echo "Connection Error...".mysqli_connect_error();
}
else {
  //echo "Database is Connected"."<br>";
 // #$sql = "INSERT INTO TrueCaller."
}

$caller_number= $_POST["CallerNumber"];
$ss = "";
for($i=0;$i<strlen($caller_number);$i++)
{
  if($caller_number[$i]!='+' && $caller_number[$i]!=' ')
  {
    $ss.=$caller_number[$i];
  }
}
//$ss = '+'.$ss;
//$curr .= $ss;
//echo $ss."<br>";
//$caller_number = "+".$caller_number;
//$sql1 = "SELECT name.NAME, contacts.NAMEID, number.ID FROM name, contacts, number WHERE number.NUMBER = $ss AND contacts.NUMBERID = number.ID AND contacts.NAMEID = name.ID";
$sql1 = "SELECT name.NAME as nm, contacts.NAMEID, number.ID FROM name JOIN contacts ON contacts.NAMEID = name.ID JOIN number ON number.ID=contacts.NUMBERID WHERE number.NUMBER = '$ss'";
$result = mysqli_query($con, $sql1);
//echo $sql1."\n";
//$curr .= $sql1;
if(!$result)
{
  //echo mysqli_error($con);
}
$response = array();

if($result->num_rows > 0)
{
  while($row = $result->fetch_assoc())
  {
    $name = $row['nm'];
    //$curr .= $name;
    echo $name."\n";
    //array_push($response,array("Name"=>$name));
  }
}
else {
  echo "0 result\n";
}
//file_put_contents($file,$curr);
?>
