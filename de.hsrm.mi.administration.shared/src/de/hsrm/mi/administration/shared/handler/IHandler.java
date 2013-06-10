package de.hsrm.mi.administration.shared.handler;

public interface IHandler {

	public void handle();
	
	public void setNext(IHandler handler);

}
