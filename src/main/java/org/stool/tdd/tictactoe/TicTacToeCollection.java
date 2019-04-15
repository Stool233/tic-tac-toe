package org.stool.tdd.tictactoe;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

public class TicTacToeCollection {

    private MongoCollection mongoCollection;
    protected MongoCollection getMongoCollection() {
        return mongoCollection;
    }

    public TicTacToeCollection() {
        DB db = new MongoClient().getDB("tic-tac-toe");
        mongoCollection = new Jongo(db).getCollection("game");
    }

}
