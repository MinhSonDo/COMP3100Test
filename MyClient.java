import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;  
public class MyClient {  
public static void main(String[] args) {  
try{      
Socket s=new Socket("localhost",6666);  
DataInputStream dis=new DataInputStream(s.getInputStream());  
DataOutputStream dout=new DataOutputStream(s.getOutputStream());  
dout.writeUTF("HELO");  
dout.flush();  
String s2 = dis.readUTF();
System.out.println(s2);  
dout.writeUTF("BYE");  
String s3 = dis.readUTF();
System.out.println(s3);
dout.close();  
s.close();  
}catch(Exception e){System.out.println(e);}  
}  
}  