package lostagain.nl.android;

import lostagain.nl.core.MeshExplorer;
import playn.android.GameActivity;
import playn.core.PlayN;

//import lostagain.nl.core.MeshExplorer;

public class MeshExplorerActivity extends GameActivity {

  @Override
  public void main(){
    PlayN.run(new MeshExplorer(new FileManager(this)));
  }
}
