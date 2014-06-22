package lostagain.nl.html;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import com.darkflame.client.interfaces.SSSGenericFileManager;
import com.darkflame.client.semantic.SSSNode;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.RequestBuilder.Method;
import com.google.gwt.http.client.Response;

/** Manages text files **/
public class HTMLFileManager implements SSSGenericFileManager  {
	
	
	static Logger Log = Logger.getLogger("JAM.FileManager");
	
	/** If the game is running from a local source, php is naturally not used.
	 * Because of this, files are accessed directly at this location,
	 * rather then via "textfetcher.php **/
	public static String LocalFolderLocation = ""; //JAM.theme.get("localloc");

	enum fetchmode {
		local,remote
	}
	
	static fetchmode currrentmode = fetchmode.local;
	
	
	/** The location and name of the textfetcher.php that returns requested text files **/
	public static final String textfetcher_url = "text%20fetcher.php"; //$NON-NLS-1$

	/** A cache of all known text files associated with their filename**/
	static HashMap<String,String> allCachedTextFiles = new HashMap<String,String>();
	
	/** gets a bit of text using a php file,  <br>
	 * to help hide its location from the player. <br>
	 * You can set if you want this to add to the cache or not. <br>
	 * By default, everything is cached unless you use a POST request  <br>**/
	public static void GetTextFile(final String fileurl,
			final FileCallbackRunnable callback, FileCallbackError onError, boolean forcePOST) {
		
		if (!forcePOST){
			GetTextFile(fileurl,callback,  onError,  forcePOST, true);
		} else {
			GetTextFile(fileurl,callback,  onError,  forcePOST, false);
		}
		
	}
	

	
	public static void GetTextFile(final String fileurl,
			final FileCallbackRunnable callback, final FileCallbackError onError, boolean forcePOST,final boolean UseCache) {

		
		
		//if useCache is on, first we search for the filename in the cache
		if (UseCache){
			
			String contents = allCachedTextFiles.get(fileurl);
			
			if (contents!=null){
				Log.info("contents found in cache");
				callback.run(contents, Response.SC_OK);
				
				
			}
			
			
		}
		
		
		//if not a cache, we create a new RequestCallback for a real sever request response
		RequestCallback responseManagment = new RequestCallback(){
			@Override
			public void onResponseReceived(Request request, Response response) {	
				
				//add to cache if not a 404	
				if (response.getStatusCode()==Response.SC_OK && UseCache){
					Log.info("found file, storing in cache");
					allCachedTextFiles.put(fileurl,response.getText());
				}
				
				Log.info("got file,running callback ");
				callback.run(response.getText(), response.getStatusCode());
				
			}

			@Override
			public void onError(Request request, Throwable exception) {
				
				onError.run("",exception);
				
				
			}
			
		};
		
		
		if (currrentmode==fetchmode.remote) {
			RequestBuilder requestBuilder = new RequestBuilder(
					RequestBuilder.POST, textfetcher_url);

			try {
				requestBuilder.sendRequest("FileURL=" + fileurl, responseManagment);
			} catch (RequestException e) {
				e.printStackTrace();
				System.out
						.println("can not connect to file via php:" + fileurl);
			}
		} else {
			Method requestType = RequestBuilder.GET;
			if (forcePOST) {
				requestType = RequestBuilder.POST;
			}

			RequestBuilder requestBuilder = new RequestBuilder(requestType,
					LocalFolderLocation + fileurl);

			try {
				requestBuilder.sendRequest("", responseManagment);
			} catch (RequestException e) {
				System.out.println("can not connect to file:" + fileurl); //$NON-NLS-1$
				e.printStackTrace();

				// special runnable commands on error if any
				if (onError != null) {

					onError.run("could not event send request:"+e.getLocalizedMessage(),null);

				}

			}

		}

	}
	
	/** legacy method. Should be phased out.
	 * Hay, look, I used a deprecated! Didnt do that before whoa...getting to be a real coder now **/	
	@Deprecated 
	public static void GetTextSecurelyOLDMETHOD_DEPR(final String fileurl,
			final RequestCallback callback, final Runnable onError, boolean forcePOST) {

		if (LocalFolderLocation.length()<3) {
			RequestBuilder requestBuilder = new RequestBuilder(
					RequestBuilder.POST, textfetcher_url);

			try {
				requestBuilder.sendRequest("FileURL=" + fileurl, callback);
			} catch (RequestException e) {
				e.printStackTrace();
				System.out
						.println("can not connect to file via php:" + fileurl);
			}
		} else {
			Method requestType = RequestBuilder.GET;
			if (forcePOST) {
				requestType = RequestBuilder.POST;
			}

			RequestBuilder requestBuilder = new RequestBuilder(requestType,
					LocalFolderLocation + fileurl);

			try {
				requestBuilder.sendRequest("", callback);
			} catch (RequestException e) {
				System.out.println("can not connect to file:" + fileurl); //$NON-NLS-1$
				e.printStackTrace();

				// special runnable commands on error if any
				if (onError != null) {

					onError.run();

				}

			}

		}

	}



	@Override
	public void getText(String location,
			FileCallbackRunnable runoncomplete, FileCallbackError runonerror,
			boolean forcePost, boolean useCache) {
		
		HTMLFileManager.GetTextFile(location, runoncomplete, runonerror, forcePost, useCache);
		
		
	}



	@Override
	public void getText(String location,
			FileCallbackRunnable runoncomplete, FileCallbackError runonerror,
			boolean forcePost) {

		HTMLFileManager.GetTextFile(location, runoncomplete, runonerror, forcePost);
		
	}

	/** callback class type designed to be used as a response when 
	 * fetching text from a server, we now use the identical class's 
	 * in the SSSGenericFileManager class **/
//	
//	public static interface FileCallbackRunnable {		
//		void run(String responseData, int responseCode);
//
//	}
//	/** callback class type designed to be used when theres an error fetching text from a server */
//	public static interface FileCallbackError {		
//		void run(String errorData, Throwable exception);
//
//	}
//	
	

}



