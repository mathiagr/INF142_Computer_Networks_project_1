import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Client_2 {

    public static void main(String[] args) throws UnknownHostException, IOException {

        String name = "Client_1"; //Name of the client
        Socket socket = new Socket("127.0.0.1", 1234); //Creating a new socket with local IP and PORT of the server

        //Using printwriter to handle output from client to server
        PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);
        printWriter.println(name); //Sending our name to the server


        //Ask for input from server using BufferedReader of Scanner
        //BufferedReader serverEcho = new BufferedReader(new InputStreamReader((socket.getInputStream())));
        Scanner serverEcho = new Scanner(new InputStreamReader((socket.getInputStream())));

        //Same as above but asking user for input
        //BufferedReader bufferedReader = new java.io.BufferedReader(new InputStreamReader(System.in));
        Scanner scanner = new Scanner(new InputStreamReader(System.in));


        String readerInput; //Variable for storing input from client
        String echo; //Variable for storing input from server to client
        boolean running = true; //If the program keeps prompting for input in a while loop

        //Getting initial message from server (verification of established connection)
        echo = serverEcho.nextLine();
        System.out.println(echo);

        //Loop for prompting user input as long as input is not "CLOSE"
        while (running) {

            //Take input from Clients terminal; «FULL», «DATE», «TIME» or «CLOSE»
            System.out.println("Input: ");
            readerInput = scanner.nextLine();


            //Transmits to server as an output stream
            System.out.println("Transmitting to server: " + readerInput);
            printWriter.println(readerInput);

            //Takes input from server and prints it out
            if(!(readerInput).equalsIgnoreCase("CLOSE")){
                echo = serverEcho.nextLine();
                System.out.println(echo);
            }
            //If user CLOSES the connection we break the loop of asking for commands
            if((readerInput).equalsIgnoreCase("CLOSE")){
                echo = serverEcho.nextLine();
                System.out.println(echo);
                running=false;}
        }
    }
}
