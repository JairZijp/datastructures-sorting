/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hva.ict.ds;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Simon
 */
public class PriorityQueueHighScores implements HighScoreList{
    private ArrayList<Player> playerList = new ArrayList<>();

    public PriorityQueueHighScores(){}
    
    public PriorityQueueHighScores(Player[] players){
        for (Player player : players) {
            add(player);
        }
    }
    
    @Override
    public void add(Player newPlayer){
        playerList.add(newPlayer);
        
        // when the first player is added, currentindex will be 0
        int currentIndex = playerList.size() - 1;
  
        // the while loop will only be entered when there are 2 elements or more
        while( currentIndex > 0 ){
            int parentIndex = ( currentIndex - 1 ) / 2;
            long currentscore = playerList.get(currentIndex).getHighScore();
            long parentscore = playerList.get(parentIndex).getHighScore();
            boolean aaa = currentscore > parentscore;
            if(playerList.get(currentIndex).getHighScore() > playerList.get(parentIndex).getHighScore()){
                Player temp = playerList.get(currentIndex);
                playerList.set(currentIndex, playerList.get(parentIndex));
                playerList.set(parentIndex, temp);
            }  
            else{
                break;
            }
            currentIndex = parentIndex;
        }
    }
    
    @Override
    public List<Player> getHighScores(int numberOfHighScores) {
        
        List<Player> result = new ArrayList<>();

        for (int i = 0; i < numberOfHighScores && i < playerList.size(); i++) {
            if (playerList.get(i) == null) return result;

            result.add(playerList.get(i));
        }
        return result;
    }

    @Override
    public List<Player> findPlayer(String firstName, String lastName) throws IllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}