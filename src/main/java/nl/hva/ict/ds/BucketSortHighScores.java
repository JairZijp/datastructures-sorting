/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.hva.ict.ds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Simon
 */
public class BucketSortHighScores implements HighScoreList {
    
    class Bucket {

        Player[] players = new Player[1];
        int count;

        public void sortElements() {   
            
            Player player;
            
            for (int i = 1; i < this.size(); i++) {     

                player = players[i];

                int index = i - 1;

                while (index >= 0 && players[index].getHighScore() < player.getHighScore()) {
                    players[index+1] = players[index];
                    index--;
                }
   
               // System.out.print(players[i].getHighScore() + " - " );
                
                players[index + 1] = player;
            
            }         
            
        }
        
        public void addPlayerToBucket(Player player) {

            if (players.length <= size() ) {
                increaseArraySize();
            }
            
            players[count] = player;

            count++;

        }
        
        private void increaseArraySize() {
            players = Arrays.copyOf(players, players.length+1);
        }

        public int size(){
            return count;
        }
        
    }
    
    Player[] players = new Player[1];
    int count = 0;
    
    @Override
    public void add(Player player) {
        
        if ( players.length >= size() ) {
            increaseArraySize();
        }

        players[count] = player;

        count++;
        
        //System.out.println("Score: " + player.getHighScore());

        sortElementsInBuckets();
    }

    public void sortElementsInBuckets() {

        Bucket[] buckets = new Bucket[100];

        for (int i = 0; i < count; i++) {
            
            Player currentPlayer = players[i];

            int index = (int) currentPlayer.getHighScore() / 10000;

            if(buckets[index] == null) {
                buckets[index] = new Bucket();
            }

            buckets[index].addPlayerToBucket(currentPlayer);
        }

        for(int i = 0; i < buckets.length; i++) {

            if(buckets[i] != null){
                buckets[i].sortElements();
            }
            
        }
        
        //System.out.println(Arrays.toString(buckets));
        
        int index = 0;
        
        for (int i = buckets.length - 1; i >= 0; i--) {
            
            if(buckets[i] != null){
                Bucket currentBucket = buckets[i];
                
                for(int a = 0; a < buckets[i].size(); a++){
                    players[index++] = currentBucket.players[a];
                }
                
            }
            
        }
        
    }
    
    private int size() {
        return count;
    }

    private void increaseArraySize() {
        players = Arrays.copyOf(players, players.length + 1);
    }
    
    @Override
    public List<Player> getHighScores(int numberOfHighScores) {
        
        List<Player> result = new ArrayList<>();

        for (int i = 0; i < numberOfHighScores && i < size(); i++) {
            if (players[i] == null) return result;

            result.add(players[i]);
        }
        return result;
    }


    @Override
    public List<Player> findPlayer(String firstName, String lastName) {
        List<Player> matchedPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player.getFirstName().equals(firstName)) {
                matchedPlayers.add(player);
            }
        }
        return matchedPlayers;
    }
    
}


