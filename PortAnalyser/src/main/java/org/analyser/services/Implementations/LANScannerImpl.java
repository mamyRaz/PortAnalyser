package org.analyser.services.Implementations;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.analyser.services.interfaces.ILANScanner;

public class LANScannerImpl implements ILANScanner, Serializable {
	private boolean scanError = false;
	private int ipFound = 0;
	private Long timeElapse;
	private List<InetAddress> hosts = new ArrayList<>();
	private Map<InetAddress, String> hostsName = new HashMap<InetAddress, String>();

	public LANScannerImpl() {
		super();
	}

	@Override
	public List<InetAddress> scan(String ipStart, String ipEnd) {
		// Test base Ip valide
		String[] ipStartSplited = ipStart.trim().split("\\.");
		String[] ipEndSplited = ipEnd.trim().split("\\.");

		if (ipStartSplited.length != 4 || ipEndSplited.length != 4) {
			scanError = true;
			System.out.println("Format Ip incorrecte !!! exemple 192.168.2.0");
			return null;
		}

		Long timeStart = System.currentTimeMillis();

		int ipStartSplited0 = Integer.parseInt(ipStartSplited[0]);
		int ipStartSplited1 = Integer.parseInt(ipStartSplited[1]);
		int ipStartSplited2 = Integer.parseInt(ipStartSplited[2]);
		int ipStartSplited3 = Integer.parseInt(ipStartSplited[3]);

		int ipEndSplited0 = Integer.parseInt(ipEndSplited[0]);
		int ipEndSplited1 = Integer.parseInt(ipEndSplited[1]);
		int ipEndplited2 = Integer.parseInt(ipEndSplited[2]);
		int ipEndSplited3 = Integer.parseInt(ipEndSplited[3]);

		if (ipStartSplited0 > 255 || ipStartSplited1 > 255 || ipStartSplited2 > 255 || ipStartSplited3 > 255
				|| ipEndSplited0 > 255 || ipEndSplited1 > 255 || ipEndplited2 > 255 || ipEndSplited3 > 255) {
			scanError = true;
			System.out.println("Format Ip incorrecte !!! Un octet net peut pas depasse l'entier 255");
			return null;
		}

		for (int i = ipStartSplited0; i <= ipEndSplited0; i++) {
			for (int j = ipStartSplited1; j <= ipEndSplited1; j++) {
				for (int k = ipStartSplited2; k <= ipEndplited2; k++) {
					for (int l = ipStartSplited3; l <= ipEndSplited3; l++) {

						byte[] ipAddress = { (byte) i, (byte) j, (byte) k, (byte) l };

						try {
							InetAddress addrress = InetAddress.getByAddress(ipAddress);
							System.out.println("Scan en cours de: " + addrress.getHostAddress());
							if (isAlive(addrress.getHostAddress())) {
								hosts.add(addrress);
								hostsName.put(addrress, addrress.getHostName());
								ipFound++;
							}
						} catch (UnknownHostException e) {
							System.out.println(e.getMessage());
							scanError = true;
							return null;
						}

					}
				}
			}
		}
		timeElapse = System.currentTimeMillis() - timeStart;
		timeElapse = (long) Math.round(timeElapse / 1000);
		System.out.println(ipFound + " postes détectés sur le réseau " + ipStart + " en "
				+ Math.round(timeElapse / 1000) + " secondes");
		return hosts;
	}

	/**
	 * Ping sur une adresse ip
	 *
	 * @param Ipv4Adr ip adresse ip du poste
	 * @return boolean
	 */
	private boolean isAlive(String IPV4Address) {
		Process p1;
		boolean reachable = false;
		try {
			p1 = java.lang.Runtime.getRuntime().exec("ping -w 2 -n 2 " + IPV4Address);
			int returnVal = p1.waitFor();
			reachable = (returnVal == 0);
		} catch (IOException | InterruptedException ex) {
			Logger.getLogger(LANScanner.class.getName()).log(Level.SEVERE, null, ex);
		}
		return reachable;
	}

	public boolean isScanError() {
		return scanError;
	}

	public void setScanError(boolean scanError) {
		this.scanError = scanError;
	}

	public int getIpFound() {
		return ipFound;
	}

	public void setIpFound(int ipFound) {
		this.ipFound = ipFound;
	}

	public Long getTimeElapse() {
		return timeElapse;
	}

	public void setTimeElapse(Long timeElapse) {
		this.timeElapse = timeElapse;
	}

	public List<InetAddress> getHosts() {
		return hosts;
	}

	public void setHosts(List<InetAddress> hosts) {
		this.hosts = hosts;
	}

	public Map<InetAddress, String> getHostsName() {
		return hostsName;
	}

	public void setHostsName(Map<InetAddress, String> hostsName) {
		this.hostsName = hostsName;
	}
}
