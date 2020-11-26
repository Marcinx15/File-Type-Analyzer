package analyzer;

public class PatternSearcher {
    private final String text;
    private final String pattern;

    private PatternSearcher(String text, String pattern) {
        this.text = text;
        this.pattern = pattern;
    }

    public String getText() {
        return text;
    }

    public String getPattern() {
        return pattern;
    }

    public static PatternSearcher create(String text, String pattern) {
        return new PatternSearcher(text, pattern);
    }

    public boolean isPatternPresent(String text, String pattern, SearchingStrategy strategy) {
        return strategy.search(text, pattern);
    }

    public boolean isPatternPresent(String text, String pattern) {
        return new SimpleSearch().search(text, pattern);
    }
}
