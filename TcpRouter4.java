import java.io.*;
import java.net.*;
import java.util.*;

public class TcpRouter4
{
    private static ServerSocket serverSocket;
    private static ServerSocket serverSocket_TWO;
    private static InetAddress host;
    private static final int listener_PORT_ONE = 1243;
    private static final int listener_PORT_TWO = 1244;
    private static final int receiver_PORT = 1245;

    //Might need to just make multiple sockets nad have 3 and 2 connect to a specific one.
    private static Socket link2 = null;


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
            serverSocket = new ServerSocket(listener_PORT_ONE);  //Step 1.
            serverSocket_TWO = new ServerSocket(listener_PORT_TWO);
            link2 = new Socket(host, receiver_PORT);


        }
        catch(IOException ioEx)
        {
            System.out.println(
                    "Unable to attach to port for router!");
            ioEx.printStackTrace();
            System.exit(1);
        }

        handleClient();

    }
    private static String handleClient()
    {
        Socket out_link = null;
        Socket out_link2 = null;

        try {
            out_link = serverSocket.accept();
            out_link2 = serverSocket_TWO.accept();

            //Get read_from_sender from and send write_to_sender to Sender control functions
            Scanner read_from_sender = new Scanner(out_link.getInputStream());
            Scanner read_from_sender_two = new Scanner(out_link2.getInputStream());
            //PrintWriter write_to_sender = new PrintWriter(out_link.getOutputStream(),true);
            String message = null;
            String message2 = null;
            String message3 = null;

            message = read_from_sender.nextLine();
            message2 = read_from_sender_two.nextLine();


            //Get read_from_sender from and send write_to_sender to Receiver
            Scanner read_from_receiver = new Scanner(link2.getInputStream());


            PrintWriter write_to_receiver = new PrintWriter(link2.getOutputStream(),true);
            //Scanner read_from_router_three = new Scanner(link3.getInputStream());
            PrintWriter write_to_router_three = new PrintWriter(out_link2.getOutputStream(),true);
            PrintWriter write_to_router_two = new PrintWriter(out_link.getOutputStream(), true);
            //write_to_receiver.println(message);
            //write_to_receiver.println(message2);


            while (/*!message.equals("exit")*/true){

                //write_to_receiver.println(message);

                //write_to_receiver.println(message2);

                System.out.println("Message2 to receiver: " + message2);
                System.out.println("Message to receiver: " + message);

                /*
                if(randomInt==0) {

                    write_to_receiver.println(message);
                    //String str = read_from_receiver.nextLine();
                    //System.out.println("message from router two: " + str);
                }
                //Send to router 3
                else {
                    write_to_router_three.println(message);
                    //String str_from_three = read_from_router_three.nextLine();
                    //System.out.println("message from router three: " + str_from_three);
                    //write_to_sender.println(str);
                    //write_to_sender.println(str_from_three);//Send acknowledgement to Sender
                }
                */
                //was originally just: message = read_from_sender.nextLine();
                write_to_receiver.println(message);
                message = read_from_sender.nextLine();
                System.out.println("Ack from receiver: " + message3);
                message3 = read_from_receiver.nextLine();
                System.out.println("Ack from receiver: " + message3);
                Random randomGenerator = new Random();
                int randomInt = randomGenerator.nextInt(2);
                if(randomInt == 0){
                    write_to_router_two.println(message3);
                }
                else{
                    write_to_router_three.println(message3);
                }

                //write_to_router_two.println(message);

                write_to_receiver.println(message2);
                message2 = read_from_sender_two.nextLine();

                //message2 = read_from_receiver.nextLine();
                //write_to_router_three.println(message);


                   //message2 = read_from_sender_two.nextLine();

            }

            /*
            while (read_from_sender_two.hasNextLine()){

                //write_to_receiver.println(message);
                write_to_receiver.println(message2);
                //System.out.println("Message to receiver: " + message);
                System.out.println("Message2 to receiver: " + message2);

                if(randomInt==0) {

                    write_to_receiver.println(message);
                    //String str = read_from_receiver.nextLine();
                    //System.out.println("message from router two: " + str);
                }
                //Send to router 3
                else {
                    write_to_router_three.println(message);
                    //String str_from_three = read_from_router_three.nextLine();
                    //System.out.println("message from router three: " + str_from_three);
                    //write_to_sender.println(str);
                    //write_to_sender.println(str_from_three);//Send acknowledgement to Sender
                }

                //was originally just: message = read_from_sender.nextLine();

                //message = read_from_sender.nextLine();


                message2 = read_from_sender_two.nextLine();

            }
            */

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
                out_link.close();

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

