package Test;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * The download thread to be created by the Main class
 * @author Jamius Siam
 */
public class DownloadThread implements Runnable {


     // Thread count
    private int threadNo;

    // Starting byte of the HTTP Range
    private int startByte;

    // Ending byte of the HTTP Range
    private int endByte;

    // Url to download
    private String downloadLoc;

    // Url to temp dir
    private String tempDir;


    /**
     * The constructor for creating a download thread
     * @param threadNo  Thread count
     * @param startByte Starting byte of the HTTP Range
     * @param endByte Ending byte of the HTTP Range
     * @param downloadLoc Url to download
     * @param tempDir Url to temp dir
     */
    public DownloadThread(int threadNo, int startByte, int endByte, String downloadLoc, String tempDir) {
        this.threadNo = threadNo;
        this.startByte = startByte;
        this.endByte = endByte;
        this.downloadLoc = downloadLoc;
        this.tempDir = tempDir;
    }

    @Override
    public void run() {
        URL url = null;

        try {
            url = new URL(downloadLoc);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            url.openConnection();

            InputStream in = url.openStream();
            FileOutputStream fs = new FileOutputStream(tempDir);

            for(int b = in.read(); b != -1; b = in.read()){
                fs.write(b);
            }

            System.out.println("Download done!\nFrom: " + downloadLoc + "\nTo: " + tempDir);
            fs.close();
        } catch (java.io.IOException e){
            e.printStackTrace();
        } finally {

        }
    }
}
