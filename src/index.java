import java.util.Scanner;

import org.basex.core.BaseXException;


public class index {

	private static Scanner scanner;
	private static DAO dao;

	public static void main(String[] args) throws BaseXException {
		dao = new DAO();
		dao.openConnection();
		
		scanner = new Scanner(System.in);
		runQueries();
		
		dao.closeConnection();
	}

	private static void runQueries() {
		System.out.println("\n\nPlease enter your query.  Press q to quit");
		String query = scanner.nextLine();
		if (query.equals("q")) return;
		else {
			dao.executeQuery(query);
			runQueries();
		}
		
	}
}
