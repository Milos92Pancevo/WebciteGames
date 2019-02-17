package ws;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;

public class MySQL {

    Connection conn;

    public MySQL(String host, String schema, String user, String pass) throws SQLException {
        Properties p = new Properties();
        p.setProperty("user", user);
        p.setProperty("password", pass);
        p.setProperty("useUnicode", "yes");
        p.setProperty("characterEncoding", "UTF-8");
        String url = "jdbc:mysql://" + host + "/" + schema;
        conn = DriverManager.getConnection(url, p);
    }

    public ResultSet query(String sql, Object ... params) throws SQLException {
        PreparedStatement s = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        for(int i=0; i<params.length; i++) {
            s.setObject(i+1, params[i]);
        }
        return s.executeQuery();
    }

    public ArrayList<Integer> execute(String sql, Object ... params) throws SQLException {

        PreparedStatement s = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
        for(int i=0; i<params.length; i++) {
            s.setObject(i+1, params[i]);
        }
        s.execute();
        ResultSet r = s.getGeneratedKeys();
        ArrayList<Integer> keys = new ArrayList<>();
        while (r.next()) {
            keys.add(r.getInt(1));
        }
        r.close();
        s.close();
        return keys;
    }

    public void close() throws SQLException {
        conn.close();
    }

    public static void main(String[] args) throws Exception {
        MySQL db = new MySQL("localhost", "proba", "root", "");
        ResultSet rs = db.query("SELECT * FROM tabela");
        while (rs.next()) {
            System.out.println(rs.getObject(1));
        }
    }
}
