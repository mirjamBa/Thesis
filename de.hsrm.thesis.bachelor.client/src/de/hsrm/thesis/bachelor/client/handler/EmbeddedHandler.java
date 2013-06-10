/**
 * 
 */
package de.hsrm.thesis.bachelor.client.handler;

import de.hsrm.mi.administration.client.handler.AbstractHandler;
import de.hsrm.mi.administration.shared.handler.IHandler;

/**
 * @author mba
 */
public class EmbeddedHandler extends AbstractHandler implements IHandler {

  @Override
  public void handle() {
    System.out.println("Ich bin der EmbeddedHandler");

  }

  @Override
  public void setNext(IHandler handler) {
    setNextHandler(handler);
  }

}
