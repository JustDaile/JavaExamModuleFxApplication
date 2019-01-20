package info.justdaile.application.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

import info.justdaile.application.options.Evaluatable;
import info.justdaile.application.options.IgnoredSubmitOption;
import info.justdaile.application.options.EvaluableOption;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public abstract class OptionMenu extends VBox implements Evaluatable{
	
	private ArrayList<EvaluableOption> optionButtons = new ArrayList<EvaluableOption>();
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
	
	public void addOption(EvaluableOption button){
		this.optionButtons.add(button);
	}
	
	public void removeOption(EvaluableOption button){
		this.optionButtons.remove(button);
	}
	
	public int options() {
		return this.getChildren().size();
	}
	
	public void show(){
		Random rand = new Random();
		EvaluableOption append = null;
		
		while(optionButtons.size() > 0){
			int r = 0;
			if(!this.fixed){
				r = rand.nextInt(optionButtons.size());
			}
			EvaluableOption add = optionButtons.remove(r);
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
	
	public EvaluableOption[] getOptions(Predicate<EvaluableOption> filter){
		List<EvaluableOption> list = new ArrayList<EvaluableOption>();
		this.getSelectableChildren().stream().filter(filter).forEach(list::add);
		EvaluableOption[] arr = new EvaluableOption[list.size()];
		return list.toArray(arr);
	}
	
	public List<EvaluableOption> getSelectableChildren(){
		List<EvaluableOption> list = new ArrayList<EvaluableOption>();
		this.getChildrenUnmodifiable().forEach(e -> {
			if(e instanceof EvaluableOption){
				list.add((EvaluableOption) e);
			}
		});
		EvaluableOption[] arr = new EvaluableOption[list.size()];
		list.toArray(arr);
		return Arrays.asList(arr);
	}
	
	public void revealOptions(){
		this.getChildren().stream().forEach(e -> ((EvaluableOption) e).reveal());
	}
	
	@Override
	public boolean eval(){
		boolean eval = true;
		for(EvaluableOption o : this.getSelectableChildren()){
			if(eval){
				eval = o.eval();
			}
		}
		return eval;
	}
	
	public abstract void init();
	public abstract void onSubmitted(EvaluableOption...selectedOptions);

}
