package bitc.next502.team_prj.service;

import bitc.next502.team_prj.common.FileUtils;
import bitc.next502.team_prj.dto.FileDTO;
import bitc.next502.team_prj.dto.ReviewDTO;
import bitc.next502.team_prj.mapper.FileMapper;
import bitc.next502.team_prj.mapper.ReviewMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewMapper reviewMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FileUtils fileUtils;

    @Override
    public List<ReviewDTO> selectReviewsList(String restaurantId) throws Exception {
        return reviewMapper.selectReviewsList(restaurantId);
    }

    @Override
    public Page<ReviewDTO> selectMyReviewsList(int pageNum, String userId) throws Exception {
        int pageSize = 3;
        PageHelper.startPage(pageNum, pageSize);
        return reviewMapper.selectMyReviewsList(userId);
    }

    @Override
    public ReviewDTO selectReviewsDetail(int reviewId) throws Exception {
        return reviewMapper.selectReviewsDetail(reviewId);
    }

    @Override
    public void insertReview(ReviewDTO review) throws Exception {
        reviewMapper.insertReview(review);
    }

    // ✅ 개편된 리뷰+파일 등록 기능
    @Transactional // 리뷰와 파일 저장은 하나로 묶여야 함
    @Override
    public void insertReviewFile(ReviewDTO review, MultipartHttpServletRequest multipart) throws Exception {

        // 1. 예약 정보를 바탕으로 리뷰 등록 (이때 DB에서 reviewIdx가 생성됨)
        reviewMapper.insertReviewByResvId(review);

        // 2. 생성된 reviewIdx가 review 객체에 잘 담겼는지 확인 후 파일 파싱
        // (Mapper XML에 useGeneratedKeys="true" keyProperty="reviewIdx"가 필수입니다)
        List<FileDTO> fileList = fileUtils.parseFileInfo(review.getRestaurantId(), review.getReviewIdx(), multipart);

        // 3. 파일이 존재할 경우에만 DB(file 테이블)에 저장
        if (!CollectionUtils.isEmpty(fileList)) {
            fileMapper.insertFileList(fileList);
        }

        // 4. (기존 기능 유지) 콘솔에 파일 정보 출력
        if (!ObjectUtils.isEmpty(multipart)) {
            Iterator<String> it = multipart.getFileNames();
            while (it.hasNext()) {
                String name = it.next();
                List<MultipartFile> files = multipart.getFiles(name);
                for (MultipartFile file : files) {
                    if (!file.isEmpty()) {
                        System.out.println("\n--------- 업로드 파일 정보 -----------------------");
                        System.out.println(" 파일명 : " + file.getOriginalFilename());
                        System.out.println(" 파일 크기 : " + file.getSize());
                        System.out.println("----------------------------------------------");
                    }
                }
            }
        }
    }

    @Transactional
    @Override
    public void updateReview(ReviewDTO review, MultipartHttpServletRequest multipart) throws Exception {
        reviewMapper.updateReview(review);

        List<FileDTO> fileList = fileUtils.parseFileInfo(review.getRestaurantId(), review.getReviewIdx(), multipart);

        if (!CollectionUtils.isEmpty(fileList)) {
            fileMapper.insertFileList(fileList);
        }
    }

    @Override
    public void deleteReview(int reviewId) throws Exception {
        reviewMapper.deleteReview(reviewId);
    }
}