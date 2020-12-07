<?php
/*
 * 
 * This is the test file that goes along with our basic android remote request tutorial.
 * It returns a JSON object representing the response, with the results in an array.
 * 
 * 
 * 
 */

$dataset = ["burger", "pizza", "taco", "burrito", "sub sandwich", "salad", "french fries"];

$response['metadata'] = "gooddata";

$response['results'] = $dataset;

header('Content-type: application/json');
echo json_encode($response);

?>