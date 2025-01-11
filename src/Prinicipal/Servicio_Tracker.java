package Prinicipal;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import trackerBT.Constants;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

/**
 * Clase Servicio_Tracker.java hereda de la clase Trread, con la cual podemos
 * escucchar conexiones mediante un hilo actualizable
 */
public class Servicio_Tracker extends Thread {

    final String os = System.getProperty("os.name");
    //Metodo run, se ejecutara cuando lo llame el metodo star

    @Override
    public void run() {
        //Emeplamos un bucle whiel, el cual se repite hasta dar enter
        while (true) {
            System.out.println("--> Tracker en linea, escuchando el puerto --> + " + Constants.get("listeningPort"));
            System.out.println("Enter para deter el Tracker....");
            //Creamos y escribimos el archivo peer.xml
            File archivo_peer = new File("-/tracker/archivos/peers.xml");
            if (!archivo_peer.exists()) {
                //Si el archivo peer.xml no existe, deducimos que no contiene peers
                System.out.println("Sin peers");
                try {
                    //Detenemos el programa 5 seg, para el efecto de actualizarlo
                    Servicio_Tracker.sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                //Actulizamos el archivo peers.xml y mapper.xml
                DocumentBuilderFactory objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder objDocumentBuilder = null;
                //Construimos el archivo
                try {
                    objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //Epleamos un objeto de documentos de servicio, para poder manipular el archivo peer.xml
                org.w3c.dom.Document documento = null;
                try {
                    documento = objDocumentBuilder.parse(archivo_peer);
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                documento.getDocumentElement().normalize();
                System.out.println("Peers: " + documento.getDocumentElement().getNodeName());
                //Declaramos un lista de nodos, la cual contiene los peers
                NodeList lista_nodos = documento.getElementsByTagName("peer");
                //Empleamos un ciclo for, para poder desplazarnos en el documento
                for (int i = 0; i < lista_nodos.getLength(); i++) {
                    //Declaramos un nuevo nodo, el cual agregaremos a nuestra lista
                    Node nuevo_nodo = lista_nodos.item(i);
                    System.out.println(nuevo_nodo.getNodeName() + (i + 1));
                    if (nuevo_nodo.getNodeType() == Node.ELEMENT_NODE) {
                        //Con un objeto elemento, obtendremos los elementos del nodo
                        org.w3c.dom.Element elemento_nodo = (org.w3c.dom.Element) nuevo_nodo;
                        System.out.println("\tDireccion IP: " + elemento_nodo.getElementsByTagName("IP").item(0).getTextContent());
                        System.out.println("\tPuerto: " + elemento_nodo.getElementsByTagName("puerto").item(0).getTextContent());
                        System.out.println("\tArchivos Torrents en el Peer:");
                        //Empleamos otra lista, con la cual manipolaremos los archivos torrent en cada peer
                        NodeList lista_torrent_peer = elemento_nodo.getElementsByTagName("torrents");
                        Node torrent_peer = lista_torrent_peer.item(0);
                        org.w3c.dom.Element elementos_Torrent = (org.w3c.dom.Element) torrent_peer;
                        //Lista de nodos, la cual contiene el ID de los torrents
                        NodeList lista_nodos_Torrent_ID = elementos_Torrent.getElementsByTagName("torrent_id");
                        Node nodo_ID = lista_nodos_Torrent_ID.item(0);
                        org.w3c.dom.Element elementos_ID = (org.w3c.dom.Element) nodo_ID;
                        System.out.println("\t" + nodo_ID.getNodeName() + ":" + elementos_Torrent.getTextContent());
                    }
                }
                try {
                    Servicio_Tracker.sleep(9000);
                    System.out.println("\b\b\b");
                    System.out.println("\n");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
