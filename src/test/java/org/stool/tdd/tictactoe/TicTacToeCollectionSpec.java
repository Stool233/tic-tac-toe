package org.stool.tdd.tictactoe;

import com.mongodb.MongoException;
import org.jongo.MongoCollection;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.stool.tdd.tictactoe.mongo.TicTacToeBean;
import static org.mockito.Mockito.*;

/**
 * 需求1：实现一个保存单步棋——包含轮次、X和Y坐标以及玩家（X或O）——的选项
 *
 * 需求2：将每步棋保存到数据库，并确保并确保开始新游戏时删除旧数据
 */
public class TicTacToeCollectionSpec {

    @Rule
    public ExpectedException exception =
            ExpectedException.none();

    TicTacToeCollection collection;
    TicTacToeBean bean;
    MongoCollection mongoCollection;
    TicTacToe ticTacToe;

    @Before
    public void before() {
        collection = mock(TicTacToeCollection.class);
        doReturn(true)
                .when(collection)
                .saveMove(any(TicTacToeBean.class));
        ticTacToe = new TicTacToe(collection);
        bean = new TicTacToeBean(3, 2, 1, 'Y');
        mongoCollection = mock(MongoCollection.class);
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

    @Test
    public void whenSaveMoveThenReturnTrue() {
        TicTacToeBean bean = new TicTacToeBean(3, 2, 1, 'Y');
        MongoCollection mongoCollection = mock(MongoCollection.class);
        doReturn(mongoCollection).when(collection)
                .getMongoCollection();
        Assert.assertTrue(collection.saveMove(bean));
    }

    @Test
    public void givenExceptionWhenSaveMoveThenReturnFalse() {
        doThrow(new MongoException("Bla"))
                .when(mongoCollection)
                .save(any(TicTacToeBean.class));
        doReturn(mongoCollection).when(collection)
                .getMongoCollection();
        Assert.assertFalse(collection.saveMove(bean));
    }

    @Test
    public void whenDropThenInvokeMongoCollectionDrop() {
        doReturn(mongoCollection).when(collection)
                .getMongoCollection();
        collection.drop();
        verify(mongoCollection).drop();
    }

    @Test
    public void whenDropThenReturnTrue() {
        doReturn(mongoCollection).when(collection)
                .getMongoCollection();
        Assert.assertTrue(collection.drop());
    }

    @Test
    public void givenExceptionWhenDropThenReturnFalse() {
        doThrow(new MongoException("Bla"))
                .when(mongoCollection)
                .drop();
        doReturn(mongoCollection).when(collection)
                .getMongoCollection();
        Assert.assertFalse(collection.drop());
    }


    @Test
    public void whenInstantiateThenSetCollection() {
        Assert.assertNotNull(ticTacToe.getTicTacToeCollection());
    }

    @Test
    public void whenPlayThenSaveMoveIsInvoked() {
        TicTacToeBean move = new TicTacToeBean(1, 1, 3, 'X');
        ticTacToe.play(move.getX(), move.getY());
        verify(collection).saveMove(move);
    }

    @Test
    public void whenPlayAndSaveReturnsFalseThenThrowException() {
        doReturn(false).when(collection)
                .saveMove(any(TicTacToeBean.class));
        TicTacToeBean move = new TicTacToeBean(1, 1, 3, 'X');
        exception.expect(RuntimeException.class);
        ticTacToe.play(move.getX(), move.getY());
    }

    @Test
    public void whenPlayInvokedMultipleTimesThenTurnIncreases() {
        TicTacToeBean move1 = new TicTacToeBean(1, 1, 1, 'X');
        ticTacToe.play(move1.getX(), move1.getY());
        verify(collection, times(1)).saveMove(move1);

        TicTacToeBean move2 = new TicTacToeBean(2, 1, 2, 'O');
        ticTacToe.play(move2.getX(), move2.getY());
        verify(collection, times(1)).saveMove(move2);
    }



}
