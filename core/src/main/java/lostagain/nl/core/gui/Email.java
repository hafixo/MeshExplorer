package lostagain.nl.core.gui;

import java.util.Iterator;
import java.util.logging.Logger;

import playn.core.Layer;
import playn.core.Log;

import com.darkflame.client.SuperSimpleSemantics;
import com.darkflame.client.interfaces.SSSGenericFileManager.FileCallbackError;
import com.darkflame.client.interfaces.SSSGenericFileManager.FileCallbackRunnable;
import com.darkflame.client.semantic.SSSNode;

import react.Slot;
import react.UnitSlot;
import tripleplay.ui.Background;
import tripleplay.ui.Button;
import tripleplay.ui.Element;
import tripleplay.ui.Field;
import tripleplay.ui.Group;
import tripleplay.ui.HistoryGroup;
import tripleplay.ui.Label;
import tripleplay.ui.Layout;
import tripleplay.ui.SizableGroup;
import tripleplay.ui.Slider;
import tripleplay.ui.Style;
import tripleplay.ui.Stylesheet;
import tripleplay.ui.layout.AxisLayout;
import tripleplay.ui.layout.BorderLayout;
import tripleplay.ui.layout.TableLayout;
import tripleplay.util.Colors;
import lostagain.nl.core.interfaces.Software;

public class Email  implements Software {
	
	Group  mainGroup;

	static Logger Log = Logger.getLogger("Email");
    HistoryGroup.Labels history = new HistoryGroup.Labels();
    
	    public Group createIface () {
	    		        
	        Label topbar = new Label("Messages:");
	        
	        Button test = new Button("test");
	        
	        final SizableGroup historyBox = new SizableGroup(AxisLayout.vertical());
	        
	        history.setConstraint(AxisLayout.stretched());
	        
	        historyBox.setConstraint(AxisLayout.stretched());
	        historyBox.add(test);
	        historyBox.add(history);

	        test.clicked().connect(addSome(history, "_", 11));
	    
	    
	        
	        Group top = new Group(AxisLayout.horizontal());
	        top.add(topbar);
	        	        
	        
	        
	        Stylesheet labelstyle = Stylesheet.builder().add(Label.class,
		            Style.BACKGROUND.is(Background.composite(
			                Background.blank().inset(0, 2),
			                Background.bordered(Colors.WHITE, Colors.BLACK, 1).inset(10))),
			            Style.TEXT_WRAP.on, Style.HALIGN.center).create();
	      
	       
	        history.setStylesheet(labelstyle);
	        
	        
	        
	        
	        history.addStyles(Style.BACKGROUND.is(Background.beveled(	        		
	            Colors.CYAN, Colors.brighter(Colors.CYAN), Colors.darker(Colors.CYAN)).inset(5)));
	   
	        
	        mainGroup = new Group(AxisLayout.vertical().offStretch());
	        
	        mainGroup.add(top, historyBox.setConstraint(AxisLayout.stretched())).addStyles(
		    	            Style.BACKGROUND.is(Background.blank().inset(5)));
	        
	        mainGroup.setConstraint(AxisLayout.stretched());
	        
	        return mainGroup;
	    }

	    protected UnitSlot addSome (final HistoryGroup.Labels group, final String Message, final int num) {
	        return new UnitSlot() {
	            @Override public void onEmit () 
	            {
	            	 removeAll(); 
	                for (int ii = 0; ii < num; ++ii) {
	                	
	                    group.addItem(Message+Math.random());
	                    
	                }
	            }
	        };
	    }
	    
	    protected void addEmail (final HistoryGroup.Labels group, final String Message) 
	      {
	    		                	
	                    group.addItem(Message);	                    
	                
	    }

	    
	@Override
	public void onOpen() {

	}

	@Override
	public void Show() {
		mainGroup.setVisible(true);
	}

	@Override
	public void Hide() {
		mainGroup.setVisible(false);

	}

	public void addEmailLocation(SSSNode sssNode) {
		
		
		
		//load the data
		//we can borrow the SuperSimpleSemantics file load for this and 
		
		//set up the runnable for when the data is retrieved
		FileCallbackRunnable runoncomplete = new FileCallbackRunnable(){
			@Override
			public void run(String responseData, int responseCode) {
				
				addEmail(history,responseData);
				
			}
			
		};
		
		FileCallbackError runonerror = new FileCallbackError(){

			@Override
			public void run(String errorData, Throwable exception) {
				
				addEmail(history,errorData);
				
			}
			
		};
		
		String url = "/semantics/"+sssNode.getPLabel();
		
		//trigger the file retrieval
		SuperSimpleSemantics.fileManager.getText(url, runoncomplete, runonerror, false);
		

		
	}

	
	public void removeAll() {

		
		Iterator<Element<?>> alllabels = ((HistoryGroup.Labels)history).iterator();
		
		
		while (alllabels.hasNext()) {
			Element<?> element = (Element<?>) alllabels.next();
			
			 ((HistoryGroup.Labels)history).removeFromParent(element, true);
			
		}

		
		//history.layer.removeAll();
		
	}

}
