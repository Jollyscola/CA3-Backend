package swapi;

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
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;

@Path("species")
public class SpeciesResource {

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
            System.out.println("code" + code);

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

        for (int i = 1; i < 10; i++) {

            Callable<String> callable = new Call("https://swapi.co/api/species/", i);
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
