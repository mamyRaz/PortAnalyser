package org.analyser.services.interfaces;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;

public interface IHostScanner {
	public Map<InetAddress, List<Integer>> scan(List<InetAddress> addresses);
}
