package info.justdaile.application.options;

public interface Selectable {

	void select();
	void deselect();
	void toggle();
	boolean isSelected();
	void selectionChanged();
	
}
