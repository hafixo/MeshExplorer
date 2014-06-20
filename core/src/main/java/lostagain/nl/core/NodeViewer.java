package lostagain.nl.core;

import lostagain.nl.core.interfaces.Software;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Layout;
import tripleplay.ui.layout.AxisLayout;

public class NodeViewer extends Group implements Software {

	public NodeViewer() {
		super(AxisLayout.vertical());
		  super.setConstraint(AxisLayout.stretched());
		//a few tests
		super.add(new Label("test"));
		super.add(new Label("test2"));
		super.add(new Label("test3"));
	}

	@Override
	public void onOpen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Show() {
		super.setVisible(true);
	}

	@Override
	public void Hide() {
		super.setVisible(false);

	}

}
