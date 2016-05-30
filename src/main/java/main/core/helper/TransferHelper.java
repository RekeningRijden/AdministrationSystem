package main.core.helper;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

/**
 * @author Sam
 *         <p>
 *         Utility class for downloading files from the system or uploading files to the
 *         system.
 *         </p>
 */
public final class TransferHelper {

    private static final int BUFFER_SIZE = 2048;

    private TransferHelper() {
        //Utility class constructor cannot be called.
    }

    /**
     * Download a file to the users filesystem.
     *
     * @param file to download.
     * @throws IOException when to file cannot be found or downloaded.
     */
    public static void downloadFile(File file) throws IOException {
        download(file, "attachment");
    }

    /**
     * Show a file in the browsers inline view.
     *
     * @param file to show.
     * @throws IOException when a file cannot be found or shown.
     */
    public static void downloadView(File file) throws IOException {
        download(file, "inline");
    }

    /**
     * Download a file.
     *
     * @param file   to download.
     * @param action specifying to download the file to the users filesystem or
     *               to show the file in the browsers inline view.
     * @throws IOException when a file cannot be found, downloaded or shown.
     */
    private static void download(File file, String action) throws IOException {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        String fileName = file.getName();
        String contentType = ec.getMimeType(fileName);
        int contentLength = (int) file.length();

        ec.responseReset();
        ec.setResponseContentType(contentType);
        ec.setResponseContentLength(contentLength);
        ec.setResponseHeader("Content-Disposition", action + "; filename=\"" + fileName + "\"");

        OutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            outputStream = ec.getResponseOutputStream();
            inputStream = new FileInputStream(file);

            int bytesRead;
            byte[] bytesBuffer = new byte[BUFFER_SIZE];

            while ((bytesRead = inputStream.read(bytesBuffer)) > 0) {
                outputStream.write(bytesBuffer, 0, bytesRead);
            }

            outputStream.flush();
        } finally {
            close(outputStream);
            close(inputStream);
        }

        fc.responseComplete();
    }

    /**
     * Upload a file.
     *
     * @param part         specifying the data to be uploaded.
     * @param uploadedFile specifying to which file the data has to go.
     * @throws IOException when the uploadedFile cannot be found.
     */
    public static void upload(Part part, File uploadedFile) throws IOException {
        OutputStream outputStream = null;
        InputStream inputStream = null;

        try {
            outputStream = new FileOutputStream(uploadedFile);
            inputStream = part.getInputStream();

            int bytesRead;
            byte[] bytes = new byte[BUFFER_SIZE];

            while ((bytesRead = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, bytesRead);
                outputStream.flush();
            }
        } finally {
            close(outputStream);
            close(inputStream);
        }
    }

    /**
     * Close a stream.
     *
     * @param stream to close.
     */
    private static void close(Closeable stream) {
        if (stream != null) {
            try {
                stream.close();
            } catch (IOException e) {
                Logger.getLogger(TransferHelper.class.getName()).log(Level.SEVERE, null, e);
            }
        }
    }
}
