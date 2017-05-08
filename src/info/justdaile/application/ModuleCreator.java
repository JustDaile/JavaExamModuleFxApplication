package info.justdaile.application;

import java.io.File;
import java.io.FileWriter;

import info.justdaile.application.components.ApplicationMenuBar;
import info.justdaile.application.components.ConfirmWindow;
import info.justdaile.application.components.ModuleEditor;
import info.justdaile.application.components.popup.ModuleSelectorWindow;
import info.justdaile.application.components.popup.PopupOptionWindow;
import info.justdaile.application.components.popup.PopupWindow;
import info.justdaile.application.components.popup.PopupYesNoOptionWindow;
import info.justdaile.application.json.Module;
import info.justdaile.application.strings.Buttons;
import info.justdaile.application.strings.Labels;
import info.justdaile.application.strings.Titles;
import info.justdaile.application.strings.Descriptions;
import info.justdaile.application.strings.WindowText;
import info.justdaile.io.ModuleLoader;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventType;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ModuleCreator extends Application {

	private Module module;
	private File saveFile;
	private Insets padding = new Insets(15, 15, 15, 15);
	private boolean cancelSave;
	private boolean shutdown;
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle(Titles.MODULE_CREATOR_APP_TITLE);
		ApplicationMenuBar menubar = new ApplicationMenuBar(primaryStage);
		this.shutdown = false;
		while(!this.shutdown && this.module == null){
			this.initModuleLoad().showAndWait();
			if(this.shutdown){
				primaryStage.close();
			}
		}
		if(this.shutdown) return;
		Label moduleLabel = new Label(Labels.MODULE_NAME_LABEL);
		TextField title = new TextField(this.module.getTitle());
		title.addEventHandler(EventType.ROOT, e -> {
			if(e.getEventType() == KeyEvent.KEY_RELEASED){
				this.module.setTitle(title.getText());
			}
		});
		Button save = new Button(Buttons.SAVE_MODULE_BUTTON);
		HBox centerTop = new HBox(10, moduleLabel, title, save);
		centerTop.setAlignment(Pos.TOP_CENTER);
		centerTop.setPadding(this.padding);
		HBox.setHgrow(title, Priority.ALWAYS);
		ModuleEditor moduleEdit = new ModuleEditor(module);	
		ScrollPane scroller = new ScrollPane(moduleEdit);
		scroller.setFitToWidth(true);
		VBox centerBottom = new VBox(10, scroller);
		centerBottom.setAlignment(Pos.TOP_CENTER);
		centerBottom.setPadding(this.padding);
		VBox.setVgrow(scroller, Priority.ALWAYS);
		VBox centerPane = new VBox(centerTop, centerBottom);
		save.setOnAction(e -> {
			Platform.runLater(() -> {
				try{
					cancelSave = false;
					while(!cancelSave && this.saveFile == null){
						this.saveFile = selectSaveFile();
						if(this.saveFile == null){
							new PopupYesNoOptionWindow(WindowText.ERROR_TITLE, WindowText.ERROR_NO_FILE_SELECTED, Buttons.RETRY, Buttons.CANCEL) {
								@Override
								public void onOptionPressed(int actionType) {
									switch(actionType){
									case PopupYesNoOptionWindow.POSITIVE_ACTION:
										close();
										break;
									case PopupYesNoOptionWindow.NEGATIVE_ACTION:
										cancelSave = true;
									case PopupWindow.CLOSE_WINDOW_ACTION:
										close();
										break;
									}
								}
							}.showAndWait();
						}
					}
					if(this.saveFile != null){
						if(!this.saveFile.exists()){
							this.saveFile.createNewFile();
						}
						FileWriter w = new FileWriter(this.saveFile);
						w.write(moduleEdit.getModule().toJSON().toString(2));
						w.close();
						new ConfirmWindow(WindowText.SAVED_TITLE, WindowText.SAVED_MODULE).show();
					}else{
						new ConfirmWindow(WindowText.NOT_SAVED_TITLE, WindowText.NOT_SAVED_MODULE).show();
					}
				}catch(Exception err){
					err.printStackTrace();
				}
			});
		});
		save.setTooltip(new Tooltip(Descriptions.SAVE_BUTTON));
		BorderPane pane = new BorderPane(centerPane, menubar, null, null, null);
		Scene scene = new Scene(pane, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.setResizable(true);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}
	
	private File selectSaveFile() {
		FileChooser fc = new FileChooser();
		fc.setInitialDirectory(new File(ModuleLoader.MODULE_ROOT));
		return fc.showSaveDialog(new Stage());
	}

	private PopupYesNoOptionWindow initModuleLoad() {
		return new PopupYesNoOptionWindow(WindowText.OPTION_TITLE, WindowText.OPTION_CREATE_MODULE, Buttons.CREATE_NEW_MODULE_BUTTON, Buttons.LOAD_NEW_MODULE_BUTTON) {
			@Override
			public void onOptionPressed(int actionType) {
				switch(actionType){
				case PopupYesNoOptionWindow.POSITIVE_ACTION:
					FileChooser fc = new FileChooser();
					fc.setInitialDirectory(new File(ModuleLoader.MODULE_ROOT));
					File file = fc.showSaveDialog(new Stage());
					if(file != null){
						module = new Module(Descriptions.DEFAULT_MODULE_TEXT);
						saveFile = file;
					}
					break;
				case PopupYesNoOptionWindow.NEGATIVE_ACTION:
					try{
						module = new ModuleSelectorWindow(WindowText.SELECTION_TITLE, WindowText.SELECT_MODULE_SELECT).ShowSelectorWindow();
					}catch(Exception e){
						e.printStackTrace();
					}
					break;
				case PopupOptionWindow.CLOSE_WINDOW_ACTION:
					shutdown = true;
					return;
				}	
				close();
			}
		};
	}

}
