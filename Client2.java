import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client2 {
    public static void main(String[] args) {

        clientServerCommunication();

    }

    // This method saves all strings generated from GETS Capable command into a list
    // of strings
    public static List<String> saveAllServers(BufferedReader in, int numberOfServer) throws IOException {
        List<String> allCapableServer = new ArrayList<String>();
        while (numberOfServer != 0) {
            allCapableServer.add(in.readLine());

            numberOfServer--;
        }
        return allCapableServer;
    }

    // This method compares the cores of all capable servers. The server with the
    // highest number of cores will saved into a string.
    // In addition, this method will count the number of the largest servers.
    // This method will return a string containing the largest server type and the
    // number of the largest servers
    public static String largestServerSearch(DataOutputStream dout, BufferedReader in, int saveCount,
            int numberOfCapableServers, int largestCore, String largestType) {

        int countLargest = 0;
        List<String> temp = new ArrayList<String>();

        try {
            dout.write(("OK\n").getBytes());

            temp = saveAllServers(in, numberOfCapableServers);

            for (int i = 0; i < numberOfCapableServers; i++) {

                saveCount = 0;
                if (temp.size() > 0) {
                    if (temp.get(i) != null && temp.get(i).length() > 4)
                        saveCount = Integer.parseInt(temp.get(i).split(" ")[4]);
                }

                if (saveCount >= largestCore) {
                    largestCore = saveCount;

                }

            }

            for (int i = 0; i < numberOfCapableServers; i++) {

                if (largestCore == Integer.parseInt(temp.get(i).split(" ")[4])) {
                    largestType = temp.get(i).split(" ")[0];
                    // break;

                }

            }

            for (int i = 0; i < numberOfCapableServers; i++) {
                if (temp.get(i).length() > 4) {
                    if (largestType.equalsIgnoreCase(temp.get(i).split(" ")[0])) {

                        countLargest++;

                    }
                }

            }
            // ./ds-server -c ds-S1-config01--wk6.xml -v brief -n > ds-test-config10-log.txt
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return largestType + " " + countLargest;
    }

    // This method is in charge of sending initial messages like "HELO" and "AUTH"
    public static void initialMessage(DataOutputStream dout, BufferedReader in) {

        try {
            dout.write(("HELO\n").getBytes());
            dout.flush();
            String OK = in.readLine();
            System.out.println(OK);
            dout.write(("AUTH minh\n").getBytes());
            dout.flush();
            String OK2 = in.readLine();
            System.out.println(OK2);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

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

            requestHandler(dout, in, 0);

            closeConnection(dout, in, s);

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    // This method is responsible for communicating with ds-server after the
    // intitial messages.
    // It also contains the LRR algorithm.
    public static void requestHandler(DataOutputStream dout, BufferedReader in, int counter2) {

        int y = counter2;

        while (true) {
            try {
                dout.write(("REDY\n").getBytes());
                dout.flush();
                String JOBN = in.readLine();
                System.out.println(JOBN);
                if (JOBN.contains("NONE")) {
                    dout.write(("QUIT\n").getBytes());
                    dout.flush();
                    break;

                }
                if (JOBN.contains("JCPL")) {

                    requestHandler(dout, in, y);

                }
                
                String JOBNArray[] = JOBN.split(" ");
                String core = "";
                String memory = "";
                String disk = "";
                if (JOBNArray.length > 5) {
                    core = JOBNArray[4];
                    memory = JOBNArray[5];
                    disk = JOBNArray[6];
                }
                dout.write(("GETS Capable " + core + " " + memory + " " + disk + "\n").getBytes());
                String dataFromGets = in.readLine();
                System.out.println(dataFromGets);
                dout.flush();
                String dataFromGetsInArrayForm[] = dataFromGets.split(" ");
                int dataCount = Integer.parseInt(dataFromGetsInArrayForm[1]);
                int largestCore = 0;
                int compareCore = 0;
                String largestServerType = "";

                String largestServerType2 = largestServerSearch(dout, in, largestCore, dataCount, compareCore,
                        largestServerType);
                // split the string to extract the number of the largest servers and the largest
                // server type
                int largestServerCount = Integer.parseInt(largestServerType2.split(" ")[1]);
                String largestType = largestServerType2.split(" ")[0];

                String temp[] = JOBN.split(" ");
                while (true) {

                    if (y >= largestServerCount)
                        y = 0;
                    if (JOBN.length() > 0) {
                        dout.write(("OK\n").getBytes());
                        dout.flush();
                        String str = in.readLine();

                        dout.write(("SCHD " + temp[2] + " " + largestType + " " + y + "\n").getBytes());
                        dout.flush();
                        y++;

                    }

                    String str2 = in.readLine();
                    System.out.println(str2);

                    requestHandler(dout, in, y);

                    break;

                }

            } catch (Exception e) {

            }
            break;
        }

    }

}
