package info.justdaile.application.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import info.justdaile.application.options.Evaluatable;
import info.justdaile.application.options.IgnoredSubmitOption;
import info.justdaile.application.options.EvaluatableOption;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public abstract class OptionMenu extends VBox implements Evaluatable{
	
	private ArrayList<EvaluatableOption> optionButtons = new ArrayList<EvaluatableOption>();
	private boolean fixed;
	
	public OptionMenu(){
		this.setAlignment(Pos.TOP_CENTER);
		this.setFillWidth(true);
		this.fixed = false;
		this.init();
	}
	
	public void clearOptions(){
		this.getChildren().clear();
	}
	
	public void disableOptions(){
		this.getChildren().stream().forEach(e -> e.setDisable(true));
	}
	
	public void setFixed(boolean fixed){
		this.fixed = fixed;
	}
	
	public void addOption(EvaluatableOption button){
		this.optionButtons.add(button);
	}
	
	public void removeOption(EvaluatableOption button){
		this.optionButtons.remove(button);
	}
	
	public int options() {
		return this.getChildren().size();
	}
	
	public void show(){
		Random rand = new Random();
		EvaluatableOption append = null;
		
		while(optionButtons.size() > 0){
			int r = 0;
			if(!this.fixed){
				r = rand.nextInt(optionButtons.size());
			}
			EvaluatableOption add = optionButtons.remove(r);
			if(!(add instanceof IgnoredSubmitOption)){
				VBox.setMargin(add, new Insets(10f, 10f, 10f, 10f));
				this.getChildren().add(add);
				this.setAlignment(add.getAlignment()); 
			}else{
				append = add;
			}
		}
		if(append != null){
			VBox.setMargin(append, new Insets(10f, 10f, 10f, 10f));
			this.getChildren().add(append);
			this.setAlignment(Pos.CENTER_RIGHT);
		}
	}
	
	public EvaluatableOption[] getOptions(Predicate<EvaluatableOption> filter){
		List<EvaluatableOption> list = new ArrayList<EvaluatableOption>();
		this.getSelectableChildren().stream().filter(filter).forEach(list::add);
		EvaluatableOption[] arr = new EvaluatableOption[list.size()];
		return list.toArray(arr);
	}
	
	public List<EvaluatableOption> getSelectableChildren(){
		List<EvaluatableOption> list = new ArrayList<EvaluatableOption>();
		this.getChildrenUnmodifiable().forEach(e -> {
			if(e instanceof EvaluatableOption){
				list.add((EvaluatableOption) e);
			}
		});
		EvaluatableOption[] arr = new EvaluatableOption[list.size()];
		list.toArray(arr);
		return Arrays.asList(arr);
	}
	
	public void revealOptions(){
		this.getChildren().stream().forEach(e -> ((EvaluatableOption) e).reveal());
	}
	
	@Override
	public boolean eval(){
		boolean eval = true;
		for(EvaluatableOption o : this.getSelectableChildren()){
			if(eval){
				eval = o.eval();
			}
		}
		return eval;
	}
	
	public abstract void init();
	public abstract void onSubmitted(EvaluatableOption...selectedOptions);

}
