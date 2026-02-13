package bitc.next502.team_prj.service;

import bitc.next502.team_prj.dto.BookmarkDTO;
import bitc.next502.team_prj.mapper.BookmarkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class BookmarkServiceImpl implements BookmarkService {

    @Autowired
    private BookmarkMapper bookmarkMapper;

    // 1. 찜 추가/취소 (하트 클릭)
    @Override
    public int toggleBookmark(String userId, String restaurantId, String restaurantName) {
        int count = bookmarkMapper.isBookmarked(userId, restaurantId);

        if (count > 0) {
            bookmarkMapper.deleteBookmark(userId, restaurantId);
            return 0;
        } else {
            bookmarkMapper.insertBookmark(userId, restaurantId, restaurantName);
            return 1;
        }
    }

    // 2. 찜 목록 가져오기
    @Override
    public List<BookmarkDTO> getBookmarkList(String userId) {
        return bookmarkMapper.selectMyBookmarkList(userId);
    }

    // 3. 찜 삭제하기 (마이페이지에서 삭제)
    @Override
    public void deleteBookmark(String userId, String restaurantId) {
        bookmarkMapper.deleteBookmark(userId, restaurantId);
    }

    @Override
    public boolean isBookmarked(String userId, String restaurantId) {
        // 이미 작성하신 mapper 로직을 그대로 활용하면 됩니다.
        int count = bookmarkMapper.isBookmarked(userId, restaurantId);
        return count > 0;
    }
}