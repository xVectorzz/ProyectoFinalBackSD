package firestore;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.WriteResult;

import java.io.FileInputStream;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class FirestoreWriter {
    private Firestore db;

    public FirestoreWriter(String pathToJsonKey) throws Exception {
        FileInputStream serviceAccount = new FileInputStream(pathToJsonKey);

        FirestoreOptions options = FirestoreOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setProjectId("proyectofinalprueba-463717")
                .setDatabaseId("database")  // Igual que en tu conexión exitosa
                .build();

        db = options.getService();
    }

    public void guardarPrecio(String nombreCripto, double precioUSD) throws Exception {
        String timestamp = LocalDateTime.now().toString();

        Map<String, Object> data = new HashMap<>();
        data.put("precio", precioUSD);
        data.put("timestamp", timestamp);

        DocumentReference docRef = db.collection("precios")
                .document(nombreCripto)
                .collection("historial")
                .document(timestamp);

        WriteResult result = docRef.set(data).get();
        System.out.println("Guardado: " + nombreCripto + " $" + precioUSD + " a las " + result.getUpdateTime());
    }

    public static void main(String[] args) {
        try {
            FirestoreWriter writer = new FirestoreWriter("src/main/resources/proyectofinalprueba-463717-41c1eef35d84.json");
            writer.guardarPrecio("bitcoin", 35000.00);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Guardado: ...");
        System.exit(0);

    }
}
