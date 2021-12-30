package observer;

public class SampleSpectator implements Observer<Integer> {

	private Integer value = 0;
	private boolean notified = false;
	
	@Override
	public void update(Integer value) {
		this.value = value;
		notified = true;
	}

	public boolean wasNotified() {
		return this.notified;
	}

	public Integer getValue() {
		return value;
	}

}
