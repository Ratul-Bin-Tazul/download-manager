package Test;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * DownloadTask class handles a single download task. It Creates DownloadConnectionThread according to the number of simultaneous download connection set
 * by the user.
 *
 * @see DownloadConnectionThread
 */
public class DownloadTask implements Runnable {


    // Url to download
    private String url;

    // Number of simultaneous download connection for the Download Task
    private int connectionCount;

    private Thread[] downloadThreads;

    private DownloadConnectionThread[] downloadConnectionThreads;

    // Internal use **Do not edit**
    private String fileExt;

    /**
     * The constructor for creating a DownloadTask
     *
     * @param url             Url to download
     * @param connectionCount Number of simultaneous download connection for the Download Task
     */
    public DownloadTask(String url, int connectionCount) {
        this.url = url;
        this.connectionCount = connectionCount;
        downloadConnectionThreads = new DownloadConnectionThread[connectionCount];
        downloadThreads = new Thread[connectionCount];
    }

    @Override
    public void run() {
        try {
            long size = new URL(url).openConnection().getContentLengthLong();
            long partSize = size / connectionCount;
            long lastBytePosition = 0;

            System.out.println(String.format("Download Size:\t%d \nPart Size:\t%d \nNumber of connection:\t%d\n", size, partSize, connectionCount));

            for (int i = 0; i < connectionCount; i++) {
                System.out.print("Thread " + i + "\t");
            }
            System.out.println();

            long startTime = System.nanoTime();
            for (int i = 0; i < connectionCount; i++) {
                if (i != connectionCount - 1) {


                    downloadConnectionThreads[i] = new DownloadConnectionThread(i, lastBytePosition, lastBytePosition + partSize, url, fileExt());
                    lastBytePosition += partSize + 1;
                } else {
                    downloadConnectionThreads[i] = new DownloadConnectionThread(i, lastBytePosition, size, url, fileExt());
                }

                downloadThreads[i] = new Thread(downloadConnectionThreads[i]);
                downloadThreads[i].start();
            }

            while (isDownloading()) {
                for (int i = 0; i < connectionCount; i++) {
                    System.out.printf("%.2f\t\t", downloadConnectionThreads[i].getProgess());
                }

                System.out.println();
                Thread.sleep(500);
            }

            long endTime = System.nanoTime();
            long end2 = (endTime - startTime) / 1000000000;

            System.out.println( "\nDownload Time:\t" + end2 + " seconds");

            System.out.println("Merging");
            Thread t1 = new Thread(new FileMerger(System.getProperty("user.home") + "/Desktop/", connectionCount, fileExt()));

            t1.start();
            t1.join();



            System.out.println("Done. Downlaoded to: " + System.getProperty("user.home") + "/Desktop/" + fileExt());
            File file = new File(System.getProperty("user.home") + "/Desktop/temp/" + fileExt());

            Thread.sleep(2000);
            FileUtils.deleteDirectory(file);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isDownloading() {
        for (int i = 0; i < connectionCount; i++) {
            if (downloadThreads[i].isAlive())
                return true;
        }

        return false;
    }

    private String fileExt() {
        if (fileExt != null) return fileExt;

        int index = -1;
        for (int i = url.length() - 1; i >= 0; i--) {
            if (url.charAt(i) == '/' || url.charAt(i) == '\\') {
                index = i;
                break;
            }
        }
        fileExt = url.substring(index + 1, url.length());
        fileExt = fileExt.trim();
        return ((index == -1) ? null : fileExt);
    }
}
