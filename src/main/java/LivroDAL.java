
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.*;
import com.mycompany.desafio.tecnicas.Erro;
import com.mycompany.desafio.tecnicas.Livro;


import java.io.IOException;

public class LivroDAL {

    private static DatabaseReference database;

    public static void conecta() {
        try {
            GoogleCredentials credentials = GoogleCredentials.getApplicationDefault();
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .setProjectId("db-desafio-tecnicas-9ce74")
                    .build();
            FirebaseApp.initializeApp(options);

            Firestore db = FirestoreClient.getFirestore();
        } catch (IOException e) {
            Erro.setErro(e.getMessage());
        }
    }

    public static void inseriLivro(Livro _umlivro) {
        Erro.setErro(false);
        try {
            String key = database.push().getKey();
            database.child(key).setValueAsync(_umlivro);
        } catch (Exception e) {
            Erro.setErro(e.getMessage());
        }
    }

}
