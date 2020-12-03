package analyzer;

import java.util.List;

public class KmpSearch implements SearchingStrategy {

    @Override
    public Pattern search(String text, List<Pattern> patterns) {
        for(Pattern elem : patterns) {
            String pattern = elem.getPattern();

            int[] border = borderFunction(pattern);
            int patternIndex = 0;

            for (int i = 0; i < text.length(); i++) {
                while (patternIndex > 0) {
                    if (text.charAt(i) == pattern.charAt(patternIndex)) {
                        patternIndex++;
                        break;
                    } else {
                        patternIndex = border[patternIndex - 1];
                    }
                }

                if (patternIndex == 0 && text.charAt(i) == pattern.charAt(patternIndex)) {
                    patternIndex++;
                }

                if (patternIndex == pattern.length()) {
                    return elem;
                }
            }
        }

        return null;
    }

    private int[] borderFunction(String word) {
        int[] border = new int[word.length()];

        int prefixLength;
        for (int i = 1; i < word.length(); i++) {
            prefixLength = border[i - 1];
            while (prefixLength > 0) {
                if (word.charAt(i) == word.charAt(prefixLength)) {
                    border[i] = prefixLength + 1;
                    break;
                }
                prefixLength = border[prefixLength - 1];
            }

            if (prefixLength == 0) {
                border[i] = word.charAt(i) == word.charAt(border[0]) ? 1 : 0;
            }
        }

        return border;
    }

}
