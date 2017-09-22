<?php
//setting header to json
 $val1 = htmlentities($_GET['datetimepickerfrom']);
 echo $val1;

//get connection
$mysqli = new mysqli("localhost", "root", "", "rpi");

if(!$mysqli){
	die("Connection failed: " . $mysqli->error);
}

//query to get data from the table
$query = sprintf("SELECT temp, date_time FROM new_table");

//execute query
$result = $mysqli->query($query);

//loop through the returned data
$data = array();
foreach ($result as $row) {
	$data[] = $row;
}

//free memory associated with result
$result->close();

//close connection
$mysqli->close();

//now print the data
print json_encode($data);