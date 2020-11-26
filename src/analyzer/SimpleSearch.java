package analyzer;

public class SimpleSearch implements SearchingStrategy {

    @Override
    public boolean search(String text, String pattern) {
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) != pattern.charAt(0)) {
                continue;
            }

            boolean same = true;
            for (int j = 1; j < pattern.length(); j++) {
                if (text.charAt(i + j) != pattern.charAt(j)) {
                    same = false;
                    break;
                }
            }
            if (same) return true;
        }

        return false;
    }
}
