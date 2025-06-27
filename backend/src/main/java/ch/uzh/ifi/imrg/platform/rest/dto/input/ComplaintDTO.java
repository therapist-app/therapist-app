package ch.uzh.ifi.imrg.platform.rest.dto.input;

import lombok.Data;

@Data
public class ComplaintDTO {
  private String mainComplaint;
  private String duration;
  private String onset;
  private String course;
  private String precipitatingFactors;
  private String aggravatingRelieving;
  private String timeline;
  private String disturbances;
  private String suicidalIdeation;
  private String negativeHistory;
}
