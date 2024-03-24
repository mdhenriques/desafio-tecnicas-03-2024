package com.mycompany.desafio.tecnicas;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class LivroDAL {

    private static Firestore db;

    public static void conecta() {
        try {

            InputStream serviceAccount = new FileInputStream("credenciais/db-desafio-tecnicas-9ce74-firebase-adminsdk-iy2eb-232854ee9f.json");
            GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(credentials)
                    .build();
            FirebaseApp.initializeApp(options);

            db = FirestoreClient.getFirestore();

        } catch (IOException e) {
            Erro.setErro(e.getMessage());
        }
    }

    public static void insereLivro(Livro _umlivro) {
        try {
            if (db != null) {
                DocumentReference docRef = db.collection("livros").document();

                Map<String, Object> data = new HashMap<>();
                data.put("titulo", _umlivro.getTitulo());
                data.put("autor", _umlivro.getAutor());
                data.put("editora", _umlivro.getEditora());
                data.put("anoDeEdicao", _umlivro.getAnoEdicao());
                data.put("localizacao", _umlivro.getLocalizacao());

                ApiFuture<WriteResult> result = docRef.set(data);


                System.out.println("Update time : " + result.get().getUpdateTime());
            } else {
                System.err.println("Db nao foi inicializado corretamente");
            }

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Erro ao obter a hora da atualização");
        }
    }

    public static void excluiLivro(String titulo) {
        try {
            if (db != null) {
                Query query = db.collection("livros").whereEqualTo("titulo", titulo);
                QuerySnapshot querySnapshot = query.get().get();

                if (!querySnapshot.isEmpty()) {
                    DocumentSnapshot docSnapshot = querySnapshot.getDocuments().get(0);
                    String livroId = docSnapshot.getId();

                    DocumentReference docRef = db.collection("livros").document(livroId);

                    ApiFuture<WriteResult> result = docRef.delete();

                    result.get();
                } else {
                    System.err.println("Livro nao encontrado");
                }
            } else {
                System.err.println("Erro: db nao inicializado corretamente.");
            }
        } catch (Exception e) {
            Erro.setErro(e.getMessage());
        }
    }

    public static void alteraLivroPorTitulo(String titulo, Livro novoLivro) {
        try {
            if (db != null) { 
               
                Query query = db.collection("livros").whereEqualTo("titulo", titulo);
                QuerySnapshot querySnapshot = query.get().get();

                if (!querySnapshot.isEmpty()) {
                    DocumentSnapshot docSnapshot = querySnapshot.getDocuments().get(0);
                    String livroId = docSnapshot.getId();

                    DocumentReference docRef = db.collection("livros").document(livroId);

                    ApiFuture<WriteResult> result = docRef.set(novoLivro);

                    result.get();

                    System.out.println("Livro com título '" + titulo + "' atualizado com sucesso!");
                } else {
                    System.err.println("Livro com título '" + titulo + "' não encontrado.");
                }
            } else {
                System.err.println("Erro: db não foi inicializado corretamente.");
            }
        } catch (Exception e) {
            Erro.setErro(e.getMessage());
        }
    }

    public static Livro consultaLivroPorTitulo(String titulo) {
        Livro livro = null;
        try {
            if (db != null) {
                
                Query query = db.collection("livros").whereEqualTo("titulo", titulo);
                QuerySnapshot querySnapshot = query.get().get();
                
                if (!querySnapshot.isEmpty()) {                   
                    DocumentSnapshot docSnapshot = querySnapshot.getDocuments().get(0);
                    
                    livro = docSnapshot.toObject(Livro.class);
                } else {
                    System.err.println("Livro com título '" + titulo + "' não encontrado.");
                }
            } else {
                System.err.println("Erro: db não foi inicializado corretamente.");
            }
        } catch (InterruptedException | ExecutionException e) {
            Erro.setErro(e.getMessage());
        }
        return livro;
    }

}
