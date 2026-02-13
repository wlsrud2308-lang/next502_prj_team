package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.BookmarkDTO;

import java.util.List;

public interface BookmarkService {
    // 찜  ( 1=등록됨, 0=취소됨)

    int toggleBookmark(String userId, String restaurantId, String restaurantName);
    List<BookmarkDTO> getBookmarkList(String userId);

    void deleteBookmark(String userId, String restaurantId);

    boolean isBookmarked(String userId, String restaurantId);
}