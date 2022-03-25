import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
public class Client2 {
    public static void main(String[] args) {

        while (true) {

            ProcessRequest();
            break;

        }

    }

    public static List<String> ReadBigStringIn(BufferedReader buffIn, int max2) throws IOException {
        List<String> allCapableServers = new ArrayList<String>();
        String line;
        while( max2!=0) {
           allCapableServers.add(buffIn.readLine());
           max2--;
        }
        return allCapableServers;
    }

    public static String schedule(DataOutputStream dout, BufferedReader in, int s1, int max2, int x1, String sx13) {

       
        int countMax = 0;

        try {
            dout.write(("OK\n").getBytes());

            List<String> temp2 = ReadBigStringIn(in,max2);
            String sx5;
            
            for (int i = 0; i < max2; i++) {
                
                s1 = 0;
                if (temp2.size() > 4) {
                    if (temp2.get(i) != null && temp2.get(i).length() > 4)
                        s1 = Integer.parseInt(temp2.get(i).split(" ")[4]);
                }

                if (s1 >= x1) {
                    x1 = s1;
                    if (temp2.get(i) != null)
                        sx13 = temp2.get(i).split(" ")[0];
                }

            }

            for (int i = 0; i < max2; i++) {
                if (temp2.get(i).length() > 4) {
                    if (Integer.parseInt(temp2.get(i).split(" ")[4]) == x1) {

                        countMax++;

                    }
                }

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return sx13 + " " + countMax;
    }

    public static void ProcessRequest() {
        try {
            Socket s = new Socket("localhost", 50000);
            // DataInputStream dis = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            dout.write(("HELO\n").getBytes());
            dout.flush();
            String s2 = in.readLine();
            System.out.println(s2);
            dout.write(("AUTH 44751494\n").getBytes());
            dout.flush();
            String s3 = in.readLine();
            System.out.println(s3);
            Boolean right = false;

            while (true) {
                V(dout, in, 0, 0, right);
                break;
            }

            dout.write(("QUIT\n").getBytes());
            dout.flush();
            String s5 = in.readLine();
            System.out.println(s5);

            dout.close();
            in.close();
            s.close();
            
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void V(DataOutputStream dout, BufferedReader in, int x, int y, boolean b) {
        int x1 = x;
        int y1 = y;
        boolean b1 = b;

        while (true) {
            try {
                dout.write(("REDY\n").getBytes());
                dout.flush();
                String s4 = in.readLine();
                System.out.println(s4);
                if (s4.contains("JCPL")) {
                     
                     V(dout, in, x1, y1, false);

                }
                if (s4.contains("NONE")) {
                    dout.write(("QUIT\n").getBytes());
                    dout.flush();
                    break;

                }
                
                String readyReply[] = s4.split(" ");
                String core ="";
                String memory ="";
                String disk ="";
                if(readyReply.length>5){
                core = readyReply[4];
                memory = readyReply[5];
                disk = readyReply[6];
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

                
                String largestServerType2 = schedule(dout, in, largestCore, dataCount , compareCore, largestServerType);
                int largest = Integer.parseInt(largestServerType2.split(" ")[1]);
                String type =  largestServerType2.split(" ")[0];
                


                if (s4.contains("QUIT")) {
                    break;

                }
                if (!s4.equalsIgnoreCase("NONE")) {

                    String temp[] = s4.split(" ");
                    while (true) {

                        if (y1 >= largest)
                            y1 = 0;
                        if (s4.length() > 0 && !s4.contains("JCPL")) {
                            dout.write(("OK\n").getBytes());
                             dout.flush(); 
                             String s11 = in.readLine();

                            dout.write(("SCHD " + temp[2] + " " +type+" "+ y1 + "\n").getBytes());
                            dout.flush();
                            y1++;

                        }

                        String s10 = in.readLine();
                        System.out.println(s10);
                        x1++;

                        V(dout, in, x1, y1, false);
                        if (s10.contains("DATA") || s10.contains("OK")) {
                            while (true) {
                                dout.write(("OK\n").getBytes());
                                dout.flush();
                                String s5 = in.readLine();
                                System.out.println(s5);

                                if (s5.contains(".")) {
                                    break;

                                }

                            }

                        }

                        break;

                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            break;
        }



    }

}
