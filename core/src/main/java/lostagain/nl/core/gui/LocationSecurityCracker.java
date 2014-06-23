package lostagain.nl.core.gui;

import static playn.core.PlayN.log;
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
	 ImageLayer testimage;
	 Label label = new Label("test lab");
	 Label label2 = new Label("test lab3");
	 Label label3 = new Label("test lab4");
	/** will provide basic system information for the current location.
	 *  ImageLayer testimage
	 * currently used as a widget/tripleplay testing ground for stuff */
	 
	public LocationSecurityCracker() {
		
		super();	
		super.addElement(label,50,50);
		
	//	super.addElement(label2,50,5);
		 CanvasImage image = PlayN.graphics().createImage(100, 100);
         StringBuffer text = new StringBuffer();
         text.append("la la la");
         
          final TextStyle TEXT = new TextStyle().
        	        withFont(PlayN.graphics().createFont("Helvetiva", Font.Style.PLAIN, 72));
         StyledText.span(text.toString(), TEXT).render(image.canvas(), 0, 0);
          testimage = PlayN.graphics().createImageLayer(image);
        
         //super.layer.addAt(testimage, 0, 60);
    
		super.addElement(label2,50,150);
		
		
		//add a region as an expirement
		Rectangle testRestriction = new Rectangle(0,0,100,500);
	
		super.addRegion(testRestriction, "leftbox");
		
		Rectangle testRestrictionR = new Rectangle(100,0,100,500);
		
		super.addRegion(testRestrictionR, "rightbox");
		
		super.restrictElementTo(label2, "leftbox");
		
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
/*
		
		
		 super.layer.addListener(_flicker);
		 _flicker.reset(500, 500);
		 _flicker.positionChanged(50, 50);
		 
		 super.layer.setHitTester(new Layer.HitTester() {
	            public Layer hitTest (Layer layer, Point p) {

	          		 log().info("hit tester");
	          		 
	                return layer;//(p.x < 500) ? layer : null;
	            }
	        });
		 
		//spacer.setConstraint(new AbsoluteLayout.Constraint(position.point.get(), origin.point.get()));
		 CanvasImage image = PlayN.graphics().createImage(100, 100);
         StringBuffer text = new StringBuffer();
         text.append("la la la");
         
          final TextStyle TEXT = new TextStyle().
        	        withFont(PlayN.graphics().createFont("Helvetiva", Font.Style.PLAIN, 72));
         StyledText.span(text.toString(), TEXT).render(image.canvas(), 0, 0);
          testimage = PlayN.graphics().createImageLayer(image);
        
         //super.layer.addAt(testimage, 0, 60);
          super.add(label);
		
          label.setConstraint(Constraints.fixedSize(100, 50));
		
		
		//super.layer.addListener(_flicker);
		*/
		
	}

	/*
	protected XYFlicker _flicker = new XYFlicker() {
		
        @Override protected float friction () { return 0.001f; }
        
       
        @Override 
        public void onPointerStart (Pointer.Event event) {
        	
        	super.onPointerStart(event);        	

   		// log().info("onPointerStart");
   		 
        }
        @Override         
        public void update (float delta) {
        	// log().info("update "+super._position.x+" "+super._min.x+" "+super._max.x);
        	 super.update(delta);
        	 
        }
    };*/
	

    /** Prepares the stroller for the next frame, at t = t + delta. 
    public void update (float delta) {
        _flicker.update(delta);
        
       // testimage.setTranslation(_flicker.position().x(),_flicker.position().y());
        
        
    	AbsoluteLayout.at(label, -_flicker.position().x(),-_flicker.position().y());
        
    	//layer.setTranslation(_flicker.position().x(), _flicker.position().y());
    	
		// log().info("_flicker.position"+_flicker.position().x()+","+
			//	 super.layer.interactive());
    }
    */
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
