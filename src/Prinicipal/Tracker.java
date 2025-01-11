package Prinicipal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import simple.http.connect.ConnectionFactory;
import simple.http.load.MapperEngine;
import simple.http.serve.Context;
import simple.http.serve.FileContext;

/**
 * Clar Tracker.java encargada de mostrar las conexiones con los peers
 */
public class Tracker {

    public static void main(String[] args) {
        //Al ejecutar el programa el usuario debe ingresar el nombre del archivo de configuracion del tracker .xml
        if (args.length > 0) {
            try {
                //Asiganamos el nombre del archivo a una Consante, el cual escribira el archivo Mappers.xml
                trackerBT.Constants.loadConfig(args[0]);
            } catch (Exception e) {
                System.err.println(
                        "Archivo de configuracion no encontrado");
                System.exit(0);
            }
        } else {
            System.err.println(
                    "No se indicio la direccion del archivo de configuracion");
            System.exit(0);
        }
        //Limpiamos el archivo peer.xml
        File fichero = new File("./tracker/archivos/peers.xml");
        if (fichero.delete()) {
            System.out.print("");
        } else {
            System.out.print("");
        }
        //Empleamos un hilo actualizable, en el cual mostraremos los peers
        Servicio_Tracker objServicio_Tracker = new Servicio_Tracker();
        new File((String) trackerBT.Constants.get("context")).mkdirs();
        try {
            //Objeto para crear y escribir el archivo Mappers
            FileWriter fw = new FileWriter((String) trackerBT.Constants.get("context")
                    + "Mapper.xml");
            //Escribimos el archivo Mappers
            fw.write("<?xml version=\"1.0\"?>\r\n<mapper>\r\n<lookup>\r\n"
                    + "<service name=\"file\" type=\"trackerBT.FileService\"/>\r\n"
                    + "<service name=\"tracker\" type=\"trackerBT.TrackerService\"/>\r\n"
                    + "<service name=\"upload\" type=\"trackerBT.UploadService\"/>\r\n"
                    + "</lookup>\r\n<resolve>\r\n"
                    + "<match path=\"/*\" name=\"file\"/>\r\n"
                    + "<match path=\"/announce*\" name=\"tracker\"/>\r\n"
                    + "<match path=\"/upload*\" name=\"upload\"/>\r\n"
                    + "</resolve>\r\n</mapper>");
            fw.flush();
            fw.close();
        } catch (IOException ioe) {
            System.err.println("Error al crear archivo Mapper.xml'");
            System.exit(0);
        }
        Context context = new FileContext(new File((String) trackerBT.Constants.get("context")));
        try {
            MapperEngine engine = new MapperEngine(context);
            ConnectionFactory.getConnection(engine).connect(new ServerSocket(Integer.parseInt((String) trackerBT.Constants.get("listeningPort"))));
            objServicio_Tracker.start();
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            //Cadena empleada para escribir lo que escucha el tracker
            String s = null;
            try {
                s = br.readLine();
            } catch (IOException ioe) {
            }
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
