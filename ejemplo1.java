import javax.transaction.xa.*;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.*;

 
import javax.sql.DataSource;
import javax.sql.XADataSource;
 
import javax.sql.XAConnection;
	import org.postgresql.xa.*;


 
public class ejemplo1 {

	public static PGXADataSource getDataSource()
	throws SQLException
	{
	 PGXADataSource xaDS = new PGXADataSource();

	 xaDS.setServerName("localhost");
	 xaDS.setDatabaseName("sitio1");		
	 xaDS.setUser("postgres");				
	 return xaDS;
	}

	
	public static void main(String[] argv) {

	 XADataSource xaDS;
	 XAConnection xaCon;
	 XAResource xaRes, xaRes2;
	 Xid xid;
	 Connection con;
	 Statement stmt;
	 int ret;

	 try { 
	  xaDS = getDataSource();
	  xaCon = xaDS.getXAConnection("cristian", "cristian");
	  xaRes = xaCon.getXAResource();

 	  System.out.println("Conect!");

	  con = xaCon.getConnection();
	  stmt = con.createStatement();

 	  xid = new MyXid(101, new byte[]{0x01}, new byte[]{0x02});
 
	  xaRes.start(xid, XAResource.TMNOFLAGS);
 	  stmt.executeUpdate("insert into cuentas (titular) values ('titular 1')");
	  xaRes.end(xid, XAResource.TMSUCCESS);
	
	  ret = xaRes.prepare(xid);
	  if (ret == XAResource.XA_OK) {
	      ret2 = xaRes2.prepare(xid);
		  
		  xaRes.commit(xid, false);
	  }
	  stmt.close();
	  con.close();
	  xaCon.close();
	 
	 }
	 catch (XAException e) {
	  e.printStackTrace();
	 }
	 catch (SQLException e) {
	  e.printStackTrace();
	 }
	 finally {
	 }
	}
}