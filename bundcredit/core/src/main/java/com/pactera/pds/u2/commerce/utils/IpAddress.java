package com.pactera.pds.u2.commerce.utils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class IpAddress {
	public static String getIpAddr(HttpServletRequest request) {
		
		String ip = request.getHeader("x-forwarded-for");
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getHeader("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = request.getRemoteAddr();
			}

		return ip;
	}
	public static String getSerIpAddr() throws SocketException{
		StringBuffer ips=new StringBuffer(2<<7);
		Enumeration<NetworkInterface> netInterfaces = NetworkInterface.getNetworkInterfaces();
		while (netInterfaces.hasMoreElements()) {
			NetworkInterface ni = netInterfaces
					.nextElement();

			Enumeration<InetAddress> addrs = ni.getInetAddresses();
			while (addrs.hasMoreElements()) {
				InetAddress ip = addrs.nextElement();
				ips.append("[").append(ip).append("] ");
				
			}
			ips.append("\n");
		}
		return ips.toString();
	}
	
	public static void main(String [] args) throws SocketException{
		System.out.println(getSerIpAddr());
	}
}
