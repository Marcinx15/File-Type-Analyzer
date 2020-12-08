package analyzer;

import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        String folderPath = args[0];
        String patternsDb = args[1];

        PatternsReader reader = new PatternsReader();
        reader.readPatterns(patternsDb);
        reader.sortPatternsByPriority();

        FileChecker checker = new FileChecker(Path.of(folderPath));
        checker.printResults(checker.checkFiles(new RabinKarpSearch(), reader.getPatterns()));
    }
}
