package org.stool.tdd.tictactoe;

public class TicTacToe {

    private Character[][] board = {
            {'\0', '\0', '\0'},
            {'\0', '\0', '\0'},
            {'\0', '\0', '\0'}
    };

    private char lastPlayer = '\0';

    public String play(int x, int y) {
        checkAxis(x);
        checkAxis(y);
        lastPlayer = nextPlayer();
        setBox(x, y);

        if (isWin()) {
            return lastPlayer + " is the winner";
        }

        return "No Winner";
    }

    private boolean isWin() {
        for (int index = 0; index < 3; index++) {
            if (board[0][index] == lastPlayer &&
                    board[1][index] == lastPlayer &&
                    board[2][index] == lastPlayer) {
                return true;
            }
            if (board[index][0] == lastPlayer &&
                    board[index][1] == lastPlayer &&
                    board[index][2] == lastPlayer) {
                return true;
            }
        }

        if (board[0][0] == lastPlayer &&
                board[1][1] == lastPlayer &&
                board[2][2] == lastPlayer) {
            return true;
        }

        if (board[0][2] == lastPlayer &&
                board[1][1] == lastPlayer &&
                board[2][0] == lastPlayer) {
            return true;
        }

        return false;
    }

    private void checkAxis(int x) {
        if (x < 1 || x > 3) {
            throw new RuntimeException("X is outside board");
        }
    }

    private void setBox(int x, int y) {
        if (board[x - 1][y - 1] != '\0') {
            throw new RuntimeException("Box is occupied");
        } else {
            board[x - 1][y - 1] = lastPlayer;
        }
    }

    public char nextPlayer() {
        if (lastPlayer == 'X') {
            return 'O';
        }

        return 'X';
    }
}
