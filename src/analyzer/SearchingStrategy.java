package analyzer;

import java.util.List;

@FunctionalInterface
public interface SearchingStrategy {
    Pattern search(String text, List<Pattern> patterns);

    static SearchingStrategy getInstance(String algorithm) {
        switch (algorithm) {
            case "--naive":
                return new SimpleSearch();
            case "--KMP":
                return new KmpSearch();
            default:
                return null;
        }
    }
}
