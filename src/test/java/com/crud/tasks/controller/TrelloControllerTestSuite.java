package com.crud.tasks.controller;

import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.domain.card.TrelloCardAttachmentsByType;
import com.crud.tasks.domain.card.TrelloCardBadges;
import com.crud.tasks.domain.card.TrelloCardTrello;
import com.crud.tasks.trello.facade.TrelloFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloControllerTestSuite {

    @InjectMocks
    private TrelloController trelloController;

    @Mock
    private TrelloFacade trelloFacade;

    @Test
    public void shouldFetchEmptyTrelloBoards() {
        //Given
        List<TrelloBoardDto> boards = new ArrayList<>();

        //When
        when(trelloFacade.fetchTrelloBoards()).thenReturn(boards);
        List<TrelloBoardDto> trelloBoards = trelloController.getTrelloBoards();

        //Then
        assertNotNull(trelloBoards);
        assertEquals(0, trelloBoards.size());
    }

    @Test
    public void shouldFetchTrelloBoards() {
        //Given
        List<TrelloListDto> lists = new ArrayList<>();
        lists.add(new TrelloListDto("1", "Test List", false));

        List<TrelloBoardDto> boards = new ArrayList<>();
        boards.add(new TrelloBoardDto("1", "Test Task", lists));

        //When
        when(trelloFacade.fetchTrelloBoards()).thenReturn(boards);
        List<TrelloBoardDto> trelloBoards = trelloController.getTrelloBoards();

        //Then
        assertNotNull(trelloBoards);
        assertEquals(1, trelloBoards.size());
    }

    @Test
    public void  testCreatedTrelloCard() {
        //Given
        TrelloCardDto card = new TrelloCardDto(
                "Test","Test Description", "top", "1");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "Test",
                "http://test.com",
                new TrelloCardBadges(1,
                        new TrelloCardAttachmentsByType(
                                new TrelloCardTrello(1, 2))));

        //When
        when(trelloFacade.createCard(card)).thenReturn(createdTrelloCardDto);
        CreatedTrelloCardDto trelloCard = trelloController.createdTrelloCard(card);

        //Then
        assertNotNull(trelloCard);
        assertEquals("1", trelloCard.getId());
        assertEquals("Test", trelloCard.getName());
        assertEquals("http://test.com", trelloCard.getShortUrl());
    }
}
