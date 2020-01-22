package org.analyser.services.Implementations;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.analyser.entities.IPAddress;
import org.analyser.services.interfaces.ILANScanner;

public class LANScanner implements ILANScanner {
	/**
	 * Parcourir l'ensemble du réseau pour détecter les postes de travail ou les
	 * serveurs
	 *
	 * @param iproot plage de départ du scan
	 */
	
	public void r(String ipStart, String ipEnd) {
		// Test base Ip valide
		String[] nip = ipStart.split(" ");
		if (nip.length != 4) {
			System.out.println("Base Ip incorrecte !!! exemple 192.168.2.0");
			return;
		}

		// timer
		int ifound = 0;
		Long timestart = System.currentTimeMillis();

		// Entete du tableau
		//System.out.printf("%-16s %-30s %-10sn", "Adresse ip", "Nom du poste", "Port ouvert");
		byte[] ip = { (byte) Integer.parseInt(nip[0]), (byte) Integer.parseInt(nip[1]), (byte) Integer.parseInt(nip[2]),
				(byte) 0 };

		// Boucle sur l'ensemble du masque réseau
		for (int i = 0; i < 255; i++) {
			ip[3] = (byte) i;
			try {
				InetAddress addr = InetAddress.getByAddress(ip);
				if (isAlive(addr.getHostAddress())) {
					System.out.println(addr.getHostAddress() +"->"+ addr.getHostName()+":"+scanPort(addr.getHostAddress()));
					ifound++;
				} else {
					// System.out.println(addr.getHostAddress() + " empty");
				}
			} catch (UnknownHostException e) {
				System.out.println(e.getMessage());
			}
		}
		Long timeelapse = System.currentTimeMillis() - timestart;
		System.out.println(ifound + " postes détectés sur le réseau " + ipStart + " en " + Math.round(timeelapse / 1000)
				+ " secondes");

	}

	/**
	 * Ping sur une adresse ip
	 *
	 * @param Ipv4Adr ip adresse ip du poste
	 * @return boolean
	 */
	private boolean isAlive(String Ipv4Adr) {
		Process p1;
		boolean reachable = false;
		try {
			p1 = java.lang.Runtime.getRuntime().exec("ping -w 2 -n 2 " + Ipv4Adr);
			int returnVal = p1.waitFor();
			reachable = (returnVal == 0);
		} catch (IOException | InterruptedException ex) {
			Logger.getLogger(LANScanner.class.getName()).log(Level.SEVERE, null, ex);
		}
		return reachable;
	}

	/**
	 * Exemple de plage de port à scanner
	 *
	 * @param ip adresse ip du poste
	 * @return String
	 */
	private String scanPort(String ip) {
		String openPort = "";

		// Port de communication FTP
		if (portIsOpen(ip, 21, 100)) {
			openPort += " FTP";
		}
		// Port standard pour le web, par ex Apache
		if (portIsOpen(ip, 80, 100)) {
			openPort += " Http";
		}
		// Port d'une imprimante
		if (portIsOpen(ip, 515, 100)) {
			openPort += " Printer";
		}
		// Port du serveur MySql
		if (portIsOpen(ip, 3306, 100)) {
			openPort += " MySql";
		}

		return openPort.trim();
	}

	/**
	 * Tester l'état du port sur un poste
	 *
	 * @param ip      adresse ip du poste
	 * @param port    Numero du port
	 * @param timeout délai en ms
	 * @return port ouvert ou non
	 */
	private boolean portIsOpen(String ip, int port, int timeout) {
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(ip, port), timeout);
			socket.close();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	public List<InetAddress> scan(String ipStart, String ipEnd) {
		// TODO Auto-generated method stub
		return null;
	}
}
