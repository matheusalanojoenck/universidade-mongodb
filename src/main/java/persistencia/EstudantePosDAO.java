package persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import dados.Departamento;
import dados.EstudantePos;
import dados.Professor;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudantePosDAO {
    private static EstudantePosDAO instance = null;
    private final MongoCollection<Document> collectionEstudante;

    public static EstudantePosDAO getInstance() {
        if (instance == null) instance = new EstudantePosDAO();
        return instance;
    }

    private EstudantePosDAO() {
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("universidade");
        collectionEstudante = database.getCollection("estudante_pos");
    }

    public EstudantePos select(int mat_est) {
        EstudantePos est = null;

        return est;
    }


    public List<EstudantePos> selectAll(){
        EstudantePos e = null;
        List<EstudantePos> estudantesPos = new ArrayList<>();
        List<Document>  estudanteList = collectionEstudante.find().into(new ArrayList<>());

        for(Document estudante : estudanteList){
            e = new EstudantePos();
            e.setMat_est(Integer.parseInt(estudante.get("mat_est").toString()));
            e.setNome(estudante.get("nome").toString());
            e.setIdade(Integer.parseInt(estudante.get("idade").toString()));
            e.setTipo_curso(estudante.get("tipo_curso").toString());
            e.setNum_dep(Integer.parseInt(estudante.get("num_dep").toString()));
            e.setMat_est_aconselhador(Integer.parseInt(estudante.get("mat_est_aconselhador").toString()));

            estudantesPos.add(e);
        }

        return estudantesPos;
    }

    public boolean insert(int mat_est, String nome, int idade,  String tipo_curso, int num_dep,int mat_est_aconselhador){
        Document estudante = new Document("_id", new ObjectId());

        estudante.append("mat_est", mat_est)
                .append("nome", nome)
                .append("idade", idade)
                .append("tipo_curso", tipo_curso)
                .append("num_dep", num_dep)
                .append("mat_est_aconselhador", mat_est_aconselhador);
        try {
            collectionEstudante.insertOne(estudante);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int mat_est){
        try {
            collectionEstudante.deleteOne(Filters.eq("mat_est", mat_est));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(int mat_est, String nome, int idade,  String tipo_curso, int num_dep,int mat_est_aconselhador){
        try {
            collectionEstudante.updateMany(
                    Filters.eq("mat_est", mat_est),
                    Updates.combine(
                            Updates.set("nome", nome),
                            Updates.set("idade", idade),
                            Updates.set("tipo_curso", tipo_curso),
                            Updates.set("num_dep", num_dep),
                            Updates.set("mat_est_aconselhador",mat_est_aconselhador)
                    )
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
