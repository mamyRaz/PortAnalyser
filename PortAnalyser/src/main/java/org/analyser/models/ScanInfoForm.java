package org.analyser.models;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class ScanInfoForm implements Serializable {
	@Size(min = 7, max = 15)
	private String ipStart;
	@Size(min = 7, max = 15)
	private String ipEnd;
	
	@Max(65353)
	@Min(0)
	private int portStart;
	
	@Max(65535)
	@Min(0)
	private int portEnd;
	
	public ScanInfoForm() {
		super();
	}

	public ScanInfoForm(String ipStart, String ipEnd, int portStart, int portEnd) {
		super();
		this.ipStart = ipStart;
		this.ipEnd = ipEnd;
		this.portStart = portStart;
		this.portEnd = portEnd;
	}

	public String getIpStart() {
		return ipStart;
	}

	public void setIpStart(String ipStart) {
		this.ipStart = ipStart;
	}

	public String getIpEnd() {
		return ipEnd;
	}

	public void setIpEnd(String ipEnd) {
		this.ipEnd = ipEnd;
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
	
}
