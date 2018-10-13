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
 * @author jairz
 */
public class SelectionSortHighScores implements HighScoreList {

    private Player[] players = new Player[100];
    private int count = 0;
    
    @Override
    public void add(Player player) {

        if (count >= players.length) {
            increaseArraySize();
        }
       
        players[count] = player;

        count++;
        
        sort(players);
        
    }


    public void sort(Player[] players) {
        
        int lowestElement;

        for (int i = 0; i < players.length - 1; i++) {

            lowestElement = i;

            for (int a = i + 1; a < count; a++) {           
         
                if (players[a].getHighScore() > players[lowestElement].getHighScore()) {
                    lowestElement = a;
                }
                
            }
            
            Player temp = players[lowestElement];   
            
            players[lowestElement] = players[i];
            
            players[i] = temp;
            
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
        
        for (int i = 0; i < numberOfHighScores; i++) {
            if (i > players.length) return result;
                       
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