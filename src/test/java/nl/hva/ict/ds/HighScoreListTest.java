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
        // make highscores selectionsort
        highScores = new SelectionSortHighScores();
        
        // tests if the scores are sorted correcty
        boolean validResults = scoresAreSortedCorrectly();
        assertTrue("The scores are not sorted on the selection-sort way!", validResults);
    }
    
    @Test
    public void bucketSortScoresAreSorted(){
        // ake highscores bucketsort
        highScores = new BucketSortHighScores();
        
        // tests if the scores are sorted correctly
        boolean validResults = scoresAreSortedCorrectly();
        assertTrue("The cores are not sorted on the bucket-sort way!", validResults);
    }
       
    @Test
    public void priorityQueueScoresAreSorted(){
        // make highscores priorityqueue 
        highScores = new PriorityQueueHighScores();
        
        int playersToTest = 10;
        // the function returns a lit of players of size == playersToTest
        List<Player> sortedScoreBoard = getTestHighScoreList(playersToTest);

        // boolean to determine if the list is correctly sorted
        boolean sortedCorrectly = false;
        // loopp through players to test, the last element tested will be of index 1
        while(playersToTest > 2){
            // define the index of current element and the parent of the element
            int currentIndex = playersToTest - 1;
            int parentIndex = (currentIndex - 1 ) / 2;
            
            // get the highscores by index defined above, and check if the parent's score is higher
            long currentElementScore = sortedScoreBoard.get(currentIndex).getHighScore();
            long lastElementScore = sortedScoreBoard.get(parentIndex).getHighScore();
            sortedCorrectly = (currentElementScore < lastElementScore);
            
            playersToTest--;
            
            // when it is not sorted correctly, stop the loop
            if(!sortedCorrectly) break;
        }
        
        assertTrue("The scores are not sorted on the priority-queue way!", sortedCorrectly);
    }
    
    private boolean scoresAreSortedCorrectly(){
        // test 10 players
        int playersToTest = 10;        
        // get highscores of 10 players and store them in sortedscoreboard
        List<Player> sortedScoreBoard = getTestHighScoreList(playersToTest);
        
        // boolean to determine if the list is sorted incorrectly
        boolean lessThanPrevious = false;
        for (int a = 1; a < playersToTest; a++){
            // loop through all players and get the current score and the parent score
            long currentElementScore = sortedScoreBoard.get(a).getHighScore();
            long lastElementScore = sortedScoreBoard.get(a - 1).getHighScore();
            // is false when the score of the previous element iss lower than the current score
            lessThanPrevious = ( currentElementScore < lastElementScore ) ;
            
            // loop breaks if list is not sorted correctly
            if(!lessThanPrevious) break;
        }
        
        // return the result of the loop
        return lessThanPrevious;        
    }
    
    private List<Player> getTestHighScoreList(int size){
        // iterate a certain amount of times
        for(int i = 0; i < size; i++){
            
            // for eah iteration, add a new player to the scoreboard
            Player person = new Player("Person", "-" + i, getHighScore());
            highScores.add(person);
        }
        
        // return the list of players with the given size
        return highScores.getHighScores(size);
    }
}