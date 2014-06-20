package lostagain.nl.core;

import static playn.core.PlayN.*;

import java.util.ArrayList;
import java.util.logging.Logger;

import com.darkflame.client.SuperSimpleSemantics;
import com.darkflame.client.interfaces.SSSGenericFileManager;
import com.darkflame.client.query.Query;
import com.darkflame.client.semantic.QueryEngine;
import com.darkflame.client.semantic.SSSIndex;
import com.darkflame.client.semantic.SSSNode;
import com.darkflame.client.semantic.QueryEngine.DoSomethingWithNodesRunnable;

import lostagain.nl.core.interfaces.Software;
import lostagain.nl.utilities.GameTimer;
import playn.core.Color;
import playn.core.Font;
import playn.core.Game;
import playn.core.GroupLayer;
import playn.core.Image;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.TextFormat;
import playn.core.TextLayout;
import playn.core.util.Clock;
import tripleplay.game.Screen;
import tripleplay.game.ScreenStack;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Group;
import tripleplay.ui.Interface;
import tripleplay.ui.Label;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.Stylesheet;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.ui.layout.BorderLayout;
import tripleplay.util.Colors;

import com.darkflame.client.interfaces.SSSGenericFileManager;

public class MeshExplorer extends Game.Default {
	

	static Logger Log = Logger.getLogger("MeshExplorer");
	
	 private GroupLayer base;
	  private Interface iface;
	  private GroupLayer layer;
	  
	  static Email emailpage;
	  static NodeViewer nodepage;
	//  static Links linkpage= new Links();
	  
	  static Software CurrentlyOpen = emailpage;
	  

	  public static final int UPDATE_RATE = 25;
	  protected final Clock.Source _clock = new Clock.Source(UPDATE_RATE);
	  
	private SSSGenericFileManager platformSpecificFileLoader;
	  
	
	public final static String INTERNALNS = "http://darkflame.co.uk/meshexplorer#";
	public static SSSNode mycomputerdata = SSSNode.createSSSNode("HomeMachine",INTERNALNS);

	  
  public MeshExplorer(SSSGenericFileManager platformSpecificFileLoader) {
    super(33); // call update every 33ms (30 times per second)
    
    this.platformSpecificFileLoader = platformSpecificFileLoader;
    
  }

