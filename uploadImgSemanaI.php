<?php
    $name = $_POST['name']; //image name
    $image = $_POST['image']; //image in string format
    $i = 0;
    //decode the image
    $decodedImage = base64_decode($image);
 
    //upload the image
    file_put_contents("raw/".$name.".jpg", $decodedImage);
    
    $contenido = file_get_contents(getcwd().'/myText.txt');
    $i = (int)$contenido;
    
    $i = $i + 1;
    $i = (string)$i;
    $fp = fopen(getcwd() ."/myText.txt","w+");
    fwrite($fp, $i);
    fclose($fp);
?>