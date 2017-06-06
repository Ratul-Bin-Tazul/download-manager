package Test;

import java.util.Scanner;

/**
 * Main class for testing purpose
 * @author Jamius Siam
 */
public class Test {
    
    /**
     *  Use this url to save the file
     */
    public static String saveLocation = System.getProperty("user.home") + "/Desktop/temp/";


    /**
     * The main method for trying out the download System
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        Scanner stdin = new Scanner(System.in);

        do {
            System.out.println("Please enter the download link.");
            String urlI = stdin.nextLine();
            System.out.println("Please enter number of connections");
            int conn = stdin.nextInt();
            stdin.nextLine();
            Thread t1 = new Thread(new DownloadTask(urlI, conn));
            t1.start();
            t1.join();
            System.out.println("\n\nDownload Another? (Y/N)");
        } while ((stdin.nextLine().equalsIgnoreCase("y")));
    }
}
