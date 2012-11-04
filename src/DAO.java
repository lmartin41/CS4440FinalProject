import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQDataSource;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQResultSequence;

import net.xqj.basex.BaseXXQDataSource;


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
	 */
	public static void main(String[] args) {
		openConnection();
		try {
			XQExpression xqe = xqc.createExpression();
			XQResultSequence rs = xqe.executeQuery("'Hello World'");
			rs.writeSequence(System.out, null);
		} catch (XQException e) {
			e.printStackTrace();
		}
		closeConnection();
	}

}
