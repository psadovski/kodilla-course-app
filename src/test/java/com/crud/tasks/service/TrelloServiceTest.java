package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloListDto;
import com.crud.tasks.domain.card.TrelloCardAttachmentsByType;
import com.crud.tasks.domain.card.TrelloCardBadges;
import com.crud.tasks.domain.card.TrelloCardTrello;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTest {

    @InjectMocks
    private TrelloService trelloService;

    @Mock
    private TrelloClient trelloClient;

    @Mock
    private SimpleEmailService simpleEmailService;

    @Mock
    private AdminConfig adminConfig;

    @Test
    public void fetchTrelloBoardsWhenBoardsEmpty() {
        //Given
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtos);

        //When
        List<TrelloBoardDto> fetchedTrelloBoard = trelloService.fetchTrelloBoards();

        //Then
        assertNotNull(fetchedTrelloBoard);
        assertEquals(0, fetchedTrelloBoard.size());

    }

    @Test
    public void fetchTrelloBoards() {
        //Given
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(new TrelloListDto("1", "Test list", true));

        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(new TrelloBoardDto("1", "Test board", trelloListDtos));

        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtos);

        //When
        List<TrelloBoardDto> fetchedTrelloBoard = trelloService.fetchTrelloBoards();

        //Then
        assertNotNull(trelloBoardDtos);
        assertEquals(1, trelloBoardDtos.size());

        fetchedTrelloBoard.forEach(trelloBoardDto -> {
            assertEquals("1", trelloBoardDto.getId());
            assertEquals("Test board", trelloBoardDto.getName());

            trelloBoardDto.getLists().forEach(trelloListDto -> {
                assertEquals("1", trelloListDto.getId());
                assertEquals("Test list", trelloListDto.getName());
                assertEquals(true, trelloListDto.isClosed());
            });
        });
    }

    @Test
    public void testCreatedTrelloCard() {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("Card 1", "Content 1", "1", "1");
        TrelloCardTrello cardTrello = new TrelloCardTrello(1, 1);
        TrelloCardAttachmentsByType attachmentsByType = new TrelloCardAttachmentsByType(cardTrello);
        TrelloCardBadges cardBadges = new TrelloCardBadges(1, attachmentsByType);
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "1",
                "Card name",
                "http://test.com",
                cardBadges);

        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);

        //When
        CreatedTrelloCardDto card = trelloService.createdTrelloCard(trelloCardDto);

        //Then
        assertThat(createdTrelloCardDto).isEqualToComparingFieldByField(card);
        assertEquals(1,card.getBadges().getAttachmentsByType().getTrello().getCard());
        assertEquals(1,card.getBadges().getAttachmentsByType().getTrello().getBoard());
        assertEquals(1,card.getBadges().getVotes());
    }
}
