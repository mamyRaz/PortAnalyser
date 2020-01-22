package org.analyser.services.interfaces;

import java.net.InetAddress;
import java.util.List;

public interface ILANScanner {
	public List<InetAddress> scan(String ipStart, String ipEnd);
}
