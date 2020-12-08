package analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FileChecker {
    private final Path folderPath;
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 1);

    public Map<String, Future<Optional<Pattern>>> checkFiles (SearchingStrategy strategy, List<Pattern> patterns) {
        Map <String, Future<Optional<Pattern>>> nameToFutureResult = new HashMap<>();

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

    public void printResults(Map<String, Future<Optional<Pattern>>> results) {
        results.forEach((name, result) -> {
            try {
                var pattern = result.get();
                System.out.print(name + ": ");
                pattern.ifPresentOrElse(s -> System.out.println(s.getFileType()),
                        () -> System.out.println("Unknown file type"));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });
    }

}
