package org.stool.tdd.tictactoe;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * 需求1：检查棋子是否放在3x3棋盘的边界内
 *
 * 需求2：需要提供一个途径，用于判断接下来该谁落子
 *
 * 需求3：最先在水平、垂直或对角线上将自己的3个标记连起来的玩家获胜
 */
public class TicTacToeSpec {

    // 指定ExpectedException是一条规则
    @Rule
    public ExpectedException exception =
            ExpectedException.none();

    private TicTacToe ticTacToe;

    @Before
    public final void before() {
        ticTacToe = new TicTacToe();
    }

    @Test
    public void whenXOutsideBoardThenRuntimeException() {
        exception.expect(RuntimeException.class);   //期待后面会出现RuntimeException
        ticTacToe.play(5, 2);
    }

    @Test
    public void whenYOutsideBoardThenRuntimeException() {
        exception.expect(RuntimeException.class);
        ticTacToe.play(2, 5);
    }

    @Test
    public void whenOccupiedThenRuntimeException() {
        ticTacToe.play(2, 1);
        exception.expect(RuntimeException.class);
        ticTacToe.play(2, 1);
    }

    /**
     * 玩家x先下
     */
    @Test
    public void givenFirstTurnWhenNextPlayerThenX() {
        Assert.assertEquals('X', ticTacToe.nextPlayer());
    }


    /**
     * 如果上一次是X下的，接下来将轮到O下
     */
    @Test
    public void givenLastTurnWasXWhenNextPlayerThenO() {
        ticTacToe.play(1, 1);
        Assert.assertEquals('O', ticTacToe.nextPlayer());
    }

    @Test
    public void whenPlayThenNoWinner() {
        String actual = ticTacToe.play(1, 1);
        Assert.assertEquals("No Winner", actual);
    }

    /**
     * 当玩家的棋子占据整条水平线就赢了
     */
    @Test
    public void whenPlayAndWholeHorizontalLineThenWinner() {
        ticTacToe.play(1, 1);   // x
        ticTacToe.play(1, 2);   // o
        ticTacToe.play(2, 1);   // x
        ticTacToe.play(2, 2);   // o
        String actual = ticTacToe.play(3, 1);   // x
        Assert.assertEquals("X is the winner", actual);
    }

    /**
     * 当玩家的棋子占据整条垂直线就赢了
     */
    @Test
    public void whenPlayAndWholeVerticalLineThenWinner() {
        ticTacToe.play(2, 1);   // x
        ticTacToe.play(1, 1);   // o
        ticTacToe.play(3, 1);   // x
        ticTacToe.play(1, 2);   // o
        ticTacToe.play(2, 2);   // x
        String actual = ticTacToe.play(1, 3);   // o
        Assert.assertEquals("O is the winner", actual);
    }

    /**
     * 当玩家的棋子占据左上角到右下角就赢了
     */
    @Test
    public void whenPlayAndTopBottomDiagonalLineThenWinner() {
        ticTacToe.play(1, 1);   // x
        ticTacToe.play(1, 2);   // o
        ticTacToe.play(2, 2);   // x
        ticTacToe.play(1, 3);   // o
        String actual = ticTacToe.play(3, 3);   // x
        Assert.assertEquals("X is the winner", actual);
    }

    /**
     * 当玩家的棋子占据左下角到右上角就赢了
     */
    @Test
    public void whenPlayAndBottomTopDiagonalLineThenWinner() {
        ticTacToe.play(1, 3);   // x
        ticTacToe.play(1, 1);   // o
        ticTacToe.play(2, 2);   // x
        ticTacToe.play(1, 2);   // o
        String actual = ticTacToe.play(3, 1);   // x
        Assert.assertEquals("X is the winner", actual);
    }
}
