/*
Classe Cliente (Alice): 
    - Envia Mensagem para servidor (msgOriginal)
    - Recebe Hash da Mensagem recebida pelo Servidor (serverHash = inFromServer.readLine())
    - Compara Hash da Mensagem Original (msgHash) com o Hash da Mensagem recebida pelo servidor (serverHash)
*/
package bobalice;

import java.io.*;
import java.net.*;

public class TCPClient {

    public static void main(String argv[]) throws Exception {
        String msgOriginal;
        Kriptonita k = new Kriptonita();
        String msgHash;
        String serverHash;
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        Socket clientSocket = new Socket("127.0.0.1", 15000);
        DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());

        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        msgOriginal = inFromUser.readLine();
        outToServer.writeBytes(msgOriginal + '\n');
        serverHash = inFromServer.readLine();
        msgHash = k.hashificando(msgOriginal);
        if(msgHash.equals(serverHash))
            System.out.println("IGUAIS");
        else
            System.out.println("DIFERENTES");
        clientSocket.close();
    }

}
