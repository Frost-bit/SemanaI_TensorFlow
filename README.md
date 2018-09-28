# SemanaI_TensorFlow
Repositorio de códigos utilizados durante la semana I de machine learning: reconocimiento de imágenes 2018.
Este archivo explica el proceso llevado a cabo para entrenar el modelo de reconocimiento de botellas de cerveza y la implementación del mismo.
Ademas se detalla la función que cumple cada script que se utilzó para generar el modelo y la implementación.

# Proceso de creación de modelo e implementación
## Pre-procesamiento del modelo
1. Identificar que modelo es conveniente para el problema (Model Zoo)
2. Recolectar imágenes significativas para el problema a solucionar
3. Etiquetar los datos para generar el XML
4. Conversión de datos de XML a CSV
5. Conversión de datos de CSV a TFRecord

## Entrenamiento del modelo
1. Se subió el modelo a utilizar CMLE para entrenamiento
2- Se creó el archivo .config y .pbtxt
  .config : establece parametros de entrenamiento
  .pbtxt : establece los distintos elementos que se encontraran en las imágenes
3. Se ejecutó el entrenar del modelo en CMLE
4. Se exportó el moderlo
5. Se implementó el modelo
6. Se desarrolló la aplicación movil y web que utilizan el modelo

## Data de entrenamiento
Para entrenar el modelo se parte de un set de imagenes que se tranforman a tfrecord para poder ser utilizadas en tensorflow para el entrenamiento del modelo. El proceso incluye los siguientes pasos:
1. Etiquetar las imagenes con LabelImg para generar las .xml.
2. Transformar los .xml en .csv.
3. Convertir las .jpg y .csv en tfrecord.

### LabelImg
Este programa se usa para poner labels a las imagenes, lo cual crea los archivos .xml.

### JPG
Imagenes que se usan para entrenar el modelo. Estas se tienen que transformar en TFRecord para poder procesarlas en CMLE.
Las imagenes estan almacenadas en esta liga de [Drive](https://drive.google.com/drive/folders/1hcYdRf5fwMtzNtgWppR6NcXYrHgjNn4q)

### XML
Archivos que contienen los labels de las imagenes. Las imagenes jpg se etiquetan con ayuda LabelImg. Estos archivos se encuentran junto con los xml

### CSV
Archivos que contienen la información de los xml. Estos se generan para poder utilizar generate_tfrecord.py.

### TFRecord
Son dos archivos que se utilizan para el entranimiento del modelo en tensorflow.
* train.record contiene las imagenes para el entrenamiento del modelo.
* [test.record](https://drive.google.com/file/d/1WzFf3rQVvJAN7w56v4-TLPXVrn6g8htL/view?usp=sharing) contiene las imagenes de validación del modelo.

## Conversión de data
Para poder entrenar el modelo es necesario transformar las imagenes .jpg en archivos TFRecord para poder entrenarlos con tensorflow. Para esto se utilizan los siguientes scripts.

### xml_to_csv
Script transforma los archivos de .xml a .csv como un paso intermedio de la conversión a TFRecord.

### generate_tfrecord.py
Script que genera los tfrecord a partir de los .csv y las imagenes .jpg.

## Ejecución del Modelo
Para ejecuctar el modelo se utilizan dos scripts que se encargan de procesar las imagenes cada vez que se sube un nuevo archivo al servidor. Los scripts que realizan estas tareas son los siguientes:

### procesaImg.py
El programa procesaImg utiliza las librerias de requests para comunicarse con un servidor. Se envian requests al servidor en espera de que este notifique que se recibió una imagen. Al ser recibida, la imagen se descarga a un directorio local de la computadora. A continuacion, se hace una llamada a detectModel, el cual se encarga de utilizar el modelo de reconocimiento para detectar los objetos presentes.
Despues de hacer el procesamiento, se genera una nueva imagen que tambien es guardada en el directorio y se envia nuevamente al servidor por medio de request.post, en el que se despliega en una pagina web publica.

### detectModel.py
Este código se encarga de cargar el modelo en memoria a partir del directorio trained_model. En seguida toma la imagen raw.jpg del directorio raiz y la procesa con el modelo para detectar las botellas en la imagen.

## Uso del modelo
Para utilizar el modelo se desarrollo un aplicación móvil que se utiliza para alimentar al sistema con imágenes y una aplicación web que despliega la imagen ya procesada. La imagen procesada muestra los objetos detectados.

### Aplicación movil
La aplicación móvil, a traves de la interaccion con el usuario, manda una imagen embebida en un POST request hacia la aplicacion web.

### Aplicación web
La aplicacion web funciona como centro de informacion para el manejo de las imagenes tanto las procesadas como las imagenes tomadas desde el celular. Se compone principalmente de modulos .php con los cuales se otorga informacion a los dispositivos sobre cuando existe una nueva imagen disponible para procesar ademas de regular la comunicacion entre ellos.

:shipit:
