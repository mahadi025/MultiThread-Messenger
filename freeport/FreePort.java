package freeport;

import java.io.IOException;
import java.net.ServerSocket;

public class FreePort {
	private static ServerSocket ss;
	public static int portfinder(){
		try {
			ss = new ServerSocket(0);
		} catch (IOException ex) {
			System.out.println("Could not connect with the socket");
		}
		int x=ss.getLocalPort();
		try {
			ss.close();
		} catch (IOException ex) {
			System.out.println("Could not find any free port");
		}
		return x;
	}
}
