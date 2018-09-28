<?php
//echo getcwd() . "\n";
$target_dir = getcwd() . "/uploads/";
//$target_dir = $target_dir . basename( $_FILES["file"]["tmp_name"]) . ".jpg";
$target_dir = $target_dir . "img1" . ".jpg";
//echo $target_dir . "\n";
$uploadOk=1;

if (move_uploaded_file($_FILES["file"]["tmp_name"], $target_dir)) {
    $variable = "The file ". "img1". " has been uploaded.\n";
    echo $variable;

} else {
    $variable = "Sorry, there was an error uploading your file.\n";
    echo $variable;
}
    
$contenido = file_get_contents(getcwd().'/myText.txt');
$i = (int)$contenido;
echo $i;

?>