/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import org.junit.Test;

import java.util.ArrayList;

import static loa.Square.BOARD_SIZE;
import static org.junit.Assert.*;

import static loa.Piece.*;
import static loa.Square.sq;
import static loa.Move.mv;

/** Tests of the Board class API.
 *  @author
 */
public class BoardTest {

    /** A "general" position. */
    static final Piece[][] BOARD1 = {
        { EMP, BP,  EMP,  BP,  BP, EMP, EMP, EMP },
        { WP,  EMP, EMP, EMP, EMP, EMP, EMP, WP  },
        { WP,  EMP, EMP, EMP,  BP,  BP, EMP, WP  },
        { WP,  EMP,  BP, EMP, EMP,  WP, EMP, EMP  },
        { WP,  EMP,  WP,  WP, EMP,  WP, EMP, EMP  },
        { WP,  EMP, EMP, EMP,  BP, EMP, EMP, WP  },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP  },
        { EMP, BP,  BP,  BP,  EMP,  BP,  BP, EMP }
    };

    /** A position in which black, but not white, pieces are contiguous. */
    static final Piece[][] BOARD2 = {
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP,  BP,  WP,  BP,  BP,  BP, EMP, EMP },
        { EMP,  WP,  BP,  WP,  WP, EMP, EMP, EMP },
        { EMP, EMP,  BP,  BP,  WP,  WP, EMP,  WP },
        { EMP,  WP,  WP,  BP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP,  BP, EMP, EMP, EMP, EMP },
    };

    /** A position in which black, but not white, pieces are contiguous. */
    static final Piece[][] BOARD3 = {
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
        { EMP,  BP,  WP,  BP,  WP, EMP, EMP, EMP },
        { EMP,  WP,  BP,  WP,  WP, EMP, EMP, EMP },
        { EMP, EMP,  BP,  BP,  WP,  WP,  WP, EMP },
        { EMP,  WP,  WP,  WP, EMP, EMP, EMP, EMP },
        { EMP, EMP, EMP, EMP, EMP, EMP, EMP, EMP },
    };


    static final String BOARD1_STRING =
        "===\n"
        + "    - b b b - b b - \n"
        + "    - - - - - - - - \n"
        + "    w - - - b - - w \n"
        + "    w - w w - w - - \n"
        + "    w - b - - w - - \n"
        + "    w - - - b b - w \n"
        + "    w - - - - - - w \n"
        + "    - b - b b - - - \n"
        + "Next move: black\n"
        + "===";

    /** Board init. */
    @Test
    public void coordTest() {
        Board test = new Board(Board.INITIAL_PIECES2, BP);
        int i = (2 << 3) + 1;
        int y = i >> 3;
        int x = i - y * 8;
        int out = (y << 3) + x;
        assertEquals(2, y);
        assertEquals(1, x);
        assertEquals(17, out);

    }

    /** Test display (which also checks to see if initializer is working
     * properly). */
    @Test
    public void toStringTest() {
        Board test = new Board(BOARD1, BP);
        Board initial = new Board(Board.INITIAL_PIECES, WP);
        assertEquals(BOARD1_STRING, test.toString().replaceAll("\\r\\n", "\n"));
    }

    /** Test to see if counting actions works. */
    @Test
    public void testActions() {
        Board test = new Board(BOARD1, BP);
        System.out.println(test);
        assertEquals(5, test.actions(sq(0, 0), 0));
        assertEquals(3, test.actions(sq(7, 7), 4));
        assertEquals(4, test.actions(sq(5, 2), 0));
        assertEquals(2, test.actions(sq(0, 1), 2));
        assertEquals(4, test.actions(sq(0, 4), 2));
        assertEquals(5, test.actions(sq(7, 7), 6));
        assertEquals(3, test.actions(sq(5, 0), 6));


    }

    /** Test to see if piece is blocked */
    @Test
    public void testBlocked() {
        Board test = new Board(BOARD1, BP);
        assertEquals(0, test.blocked(sq(0, 1), 0));
        assertEquals(2, test.blocked(sq(0, 3), 2));
        assertEquals(1, test.blocked(sq(5, 2), 0));
        assertEquals(4, test.blocked(sq(1, 0), 1));
        assertEquals(1, test.blocked(sq(5, 3), 5));
    }


    /** Test legal moves. */
    @Test
    public void testLegality1() {
        Board b = new Board(BOARD1, BP);
        assertTrue("f3-d5", b.isLegal(mv("f3-d5")));
        assertTrue("f3-h5", b.isLegal(mv("f3-h5")));
        assertTrue("f3-h1", b.isLegal(mv("f3-h1")));
        assertTrue("f3-b3", b.isLegal(mv("f3-b3")));
        assertFalse("f3-d1", b.isLegal(mv("f3-d1")));
        assertFalse("f3-h3", b.isLegal(mv("f3-h3")));
        assertFalse("f3-e4", b.isLegal(mv("f3-e4")));
        assertFalse("c4-c7", b.isLegal(mv("c4-c7")));
        assertFalse("b1-b4", b.isLegal(mv("b1-b4")));
    }

    /** Test numContig. */
    @Test
    public void testNumContig() {
        Board test = new Board(Board.INITIAL_PIECES, BP);
        boolean[][] v = new boolean[BOARD_SIZE][BOARD_SIZE];
        Square boo = sq(7, 7);
        int x = test.numContig(boo, v, WP, 0);
        assertEquals(6, x);
        Board test2 = new Board(BOARD1, BP);
        System.out.println(test2);
        boolean[][] z = new boolean[8][8];
        assertEquals(2, test2.numContig(sq(2, 4), z, WP, 0));
        assertEquals(3, test2.numContig(sq(2, 7), z, BP, 0));
    }

    /** Test makeMove. */
    @Test
    public void testMakeMove() {
        Board test = new Board(Board.INITIAL_PIECES, BP);
        Square from = sq('b', 1);
        Square to = sq('d', 3);
        System.out.println(test);
        Move moveTest = new Move(from, to, false);
        test.makeMove(moveTest);
        System.out.println(test);
        assertEquals(BP, to.contains());
    }

    /** Test contiguity. */
    @Test
    public void testContiguous1() {
        Board b1 = new Board(BOARD1, BP);
        assertFalse("Board 1 black contiguous?", b1.piecesContiguous(BP));
        assertFalse("Board 1 white contiguous?", b1.piecesContiguous(WP));
        assertFalse("Board 1 game over?", b1.gameOver());
        Board b2 = new Board(BOARD2, BP);
        assertTrue("Board 2 black contiguous?", b2.piecesContiguous(BP));
        assertFalse("Board 2 white contiguous?", b2.piecesContiguous(WP));
        assertTrue("Board 2 game over", b2.gameOver());
        Board b3 = new Board(BOARD3, BP);
        assertTrue("Board 3 white contiguous?", b3.piecesContiguous(WP));
        assertTrue("Board 3 black contiguous?", b3.piecesContiguous(BP));
        assertTrue("Board 3 game over", b3.gameOver());
    }

    @Test
    public void testEquals1() {
        Board b1 = new Board(BOARD1, BP);
        Board b2 = new Board(BOARD1, BP);

        assertEquals("Board 1 equals Board 1", b1, b2);
    }

    @Test
    public void testMove1() {
        Board b0 = new Board(BOARD1, BP);
        Board b1 = new Board(BOARD1, BP);
        b1.makeMove(mv("f3-d5"));
        assertEquals("square d5 after f3-d5", BP, b1.get(sq(3, 4)));
        assertEquals("square f3 after f3-d5", EMP, b1.get(sq(5, 2)));
        assertEquals("Check move count for board 1 after one move",
                     1, b1.movesMade());
        b1.retract();
        assertEquals("Check for board 1 restored after retraction", b0, b1);
        assertEquals("Check move count for board 1 after move + retraction",
                     0, b1.movesMade());
    }
    @Test
    public void testMoveAndActions() {
        Board test = new Board(Board.INITIAL_PIECES, BP);
        test.makeMove(mv("b1-d3"));
        test.makeMove(mv("a3-d3"));
        System.out.println(test);
        Square boo = sq('f', 1);
        int x = test.actions(boo, 6);
        System.out.println(x);
        System.out.println(test);
    }

    @Test
    public void bootlegIllegal() {
        Board test = new Board(Board.INITIAL_PIECES, BP);
        test.makeMove(mv("c1-e3"));
        test.makeMove(mv("a5-c5"));
        test.makeMove(mv("f8-c5"));
        test.makeMove(mv("h3-f1"));
        test.makeMove(mv("c8-d7"));
        System.out.println(test);
        System.out.println(test);
    }

    @Test
    public void legalMovesTest() {
        Board test = new Board(Board.INITIAL_PIECES, BP);
        ArrayList<Move> movelist = test.legalMoves();
        for (Move m : movelist) {
            System.out.println(m);
        }
        System.out.println(movelist.get(0));
    }

    @Test
    public void inBoundsTest() {
        Board test = new Board(Board.INITIAL_PIECES, BP);
        boolean x = test.inbounds(8, 0);
        System.out.println(x);
    }
}
