package lostagain.nl.core;

import static playn.core.PlayN.graphics;

import java.util.ArrayList;
import java.util.logging.Logger;

import lostagain.nl.core.interfaces.Software;

import com.darkflame.client.query.Query;
import com.darkflame.client.semantic.QueryEngine;
import com.darkflame.client.semantic.SSSNode;
import com.darkflame.client.semantic.QueryEngine.DoSomethingWithNodesRunnable;

import playn.core.Color;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Interface;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.ui.layout.BorderLayout;
import tripleplay.util.Colors;

public class NetworkNodeScreen extends GameScreen {
	int BackCol;


	static Logger Log = Logger.getLogger("NetworkNodeScreen");
	   Email emailpage;
	   NodeViewer nodepage;
	   Links linkpage= new Links(this);

	  Software CurrentlyOpen = emailpage;


	private SSSNode networkNode;
	
	  
	public NetworkNodeScreen(int col,SSSNode networkNode) {		
		super();
		BackCol = col;
		this.networkNode=networkNode;
		
	}

	
	@Override
	protected void createIface() {
				
		  // Group root = new Group(AxisLayout.vertical());		   	   
		   createLayout(networkNode.getPLabel());
		   
		   loadNodesData(networkNode);
		   
	    return;
	    
	}
	
	

private void createLayout(String CurrentLocation) {
	BorderLayout border = new BorderLayout(3);
	  
	  Group mainLayout = new Group(border);
	  
	  Group Top    = new Group(AxisLayout.horizontal(), Style.HALIGN.center).
			    setConstraint(BorderLayout.NORTH);
	  
	  Label Titlebar = new Label("CurrentlyAt: "+CurrentLocation);
	  Top.add(Titlebar);
	  
	  Group Bottom = new Group(AxisLayout.vertical(), Style.HALIGN.center).
			    setConstraint(BorderLayout.SOUTH).add(
					      new Button("Lower "));
	  
	  
	  Group Left = new Group(AxisLayout.vertical(), Style.HALIGN.center).
			    setConstraint(BorderLayout.WEST);
	  
	  
			    
			    
	//  Left.add(new Button("TaskBar"));
	  Taskbar maintaskbar  = new Taskbar(this);
	  Left.add(maintaskbar);
	  
	     Background colorBg = Background.solid(Color.argb(255, 255, 20, 0));
	//  Left.addStyles(Style.BACKGROUND.is(colorBg));
	  

	     Group Right = new Group(AxisLayout.horizontal()).
				    setConstraint(BorderLayout.EAST).add(
						      new Button("east"));
		  
	     //Button middleContents = new Button("Contents");
	     emailpage = new Email();
	     Group middleContents=  emailpage.createIface();     	     
	     middleContents.setConstraint(AxisLayout.stretched());
	     emailpage.Hide();
	     CurrentlyOpen=emailpage;
	     
	    
	     //create all pages hidden
	     nodepage = new NodeViewer();
	     nodepage.Hide();
	     
	 //    linkpage = new Links();
	     linkpage.Hide();
	     
	  Group Center = new Group(AxisLayout.horizontal()).
			    setConstraint(BorderLayout.CENTER).add(
			    		middleContents);
	  
	  
	  //add all the other pages
	  Center.add(nodepage);
	  Center.add(linkpage);
	  
	  //show the default page
	  gotoEmail();
	  
	  
	     Background centerbg = Background.solid(Color.argb(255, 44, 255, 0));
	     Center.addStyles(Style.BACKGROUND.is(centerbg));
	     
	  mainLayout.add(Top);
	  mainLayout.add(Bottom);
	  mainLayout.add(Left);
	//  mainLayout.add(Right);
	  
	  mainLayout.add(Center);
	  
	   Background mls = Background.solid(Color.argb(255, 0, 255, 0));	
	  mainLayout.addStyles(Style.BACKGROUND.is(mls));
	  
	  mainLayout.setConstraint(AxisLayout.stretched());

	    // create our UI manager and configure it to process pointer events
	   // iface = new Interface();
	    
	// Root root = iface.createRoot(AxisLayout.vertical().gap(0).offStretch(), stylesheet(), layer);
	  
	  //Root root = _root;
	  
	  _root.addStyles(Style.BACKGROUND.is(background()), Style.VALIGN.top);
	  _root.setSize(graphics().width(), graphics().height());
      
      Background bg = Background.solid(0xFFCC99FF).inset(0, 0, 5, 0);
     
      _root.addStyles(Style.BACKGROUND.is(bg));
      
	_root.setConstraint(AxisLayout.stretched());
      
	 //   Label test=    new Label("PlayN Demos:");
	    
	       
	    // Group _root2 = new Group(AxisLayout.vertical().offStretch()).setConstraint(AxisLayout.stretched());
	   //  _root2.add(0, mainLayout);
	    
	  //  layer.add(root.layer);

      _root.add(0, mainLayout);
      
	   //  root.add(0,_root);
	     
	    //graphics().rootLayer().add(root.layer);
	     
	 //    graphics().rootLayer().add(root.layer);
}
	
protected void populateVisibleComputers(ArrayList<SSSNode> testresult) {
	Log.info("computers visible to this = "+testresult.size());
	
	for (SSSNode sssNode : testresult) {
		
		linkpage.addLink(sssNode);
		
		
	}
	
	
}

private void getVisibleMachines(SSSNode tothisnode){
	   
	String allNodes = SSSNode.getAllKnownNodes().toString();
	
	Log.info("all nodes"+allNodes.toString());


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
	  
}



public  void gotoLinks() {
	  
	
	  CurrentlyOpen.Hide();
	  linkpage.Show();
	  CurrentlyOpen=linkpage;	
}

public  void gotoNodeViewer() {
		
	  CurrentlyOpen.Hide();
	  nodepage.Show();
	  CurrentlyOpen=nodepage;	
}

public  void gotoEmail() {
	  
	
	  CurrentlyOpen.Hide();
	  emailpage.Show();
	  CurrentlyOpen=emailpage;	
}

}
