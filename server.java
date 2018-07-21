import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 The server has one method called runServer
 This method listens to new sockets and accepts them if the port is right
 When a socket is accepted we start a new SeverThread object for that socket.
 This way we can maintain several threads connected to the server simultaneously
 */
public class server {

    //Setting the port of the server
    public static final int PORT = 1234;

    public static void main(String[] args) throws IOException {

       new server().runServer();
    }
    public void runServer() throws IOException{

        //The port number must be the same for client and server
        ServerSocket serverSocket = new ServerSocket(PORT);

        System.out.println("Server running...");
        while (true){
            //Listens and accepts the incoming socket to serverSocket
            Socket socket = serverSocket.accept();

            //Start a thread with the client which starts the connection
            new ServerThread(socket).start();
        }
    }
}
