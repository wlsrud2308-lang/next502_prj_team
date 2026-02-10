package bitc.next502.team_prj.dto;

import lombok.Data;

@Data
public class FileDTO {

    private int imgIdx;
    private String imgType;
    private String restaurantId;
    private int reviewId;
    private String originalFilename;
    private String storedFilename;
    private long fileSize;
    private String createDate;
    private String createId;


}
