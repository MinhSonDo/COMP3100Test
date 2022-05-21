import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client {

    
    public static void main(String[] args) {

        clientServerCommunication();

    }

    // This method saves all strings generated from GETS Capable command into a list
    // of strings
    public static List<String> saveAllServers(BufferedReader in, int numberOfServer) throws IOException {
        List<String> allCapableServer = new ArrayList<String>();
       try{ while (numberOfServer != 0) {
        String arr="";
              
              arr = in.readLine();

            allCapableServer.add(arr);

            numberOfServer--;
        }}
        catch(Exception e){
            
            e.printStackTrace();}
        return allCapableServer;
    }

    public static String serverSearch(DataOutputStream dout, BufferedReader in, String core, int numberOfCapableServers)
            throws Exception {
        String server = " ";

        List<String> allServer = new ArrayList<String>();
        String largestServer =" ";
        try {
            dout.write(("OK\n").getBytes());
            dout.flush();
            allServer = saveAllServers(in, numberOfCapableServers);
            largestServer= allServer.get(allServer.size()-1).split(" ")[4];
            for (int i = 0; i < allServer.size(); i++) {

                if (allServer.get(i).split(" ")[2].equals("inactive")
                        || allServer.get(i).split(" ")[2].equals("idle")) {

                    
                    if (Integer.parseInt(allServer.get(i).split(" ")[4]) < Integer.parseInt(core)) {

                     

                        server += allServer.get(i);

                        break;
                    }

                }

            }

            List<String> capableServers = new ArrayList<>();
            List<String>fastServers = new ArrayList<>();

            if (server.equals(" ")) {
                dout.write(("OK\n").getBytes());
                dout.flush();

                for (int i = 0; i < allServer.size(); i++) {

                    if (Integer.parseInt(allServer.get(i).split(" ")[4]) >= Integer.parseInt(core)) {

                        capableServers.add(allServer.get(i));

                    }
                }
                
                List<String> waitTime = new ArrayList<>();
                
                
                for (int i = 0; i < capableServers.size(); i++) {

                    dout.write(("EJWT " + capableServers.get(i).split(" ")[0] + " "
                            + capableServers.get(i).split(" ")[1] + "\n").getBytes());

                    dout.flush();
                    String arr =".";
                   
                     arr = in.readLine();
                    
                   if(!arr.equals("."))waitTime.add(arr);
                    System.out.println(arr);
                }
                
                String arr =".";
               
                arr = in.readLine();
               
                System.out.println(arr);
                if(!arr.equals("."))waitTime.add(arr);
                   
                
                int minimum= 0;
               if(waitTime.size()>0)  {
               minimum= Integer.parseInt(waitTime.get(0));

                 
               for (int i = 1; i < waitTime.size(); i++) {

                   if(minimum>Integer.parseInt(waitTime.get(i))){

                     minimum = Integer.parseInt(waitTime.get(i));
                   }

               }
            }

               List<Integer>indexOfTheLowest = new ArrayList<>();

                  //find all servers with this wait time
               for(int i=0; i<waitTime.size();i++){

                     if(minimum== Integer.parseInt(waitTime.get(i))){

                        indexOfTheLowest.add(i);

                     }
                                       
               } 



            

                // all the servers with the minimum wait time
            for( int i =0; i<indexOfTheLowest.size();i++){


                  fastServers.add(capableServers.get(indexOfTheLowest.get(i)));



            }



             







               if(fastServers.size()>0)
               server += fastServers.get(0);            
              


            }

        } catch (Exception e) {
           e.printStackTrace();
        }

        
        if (server.equals(" ") || server.contains(largestServer)){
            server += allServer.get(0);
            
        }


        

        return server;

    }

    // This method is in charge of sending initial messages like "HELO" and "AUTH"


    // This method closes data output stream, buffered reader and socket.
    public static void closeConnection(DataOutputStream dout, BufferedReader in, Socket s) {

        try {
            dout.close();
            in.close();
            s.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    // This method combines all other helper functions to communicate and schedule
    // jobs from ds-server
    public static void clientServerCommunication() {
        try {
            Socket s = new Socket("localhost", 50000);
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            initialMessage(dout, in);
            requestHandler(dout, in);
            closeConnection(dout, in, s);

        } catch (Exception e) {

        }

    }

    // This method is responsible for communicating with ds-server after the
    // intitial messages.
    // It also contains the LRR algorithm.
    public static void requestHandler(DataOutputStream dout, BufferedReader in) {

        String JOBN = "";
        int serverId = 0;
        int largestServerCount = 0;
        String largestType = "";
        // Boolean flag = false;

        while (!(JOBN == null) && !(JOBN.contains("NONE"))) {
            try {
                dout.write(("REDY\n").getBytes());
                dout.flush();
                
                JOBN = in.readLine();

                if (!(JOBN == null) && JOBN.contains("JOBN")) {

                    String JOBNArray[] = JOBN.split(" ");
                    String core = "";
                    String memory = "";
                    String disk = "";
                    if (JOBNArray.length > 6) {
                        core = JOBNArray[4];
                        memory = JOBNArray[5];
                        disk = JOBNArray[6];
                    }
                    // execute this GETS code only once
                    if (!core.isEmpty()) {
                        dout.write(("GETS Capable " + core + " " + memory + " " + disk + "\n").getBytes());
                        dout.flush();
                    }
                    // execute this GETS part only once
                    
                    String dataFromGets="";
                  
                     dataFromGets = in.readLine();
                    
                    if (dataFromGets.contains("JOBN")){

                        dataFromGets = in.readLine();

                    }
                   
                    dout.flush();

                    String dataFromGetsInArrayForm[] = dataFromGets.split(" ");
                    int dataCount = Integer.parseInt(dataFromGetsInArrayForm[1]);
                    int largestCore = 0;
                    int compareCore = 0;
                    String largestServerType = "";

                    String largestServerType2 = serverSearch(dout, in, core, dataCount);
                    // split the string to extract the number of the largest servers and the largest
                    // server type

                    largestServerCount = Integer.parseInt(largestServerType2.split(" ")[2]);

                    largestType = largestServerType2.split(" ")[1];

                    String temp[] = JOBN.split(" ");

                    if (JOBN.length() > 0) {

                        dout.write(("OK\n").getBytes());
                        dout.flush();
                       
                        in.readLine();
                        // GETS command has done executing so set flag to true

                        dout.write(
                                ("SCHD " + temp[2] + " " + largestType + " " + largestServerCount + "\n").getBytes());
                        dout.flush();

                    }
                    
                    in.readLine();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        try {
            dout.write(("QUIT\n").getBytes());
            dout.flush();

        }

        catch (Exception e) {

        }

    }







    public static void initialMessage(DataOutputStream dout, BufferedReader in) {

        try {
            dout.write(("HELO\n").getBytes());
            dout.flush();
            in.readLine();
            dout.write(("AUTH minh\n").getBytes());
            dout.flush();
            in.readLine();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
