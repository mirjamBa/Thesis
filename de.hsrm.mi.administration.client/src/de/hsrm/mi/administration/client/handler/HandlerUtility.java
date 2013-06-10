package de.hsrm.mi.administration.client.handler;

import java.util.ArrayList;
import java.util.List;

import de.hsrm.mi.administration.shared.handler.IHandler;

public class HandlerUtility {

	private List<IHandler> handler;

	public HandlerUtility() {
		handler = new ArrayList<IHandler>();
		
		ExampleHandler eh = new ExampleHandler();
		LastHandler lh = new LastHandler();
		
		eh.setNextHandler(lh);
		
		handler.add(eh);
		handler.add(lh);
	}

	public void addHandler(IHandler newHandler) {
		handler.get(handler.size()-1).setNext(newHandler);
		handler.add(newHandler);
	}

	public void handle() {
		handler.get(0).handle();
	}

}
