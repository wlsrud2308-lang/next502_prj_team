package bitc.next502.team_prj.common;


import bitc.next502.team_prj.dto.FileDTO;
import org.apache.ibatis.javassist.bytecode.Descriptor;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// @Component 사용자가 직접 작성한 클래스를 스프링 프레임워크가 관리하도록 함

//@Bean 외부 라이브러리 클래스임 , 스프링 프레임워크가 관리 하도록하는 어노테이션

@Component
public class FileUtils {

    public List<FileDTO> parseFileInfo (String restaurantId,int reviewId, MultipartHttpServletRequest multi) throws Exception{


        //ObjectUtils  :스프링 프레임워크에서 제공하는 유틸클래스
        //업ㄹ드된 파일 정보가 있는지 체크
       if(ObjectUtils.isEmpty(multi)){
            return null;
       }
       List<FileDTO> filelist = new ArrayList<>();

        DateTimeFormatter fm=DateTimeFormatter.ofPattern("yyyy-MM-dd");
        ZonedDateTime currentTime=ZonedDateTime.now();

        String path ="C:/next502/images/" + currentTime.format(fm);
        File file = new File(path);
        if(!file.exists()){
            file.mkdirs();
        }

        Iterator<String> it = multi.getFileNames();

        String newFileName="";
        String originalFileExt="";
        String contentType="";

        while (it.hasNext()) {
            //next() : Iterator 타입의 객체에서 실제로 데이터 출력
            String name = it.next();
            List<MultipartFile> mfFileList = multi.getFiles(name);

            for(MultipartFile uploadFile : mfFileList){
                contentType = uploadFile.getContentType();
                if (ObjectUtils.isEmpty(contentType)) {
                    break;
                }
                else {
                    if (contentType.contains("image/jpeg")){
                        originalFileExt=".jpg";

                    }
                    else if(contentType.contains("image/png")){
                        originalFileExt=".png";
                    }
                    else if(contentType.contains("image/gif")){
                        originalFileExt=".gif";
                    }
                    else {
                        break;
                    }
                }
                //현재시간을 기준으로 nano 초로 표시
                //현재시간을 기준으로 확장자를 가지고 있는 파일명을 새로 생성함
                newFileName = System.nanoTime() + originalFileExt;

                FileDTO fileDTO = new FileDTO();
                fileDTO.setRestaurantId(restaurantId);
                fileDTO.setReviewId(reviewId);
                fileDTO.setFileSize(uploadFile.getSize());
                fileDTO.setOriginalFilename(uploadFile.getOriginalFilename());
                fileDTO.setStoredFilename(currentTime.format(fm) + "/" + newFileName);

                filelist.add(fileDTO);
                file = new File(path + "/" + newFileName);

                uploadFile.transferTo(file);
            }

        }

        return filelist;

    }
}
