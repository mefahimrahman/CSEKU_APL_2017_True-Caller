<?php

$db_name = "database3";
$user = "root";
$pass = "";
$server_name = "localhost";

$con = mysqli_connect($server_name,$user,$pass,$db_name);

if(!$con)
{
  echo "Connection Error...".mysqli_connect_error();
}
else {
  echo "Database is Connected"."<br>";
 // #$sql = "INSERT INTO TrueCaller."
}
$name= $_POST["NAME"];
$number = $_POST["NUMBER"];
$ss = "";
for($i=0;$i<strlen($number);$i++)
{
  if($number[$i]!='+' && $number[$i]!=' ')
  {
    $ss.=$number[$i];
  }
}
echo $name." ".$number."<br>";
$sql = "SELECT ID FROM number WHERE NUMBER='$ss'";
$res = $con->query($sql) or die(mysqli_error());
$sql_query2 = "INSERT INTO number (`NUMBER`) VALUES ('$ss')";

if($res->num_rows > 0)
{
  $row = $res->fetch_assoc();
  $id = $row['ID'];
}
else {
  $res = $con->query($sql_query2) or die(mysqli_error());
  $res = $con->query($sql) or die(mysqli_error());
  $row = $res->fetch_assoc();
  $id = $row['ID'];
}
echo $id."<br>";

$sql_query = "INSERT INTO name (`NAME`) VALUES ('$name')";
$idname = array();
$res = $con->query($sql_query);
$sqld = "SELECT ID FROM name WHERE NAME='$name'";
$res = $con->query($sqld) or die(mysqli_error());
while($row = $res->fetch_assoc())
{
  $idname[] = $row['ID'];
  //echo $idname."<br>";
}
foreach ($idname as $key) {
  $fuck = "INSERT INTO contacts (`NAMEID`,`NUMBERID`) VALUES ($key,$id)";
  $con->query($fuck) or die("Error");
}

/// Getting Number From Caller

// $caller_number= $_POST["CallerNumber"];
// echo $caller_number."<br>"

?>
