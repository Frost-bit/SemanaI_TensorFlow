<?php
//echo getcwd() . "\n";
session_start();
$target_dir = getcwd() . "/uploads/";
//$target_dir = $target_dir . basename( $_FILES["file"]["tmp_name"]) . ".jpg";
$target_dir = $target_dir . "img1" . ".jpg";
//echo $target_dir . "\n";
$uploadOk=1;
$i = 0;

$contenido = file_get_contents(getcwd().'/myText.txt');
$i = (int)$contenido;

if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_dir)) {
    $variable = "The file ". "img1". " has been uploaded.\n";
    //echo $variable;

    $i = $i + 1;
    $i = (string)$i;
    $fp = fopen(getcwd() ."/myText.txt","w+");
    fwrite($fp, $i);
    fclose($fp);

} else {
    $variable = "Sorry, there was an error uploading your file.\n";
    //echo $variable;
    echo 5;
}
echo $i;

?>