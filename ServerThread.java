import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by mathiasgronstad on 12.02.17.
 *
 * This file maintains different Threads with several clients. Each client gets its own thread
 * This class is called upon from server.java when a new socket is accepted.
 * The socket is then used in this class to create a new Thread for the client with that socket
 */

public class ServerThread extends Thread{

    //Instantiating the socket which is used on creation of the new ServerThread object
    Socket socket;
    ServerThread(Socket socket){
        this.socket=socket;
    }

    public void run(){
        try {

        //Using printwriter to handle the output stream and sending data back to the client
            PrintWriter printWriter = new PrintWriter(socket.getOutputStream(), true);

        //Reading from client using either BufferedReader og Scanner
            //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            //System.out.println(bufferedReader.readLine() + " has connected...");

            Scanner scanner = new Scanner(new InputStreamReader(socket.getInputStream()));
            String name = scanner.next();
            System.out.println(name + " has connected...");


            String input; //For storing input from used
            printWriter.println("Server echo => Connection established");

            Date date = new Date(); //Date object for handling dates


        //As long as user doesn't send "CLOSE" we keep the connection established
            while (!(input = scanner.nextLine()).equals("CLOSE")) {

                //Printing input from Client on the serverside
                if(!input.isEmpty()) System.out.print(name+": "+input+"\n");


                //If user CLOSES connection
                if(input.equalsIgnoreCase("CLOSE")){
                    printWriter.println("Server echo => Shutting down connection.");
                    System.out.println("Transmitting to " + name + ": Shutting down connection.");
                    System.out.println("\nShutting down connection with " + name);
                    socket.close();
                    break;
                }

                //Handling the different time formats «FULL», «DATE», «TIME» or «CLOSE»
                //And appends to the input String
                if(input.equalsIgnoreCase("FULL")){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM yyyy HH:mm:ss");
                    input += " -- Server time: "+simpleDateFormat.format(date);
                }

                else if(input.equalsIgnoreCase("DATE")){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MMMM yyyy");
                    input += " -- Server date: "+simpleDateFormat.format(date);
                }
                else if(input.equalsIgnoreCase("TIME")){
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                    input += " -- Server time: "+simpleDateFormat.format(date);
                }

            //Echoing (modified) input back to client
                if(!input.isEmpty()) {
                    System.out.println("Transmitting to " + name + ": " + input);
                    printWriter.println("Server echo => " + input);
                }
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
