package org.argszero.hu;

import org.apache.commons.lang.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: shaoaq
 * Date: 12-9-20
 * Time: 下午2:53
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    private static int ack =0 ;
    private static int send = 0;
    public static void main(String[] args) {
        System.out.println(Integer.toBinaryString(((int)'A')));
        System.out.println(Integer.toBinaryString(((int)'B')));
        System.out.println(Integer.toBinaryString(((int)'C')));
        System.out.println(Integer.toBinaryString(((int)'D')));
        System.out.println(Integer.toBinaryString(((int)'E')));
        System.out.println(Integer.toBinaryString(((int)'F')));
        System.out.println(Integer.toBinaryString(((int) 'G')));

        create('A');
        finish('A', 'B', 'C');
        finish('B','D','E','F');
        finish('C');
        finish('D','G');
        finish('E');
        finish('F');
        finish('G');



//        int ack = 0;
//        System.out.println("A 发出 B和C");
//        int send = 'A' ^ 'B'^ 'C';
//        System.out.println("send to ack:"+ toStr(send));
//        ack = ack ^ send;
//        System.out.println("ack:"+ toStr(ack));
//
//        System.out.println("B 发出 D和E和F");
//        send = 'B' ^ 'D'^ 'E'^ 'F';
//        System.out.println("send to ack:"+ toStr(send));
//        ack = ack ^ send;
//        System.out.println("ack:"+ toStr(ack));
//
//        System.out.println("C完成，不发出新的");
//        send = 'C';
//        System.out.println("send to ack:"+ toStr(send));
//        ack = ack ^ send;
//        System.out.println("ack:"+ toStr(ack));
//
//        System.out.println("D完成，不发出新的");
//        send = 'D';
//        System.out.println("send to ack:"+ toStr(send));
//        ack = ack ^ send;
//        System.out.println("ack:"+ toStr(ack));
//
//        System.out.println("E完成，发出G");
//        send = 'E'^'G';
//        System.out.println("send to ack:"+ toStr(send));
//        ack = ack ^ send;
//        System.out.println("ack:"+ toStr(ack));
//
//        System.out.println("F完成，不发出新的");
//        send = 'F';
//        System.out.println("send to ack:"+ toStr(send));
//        ack = ack ^ send;
//        System.out.println("ack:"+ toStr(ack));
//
//        System.out.println("G完成，不发出新的");
//        send = 'G';
//        System.out.println("send to ack:"+ toStr(send));
//        ack = ack ^ send;
//        System.out.println("ack:"+ toStr(ack));

    }
    private static void create(char c){
        System.out.println("创建"+c);
        send =c;
        System.out.println("send:"+ toStr(send));
        ack = ack ^ send;
        System.out.println("ack:"+ toStr(ack));
    }

    private static void finish(char c,char ...cs){
        System.out.println("完成"+c+","+(cs.length==0?"不发出新的":"同时发出"+ new String(cs)));
        send =c;
        for(char ci : cs){
            send = send^ci;
        }
        System.out.println("send:"+ toStr(send));
        ack = ack ^ send;
        System.out.println("ack:"+ toStr(ack));
    }


    private static String toStr(int v){
        return  StringUtils.leftPad(Integer.toBinaryString(v),7,'0');
    }
}
