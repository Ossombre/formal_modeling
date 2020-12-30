package emergency_care_service;

public class Service {

	private int states[];
	private int timers[];
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
	 * 8 nurse					8 give_room
	 * 9 med
	 * 10 room
	 * 11 waiting
	 * 12 extern_med
	 * 13 extern_room
	 */
	
	public Service() {
		this.states = new int[14];
		this.timers = new int[9];
		this.states[0] = 1;
		this.states[8] = 2;
		this.states[9] = 2;
		this.states[10] = 3;
		this.states[11] = 10;
		this.states[12] = 5;
		this.states[13] = 7;
	}
	
	private void reset(int timerIndex) {
		this.timers[timerIndex] = 0;
	}
	
	private void minus(int stateIndex) {
		this.states[stateIndex] = this.states[stateIndex] - 1;
	}
	
	private void plus(int stateIndex) {
		this.states[stateIndex] = this.states[stateIndex] + 1;
	}
	
	private void increase(int timerIndex) {
		this.timers[timerIndex] = this.timers[timerIndex] + 1;
	}
	
	private void come() {
		this.states[0] = 0;
		this.states[1] = 1;
	}
	
	private void fill_out() {
		reset(2);
		reset(7);
		reset(8);
		this.states[1] = 0;
		this.states[0] = 1;
		minus(11);
		plus(3);
	}
	
	private void proceed() {
		reset(3);
		minus(3);
		minus(8);
		plus(4);
	}
	
	private void enter() {
		reset(4);
		minus(4);
		minus(10);
		plus(8);
		plus(11);
		plus(5);
	}
	
	private void treat() {
		reset(5);
		minus(5);
		minus(9);
		plus(6);
	}
	
	private void check_out() {
		reset(6);
		minus(6);
		plus(9);
		plus(10);
		plus(7);
	}
	
	private void too_many_waiting() {
		this.states[1] = 0;
		plus(2);
	}
	
	private void leave() {
		this.states[2] = 0;
		this.states[0] = 1;
	}
	
	private void request_med() {
		reset(0);
		this.states[2] = 0;
		minus(12);
		this.states[1] = 1;
		plus(9);
	}
	
	private void request_room() {
		reset(1);
		this.states[2] = 0;
		minus(13);
		this.states[1] = 1;
		plus(10);
	}
	
	private void give_med() {
		reset(7);
		reset(8);
		minus(9);
		plus(12);
	}
	
	private void give_room() {
		reset(7);
		reset(8);
		minus(10);
		plus(13);
	}
	
	private void waitOnce() {
		if (this.states[1] == 1 && this.states[11] > 0 && this.timers[2] < 4)
			increase(2);
		if (this.states[3] > 0 && this.states[8] > 0 && this.timers[3] < 10)
			increase(3);
		if (this.states[4] > 0 && this.states[10] > 0 && this.timers[4] < 5)
			increase(4);
		if (this.states[5] > 0 && this.states[9] > 0 && this.timers[5] < 12)
			increase(5);
		if (this.states[6] > 0 && this.timers[6] < 1)
			increase(6);
		if (this.states[2] == 1 && this.states[12] > 0)
			increase(0);
		if (this.states[2] == 1 && this.states[13] > 0)
			increase(1);
		if (this.states[11] == 10 && this.states[9] > 0)
			increase(7);
		if (this.states[11] == 10 && this.states[10] > 0)
			increase(8);
	}
	
