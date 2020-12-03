package analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FileChecker {
    private final Path folderPath;
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public Map<String, Future<Pattern>> checkFiles (SearchingStrategy strategy, List<Pattern> patterns) {
        Map <String, Future<Pattern>> nameToFutureResult = new HashMap<>();

        File folder = folderPath.toFile();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            nameToFutureResult.put(file.getName(), executor.submit(() -> {
                String text = readFile(file.getPath());
                return strategy.search(text, patterns);
            }));
        }

        return nameToFutureResult;
    }

    private static String readFile(String fileName) {
        try {
            return Files.readString(Path.of(fileName));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return "";
    }

    public FileChecker(Path folderPath) {
        this.folderPath = folderPath;
    }

    public void printResults(Map<String, Future<Pattern>> results) {
        results.forEach((name, result) -> {
            try {
                var pattern = result.get();
                System.out.printf("%s: %s", name, pattern != null ? pattern.getFileType() : "Unknown file type");
                System.out.println();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

}
