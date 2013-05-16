package de.hsrm.thesis.bachelor.ui.swt.views;

import org.eclipse.scout.rt.ui.swt.ISwtEnvironment;
import org.eclipse.scout.rt.ui.swt.window.desktop.view.AbstractScoutView;

import de.hsrm.thesis.bachelor.ui.swt.Activator;

public class SouthEastView extends AbstractScoutView {

  public SouthEastView() {
  }

  @Override
  protected ISwtEnvironment getSwtEnvironment() {
    return Activator.getDefault().getEnvironment();
  }
}
