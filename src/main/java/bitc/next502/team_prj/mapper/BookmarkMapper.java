package bitc.next502.team_prj.mapper;

import bitc.next502.team_prj.dto.BookmarkDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface BookmarkMapper {

    // 1. 찜 하기
    void insertBookmark(@Param("userId") String userId,
                        @Param("restaurantId") String restaurantId,
                        @Param("restaurantName") String restaurantName);

    // 2. 찜 취소
    void deleteBookmark(@Param("userId") String userId,
                        @Param("restaurantId") String restaurantId);

    // 3. 찜 여부 확인
    int isBookmarked(@Param("userId") String userId,
                     @Param("restaurantId") String restaurantId);

    // 4. 내 찜 목록 가져오기
    List<BookmarkDTO> selectMyBookmarkList(@Param("userId") String userId);
}