	private void displayResources() {
		System.out.println("Nurses: " + this.states[8]);
		System.out.println("Physicians: " + this.states[9]);
		System.out.println("Rooms: " + this.states[10]);
		System.out.println("Physicians available from the resource provider: " + this.states[12]);
		System.out.println("Rooms available from the resource provider: " + this.states[13]);
		System.out.println("Patients between check in and examining rooms (waiting): " + (10 - this.states[11]));
		if (this.states[0] == 1) {
			System.out.println("A new patient can come.");
		}
		else if (this.states[1] == 1) {
			System.out.println("A new patient is checking in.");
		}
		else if (this.states[2] == 1) {
			System.out.println("A new patient is waiting because of the excess waiting time.");
		}
		if (this.states[3] > 0) {
			System.out.println("Patients having filled out their paperwork and waiting for a nurse: " + this.states[3]);
		}
		if (this.states[4] > 0) {
			System.out.println("Patients waiting for a room after being handled by a nurse: " + this.states[4]);
		}
		if (this.states[5] > 0) {
			System.out.println("Patients waiting for a physician in a room: " + this.states[5]);
		}
		if (this.states[6] > 0) {
			System.out.println("Treated patients ready to check out: " + this.states[6]);
		}
	}
	
	public Boolean printAvailableActions() {
		Boolean printed = false;
		System.out.println("0 - display resources"); //displayResources
		if (this.timers[2] < 4 && this.timers[3] < 10 && this.timers[4] < 5 && this.timers[5] < 12 && this.timers[6] < 1) {
			System.out.println("1 - wait for 1 unit of time"); //waitOnce
			printed = true;
		}
		if (this.states[0] == 1) {
			System.out.println("2 - have a new patient check in"); //come
			printed = true;
		}
		if (this.states[1] == 1 && this.states[11] > 0 && this.timers[2] >= 3) {
			System.out.println("3 - have the new patient fill out paperwork"); //fill_out
			printed = true;
		}
		if (this.states[3] > 0 && this.states[8] > 0 && this.timers[3] >= 2) {
			System.out.println("4 - have a nurse process the paperwork of a patient"); //proceed
			printed = true;
		}
		if (this.states[4] > 0 && this.states[10] > 0 && this.timers[4] >= 1) {
			System.out.println("5 - have a patient enter an examining room"); //enter
			printed = true;
		}
		if (this.states[5] > 0 && this.states[9] > 0 && this.timers[5] >= 10) {
			System.out.println("6 - have a physician examine a patient"); //treat
			printed = true;
		}
		if (this.states[6] > 0) {
			System.out.println("7 - have a treated patient check out"); //check_out
			printed = true;
		}
		if (this.states[11] == 0 && this.states[1] == 1) {
			System.out.println("8 - the new patient is not admitted because of the waiting time"); //too_many_waiting
			printed = true;
		}
		if (this.states[2] == 1) {
			System.out.println("9 - the new patient simply leaves"); //leave
			printed = true;
		}
		if (this.states[2] == 1 && this.states[12] > 0 && this.timers[0] >= 2) {
			System.out.println("10 - request a physician from the service provider"); //request_med
			printed = true;
		}
		if (this.states[2] == 1 && this.states[13] > 0 && this.timers[1] >= 2) {
			System.out.println("11 - request a room from the service provider"); //request_room
			printed = true;
		}
		if (this.states[11] == 10 && this.states[9] > 0 && this.timers[7] >= 2) {
			System.out.println("12 - offer a physician through the resource provider"); //give_med
			printed = true;
		}
		if (this.states[11] == 10 && this.states[10] > 0 && this.timers[8] >= 2) {
			System.out.println("13 - offer a room through the resource provider"); //give_room
			printed = true;
		}
		return printed;
	}
	
	public void executeAction(String action) {
		int code = Integer.parseInt(action);
		switch(code) {
			case 0:
				displayResources();
				break;
			case 1:
				waitOnce();
				break;
			case 2:
				come();
				break;
			case 3:
				fill_out();
				break;
			case 4:
				proceed();
				break;
			case 5:
				enter();
				break;
			case 6:
				treat();
				break;
			case 7:
				check_out();
				break;
			case 8:
				too_many_waiting();
				break;
			case 9:
				leave();
				break;
			case 10:
				request_med();
				break;
			case 11:
				request_room();
				break;
			case 12:
				give_med();
				break;
			case 13:
				give_room();
				break;
			default:
				throw new java.lang.NumberFormatException("Invalid number");
		}
	}
	
}
