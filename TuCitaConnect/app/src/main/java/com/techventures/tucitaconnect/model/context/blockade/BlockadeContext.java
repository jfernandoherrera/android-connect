package com.techventures.tucitaconnect.model.context.blockade;

import com.techventures.tucitaconnect.model.domain.blockade.Blockade;
import com.techventures.tucitaconnect.model.domain.venue.Venue;

public class BlockadeContext {

    public static BlockadeContext context(BlockadeContext blockadeContext) {

        if (blockadeContext == null) {

            blockadeContext = new BlockadeContext();

        }

        return blockadeContext;

    }

    public BlockadeContext() {

        blockadeRemote = new BlockadeRemote();

    }

    private BlockadeRemote blockadeRemote;

    public void getBlockadeFromVenue(Venue venue, BlockadeCompletion.ErrorCompletion completion){

      blockadeRemote.getBlockadeFromVenue(venue, completion);

    }

    public void saveBlockade(Blockade blockade, Venue venue, BlockadeCompletion.ErrorCompletion completion) {

        blockadeRemote.saveBlockade(blockade, venue, completion);

    }
}
