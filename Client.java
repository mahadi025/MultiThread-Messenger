

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import freeport.*;
import address.*;

public class Client implements Runnable{
	private Thread t;
	private Thread t1;
	private Thread t2;
	private String LocalHost;
	private String readLine="",sentLine="";
	private int port;
	private DataInputStream inp,in; 
    private DataOutputStream out;
	private Socket s;
	private ServerSocket ss;
	
	Client(String LocalHost,int port){
		this.LocalHost=LocalHost;
		this.port=port;
		t=new Thread(this,"Client");
		t.start();
	}
	
	@Override
	public void run() {
		try {
			s=new Socket(LocalHost,port);
			System.out.println("Connected");
		} catch (IOException ex) {
			System.out.println("Could not connect with the server");
			return;
		}
		t1=new Thread(new Runnable() {

			@Override
			public void run() {
				inp=new DataInputStream(System.in);
				try {
					out=new DataOutputStream(s.getOutputStream());
				} catch (IOException ex) {
					System.out.println("Could not send the message");
					return;
				}
					while(!sentLine.equalsIgnoreCase("bye")){
					try {
						sentLine=inp.readLine();
					} catch (IOException ex) {
						System.out.println("Error in reading  message from the console");
						return;
					}
					try {
						out.writeUTF("C: "+sentLine);
					} catch (IOException ex) {
						System.out.println("Error in sending message");
						return;
					}
					}
			}
		});
		t1.start();
		
		t2=new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					in=new DataInputStream(new BufferedInputStream(s.getInputStream()));
				} catch (IOException ex) {
					System.out.println("Could not read the message");
					return;
				}
				while(!readLine.equalsIgnoreCase("bye")){
					try {
						readLine=in.readUTF();
					} catch (IOException ex) {
						System.out.println("Something error occured while reading the message!");
						return;
					}
						System.out.println(readLine);
					}
			}
		});
		t2.start();
	}
	
	public static void main(String[] args){
		Scanner inps=new Scanner(System.in);
		Scanner inpi=new Scanner(System.in);
		System.out.print("Enter the IP Address or HostNAme: ");
		String address=inps.nextLine();
		System.out.print("Enter the port: ");
		int port=inpi.nextInt();
		
		Client c=new Client(address, port);
	}
	
}
