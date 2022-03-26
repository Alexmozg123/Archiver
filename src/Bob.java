import java.io.*;

public class Bob {

    public static void main(String[] args) {
        if (args.length == 0) {
            return;
        }

        File sourceFile = new File(args[0]);
        String reverseContent = reverseContent(sourceFile);

        String newFileName = fileNameWithoutExtension(sourceFile);
        File parent = sourceFile.getParentFile();
        File pathNewFile = new File(parent, reverse(newFileName) + ".txt");
        writeToFile(pathNewFile, reverseContent);
    }

    public static void writeToFile(File path, String text) {
        try (Writer writer = new FileWriter(path)) {
            writer.write(text);
            writer.flush();
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    public static String reverseContent(File source) {
        StringBuilder result = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(source))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (result.length() != 0) {
                    result.append("\n");
                }
                result.append(line);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
        return result.reverse().toString();
    }

    public static String fileNameWithoutExtension(File file) {
        String fileName = file.getName();
        int pos = fileName.lastIndexOf(".");
        if (pos > 0) {
            fileName = fileName.substring(0, pos);
        }
        return fileName;
    }

    public static String reverse(String input) {
        char[] in = input.toCharArray();
        int begin = 0;
        int end = in.length - 1;
        char temp;
        while (end > begin) {
            temp = in[begin];
            in[begin] = in[end];
            in[end] = temp;
            end--;
            begin++;
        }
        return new String(in);
    }
}
