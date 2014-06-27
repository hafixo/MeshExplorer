package lostagain.nl.core;

import static playn.core.PlayN.log;
import tripleplay.game.UIScreen;
import tripleplay.ui.Background;
import tripleplay.ui.Layout;
import tripleplay.ui.Root;
import tripleplay.ui.SimpleStyles;
import tripleplay.ui.Style;
import tripleplay.ui.Stylesheet;
import tripleplay.ui.layout.AxisLayout;

public abstract class GameScreen extends UIScreen
{

	public GameScreen () {
        
    }

    @Override public void wasAdded () {
        super.wasAdded();
        
        _root = iface.createRoot(createLayout(), stylesheet(), layer);
        
     //  _root.addStyles(Style.BACKGROUND.is(background()));
        _root.setSize(width(), height());
        createIface();
    }

    @Override public void wasShown () {
        super.wasShown();
        log().info(this + ".wasShown()");
        
		
    }

    @Override public void wasHidden () {
        super.wasHidden();
        log().info(this + ".wasHidden()");
    }

    /** Note; Screens dont get destroyed when removed, as they can still be active in the background.
     * If we need a specific destruction it should only happen if they are unactive,
     * ans removed from the NetowrkNode list too **/
    @Override public void wasRemoved () {
        super.wasRemoved();
        //log().info(this + ".wasRemoved()");
       // layer.destroy();
       // iface.destroyRoot(_root);
    }

    @Override public void showTransitionCompleted () {
        super.showTransitionCompleted();
        log().info(this + ".showTransitionCompleted()");
    }

    @Override public void hideTransitionStarted () {
        super.hideTransitionStarted();
        log().info(this + ".hideTransitionStarted()");
    }

    /** Returns the stylesheet to use for this screen. */
    protected Stylesheet stylesheet () {
        return SimpleStyles.newSheet();
    }

    /** Creates the layout for the interface root. The default is a vertical axis layout. */
    protected Layout createLayout () {
        return AxisLayout.vertical().offStretch();
    }

    /** Override this method and create your UI in it. Add elements to {@link #_root}. */
    protected abstract void createIface ();

   // protected final int _depth;
    protected Root _root;
}