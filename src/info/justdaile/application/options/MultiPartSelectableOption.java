package info.justdaile.application.options;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;

public abstract class MultiPartSelectableOption extends EvaluatableOption {

	private List<EvaluatableOption> subOptions;
	private Insets padding = new Insets(15, 0, 0, 10);
	private TextFlow flow;
	
	public MultiPartSelectableOption() {
		super(true);		
	}
	
	public void init(){
		this.subOptions = initOptions();
		flow = new TextFlow();
		flow.setTextAlignment(TextAlignment.LEFT);
		this.subOptions.stream()
        .forEach(e -> {
        	if(((SelectableTextOption)e).toString().contentEquals("\n")){
        		flow.getChildren().add(new Text(System.lineSeparator()));
        	}else{
        		flow.getChildren().add(e);
        	}
        });
		flow.setPadding(padding);
		flow.setLineSpacing(0);
		this.getChildren().add(flow);
	}

	public float getTotalCorrectSubOptions(){
		return subOptions.stream()
				        .filter(EvaluatableOption::isValid)
				        .count();
	}
	
	public abstract ArrayList<EvaluatableOption> initOptions();

	@Override
	public void selectionChanged() {
		super.toggle();
	}
	
	@Override
	public String toString(){
		StringBuffer s = new StringBuffer();
		subOptions.stream()
                  .filter(EvaluatableOption::isValid)
                  .forEach(r -> s.append(r + " "));
		return s.toString();
	}
	
	@Override
	public void reveal() {
		this.getSelectableChildren(flow).forEach(EvaluatableOption::reveal);
	}
	
	@Override
	public boolean eval(){
		boolean eval = true;
		List<EvaluatableOption> selected = this.getSelectableChildren(flow);
		for(int i = 0; i < selected.size(); i++){
			EvaluatableOption op = selected.get(i);
			if(eval){
				eval = op.eval();
			}
		}
		return eval;
	}
	
}
