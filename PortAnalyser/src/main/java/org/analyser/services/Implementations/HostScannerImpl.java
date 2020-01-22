package org.analyser.services.Implementations;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.analyser.entities.IPAddress;
import org.analyser.entities.Scan;
import org.analyser.entities.SystemService;
import org.analyser.security.SessionHandler;
import org.analyser.services.interfaces.IHostScanner;
import org.analyser.services.interfaces.IScannerService;
import org.springframework.beans.factory.annotation.Autowired;

public class HostScannerImpl implements IHostScanner{
	private int portStart;
	private int portEnd;
	private int timeout;
	private Scan scan;
	@Autowired
	private IScannerService scannerService;
	
	public HostScannerImpl() {
		super();
	}

	public HostScannerImpl(int portStart, int portEnd, int timeout) {
		super();
		this.portStart = portStart;
		this.portEnd = portEnd;
		this.timeout = timeout;
	}

	@Override
	public Map<InetAddress, List<Integer>> scan(List<InetAddress> addresses) {
		scan = new Scan();
		
		/**
		 * une adresse IP peut avoir une liste de ports ouverts (Map<>)
		 * qui va être renvoyée par la fonction
		 */
		Map<InetAddress, List<Integer>> ipMapToPortList = new HashMap<InetAddress, List<Integer>>();
		List<Integer> listOpenedPorts = new ArrayList<Integer>();
		List<IPAddress> listIPAddresses = new ArrayList<IPAddress>(); // Liste des addresses IP scannées pour un objet de Scan
		
		for(InetAddress currentIP : addresses) {
			IPAddress ip = new IPAddress();
			ip.setIpAddress(currentIP);
			System.out.print("current IP: " + currentIP + " -> ");
			for( int currentPort = portStart; currentPort <= portEnd; currentPort++ ) {            
	            try {
	                Socket s = new Socket();
	                System.out.println("current port: "+currentPort);
	                s.connect( new InetSocketAddress(currentIP, currentPort), timeout);
	                s.close();
	                System.out.println("Port ouvert: " + currentPort);
	                listOpenedPorts.add(currentPort); // Si c'est un port ouvert on l'ajoute dans la liste a retourner
	                SystemService service = new SystemService(ip, currentPort, "1.0", true, false, false);
	                ip.getServices().add(service);
	            }
	            // Si le socket lève une exception cad n'arrive pas à ateindre le port, c'est qu'il est fermé
	            catch( IOException ioe ) {
	            	SystemService service = new SystemService(ip, currentPort, "1.0", false, false, true);
	                ip.getServices().add(service);
	            }
			}
			ipMapToPortList.put(currentIP, listOpenedPorts);
			scan.getIps().add(ip);
		}
		
		return ipMapToPortList;
	}

	public int getPortStart() {
		return portStart;
	}

	public void setPortStart(int portStart) {
		this.portStart = portStart;
	}

	public int getPortEnd() {
		return portEnd;
	}

	public void setPortEnd(int portEnd) {
		this.portEnd = portEnd;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public Scan getScan() {
		return scan;
	}

	public void setScan(Scan scan) {
		this.scan = scan;
	}

}
