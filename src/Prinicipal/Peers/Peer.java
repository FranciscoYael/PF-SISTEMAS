package Prinicipal.Peers;

import jBittorrentAPI.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 *
 * @author Francisco Yael, Raybel Antonio, Sui Som
 */
public class Peer {

    //Variable global, la cual nos indica el maximo de archivos en el pool
    static final int max_torrent = 8;
    Admi_Descargas[] admi_Descargas_arreglo = new Admi_Descargas[max_torrent];//Arreglo de hilos
    //

    public void Peer_Hilos(String[] args) throws IOException {
        //Varianles auxilares:
        int opcion = 0, indice = 0;
        boolean salir = false;
        //Objerto para leer datos ingresador por el teclado
        Scanner teclado = new Scanner(System.in);
        //Objeto teclado_ruta, el cual usamos para asignar un ruta especificada por el usuario
        BufferedReader teclado_ruta = new BufferedReader(new InputStreamReader(System.in));
        //Emplemoa un ciclo do-while, en el cual se repetira el siguiente bloque de instrucciones
        do {
            opcion = menu(teclado);
            switch (opcion) {
                case 1:
                    compartir_archivo_torrent(indice, admi_Descargas_arreglo, teclado_ruta);
                    break;
                case 2:
                    decargar_compartir_archivo_torrent(indice, admi_Descargas_arreglo, teclado_ruta);
                    break;
                case 3:
                    agregar_torrent_tracker(teclado_ruta);
                    break;
                case 4:
                    salir = true;
                    break;
                default:
                    System.out.println("Opcion invalida, ingrese otra opcion");
                    break;
            }
        } while (!salir);
    }

    //Funcion menu, devulve un valor tipo entero, entre 1 y 4
    public int menu(Scanner teclado) {
        //Variable que retorna el numero de instruccion del usuario
        int opc = 0;
        do {
            System.out.println("\tMenu: \n1. Compartir archivo torrent"
                    + "\n2. Descargar y compartir archivo torrent"
                    + "\n3. Agregar archivo torrent al tracker \n4. Salir"
                    + "\nIngrese su opcion: ");
            //Empleamos el objeto teclado, para asignar el numero de opcion del ususario
            opc = teclado.nextInt();
            //Validemos que el numero de opcion sea correcto
            if (opc < 1 && opc > 4) {
                System.out.println("Opcion invalida, ingrese otra opcion");
            }
        } while (opc < 1 && opc > 4);
        return opc;
    }

    //Funcion compartir torrent, la cual requiere de la ruta del archivo a compartir y los archivos que lo integran
    public void compartir_archivo_torrent(int indice, Admi_Descargas[] admi_Descargas_arreglo, BufferedReader teclado_ruta) {
        //Variable que almacena la dirrccion, donde se guardan los archivos del torrent
        String ruta_archivos_torrent = "";
        //Variable que guarda la dirrecion la reuta, donde se encuentra el archivo torrent
        String ruta_archivo_torrent = "";
        try {
            System.out.println("Ingrese la ruta de los archivos: ");
            ruta_archivos_torrent = teclado_ruta.readLine();
            System.out.println("Ingrese la ruta del archivo torrent: ");
            ruta_archivo_torrent = teclado_ruta.readLine();
        } catch (IOException ex) {
            System.err.println(ex);
        }
        if (indice <= 8) {
            //Asiganamos un objeto de la clase Admi_Descargas, pasamos como 
            //parametros del contructor de la clase, la ruta del los archivos y 
            //la ruta del archivo torrent
            admi_Descargas_arreglo[indice] = new Admi_Descargas(ruta_archivo_torrent, ruta_archivos_torrent);
            //Iniciamos la carga de los archivos, con el metodo de start
            admi_Descargas_arreglo[indice].start();
            admi_Descargas_arreglo[indice].setName("Archivo: " + indice);
            //Incrementamos el indice de descarga
            indice++;
        } else {
            //Si alcanzamos el numero maximo de descarga, mostraremos el mensaje:
            System.out.println("Se alcanzo el limite de descargas");
        }
    }

