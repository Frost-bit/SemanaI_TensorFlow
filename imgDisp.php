<!DOCTYPE html>
<html>
<head>
    <title>Footer with Logo</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.6.3/css/font-awesome.min.css">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="Footer-with-logo.css">
    <link href="https://fonts.googleapis.com/css?family=Poppins" rel="stylesheet">
</head>

<body>
<div class="content">
    <?php
        $target_dir = "/uploads/";
        $target_dir = $target_dir . "img1.jpg";

        echo "<img height=\"50%\" width=\"50%\" transform:rotate(-90deg) src=\"". $target_dir ."\">"
    ?>
</div>
    <footer id="myFooter">
        <div class="second-bar">
           <div class="container">
                <h2 class="logo"><a> Detección de Cervezas </a></h2>
                <div class="social-icons">
                    <a href="https://github.com/Frost-bit/SemanaI_TensorFlow" class="github"><i class="fa fa-github"></i></a>
                    <!-- android button -->
                    <img width="50px" src="ic_launcher.png">
                    <img width="160px" src="http://altitude-images.s3.amazonaws.com/Google-play-logo.png">
                </div>
            </div>
        </div>
    </footer>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</body>
</html>