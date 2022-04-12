import FileWorker.FileOutputHelper;
import Haffman.HuffmanOperator;
import Haffman.HuffmanTree;

import java.io.File;
import java.io.IOException;
import java.nio.charset.MalformedInputException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;

public class Archiver {

    public static void main(String[] args) {
        System.out.println(Integer.toString(67, 2));

        if (args.length != 2) {     // проверка что переданны 3 аргумента (команда, исходник, финальный)
            System.err.println("Недостаточное количество параметров");
            System.exit(1);
        }

        String command = args[0];
        File source = new File(args[1]);

        if (!source.exists() || !source.canRead()) {    // проверка доступа к чтению и проверка на существование
            System.err.println("Невозможно получить доступ к исходному файлу");
            System.exit(1);
        }

        try {
            switch (command) {
                case "a" -> encode(source);    // отрабатывает функция, и мы получаем ЗАкодированный результат
                case "e" -> decode(source);    // отрабатывает функция, и мы получаем ДЕкодированный результат
                default -> {
                    System.err.println("Неверная команда");
                    System.exit(1);
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }

    private static byte[] decode(File source) {     // функция что декодирует исходник

        return new byte[0];
    }

    private static void encode(File source) throws IOException {     // функция что кодирует исходник
        List<String> stringList;
        File inputFile = new File(String.valueOf(source));
        StringBuilder s = new StringBuilder();
        File compressedFile, table;

        try {
            stringList = Files.readAllLines(Paths.get(inputFile.getAbsolutePath()));
        } catch (NoSuchFileException e) {
            System.out.println("Неверный путь, или такого файла не существует!");
            return;
        } catch (MalformedInputException e) {
            System.out.println("Текущая кодировка файла не поддерживается");
            return;
        }

        for (String item : stringList) {
            s.append(item);
            s.append('\n');
        }

        HuffmanOperator operator = new HuffmanOperator(new HuffmanTree(s.toString()));

        compressedFile = new File(inputFile.getAbsolutePath() + ".cpr");
        compressedFile.createNewFile();
        try (FileOutputHelper fo = new FileOutputHelper(compressedFile)) {
            fo.writeBytes(operator.getByteMsg());
        }
        //create file with encoding table:

        table = new File(inputFile.getAbsolutePath() + ".table.txt");
        table.createNewFile();
        try (FileOutputHelper fo = new FileOutputHelper(table)) {
            fo.writeString(operator.getEncodingTable());
        }

        System.out.println("Путь к сжатому файлу: " + compressedFile.getAbsolutePath());
        System.out.println("Путь к кодировочной таблице " + table.getAbsolutePath());
        System.out.println("Без таблицы файл будет невозможно извлечь!");

        double idealRatio = Math.round(operator.getCompressionRatio() * 100) / (double) 100;//идеализированный коэффициент
        double realRatio = Math.round((double) inputFile.length()
                / ((double) compressedFile.length() + (double) table.length()) * 100) / (double)100;//настоящий коэффициент

        System.out.println("Идеализированный коэффициент сжатия равен " + idealRatio);
        System.out.println("Коэффициент сжатия с учетом кодировочной таблицы " + realRatio);
    }
}