  @Override
  public void init() {
	  
	  
	  SuperSimpleSemantics.setFileManager(platformSpecificFileLoader);	  
	  
	  SuperSimpleSemantics.setAutoloadLabels(true);
	  SuperSimpleSemantics.setPreloadIndexs(true);
	  SuperSimpleSemantics.clearAllIndexsAndNodes();
	  	  
	  SuperSimpleSemantics.setup();
	  
	  SuperSimpleSemantics.setLoadedRunnable(new Runnable() {			
			@Override
			public void run() {
							
			   loadFirstPC();
			}
		});
	  

	  final ArrayList<String> trustedIndexs = new  ArrayList<String>();  	  
	  trustedIndexs.add("\\semantics\\TomsNetwork.ntlist");
  	  SuperSimpleSemantics.loadIndexsAt(trustedIndexs);
  	  
	  
	  
	  
//	 final SSSNode Wood = SSSNode.getNodeByLabel("Wood");
//	System.out.print(Wood.getDirectParentsAsString());
	
	  
	  String CurrentLocation = "";
	  
	  layer = graphics().createGroupLayer();
	
	  
	 // ScreenStack test = new ScreenStack();
	  
	  Screen screen1 = new NetworkNodeScreen(Colors.BLUE,mycomputerdata);
	
	  
	  
	 // createLayout(CurrentLocation);
	        
	  _screens.push(screen1);
	     
		  
    
  }
//
//private void createLayout(String CurrentLocation) {
//	BorderLayout border = new BorderLayout(3);
//	  
//	  Group mainLayout = new Group(border);
//	  
//	  Group Top    = new Group(AxisLayout.horizontal(), Style.HALIGN.center).
//			    setConstraint(BorderLayout.NORTH);
//	  
//	  Label Titlebar = new Label("Title Bar: Home"+CurrentLocation);
//	  Top.add(Titlebar);
//	  
//	  Group Bottom = new Group(AxisLayout.vertical(), Style.HALIGN.center).
//			    setConstraint(BorderLayout.SOUTH).add(
//					      new Button("Lower "));
//	  
//	  
//	  Group Left = new Group(AxisLayout.vertical(), Style.HALIGN.center).
//			    setConstraint(BorderLayout.WEST);
//	  
//	  
//			    
//			    
//	//  Left.add(new Button("TaskBar"));
//	  Taskbar maintaskbar  = new Taskbar();
//	  Left.add(maintaskbar);
//	  
//	     Background colorBg = Background.solid(Color.argb(255, 255, 20, 0));
//	//  Left.addStyles(Style.BACKGROUND.is(colorBg));
//	  
//
//	     Group Right = new Group(AxisLayout.horizontal()).
//				    setConstraint(BorderLayout.EAST).add(
//						      new Button("east"));
//		  
//	     //Button middleContents = new Button("Contents");
//	     emailpage = new Email();
//	     Group middleContents=  emailpage.createIface();     	     
//	     middleContents.setConstraint(AxisLayout.stretched());
//	     emailpage.Hide();
//	     CurrentlyOpen=emailpage;
//	     
//	    
//	     //create all pages hidden
//	     nodepage = new NodeViewer();
//	     nodepage.Hide();
//	     
//	 //    linkpage = new Links();
//	 //    linkpage.Hide();
//	     
//	  Group Center = new Group(AxisLayout.horizontal()).
//			    setConstraint(BorderLayout.CENTER).add(
//			    		middleContents);
//	  
//	  
//	  //add all the other pages
//	  Center.add(nodepage);
//	 // Center.add(linkpage);
//	  
//	  //show the default page
//	  gotoEmail();
//	  
//	  
//	     Background centerbg = Background.solid(Color.argb(255, 44, 255, 0));
//	     Center.addStyles(Style.BACKGROUND.is(centerbg));
//	     
//	  mainLayout.add(Top);
//	  mainLayout.add(Bottom);
//	  mainLayout.add(Left);
//	//  mainLayout.add(Right);
//	  
//	  mainLayout.add(Center);
//	  
//	   Background mls = Background.solid(Color.argb(255, 0, 255, 0));	
//	  mainLayout.addStyles(Style.BACKGROUND.is(mls));
//	  
//	  mainLayout.setConstraint(AxisLayout.stretched());
//
//	    // create our UI manager and configure it to process pointer events
//	    iface = new Interface();
//	    
//	 Root root = iface.createRoot(AxisLayout.vertical().gap(0).offStretch(), stylesheet(), layer);
//	 root.addStyles(Style.BACKGROUND.is(background()), Style.VALIGN.top);
//	 root.setSize(graphics().width(), graphics().height());
//      
//      Background bg = Background.solid(0xFFCC99FF).inset(0, 0, 5, 0);
//     
//      root.addStyles(Style.BACKGROUND.is(bg));
//	 //   Label test=    new Label("PlayN Demos:");
//	    
//	       
//	     Group _root = new Group(AxisLayout.vertical().offStretch()).setConstraint(AxisLayout.stretched());
//	     _root.add(0, mainLayout);
//	    
//	  //  layer.add(root.layer);
//
//		    
//	     root.add(0,_root);
//	     
//	    //graphics().rootLayer().add(root.layer);
//	     
//	     graphics().rootLayer().add(root.layer);
//}
//  
  private void loadFirstPC() {
	  loadNodesData(mycomputerdata);
  }
  
  
  private void loadNodesData(SSSNode mycomputerdata) {
	  System.out.print("loading node:"+mycomputerdata);
	  // get the data for this node
	  
	  //first load the links visible to this one
	  getVisibleMachines(mycomputerdata);
	  
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
	
protected void populateVisibleComputers(ArrayList<SSSNode> testresult) {
		Log.info("computers visible to this = "+testresult.size());
		
		for (SSSNode sssNode : testresult) {
			
		//	linkpage.addLink(sssNode);
			
			
		}
		
		
	}




@Override
  public void update(int delta) {
    _clock.update(delta);
    _screens.update(delta);
    
    GameTimer.updateAllTimers(delta);
    
    if (iface != null) {
      iface.update(delta);
      
     
      
    } 
    
  }

  @Override
  public void paint(float alpha) {
    _clock.paint(alpha);
    _screens.paint(_clock);
    
    if (iface != null) {
      iface.paint(_clock);
    }
  }

  /** Returns the stylesheet to use for this screen. */
  protected Stylesheet stylesheet () {
      return SimpleStyles.newSheet();
  }

  /** Returns the background to use for this screen. */
  protected Background background () {
      return Background.bordered(0xFFCCCCCC, 0xFFCC99FF, 5).inset(5);
  }

  protected final static ScreenStack _screens = new ScreenStack() {
      @Override protected void handleError (RuntimeException error) {
          PlayN.log().warn("Screen failure", error);
      }
      @Override protected Transition defaultPushTransition () {
          return slide();
      }
      @Override protected Transition defaultPopTransition () {
          return slide().right();
      }
  };
  
public static void gotoLocation(SSSNode linksToThisPC) {
	
	  Screen screen1 = new NetworkNodeScreen(Colors.CYAN,linksToThisPC);
		        
	  _screens.push(screen1);
	
}
  
}
