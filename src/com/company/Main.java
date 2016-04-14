package com.company;

import java.io.File;
import java.io.FileWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Main extends java.lang.Thread {

    private boolean OutServer = false;
    private ServerSocket server;
    DateFormat sdf = new SimpleDateFormat("MM_dd_HH_mm");
    DateFormat sdf2 = new SimpleDateFormat("HH,mm,ss");
    private final int ServerPort = 6789;// 要監控的port
    FileWriter fw;
    private File fp = new File("acc.txt");
    public Main() {
        try {
            fw = new FileWriter(sdf.format(System.currentTimeMillis()) +".txt",false);
            server = new ServerSocket(ServerPort);




        } catch (java.io.IOException e) {
            System.out.println("Socket啟動有問題 !");
            System.out.println("IOException :" + e.toString());
        }
    }

    public void run() {
        Socket socket;
        java.io.BufferedInputStream in;

        System.out.println("伺服器已啟動 !");
        while (!OutServer) {
            socket = null;
            try {
                synchronized (server) {
                    socket = server.accept();
                }
                //System.out.println("取得連線 : InetAddress = "
                        //+ socket.getInetAddress());
                // TimeOut時間
                socket.setSoTimeout(15000);

                in = new java.io.BufferedInputStream(socket.getInputStream());
                byte[] b = new byte[1024];
                String data = "";
                int length;
                while ((length = in.read(b)) > 0)// <=0的話就是結束了
                {
                    data += new String(b, 0, length);
                }

                System.out.println("我取得的值:" + data);
               // System.out.println("我取得的值:" + data.substring(0, 10));

                if(data.contains("Label")){
                    fw.write("T:" + sdf2.format(System.currentTimeMillis()) + "\r\n");
                    fw.write(data);
                    fw.write("\r\n");
                    fw.flush();
                }

                in.close();
                in = null;
                socket.close();


            } catch (java.io.IOException e) {
                System.out.println("Socket連線有問題 !");
                System.out.println("IOException :" + e.toString());
            }

        }
    }

    public static void main(String args[]) {
        (new Main()).start();
    }

}