package com.company;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        String inputFile = "resources\\input.txt";
        String outputFile = "resources\\output.txt";
        Path path = Paths.get(inputFile);
        if (!Files.exists(path)) {
            Files.createFile(path);
        }
        DataWriter.writeRandomData(inputFile); // запишем данные в файл
        Scanner in = new Scanner((System.in));
        if (!Files.exists(Paths.get(outputFile))) {
            Files.createFile(Paths.get(outputFile));
        }
        DataSorter dataSorter = new DataSorter();
        int result = dataSorter.mainSorter(inputFile, outputFile);
        if(result != -1){
            System.out.println("Сортировка завершена!");
        }
        System.exit(0);
    }
}
