package com.crud.tasks.mapper;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.domain.TrelloList;
import com.crud.tasks.domain.TrelloListDto;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTestSuite {

    @InjectMocks
    private TrelloMapper trelloMapper;

    @Test
    public void testMapToBoard() {
        //Given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1", "List 1", true));

        List<TrelloBoardDto> trelloBoardDto = new ArrayList<>();
        trelloBoardDto.add(new TrelloBoardDto("1", "Board 1", trelloListDto));

        //When
        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoard(trelloBoardDto);

        //Then
        assertEquals(1, trelloBoards.size());
        assertEquals("1", trelloBoards.get(0).getId());
        assertEquals("Board 1", trelloBoards.get(0).getName());
        assertEquals("List 1", trelloBoards.get(0).getLists().get(0).getName());
    }

    @Test
    public void testMapToBoardDto() {
        //Given
        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(new TrelloList("1", "List 1", true));

        List<TrelloBoard> trelloBoard = new ArrayList<>();
        trelloBoard.add(new TrelloBoard("1", "Board 1", trelloList));

        //When
        List<TrelloBoardDto> trelloBoardDto = trelloMapper.mapToBoardDto(trelloBoard);

        //Then
        assertEquals(1, trelloBoardDto.size());
        assertEquals("1", trelloBoardDto.get(0).getId());
        assertEquals("Board 1", trelloBoardDto.get(0).getName());
        assertEquals("List 1", trelloBoardDto.get(0).getLists().get(0).getName());
    }

    @Test
    public void testMapToList() {
        //Given
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        trelloListDto.add(new TrelloListDto("1", "List 1", true));

        //When
        List<TrelloList> trelloList = trelloMapper.mapToList(trelloListDto);

        //Then
        assertEquals(1, trelloList.size());
        assertEquals("1", trelloList.get(0).getId());
        assertEquals("List 1", trelloList.get(0).getName());
        assertTrue(trelloList.get(0).isClosed());
    }

    @Test
    public void testMapToListDto() {
        //Given
        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(new TrelloList("1", "List 1", true));

        //When
        List<TrelloListDto> trelloListDto = trelloMapper.mapToListDto(trelloList);

        //Then
        assertEquals(1, trelloListDto.size());
        assertEquals("1", trelloListDto.get(0).getId());
        assertEquals("List 1", trelloListDto.get(0).getName());
        assertTrue(trelloListDto.get(0).isClosed());
    }

    @Test
    public void testMapToCardDto() {
        //Given
        TrelloCard card = new TrelloCard("Card", "Content", "Top", "1");

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(card);

        //Then
        Assert.assertEquals("Card", trelloCardDto.getName());
        Assert.assertEquals("Content", trelloCardDto.getDescription());
        Assert.assertEquals("Top", trelloCardDto.getPos());
        Assert.assertEquals("1", trelloCardDto.getListId());
    }

    @Test
    public void testMapToCard() {
        //Given
        TrelloCardDto cardDto = new TrelloCardDto("Card", "Content", "Top", "1");

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(cardDto);

        //Then
        Assert.assertEquals("Card", trelloCard.getName());
        Assert.assertEquals("Content", trelloCard.getDescription());
        Assert.assertEquals("Top", trelloCard.getPos());
        Assert.assertEquals("1", trelloCard.getListId());
    }
}
