package com.company;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class FileWorker {
    public static boolean createTmpDirectories(String dirName){
        Path path = Paths.get(dirName);
        try {
            Files.createDirectories(path);
            return true;
        } catch (IOException e) {
            System.out.println("Не удается создать директорию, проверьте и перезапустите приложение");
            e.printStackTrace();
            return false;
        }
    }
    public static void deleteDirectories() throws IOException {
        Files.walk(Paths.get(DataSorter.tmpDirectory))
                .sorted(Comparator.naturalOrder())
                .map(Path::toFile)
                .forEach(File::delete);
    }

}
