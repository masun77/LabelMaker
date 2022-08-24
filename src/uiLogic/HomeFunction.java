package uiLogic;

import java.awt.Container;
import java.awt.Dimension;

import main.AppListener;

public interface HomeFunction extends AppListener {
	public Container getMainContent();
	public void setScrollPaneSize(int width, int height);
}
