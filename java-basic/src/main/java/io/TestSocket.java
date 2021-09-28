package io;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author hike97
 * @create 2021-08-31 20:09
 * @desc
 **/
public class TestSocket {
    public static void main (String[] args) throws IOException {
        ServerSocket server = new ServerSocket (8090);
        System.out.println ("step1: new ServerSocket(8090)");
        while (true) {
            Socket client = server.accept ();
            System.out.println ("step1:client\t" + client.getPort ());
            new Thread (() -> {

                try {
                    InputStream in = client.getInputStream ();
                    BufferedReader reader = new BufferedReader (new InputStreamReader (in));
                    System.out.println (reader.readLine ());
                } catch (IOException e) {
                    e.printStackTrace ();
                }
            }).start ();
        }
    }

    //2693 write(1, "step1: new ServerSocket(8090)", 29) = 29
}
