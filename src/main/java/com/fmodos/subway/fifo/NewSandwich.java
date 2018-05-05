package com.fmodos.subway.fifo;

public class NewSandwich {

	private final String clientName;

	private final int timeout;

	public NewSandwich(String clientName, int timeout) {
		super();
		this.clientName = clientName;
		this.timeout = timeout;
	}

	public String getClientName() {
		return clientName;
	}

	public int getTimeout() {
		return timeout;
	}

}
