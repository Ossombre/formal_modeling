package emergency_care_service;
import java.util.Scanner;

public class Simulation {

	public static void main(String[] args) {
		Service service = new Service();
		Scanner scan = new Scanner(System.in);
		Boolean availableActions = true;
		Boolean wrong = false;
		while(availableActions) {
			System.out.println("The following actions are available:");
			availableActions = service.printAvailableActions();
			do {
				System.out.println("Please enter the number of the desired action:");
				String action = scan.nextLine();
				try {
					service.executeAction(action);
				}
				catch(java.lang.NumberFormatException e) {
					System.out.println("Invalid number.");
					wrong = true;
				}
			} while(wrong);
			wrong = false;
		}
		scan.close();
		System.out.println("It seems we hit a deadlock situation");
	}

}
