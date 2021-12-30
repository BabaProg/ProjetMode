package observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Observable<T> {

	private List<Observer<T>> observers = new ArrayList<>();

	public void attach(Observer<T> observer) {
		observers.add(observer);
	}

	public void detach(Observer<T> observer) {
		observers.remove(observer);

	}

	public void notifyObservers(T value) {
		for (Observer<T> observer : observers) {
			observer.update((T) value);
		}
	}

}
