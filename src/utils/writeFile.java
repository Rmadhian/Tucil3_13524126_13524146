package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class writeFile {
    public static boolean writeToFile(String filename, String content) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(content);
            return true;
        } catch (java.io.IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }

    public static String buildSolutionFilePath(String inputFilePath) {
        File inputFile = new File(inputFilePath);
        String fileName = inputFile.getName();
        int extensionIndex = fileName.lastIndexOf('.');

        if (extensionIndex > 0) {
            fileName = fileName.substring(0, extensionIndex);
        }

        File parent = inputFile.getParentFile();
        File outputFile = new File(parent == null ? "." : parent.getPath(), fileName + "_solusi.txt");
        return outputFile.getPath();
    }
}
