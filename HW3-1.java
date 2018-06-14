import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("school");
        MongoCollection<Document> collection = db.getCollection("students");
        List<Document> all = collection.find().into(new ArrayList<Document>()); //all documents
        for(Document d : all) {
            List<Document> scores = (List<Document>) d.get("scores");   //scores array in each document
            double min = 100.0;
            for(Document score:scores) {    //all scores for one student
                if(score.getString("type").equals("homework") && score.getDouble("score") < min) {
                    min = score.getDouble("score");
                }
            }
            BasicDBObject query = new BasicDBObject("_id", d.get("_id"));
            System.out.println(d.get("_id") + "   " + min);
            BasicDBObject fields = new BasicDBObject("scores", new BasicDBObject("type", "homework").append("score", min));
            BasicDBObject update = new BasicDBObject("$pull", fields);
            collection.updateOne(query, update);
        }

        //System.out.println();
    }
}
