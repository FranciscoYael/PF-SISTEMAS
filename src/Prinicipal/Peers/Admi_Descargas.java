package Prinicipal.Peers;

import jBittorrentAPI.*;

public class Admi_Descargas extends Thread {

    //Atributos:
    String mapper_ruta_torrent;
    String mapper_ruta_archivos;

    //Contructor de la clase
    public Admi_Descargas(String mapper_ruta_torrent, String mapper_ruta_archivos) {
        this.mapper_ruta_torrent = mapper_ruta_torrent;
        System.out.println(mapper_ruta_torrent);
        this.mapper_ruta_archivos = mapper_ruta_archivos;
        System.out.println(mapper_ruta_archivos);
    }
    //Metodo run

    @Override
    public void run() {
        try {
            //Objeto empleado para mainupular el archivo torrent
            TorrentProcessor procesadorTorrent = new TorrentProcessor();
            //Creamos el torrent que obtnemos del archivo mapper
            TorrentFile archivoTorrent = procesadorTorrent.getTorrentFile(procesadorTorrent.parseTorrent(mapper_ruta_torrent));
            //Guardamos la ruta de direccion de los archivos torrent 
            Constants.SAVEPATH = mapper_ruta_archivos;
            //Escibimos los archivos descagados desde nuesto servidor
            if(archivoTorrent != null){
                DownloadManager admi_DownloadManager = new DownloadManager(archivoTorrent, Utils.generateID());
                //Creamos la coneccion de los peers e indicamos el rango de puertos a escuchar
                admi_DownloadManager.startListening(6881, 6889);
                admi_DownloadManager.startTrackerUpdate();
                admi_DownloadManager.blockUntilCompletion(this.getName());
                admi_DownloadManager.stopTrackerUpdate();
                admi_DownloadManager.closeTempFiles();
            }
        } catch (Exception e) {
            System.out.println("Archivo invalido");
            System.exit(0);
        }
    }
}
