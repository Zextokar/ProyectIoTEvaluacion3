# LaEconomicaApp
### ProyectoIOT Evaluación2
***
|Correo|Contraseña|Telefono Contacto|
|--------------|--------------|--------------|
|rrodriguez.rrg76@gmail.com|proyectoiot1099||
***

### Codigo Python, para envio de datos desde el Arduino a la BD en Firebase

#### Bibliotecas Necesarias
```python
pip install git+https://github.com/ozgur/python-firebase
```
```python
pip install pyserial
```

#### Código

```python
import serial
import time
from firebase import firebase

# Configuración de Firebase
firebase = firebase.FirebaseApplication('https://evaluacion3-87b9d-default-rtdb.firebaseio.com/')

# Inicialización del puerto serie
try:
    arduino1 = serial.Serial("COM5", "9600")
    time.sleep(2)
except serial.SerialException as e:
    print(f"Error al abrir el puerto serie: {e}")
    exit()

while True:
    try:
        # Lectura de datos desde Arduino
        dato = arduino1.readline().decode("UTF-8")

        # Creación del diccionario de datos
        data = {'Temperatura': dato}

        # Impresión del dato
        print(dato)

        # Envío de datos a Firebase
        result = firebase.put('datos/Temperatura1', 'Temperatura', dato)
    except Exception as e:
        print(f"Error durante la lectura o envío a Firebase: {e}")
        # Puedes agregar aquí acciones específicas en caso de error, como volver a intentar la conexión.
```
### Código insertado en la memoria del Arduino
```cpp
#include <DHT.h>


#define DHTTYPE DHT11
#define DHTPIN 8

DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(9600);
  dht.begin();
}

void loop() {
  delay(2000);
  int temperature = dht.readTemperature();

  if(isnan(temperature)){
    Serial.println("ERROR");
    return;
  }
  Serial.println(temperature);

}

```