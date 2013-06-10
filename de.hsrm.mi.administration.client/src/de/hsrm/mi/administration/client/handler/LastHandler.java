package de.hsrm.mi.administration.client.handler;

import de.hsrm.mi.administration.shared.handler.IHandler;

public class LastHandler extends AbstractHandler implements IHandler {

	@Override
	public void handle() {
		System.out.println("Ich bin der LastHandler");
		
		if(getNextHandler() != null){
			getNextHandler().handle();
		}

	}

	@Override
	public void setNext(IHandler handler) {
		setNextHandler(handler);
		
	}

}
