package lostagain.nl.core.gui;

import static playn.core.PlayN.log;

import java.util.ArrayList;
import java.util.HashSet;

import com.darkflame.client.query.Query;
import com.darkflame.client.query.QueryElement;
import com.darkflame.client.semantic.QueryEngine;
import com.darkflame.client.semantic.SSSNode;
import com.darkflame.client.semantic.SSSNodesWithCommonProperty;
import com.darkflame.client.semantic.QueryEngine.DoSomethingWithNodesRunnable;

import playn.core.CanvasImage;
import playn.core.Color;
import playn.core.Font;
import playn.core.Gradient;
import playn.core.GroupLayer;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.util.Callback;
import playn.core.util.Clock;
import pythagoras.f.Point;
import pythagoras.i.IRectangle;
import pythagoras.i.Rectangle;
import lostagain.nl.core.NetworkLocationScreen;
import lostagain.nl.core.StaticSSSNodes;
import lostagain.nl.core.SSSNodes.PlayersStartingLocation;
import lostagain.nl.core.gui.DraggablesPanel.DragItem;
import lostagain.nl.core.interfaces.Software;
import lostagain.nl.core.utilities.GameTimer;
import tripleplay.anim.Flicker;
import tripleplay.ui.Background;
import tripleplay.ui.Constraints;
import tripleplay.ui.Field;
import tripleplay.ui.Group;
import tripleplay.ui.Label;
import tripleplay.ui.Layout;
import tripleplay.ui.Style;
import tripleplay.ui.layout.AbsoluteLayout;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.ui.util.XYFlicker;
import tripleplay.util.Colors;
import tripleplay.util.StyledText;
import tripleplay.util.TextStyle;


public class LocationSecurityCracker extends DraggablesPanel  implements Software {

	private static final String INVENTORYREGION ="InventoryRegion";
	NetworkLocationScreen locationProtectedByThis;
	
	private SSSNode securedBy;
	ImageLayer VisualGuide;

	CanvasImage greenback;
	CanvasImage blueback;
	CanvasImage redback;
	
	ArrayList<DragItem> allItems = new ArrayList<DragItem>();
	
	/** if the security is passing a query, this string is the query **/
	private String QueryPass;
	
	/** Acceptable results for query pass **/
	public ArrayList<SSSNode> acceptableAnswers = new ArrayList<SSSNode>();

	private boolean readyForAnswer = false; //is true when everything is loaded and is ready to accept an answer
	private DragItem LockedText;
	private DragItem RequirementsText;
	
	/** displays the cracking minigame necessary to access a location.
	 * if already open, displays locations stats 
	 * @param securedBy */
	 
	public LocationSecurityCracker(NetworkLocationScreen networkLocationScreen, SSSNode securedBy) {
		
		
		super();	
		locationProtectedByThis = networkLocationScreen;
		this.securedBy = securedBy;
		
		
		//securedBy = securedByThis;
		if (securedBy!=null){
			
			setAsLocked();
			
		} else {
			setAsUnlocked();
		}
		
        
		//ArrayList<SSSNode> allSecuredPCs = SSSNodesWithCommonProperty.getAllNodesWithPredicate(StaticSSSNodes.SecuredBy);

      
          /*

		
		
		//add a region as an experiment
		
		super.addRegion(testRestrictionR, "rightbox");
		
		super.restrictElementTo(testimage, "leftbox");
		
		super.addDropListenerToRegion("leftbox",new DropListener() {			
			@Override
			public void wasDropped(GroupLayer justdroppeditem, float cameFromX,
					float cameFromY) {
				// TODO Auto-generated method stub
				log().info("dropped on leftbox!");
			}
		});
		
		super.addDropListenerToRegion("rightbox",new DropListener() {			
			@Override
			public void wasDropped(GroupLayer justdroppeditem, float cameFromX,
					float cameFromY) {
				// TODO Auto-generated method stub
				log().info("dropped on rightbox!");
			}
		});

		*/
		
	}



	private void setAsUnlocked() {
		
		//add to players data as unlocked
		if (!(locationProtectedByThis.networkNode==null)){
			PlayersStartingLocation.addUnlockedLink(locationProtectedByThis.networkNode);
		}
		
		//remove existing text if present
		if (LockedText!=null){
			super.removeIcon(LockedText);
			super.removeIcon(RequirementsText);
		}
		
		//add unlocked message and details
		super.addText("COMPUTER UNLOCKED : ",150,40, 10,10,false);
		
		
		
		
	}



