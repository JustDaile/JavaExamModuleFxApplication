package info.justdaile.application.components.popup;

import info.justdaile.application.events.PopupOptionEvent;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class PopupWindow extends Stage{
	
	public static final int CLOSE_WINDOW_ACTION = -1;
	
	public PopupWindow(String title){
		this.setTitle(title);
		this.setOnShowing(e -> this.initOnShow());
	}

	public final void initOnShow(){
		Scene scene = new Scene((Parent) this.init());
		this.setScene(scene);
		this.sizeToScene();
		this.setOnCloseRequest(e -> this.onAction(new PopupOptionEvent(e, PopupWindow.CLOSE_WINDOW_ACTION)));
	}
	
	public abstract Node init();
	public abstract void onAction(PopupOptionEvent event);


}
