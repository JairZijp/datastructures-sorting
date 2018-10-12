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
    Player[] players = new Player[100];
    int count = 0;
    
    class Bucket {

        Player[] players = new Player[100];
        int count;

        public void addPlayer(Player player) {

            if (count >= players.length) {
                increaseArraySize();
            }
            players[count] = player;

            count++;

        }
        
        private void increaseArraySize() {
            players = Arrays.copyOf(players, players.length+1);
        }

        public void sortBucket() {
            
            Player key;
            for (int i = 1; i < this.size(); i++) {     

                key = players[i];

                int index = i - 1;

                while (index >= 0 && players[index].getHighScore() < key.getHighScore()) {
                    players[index+1] = players[index];
                    index--;
                }
   
                //System.out.println(players[i].getFirstName() + " - " + players[i].getHighScore());
                
                players[index + 1] = key;
            
            }      
            
        }

        public int size(){
            return count;
        }
        
    }
    
    @Override
    public void add(Player player) {
        
        if (count >= players.length) {
            increaseArraySize();
        }

        players[count] = player;

        count++;
        
        //System.out.println("Score: " + player.getHighScore());

        bucketSort();
    }

    public void bucketSort() {

        Bucket[] buckets = new Bucket[100];

        for (int i = 0; i < count; i++) {
            Player currentPlayer = players[i];

            int index = (int) currentPlayer.getHighScore() / 10000; 

            if(buckets[index] == null) {
                buckets[index] = new Bucket();
            }

            buckets[index].addPlayer(currentPlayer);
        }

        for(int i = 0; i < buckets.length; i++) {

            if(buckets[i] != null){
                buckets[i].sortBucket();
            }
            
        }
        
        //System.out.println(Arrays.toString(buckets));
        
        int index = 0;
        for(int i = 0; i < buckets.length; i++) {
            
            if(buckets[i] != null){
                Bucket currentBucket = buckets[i];
                
                for(int j = 0; j < buckets[i].size(); j++){
                    players[index++] = currentBucket.players[j];
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


