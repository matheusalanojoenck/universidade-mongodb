package persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.UpdateManyModel;
import com.mongodb.client.model.Updates;
import dados.Departamento;
import dados.Professor;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAO {
    private static DepartamentoDAO instance = null;
    private final MongoCollection<Document> collectionDepartamento;


    public static DepartamentoDAO getInstance() {
        if (instance == null) instance = new DepartamentoDAO();
        return instance;
    }

    private DepartamentoDAO(){
        MongoClient mongoClient = MongoClients.create();
        MongoDatabase database = mongoClient.getDatabase("universidade");
        collectionDepartamento = database.getCollection("departamento");
    }

    public Departamento select(int num_dep) {
        Departamento d = null;

        return d;
    }
    public List<Departamento> selectAll(){
        Departamento d = null;
        List<Departamento> departamentos = new ArrayList<>();
        List<Document> departamentoList = collectionDepartamento.find().into(new ArrayList<>());
        for (Document departamento: departamentoList){
            d = new Departamento();
            d.setNum_dep(Integer.parseInt(departamento.get("num_dep").toString()));
            d.setNome(departamento.get("nome").toString());
            d.setEscritorio(departamento.get("escritorio").toString());
            d.setMat_prof(Integer.parseInt(departamento.get("mat_prof").toString()));

            departamentos.add(d);
        }

        return  departamentos;
    }

    public boolean insert(int num_dep, String nome, String escritorio, int mat_prof){
        Document departamento = new Document("_id", new ObjectId());

        departamento.append("num_dep", num_dep)
                .append("nome", nome)
                .append("escritorio", escritorio)
                .append("mat_prof", mat_prof);

        try{
            collectionDepartamento.insertOne(departamento);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int num_dep){
        try {
           collectionDepartamento.deleteOne(Filters.eq("num_dep", num_dep));
           return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(int num_dep, String nome, String escritorio, int mat_prof){
        try {
            collectionDepartamento.updateMany(
                    Filters.eq("num_dep", num_dep),
                    Updates.combine(
                            Updates.set("nome", nome),
                            Updates.set("escritorio", escritorio),
                            Updates.set("mat_prof", mat_prof)
                    )
            );
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
