import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;  
public class MyClient {  
public static void main(String[] args) {  
try{      
Socket s=new Socket("localhost",50000);  
DataInputStream dis=new DataInputStream(s.getInputStream());  
DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
dout.write(("HELO\n").getBytes());  
dout.flush();  
String s2 = in.readLine();
System.out.println(s2);  
dout.write(("AUTH 44751494\n").getBytes());  
String s3 = in.readLine();
System.out.println(s3);
dout.write(("REDY\n").getBytes());  
String s4 = in.readLine();
System.out.println(s4);
dout.write(("QUIT\n").getBytes());  
String s5 = in.readLine();
System.out.println(s5);
dout.close();  
s.close();  
}catch(Exception e){System.out.println(e);}  
}  
}  
