package observer;

public class ObservableProperty<T> extends Observable<T> {
	
	private T value;
	
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		if(shouldUpdate(value)) {
			this.value = value;
			notifyObservers(value);
		}
	}
	
	private boolean shouldUpdate(T value) {
		if(this.value == null && value == null) return false;
		if(this.value == null) return true;
		return !this.value.equals(value);
	}

}