    //Funcion que descarga y comparte un archivo torrent
    public void decargar_compartir_archivo_torrent(int indice, Admi_Descargas[] admi_Descargas_arreglo, BufferedReader teclado_ruta) {
        //Variable que almacena la ruta, en donde se descargaran los archivos que integran al torrent
        String ruta_archivos_torrent = "";
        //Variable que almacena la ruta en la cual se guardara el archivo torrent
        String ruta_archivo_torrent = "";
        try {
            //Soliictamos las rutas al usuario
            System.out.println("Indique la ruta deonde se guardaran los archivos: ");
            ruta_archivos_torrent = teclado_ruta.readLine();
            System.out.println("Indique la ruta en donde se guardara el torrent: ");
            ruta_archivo_torrent = teclado_ruta.readLine();
        } catch (IOException ex) {
            System.err.println(ex);
        }
        if (indice < 4) {
            //Asiganamos un objeto de la clase Direcciones, pasamos como 
            //parametros del contructor de la clase, la ruta del los archivos y 
            //la ruta del archivo torrent
            admi_Descargas_arreglo[indice] = new Admi_Descargas(ruta_archivo_torrent, ruta_archivos_torrent);
            //Iniciamos la carga de los archivos, con el metodo de start
            admi_Descargas_arreglo[indice].start();
            admi_Descargas_arreglo[indice].setName("Archivo: " + indice);
            //Incrementamos el indice de descarga
            indice++;
        } else {
            //Si alcanzamos el numero maximo de descarga, mostraremos el mensaje:
            System.out.println("Se alcanzo el limite de descargas");
        }
    }

    //Funcion que asigna un nuevo torrent al Tracker
    public void agregar_torrent_tracker(BufferedReader teclado_ruta) {
        //Variable que almacena la dirrccion IP, de la cual se compartira el torrent
        String direccion_IP_archivos_torrent = "";
        //Variable que guarda la dirrecion la reuta, donde se encuentra el archivo torrent
        String ruta_archivo_torrent = "";
        //Variable que guarda el pueto de la direccion IP
        String puerto_IP = "";
        //Variable que guarda la direccion IP del tracker
        String direccion_IP_tracker = "";
        try {
            //Soliictamos las rutas al usuario
            System.out.println("Indique la direccion IP de los archivos: ");
            direccion_IP_archivos_torrent = teclado_ruta.readLine();
            System.out.println("Indique el puerto: ");
            puerto_IP = teclado_ruta.readLine();
            System.out.println("Indique la ruta en donde se encuentra el torrent: ");
            ruta_archivo_torrent = teclado_ruta.readLine();
            /*System.out.println("Indique la direccion IP del tracker: ");
            direccion_IP_tracker = teclado_ruta.readLine();*/
        } catch (IOException ex) {
            System.out.println(ex);
        }
        String direccion = ("http://" + direccion_IP_archivos_torrent + ":" + puerto_IP + "/upload");
        System.out.println(direccion);
        String[] argumentos = {ruta_archivo_torrent, direccion, "none", "none"};
        conectar_tracker(argumentos);
    }

    //Funcion para agregar un archivo torrent al tracker
    public static void conectar_tracker(String[] argumentos) {
        File archivo = new File(argumentos[0]);
        String conexion = "";
        for (int i = 4; i < argumentos.length; i++) {
            conexion += argumentos[i];
        }
        try {
            ConnectionManager.publish(argumentos[0], argumentos[1], argumentos[2],
                    argumentos[3], archivo.getName(), "", conexion, "7");
        } catch (Exception e) {
            System.out.println("Error al agregar al tracket...");
        }
    }

    //Funcion principal (main)
    public static void main(String[] args) throws IOException {
        new Peer().Peer_Hilos(args);
    }
}
