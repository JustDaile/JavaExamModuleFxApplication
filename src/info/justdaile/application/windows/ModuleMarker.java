package info.justdaile.application.windows;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import info.justdaile.application.components.AnswerView;
import info.justdaile.application.components.ApplicationMenuBar;
import info.justdaile.application.strings.Buttons;
import info.justdaile.application.strings.Descriptions;
import info.justdaile.application.strings.JSONKeys;
import info.justdaile.application.strings.Labels;
import info.justdaile.application.strings.Messages;
import info.justdaile.application.strings.Titles;
import info.justdaile.io.UserFileManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class ModuleMarker extends Application {

	private UserFileManager userFile = new UserFileManager();
	private ArrayList<String[]> results;
	private String selectedModule;
	private int totalQuestions = 0;
	private int score = 0;
	private Insets padding = new Insets(15, 15, 15, 15);
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle(Titles.MODULE_MARKER_APP_TITLE);
		ApplicationMenuBar menubar = new ApplicationMenuBar(primaryStage);
		Label modelComboLabel = new Label(Labels.MODULE_NAME_LABEL);
		modelComboLabel.setTextAlignment(TextAlignment.CENTER);
		ComboBox<String> modules = new ComboBox<String>();
		for(int i = 0; i < userFile.getModules().length(); i++){
			modules.getItems().add(userFile.getModules().getJSONObject(i).getString("Title"));
		}
		Button load = new Button(Buttons.LOAD_SELECTED_MODULE_BUTTON);
		HBox top = new HBox(10, modelComboLabel, modules, load);
		top.setAlignment(Pos.CENTER);
		top.setPadding(padding);
		ListView<AnswerView> answers = new ListView<AnswerView>(); 
		answers.setPrefWidth(500);
		Text moduleNameText = new Text(Descriptions.NO_INPUT_TEXT);
		VBox center = new VBox(moduleNameText, answers);
		Text moduleStatsText = new Text();
		VBox bottom = new VBox(moduleStatsText);
		bottom.setPadding(new Insets(15, 15, 15, 15));
		bottom.setAlignment(Pos.CENTER);
		GridPane pane = new GridPane();
		pane.add(menubar, 0, 0);
		pane.add(top, 0, 1);
		pane.add(center, 0, 2);
		pane.add(bottom, 0, 3);
		pane.setAlignment(Pos.TOP_CENTER);
		Scene scene = new Scene(pane);		
		load.setOnAction(e -> {
			SingleSelectionModel<String> selectionModel = modules.getSelectionModel();
			if(!selectionModel.isEmpty()){
				int index = selectionModel.getSelectedIndex();
				JSONObject object = userFile.getModules().getJSONObject(index);
				this.loadStats(object);
				selectedModule = object.getString("Title");
				moduleNameText.setText("Selected : " + selectedModule + " : " + index);
				selectionModel.clearSelection();
				answers.getItems().clear();
				for(int i = 0; i < this.results.size(); i++){
					String[] results = this.results.get(i);
					answers.getItems().add(new AnswerView(results[0], results[1], Boolean.valueOf(results[2])));
				}
				moduleStatsText.setText(Messages.compileMessage(Messages.TEST_RESULT_MESSAGE, this.score, this.totalQuestions));
				primaryStage.sizeToScene();
			}
		});
		primaryStage.setScene(scene);
		primaryStage.sizeToScene();
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
		primaryStage.setAlwaysOnTop(true);
		primaryStage.show();
	}

	private void loadStats(JSONObject jsonObject) {
		JSONArray answers = jsonObject.getJSONArray(JSONKeys.USER_ANSWERS_KEY);
		this.totalQuestions = this.score = 0;
		this.results = new ArrayList<String[]>();
		String[] str;
		for(int i = 0; i < answers.length(); i++){
			JSONObject answer = answers.getJSONObject(i);
			str = new String[3];
			str[0] = answer.getString(JSONKeys.MODULE_QUESTION_KEY);
			str[1] = answer.getString(JSONKeys.USER_ANSWER_KEY);
			str[2] = String.valueOf(answer.getBoolean(JSONKeys.USER_QUESTION_CORRECT_KEY));
			this.results.add(str);
			this.totalQuestions++;
			if(str[2].contentEquals("true")){
				this.score++;
			}
		}
	}

}
