package lostagain.nl.html;

import playn.core.PlayN;
import playn.html.HtmlGame;
import playn.html.HtmlPlatform;

import com.darkflame.client.interfaces.SSSGenericFileManager;
import lostagain.nl.core.MeshExplorer;

public class MeshExplorerHtml extends HtmlGame {

  @Override
  public void start() {
    HtmlPlatform.Config config = new HtmlPlatform.Config();
    // use config to customize the HTML platform, if needed
    HtmlPlatform platform = HtmlPlatform.register(config);
    platform.assets().setPathPrefix("meshexplorer/");
    PlayN.run(new MeshExplorer(new HTMLFileManager()));
  }
}
