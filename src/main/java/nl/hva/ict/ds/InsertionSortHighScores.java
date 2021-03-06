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
public class InsertionSortHighScores implements HighScoreList {
    private List<Player> players = new ArrayList<>();

    @Override
    public void add(Player player) {
        players.add(player);
    }

    @Override
    public List<Player> getHighScores(int numberOfHighScores) {
        return players.subList(0, Math.min(numberOfHighScores, players.size()));
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
