package com.company;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class DataReader {
    public static long readAndSortData(long numberOfStrings, String tmpDirName, String fileName) {
        long chunkNumber = 0;
        String inputString;
        String cur;
        long bufNumberOfStrings = 0;
        LinkedList<String> textList = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            while ((inputString = reader.readLine()) != null) {
                bufNumberOfStrings++;
                textList.add(inputString);
                if (bufNumberOfStrings >= numberOfStrings) {
                    if (DataWriter.sortAndWriteData(textList, chunkNumber, tmpDirName)){
                        textList.clear();
                        bufNumberOfStrings = 0;
                        chunkNumber++;
                    }else {
                        return -1;
                    }
                }
            }
            if (!textList.isEmpty()) {
                DataWriter.sortAndWriteData(textList, chunkNumber, tmpDirName);
                chunkNumber++;
            }
            return chunkNumber;
        } catch (IOException e) {
            System.out.println("Не удалось прочитать из файла " + tmpDirName);
            e.printStackTrace();
            return -1;
        }
    }

    public static HashMap<Long, LinkedList<String>> fill(long chunkNumber, HashMap<Long, Long> indexes,
                                                         HashMap<Long, LinkedList<String>> mergedText,
                                                         String tmpDir, long chunkSize) {
        try (BufferedReader reader = new BufferedReader(new FileReader(tmpDir + "\\" + chunkNumber +".txt"))) {
            long count = 0;
            long sumSize = 0;
            long maxSize = 0;
            List<String> forFill = mergedText.get(chunkNumber);
            String curString;
            while ((curString = reader.readLine()) != null) {
                if (count < indexes.get(chunkNumber)) {
                    count++;
                    continue;
                }
                mergedText.get(chunkNumber).add(curString);
                count++;
                sumSize += curString.getBytes().length;
                long curStrBytesLength = curString.getBytes().length;
                if (curStrBytesLength > maxSize) {
                    maxSize = curStrBytesLength;
                }
                if (sumSize + maxSize > chunkSize) {
                    break;
                }
            }
            indexes.put(chunkNumber, count);
            if (curString == null && forFill.isEmpty()) {
                indexes.remove(chunkNumber);
            }
            return mergedText;
        } catch (IOException e) {
            System.out.println("Возникли проблемы с директорией " + tmpDir);
            e.printStackTrace();
            return null;
        }
    }

}
