package com.alibaba.csp.sentinel.dashboard.nacos.exception;

/**
 * A {@code NacosConnectionFailureException} is thrown when the application fails to
 * connect to Nacos Server.
 *
 * @author juven.xuxb
 */
public class NacosConnectionFailureException extends RuntimeException {

	private final String serverAddr;

	public NacosConnectionFailureException(String serverAddr, String message) {
		super(message);
		this.serverAddr = serverAddr;
	}

	public NacosConnectionFailureException(String serverAddr, String message,
			Throwable cause) {
		super(message, cause);
		this.serverAddr = serverAddr;
	}

	public String getServerAddr() {
		return serverAddr;
	}

}
