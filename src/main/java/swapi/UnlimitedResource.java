package swapi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;

@Path("unlimited")
public class UnlimitedResource {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UnlimitedResource
     */
    public UnlimitedResource() {
    }

    public String getSwapiData(int id) throws InterruptedException, ExecutionException, ProtocolException, IOException {

        URL url = new URL("https://swapi.co/api/people/" + id);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        con.setRequestProperty("User-Agent", "server");
        Scanner scan = new Scanner(con.getInputStream());
        String jsonStr = "";
        if (scan.hasNext()) {
            jsonStr += scan.nextLine();
        }
        scan.close();
        return jsonStr;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getUnlimited() throws InterruptedException, ExecutionException, IOException {
        
        
        List<String> array = new ArrayList();
        for (int i = 1; i < 6; i++) {
            array.add(getSwapiData(i));
        }
        StringBuilder listString = new StringBuilder();
        for (String film : array) {
            listString.append(String.valueOf(film));
        }
        
        System.out.println("List" + listString.toString());
        
        return listString.toString();
    }

    /**
     * PUT method for updating or creating an instance of UnlimitedResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
