package analyzer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        String searchingAlgorithm = args[0];
        String fileName = args[1];
        String pattern = args[2];
        String fileType = args[3];

        String fileText = readFile(fileName);
        SearchingStrategy searchingStrategy = SearchingStrategy.getInstance(searchingAlgorithm);

        assert searchingStrategy != null;
        long start = System.nanoTime();
        System.out.println(searchingStrategy.search(fileText,pattern) ? fileType : "Unknown file type");
        long end = System.nanoTime();
        double executionTime = (double) (end - start) / 1_000_000_000;
        System.out.printf("It took %.3f seconds", executionTime);
    }

    public static String readFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return "";
    }

}
