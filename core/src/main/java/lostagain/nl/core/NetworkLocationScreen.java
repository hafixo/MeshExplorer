package lostagain.nl.core;

import static playn.core.PlayN.graphics;
import static playn.core.PlayN.log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Logger;

import javax.swing.SpringLayout.Constraints;

import lostagain.nl.core.SSSNodes.PlayersStartingLocation;
import lostagain.nl.core.gui.Email;
import lostagain.nl.core.gui.MessagesPage;
import lostagain.nl.core.gui.Links;
import lostagain.nl.core.gui.LocationSecurityCracker;
import lostagain.nl.core.gui.LocationsContents;
import lostagain.nl.core.gui.NavigationBar;
import lostagain.nl.core.gui.Taskbar;
import lostagain.nl.core.interfaces.Software;

import com.darkflame.client.query.Query;
import com.darkflame.client.query.QueryElement;
import com.darkflame.client.semantic.QueryEngine;
import com.darkflame.client.semantic.SSSNode;
import com.darkflame.client.semantic.SSSNodesWithCommonProperty;
import com.darkflame.client.semantic.QueryEngine.DoSomethingWithNodesRunnable;

import playn.core.Color;
import playn.core.util.Clock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack.Predicate;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Container;
import tripleplay.ui.Group;
import tripleplay.ui.Interface;
import tripleplay.ui.Label;
import tripleplay.ui.Layout;
import tripleplay.ui.Layout.Constraint;
import tripleplay.ui.Root;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.ui.layout.BorderLayout;
import tripleplay.util.Colors;

public class NetworkLocationScreen extends GameScreen implements Predicate  {
	int BackCol;


	
	
	static Logger Log = Logger.getLogger("NetworkNodeScreen");
	  
	  Group Top;

	private static HashMap<SSSNode,NetworkLocationScreen> knownLocations=new HashMap<SSSNode,NetworkLocationScreen>();
	
	 Taskbar maintaskbar;
	 
	//various pages on this computer
	 LocationSecurityCracker securityPage;
	 //Email emailpage  = new Email();	 
	 
	 MessagesPage emailpage = new MessagesPage(this);
	 
	 Links linkpage= new Links(this);
	 
	 LocationsContents locationsfiles = new LocationsContents(this);
	 
	 
	 //page currently open
	  Software CurrentlyOpenPage = emailpage;


	public SSSNode networkNode;
	

	public Boolean locked = true; //while its locked you can only access the security page
	
	
	

	private boolean setupdone=false;
	
	
	  
	private NetworkLocationScreen(int col,SSSNode locationsNode) {		
		super();
		
		//first we work out if this page is locked or not
		
		//if its the home computer it is always unlocked
		if (locationsNode == PlayersStartingLocation.computersuri){
			locked = false;

			securityPage = new LocationSecurityCracker(this,null); 
			
		} else {

			  //load the security, if any  
			  getSecurity(locationsNode);
			  
			  
			  /*
			//we check if its got security associated with it
			//we could optimise to skip this check if  we already know its secured
			//incoming links will know that already
			ArrayList<SSSNode> allSecuredPCs = SSSNodesWithCommonProperty.getAllNodesWithPredicate(StaticSSSNodes.SecuredBy);
			
			//if so, then lock it.
			if (allSecuredPCs.contains(locationsNode)){
				locked = true;
			} else {
				locked = false;
			}
			*/
			
		}
		
		
		
		
		BackCol = col;
		this.networkNode=locationsNode;
				
		knownLocations.put(locationsNode, this);
      
	}

	
	@Override
	protected void createIface() {
		
		//if this screen has not been setup yet, we set it up, else we do nothing
		if (!setupdone){
		 
		   createLayout(networkNode.getPLabel());
		   
		   loadNodesData(networkNode);
		} 
		
		setupdone = true;
		
		  
	    return;
	    
	}
	

    @Override public void wasShown () {
        super.wasShown();
        log().info(this + ".wasShown()");
        
        //always needs to be updated
  	  updatetopbar();
  	  
  	  //update security
  	  securityPage.clearInventorys();
  	  securityPage.getUsersInventorys();
  	
  	  //update contents
  	  getContentOfMachine(networkNode);
		
    }


