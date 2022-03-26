import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class Rar {

    public static void main(String[] args) {
        Map<Character, Integer> result = new HashMap<>();

        try (Reader reader = new FileReader(args[0])) {
            int symbol;
            while ((symbol = reader.read()) != -1) {
                Character key = (char) symbol;
                Integer counter = result.get(key);
                result.put(key, counter == null ? 1 : ++counter);
            }
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

        result.keySet()
                .forEach(key -> System.out.printf("[%s] = %03d%n", key, result.get(key)));
    }
}
