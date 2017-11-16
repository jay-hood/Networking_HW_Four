import java.io.*;
import java.net.*;  
import java.util.*;    

public class TcpRouter1
{  
   private static ServerSocket serverSocket; 
   private static InetAddress host;
   private static final int listener_PORT = 1240;
   private static final int Router_Two_PORT = 1241;
   private static final int Router_Three_PORT = 1242;

   private static Socket link2 = null;
   private static Socket link3 = null;

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
         serverSocket = new ServerSocket(listener_PORT);  //Step 1.
         link2 = new Socket(host, Router_Two_PORT);
         link3 = new Socket(host, Router_Three_PORT);
         
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

         //Get read_from_sender from and send write_to_sender to Sender control functions
         Scanner read_from_sender = new Scanner(link.getInputStream());
         PrintWriter write_to_sender = new PrintWriter(link.getOutputStream(),true);
         String message = read_from_sender.nextLine();
           
           //Get read_from_sender from and send write_to_sender to Receiver
         Scanner read_from_router_two = new Scanner(link2.getInputStream());
         PrintWriter write_to_router_two = new PrintWriter(link2.getOutputStream(),true);
         Scanner read_from_router_three = new Scanner(link3.getInputStream());
         PrintWriter write_to_router_three = new PrintWriter(link3.getOutputStream(),true);
         String message_from_two;
         String message_from_three;

         while (!message.equals("***CLOSE***")){
             Random randomGenerator = new Random();
             int randomInt = randomGenerator.nextInt(2);
             System.out.println("Generated random number for the packet is: "+randomInt);
             System.out.println("message from sender " + message);
             //Send to router 2
             if(randomInt==0) {

                 System.out.println("Sending packet to router 2");
                 write_to_router_two.println(message);
                 //String str = read_from_router_two.nextLine();
                 //System.out.println("message from router two: " + str);
             }
             //Send to router 3
             else {
                 System.out.println("Sending packet to router 3");
                 write_to_router_three.println(message);
                 //String str_from_three = read_from_router_three.nextLine();
                 //System.out.println("message from router three: " + str_from_three);
                 //write_to_sender.println(str);
                 //write_to_sender.println(str_from_three);//Send acknowledgement to Sender
             }

             message = read_from_sender.nextLine();
             //message_from_two = read_from_router_two.nextLine();
             //message_from_three = read_from_router_three.nextLine();
             //write_to_sender.println(message_from_two);
             //write_to_sender.print(message_from_three);


         }
         
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
             link2.close();
             link3.close();
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
  
