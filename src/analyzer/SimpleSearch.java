package analyzer;

import java.util.List;

public class SimpleSearch implements SearchingStrategy {

    @Override
    public Pattern search(String text, List<Pattern> patterns) {
        for (Pattern elem : patterns) {
            String pattern = elem.getPattern();
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
                if (same) {
                    return elem;
                }
            }
        }

        return null;
    }
}
