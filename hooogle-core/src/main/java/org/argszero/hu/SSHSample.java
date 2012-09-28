package org.argszero.hu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Logger;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.UserInfo;

public class SSHSample {
    private static class MyUserInfo implements UserInfo {
        private String passwd;

        MyUserInfo(String passwd) {
            this.passwd = passwd;
        }

        public String getPassword() {
            return passwd;
        }

        public boolean promptYesNo(String str) {
            return true;
        }

        public String getPassphrase() {
            return null;
        }

        public boolean promptPassphrase(String message) {
            return true;
        }

        public boolean promptPassword(String message) {
            return true;
        }

        public void showMessage(String message) {
        }
    }


    public static void main(String[] args) {


        final String hostName = "10.1.252.92";
        final String userName = "esb";
        final String password = "esb";
//        final String channelTye = "exec";
        final String channelTye = "shell";

        JSch.setLogger(new Logger() {

            public boolean isEnabled(int level) {
                return true;
            }

            public void log(int level, String message) {
                System.out.println(message);
            }
        });

        final String command = "top";
        final JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;
        boolean exec = channelTye.equals("exec");
        try {
            session = jsch.getSession(userName, hostName, 22);
            session.setUserInfo(new MyUserInfo(password));
            session.setDaemonThread(false);
            session.connect();
            channel = session.openChannel(channelTye);
            OutputStream os = channel.getOutputStream();
            BufferedReader lineReader = new BufferedReader(new
                    InputStreamReader(channel.getInputStream()));
            if (exec) {
                ((ChannelExec) channel).setErrStream(System.err);
                ((ChannelExec) channel).setCommand(command);
            }
            channel.connect();
            if (!exec) {
                os.write("PS1=\"MY_PROMPT>\"".getBytes());
                os.write("\n".getBytes());
                os.write("TERM=ansi".getBytes());
                os.write("\n".getBytes());
                os.write(command.getBytes());
                os.write("\n".getBytes());
                os.flush();
            }
            Thread.sleep(2000);
            while (lineReader.ready()) {
                System.out.println(lineReader.readLine());
                if (!lineReader.ready()) {
                    Thread.sleep(2000);
                }
            }
            if (channel.isClosed()) {
                System.out.println("exit-status: " + channel.getExitStatus());
            }
            channel.disconnect();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            if (session != null) {
                session.disconnect();
            }
        }


    }
}

