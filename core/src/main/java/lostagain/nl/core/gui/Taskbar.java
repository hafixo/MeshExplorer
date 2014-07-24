package lostagain.nl.core.gui;

import static playn.core.PlayN.assets;
import static playn.core.PlayN.log;
import lostagain.nl.core.NetworkLocationScreen;
import playn.core.Canvas;
import playn.core.CanvasImage;
import playn.core.Color;
import playn.core.Font;
import playn.core.Image;
import playn.core.PlayN;
import playn.core.util.Callback;
import react.Slot;
import react.UnitSlot;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.IconEffect;
import tripleplay.ui.ImageButton;
import tripleplay.ui.Style;
import tripleplay.ui.Style.Binding;
import tripleplay.ui.Style.HAlign;
import tripleplay.ui.Styles;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.ui.layout.TableLayout;
import tripleplay.util.Colors;
import tripleplay.util.StyledText;
import tripleplay.util.TextStyle;
import tripleplay.util.StyledText.Block;

public class Taskbar extends Group {

	public ImageButton securityButton;
	
	String emailiconloc = "images/email.png";
	public ImageButton messageButton;
	
	public ImageButton softwareButton;
	
	
	public ImageButton shortcutButton;
	public ImageButton networkButton;
	

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
	        	parent.gotoSecurity();

	        }
	    });
		
		
		messageButton = addButton(parent, emailiconloc,new UnitSlot() {
	        @Override public void onEmit () {
	        	parent.gotoEmail();

	        }
	    });

		String contentsiconloc = "images/contents.png";
		softwareButton = addButton(parent, contentsiconloc,new UnitSlot() {
	        @Override public void onEmit () {
	        	parent.gotoContents();
	        	
	        	
	        	
	        }

	    });
		
		/*
		String shortcuticonloc = "images/shortcut.png";
		shortcutButton= addButton(parent, shortcuticonloc,new UnitSlot() {
		        @Override public void onEmit () {
		        	parent.gotoNodeViewer();
		        }
		    });*/
		

			String networkiconloc = "images/network.png";
			networkButton=	addButton(parent, networkiconloc,new UnitSlot() {
		        @Override public void onEmit () {
		        	parent.gotoLinks();
		        	

		        }
		    });
			 
		
	
	}
	
	public void highlight(ImageButton button, Binding<Background> backstyle) {
		
		//un highlight all
		securityButton.addStyles(Style.BACKGROUND.is(Background.solid(0)));		
		messageButton.addStyles(Style.BACKGROUND.is(Background.solid(0)));		
		softwareButton.addStyles(Style.BACKGROUND.is(Background.solid(0)));		
		networkButton.addStyles(Style.BACKGROUND.is(Background.solid(0)));
				 
		//width
		button.addStyles(backstyle);
		
		
		
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
	
	/** visibly display the number of messages at this location **/
	public void setNumberOfMessages(final int Num){
		
		Image icon = assets().getImage(emailiconloc).subImage(0,0, 64, 64);
		
		icon.addCallback(new Callback<Image>() {

			@Override
			public void onSuccess(Image result) {
				
				//add number
			
				 CanvasImage image = PlayN.graphics().createImage(64, 64);
				
				 Canvas context = image.canvas();
				 
				 
				 context.drawImage(result, 0, 0);
				 
			   // TextStyle TEXT = new TextStyle().
		       // withFont(PlayN.graphics().createFont("Helvetiva", Font.Style.PLAIN, 10));

			    context.drawText(""+Num, 42, 42);
			    
			    messageButton.setUp(image);
			}

			@Override
			public void onFailure(Throwable cause) {
				// TODO Auto-generated method stub
				
			}

			
			
		});
		
	    
		 //Block blockoftext = StyledText.block(text.toString(), TEXT,canvasWidth); 
		 
		// blockoftext.render(context, tlx,tly+(leftover/2));
		
	}

private ImageButton addButton(final NetworkLocationScreen parent, String iconloc, UnitSlot response) {
	
	Image icon = assets().getImage(iconloc).subImage(0,0, 64, 64);
	
	 ImageButton newButton = new ImageButton(icon);
	
	 newButton.clicked().connect(response);
	 			 
	 this.add(newButton);
	 
	 return newButton;
}
	
	
	
}
