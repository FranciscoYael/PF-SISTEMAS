package Prinicipal;
import jBittorrentAPI.*;
/**
 * Descarga de archivos torren, con base a un hilo
 */
public class Direcciones extends Thread {
    //Atributos de la clase:
    String dirrecccion_torrent;
    String dirreccion_archivos;
    //Constructor de la clase:
    public Direcciones(String dirrecccion_torrent, String dirreccion_archivos) {
        this.dirrecccion_torrent = dirrecccion_torrent;
        this.dirreccion_archivos = dirreccion_archivos;
    }
    //Metodo run

    @Override
    public void run() {
        try {
            TorrentProcessor procesadorTorrent = new TorrentProcessor();
            TorrentFile archivoTorrent = procesadorTorrent.getTorrentFile(procesadorTorrent.parseTorrent(dirrecccion_torrent));
            Constants.SAVEPATH = dirreccion_archivos;
            if(archivoTorrent != null){
                DownloadManager objDownloadManager = new DownloadManager(archivoTorrent, Utils.generateID());
                //Escuchamos los puertos
                objDownloadManager.startListening(6881, 6889);
                //Actualizamos el tracker
                objDownloadManager.startTrackerUpdate();
                objDownloadManager.blockUntilCompletion(this.getName());
                objDownloadManager.stopTrackerUpdate();
                objDownloadManager.closeTempFiles();
            }else{
                System.err.println("El archivo, no es un .torrent valido....");
                System.exit(1);
            }
        } catch (Exception e) {
            System.err.println("Error al procesar archivo torren \n" + e);
            System.exit(1);
        }
    }
}
