package bobalice;

import java.io.*;
import java.net.*;

public class TCPServer {

    public static void main(String argv[]) throws Exception {
        String msgOriginal;
        String serverHash;
        Kriptonita k = new Kriptonita();
        ServerSocket welcomeSocket = new ServerSocket(15000);

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());
            msgOriginal = inFromClient.readLine();
            serverHash = k.hashificando(msgOriginal) + '\n';
            outToClient.writeBytes(serverHash);

        }

    }
}
