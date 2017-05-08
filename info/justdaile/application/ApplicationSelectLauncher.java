package info.justdaile.application;

import java.util.HashMap;

import info.justdaile.application.components.popup.PopupDialogWindow;
import info.justdaile.application.strings.Buttons;
import info.justdaile.application.strings.Descriptions;
import info.justdaile.application.strings.Labels;
import info.justdaile.application.strings.Titles;
import info.justdaile.application.strings.WindowText;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ApplicationSelectLauncher extends Application {

	private static final HashMap<String, Object[]> APPS = new HashMap<String, Object[]>();
	
	public ApplicationSelectLauncher() {
		final Object[] tester = {new ModuleTests(), Descriptions.TEST_APP_DESCRIPTION};
		final Object[] marker = {new ModuleMarker(), Descriptions.MARKER_APP_DESCRIPTION};
		final Object[] creator = {new ModuleCreator(), Descriptions.CREATOR_APP_DESCRIPTION};
		APPS.put(Titles.MODULE_TESTS_APP_TITLE, tester);
		APPS.put(Titles.MODULE_MARKER_APP_TITLE, marker);
		APPS.put(Titles.MODULE_CREATOR_APP_TITLE, creator);
	}
	
	@Override
	public void start(Stage primaryStage) {
		new PopupDialogWindow(WindowText.LOAD_APP_WINDOW_TITLE, WindowText.LOAD_APP_WINDOW) {
			@Override
			public Node initButtons() {
				VBox box = new VBox(15);
				Label comboLabel = new Label(Labels.APPLICATION_LIST_LABEL);
				Text description = new Text();
				description.setFill(new Color(0.9d, 0.2d, 0.8d, 1d));
				description.setFont(new Font(12));
				ComboBox<String> application = new ComboBox<String>();
				SingleSelectionModel<String> selection = application.getSelectionModel();
				for(int i = 0; i < ApplicationSelectLauncher.APPS.size(); i++){
					application.getItems().add(ApplicationSelectLauncher.APPS.keySet().toArray()[i].toString());
				}
				application.setOnAction(a -> {
					if(!selection.isEmpty()){
						updateDescription(description, selection);
					}
				});
				selection.selectFirst();
				updateDescription(description, selection);
				Button confirm = new Button(Buttons.LOAD_SELECTED_APPLICATION_BUTTON);
				confirm.minWidth(100);
				confirm.setOnAction(e -> {
					if(!selection.isEmpty()){
						try {
							box.setDisable(true);
							Platform.runLater(() -> {
								try {
									((Application) ApplicationSelectLauncher.APPS.get(selection.getSelectedItem())[0]).start(new Stage());
								} catch (Exception e1) {
									e1.printStackTrace();
								}
							});
							close();
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
				HBox selectorPanel = new HBox(5);
				selectorPanel.setAlignment(Pos.CENTER);
				selectorPanel.getChildren().addAll(comboLabel, application, confirm);
				box.setAlignment(Pos.TOP_CENTER);
				box.getChildren().addAll(selectorPanel, description);
				return box;
			}
			private void updateDescription(Text desc, SingleSelectionModel<String> selection) {
				desc.setText(ApplicationSelectLauncher.APPS.get(selection.getSelectedItem())[1].toString());
			}
			
		}.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
