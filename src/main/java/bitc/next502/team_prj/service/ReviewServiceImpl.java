package bitc.next502.team_prj.service;

import bitc.next502.team_prj.common.FileUtils;
import bitc.next502.team_prj.dto.FileDTO;
import bitc.next502.team_prj.dto.ReviewDTO;
import bitc.next502.team_prj.mapper.FileMapper;
import bitc.next502.team_prj.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Iterator;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;



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
        //댓글 조회 레스토랑으로 조회
        return reviewMapper.selectReviewsList(restaurantId);
    }

    @Override
    public List<ReviewDTO> selectMyReviewsList(String userId) throws Exception {
        //나의 댓글 조회 등록ID로 조회
        return reviewMapper.selectMyReviewsList(userId);
    }

    @Override
    public ReviewDTO selectReviewsDetail(int reviewId) throws Exception {
        //댓글 상세조회
        return reviewMapper.selectReviewsDetail(reviewId);
    }

    @Override
    public void insertReview(ReviewDTO review) throws Exception {
        reviewMapper.insertReview(review);

    }

    @Override
    public void insertReviewFile(ReviewDTO review, MultipartHttpServletRequest multipart) throws Exception {

        //reviewMapper.insertReview(review);

        //예약 방문후 댓글 등록
        reviewMapper.insertReviewByResvId(review);

        List<FileDTO> fileList = fileUtils.parseFileInfo(review.getRestaurantId(),review.getReviewIdx(), multipart);

        if(!CollectionUtils.isEmpty(fileList)){
            fileMapper.insertFileList(fileList);
        }

        if (ObjectUtils.isEmpty(multipart) == false){
            Iterator<String> it=multipart.getFileNames();
            String name;
            while(it.hasNext()){
                name=it.next();
                List<MultipartFile> files=multipart.getFiles(name);

                for(MultipartFile file: files){
                    System.out.println("\n--------- 파일 정보 -----------------------");
                    System.out.println(" 파일명 : "+file.getOriginalFilename());
                    System.out.println("파일 크기 : " +file.getSize());
                    System.out.println("파일 타입 : "+file.getContentType());
                    System.out.println("\n------------------------------------------");
                }
            }
        }



    }

    @Override
    public void updateReview(ReviewDTO review,MultipartHttpServletRequest multipart) throws Exception {

        reviewMapper.updateReview(review);

        List<FileDTO> fileList = fileUtils.parseFileInfo(review.getRestaurantId(),review.getReviewIdx(), multipart);

        if(!CollectionUtils.isEmpty(fileList)){
            fileMapper.insertFileList(fileList);
        }

        if (ObjectUtils.isEmpty(multipart) == false){
            Iterator<String> it=multipart.getFileNames();
            String name;
            while(it.hasNext()){
                name=it.next();
                List<MultipartFile> files=multipart.getFiles(name);

                for(MultipartFile file: files){
                    System.out.println("\n--------- 파일 정보 -----------------------");
                    System.out.println(" 파일명 : "+file.getOriginalFilename());
                    System.out.println("파일 크기 : " +file.getSize());
                    System.out.println("파일 타입 : "+file.getContentType());
                    System.out.println("\n------------------------------------------");
                }
            }
        }
    }

    @Override
    public void deleteReview(int reviewId) throws Exception {
        reviewMapper.deleteReview(reviewId);
    }
}
