package info.justdaile.application.json;

@FunctionalInterface
public interface JSONConvertable<E> {
	
	public E toJSON();

}
