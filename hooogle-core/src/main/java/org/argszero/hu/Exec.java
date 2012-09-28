package org.argszero.hu;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.common.IOUtils;
import net.schmizz.sshj.connection.channel.direct.Session;
import net.schmizz.sshj.connection.channel.direct.Session.Command;
import net.schmizz.sshj.transport.verification.HostKeyVerifier;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;

import java.io.IOException;
import java.security.PublicKey;
import java.util.concurrent.TimeUnit;

/** This examples demonstrates how a remote command can be executed. */
public class Exec {

    public static void main(String... args)
            throws IOException {
        final SSHClient ssh = new SSHClient();
        //忽略HostKeyVerifier认证，如果不忽略，需要将提前添加key，比如:ssh.addHostKeyVerifier("b6:ac:91:37:43:34:9c:19:d2:41:0b:35:fa:13:7b:13");
        ssh.addHostKeyVerifier(new PromiscuousVerifier());
        ssh.connect("10.1.252.92");
        try {
            ssh.authPassword("esb","esb");
            Session session = ssh.startSession();
            try {
                Command cmd = session.exec("ls");
                System.out.println(IOUtils.readFully(cmd.getInputStream()).toString());
                System.out.println("\n** exit status: " + cmd.getExitStatus());
            } finally {
                session.close();
            }
            session = ssh.startSession();
            try {
                Command cmd = session.exec("top -b");
                cmd.join(5,TimeUnit.SECONDS);
                System.out.println(IOUtils.readFully(cmd.getErrorStream()).toString());
                cmd.join(5,TimeUnit.SECONDS);
                System.out.println("\n** exit status: " + cmd.getExitStatus());
            } finally {
                session.close();
            }
            session = ssh.startSession();
            try {
                Command cmd = session.exec("pwd");
                System.out.println(IOUtils.readFully(cmd.getInputStream()).toString());
                System.out.println("\n** exit status: " + cmd.getExitStatus());
            } finally {
                session.close();
            }
        } finally {
            ssh.disconnect();
        }
    }

}