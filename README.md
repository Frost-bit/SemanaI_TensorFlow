# SemanaI_TensorFlow
Repositorio de códigos utilizados durante la semana I de machine learning: reconocimiento de imágenes 2018.

El programa ProcImg utiliza las librerias de requests para comunicarse con un servidor. Se envian requests al servidor en espera de que este notifique que se recibió una imagen. Al ser recibida, la imagen se descarga a un directorio local de la computadora. A continuacion, se hace una llamada a detectModel, el cual se encarga de utilizar el modelo de reconocimiento para detectar los objetos presentes.
Despues de hacer el procesamiento, se genera una nueva imagen que tambien es guardada en el directorio y se envia nuevamente al servidor por medio de request.post, en el que se despliega en una pagina web publica.

La aplicacion movil se utiliza para alimentar al sistema con imagenes. Esto lo logra a traves de la interaccion con el usuario con el cual, a traves de la interfaz, manda una imagen embebida en un POST request hacia la aplicacion web.

La aplicacion web funciona como centro de informacion para el manejo de las imagenes tanto las procesadas como las imagenes tomadas desde el celular. Se compone principalmente de modulos .php con los cuales se otorga informacion a los dispositivos sobre cuando existe una nueva imagen disponible para procesar ademas de regular la comunicacion entre ellos.

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
6. Se desarrolló la aplicación que utiliza el modelo


## Uso del Modelo
El programa ProcImg utiliza las librerias de requests para comunicarse con un servidor. Se envian requests al servidor en espera de que este notifique que se recibió una imagen. Al ser recibida, la imagen se descarga a un directorio local de la computadora. A continuacion, se hace una llamada a detectModel, el cual se encarga de utilizar el modelo de reconocimiento para detectar los objetos presentes.
Despues de hacer el procesamiento, se genera una nueva imagen que tambien es guardada en el directorio y se envia nuevamente al servidor por medio de request.post, en el que se despliega en una pagina web publica.
