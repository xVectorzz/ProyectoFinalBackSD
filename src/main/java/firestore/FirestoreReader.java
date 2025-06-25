package firestore;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.api.core.ApiFuture;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FirestoreReader {
    private final Firestore db;

    public FirestoreReader(String pathToJsonKey) throws IOException {
        FileInputStream serviceAccount = new FileInputStream(pathToJsonKey);

        FirestoreOptions options = FirestoreOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setProjectId("proyectofinalprueba-463717")
                .setDatabaseId("database") // Esto es importante, igual que tu FirestoreWriter
                .build();

        this.db = options.getService();
    }

    public void leerHistorial(String nombreCripto) throws ExecutionException, InterruptedException {
        ApiFuture<QuerySnapshot> future = db.collection("precios")
                .document(nombreCripto)
                .collection("historial")
                .get();

        List<QueryDocumentSnapshot> documentos = future.get().getDocuments();

        if (documentos.isEmpty()) {
            System.out.println("No hay registros para " + nombreCripto);
        } else {
            System.out.println("Historial de " + nombreCripto + ":");
            for (QueryDocumentSnapshot doc : documentos) {
                System.out.println("→ " + doc.getId() + ": " + doc.getData());
            }
        }
    }

    public void cerrar() throws Exception {
        db.close();
    }

    // Método de prueba para ejecutar desde main
    public static void main(String[] args) throws Exception {
        FirestoreReader reader = new FirestoreReader("src/main/resources/proyectofinalprueba-463717-41c1eef35d84.json");
        reader.leerHistorial("bitcoin");
        reader.cerrar();
        System.exit(0);
    }
}
