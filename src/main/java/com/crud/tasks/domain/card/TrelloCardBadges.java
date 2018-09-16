package com.crud.tasks.domain.card;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class TrelloCardBadges {

    @JsonProperty("votes")
    private int votes;

    @JsonProperty("trelloCardAttachmentsByType")
    private TrelloCardAttachmentsByType attachmentsByType;
}
