package swapi;

import java.io.IOException;
import java.net.HttpURLConnection;
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

public class ThreadedDownload {

    public List<String> allSwapiData() throws InterruptedException, ExecutionException {
        ArrayList<String> swapi = new ArrayList();
        swapi.add("https://swapi.co/api/films/");
        swapi.add("https://swapi.co/api/people/");
        swapi.add("https://swapi.co/api/planets/");
        swapi.add("https://swapi.co/api/species/");
        swapi.add("https://swapi.co/api/starships/");
        swapi.add("https://swapi.co/api/vehicles/");
        System.out.println("ArrayList " + swapi);

        ExecutorService executorService = Executors.newFixedThreadPool(swapi.size());
        List<Future<String>> list = new ArrayList<>();

        for (int i = 0; i < 1; i++) {
            for (int j = 1; j < 6; j++) {
                Callable<String> callable = new Call(swapi.get(i) + j);
                Future<String> future = executorService.submit(callable);
                list.add(future);
            }

        }

        List<String> returnlist = new ArrayList();
        for (Future<String> f : list) {
            try {
                returnlist.add(f.get().toString());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        executorService.shutdown();
        return returnlist;

    }

    private static class Call implements Callable<String> {

        private String url;

        public Call(String url) {
            this.url = url;
        }

        @Override
        public String call() throws Exception {
            return getSwapiData();
        }

        public String getSwapiData() throws InterruptedException, ExecutionException, ProtocolException, IOException {

            URL url = new URL(this.url);
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

    }
}
