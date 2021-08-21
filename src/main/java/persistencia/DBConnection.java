package persistencia;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.sql.*;


public class DBConnection {
    private static MongoClient connection = null;
    private static MongoDatabase database = null;

    static public MongoDatabase getConnection() {
        if (connection == null) {

            try {
                connection = MongoClients.create();
                database = connection.getDatabase("unicersidade");
            }
            catch (Exception e) {
                e.printStackTrace();
            }

        }

        //if(connection == null) System.out.println("connection null");
        return database;
    }
}