	private void addAnswerDropTarget() {
		Rectangle droptargetregion = new Rectangle(400,10,100,100);
		super.addRegion(droptargetregion, "droptargetregion");
		super.addDropListenerToRegion( "droptargetregion",new DropListener() {			
			@Override
			public void wasDropped(DragItem justdroppeditem, float cameFromX,
					float cameFromY) {
					
				
				SSSNode ItemNode = (SSSNode) justdroppeditem.data;
				log().info("~item uri="+ItemNode.getPURI());
				//test
				
				if (acceptableAnswers.contains(ItemNode)){
					
					locationProtectedByThis.unlockComputer();
					
					log().info( "contains:"+acceptableAnswers.contains(ItemNode));
					acceptedAnsAnimation();
					setAsUnlocked();
					 
					//remove movement restriction
					LocationSecurityCracker.this.removeElementsRestriction(justdroppeditem);
							
					
				} else {
					
					rejectedAnsAnimation();		
					
				}
			
				
				
			}

		});
		
		
		//create backgrounds for visual guide 
		blueback = createBlueBack(droptargetregion);		
		redback = createRedBack(droptargetregion);
		greenback =  createAcceptedBack(droptargetregion);
		
		//add the same region as a visual guide		
		VisualGuide = PlayN.graphics().createImageLayer(blueback); 
		
	    DragItem newItem = new DragItem(VisualGuide,null);
	    
		super.addElement(newItem, 400, 10, false);
		
	}


private CanvasImage createAcceptedBack(Rectangle droptargetregion) {
		
		CanvasImage blueback = PlayN.graphics().createImage(droptargetregion.width, droptargetregion.height);
		
		Gradient grad =  PlayN.graphics().createRadialGradient(50,25, 50, new int[]{Colors.GREEN,Color.rgb(30,200,100)},new float[]{0,1});
		blueback.canvas().setFillGradient(grad);		 				
		blueback.canvas().fillRect(0, 0, droptargetregion.width, droptargetregion.height);
		blueback.setRepeat(true, true);
		
		return blueback;
		
	}
	private CanvasImage createBlueBack(Rectangle droptargetregion) {
		
		CanvasImage blueback = PlayN.graphics().createImage(droptargetregion.width, droptargetregion.height);
		
		Gradient grad =  PlayN.graphics().createRadialGradient(50,25, 50, new int[]{Colors.CYAN,Colors.BLUE},new float[]{0,1});
		blueback.canvas().setFillGradient(grad);		 				
		blueback.canvas().fillRect(0, 0, droptargetregion.width, droptargetregion.height);
		blueback.setRepeat(true, true);
		
		return blueback;
		
	}

	private CanvasImage createRedBack(Rectangle droptargetregion) {
		
		CanvasImage blueback = PlayN.graphics().createImage(droptargetregion.width, droptargetregion.height);
		
		Gradient grad =  PlayN.graphics().createRadialGradient(50,25, 50, new int[]{Colors.RED,Colors.PINK},new float[]{0,1});
		blueback.canvas().setFillGradient(grad);		 				
		blueback.canvas().fillRect(0, 0, droptargetregion.width, droptargetregion.height);
		blueback.setRepeat(true, true);
		
		return blueback;
		
	}


	private void setAsLocked() {
		//me:queryPass
		//add interface elements (non-dragable)
		 LockedText = super.addText("COMPUTER LOCKED : ",150,40, 10,10,false);
		 RequirementsText = super.addText("Requirements Not Yet Met:",350,40,  10, 40,false);
		
		 
		//get protection string
		HashSet<SSSNodesWithCommonProperty> securitysPropertys = SSSNodesWithCommonProperty.getCommonPropertySetsContaining(securedBy.PURI);
		
		for (SSSNodesWithCommonProperty propertset : securitysPropertys) {
			
			if (propertset.getCommonPrec()==StaticSSSNodes.queryPass){
				QueryPass = propertset.getCommonValue().getPLabel();
				
			}
			
			//save other data here
			
		}
		
		String protectionString = QueryPass;
		
		if (protectionString!=null)
		{
			//strip quotes
			if (protectionString.startsWith("\"")){
				protectionString = protectionString.substring(1);
				
			}
			if (protectionString.endsWith("\"")){
				protectionString = protectionString.substring(0, protectionString.length()-1);
						
			}
		}
		retrieveAnswersAsycn(protectionString);
		
		
		super.addText(protectionString, 500, 70, 10,50, false);
		
		Rectangle textregion = new Rectangle(0,0,500,100);
		super.addRegion(textregion, "textregion");
		super.addDropListenerToRegion( "textregion",new DropListener() {			
			@Override
			public void wasDropped(DragItem justdroppeditem, float cameFromX,
					float cameFromY) {
					
				justdroppeditem.contentLayer.setTranslation(cameFromX, cameFromY);
				
				
			}
		});
		
		//set inventory region
		Rectangle InventoryRegion = new Rectangle(0,0,450,450);
		super.addRegion(InventoryRegion,INVENTORYREGION);
		
		getUsersInventorys();
		addAnswerDropTarget(); 
		
	}

	

