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
public class SelectionSortHighScores implements HighScoreList {

    private Player[] players = new Player[100];
    private int count = 0;

    /***
     * A sortBucket algorithm that sorts the collection based on the player high scores in descending order
     * @param array The array you want to sortBucket
     */
    public void sort(Player[] array) {

        //loop trough all elements in the array,
        for (int i = 0; i < count; i++) {

            //initialize the maximum index as the first item in the unsorted part of the array
            int maximumValIndex = i;

            for (int j = i + 1; j < count; j++) {           
                if (array[j].getHighScore() > array[maximumValIndex].getHighScore()) {
                    maximumValIndex = j;
                }
            }
            
            Player minValue = array[maximumValIndex];
            array[maximumValIndex] = array[i];
            array[i] = minValue;
        }

    }

    /***
     * A simple method to double the current max size of the collection
     */
    private void doubleArraySize() {
        players = Arrays.copyOf(players, players.length * 2);

    }

    @Override
    public void add(Player player) {
        //in case the max size has been reached, increase the max size
        if (count >= players.length) doubleArraySize();

        //you can use the item count as new index since it counts from 1 and therefor is always the highest index +1
        players[count] = player;

        count++;

        //make sure to always re-sortBucket the collection after altering the content of the collection
        sort(players);
    }

    @Override
    public List<Player> getHighScores(int numberOfHighScores) {

        List<Player> result = new ArrayList<>();

        //loop an x amount of times based on the number of high scores asked by the user
        for (int i = 0; i < numberOfHighScores; i++) {
            //if the index lies beyond the length of the array return the current result
            if (i > players.length) return result;
            //when the current player is null this means there are no more players in the collection, return the current result
            if (players[i] == null) return result;

            result.add(players[i]);
        }
        return result;
    }

    @Override
    public List<Player> findPlayer(String firstName, String lastName) throws IllegalArgumentException {

        //if both first and last name are empty
        if ((firstName == null || firstName.trim().isEmpty()) && (lastName == null || lastName.trim().isEmpty()))
            throw new IllegalArgumentException("Either a valid first name or a last name has to be supplied");

        List<Player> result = new ArrayList<>();

        //go trough all items in the collection to find matches with the search criteria
        for (int i = 0; i < count; i++) {
            String pFirstname = players[i].getFirstName();
            String pLastname = players[i].getLastName();

            if (pFirstname.startsWith(firstName) || pFirstname == firstName
                    || pLastname.startsWith(lastName) || pLastname == lastName) {
                result.add(players[i]);
            }
        }

        return result;
    }
}