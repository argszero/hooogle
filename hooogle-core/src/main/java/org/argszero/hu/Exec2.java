package org.argszero.hu;

import com.jcraft.jsch.*;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created with IntelliJ IDEA.
 * Operator: shaoaq
 * Date: 12-9-18
 * Time: 下午6:15
 * To change this template use File | Settings | File Templates.
 */
public class Exec2 {
    public static void main(String[] args) throws JSchException, IOException, InterruptedException {
        JSch jsch=new JSch();
        Session session=jsch.getSession("esb", "10.1.252.92", 22);
        java.util.Properties config = new java.util.Properties();
        config.put("StrictHostKeyChecking", "no");
        session.setConfig(config);
        session.setPassword("esb");
        session.connect();
        Channel channel=session.openChannel("shell");
        OutputStream os = channel.getOutputStream();
//        ((ChannelExec)channel).setCommand("ls");
//        channel.setInputStream(null);
//        ((ChannelExec)channel).setErrStream(System.err);
        InputStream in=channel.getInputStream();
        channel.connect();
//        os.write("export TERM=dumb\n".getBytes());
        os.write("top\n".getBytes());
//        os.write("ls\n".getBytes());
        os.flush();
        Thread.sleep(2000);
        IOUtils.copy(in,System.out);

//        byte[] tmp=new byte[1024];
//        while(true){
//            while(in.available()>0){
//                int i=in.read(tmp, 0, 1024);
//                if(i<0)break;
//                System.out.print(new String(tmp, 0, i));
//            }
//            if(channel.isClosed()){
//                System.out.println("exit-status: "+channel.getExitStatus());
//                break;
//            }
//            try{Thread.sleep(1000);}catch(Exception ee){}
//        }
        channel.disconnect();
        session.disconnect();
    }
}
