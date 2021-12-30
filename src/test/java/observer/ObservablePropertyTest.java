package observer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.sun.javafx.scene.paint.GradientUtils.Point;

import observer.Observable;
import observer.ObservableProperty;
import observer.SampleSpectator;

class ObservablePropertyTest {
	
	@Test
	public void devrait_etre_observable() {
		Observable<Integer> observable = new ObservableProperty<Integer>();
	}
	
	@Test
	public void on_change_les_proporiete_observable_sans_observeur() {
		ObservableProperty<Integer> property = new ObservableProperty<>();
		property.setValue(5);
		assertEquals(5, property.getValue());
	}
	
	@Test
	public void on_change_ObservableProperty_qui_notifies_les_observers() {
		ObservableProperty<Integer> property = new ObservableProperty<>();
		SampleSpectator observer = new SampleSpectator();
		property.attach(observer);
		property.setValue(5);
		assertTrue(observer.wasNotified());
	}
	
	@Test
	public void on_change_ObservableProperty_qui_notifies_pas_les_observers_detache() {
		ObservableProperty<Integer> property = new ObservableProperty<>();
		SampleSpectator observer = new SampleSpectator();
		
		property.attach(observer);
		property.detach(observer);
		property.setValue(5);
		assertFalse(observer.wasNotified());
	}
}
