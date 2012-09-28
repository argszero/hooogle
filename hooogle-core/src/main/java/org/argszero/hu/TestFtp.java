package org.argszero.hu;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: shaoaq
 * Date: 12-9-21
 * Time: 下午6:06
 * To change this template use File | Settings | File Templates.
 */
public class TestFtp {
    public static void main(String[] args) throws IOException {
        String s_url = "10.1.252.40";
        String uname = "ftpbea";
        String password = "ftpbea";

        try{
            //连接
            FTPClient ftpClient = new FTPClient();
            ftpClient.connect(s_url);
            ftpClient.enterLocalPassiveMode();
            ftpClient.login(uname,password);
            //检测连接是否成功
            int reply = ftpClient.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)) {
                System.err.println("FTP server refused connection.");
                System.exit(1);
            }

            ftpClient.listFiles();
//            boolean  b =ftpClient.retrieveFile("ba96b3d4-fe2b-48db-9017-d0a86096907b.xls", new FileOutputStream(new File("D:\\","a.xsl")));
//            System.out.println(b);
        }catch(Exception ex){
            ex.printStackTrace();
            //关闭
        }
    }
}
