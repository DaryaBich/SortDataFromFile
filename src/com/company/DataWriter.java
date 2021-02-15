package com.company;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class DataWriter {
    private static long genLineCount = 4_500_000;
    public static void writeRandomData(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            Random randomizer = new Random();
            for (int i = 0; i < genLineCount; i++) {
                writer.write(Base64
                        .getEncoder()
                        .encodeToString(UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8)));
               if(Math.random() > 0.4){
                    writer.newLine();
                }
            }
        }
    }

    public static boolean sortAndWriteData(LinkedList<String> data, long chunkNumber, String dirName) {
        data.sort(Comparator.naturalOrder());
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dirName + "\\"+chunkNumber +".txt"))) {
            for (int i = 0; i < data.size() - 1; i++) {
                writer.write(data.get(i));
                writer.newLine();
            }
            writer.write(data.get(data.size() - 1));
            return true;
        } catch (IOException e) {
            System.out.println("Не удалось записать в файл " + dirName);
            e.printStackTrace();
            return false;
        }
    }

    public static boolean writeMergingData(String outputFile, HashMap<Long, LinkedList<String>> mergedText,
                                        HashMap<Long, Long> indexes, long curChunkSize) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {
            while (true) {
                for (Map.Entry<Long, LinkedList<String>> e:mergedText.entrySet()) {
                    if(e.getValue().isEmpty() && indexes.get(e.getKey()) != null){
                        mergedText = DataReader.fill(e.getKey(), indexes, mergedText, DataSorter.tmpDirectory, curChunkSize);
                    }

                }
                String nextWrite = findNextStr(mergedText);
                if(nextWrite == null){
                    break;
                }
                writer.write(nextWrite);
                writer.newLine();
            }
           FileWorker.deleteDirectories();
            return true;
        } catch (IOException e) {
            System.out.println("Не удается записать данные в конечный файл");
            e.printStackTrace();
            return false;
        }
    }

    private static String findNextStr(Map<Long, LinkedList<String>> mergeData){
        String minimal = null;
        long chunk = -1;
        for (Map.Entry<Long, LinkedList<String>> e:mergeData.entrySet()) {
            if(!e.getValue().isEmpty() && (minimal == null || minimal.compareTo(e.getValue().get(0)) > 0)){
                minimal = e.getValue().get(0);
                chunk = e.getKey();
            }
        }
        if(minimal != null){
            mergeData.get(chunk).remove(0);
        }
        return minimal;
    }
}
