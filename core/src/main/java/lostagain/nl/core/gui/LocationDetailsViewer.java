package lostagain.nl.core.gui;

import lostagain.nl.core.interfaces.Software;
import tripleplay.ui.Background;
import tripleplay.ui.Constraints;
import tripleplay.ui.Field;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Layout;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AbsoluteLayout;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.util.Colors;

public class LocationDetailsViewer extends Group implements Software {

	/** will provide basic system information for the current location.
	 * 
	 * currently used as a widget/tripleplay testing ground for stuff */
	public LocationDetailsViewer() {
		super(AxisLayout.vertical().offStretch());
		super.setConstraint(AxisLayout.stretched());
			
		//final Group widget = new Group(AxisLayout.horizontal()).addStyles(
	       //     Style.BACKGROUND.is(Background.solid(Colors.CYAN)));
	    
		//super.add(widget);
		  	
		Group group = new Group(new AbsoluteLayout()).add(AbsoluteLayout.at(new Field("username"), 197, 337));
		super.add(AbsoluteLayout.at(group, 0, 0));
		
		//a few tests
		//group.add(new Label("test"));
		//group.add(new Label("test2"));	
		
		Label spacer = new Label("test3");
		
		//spacer.setConstraint(new AbsoluteLayout.Constraint(position.point.get(), origin.point.get()));
		
		
		
		//spacer.setConstraint(Constraints.fixedSize(100, 50));
		
		AbsoluteLayout.at(spacer, 50, 50,100,50);
		
		
		group.add(spacer);
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
