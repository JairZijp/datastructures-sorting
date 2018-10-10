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

    private Player[] playerCollection = new Player[100];
    private int itemCount = 0;

    /***
     * A sortBucket algorithm that sorts the collection based on the player high scores in descending order
     * @param array The array you want to sortBucket
     */
    public void sort(Player[] array) {

        //loop trough all elements in the array,
        for (int i = 0; i < itemCount; i++) {

            //initialize the maximum index as the first item in the unsorted part of the array
            int maximumValIndex = i;

            //loop trough all the elements in the unsorted part of the array (every index above i)
            //to check if there is a higher value
            for (int j = i + 1; j < itemCount; j++) {
                //If the value of index j is higher than the value of the current maximum,
                // j becomes the index that holds the current max found value
                if (array[j].getHighScore() > array[maximumValIndex].getHighScore()) {
                    maximumValIndex = j;
                }
            }

            //now swap the player with the maximum high score found with the value of the current i index
            Player minValue = array[maximumValIndex];
            array[maximumValIndex] = array[i];
            array[i] = minValue;

            //now i will be increased in the next round of the loop, this means all indexes before the next i value
            //wil be left untouched for the remainder of the algorithm. This kinda creates a situation where you
            //end up with 2 separate sub-arrays within a single array, everything before i(the sorted array) and everything
            //after and including i(the part of the array that still has to be sorted).
        }

    }

    /***
     * A simple method to double the current max size of the collection
     */
    private void doubleArraySize() {
        playerCollection = Arrays.copyOf(playerCollection, playerCollection.length * 2);

    }

    @Override
    public void add(Player player) {
        //in case the max size has been reached, increase the max size
        if (itemCount >= playerCollection.length) doubleArraySize();

        //you can use the item count as new index since it counts from 1 and therefor is always the highest index +1
        playerCollection[itemCount] = player;

        itemCount++;

        //make sure to always re-sortBucket the collection after altering the content of the collection
        sort(playerCollection);
    }

    @Override
    public List<Player> getHighScores(int numberOfHighScores) {

        List<Player> result = new ArrayList<>();

        //loop an x amount of times based on the number of high scores asked by the user
        for (int i = 0; i < numberOfHighScores; i++) {
            //if the index lies beyond the length of the array return the current result
            if (i > playerCollection.length) return result;
            //when the current player is null this means there are no more players in the collection, return the current result
            if (playerCollection[i] == null) return result;

            result.add(playerCollection[i]);
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
        for (int i = 0; i < itemCount; i++) {
            String pFirstname = playerCollection[i].getFirstName();
            String pLastname = playerCollection[i].getLastName();

            if (pFirstname.startsWith(firstName) || pFirstname == firstName
                    || pLastname.startsWith(lastName) || pLastname == lastName) {
                result.add(playerCollection[i]);
            }
        }

        return result;
    }
}