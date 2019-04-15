package org.stool.tdd.tictactoe;

import org.jongo.MongoCollection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.stool.tdd.tictactoe.mongo.TicTacToeBean;
import static org.mockito.Mockito.*;

public class TicTacToeCollectionSpec {

    TicTacToeCollection collection;

    @Before
    public void before() {
        collection = spy(new TicTacToeCollection());
    }

    @Test
    public void whenInstantiatedThenMongoHasDbNameTicTacToe() {
        Assert.assertEquals("tic-tac-toe", collection.getMongoCollection()
                                                              .getDBCollection().getDB().getName());
    }

    @Test
    public void whenInstaniatedThenMongoCollectionHasNameGame() {
        Assert.assertEquals("game", collection.getMongoCollection().getName());
    }

    @Test
    public void whenSaveMoveThenInvokeMongoCollectionSave() {
        TicTacToeBean bean = new TicTacToeBean(3, 2, 1, 'Y');
        MongoCollection mongoCollection = mock(MongoCollection.class);
        doReturn(mongoCollection).when(collection)
                .getMongoCollection();
        collection.saveMove(bean);
        verify(mongoCollection, times(1)).save(bean);
    }

}
