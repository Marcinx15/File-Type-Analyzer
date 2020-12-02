package analyzer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FileChecker {
    private Path folderPath;
    private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public Map<String, Future<Boolean>> checkFiles (SearchingStrategy strategy, String pattern) {
        Map <String, Future<Boolean>> nameToFutureResult = new HashMap<>();

        File folder = folderPath.toFile();
        for (File file : Objects.requireNonNull(folder.listFiles())) {
            nameToFutureResult.put(file.getName(), executor.submit(() -> {
                String text = readFile(file.getPath()folderPath + "/" + file.getName());
                return strategy.search(text, pattern);
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

    public void setFolderPath(Path folderPath) {
        this.folderPath = folderPath;
    }


}
