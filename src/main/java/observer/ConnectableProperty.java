package observer;

public class ConnectableProperty<T> extends ObservableProperty<T> implements Observer<T> {

	private T value;

	public ConnectableProperty(){}
	
	public ConnectableProperty(T value) {
		setValue( value);
	}
	
	
	@Override
	public void update(T value) {
		setValue(value);
	}

	public void bind(ConnectableProperty<T> property) {
		this.setValue(property.getValue());
		property.attach(this);
	}

	public void bindBidrectional(ConnectableProperty<T> property) {
		property.setValue(this.getValue());
		property.attach(this);
		this.attach(property);
	}

	public void unbind(ConnectableProperty<T> property) {
		property.detach(this);
		
	}

	public void unbindBidrectional(ConnectableProperty<T> property) {
		property.detach(this);
		this.detach(property);
	}

}
