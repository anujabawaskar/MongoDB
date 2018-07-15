import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;

public class Main {
    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("pictures");
        MongoCollection<Document> images = db.getCollection("images");
        MongoCollection<Document> albums = db.getCollection("albums");
        albums.createIndex(new BasicDBObject("images", 1));
        MongoCursor<Document> cursor = images.find().iterator();
        int count = 0;
        while(cursor.hasNext()) {
            Document next = cursor.next();
            int id = next.getInteger("_id");
            long id1 = albums.count(Filters.eq("images", id));
            if(id1 == 0) {
                images.deleteOne(next);
                count++;
            }
        }
        System.out.println(count);

    }
}

