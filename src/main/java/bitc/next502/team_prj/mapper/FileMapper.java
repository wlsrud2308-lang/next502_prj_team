package bitc.next502.team_prj.mapper;

import bitc.next502.team_prj.dto.FileDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileMapper {
    //하나의 파일만 올림
    void insertFileOne(FileDTO file) throws Exception;

    //여러개의 파일 업로드
    void insertFileList(List<FileDTO> fileList) throws Exception;

    //imgType : 1- 메인이미지 2-댓글이미지
    public List<FileDTO> selectFileList(@Param("restaurantId") int restaurantId,@Param("imgType") int imgType) throws Exception;

    public FileDTO selectFileDetail(@Param("restaurantId") int restaurantId,@Param("fileIdx") int fileIdx) throws Exception;


}
