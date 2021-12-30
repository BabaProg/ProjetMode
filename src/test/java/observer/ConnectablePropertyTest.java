package observer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import observer.ConnectableProperty;
import observer.ObservableProperty;
import observer.Observer;

class ConnectablePropertyTest {

	 @Test
	 public void devrait_etre_observe() {
		 ObservableProperty<String> observable = new ConnectableProperty<>();
	 }
	 
	 @Test
	 public void devrait_etre_un_observer() {
		 Observer<String> observer = new ConnectableProperty<>();
	}
	 
	 @Test
	 public void on_devrait_initialiser_la_valeur_dans_le_constructeur() {
		 ConnectableProperty<Integer> p1 = new ConnectableProperty<>(42);
		 assertEquals(42, p1.getValue());
	 }
	 
	 @Test
	 public void devrait_changer_la_valeur_des_observeur_connecte() {
		 ConnectableProperty<Integer> p1 = new ConnectableProperty<Integer>(42);
		 ConnectableProperty<Integer> p2 = new ConnectableProperty<Integer>();
		 p2.bind(p1);
		 assertEquals(42, p2.getValue());
	 }
	 
	 @Test
	 public void on_devrait_transmettre_la_valeur_aux_observer_connectes() {
		 ConnectableProperty<Integer> p1 = new ConnectableProperty<Integer>();
		 ConnectableProperty<Integer> p2 = new ConnectableProperty<Integer>();
		 
		 p1.bind(p2);
		 p2.setValue(5);
		 
		 assertEquals(5, p1.getValue());
		 assertEquals(5, p2.getValue());
	 }
	 
	 @Test
	 public void connect_ne_transmet_pas_la_valeur_dun_observer_connecte() {
		 ConnectableProperty<Integer> p1 = new ConnectableProperty<Integer>();
		 ConnectableProperty<Integer> p2 = new ConnectableProperty<Integer>(42);
		 
		 p1.bind(p2);
		 assertEquals(42, p2.getValue());
		 
		 p1.setValue(5);
		 assertEquals(42, p2.getValue());
		 
	 }
	 
	 @Test
	 public void bindDirectionnel_init_value_depuis_le_premier() {
		 ConnectableProperty<Integer> p1 = new ConnectableProperty<Integer>(42);
		 ConnectableProperty<Integer> p2 = new ConnectableProperty<Integer>(14);
		 
		 p1.bindBidrectional(p2);
		 
		 assertEquals(42, p1.getValue());
		 assertEquals(42, p2.getValue());
	 }
	 
	 @Test
	 public void bindDirectionnel_transmet_la_valeur_dans_les_2_cas() {
		 ConnectableProperty<Integer> p1 = new ConnectableProperty<Integer>();
		 ConnectableProperty<Integer> p2 = new ConnectableProperty<Integer>();
		 
		 p1.bindBidrectional(p2);
		 p1.setValue(42);
		 
		 assertEquals(42, p1.getValue());
		 assertEquals(42, p2.getValue());
		 
		 p2.setValue(5);
		 assertEquals(5, p1.getValue());
		 assertEquals(5, p2.getValue());
		 
		 p1.setValue(12);
		 assertEquals(12, p1.getValue());
		 assertEquals(12, p2.getValue());
		 
	 }
	 
	 @Test
	 public void circulaire_connect_mais_sans_boucle() {
		 ConnectableProperty<Integer> p1 = new ConnectableProperty<Integer>();
		 ConnectableProperty<Integer> p2 = new ConnectableProperty<Integer>();
		 ConnectableProperty<Integer> p3 = new ConnectableProperty<Integer>();
		 
		 p1.bindBidrectional(p2);
		 p2.bindBidrectional(p3);
		 p3.bindBidrectional(p1);
		 
		 
		 p2.setValue(5);
		 
		 assertEquals(5, p1.getValue());
		 assertEquals(5, p2.getValue());
		 assertEquals(5, p3.getValue());
		 
		 p1.setValue(42);
		 
		 assertEquals(42, p1.getValue());
		 assertEquals(42, p2.getValue());
		 assertEquals(42, p3.getValue());
		 
		 p3.setValue(17);
		 
		 assertEquals(17, p1.getValue());
		 assertEquals(17, p2.getValue());
		 assertEquals(17, p3.getValue());
	 }
	 
	 @Test
	 public void deconnecter_des_property_ne_transmet_pas_les_valeurs() {
		 ConnectableProperty<Integer> p1 = new ConnectableProperty<Integer>();
		 ConnectableProperty<Integer> p2 = new ConnectableProperty<Integer>(5);
		 
		 p1.bind(p2);
		 assertEquals(5, p1.getValue());
		 
		 p1.unbind(p2);
		 
		 p2.setValue(42);
		 assertEquals(5, p1.getValue());
	 }
	 
	 @Test
	 public void deconnecter_property_ne_transmet_pas_les_valeurs() {
		 ConnectableProperty<Integer> p1 = new ConnectableProperty<Integer>(5);
		 ConnectableProperty<Integer> p2 = new ConnectableProperty<Integer>();
		 
		 p1.bindBidrectional(p2);
		 
		 p1.unbindBidrectional(p2);
		 
		 p2.setValue(42);
		 assertEquals(5, p1.getValue());
		 assertEquals(42, p2.getValue());
		 
		 p1.setValue(7);
		 assertEquals(7, p1.getValue());
		 assertEquals(42, p2.getValue());
	 }

}
