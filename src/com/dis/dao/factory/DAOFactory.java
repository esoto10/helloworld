package com.dis.dao.factory;

import com.dis.dao.iface.CheckinDAO;
import com.dis.dao.iface.ReservaDAO;
import com.dis.dao.iface.UsuarioDAO;
import com.dis.dao.iface.VueloDAO;

public abstract class DAOFactory {
    
    public static final int MYSQL = 1;
    public static final int ORACLE = 2;
    public static final int DB2 = 3;
    public static final int SQLSERVER = 4;
    public static final int XML = 5;
    public static final int JPA = 6;
    
    
    // Existirá un método por cada DAO que pueda ser creado.
    // Ejemplo:
    //public abstract ArticuloDAO getArticuloDAO();

    public abstract UsuarioDAO getUsuarioDAO();
    public abstract ReservaDAO getReservaDAO();
    public abstract VueloDAO getVueloDAO();  
    public abstract CheckinDAO getCheckinDAO();

    public static DAOFactory getDAOFactory(int whichFactory){
       switch(whichFactory){
      	case MYSQL:
       	    return new MySqlDAOFactory();
       	case XML:
       	    return new XmlDAOFactory();
       	case ORACLE:
       	    return new OracleDAOFactory();
       	/*case JPA:
       	    return new JpaDAOFactory();
       	case DB2:
       	    return new Db2DAOFactory();
       	case SQLSERVER:
       	    return new SqlServerDAOFactory();
       	case XML:
       	    return new XmlDAOFactory();*/
       	default:
       	    return null;
       }
    }
    
    
}
