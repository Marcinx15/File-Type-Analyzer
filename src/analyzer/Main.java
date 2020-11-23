package analyzer;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Main {
    public static void main(String[] args) {

//        try {
//            boolean match =
//                    Files.readAllLines(Paths.get(args[0]))
//                    .stream()
//                    .anyMatch(s -> s.contains(args[1]));
//            System.out.println(match ? args[2] : "Unknown file type");
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        }

        String fileName = args[0];
        String pattern = args[1];
        String typeName = args[2];

        boolean found = false;
        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(fileName))) {
            char[] charData = convertByteArrayToCharArray(inputStream.readAllBytes());
            found = searchForPattern(charData, pattern);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(found ? typeName : "Unknown file type");
    }

    public static char[] convertByteArrayToCharArray(byte[] bytes) {
        char[] charData = new char[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            charData[i] = (char) bytes[i];
        }
        return charData;
    }

    public static boolean searchForPattern(char[] data, String pattern) {
        for (int i = 0; i < data.length; i++) {
            if (data[i] != pattern.charAt(0)) {
                continue;
            }

            boolean same = true;
            for (int j = 1; j < pattern.length(); j++) {
                if (data[i + j] != pattern.charAt(j)) {
                    same = false;
                    break;
                }
            }
            if (same) return true;
        }

        return false;
    }


}
