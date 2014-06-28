package lostagain.nl.core.gui;

import static playn.core.PlayN.log;

import java.util.ArrayList;

import com.darkflame.client.semantic.SSSNode;
import com.darkflame.client.semantic.SSSNodesWithCommonProperty;
import com.darkflame.client.semantic.QueryEngine.DoSomethingWithNodesRunnable;

import playn.core.CanvasImage;
import playn.core.Font;
import playn.core.GroupLayer;
import playn.core.ImageLayer;
import playn.core.Layer;
import playn.core.PlayN;
import playn.core.Pointer;
import playn.core.util.Clock;
import pythagoras.f.Point;
import pythagoras.i.IRectangle;
import pythagoras.i.Rectangle;
import lostagain.nl.core.StaticSSSNodes;
import lostagain.nl.core.SSSNodes.PlayersStartingLocation;
import lostagain.nl.core.interfaces.Software;
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

	/** will provide basic system information for the current location.
	 *  ImageLayer testimage
	 * currently used as a widget/tripleplay testing ground for stuff */
	 
	public LocationSecurityCracker() {
		
		super();	
		
		
		getUsersInventorys();
		//super.addElement(label,50,50);
		
	//	super.addElement(label2,50,5);
		
        
         //super.layer.addAt(testimage, 0, 60);
      
          /*

		
		
		//add a region as an expirement
		Rectangle testRestriction = new Rectangle(0,0,100,500);
	
		super.addRegion(testRestriction, "leftbox");
		
		Rectangle testRestrictionR = new Rectangle(100,0,100,500);
		
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

	

	public void addUsersInventory(SSSNode item,int x,int y){
		
		ImageLayer newinventory = super.addTextIcon(item.getPLabel(), x, y);
		
		
	}
	
	
	public void addUsersInventorys(ArrayList<SSSNode> contents){
		
		for (SSSNode item : contents) {
			
			int rx = (int) (Math.random() * 200); 
			int ry =(int) (Math.random() * 200);
			
			addUsersInventory(item,  rx,  ry);
			
			
		}
		
	}
	
	public void getUsersInventorys(){
		
		SSSNodesWithCommonProperty contentOfMACHINE =  SSSNodesWithCommonProperty.getSetFor(StaticSSSNodes.isOn, PlayersStartingLocation.computersuri); //.getAllNodesInSet(callback);


		DoSomethingWithNodesRunnable doThisAfter = new DoSomethingWithNodesRunnable(){

			@Override
			public void run(ArrayList<SSSNode> testresult, boolean invert) {
				log().info("populate contents");
				addUsersInventorys(testresult);
				}
								
			
			
		};
		
		
		
		
		if (contentOfMACHINE!=null){
			contentOfMACHINE.getAllNodesInSet(doThisAfter);
		}
		
	}
	
	@Override
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

}
