package lostagain.nl.core;

import java.util.ArrayList;

import com.darkflame.client.semantic.SSSNode;
import com.darkflame.client.semantic.SSSNodesWithCommonProperty;

import playn.core.Color;
import react.UnitSlot;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Constraints;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Layout;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AbsoluteLayout;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.ui.util.BoxPoint;
import tripleplay.util.Colors;
import pythagoras.f.Dimension;
import pythagoras.f.IDimension;
import pythagoras.f.Point;
public class Link extends Group {

	Button gotoLinkButton;
	String Location = "";
	String LinkName = "";
	Label ProgressBar = new Label ("--->");

	final static String UNKNOWEN = "UNKNOWEN - ";
	final static String SCANNING = "SCANNING - ";
	int PercentageScanned = 0;
	
	final static String LOCKED = "LOCKED  ( ";
	
	//the current parent panel this is being viewed on.
	//(this determines where to send the clicks to start scanning,
	//as scanning is controlled by the parent panel)
	Links currentParent = null;
	
	enum LinkMode {
		Unknown,Scanning,Locked,Open;
	}

	LinkMode currentMode =  LinkMode.Unknown;
	boolean ComputerOpen;
	SSSNode linksToThisPC;
	//----
	static final SSSNode VISIBLETO = SSSNode.createSSSNode(MeshExplorer.INTERNALNS+"visibleto", MeshExplorer.INTERNALNS);
	static final SSSNode EVERYONE =  SSSNode.createSSSNode(MeshExplorer.INTERNALNS+"everyone", MeshExplorer.INTERNALNS);
	static final SSSNode Computer =  SSSNode.createSSSNode(MeshExplorer.INTERNALNS+"computer", MeshExplorer.INTERNALNS);
	static final SSSNode SecuredBy =  SSSNode.createSSSNode(MeshExplorer.INTERNALNS+"SecuredBy", MeshExplorer.INTERNALNS);
	static final SSSNode Security =  SSSNode.createSSSNode(MeshExplorer.INTERNALNS+"Security", MeshExplorer.INTERNALNS);
	static final SSSNode queryPass =  SSSNode.createSSSNode(MeshExplorer.INTERNALNS+"queryPass", MeshExplorer.INTERNALNS);
	static final SSSNode clueText =  SSSNode.createSSSNode(MeshExplorer.INTERNALNS+"clueText", MeshExplorer.INTERNALNS);
	static final SSSNode textPass =  SSSNode.createSSSNode(MeshExplorer.INTERNALNS+"textPass", MeshExplorer.INTERNALNS);

	public Link(String name,final String location,Links parent, boolean ComputerOpen) {

		//super(AxisLayout.horizontal().gap(5));
		super(new AbsoluteLayout());

		setup(name, location, parent, ComputerOpen);	
		
		
			     
		//super.add(gotoLinkButton);


	}

	private void setup(String name, final String location, Links parent,
			boolean ComputerOpen) {
		this.ComputerOpen=ComputerOpen;
		currentParent = parent; //should be changed if viewed from elsewhere
		//super();
		// final Group widget = new Group(AxisLayout.horizontal()).addStyles(
		 //           Style.BACKGROUND.is(Background.solid(Colors.CYAN)));
		 
		//widget.setConstraint(new AbsoluteLayout.Constraint(
         //       new BoxPoint(10, 10), new BoxPoint(10, 10),
        //        new Dimension(50, 50)));
			
	//	super.setConstraint(AxisLayout.stretched());
		super.setStyles(Style.BACKGROUND.is(Background.solid(Color.argb(255, 250,50, 55))));
		
		
		Location = location;
		LinkName = name;

		gotoLinkButton = new Button(UNKNOWEN+location);
		
		gotoLinkButton.setStyles(Style.BACKGROUND.is(Background.blank()));
		
		gotoLinkButton.clicked().connect(new UnitSlot() {
			@Override public void onEmit () {


			

				switch (currentMode)
				{
				case Unknown:
					 scan();
					break;
				case Locked:
					break;
				case Open:
					MeshExplorer.gotoLocation(linksToThisPC);
					
					break;
				case Scanning:
					break;
				default:
					break;


				}

			}
		});

		
		super.add(AbsoluteLayout.at(ProgressBar, 0, 0,50,20));
		
		super.add(AbsoluteLayout.at(gotoLinkButton, 50, 5));
	}

	public Link(SSSNode sssNode, Links parent) {
		
		super(new AbsoluteLayout());
		linksToThisPC=sssNode;
		setup(sssNode.getPLabel(),sssNode.getPURI(),parent, false);	
		
	}

	private void scan(){
		
		currentMode = LinkMode.Scanning;
		
		gotoLinkButton.text.update(SCANNING+Location);
		
		super.setStyles(Style.BACKGROUND.is(Background.solid(Color.argb(255, 50,50, 255))));
		
		ProgressBar.setStyles(Style.BACKGROUND.is(Background.solid(Color.argb(255, 250,50, 55))));


		currentParent.startScanningLink(this);
		


	}
	private void setLockedStyle(){
		
		super.setStyles(Style.BACKGROUND.is(Background.solid(Color.argb(255,255,30,20))));
		ProgressBar.setVisible(false);
		
	}
	private void setScanningStyle(){
		
	}
	
	private void setOpenStyle(){
		
		super.setStyles(Style.BACKGROUND.is(Background.solid(Color.argb(255,0,30,200))));
		ProgressBar.setVisible(false);
	}
	
	

	//used to indicate the link is being scanned
	//speed is based on Node timed security / scanner speed
	public void setScanningAmount(int Percentage){
		
		float pixals = (super.size().width()/100)*Percentage;
		
		
		//crude, we should really adjust size rather then re-adding it
		super.add(AbsoluteLayout.at(ProgressBar, 0, 0,pixals,20));
		//have to re-add this one to make sure its on top *(dunno how to set z-index)
		super.add(AbsoluteLayout.at(gotoLinkButton, 50, 0));	
		
		
	}
	//node is locked by Security (only known after a scan)
	public void setLocked(){

	}
	//node is unlocked and can be accessed
	public void setOpen(){

	}
	public void setEnable(){
		gotoLinkButton.setEnabled(true);

	}

	public void setDisable(){
		gotoLinkButton.setEnabled(false);

	}

	
	public void stepForwardScanningAmount(int SPEEDSTEP) {
		
		PercentageScanned = PercentageScanned + SPEEDSTEP;
		
		if (PercentageScanned>=100) {
			PercentageScanned = 100;

			setScanningAmount(PercentageScanned);
			scanComplete();
			
		} else {
		
		setScanningAmount(PercentageScanned);
		}
	}

	private void scanComplete() {
		ComputerOpen = true;
		//detect if its secured by anything 
		if (linksToThisPC!=null){
			
			ArrayList<SSSNode> allSecuredPCs = SSSNodesWithCommonProperty.getAllNodesWithPredicate(SecuredBy);
			
			if (allSecuredPCs.contains(linksToThisPC)){
				ComputerOpen = false;
			}
						
		}
		
		
		if (!ComputerOpen){
			currentMode = LinkMode.Locked;
			setLockedStyle();
			gotoLinkButton.text.update(LOCKED+Location+" )");
		} else {
			currentMode = LinkMode.Open;
			setOpenStyle();
			gotoLinkButton.text.update(Location);
		}

		
		
	}
}
