package observer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import observer.Observable;
import observer.Observer;
import observer.SampleSpectator;
import observer.SampleSubject;

class ObserverTest {

	@Test
	public void sujet_sense_etre_observable() {
		Observable<Integer> subject = new SampleSubject();
	}
	
	@Test
	public void spectator_doit_etre_observe() {
		Observer<Integer> observer = new SampleSpectator();
		observer.update(5);
	}
	
	@Test
	public void spectateur_doit_etre_notifie_par_un_appel_update() {
		SampleSpectator observer = new SampleSpectator();
		observer.update(5);
		assertTrue(observer.wasNotified());
	}
	
	@Test
	public void spectateur_ne_doit_pas_etre_notifie_sans_appel_update() {
		SampleSpectator observeur = new SampleSpectator();
		assertFalse(observeur.wasNotified());
	}
	
	@Test
	public void oberserver_devrait_etre_attache_et_detache() {
		Observable<Integer> observable = new SampleSubject();
		Observer<Integer> observer = new SampleSpectator();
		observable.attach(observer);
		observable.detach(observer);		
	}
	
	@Test
	public void on_ne_devrait_pas_notifier_les_observer_detache() {
		Observable<Integer> subject = new SampleSubject();
		subject.notifyObservers(5);
		assertFalse(new SampleSpectator().wasNotified());
	}
	
	@Test
	public void on_devrait_notifies_les_observers_attache() {
		Observable<Integer> subject = new SampleSubject();
		SampleSpectator spectator = new SampleSpectator();
		subject.attach(spectator);
		subject.notifyObservers(7);
		assertTrue(spectator.wasNotified());
	}
	
	@Test
	public void on_devrait_notifier_tout_les_observers_attache() {
		Observable<Integer> subject = new SampleSubject();
		SampleSpectator spectator1 = new SampleSpectator();
		SampleSpectator spectator2 = new SampleSpectator();
		SampleSpectator spectator3 = new SampleSpectator();
		
		subject.attach(spectator1);
		subject.attach(spectator2);
		subject.attach(spectator3);
		subject.notifyObservers(5);
		
		assertTrue(spectator1.wasNotified());
		assertTrue(spectator2.wasNotified());
		assertTrue(spectator3.wasNotified());
	}
	
	@Test
	public void ne_pas_notife_un_observer_attache_puis_detache() {
		Observable<Integer> subject = new SampleSubject();
		SampleSpectator spectator = new SampleSpectator();
		
		subject.attach(spectator);
		subject.detach(spectator);
		subject.notifyObservers(5);
		
		assertFalse(spectator.wasNotified());
	}
	
	@Test
	public void on_ne_doit_pas_notifier_un_spectateur_detache() {
		Observable<Integer> subject = new SampleSubject();
		SampleSpectator spectator = new SampleSpectator();
		
		subject.detach(spectator);
		subject.notifyObservers(5);
		
		assertFalse(spectator.wasNotified());
	}
	
	@Test
	public void transmettre_des_valeurs_aux_notifie() {
		Observable<Integer> subject = new SampleSubject();
		SampleSpectator spectator = new SampleSpectator();
		
		subject.attach(spectator);
		assertEquals(0, spectator.getValue());
		
		subject.notifyObservers(5);
		assertTrue(spectator.wasNotified());
		assertEquals(5, spectator.getValue());
	}

}

