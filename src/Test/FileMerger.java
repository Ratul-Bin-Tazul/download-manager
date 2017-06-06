package Test;

import java.io.*;
import java.nio.channels.FileChannel;

/**
 * This class merges file splits created by the DownloadTask class.
 *
 * @see DownloadTask
 */
public class FileMerger implements Runnable {

    // Save location for the file
    private String saveLoc;

    private FileInputStream[] inputStreams;

    private int noOfSplits;

    private String fileName;

    /**
     * Constrcutor for FileMerger
     *
     * @param saveLoc    Save location for the file
     * @param noOfSplits No of split file. This should be equal to the number of connections made by the DownloadTask thread.
     * @see DownloadTask
     */
    public FileMerger(String saveLoc, int noOfSplits, String fileName) {
        this.saveLoc = saveLoc + fileName;
        this.noOfSplits = noOfSplits;
        this.fileName = fileName;

        inputStreams = new FileInputStream[noOfSplits];

        try {
            FileOutputStream fs = new FileOutputStream(this.saveLoc);
            fs.flush();
            fs.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try{

            for (int i = 0; i < noOfSplits; i++) {
                File temp = new File(Test.saveLocation + fileName + "/" + i + ".file");
                inputStreams[i] = new FileInputStream(temp);
            }
            FileOutputStream fs = new FileOutputStream(saveLoc, true);

            FileChannel fsOut = fs.getChannel();

            long lastBytePosition = 0;

            for (int i = 0; i < noOfSplits; i++) {
                FileChannel fsIn = inputStreams[i].getChannel();
                fsOut.transferFrom(fsIn, lastBytePosition, fsIn.size());
                lastBytePosition += fsIn.size();
                fsIn.close();
                inputStreams[i].close();
            }

            fsOut.close();

            fs.flush();
            fs.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
