package de.hsrm.mi.administration.client.handler;

import de.hsrm.mi.administration.shared.handler.IHandler;

public abstract class AbstractHandler {
	protected IHandler nextHandler;

	/**
	 * @return the nextHandler
	 */
	public IHandler getNextHandler() {
		return nextHandler;
	}

	/**
	 * @param nextHandler the nextHandler to set
	 */
	public void setNextHandler(IHandler nextHandler) {
		this.nextHandler = nextHandler;
	}
	
	

}
