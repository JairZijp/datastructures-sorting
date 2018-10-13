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
//        highScores = new BucketSortHighScores();
//        highScores = new SelectionSortHighScores();
        highScores = new PriorityQueueHighScores();

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
        
        //System.out.print(dumbledore.getHighScore() + " || " + harry.getHighScore());

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
    public void selectionSortScoresAreSorted(){
        highScores = new SelectionSortHighScores();
        
        boolean validResults = scoresAreSortedCorrectly();
        assertTrue("The scores are not sorted on the selection-sort way!", validResults);
    }
    
    @Test
    public void bucketSortScoresAreSorted(){
        highScores = new BucketSortHighScores();
        
        boolean validResults = scoresAreSortedCorrectly();
        assertTrue("The cores are not sorted on the bucket-sort way!", validResults);
    }
       
    @Test
    public void priorityQueueScoresAreSorted(){
        highScores = new PriorityQueueHighScores();
        
        int playersToTest = 10;
        List<Player> sortedScoreBoard = getTestHighScoreList(playersToTest);

        boolean sortedCorrectly = false;
        while(playersToTest > 2){
            int currentIndex = playersToTest - 1;
            int parentIndex = (currentIndex - 1 ) / 2;
            
            long currentElementScore = sortedScoreBoard.get(currentIndex).getHighScore();
            long lastElementScore = sortedScoreBoard.get(parentIndex).getHighScore();
            sortedCorrectly = (currentElementScore < lastElementScore);
            
            playersToTest--;
            
            if(!sortedCorrectly) break;
        }
        
        assertTrue("The scores are not sorted on the priority-queue way!", sortedCorrectly);
    }
    
    private boolean scoresAreSortedCorrectly(){
        highScores = new SelectionSortHighScores();
        
        int playersToTest = 10;        
        List<Player> sortedScoreBoard = getTestHighScoreList(playersToTest);
        
        boolean lessThanPrevious = false;
        for (int a = 1; a < playersToTest; a++){
            long currentElementScore = sortedScoreBoard.get(a).getHighScore();
            long lastElementScore = sortedScoreBoard.get(a - 1).getHighScore();
            lessThanPrevious = ( currentElementScore < lastElementScore ) ;
            
            if(!lessThanPrevious) break;
        }
        
        return lessThanPrevious;        
    }
    
    private List<Player> getTestHighScoreList(int size){
        for(int i = 0; i < size; i++){
            Player person = new Player("Person", "-" + i, getHighScore());
            highScores.add(person);
        }
        
        return highScores.getHighScores(10);
    }
}