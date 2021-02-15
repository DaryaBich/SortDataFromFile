package com.company;
import java.io.*;
import java.util.*;

public class DataWorker {
    private static final long MAX_AVAILABLE_BYTES = 125_829_120;

    public static long getOptimalNumberOfStrings(String fileName){
        long numberOfStrings = 0;
        long maxNumberOfBytes = 0;
        long sumBytes = 0;
        String line = "";

        try(BufferedReader reader = new BufferedReader(new FileReader(fileName))){
            while (sumBytes + maxNumberOfBytes < MAX_AVAILABLE_BYTES && (line = reader.readLine()) != null){
                numberOfStrings++;
                long curNumberOfBytes = line.getBytes().length;
                if (curNumberOfBytes > maxNumberOfBytes){
                    maxNumberOfBytes = curNumberOfBytes;
                }
                sumBytes += curNumberOfBytes;
            }
            System.gc();
        } catch (FileNotFoundException e) {
            System.out.println("Указанный файл не найден проверьте его наличие и повторите запуск программы");
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            System.out.println("Не удалось считаь данные из файла проверьте файл и повторите запуск программы");
            e.printStackTrace();
            return  -1;
        }
        return numberOfStrings;
    }


    public static long splitData(long numberOfStrings, String tmpDirName, String fileName){
        long chunkNumber = DataReader.readAndSortData(numberOfStrings, tmpDirName, fileName);
        return chunkNumber;
    }

    public static boolean mergeData(long chunkNumber, String outputFile) {
        HashMap<Long, LinkedList<String>> mergedText = new HashMap<>();
        HashMap<Long, Long> indexes = new HashMap<>();
        for (long i = 0; i < chunkNumber; i++) {
            mergedText.putIfAbsent(i , new LinkedList<>());
            indexes.putIfAbsent(i , 0L);
        }
        long curChunkSize = MAX_AVAILABLE_BYTES / (chunkNumber + 1);
        return DataWriter.writeMergingData(outputFile, mergedText, indexes, curChunkSize);
    }
}
