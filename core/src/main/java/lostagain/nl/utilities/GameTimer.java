package lostagain.nl.utilities;

import static playn.core.PlayN.*;

import java.util.ArrayList;
import java.util.HashSet;

import playn.core.util.Callback;
 
public class GameTimer {
  
  public boolean running = false;
	
  private double startTime;
  private double elapseTime;
  private double endTime;
  
  Boolean repeat = false;
  private Callback<String> timerCallback;
  
  static HashSet<GameTimer> allTimers = new HashSet<GameTimer>();
  
  
	
  public GameTimer(double waitTime, Callback<String> callback) {
	 
    elapseTime = waitTime;
    timerCallback = callback;
  
  }
  
  public GameTimer(double waitTime, Callback<String> callback,Boolean repeat) {
		 
	    elapseTime = waitTime;
	    timerCallback = callback;
	    this.repeat = repeat;
	  
  }
  public void start(){
	 
    startTime = currentTime();
    endTime = startTime + elapseTime;
    running = true;
    allTimers.add(this);
  } 
  
  public void stop(){
	  
	allTimers.remove(this);
  	running = false;
	  
  }
  
  static public void updateAllTimers(float delta) {
	  
	  //loop over all known instances of GameTimer and run update in them
	  for (GameTimer timer : allTimers) {
		  timer.update(delta);
		  
		
		  
	  }
	  
  }
  
  //should be called from main update function 
  public void update(float delta) {
    
    if(running){
      if(currentTime() >= endTime){
        
        timerCallback.onSuccess("Time has elapsed");
       
        if (!repeat){
        	allTimers.remove(this);
        	running = false;
        } else {
        	//new end time
        	 endTime = currentTime() + elapseTime;
        }
        
      }
    }	  
  }
  
}
