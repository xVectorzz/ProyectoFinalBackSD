package coingecko;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CoinGeckoHypeFetcher {
    private static final String API_URL =
            "https://api.coingecko.com/api/v3/simple/price?ids=hype&vs_currencies=usd";

    public double obtenerPrecioHYPE() throws IOException {
        URL url = new URL(API_URL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        Scanner scanner = new Scanner(connection.getInputStream());
        StringBuilder jsonStr = new StringBuilder();

        while (scanner.hasNext()) {
            jsonStr.append(scanner.nextLine());
        }

        scanner.close();

        JSONObject json = new JSONObject(jsonStr.toString());
        return json.getJSONObject("hype").getDouble("usd");
    }
}
