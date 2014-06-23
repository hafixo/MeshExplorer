package lostagain.nl.core.gui;

import static playn.core.PlayN.assets;
import lostagain.nl.core.NetworkLocationScreen;
import playn.core.Color;
import playn.core.Image;
import react.Slot;
import react.UnitSlot;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.ImageButton;
import tripleplay.ui.Style;
import tripleplay.ui.Style.HAlign;
import tripleplay.ui.Styles;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.ui.layout.TableLayout;
import tripleplay.util.Colors;

public class Taskbar extends Group {
/*
    Group layout;
    static class ExposedColumn extends TableLayout.Column
    {
        public ExposedColumn (Style.HAlign halign, boolean stretch, float weight, float minWidth) {
            super(halign, stretch, weight, minWidth);
          
        }
        public Style.HAlign halign () { return _halign; }
        public float weight () { return _weight; }
        public float minWidth () { return _minWidth; }
        public boolean isStretch () { return _stretch; }
    }
    */
	ImageButton securityButton;
	

	String lockediconloc = "images/locked.png";
	Image lockedicon = assets().getImage(lockediconloc).subImage(0,0, 64, 64);

	String unlockediconloc = "images/unlocked.png";
	Image unlockedicon = assets().getImage(unlockediconloc).subImage(0,0, 64, 64);
	
	
	public Taskbar(final NetworkLocationScreen parent) {
		super(AxisLayout.vertical().gap(30));
		
		///super.fillHeight();
		//super.alignTop();
		
		 Styles tableStyles = Styles.make(Style.BACKGROUND.is(Background.bordered(Color.argb(0, 0, 0, 0),Colors.WHITE,3)),
		            Style.VALIGN.top);
		 
		 
		this.addStyles(tableStyles);
		
		 
		//layout = new Group((this), tableStyles);
		
		this.setConstraint(AxisLayout.stretched());
		
		securityButton = addButton(parent, lockediconloc,new UnitSlot() {
	        @Override public void onEmit () {
	        	parent.gotoLinks();
	        }
	    });
		
		String emailiconloc = "images/email.png";
		addButton(parent, emailiconloc,new UnitSlot() {
	        @Override public void onEmit () {
	        	parent.gotoEmail();
	        }
	    });

		String contentsiconloc = "images/contents.png";
		addButton(parent, contentsiconloc,new UnitSlot() {
	        @Override public void onEmit () {
	        	parent.gotoContents();
	        }
	    });
		
		String shortcuticonloc = "images/shortcut.png";
		 addButton(parent, shortcuticonloc,new UnitSlot() {
		        @Override public void onEmit () {
		        	parent.gotoNodeViewer();
		        }
		    });

			String networkiconloc = "images/network.png";
			addButton(parent, networkiconloc,new UnitSlot() {
		        @Override public void onEmit () {
		        	parent.gotoLinks();
		        }
		    });
			 
		
	
	}
	
	public void setSecurityIconOn(Boolean status){
		
		if (status){
			securityButton.setDown(lockedicon);
			securityButton.setUp(lockedicon);
		} else {
			securityButton.setDown(unlockedicon);
			securityButton.setUp(unlockedicon);
		}
		
	}
	

private ImageButton addButton(final NetworkLocationScreen parent, String iconloc, UnitSlot response) {
	Image icon = assets().getImage(iconloc).subImage(0,0, 64, 64);
	
	
	 ImageButton newButton = new ImageButton(icon);
	
	 newButton.clicked().connect(response);
	 			 
	 this.add(newButton);
	 
	 return newButton;
}
	
	
	
}
