package com.xiffox.snippets.sftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.File;
import java.io.FileInputStream;

public class SFTPDemo {

    private String filePath;
    private String sftpHost = "host";
    private Integer sftpPort = 22;
    private String sftpUserName = "user";
    private String sftpPassword = "pass";
    private String schKey = "";
    private String schValue = "";

    public SFTPDemo(String filePath) {
        this.filePath = filePath;
    }

    public static void main(String[] args) {
        SFTPDemo demo = new SFTPDemo("testpath");

        Session session = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        File f = new File(demo.filePath);
        try (FileInputStream fis = new FileInputStream(f)) {
            JSch jsch = new JSch();
            session = jsch.getSession(demo.sftpUserName, demo.sftpHost, demo.sftpPort);
            session.setPassword(demo.sftpPassword);
            session.setConfig(demo.schKey, demo.schValue);
            session.setConfig("PreferredAuthentications", "publickey,keyboard-interactive,password");
            session.connect();
            channel = session.openChannel("SFTP");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(demo.filePath);
            channelSftp.put(fis, f.getName());
        } catch (Exception e) {
            System.out.println("Error occured while uploading the file using SFTP" + e);
        } finally {
            if (session != null) {
                session.disconnect();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }
    }
}
