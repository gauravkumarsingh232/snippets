package com.xiffox.snippets.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CSVDemo {
    public static void main(String[] args) {
        read();
        write();
    }

    private static void read() {
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

    private static void write() {
        String path = "/path/example.csv";

        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path));
             CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader("ID", "Name", "Designation", "Company"));
        ) {
            csvPrinter.printRecord("1", "Name1", "Designation1", "Company1");
            csvPrinter.printRecord("2", "Name2", "Designation2", "Company2");
            csvPrinter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
