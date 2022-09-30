package oldFiles;

import java.awt.Container;
import java.awt.Dimension;

public interface HomeFunction extends AppListener {
	public Container getMainContent();
	public void setScrollPaneSize(int width, int height);
}
