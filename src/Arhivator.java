import java.io.File;
import java.util.HashSet;

public class Arhivator {

    public static void main(String[] args) {
        System.out.println(Integer.toString(67, 2));

        if (args.length != 3) {     // проверка что переданны 3 аргумента (команда, исходник, финальный)
            System.err.println("Недостаточное количество параметров");
            System.exit(1);
        }

        String command = args[0];
        File source = new File(args[1]);
        File destination = new File(args[2]);

        if (!source.exists() || !source.canRead()) {    // проверка доступа к чтению и проверка на существование
            System.err.println("Невозможно получить доступ к исходному файлу");
            System.exit(1);
        }

        byte[] result;  // результат работы будет представлен массив байтов
        try {
            switch (command) {
                case "a" -> result = encode(source);    // отрабатывает функция, и мы получаем ЗАкодированный результат
                case "e" -> result = decode(source);    // отрабатывает функция, и мы получаем ДЕкодированный результат
                default -> {
                    System.err.println("Неверная команда");
                    System.exit(1);
                    return;
                }
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
            return;
        }

        write(result, destination);
    }

    private static void write(byte[] result, File destination) {

    }

    private static byte[] decode(File source) {     // функция что декодирует исходник
        HashSet<Character> characters = new HashSet<>();
        Character[] chars = characters.toArray(new Character[0]);
        return new byte[0];
    }

    private static byte[] encode(File source) {     // функция что кодирует исходник
        HashSet<Character> characters = new HashSet<>();
        Character[] chars = characters.toArray(new Character[0]);
        return new byte[0];
    }
}
