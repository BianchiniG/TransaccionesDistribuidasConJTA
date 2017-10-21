import java.sql.SQLException;
import java.sql.*;

import javax.sql.DataSource;
import javax.sql.XADataSource;
 
import javax.sql.XAConnection;
import org.postgresql.xa.*;


public class Conexion {
	
    public static byte[] branch = "0x01";
	private long alan = 713349;
	private XADataSource xaDS;
	private XAConnection xaCon;
	private XAResource xaRes;
	private Connection conn;
	private Xid xid;

	public void Conexion(String serverName, String dataBaseName, String userNameDS, String userNameCon, String passwd) {
		branch[0] = branch[0] + ((byte) 1);  // TODO Ver documentacion.
		this.setDataSource(serverName, dataBaseName, userNameDS);
		this.setXAConexion(userNameCon, passwd);
		this.setXid(branch);
		this.conn = xaCon.getConnection();
	}

	public void setDataSource(String serverName, String dataBaseName, String userName)throws SQLException{
		this.xaDS.setServerName(serverName);
		this.xaDS.setDatabaseName(dataBaseName);
		this.xaDS.setUser(userName);
	}

	public XADataSource getDataSource(){
		return this.xaDS;
	}

	public void setXAConexion(String userName, String passwd){
		this.xaCon = xaDS.getXAConnection(userName, passwd);
	}

	public XAConnection getXAConexion(){
		return this.xaCon;
	}

	public void setResource(){
		this.xaRes = this.xaCon.getXAResource();
	}

	public XAResource getResource(){
		return this.xaRes;
	}

	public void setXid(byte[] branch){
		this.xid = new MyXid(101, new byte[]{0x01}, branch);
	}

	public Xid getXid(){
		return this.xid;
	}

	public Connection getConexion(){
		return this.conn;
	}
}



