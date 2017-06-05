package Test;

import java.io.*;

/**
 * Created by Jamius Siam on 05-Jun-17.
 */
public class FileMerger implements Runnable {

    // Save location for the file
    private String saveLoc;

    private InputStream[] inputStreams = new InputStream[8];


    /**
     * Constrcutor for FileMerger
     * @param saveLoc Save location for the file
     */
    public FileMerger(String saveLoc) {
        this.saveLoc = saveLoc;

        try {
            FileOutputStream fs = new FileOutputStream(saveLoc);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 8; i++) {
                File temp = new File(Test.saveLocation + i + ".file");
                inputStreams[i] = new FileInputStream(temp);
            }

            FileOutputStream fs = new FileOutputStream(saveLoc, true);

            for (int i = 0; i < 8; i++) {
                for (int j = inputStreams[i].read(); j != -1; j = inputStreams[i].read()) {
                    fs.write(j);
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
