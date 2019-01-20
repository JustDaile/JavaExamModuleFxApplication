package info.justdaile.application.options;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.scene.Parent;
import javafx.scene.layout.HBox;

public abstract class EvaluableOption extends HBox implements Evaluatable, Selectable{
	
	private boolean valid;
	private boolean selected;
	
	public EvaluableOption(boolean valid){
		this.valid = valid;
		this.selected = false;
	}
	
	public boolean isValid(){
		return this.valid;
	}
	
	public void reveal() {

	}
	
	public List<EvaluableOption> getSelectableChildren(Parent parent){
		List<EvaluableOption> list = new ArrayList<EvaluableOption>();
		parent.getChildrenUnmodifiable().forEach(e -> {
			if(e instanceof EvaluableOption){
				list.add((EvaluableOption) e);
			}
		});
		EvaluableOption[] arr = new EvaluableOption[list.size()];
		list.toArray(arr);
		return Arrays.asList(arr);
	}

	@Override
	public void select(){
		this.selected = true;
	}
	
	@Override
	public void deselect(){
		this.selected = false;
	}

	@Override
	public void toggle() {
		if(this.isSelected()){
			this.deselect();
			return;
		}
		this.select();
	}
	@Override
	public boolean isSelected() {
		return this.selected;
	}

	@Override
	public boolean eval(){
		return (this.isSelected() && this.isValid() || !this.isSelected() && !this.isValid());
	}
	
}
