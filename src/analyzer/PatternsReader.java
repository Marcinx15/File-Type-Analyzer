package analyzer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PatternsReader {
    private List<Pattern> patterns;

    public List<Pattern> getPatterns() {
        return patterns;
    }

    public void readPatterns(String folderPath) {
        try {
            patterns = Files.lines(Path.of(folderPath))
                    .map(s -> s.split(";"))
                    .map(strings -> new Pattern(removeBorderQuotes(strings[1]), removeBorderQuotes(strings[2]),
                            Integer.parseInt(strings[0])))
                    .collect(Collectors.toList());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    private static String removeBorderQuotes(String text) {
        if (text.startsWith("\"")) {
            text = text.substring(1);
        }

        if (text.endsWith(("\""))) {
            text = text.substring(0, text.length() - 1);
        }

        return text;
    }

    public void sortPatternsByPriority() {
        patterns.sort(Comparator.comparing(Pattern::getPriority, Comparator.reverseOrder()));
    }
}