	private void retrieveAnswersAsycn(String protectionString) {
		
		Query answers = new Query(protectionString);
		
		DoSomethingWithNodesRunnable RunWhenDone = new DoSomethingWithNodesRunnable(){

			@Override
			public void run(ArrayList<SSSNode> newnodes, boolean invert) {
				
				acceptableAnswers.clear();
				acceptableAnswers.addAll(newnodes);
				
				log().info("answers="+acceptableAnswers.toString());
				
				//flag as ready for answer
				readyForAnswer = true;
				
				
			}
			
		};
		
		QueryEngine.processQuery(answers, false, null, RunWhenDone);
		
		
		
	}



	public void addUsersInventory(SSSNode item,int x,int y){
		
		DragItem newinventoryitem = super.addTextIcon(item.getPLabel(), x, y,true);
		newinventoryitem.data = item; //we can add extra data this way				
		allItems.add(newinventoryitem);
		
		//restrict movement of item to inventory area
		super.restrictElementTo(newinventoryitem, INVENTORYREGION);
		
	}	
	
	
	public void addUsersInventorys(ArrayList<SSSNode> contents, boolean randomMode){
		int disX = 0;
		int disY = 200; //where the inventory starts from.
		
		int i=0; //item number
		for (SSSNode item : contents) {
			
			int rx =0;
			int ry =0;
			
			if (randomMode){
			 rx = (int) (Math.random() * 200); 
			 ry = (int) (Math.random() * 200);
			} else {
			 ry = ((int)(i / 5)) * 100;	
			 rx = ((i % 5)) * 100;	
			}
			
			addUsersInventory(item,  rx+disX,  ry+disY);

			i++;
			
		}
		
	}
	public void clearInventorys(){
		
		for (DragItem icon : allItems) {
			
			super.removeIcon(icon);			
			
		}
		
	}
	
	
	public void getUsersInventorys(){
		
		SSSNodesWithCommonProperty contentOfMACHINE =  SSSNodesWithCommonProperty.getSetFor(StaticSSSNodes.isOn, PlayersStartingLocation.computersuri); //.getAllNodesInSet(callback);
	
		//We can also use a query, but we dont as its slower
		
	//	Query contentOfMachine = new Query(StaticSSSNodes.isOn.PURI+"="+PlayersStartingLocation.computersuri.PURI);
				
		DoSomethingWithNodesRunnable doThisAfter = new DoSomethingWithNodesRunnable(){

			@Override
			public void run(ArrayList<SSSNode> testresult, boolean invert) {
				ArrayList<SSSNode> softwarelist = new ArrayList<SSSNode>();
								
				for (SSSNode itemOnMachine : testresult) {
					
					if (itemOnMachine.isOrHasDirectParentClass(StaticSSSNodes.software.PURI)){
		
						softwarelist.add(itemOnMachine);
					}
					
					
				}
				
				log().info("populate contents");
					addUsersInventorys(softwarelist,false);
				}
								
			
			
		};
		
		//QueryEngine.processQuery(contentOfMachine, false, null, doThisAfter);
				
		
		if (contentOfMACHINE!=null){
			contentOfMACHINE.getAllNodesInSet(doThisAfter);
		}
		
	}
	
	@Override
	public void onOpen() {	
		
		log().info("_______________security open:");
		
	}


	private void rejectedAnsAnimation() {
		
		VisualGuide.setImage(redback);
		
		//delay before going back to norm
		GameTimer resetcolor = new GameTimer(550, new Callback<String>(){

			@Override
			public void onSuccess(String result) {
				VisualGuide.setImage(blueback);
				
			}

			@Override
			public void onFailure(Throwable cause) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		resetcolor.start();
		
	}
	private void acceptedAnsAnimation() {
		
		VisualGuide.setImage(greenback);
		
	}
	@Override
	public void Show() {
		onOpen();
		super.setVisible(true);
	}

	@Override
	public void Hide() {
		super.setVisible(false);

	}



	public SSSNode getSecuredBy() {
		return securedBy;
	}



	public void setSecuredBy(SSSNode securedBy) {
		this.securedBy = securedBy;
		
	}

}
