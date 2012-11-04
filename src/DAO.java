import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQResultSequence;
import net.xqj.basex.BaseXXQDataSource;
import org.basex.core.*;
import org.basex.core.cmd.*;



public class DAO {

	//database connection parameters
	//to start server: java -cp BaseX.jar org.basex.BaseXServer
	public static final String SERVERNAME = "localhost";
	public static final String PORT = "1984";
	public static final String USERNAME = "admin";
	public static final String PASSWORD = "admin";

	public static BaseXXQDataSource ds;
	public static XQConnection xqc;

	public DAO() {

	}

	public static void openConnection() {
		ds = new BaseXXQDataSource();
		ds.setProperty("serverName", SERVERNAME);
		ds.setProperty("port", PORT);
		ds.setProperty("user", USERNAME);
		ds.setProperty("password", PASSWORD);
		xqc = ds.getConnection();
	}

	public static void closeConnection() {
		try {
			xqc.close();
		} catch (XQException e) {
			e.printStackTrace();
		}
	}
	
	/* 
	 * Test main to see if connection is working
	 
	public static void main(String[] args) {
		openConnection();
		try {
			XQExpression xqe = xqc.createExpression();
			XQResultSequence rs2 = xqe.executeQuery("//food/snack");
			System.out.println(rs2);
		} catch (XQException e) {
			e.printStackTrace();
		}
		closeConnection();
	}
	*/
	
	public static void main(final String[] args) throws BaseXException {
		openConnection();
	    /** Database context. */
	    Context context = new Context();

	    System.out.println("=== RunCommands ===");

	    // ------------------------------------------------------------------------
	    // Create a database from a local or remote XML document or XML String
	    System.out.println("\n* Create a database.");

	    new CreateDB("DBExample", "food.xml").
	      execute(context);

	    // ------------------------------------------------------------------------
	    // Close and reopen the database
	    System.out.println("\n* Close and reopen database.");

	    new Close().execute(context);
	    new Open("DBExample").execute(context);

	    // ------------------------------------------------------------------------
	    // Additionally create a full-text index
	    System.out.println("\n* Create a full-text index.");

	    new CreateIndex("fulltext").execute(context);

	    // ------------------------------------------------------------------------
	    // Show information on the currently opened database
	    System.out.println("\n* Show database information:");

	    System.out.print(new InfoDB().execute(context));

	    // ------------------------------------------------------------------------
	    // Drop indexes to save disk space
	    System.out.println("\n* Drop indexes.");

	    new DropIndex("text").execute(context);
	    new DropIndex("attribute").execute(context);
	    new DropIndex("fulltext").execute(context);

	    // ------------------------------------------------------------------------
	    // Drop the database
	    System.out.println("\n* Drop the database.");

	    new DropDB("DBExample").execute(context);

	    // ------------------------------------------------------------------------
	    // Show all existing databases
	    System.out.println("\n* Show existing databases:");

	    System.out.print(new List().execute(context));

	    // ------------------------------------------------------------------------
	    // Close the database context
	    context.close();
	    closeConnection();
	  }

}
