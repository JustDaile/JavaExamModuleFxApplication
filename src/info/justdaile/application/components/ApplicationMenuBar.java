package info.justdaile.application.components;

import info.justdaile.application.ApplicationSelectLauncher;
import info.justdaile.application.components.popup.PopupWindow;
import info.justdaile.application.events.PopupOptionEvent;
import info.justdaile.application.strings.Titles;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ApplicationMenuBar extends MenuBar{
	
	private static final String TEXT = "Module Tests, Module Creator & Module Marker"
			+ " are all applications wrote using JavaFX."
			+ " This project is not opensource and all code is property of the author."
			+ " Any of the work included in the project is not to be redistrubed without"
			+ " the permission of the author.";
		
	public ApplicationMenuBar(Stage stage){
		Menu switchMenu = new Menu(Titles.FILE_MENU);
		MenuItem launcherAppItem = new MenuItem(Titles.MODULE_APP_LAUNCHER_TITLE);
		MenuItem aboutAppItem = new MenuItem(Titles.ABOUT_LAUNCHER_TITLE);
		switchMenu.getItems().addAll(launcherAppItem, aboutAppItem);
		launcherAppItem.setOnAction(e -> {
			stage.close();
			new ApplicationSelectLauncher().start(new Stage());
		});
		aboutAppItem.setOnAction(e -> {
			new PopupWindow(Titles.ABOUT_LAUNCHER_TITLE) {
				@Override
				public void onAction(PopupOptionEvent event) {
					// ignore
				}
				@Override
				public Node init() {
					this.setResizable(false);
					this.setAlwaysOnTop(true);
					VBox box = new VBox(15);
					Text title = new Text(Titles.MAIN_TITLE);
					Text author = new Text(Titles.AUTHOR);
					Text text = new Text(ApplicationMenuBar.TEXT);
					title.setTextAlignment(TextAlignment.CENTER);
					title.setFont(new Font(18));
					author.setTextAlignment(TextAlignment.CENTER);
					text.setTextAlignment(TextAlignment.CENTER);
					text.setWrappingWidth(500);
					text.setStyle("-fx-base: #000;");
					box.setAlignment(Pos.TOP_CENTER);
					box.setPadding(new Insets(10, 10, 10, 10));
					box.getChildren().addAll(title, author, text);
					return box;
				}
			}.showAndWait();
		});
		this.getMenus().add(switchMenu);
	}

}
