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
    
    public Player remove(){
        if(playerList.size() == 0) return null;
        
        Player removedPlayer =  playerList.get(0);
        playerList.set(0, playerList.get(playerList.size() - 1));
        playerList.remove(playerList.size() - 1);
        
        int currentIndex = 0;
        while(currentIndex < playerList.size()) {
            int leftChildIndex = 2 * currentIndex + 1;
            int rightChildIndex = 2 * currentIndex + 2;
         
            if(leftChildIndex >= playerList.size()) break;
            
            int maxIndex = leftChildIndex;
            
            if(rightChildIndex >= playerList.size()){
                if(playerList.get(maxIndex).getHighScore() < playerList.get(rightChildIndex).getHighScore()){
                    maxIndex = rightChildIndex;
                }
            }
            
            if(playerList.get(currentIndex).getHighScore() < playerList.get(maxIndex).getHighScore()){
                Player tempPlayer = playerList.get(maxIndex);
                playerList.set(maxIndex, playerList.get(currentIndex));
                playerList.set(currentIndex, tempPlayer);
            }
            else{
                break;
            }
            
        }
        return removedPlayer;   
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