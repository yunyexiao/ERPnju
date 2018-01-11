package rmi;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.RMISocketFactory;

public class SMRMISocket extends RMISocketFactory {

	@Override
	public ServerSocket createServerSocket(int port) throws IOException {
		if (port == 0) port = 6668; //��ָ�������������
	    return new ServerSocket(port);
	}

	@Override
	public Socket createSocket(String host, int port) throws IOException {
        return new Socket("120.79.145.97",port);
        //return new Socket(host, port);
	}

}
