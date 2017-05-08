package info.justdaile.application.components.popup;

import java.util.ArrayList;
import java.util.List;

import info.justdaile.application.json.Module;
import info.justdaile.application.strings.Buttons;
import info.justdaile.io.ModuleLoader;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.layout.VBox;

public class ModuleSelectorWindow extends PopupDialogWindow{

	private List<Module> modules;
	private int selected;
	
	public ModuleSelectorWindow(String title, String message) {
		super(title, message);
		this.modules = new ArrayList<Module>();
		this.selected = -1;
	}

	public Module ShowSelectorWindow() {
		super.showAndWait();
		if(selected > -1){
			return modules.get(selected);
		}
		return null;
	}

	@Override
	public Node initButtons() {
		final VBox box = new VBox();
		ComboBox<String> list = new ComboBox<String>();
		Button confirm = new Button(Buttons.LOAD_SELECTED_MODULE_BUTTON);
		ModuleLoader.handle((l) -> {
			l.getModules().stream()
			              .forEach(modules::add);
		});
		modules.stream()
		       .map(m -> m.getTitle())
		       .forEach(list.getItems()::add);
		confirm.setOnAction(e -> {
			SingleSelectionModel<String> selected = list.getSelectionModel();
			if(!selected.isEmpty()){
				this.selected = selected.getSelectedIndex();
				close();
			}
		});	
		box.getChildren().add(list);
		box.getChildren().add(confirm);
		box.setAlignment(Pos.CENTER);
		box.setSpacing(10);
		return box;
	}
	
}