	private void updatetopbar() {
		
		log().info("UPDATING bar to "+networkNode.getPLabel());
		
		MeshExplorer.NavigationTopBar.setLocation(networkNode.getPLabel());  	 
		Top.add( MeshExplorer.NavigationTopBar);
	
	
	}

private void createLayout(String CurrentLocation) {
	_root.setStyles(Style.BACKGROUND.is(Background.solid(Colors.PINK)));

	
	BorderLayout border = new BorderLayout(3);
	  Group mainLayout = new Group(border);

	  mainLayout.setConstraint(AxisLayout.stretched());
	  
	   Top    = new Group(AxisLayout.horizontal(), Style.HALIGN.center).
			    setConstraint(BorderLayout.NORTH);
	  
	//  NavigationBar 
	  MeshExplorer.NavigationTopBar.setLocation(CurrentLocation);
	  //Container.removeFromParent( MeshExplorer.NavigationTopBar, false);
	 
	  	  Top.add( MeshExplorer.NavigationTopBar);
	  
	  Group Bottom = new Group(AxisLayout.vertical(), Style.HALIGN.center).
			    setConstraint(BorderLayout.SOUTH).add(
					      new Button("Lower "));
	  
	  
	  Group Left = new Group(AxisLayout.vertical(), Style.HALIGN.center).
			    setConstraint(BorderLayout.WEST);
	  
	  
			    
			    
	//  Left.add(new Button("TaskBar"));
	   maintaskbar  = new Taskbar(this);
	  Left.add(maintaskbar);
	  
	 //    Background colorBg = Background.solid(Color.argb(255, 255, 20, 0));
	//  Left.addStyles(Style.BACKGROUND.is(colorBg));
	  

	     Group Right = new Group(AxisLayout.horizontal()).
				    setConstraint(BorderLayout.EAST).add(
						      new Button("east"));
		  
	     //Button middleContents = new Button("Contents");
	     //Group emailpageiface =  emailpage.createIface();     
	     
	  //   emailpageiface.setConstraint(AxisLayout.stretched());
	     
	     emailpage.Hide();
	     CurrentlyOpenPage=emailpage;
	     
	    
	     //create all pages hidden
	     securityPage.Hide();
	     locationsfiles.Hide();
	     linkpage.Hide();
	     
	  Group Center = new Group(AxisLayout.vertical().offStretch().stretchByDefault()).
			    setConstraint(BorderLayout.CENTER);
	  
	  
	  //add all the possible middle pages
	  Center.add(emailpage);	  
	  Center.add(securityPage);
	  Center.add(linkpage);
	  Center.add(locationsfiles);
	  
	  //we correct taskbar locked icon status	 status
	  maintaskbar.setSecurityIconOn(locked);
	  
	  
	  //show the security page by default if locked, else we goto the link page
	  if (locked) {
		  gotoSecurity();
	  } else {
		  gotoLinks();
	  }
	  
	     Background centerbg = Background.solid(Color.argb(255, 44, 255, 0));
	     Center.addStyles(Style.BACKGROUND.is(centerbg));
	     
	  mainLayout.add(Top);
	 // mainLayout.add(Bottom);
	  mainLayout.add(Left);
	  //mainLayout.add(Right);
		  
		// Button test = new Button("test");
		//  test.setConstraint(BorderLayout.CENTER);
	  mainLayout.add(Center);
	 
	   Background mls = Background.solid(BackCol);	
	  mainLayout.addStyles(Style.BACKGROUND.is(mls));
	  
	  
	    // create our UI manager and configure it to process pointer events
	   // iface = new Interface();
	    
	// Root root = iface.createRoot(AxisLayout.vertical().gap(0).offStretch(), stylesheet(), layer);
	  
	  //Root root = _root;
	  
	//  _root.setSize(graphics().width(), graphics().height());
      
      //Background bg = Background.solid(0xFFCC99FF).inset(0, 0, 5, 0);
     
    //  _root.addStyles(Style.BACKGROUND.is(bg));
      
	//_root.setConstraint(AxisLayout.stretched());
      
	 //   Label test=    new Label("PlayN Demos:");
	    
	       
	    // Group _root2 = new Group(AxisLayout.vertical().offStretch()).setConstraint(AxisLayout.stretched());
	   //  _root2.add(0, mainLayout);
	    
	  //  layer.add(root.layer);

      _root.add( mainLayout);
      
	   //  root.add(0,_root);
	     
	    //graphics().rootLayer().add(root.layer);
	     
	 //    graphics().rootLayer().add(root.layer);
}
protected void populateContents(ArrayList<SSSNode> testresult) {
	//clear existing lists
	locationsfiles.removeAll();
	
	Log.info("removing emails ");
	emailpage.removeAllMessages();
	
	
	Log.info("_____________contents:  "+testresult.size());
	
	for (SSSNode sssNode : testresult) {
		
		if (sssNode.isOrHasParentClass(StaticSSSNodes.software.getPURI())){
		
			locationsfiles.addObjectFile(sssNode);
		
		}
		
		
		if (sssNode.isOrHasParentClass(StaticSSSNodes.messages.getPURI())){

			Log.info(" adding email");
			emailpage.addEmailLocation(sssNode);
		
		}
		

		
	}
	
	
}
protected void populateVisibleComputers(ArrayList<SSSNode> testresult) {
	Log.info("computers visible to this = "+testresult.size());
	linkpage.clearLinks();
	
	for (SSSNode sssNode : testresult) {
		
		linkpage.addLink(sssNode);
		
		
	}
	
	
}
/** gets the content of the supplied location **/
private void getContentOfMachine(SSSNode tothisnode){
	
	SSSNodesWithCommonProperty contentOfMACHINE =  SSSNodesWithCommonProperty.getSetFor(StaticSSSNodes.isOn, tothisnode); //.getAllNodesInSet(callback);

    //Query realQuery = new Query(" ison=alicespc ");
    
	DoSomethingWithNodesRunnable callback2 = new DoSomethingWithNodesRunnable(){

		@Override
		public void run(ArrayList<SSSNode> testresult, boolean invert) {
			
			if (testresult.size()>0){
				
				//remove duplicates by using an HashSet
				// add elements to al, including duplicates
				HashSet hs = new HashSet();
				hs.addAll(testresult);
				testresult.clear();
				testresult.addAll(hs);
				
			Log.warning("populate contents");
			 populateContents(testresult);
			 
			 
			}
							
		}
		
	};
	
  //  QueryEngine.processQuery(realQuery, false, null, callback2);
    
	if (contentOfMACHINE!=null){

		Log.warning("getting contents:"+contentOfMACHINE.isLoaded);
		Log.warning("getting contents:"+contentOfMACHINE.getCommonPrec()+":"+contentOfMACHINE.getCommonValue());
		Log.warning("getting contents:"+contentOfMACHINE.getSourceFiles().toString());
		Log.warning("getting contents:"+contentOfMACHINE.getLefttoLoad() );
		
		contentOfMACHINE.getAllNodesInSet(callback2);
	}
	
	
}

/** gets the locations visible to the supplied location **/
private void getVisibleMachines(SSSNode tothisnode){
	   
	//String allNodes = SSSNode.getAllKnownNodes().toString();
	
	//Log.info("all nodes"+allNodes.toString());


	Log.info("---------------------------------------------------------------===================----------------");
	//SSSNodesWithCommonProperty VisibleMachines =  SSSNodesWithCommonProperty.getSetFor(visibletest, everyonetest); //.getAllNodesInSet(callback);

	String thisPURI = tothisnode.getPURI(); ///"C:\\TomsProjects\\MeshExplorer\\bin/semantics/DefaultOntology.n3#bobspc";
	
	
	Query realQuery = new Query("(me:visibleto=me:everyone)||(me:visibleto="+thisPURI+")");
	
	//populate when its retrieved
	DoSomethingWithNodesRunnable callback = new DoSomethingWithNodesRunnable(){

		@Override
		public void run(ArrayList<SSSNode> testresult, boolean invert) {
			
			
			
			Log.warning("populateVisibleComputers");
			 populateVisibleComputers(testresult);
			 
							
		}
		
	};
	
	 QueryEngine.processQuery(realQuery, false, null, callback);
	 
//VisibleMachines.getAllNodesInSet(callback);
	
	
}
private void loadNodesData(SSSNode mycomputerdata) {
	  System.out.print("loading node:"+mycomputerdata);
	  
	  // get the data for this node
	  
	  
	  //first load the links visible to this one
	  getVisibleMachines(mycomputerdata);
	  
	  //then contents
	  getContentOfMachine(mycomputerdata);
	  
	  
}



private void getSecurity(SSSNode mycomputerdata) {
	
	locked = false;
	SSSNode securedBy = null;
	
	Log.info("getting security for:"+mycomputerdata.PURI);
	
	
	HashSet<SSSNodesWithCommonProperty> sets = SSSNodesWithCommonProperty.getCommonPropertySetsContaining(mycomputerdata.getPURI());
	

	Log.info("sets:"+sets.size());

	
	for (SSSNodesWithCommonProperty sssNodesWithCommonProperty : sets) {
		
		if (sssNodesWithCommonProperty.getCommonPrec()==StaticSSSNodes.SecuredBy){
			
			securedBy = sssNodesWithCommonProperty.getCommonValue();
			Log.info("security found:"+securedBy.getPURI());
			
			locked = true;
			break;
		}
		
	}
	
	securityPage = new LocationSecurityCracker(this,securedBy); 
	
	//find this machines security
	
	
	
}


public  void gotoSecurity() {
  	
	  CurrentlyOpenPage.Hide();
	  securityPage.Show();

	  maintaskbar.highlight(maintaskbar.securityButton);
	  CurrentlyOpenPage=securityPage;	
}

public  void gotoLinks() {
	  
	 if (!locked) {
		 
	  CurrentlyOpenPage.Hide();
	  linkpage.Show();

	  maintaskbar.highlight(maintaskbar.networkButton);
	  CurrentlyOpenPage=linkpage;	
	  
	  
	 }
	 
	 
}

public  void gotoNodeViewer() {
	 if (!locked) {
	//  CurrentlyOpen.Hide();
	 // securityPage.Show();
	//  CurrentlyOpen=securityPage;	
	 }
}

public  void gotoEmail() {
	  
	 if (!locked) {
		 
	  CurrentlyOpenPage.Hide();
	  
	  emailpage.Show();

	  maintaskbar.highlight(maintaskbar.messageButton);
	  
	  CurrentlyOpenPage=emailpage;	
	 }
}
public  void gotoContents() {
	
	  if (!locked) {
		  

		  maintaskbar.highlight(maintaskbar.softwareButton);
      	
	  CurrentlyOpenPage.Hide();
	  locationsfiles.Show();
	  CurrentlyOpenPage=locationsfiles;	
	  }
}




public static NetworkLocationScreen getNetworkNode(int cyan, SSSNode linksToThisPC) {
	
	NetworkLocationScreen existingLocation = knownLocations.get(linksToThisPC);
	

	if (existingLocation==null) {		
		//if theres none already existing we try creating a new one
		existingLocation = new NetworkLocationScreen( cyan, linksToThisPC);



	} else {
		
	}
	
	return existingLocation;
}


@Override 
public void paint (Clock clock) {
	  super.paint(clock);
	  
}

@Override 
public void update (int delta) {
    super.update(delta);
    securityPage.update(delta);
    
}

@Override
/** really unintuatively named method that determines if the supplied screen is the same as this one **/
public boolean apply(Screen screen) {
	
	if (this.equals(screen)){
		return true;
	}
	
	return false;
}


public void unlockComputer() {

	locked = false;
	maintaskbar.setSecurityIconOn(locked);
	
	if (!(networkNode==null)){
		PlayersStartingLocation.addUnlockedLink(networkNode);
	}
	 
}

}
