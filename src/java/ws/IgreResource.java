/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import com.mysql.jdbc.exceptions.MySQLDataException;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import json.JSONArray;
import json.JSONObject;

/**
 * REST Web Service
 *
 * @author DarthRevan
 */
@Path("igre")
public class IgreResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of IgreResource
     */
    public IgreResource() {
    }

    /**
     * Retrieves representation of an instance of ws.IgreResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() throws SQLException {
        MySQL db = new MySQL("localhost", "webprojekat", "root", "root");
        ResultSet rs = db.query("SELECT igr_naziv, znr_naziv, izd_naziv "
                + "FROM igre "
                + "INNER JOIN zanrovi "
                + "ON igre.igr_id = zanrovi.znr_id "
                + "INNER JOIN izdavaci "
                + "ON igre.igr_id = izdavaci.izd_id;");

        JSONArray list = new JSONArray();

        while (rs.next()) {
            String igr_naziv = rs.getString("igr_naziv");
            String znr_naziv = rs.getString("znr_naziv");
            String izd_naziv = rs.getString("izd_naziv");
            JSONObject obj = new JSONObject();
            obj.put("igr_naziv", igr_naziv);
            obj.put("znr_naziv", znr_naziv);
            obj.put("izd_naziv", izd_naziv);
            list.put(obj);
        }

        return list.toString();

    }

    /**
     * PUT method for updating or creating an instance of IgreResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
