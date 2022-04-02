package FileWorker;

import java.io.*;

public class FileInputHelper {
    private final FileInputStream fileInputStream;
    private final BufferedReader fileBufferedReader;

    public FileInputHelper(File file) throws IOException {
        fileInputStream = new FileInputStream(file);
        fileBufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));
    }


    public byte readByte() throws IOException {
        int cur = fileInputStream.read();
        if (cur == -1)//если закончился файл
            throw new EOFException();
        return (byte)cur;
    }

    public String readLine() throws IOException {
        return fileBufferedReader.readLine();
    }

    public void close() throws IOException{
        fileInputStream.close();
    }
}
