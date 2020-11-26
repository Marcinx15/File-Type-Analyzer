package analyzer;

@FunctionalInterface
public interface SearchingStrategy {
    boolean search(String text, String pattern);

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
