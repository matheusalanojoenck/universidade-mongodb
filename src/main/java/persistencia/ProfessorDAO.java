package persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import dados.Professor;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {
    private static ProfessorDAO instance = null;
    private final MongoCollection<Document> collectionProfessor;


    public static ProfessorDAO getInstance() {
        if (instance == null) instance = new ProfessorDAO();
        return instance;
    }

    private ProfessorDAO(){
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("universidade");
        collectionProfessor = database.getCollection("professor");
    }

    public Professor select(int mat_prof) {
        Professor p = null;
        collectionProfessor.find(Filters.eq("mat_prof", mat_prof));

        return p;
    }
    public List<Professor> selectAll(){
        Professor p = null;
        List<Professor> professores = new ArrayList<>();
        List<Document> professoresList = collectionProfessor.find().into(new ArrayList<>());

        for(Document professor : professoresList){
            p = new Professor();
            p.setMat_prof(Integer.parseInt(professor.get("mat_prof").toString()));
            p.setNome(professor.get("nome").toString());
            p.setIdade(Integer.parseInt(professor.get("idade").toString()));
            p.setSala(professor.get("sala").toString());
            p.setEspecialidade(professor.get("especialidade").toString());

            professores.add(p);
        }

        return professores;
    }

    public boolean insert(int mat_prof, String nome, int idade, String sala, String especialidade){
        Document professor = new Document("_id", new ObjectId());
        professor
                .append("mat_prof", mat_prof)
                .append("nome", nome)
                .append("idade", idade)
                .append("sala", sala)
                .append("especialidade", especialidade);
        try{
            collectionProfessor.insertOne(professor);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int mat_prof){
        try{
            collectionProfessor.deleteOne(Filters.eq("mat_prof", mat_prof));
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(int mat_prof, String nome, int idade, String sala, String especialidade){
        try{
            collectionProfessor.updateMany(
                    Filters.eq("mat_prof", mat_prof),
                    Updates.combine(
                            Updates.set("nome", nome),
                            Updates.set("idade", idade),
                            Updates.set("sala", sala),
                            Updates.set("especialidade", especialidade)
                    )
            );
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
