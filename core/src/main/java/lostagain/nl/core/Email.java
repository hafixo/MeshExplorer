package lostagain.nl.core;

import react.Slot;
import react.UnitSlot;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Field;
import tripleplay.ui.Group;
import tripleplay.ui.HistoryGroup;
import tripleplay.ui.Label;
import tripleplay.ui.Layout;
import tripleplay.ui.SizableGroup;
import tripleplay.ui.Slider;
import tripleplay.ui.Style;
import tripleplay.ui.Stylesheet;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.ui.layout.BorderLayout;
import tripleplay.ui.layout.TableLayout;
import tripleplay.util.Colors;
import lostagain.nl.core.interfaces.Software;

public class Email  implements Software {
	Group  mainGroup;

	    public Group createIface () {
	    	
	        Button add10 = new Button("+10");
	        Label topbar = new Label("X new messages");
	        
	        HistoryGroup.Labels history = new HistoryGroup.Labels();
	        
	        final SizableGroup historyBox = new SizableGroup(AxisLayout.vertical());
	        
	        history.setConstraint(AxisLayout.stretched());
	        
	        historyBox.setConstraint(AxisLayout.stretched());
	        
	        historyBox.add(history);

	        Group top = new Group(AxisLayout.horizontal());
	        top.add(topbar);
	        top.add(add10);
	        	        
	        
	        add10.clicked().connect(addSome(history, "test text-", 10));
	        
	        Stylesheet labelstyle = Stylesheet.builder().add(Label.class,
		            Style.BACKGROUND.is(Background.composite(
			                Background.blank().inset(0, 2),
			                Background.bordered(Colors.WHITE, Colors.BLACK, 1).inset(10))),
			            Style.TEXT_WRAP.on, Style.HALIGN.center).create();
	      
	       
	        history.setStylesheet(labelstyle);
	        
	        
	        
	        
	        history.addStyles(Style.BACKGROUND.is(Background.beveled(	        		
	            Colors.CYAN, Colors.brighter(Colors.CYAN), Colors.darker(Colors.CYAN)).inset(5)));
	   
	        
	        mainGroup = new Group(AxisLayout.vertical().offStretch()).add(
		            top, historyBox.setConstraint(AxisLayout.stretched())).addStyles(
		    	            Style.BACKGROUND.is(Background.blank().inset(5)));
	        mainGroup.setConstraint(AxisLayout.stretched());
	        
	        return mainGroup;
	    }

	    protected UnitSlot addSome (final HistoryGroup.Labels group, final String Message, final int num) {
	        return new UnitSlot() {
	            @Override public void onEmit () {
	                for (int ii = 0; ii < num; ++ii) {
	                	
	                    group.addItem(Message);
	                    
	                }
	            }
	        };
	    }

	    
	@Override
	public void onOpen() {
		// TODO Auto-generated method stub

	}

	@Override
	public void Show() {
		mainGroup.setVisible(true);
	}

	@Override
	public void Hide() {
		mainGroup.setVisible(false);

	}

}
