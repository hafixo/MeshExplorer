package lostagain.nl.core.gui;

import java.util.ArrayList;
import java.util.Iterator;

import com.darkflame.client.semantic.SSSNode;

import playn.core.Color;
import playn.core.util.Callback;
import lostagain.nl.core.NetworkLocationScreen;
import lostagain.nl.core.gui.Link.LinkMode;
import lostagain.nl.core.interfaces.Software;
import lostagain.nl.core.utilities.GameTimer;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Layout;
import tripleplay.ui.Style;
import tripleplay.ui.Styles;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.util.Colors;

public class Links extends Group implements Software {
	
	int ScanSpeed = 10;	
	ArrayList<Link> linksBeingScanned = new ArrayList<Link>();
	final GameTimer linkscanner;
	
	Label title = new Label ("Link page");

	/** a page on a location that displays all the other locations
	 * this location knows about 
	 * **/
	public Links(NetworkLocationScreen parent) {
		
		super(AxisLayout.vertical().gap(15).offStretch());
		super.setConstraint(AxisLayout.stretched());
		super.setStyles(GamesStyles.GameNetworkBackground);//Style.BACKGROUND.is(Background.solid(Color.argb(255, 50,50, 155))));
		
		// Styles tableStyles = Styles.make(Style.BACKGROUND.is(Background.bordered(Color.argb(0, 0, 0, 0),Colors.WHITE,3)),
		   //         Style.VALIGN.top);
		 
		super.addStyles(Style.VALIGN.top);
		title.addStyles(Style.COLOR.is(GamesStyles.standardTextColor));
		
		super.add(title);
		
		
		//setup scanner
		
	 linkscanner = new GameTimer(200, new Callback<String>(){
			

			@Override
			public void onSuccess(String result) {
				
				int SPEEDSTEP = (ScanSpeed / linksBeingScanned.size())+1; 
										
				Iterator<Link> linksToUpdate = linksBeingScanned.iterator();
				
				while (linksToUpdate.hasNext()) {
					Link currentLink = linksToUpdate.next();
										
					currentLink.stepForwardScanningAmount(SPEEDSTEP);
					
					if (currentLink.currentMode!=LinkMode.Scanning){
						linksToUpdate.remove();
					}
					
				}
				
				
				if (linksBeingScanned.size()==0){
					linkscanner.stop();
				}
				
			
				
			}

			@Override
			public void onFailure(Throwable cause) {
				// TODO Auto-generated method stub

				System.out.print("meeep!2");
			}
			
		},true);
		
	}
	
	
	public void startScanningLink(final Link link){

		link.setScanningAmount(0);
	
		linksBeingScanned.add(link);
		
		if (!linkscanner.running){
			linkscanner.start();
		}
		
	}

	
	public void addLink(Link link){
		super.add(link);
	}
;	@Override
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

	

	public void addLink(SSSNode sssNode) {
		//make a link from this sssnode (which should identify a pc on the games network) 
		Link newlink = new Link(sssNode,this);
		addLink(newlink);
	}


	public void clearLinks() {
		super.removeAll();

		super.add(title);
	}

}
