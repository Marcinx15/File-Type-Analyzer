package analyzer;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) {
        String folderPath = args[0];
        String pattern = args[1];
        String fileType = args[2];

        FileChecker checker = new FileChecker(Path.of(folderPath));
        var tasksResults = checker.checkFiles(new KmpSearch(), pattern);
        printResult(tasksResults, fileType);
    }

    public static void printResult(Map<String, Future<Boolean>> results, String fileType) {
        results.forEach((name, result) -> {
            try {
                System.out.printf("%s: %s", name, result.get() ? fileType : "Unknown file type");
                System.out.println();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

}
