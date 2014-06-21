package lostagain.nl.core.gui;

import static playn.core.PlayN.assets;
import lostagain.nl.core.NetworkNodeScreen;
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
	
	public Taskbar(final NetworkNodeScreen parent) {
		super(AxisLayout.vertical().gap(30));
		
		///super.fillHeight();
		//super.alignTop();
		
		 Styles tableStyles = Styles.make(Style.BACKGROUND.is(Background.bordered(Color.argb(0, 0, 0, 0),Colors.WHITE,3)),
		            Style.VALIGN.top);
		 
		 
		this.addStyles(tableStyles);
		
		 
		//layout = new Group((this), tableStyles);
		
		this.setConstraint(AxisLayout.stretched());
		
		 Image emailicon = assets().getImage("images/email.png").subImage(0,0, 64, 64);
		
		 ImageButton emailButton = new ImageButton(emailicon);
		
		 
		 
		// Button gotoEmail = new Button("Email");
		 emailButton.clicked().connect(new UnitSlot() {
	            @Override public void onEmit () {
	            	parent.gotoEmail();
	            }
	        });
		 
				 
		 this.add(emailButton);
		 
		 Image networkicon = assets().getImage("images/network.png").subImage(0,0, 64, 64);
		 ImageButton gotoNodeViewer = new ImageButton(networkicon);
		// Button gotoNodeViewer = new Button("NodeViewer");
		 
		 gotoNodeViewer.clicked().connect(new UnitSlot() {
	            @Override public void onEmit () {
	            	parent.gotoNodeViewer();
	            }
	        });
		 
		 this.add(gotoNodeViewer);

		 Image shortcuticon = assets().getImage("images/shortcut.png").subImage(0,0, 64, 64);
		ImageButton gotoLinks = new ImageButton(shortcuticon);
		
		 //Button gotoLinks = new Button("Links");
		 
		 gotoLinks.clicked().connect(new UnitSlot() {
	            @Override public void onEmit () {
	            	parent.gotoLinks();
	            }
	        });
		 
		 this.add(gotoLinks);
		 
	}
	
	
	
}
