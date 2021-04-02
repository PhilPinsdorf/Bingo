package de.rexituz.bingo.teams;

import java.util.ArrayList;

public class TeamsDelivery {
    ArrayList<Boolean> items;

    public  TeamsDelivery(boolean[] itemValues)
    {
        if(itemValues.length != 9)
            throw new IllegalArgumentException("Length of items values should be 9");

        this.items = new ArrayList<Boolean>(9);
        for(int i=0; i<9; i++) {
            this.items.add(i, itemValues[i]);
        }
    }

    public ArrayList<Boolean> getAllDeliverys(){
        return items;
    }
}