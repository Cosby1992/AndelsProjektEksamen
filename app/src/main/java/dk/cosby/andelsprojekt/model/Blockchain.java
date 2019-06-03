package dk.cosby.andelsprojekt.model;

/**
 * Denne klasse indeholder en blockchain og dens sværhedsgrad.
 * Sværhedsgraden bygges på hvor mange calculationer det kræver at opnå et hash
 * der begynder med sværhedsgradens antal nuller.
 *
 * @version 1.0
 * @author Cosby
 */

import java.util.ArrayList;
import java.util.List;

public class Blockchain {

    private List<Block> blockChain;

    //Constructor
    public Blockchain() {
        blockChain = new ArrayList<Block>();
    }


    //////////////////////////////////// getters and setters /////////////////////////////////////

    public List<Block> getBlockChain() {
        return blockChain;
    }

    public void setBlockChain(List<Block> blockChain) {
        this.blockChain = blockChain;
    }

}
