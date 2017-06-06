package Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Path;

/**
 * The DownloadConnectionThread to be created by the DownloadTask class for simultaneous download connection.
 * @author Jamius Siam
 * @see DownloadTask
 */
public class DownloadConnectionThread implements Runnable {


     // Thread count
    private int threadNo;

    // Starting byte of the HTTP Range
    private long startByte;

    // Ending byte of the HTTP Range
    private long endByte;

    // Url to download
    private String downloadLoc;

    // Url to temp dir
    private String fileName;

    // Progress of the current thread
    private float progess;


    /**
     * The constructor for creating a download thread
     * @param threadNo  Thread count
     * @param startByte Starting byte of the HTTP Range
     * @param endByte Ending byte of the HTTP Range
     * @param downloadLoc Url to download
     * @param fileName Url to temp dir
     */
    public DownloadConnectionThread(int threadNo, long startByte, long endByte, String downloadLoc, String fileName) {
        this.threadNo = threadNo;
        this.startByte = startByte;
        this.endByte = endByte;
        this.downloadLoc = downloadLoc;

        File file =  new File(System.getProperty("user.home") + "/Desktop/temp/" + fileName);
        if(!file.exists())
            new File(System.getProperty("user.home") + "/Desktop/temp/" + fileName).mkdirs();

        this.fileName = fileName;
    }

    @Override
    public void run() {
        URL url = null;
        try {
            url = new URL(downloadLoc);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Range", "bytes=" + startByte + "-" + endByte);
            conn.connect();

            InputStream in = conn.getInputStream();
            FileOutputStream fs = new FileOutputStream(Test.saveLocation + fileName + "\\" + threadNo + ".file");

            long totalSize = endByte - startByte;
            for(int b = in.read(), count = 0 ; count <= endByte && b != -1; b = in.read(), count++){
                fs.write(b);
                progess = (float) count / (float) totalSize * 100f;
            }

            in.close();
            fs.close();
        } catch (java.io.IOException e){
            e.printStackTrace();
        } finally {

        }
    }

    public float getProgess(){
        return progess;
    }
}
