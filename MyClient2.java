import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class MyClient2 {
    public static void main(String[] args) {
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
            String s3 = in.readLine();
            dout.flush();
            System.out.println(s3);
            dout.write(("REDY\n").getBytes());
            dout.flush();
            String s4 = in.readLine();
            System.out.println(s4);
            String temp[] = s4.split(" ");
            String core = temp[4];
            String disk = temp[5];
            String memory = temp[6];

            dout.write(("GETS Capable " + temp[4] + " " + temp[5] + " " + temp[6] + "\n").getBytes());
            String sx = in.readLine();
            System.out.println(sx);
            dout.flush();

            String sx2[] = sx.split(" ");
            int max2 = Integer.parseInt(sx2[1]);

            dout.write(("OK\n").getBytes());
            String sx3 = in.readLine();
            System.out.println(sx3);
            dout.flush();
            int x1 = 0;
            int s1 = 0;
            String sx13 = "";
            List<String> temp2 = new ArrayList<String>();

            dout.write(("OK\n").getBytes());
            String sx4 = in.readLine();
            System.out.println(sx4);
            for (int i = 0; i < max2; i++) {
                temp2.add(in.readLine());
                s1 = 0;
                if (temp2.size() > 4) {
                    if(temp2.get(i)!=null && temp2.get(i).length()>4)
                    s1 = Integer.parseInt(temp2.get(i).split(" ")[4]);
                }

                if (s1 >= x1) {
                    x1 = s1;
                    if(temp2.get(i)!=null)
                    sx13 = temp2.get(i).split(" ")[0];
                }
            }

            dout.write(("SCHD 0 " + sx13 + " 0"+"\n").getBytes());
            String s15 = in.readLine();
            System.out.println(s15);
            dout.flush();

            dout.write(("OK\n").getBytes());
            String s16 = in.readLine();
            System.out.println(s16);
            dout.flush();

            dout.write(("QUIT\n").getBytes());
            String s5 = in.readLine();
            System.out.println(s5);
            dout.flush();
            dout.close();
            s.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}