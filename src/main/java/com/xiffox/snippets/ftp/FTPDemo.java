package com.xiffox.snippets.ftp;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FTPDemo {

    private String filePath;
    private String ftpHost = "host";
    private Integer ftpPort = 22;
    private String ftpUserName = "user";
    private String ftpPassword = "pass";

    public FTPDemo(String filePath) {
        this.filePath = filePath;
    }

    public static void main(String[] args) {
        FTPDemo demo = new FTPDemo("testpath");

        FTPClient ftpClient = new FTPClient();
        File f = new File(demo.filePath);
        try (FileInputStream fis = new FileInputStream(f)) {
            if (demo.ftpPort != null) {
                ftpClient.connect(demo.ftpHost, demo.ftpPort);
            } else {
                ftpClient.connect(demo.ftpHost);
            }
            ftpClient.login(demo.ftpUserName, demo.ftpPassword);
            ftpClient.storeFile(f.getName(), fis);
            ftpClient.logout();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ftpClient.disconnect();
            } catch (IOException e) {
                System.out.println("uploadFileUsingFTP :: Error occured while disconnecting from FTP server after uploading billing file using FTP " + e);
            }
        }
    }
}
