package de.hsrm.perfunctio.core.shared.handler;

public abstract class AbstractHandler {

	protected AbstractHandler nextHandler;
	protected int priority;

	public void setNext(AbstractHandler handler) {
		nextHandler = handler;
	}

	/**
	 * @return the priority
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * @param priority
	 *            the priority to set
	 */
	public void setPriority(int priority) {
		this.priority = priority;
	}

	/**
	 * @return the nextHandler
	 */
	public AbstractHandler getNextHandler() {
		return nextHandler;
	}

}
