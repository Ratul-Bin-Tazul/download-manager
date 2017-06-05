package Test;

/**
 * Main class for testing purpose
 * @author Jamius Siam
 */
public class Test {

    /**
     * Use this url to Test out multithreaded download
     */
    static String url = "http://www.pcfreetime.com/public/FFSetup4.1.0.0.exe";

    /**
     *  Use this url to save the file
     */
    public static String saveLocation = System.getProperty("user.home") + "/Desktop/temp/";


    /**
     * The main method for trying out the download System
     * @param args
     */
    public static void main(String[] args) {
        new Thread(new DownloadTask(url, 8)).start();
    }
}
