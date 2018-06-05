
#include <LiquidCrystal.h>  // incluye la biblioteca liquidCrystal

float tempC;            // variable para la temperatura

// VARIALBES PARA MENSAJES
boolean mensajeDisponible = false;  // Variable que cambia cuando hay un mensaje para mostrar

// VARIABLES PARA PANTALLA
LiquidCrystal lcd(11, 10, 5, 4, 3, 2);  // instancia de pantalla con sus pines
int imprimir = 0;                       // CUENTA LOS CARACTERES A IMPRIMIR
String Mensaje = "";                    // VARIABLE QUE ALMACENA MENSAJE A MOSTRAR

void setup() {
  pinMode(10,OUTPUT);       // ESTABLECER EL PIN 10 COMO SALIDA
  digitalWrite(10,HIGH);    // ENCENDER EL PIN 10
  lcd.begin(16, 2);         // EMPEZAR EN 16,2

  Serial.begin(9600);       // INICIAR COMUNICACIÓN SERIAL
}

void loop() {
  
  evaluarMensajes();        // LLAMAR MÉTODO
}


/*MÉTODO QUE IMPRIME MENSAJES ENVIADOS DESDE JAVA EN LA PANTALLA DISPLAY LCD*/
void evaluarMensajes() {
  int caracteres=0;                                   // ALMACENA EL TAMAÑO DEL MENSAJE

  while (Serial.available()>0) {                      // SI HAY MENSAJES DISPONIBLES
    lcd.clear();                                      // LIMPIAR PANTALLA
    Mensaje=Mensaje+Decimal_to_ASCII(Serial.read());  // ALMACENAR MENSAJE EN VARIABLE
  }
  
  caracteres=Mensaje.length();                        // ALMACENAR TAMAÑO DEL MENSAJE EN VARIABLE
  if (caracteres>16){                                 // SI EL MENSAJE SUPERA LOS 16 CARACTERES
    mensajeDisponible = true;                         // CAMBIAR VARIABLE A TRUE
    
    if (Mensaje!="") {                                // SI EL MENSAJE NO ESTÁ VACÍO
      lcd.clear();                                    // LIMPIAR PANTALLA
      lcd.print(Mensaje.substring(0,16));             // IMPRIMIR MENSAJE EN PANTALLA LCD
      lcd.setCursor(0,1);                             // CAMBIAR POSICIÓN DEL CURSOR
      lcd.print(Mensaje.substring(16,caracteres));    // IMPRIMIR MENSAJE
    }
  } else if(caracteres <= 16 && Mensaje != "") {      // SI EL MENSAJE NO SUPERA LOS 16 CARACTERES Y NO ESTÁ VACÍO
    mensajeDisponible = true;                         // CAMBIAR VARIABLE A TRUE
    
    lcd.clear();                                      // LIMPIAR PANTALLA
    lcd.print(Mensaje);                               // IMPRIMIR MENSAJE EN PANTALLA LCD
  } else {                                            // SI NINGUNA DE ESTAS CONDICIONES SE CUMPLE
    mensajeDisponible = false;                        // NO HAY MENSAJE DISPONIBLE
  }
  
  delay(1000);                                        // ESPERAR UN SEGUNDO
  Mensaje="";                                         // ESTABLECER VARIABLE MENSAJE A VACÍA
}

