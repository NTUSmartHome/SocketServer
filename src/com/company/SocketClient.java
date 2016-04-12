package com.company;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.io.BufferedOutputStream;
import java.util.Scanner;

public class SocketClient {
    private String address = "127.0.0.1";// �s�u��ip
    private int port = 8765;// �s�u��port

    public SocketClient() {

        Socket client = new Socket();
        InetSocketAddress isa = new InetSocketAddress(this.address, this.port);
        Scanner sc = new Scanner(System.in);
        try {
            client.connect(isa, 10000);
            BufferedOutputStream out = new BufferedOutputStream(client
                    .getOutputStream());
            // �e�X�r��
            String ss = sc.next();
            out.write(ss.getBytes());
            out.flush();
            out.close();
            out = null;
            client.close();
            client = null;

        } catch (java.io.IOException e) {
            System.out.println("Socket�s�u�����D !");
            System.out.println("IOException :" + e.toString());
        }
    }

    public static void main(String args[]) {
        new SocketClient();
    }
}