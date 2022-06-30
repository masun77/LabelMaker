package userInterface;

import java.awt.Container;

import main.ApplicationState;

public interface AppFunction {
	public void setApplicationState(ApplicationState s);
	public void showFunction();
	public Container getMainContent();
	public void refresh();
}
