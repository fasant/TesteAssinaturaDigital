/*Classe Servidor (Bob):
    - Recebe Mensagem do Cliente (msgOriginal = inFromClient.readLine())
    - Cria Hash da Mensagem Recebida (serverHash = k.hashificando(msgOriginal))
    - Retorna Hash da Mensagem recebida para o Cliente (outToClient.writeBytes(serverHash))
*/
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
