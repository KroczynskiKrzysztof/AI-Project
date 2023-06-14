package Language;

import Basic.Row;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;

public class LanguageTools {
    public static ArrayList<Row> readLanguageFiles(String path) throws IOException {

        ArrayList<Row> rows = new ArrayList<>();

        FileVisitor<Path> fileVisitor = new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.getFileName().toString().endsWith(".txt")) {
                    Row row = new Row(getCharCount(Files.readString(file)), file.getParent().getFileName().toString());
                    rows.add(row);
                }

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        };


        Files.walkFileTree(new File(path).toPath(), fileVisitor);



        return rows;
    }
    private static final char[] latinAlphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    public static Double[] getCharCount(String s){
        int latincharCount = 0;
        Double[] charCount = new Double[26];
        for (char c : s.toLowerCase().toCharArray()) {
            for (int i = 0; i < latinAlphabet.length; i++) {
                char c1 = latinAlphabet[i];
                if (c == c1) {
                    charCount[i]++;
                    latincharCount++;
                    break;
                }
            }
        }

        for (int i = 0; i < charCount.length; i++) {
            charCount[i]=charCount[i]/latincharCount;
        }
        return charCount;
    }
}
