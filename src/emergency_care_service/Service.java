package emergency_care_service;

import java.util.ArrayList;
import java.util.Collections;

public class Service {

	private ArrayList<Integer> states = new ArrayList<Integer>(14);
	private ArrayList<Integer> timers = new ArrayList<Integer>(11);
	/*
	 * states:					timers:
	 * 0 new					0 request_med
	 * 1 check_in				1 request_room
	 * 2 p0						2 fill_out
	 * 3 paper_complete			3 proceed
	 * 4 paperwork_processed	4 enter
	 * 5 examining_room			5 treat
	 * 6 complete				6 check_out
	 * 7 case_closed			7 give_med
	 * 8 nurse					8 take_med
	 * 9 med					9 give_room
	 * 10 room					10 take_room
	 * 11 waiting
	 * 12 extern_med
	 * 13 extern_room
	 */
	
	public Service() {
		Collections.fill(this.states, 0);
		Collections.fill(this.timers, 0);
		this.states.set(0, 1);
		this.states.set(8, 2);
		this.states.set(9, 2);
		this.states.set(11, 10);
		this.states.set(12, 5);
		this.states.set(13, 7);
	}
	
	private void reset(int timerIndex) {
		this.timers.set(timerIndex, 0);
	}
	
	private void minus(int stateIndex) {
		this.states.set(stateIndex, this.states.get(stateIndex) - 1);
	}
	
	private void plus(int stateIndex) {
		this.states.set(stateIndex, this.states.get(stateIndex) + 1);
	}
	
	private void increase(int timerIndex) {
		this.timers.set(timerIndex, this.states.get(timerIndex) + 1);
	}
	
	public void come() {
		if (this.states.get(0) == 1) {
			this.states.set(0, 0);
			this.states.set(1, 1);
		}
	}
	
	public void fill_out() {
		if (this.states.get(1) == 1 && this.states.get(11) > 0 && this.timers.get(2) >= 3) {
			reset(2);
			this.states.set(1, 0);
			this.states.set(0, 1);
			minus(11);
			plus(3);
		}
	}
	
	public void proceed() {
		if (this.states.get(3) > 0 && this.states.get(8) > 0 && this.timers.get(3) >= 2) {
			reset(3);
			minus(3);
			minus(8);
			plus(4);
		}
	}
	
	public void enter() {
		if (this.states.get(4) > 0 && this.states.get(10) > 0 && this.timers.get(4) >= 1) {
			reset(4);
			minus(4);
			minus(10);
			plus(8);
			plus(11);
			plus(5);
		}
	}
	
	public void treat() {
		if (this.states.get(5) > 0 && this.states.get(9) > 0 && this.timers.get(5) >= 10) {
			reset(5);
			minus(5);
			minus(9);
			plus(6);
		}
	}
	
	public void check_out() {
		if (this.states.get(6) > 0) {
			reset(6);
			minus(6);
			plus(9);
			plus(10);
			plus(7);
		}
	}
	
	public void too_many_waiting() {
		if (this.states.get(11) == 0 && this.states.get(1) == 1) {
			this.states.set(1, 0);
			plus(2);
		}
	}
	
	public void leave() {
		if (this.states.get(2) == 1) {
			this.states.set(2, 0);
			this.states.set(0, 1);
		}
	}
	
	public void request_med() {
		if (this.states.get(2) == 1 && this.states.get(12) > 0 && this.timers.get(0) >= 2) {
			reset(0);
			this.states.set(2, 0);
			minus(12);
			this.states.set(1, 1);
			plus(9);
		}
	}
	
	public void request_room() {
		if (this.states.get(2) == 1 && this.states.get(13) > 0 && this.timers.get(1) >= 2) {
			reset(1);
			this.states.set(2, 0);
			minus(13);
			this.states.set(1, 1);
			plus(10);
		}
	}
	
	public void give_med() {
		if (this.states.get(11) == 10 && this.states.get(9) > 0 && this.timers.get(7) >= 2) {
			reset(7);
			reset(8);
			reset(9);
			reset(10);
			minus(9);
			plus(12);
		}
	}
	
	public void take_med() {
		if (this.states.get(11) == 10 && this.states.get(12) > 0 && this.timers.get(7) >= 2) {
			reset(7);
			reset(8);
			reset(9);
			reset(10);
			minus(12);
			plus(9);
		}
	}
	
	public void give_room() {
		if (this.states.get(11) == 10 && this.states.get(10) > 0 && this.timers.get(7) >= 2) {
			reset(7);
			reset(8);
			reset(9);
			reset(10);
			minus(10);
			plus(13);
		}
	}
	
	public void take_room() {
		if (this.states.get(11) == 10 && this.states.get(13) > 0 && this.timers.get(7) >= 2) {
			reset(7);
			reset(8);
			reset(9);
			reset(10);
			minus(13);
			plus(10);
		}
	}
	
	public void waitOnce() {
		if (this.states.get(1) == 1 && this.states.get(11) > 0 && this.timers.get(2) < 4)
			increase(2);
		if (this.states.get(3) > 0 && this.states.get(8) > 0 && this.timers.get(3) < 10)
			increase(3);
		if (this.states.get(4) > 0 && this.states.get(10) > 0 && this.timers.get(4) < 5)
			increase(4);
		if (this.states.get(5) > 0 && this.states.get(9) > 0 && this.timers.get(5) < 12)
			increase(5);
		if (this.states.get(6) > 0 && this.timers.get(6) < 1)
			increase(6);
		if (this.states.get(2) == 1 && this.states.get(12) > 0)
			increase(0);
		if (this.states.get(2) == 1 && this.states.get(13) > 0)
			increase(1);
		if (this.states.get(11) == 10 && this.states.get(12) > 0)
			increase(7);
		if (this.states.get(11) == 10 && this.states.get(9) > 0)
			increase(8);
		if (this.states.get(11) == 10 && this.states.get(13) > 0)
			increase(9);
		if (this.states.get(11) == 10 && this.states.get(10) > 0)
			increase(10);
	}
	
}
