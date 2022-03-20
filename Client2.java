import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class Client2 {
    public static void main(String[] args) {

        while (true) {

            ProcessRequest();
            break;

        }

    }

    public static void ProcessRequest() {
        try {
            Socket s = new Socket("localhost", 50000);
            DataInputStream dis = new DataInputStream(s.getInputStream());
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
                V(dout, in, 0, 0,right);
                break;
            }

            dout.write(("QUIT\n").getBytes());
            dout.flush();
            String s5 = in.readLine();
            System.out.println(s5);

            dout.close();
            s.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public static void V(DataOutputStream dout, BufferedReader in, int x, int y, boolean b) {
        int x1 = x;
        int y1 = y;
        boolean b1= b;

        while (true) {
            try {
                dout.write(("REDY\n").getBytes());
                dout.flush();
                String s4 = in.readLine();
                System.out.println(s4);
                // if(b1==false) {
                //     dout.write(("GETS Type 4xlarge\n").getBytes());
                //     dout.flush();
                //     String s6 = in.readLine();
                //     System.out.println(s6);
                //     dout.write(("OK\n").getBytes());
                //     dout.flush();
                //     String s7 = in.readLine();
                //     System.out.println(s7);
                //     dout.write(("OK\n").getBytes());
                //     dout.flush();
                //     String s8 = in.readLine();
                //     System.out.println(s8);
                //     b1=true;
                //     V(dout, in, x1, y1, b1);
                //      }




                if (s4.contains("JCPL")) {
                    dout.write(("REDY\n").getBytes());
                    dout.flush();

                }
                if (s4.contains("NONE")) {
                    dout.write(("QUIT\n").getBytes());
                    dout.flush();
                    break;

                }
                if (s4.contains("QUIT")) {
                    break;

                }
                if (!s4.equalsIgnoreCase("NONE")) {
                    
         String temp []= s4.split(" ");
                    while (true) {

                        if (y1 == 3)
                            y1 = 0;
                        if (s4.length() > 0 && !s4.contains("JCPL")) {
                            dout.write(("SCHD " + temp[2] + " 4xlarge " + y1 + "\n").getBytes());
                        }
                        dout.flush();
                        String s10 = in.readLine();
                        System.out.println(s10);
                        x1++;
                        y1++;

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
                System.out.println(e);
            }
            break;
        }

        // String s5;
        // try {
        //     s5 = in.readLine();
        //     System.out.println(s5);
        //     if (s5.equalsIgnoreCase("OK"))
        //         ;

        // } catch (IOException e) {

        //     e.printStackTrace();
        // }

    }

}
