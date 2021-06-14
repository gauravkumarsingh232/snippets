package com.xiffox.snippets.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.StringUtils;

import java.io.*;

public class CSVDemo {
    public static void main(String[] args) {

        File file = new File("example.csv");
        try {
            Reader reader = new InputStreamReader(new FileInputStream(file));
            Iterable<CSVRecord> templateCsvRecords = CSVFormat.RFC4180.withFirstRecordAsHeader().withSkipHeaderRecord(true).parse(reader);
            for (CSVRecord csvRecord : templateCsvRecords) {
                System.out.println(csvRecord);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
