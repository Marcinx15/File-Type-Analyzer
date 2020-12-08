package analyzer;

import java.util.*;
import java.util.stream.Collectors;

public class RabinKarpSearch implements SearchingStrategy {
    private static final long HASH_CONSTANT = 128;
    private static final long HASH_MODULO = 2_000_000_009;

    /* pattern list needs to be sorted by priority in reverse order (from highest to lowest)
       in order to reach maximum performance */
    @Override
    public Optional<Pattern> search(String text, List<Pattern> patterns) {
        Set<Pattern> foundMatches = new HashSet<>();
        for (Pattern pattern : patterns) {
            if (pattern.getPattern().length() > text.length()) {
                continue;
            }

            Optional<Pattern> maxPriorityPattern = foundMatches.stream().max(Pattern::compareTo);
            int maxPriority = maxPriorityPattern.map(Pattern::getPriority).orElse(-1);
            int currentPriority = pattern.getPriority();
            // if we already found a match with higher or same priority
            if (currentPriority <= maxPriority) {
                return maxPriorityPattern;
            }

            int currentLength = pattern.getPattern().length();
            var hashToPattern = hashPatternsOfLength(patterns, currentLength);

            // hash the first substring of current length starting from the end of text
            String substring = text.substring(text.length() - currentLength);
            long hash = hashFunction(substring);
            Pattern p = hashToPattern.get(hash);
            if (p != null && p.getPattern().equals(substring)) {
                if (p.getPriority() == currentPriority) {
                    return Optional.of(p);
                }
                foundMatches.add(p);
            }

            // calculate power to use it in formula that calculates previous hash in constant time
            long pow = modularExponentiation(currentLength - 1);
            for (int j = text.length() - currentLength - 1; j >= 0; j--) {
                hash = ((hash - charToLong(text.charAt(j + currentLength)) * pow) % HASH_MODULO + HASH_MODULO) * HASH_CONSTANT;
                hash += charToLong(text.charAt(j));
                hash %= HASH_MODULO;

                Pattern match = hashToPattern.get(hash);
                if (match != null && match.getPattern().equals(text.substring(j, j + currentLength))) {
                    // if the found match corresponds to the pattern of highest possible priority at the time
                    if (match.getPriority() == currentPriority) {
                        return Optional.of(match);
                    }
                    foundMatches.add(match);
                }
            }
        }

        return Optional.empty();
    }

    private long modularExponentiation(int power) {
        long result = 1;
        for (int i = 0; i < power; i++) {
            result *= RabinKarpSearch.HASH_CONSTANT;
            result %= RabinKarpSearch.HASH_MODULO;
        }
        return result;
    }


    private Map<Long, Pattern> hashPatternsOfLength(List<Pattern> patterns, int len) {
        List<Pattern> patternsOfLength = getPatternsOfLength(patterns,len);
        if (patternsOfLength.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Long, Pattern> hashToPattern = new HashMap<>();
        for (Pattern pattern : patternsOfLength) {
            String patternText = pattern.getPattern();
            hashToPattern.put(hashFunction(patternText), pattern);
        }

        return hashToPattern;
    }

    private long hashFunction(String text) {
        long hash = 0;
        long pow = 1;

        for (int i = 0; i < text.length(); i++) {
            hash += (charToLong(text.charAt(i)) * pow) % HASH_MODULO;
            hash %= HASH_MODULO;
            pow = (pow * HASH_CONSTANT) % HASH_MODULO;
        }

        return hash;
    }


    private List<Pattern> getPatternsOfLength(List<Pattern> patterns, int len) {
        return patterns.stream()
                .filter(p -> p.getPattern().length() == len)
                .collect(Collectors.toList());
    }

    private static long charToLong(char ch) {
        return ch;
    }
}
