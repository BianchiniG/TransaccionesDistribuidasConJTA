import java.sql.Statement;
import java.sql.SQLException;

import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

public class Punto14{

    public static String ip_alan;
    public static String ip_emiliano;
    public static String ip_german;
    public static String ip_ian;
    public Conexion conexion_alan;
    public Conexion conexion_emiliano;
    public Conexion conexion_german;
    public Conexion conexion_ian;

    public Punto14(){};

    public static Boolean realizaTransaccionPrepare(XAResource res, Xid xid) throws XAException {
        int ret = res.prepare(xid);
        return ret == XAResource.XA_OK;
    }

    public void Conectar(String[] Conexiones){
        
        this.ip_alan = (Conexiones[0]);
        this.ip_emiliano = Conexiones[1];
        this.ip_german = Conexiones[2];
        this.ip_ian = Conexiones[3];

        this.conexion_alan = new Conexion(
            this.ip_alan,"sitioAlan","postgres",
            "alan","alan"
        );
        this.conexion_emiliano = new Conexion(
            this.ip_emiliano,"sitioEmiliano","postgres",
            "emiliano","emiliano"
        );
        this.conexion_german = new Conexion(
            this.ip_german,"sitioGerman","postgres",
            "german","german"
        );
        this.conexion_ian = new Conexion(
            this.ip_ian,"sitioIan","postgres",
            "ian","ian"
        );
    }

    public Conexion determinar_conexion(int idCuenta){
        if (idCuenta >= 1000 && idCuenta < 2000){
            return this.conexion_alan; 
         }else if (idCuenta >= 2000 && idCuenta < 3000){
            return this.conexion_emiliano;
         }else if (idCuenta >= 3000 && idCuenta < 4000){
            return this.conexion_german;
         }else if (idCuenta >= 4000 && idCuenta < 5000){
            return this.conexion_ian;
         }else{
             System.out.println("ID FUERA DE RANGO");
             return null;
         }
    }

    public int Transferir(int monto, int idOrigen, int idDestino){
        try {
            Conexion origen = determinar_conexion(idOrigen);
            Conexion destino = determinar_conexion(idDestino);
            
            Statement stmt_origen = origen.getConexion().createStatement(); 
            Statement stmt_destino = destino.getConexion().createStatement();

            origen.getResource().start(origen.getXid(), XAResource.TMNOFLAGS);
            destino.getResource().start(destino.getXid(), XAResource.TMNOFLAGS);
            try{
                stmt_origen.executeUpdate("UPDATE cuentas SET saldo = saldo -" + monto);
                stmt_destino.executeUpdate("UPDATE cuentas SET saldo = saldo +" + monto);

                origen.getResource().end(origen.getXid(), XAResource.TMSUCCESS);
                destino.getResource().end(destino.getXid(), XAResource.TMSUCCESS);
        
                if ( realizaTransaccionPrepare(origen.getResource(), origen.getXid()) &&
                realizaTransaccionPrepare(destino.getResource(), destino.getXid())){
                    origen.getResource().commit(origen.getXid(),false);
                    destino.getResource().commit(destino.getXid(),false);
                }else{
                    origen.getResource().rollback(origen.getXid());
                    destino.getResource().rollback(destino.getXid());
                }
            }catch(SQLException e){
                System.out.println("Hubo falla en el Update");
                origen.getResource().rollback(origen.getXid());
                destino.getResource().rollback(destino.getXid());
            }
            stmt_origen.close();
            stmt_destino.close();
            origen.getConexion().close();
            destino.getConexion().close();
            origen.getXAConexion().close();
            destino.getXAConexion().close();
            return 0;
        }catch (XAException e) {
            e.printStackTrace();
            return 1;
        }catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
    }
}


