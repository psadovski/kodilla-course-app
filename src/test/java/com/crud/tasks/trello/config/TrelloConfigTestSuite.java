package com.crud.tasks.trello.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TrelloConfigTestSuite {

    @Test
    public void testGetTrelloApiEndpoint() {
        //Given
        TrelloConfig trelloConfig = new TrelloConfig();

        //When
        String actual = trelloConfig.getTrelloApiEndpoint();
        String expected = "https://trello.com/1";

        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetTrelloAppKey() {
        //Given
        TrelloConfig trelloConfig = new TrelloConfig();

        //When
        String actual = trelloConfig.getTrelloAppKey();
        String expected = "5ac9dce27073d35628a6711365344d73";
        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetTrelloToken() {
        //Given
        TrelloConfig trelloConfig = new TrelloConfig();

        //When
        String actual = trelloConfig.getTrelloToken();
        String expected = "3ee8f057474e30c0036ed1257b63ac2195085326a7342d53e2bfeceda71c0e11";

        //Then
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testGetUsername() {
        //Given
        TrelloConfig trelloConfig = new TrelloConfig();

        //When
        String actual = trelloConfig.getUsername();
        String expected = "pawesadowski8";

        //Then
        Assert.assertEquals(expected, actual);
    }
}

