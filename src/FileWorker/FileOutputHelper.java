package FileWorker;

import java.io.*;

public class FileOutputHelper implements AutoCloseable {
    private final File outputFile;
    private final FileOutputStream fileOutputStream;

    public FileOutputHelper(File file) throws FileNotFoundException {
        outputFile = file;
        fileOutputStream = new FileOutputStream(outputFile);
    }

    public void writeByte(byte msg) throws IOException {
        fileOutputStream.write(msg);
    }

    public void writeBytes(byte[] msg) throws IOException {
        fileOutputStream.write(msg);
    }

    public void writeString(String msg) {
        try (PrintWriter pw = new PrintWriter(outputFile)) {
            pw.write(msg);
        } catch (FileNotFoundException e) {
            System.out.println("Неверный путь, или такого файла не существует!");
        }
    }

    public void close() throws IOException {
        fileOutputStream.close();
    }

    public void finalize() throws IOException {
        close();
    }
}
