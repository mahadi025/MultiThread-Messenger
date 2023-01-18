package address;

import java.net.InetAddress;
import java.net.UnknownHostException;



public class Address {
	public static String hostName() throws UnknownHostException{
			InetAddress address = InetAddress. getLocalHost();
			return address.getHostName();
		
	}
	public static String ipAddress() throws UnknownHostException{
		InetAddress address = InetAddress. getLocalHost();
		return address.getHostAddress();
	}
}
