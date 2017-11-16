import java.io.*;
import java.net.*;
import java.util.*;

public class TcpRouter2
{
    private static ServerSocket serverSocket;
    private static InetAddress host;
    private static final int listener_PORT = 1241;
    private static final int Router_Four_PORT = 1243;

    private static Socket link4 = null;

    public static void main(String[] args)
    {
        System.out.println("Opening port");
        {
            try
            {
                host = InetAddress.getLocalHost();
                //System.out.println("Enter TcpReceiver IP Address:");
//        	  Scanner readIP = new Scanner(System.in);
//        	  host = readIP.nextLine();
            }
            catch(Exception uhEx)
            {
                System.out.println("Host ID not found!");
                System.exit(1);
            }

        }
        try
        {
            serverSocket = new ServerSocket(listener_PORT);  //Step 1
            link4 = new Socket(host, Router_Four_PORT);

        }
        catch(IOException ioEx)
        {
            System.out.println(
                    "Unable to attach to port for router!");
            System.exit(1);
        }

        handleClient();

    }
    private static String handleClient()
    {
        Socket link = null;

        try {
            link = serverSocket.accept();
            String message = null;
            //Get read_from_router_one from and send write_to_router_one to Sender control functions
            Scanner read_from_router_one = new Scanner(link.getInputStream());
            PrintWriter write_to_router_one = new PrintWriter(link.getOutputStream(),true);
            Scanner read_from_router_four = new Scanner(link4.getInputStream());
            String str_from_four =  null;
            try{
                message = read_from_router_one.nextLine();
                PrintWriter write_to_router_four = new PrintWriter(link4.getOutputStream(),true);

                while (!message.equals("exit")){

                    Random randomGenerator = new Random();
                    int randomInt = randomGenerator.nextInt(100);
                    System.out.println("Generated random number for the packet is: "+randomInt);

                    System.out.println("Received: " + message);
                    write_to_router_four.println(message);
                    message = read_from_router_one.nextLine();

                        //str_from_four = read_from_router_four.nextLine();
                        //System.out.println("message from router four: " + str_from_four);


                    //write_to_router_one.println(str_from_four);//Send acknowledgement to Sender


                }
            }
            catch(NoSuchElementException iex){
                System.out.println("Couldn't connect to router one.");
                iex.printStackTrace();
            }


            //Get read_from_router_one from and send write_to_router_one to Receiver

            //Scanner read_from_router_four = new Scanner(link4.getInputStream());


        }

        catch(IOException ioEx)
        {
            ioEx.printStackTrace();
        }
        finally
        {
            try
            {
                System.out.println(
                        "\n* Closing connections (Router side)*");
                link.close();
                link4.close();
            }
            catch(IOException ioEx)
            {
                System.out.println(
                        "Unable to disconnect!");
                System.exit(1);
            }
        }
        return null;
    }

}
  
