package binance;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;

public class BinancePriceFetcher {
    private static final String[] CRYPTOS = {
            "BTCUSDT", "ETHUSDT", "XRPUSDT", "SOLUSDT", "TRXUSDT",
            "DOGEUSDT", "ADAUSDT", "BCHUSDT", "LINKUSDT"
    };

    public Map<String, Double> obtenerPrecios() throws IOException {
        Map<String, Double> precios = new HashMap<>();

        for (String symbol : CRYPTOS) {
            URL url = new URL("https://api.binance.com/api/v3/ticker/price?symbol=" + symbol);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            Scanner scanner = new Scanner(connection.getInputStream());
            StringBuilder jsonStr = new StringBuilder();

            while (scanner.hasNext()) {
                jsonStr.append(scanner.nextLine());
            }

            scanner.close();

            JSONObject response = new JSONObject(jsonStr.toString());
            double price = response.getDouble("price");
            precios.put(symbol, price);
        }

        return precios;
    }
}
