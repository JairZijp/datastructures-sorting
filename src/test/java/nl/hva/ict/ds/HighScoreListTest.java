package nl.hva.ict.ds;

import org.junit.Before;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.List;
import java.util.Random;
import nl.hva.ict.ds.InsertionSortHighScores;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * This class contains some unit tests. They by no means ensure that all the requirements are implemented
 * correctly.
 */
public class HighScoreListTest {
    private static final int MAX_HIGH_SCORE = 100000;
    private Random randomizer = new SecureRandom();
    private HighScoreList highScores;
    private Player nearlyHeadlessNick;
    private Player dumbledore;

    @Before
    public void setup() {
        // Here you should select your implementation to be tested.
//        highScores = new DummyHighScores();
       highScores = new BucketSortHighScores();
      //  highScores = new SelectionSortHighScores();
//        highScores = new PriorityQueueHighScores();

        nearlyHeadlessNick = new Player("Nicholas", "de Mimsy-Porpington", getHighScore() % 200);
        dumbledore = new Player("Albus", "Dumbledore", nearlyHeadlessNick.getHighScore() * 1000);
    }

    @Test
    public void noPlayerNoHighScore() {
        assertTrue("There are high-score while there should be no high-scores!", highScores.getHighScores(1).isEmpty());
    }

    @Test
    public void whenNoHighScoreIsAskedForNonShouldBeGiven() {
        highScores.add(dumbledore);

        assertEquals(0, highScores.getHighScores(0).size());
    }

    @Test
    public void noMoreHighScoresCanBeGivenThenPresent() {
        highScores.add(nearlyHeadlessNick);
        highScores.add(dumbledore);

        assertEquals(2, highScores.getHighScores(10).size());
    }

    @Test
    public void keepAllHighScores() {
        highScores.add(nearlyHeadlessNick);
        highScores.add(dumbledore);

        assertEquals(2, highScores.getHighScores(2).size());
    }

    @Test
    public void singlePlayerHasHighScore() {
        highScores.add(dumbledore);

        assertEquals(dumbledore, highScores.getHighScores(1).get(0));
    }

    @Test
    public void harryBeatsDumbledore() {
        highScores.add(dumbledore);
        Player harry = new Player("Harry", "Potter", dumbledore.getHighScore() + 1);
        highScores.add(harry);
        
        System.out.print("harryBeatsDumbledore: \n");
        System.out.print(dumbledore.getHighScore() + " || " + harry.getHighScore());

        assertEquals(harry, highScores.getHighScores(1).get(0));
    }

    // Extra unit tests go here

    private long getHighScore() {
        return randomizer.nextInt(MAX_HIGH_SCORE);
    }
    
    @Test
    public void manyDifferentHighScores() {        
        
        for(int i=0; i < 1; i++) {
            
            Player person = new Player("Person", "- " + i, getHighScore());
            highScores.add(person);                
            //System.out.print(person.getHighScore());
         
        }
   }
    
    @Test
    public void highScoresAreSorted(){
        int totalPlayers = 10;
        
        for(int i = 0; i < totalPlayers; i++){
            Player person = new Player("Person", "-" + i, getHighScore());
            highScores.add(person);
        }
        
        boolean lessThanPrevious = false;
        List<Player> sortedScoreBoard = highScores.getHighScores(10);
        
        System.out.print("\n highScoresAreSorted: \n");
        
        for (int a = 1; a < totalPlayers; a++){
            long thiselementhighscore = sortedScoreBoard.get(a).getHighScore();
            long lastelementhighscore = sortedScoreBoard.get(a - 1).getHighScore();
            lessThanPrevious = ( thiselementhighscore < lastelementhighscore ) ;
            
            System.out.print(sortedScoreBoard.get(a).getHighScore() + " - ");
            
            if(!lessThanPrevious) break;
        }
        
        
        
        assertTrue("The scores are not sorted correctly!", lessThanPrevious);
        
    }
    
}