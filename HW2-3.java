package com.company;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("students");
        MongoCollection<Document> collection = db.getCollection("grades");
        Bson filter = new Document("type", "homework");
        Bson sort = Sorts.orderBy(Sorts.ascending("student_id"), Sorts.ascending("score"));
        List<Document> homework = collection.find(filter).sort(sort).into(new ArrayList<Document>());
        int id = -1;
        for(Document d : homework) {
            int temp = d.getInteger("student_id");

            if(temp != id) {
                Bson del = new Document("_id", d.get("_id"));
                collection.deleteOne(del);
            }

            id = temp;
            //System.out.println(d.getString("type"));
            //System.out.println(d.getInteger("student_id"));
            //System.out.println(d.getDouble("score"));
        }

    }
}
