import java.beans.Statement;
import java.sql.SQLException;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

public class Punto14{

    public static final String ip_alan = "192.168.2.117";
    public static final String ip_emiliano = "192.168.2.112";
    public static final String ip_german = "192.168.2.129";
    public static final String ip_ian = "192.168.2.69";

    public static void main(String[] argv) {
        try {
            // Creamos las conexiones 
            Conexion conexion_alan = new Conexion(
                ip_alan,"sitioAlan","postgres",
                "alan","alan"
            );
            Conexion conexion_emiliano = new Conexion(
                ip_emiliano,"sitioEmiliano","postgres",
                "emiliano","emiliano"
            );
            Conexion conexion_german = new Conexion(
                ip_german,"sitioGerman","postgres",
                "german","german"
            );
            Conexion conexion_ian = new Conexion(
                ip_ian,"sitioIan","postgres",
                "ian","ian"
            );
            
            // Creamos los statements
            Statement stmt_alan = conexion_alan.getConexion().createStatement();
            Statement stmt_emiliano = conexion_emiliano.getConexion().createStatement();
            Statement stmt_german = conexion_german.getConexion().createStatement();
            Statement stmt_ian = conexion_ian.getConexion().createStatement();
            
            // 
            conexion_alan.getResource().start(conexion_alan.getXid(), XAResource.TMNOFLAGS);
            conexion_emiliano.getResource().start(conexion_emiliano.getXid(), XAResource.TMNOFLAGS);
            conexion_german.getResource().start(conexion_german.getXid(), XAResource.TMNOFLAGS);
            conexion_ian.getResource().start(conexion_ian.getXid(), XAResource.TMNOFLAGS);

            // 
            stmt_alan.executeUpdate("insert into cuentas (titular) values ('titular 1')");
            stmt_emiliano.executeUpdate("insert into cuentas (titular) values ('titular 1')");
            stmt_german.executeUpdate("insert into cuentas (titular) values ('titular 1')");
            stmt_ian.executeUpdate("insert into cuentas (titular) values ('titular 1')");

            // 
            conexion_alan.getResource().end(conexion_alan.getXid(), XAResource.TMSUCCESS);
            conexion_emiliano.getResource().end(conexion_emiliano.getXid(), XAResource.TMSUCCESS);
            conexion_german.getResource().end(conexion_german.getXid(), XAResource.TMSUCCESS);
            conexion_ian.getResource().end(conexion_ian.getXid(), XAResource.TMSUCCESS);

            // 
            System.out.println(realizaTransaccion(conexion_alan.getResource(), conexion_alan.getXid()));
            System.out.println(realizaTransaccion(conexion_emiliano.getResource(), conexion_emiliano.getXid()));
            System.out.println(realizaTransaccion(conexion_german.getResource(), conexion_german.getXid()));
            System.out.println(realizaTransaccion(conexion_ian.getResource(), conexion_ian.getXid()));
            
            //
            stmt_alan.close();
            stmt_emiliano.close();
            stmt_german.close();
            stmt_ian.close();
            conexion_alan.getConexion().close();
            conexion_emiliano.getConexion().close();
            conexion_german.getConexion().close();
            conexion_ian.getConexion().close();
            conexion_alan.getXAConexion().close();
            conexion_emiliano.getXAConexion().close();
            conexion_german.getXAConexion().close();
            conexion_ian.getXAConexion().close();

        }catch (XAException e) {
            e.printStackTrace();
        }catch (SQLException e) {
            e.printStackTrace();
        }finally {
        }
    }

    public static Boolean realizaTransaccion(XAResource res, Xid xid) {
        ret = res.prepare(xid);
        if (ret == XAResource.XA_OK) {
            res.commit(xid, false);
            return true;
        }
        return false;
    }
}

