package com.xiffox.snippets.jpos;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.packager.GenericPackager;

import java.io.FileInputStream;
import java.io.InputStream;

public class ISOMsgParser {
    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Usage requires 2 argument!!!\n" +
                    "sample: java -jar packet-parser.jar <packager_xml> <hex_dump>\n");
        } else {
            String packager = args[0];
            String hex = args[1];
            try {
                System.out.println(">>>>>> Parsing input hexdump <<<<<<");
                ISOMsg isoMsg = ISOMsgParser.parseISOMessage(packager, hex);
                ISOMsgParser.printISOMessage(isoMsg);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private static ISOMsg parseISOMessage(String packagerPath, String hex) throws Exception {

        byte[] hex2byte = ISOUtil.hex2byte(hex);
        String message = ISOUtil.hexdump(hex2byte);
        System.out.printf("Message = %s%n", message);

        try {
            InputStream is = new FileInputStream(packagerPath);
            GenericPackager packager = new GenericPackager(is);

            ISOMsg isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);
            isoMsg.unpack(hex2byte);
            System.out.println("========================");
            for (int k=0; k<=isoMsg.getMaxField();k++) {
                if (isoMsg.hasField(k)) {
                    System.out.println("    Field-"+k+" : "+isoMsg.getString(k));
                }
            }
            System.out.println(isoMsg.getMTI() + ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            System.out.println();
            System.out.println("------ISOUtil.hexString---------");
            System.out.println(ISOUtil.hexString(hex2byte));
            System.out.println("------ISOUtil.hexdump---------");
            System.out.println(ISOUtil.hexdump(hex2byte));
            System.out.println("-----------------------");
            return isoMsg;
        } catch (ISOException e) {
            throw new Exception(e);
        }
    }

    private static void printISOMessage(ISOMsg isoMsg) {
        System.out.println("-----------------isoMsg.dump------------------");
        isoMsg.dump(System.out, "");
        System.out.println("-----------------------");
    }

}