/*MÉTODO QUE RETORNA UN CARACTER EN FUNCIÓN DEL NÚMERO QUE RECIBE COMO PARÁMETRO*/
char Decimal_to_ASCII(int entrada){
  char salida=' '; // VARIABLE QUE ALMACENA EL CARACTER Y LO RETORNA

  // EVALUAR EL NÚMERO RECIBIDO
  switch(entrada){
    case 32: 
    salida=' '; 
    break; 
    case 33: 
    salida='!'; 
    break; 
    case 34: 
    salida='"'; 
    break; 
    case 35: 
    salida='#'; 
    break; 
    case 36: 
    salida='$'; 
    break; 
    case 37: 
    salida='%'; 
    break; 
    case 38: 
    salida='&'; 
    break; 
    case 39: 
    salida=' '; 
    break; 
    case 40: 
    salida='('; 
    break; 
    case 41: 
    salida=')'; 
    break; 
    case 42: 
    salida='*'; 
    break; 
    case 43: 
    salida='+'; 
    break; 
    case 44: 
    salida=','; 
    break; 
    case 45: 
    salida='-'; 
    break; 
    case 46: 
    salida='.'; 
    break; 
    case 47: 
    salida='/'; 
    break; 
    case 48: 
    salida='0'; 
    break; 
    case 49: 
    salida='1'; 
    break; 
    case 50: 
    salida='2'; 
    break; 
    case 51: 
    salida='3'; 
    break; 
    case 52: 
    salida='4'; 
    break; 
    case 53: 
    salida='5'; 
    break; 
    case 54: 
    salida='6'; 
    break; 
    case 55: 
    salida='7'; 
    break; 
    case 56: 
    salida='8'; 
    break; 
    case 57: 
    salida='9'; 
    break; 
    case 58: 
    salida=':'; 
    break; 
    case 59: 
    salida=';'; 
    break; 
    case 60: 
    salida='<'; 
    break; 
    case 61: 
    salida='='; 
    break; 
    case 62: 
    salida='>'; 
    break; 
    case 63: 
    salida='?'; 
    break; 
    case 64: 
    salida='@'; 
    break; 
    case 65: 
    salida='A'; 
    break; 
    case 66: 
    salida='B'; 
    break; 
    case 67: 
    salida='C'; 
    break; 
    case 68: 
    salida='D'; 
    break; 
    case 69: 
    salida='E'; 
    break; 
    case 70: 
    salida='F'; 
    break; 
    case 71: 
    salida='G'; 
    break; 
    case 72: 
    salida='H'; 
    break; 
    case 73: 
    salida='I'; 
    break; 
    case 74: 
    salida='J'; 
    break; 
    case 75: 
    salida='K'; 
    break; 
    case 76: 
    salida='L'; 
    break; 
    case 77: 
    salida='M'; 
    break; 
    case 78: 
    salida='N'; 
    break; 
    case 79: 
    salida='O'; 
    break; 
    case 80: 
    salida='P'; 
    break; 
    case 81: 
    salida='Q'; 
    break; 
    case 82: 
    salida='R'; 
    break; 
    case 83: 
    salida='S'; 
    break; 
    case 84: 
    salida='T'; 
    break; 
    case 85: 
    salida='U'; 
    break; 
    case 86: 
    salida='V'; 
    break; 
    case 87: 
    salida='W'; 
    break; 
    case 88: 
    salida='X'; 
    break; 
    case 89: 
    salida='Y'; 
    break; 
    case 90: 
    salida='Z'; 
    break; 
    case 91: 
    salida='['; 
    break; 
    case 92: 
    salida=' '; 
    break; 
    case 93: 
    salida=']'; 
    break; 
    case 94: 
    salida='^'; 
    break; 
    case 95: 
    salida='_'; 
    break; 
    case 96: 
    salida='`'; 
    break; 
    case 97: 
    salida='a'; 
    break; 
    case 98: 
    salida='b'; 
    break; 
    case 99: 
    salida='c'; 
    break; 
    case 100: 
    salida='d'; 
    break; 
    case 101: 
    salida='e'; 
    break; 
    case 102: 
    salida='f'; 
    break; 
    case 103: 
    salida='g'; 
    break; 
    case 104: 
    salida='h'; 
    break; 
    case 105: 
    salida='i'; 
    break; 
    case 106: 
    salida='j'; 
    break; 
    case 107: 
    salida='k'; 
    break; 
    case 108: 
    salida='l'; 
    break; 
    case 109: 
    salida='m'; 
    break; 
    case 110: 
    salida='n'; 
    break; 
    case 111: 
    salida='o'; 
    break; 
    case 112: 
    salida='p'; 
    break; 
    case 113: 
    salida='q'; 
    break; 
    case 114: 
    salida='r'; 
    break; 
    case 115: 
    salida='s'; 
    break; 
    case 116: 
    salida='t'; 
    break; 
    case 117: 
    salida='u'; 
    break; 
    case 118: 
    salida='v'; 
    break; 
    case 119: 
    salida='w'; 
    break; 
    case 120: 
    salida='x'; 
    break; 
    case 121: 
    salida='y'; 
    break; 
    case 122: 
    salida='z'; 
    break; 
    case 123: 
    salida='{'; 
    break; 
    case 124: 
    salida='|'; 
    break; 
    case 125: 
    salida='}'; 
    break; 
    case 126: 
    salida='~'; 
    break;
  }
  
  return salida; // RETORNAR VARAIBLE
}

