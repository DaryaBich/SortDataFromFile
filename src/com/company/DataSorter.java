package com.company;

public class DataSorter {
    public static String tmpDirectory = "resources\\tmp";
    public int mainSorter(String inputFile, String outputFileName) {

        long optimalNumberOfStrings = DataWorker.getOptimalNumberOfStrings(inputFile); // 1836373
        if (optimalNumberOfStrings == -1) {
            return -1;
        }
        if (!FileWorker.createTmpDirectories(tmpDirectory)) {
            return -1;
        }
        long chunkNumber = DataWorker.splitData(optimalNumberOfStrings, tmpDirectory, inputFile);
        if (chunkNumber == -1) {
            return -1;
        }
        boolean res = DataWorker.mergeData(chunkNumber, outputFileName);
        if(!res){
            return -1;
        }

        return 0;
    }


}
