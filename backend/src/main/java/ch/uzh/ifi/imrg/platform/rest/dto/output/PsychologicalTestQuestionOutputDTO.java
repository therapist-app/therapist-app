package ch.uzh.ifi.imrg.platform.rest.dto.output;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PsychologicalTestQuestionOutputDTO {

    private String question;
    private int score;

}