package Test;

/**
 * Main class for testing purpose
 * @author Jamius Siam
 */
public class test {

    /**
     * Use this url to Test out multithreaded download
     */
    static String url = "https://cmanios.files.wordpress.com/2012/02/abstracto.png";

    /**
     *  Use this url to save the file
     */
    static String saveLocation = System.getProperty("user.home") + "/Desktop/testDownload.png";

    /**
     * The main method for trying out the download System
     * @param args
     */
    public static void main(String[] args) {
        DownloadThread t = new DownloadThread(0, 0, 566, url, saveLocation);
        Thread t1 = new Thread(t);
        t1.start();
    }
}
