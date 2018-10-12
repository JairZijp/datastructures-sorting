/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hva.ict.ds;

import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.PriorityQueue;

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
        
        int currentIndex = playerList.size() - 1;
        
        while( currentIndex > 0 ){
            int parentIndex = ( currentIndex - 1 ) / 2;
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