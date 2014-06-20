package lostagain.nl.java;

import com.darkflame.client.JavaFileManager;
import com.darkflame.client.SuperSimpleSemantics;
import com.darkflame.client.interfaces.SSSGenericFileManager;

import playn.core.PlayN;
import playn.java.JavaPlatform;
import lostagain.nl.core.MeshExplorer;

public class MeshExplorerJava {

  public static void main(String[] args) {
    JavaPlatform.Config config = new JavaPlatform.Config();
    // use config to customize the Java platform, if needed
    JavaPlatform.register(config);
    
    SSSGenericFileManager filereader =  JavaFileManager.internalFileManager;
    PlayN.run(new MeshExplorer(filereader));
  }
}
