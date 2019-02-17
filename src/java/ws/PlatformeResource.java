/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
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
@Path("platforme")
public class PlatformeResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of PlatformeResource
     */
    public PlatformeResource() {
    }

    /**
     * Retrieves representation of an instance of ws.PlatformeResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() throws SQLException {
        
        MySQL db = new MySQL("localhost", "webprojekat", "root", "root");
        ResultSet rs = db.query("SELECT igre.igr_naziv, platforme.plt_naziv "
                + "FROM igre_platforme AS i "
                + "INNER JOIN igre "
                + "ON i.igr_id = igre.igr_id "
                + "INNER JOIN platforme "
                + "ON i.plt_id = platforme.plt_id");
        
        JSONArray list = new JSONArray();
        
        while (rs.next()) {
            String igr_naziv = rs.getString("igre.igr_naziv");
            String plt_naziv = rs.getString("platforme.plt_naziv");
            JSONObject obj = new JSONObject();
            obj.put("igre.igr_naziv", igr_naziv);
            obj.put("platforme.plt_naziv", plt_naziv);
            list.put(obj);
        }
        return list.toString();
    }

    /**
     * PUT method for updating or creating an instance of PlatformeResource
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
