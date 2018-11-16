/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swapi;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Jollys
 */
@Path("films")
public class FilmsResource {

    public FilmsResource() {
    }

     private class Call implements Callable<String> {

        private String url;
        private int id;

        public Call(String url, int id) {
            this.url = url;
            this.id = id;
        }

        @Override
        public String call() throws Exception {
            return getSwapiData(url, id);
        }

    }

    public String getSwapiData(String urlString, int id) throws InterruptedException, ExecutionException, ProtocolException, IOException {

        URL url = new URL(urlString + id);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        con.setRequestProperty("User-Agent", "server");
        int code = con.getResponseCode();
        if (code == 200) {
           

            Scanner scan = new Scanner(con.getInputStream());
            String jsonStr = "";
            if (scan.hasNext()) {
                jsonStr += scan.nextLine();
            }

            return jsonStr;
        } else {

            return null;
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getswapipeople() throws MalformedURLException, IOException, InterruptedException, ExecutionException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Future<String>> list = new ArrayList<>();

        //List<String> list = new ArrayList();
        for (int i = 1; i < 10; i++) {

            Callable<String> callable = new Call("https://swapi.co/api/films/", i);
            Future<String> future = executorService.submit(callable);
            list.add(future);

        }

        StringBuilder builder = new StringBuilder();
        builder.append('[');
        for (int i = 0; i < list.size(); i++) {
            String result = list.get(i).get();
            if (result != null) {

                builder.append(result);
                if (i < list.size() - 1) {
                    builder.append(',');
                }
            }

        }
        
          if (',' == builder.charAt(builder.length() - 1)) {
            builder.setLength(builder.length() - 1);
        }
        builder.append(']');
        return builder.toString();

    }
}
