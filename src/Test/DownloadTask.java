package Test;

/**
 *  DownloadTask class handles a single download task. It Creates DownloadThread according to the number of simultaneous download connection set
 *  by the user.
 *  @see DownloadThread
 */
public class DownloadTask implements Runnable {

    /**
     *  Url to download
     */
    private String url;


    /**
     * The constructor for creating a DownloadTask
     * @param url Url to download
     */
    public DownloadTask(String url) {
        this.url = url;
    }

    @Override
    public void run() {

    }
}
