package ch.uzh.ifi.imrg.platform.rest.dto.output;

import java.util.List;

import ch.uzh.ifi.imrg.platform.enums.ExerciseType;
import lombok.Data;

@Data
public class ExerciseOutputDTO {
    private String id;
    private String title;
    private ExerciseType exerciseType;
    private List<ExerciseTextOutputDTO> exerciseTextsOutputDTO;
    private List<ExerciseFileOutputDTO> exerciseFilesOutputDTO;
}
