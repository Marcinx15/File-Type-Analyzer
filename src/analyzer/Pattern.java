package analyzer;

import java.util.Comparator;

public class Pattern implements Comparable<Pattern> {
    private final String pattern;
    private final String fileType;
    private final int priority;

    public Pattern(String pattern, String fileType, int priority) {
        this.pattern = pattern;
        this.fileType = fileType;
        this.priority = priority;
    }


    public String getPattern() {
        return pattern;
    }

    public String getFileType() {
        return fileType;
    }

    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Pattern{" +
                "pattern='" + pattern + '\'' +
                ", fileType='" + fileType + '\'' +
                ", priority=" + priority +
                '}';
    }

    @Override
    public int compareTo(Pattern pattern) {
        return Comparator.comparingInt(Pattern::getPriority).compare(this, pattern);
    }
}
