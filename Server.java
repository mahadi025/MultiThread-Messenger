import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import freeport.*;
import address.*;
public class Server implements Runnable {
	private int port;
	private Socket s;
	private ServerSocket ss;
	private DataInputStream in,inp;
	private DataOutputStream out;
	private String readLine="",sentLine="";
	private Thread t;
	private Thread t1=null;
	private Thread t2=null;
	
	Server(){
		port=FreePort.portfinder();
		t=new Thread(this,"Server");
		t.start();
	}

	@Override
	public void run() {
		try {
			ss=new ServerSocket(port);
			System.out.println("Your HostName: "+Address.hostName());
			System.out.println("Your IP Address: "+Address.ipAddress());
			System.out.println("Your Port: "+port);
			System.out.println("Wating for Connection....");
		} catch (IOException ex) {
			System.out.println(port+" "+"was already in use");
			return;
		}
		try {
			s=ss.accept();
			System.out.println("Connected");
		} catch (IOException ex) {
			System.out.println("Could not connect with the server");
			return;
		}
		t1=new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					in=new DataInputStream(new BufferedInputStream(s.getInputStream()));
				} catch (IOException ex) {
					System.out.println("Was not able to get the message");
					return;
				}
				while(!readLine.equalsIgnoreCase("bye")) {
					try {
						readLine=in.readUTF();
					} catch (IOException ex) {
						System.out.println("Could not able to read the message client");
						return;
					}
						System.out.println(readLine);
					}
			}
		});
		t1.start();
		
		t2=new Thread(new Runnable() {

			@Override
			public void run() {
				inp= new DataInputStream(System.in);
				try {
					out=new DataOutputStream(s.getOutputStream());
				} catch (IOException ex) {
					System.out.println("Could not send the message");
					return;
				}
				while(!sentLine.equalsIgnoreCase("bye")) {
					try {
						sentLine=inp.readLine();
					} catch (IOException ex) {
						System.out.println("Something error occured while sending the message.Please try again");
						return;
					}
					try {
						out.writeUTF("S: "+sentLine);
					} catch (IOException ex) {
						System.out.println("Messeage sent Unsucceful");
						return;
					}
					}
			}
		});
		t2.start();
	}
	
	public static void main(String[] args){
		Server s=new Server();
	}
}
