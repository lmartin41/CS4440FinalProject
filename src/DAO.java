
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.basex.BaseXServer;
import org.basex.core.cmd.Close;
import org.basex.core.cmd.CreateDB;
import org.basex.core.cmd.DropDB;
import org.basex.core.cmd.Open;
import org.basex.core.cmd.XQuery;
import org.basex.server.ClientSession;

public class DAO {

	//database connection parameters
	public final String SERVERNAME = "localhost";
	public final int PORT = 1984;
	public final String USERNAME = "admin";
	public final String PASSWORD = "admin";
	public static final String ACCESS_PATH = "./XMLfiles/";
	public ClientSession session;
	public BaseXServer server;
	private ByteArrayOutputStream baos; 

	public void openConnection() {
		try {
			server = new BaseXServer();
			session = new ClientSession(SERVERNAME, PORT, USERNAME, PASSWORD);
			createDB();
		} catch (IOException e) {
			System.err.println("Could not start the server");
			e.printStackTrace();
		}
	}

	public void closeConnection() {
		try {
			session.close();
			server.stop();
		} catch (IOException e) {
			System.err.println("Could not close the server");
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds ALL documents in the path into a Collection
	 */
	public void createDB() {
		try {
			session.execute(new CreateDB("Collection", ACCESS_PATH));
		} catch (IOException e) {
			System.err.println("Unable to create DB");
			e.printStackTrace();
		}
	}

	public void dropDB() {
		try {
			session.execute(new DropDB("Collection"));
		} catch (IOException e) {
			System.err.println("Unable to drop DB");
			e.printStackTrace();
		}
	}
	
	public void openDB(String name) {
		try {
			session.execute(new Open(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void closeDB() {
		try {
			session.execute(new Close());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void saveDB(String name) {
		try {
			session.execute(new CreateDB(name));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String executeQuery(String query) {
		baos = new ByteArrayOutputStream();
		try {
			session.setOutputStream(baos);
			session.query(query).execute();
		} catch (IOException e) {
			System.err.println("Error executing query");
			e.printStackTrace();
		}
		return baos.toString();
	}
	
}
