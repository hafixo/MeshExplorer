package lostagain.nl.android;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.darkflame.client.interfaces.SSSGenericFileManager;

import android.content.Context;
import android.util.Log;


public class FileManager implements SSSGenericFileManager {
	
	

	private Context context;
	

	public FileManager(MeshExplorerActivity meshExplorerActivity) {
		
		context=meshExplorerActivity.getApplicationContext();
	}

	@Override
	public void getText(String location, FileCallbackRunnable runoncomplete,
			FileCallbackError runonerror, boolean forcePost, boolean useCache) {
		
		String contents = "";
	
		 try {
			 			 
			BufferedReader reader = new BufferedReader(
				        new InputStreamReader(context.getAssets().open(location), "UTF-8")
					 );
			
			if (!reader.ready()){
				runonerror.run("blah read error", null);
				return;
			}
			
			
			
			 // do reading, usually loop until end of file reading  
		    
			String mLine = "";
			
		    while (mLine != null) {
		    			    	 
		    	contents = contents + mLine; 

		    	mLine = reader.readLine();
		       
		    }

		    reader.close();
		    
		    //output the result
		    runoncomplete.run(contents, 200);
			
			
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
			runonerror.run("blah load error", e);
			
		} catch (IOException e) {
			
			e.printStackTrace();
			runonerror.run("blah load error", e);
			
		} 
		 
		 
		
	}

	@Override
	public void getText(String location, FileCallbackRunnable runoncomplete,
			FileCallbackError runonerror, boolean forcePost) {
		
		// assume no cache
		getText(location, runoncomplete,runonerror, forcePost, false);
		
		
	}
	
	
	
}
