package hello.upload.controller;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 상품 저장용 Form
 */
@Data
public class ItemForm {

    private Long itemId;
    private String itemName;
    private MultipartFile attachFile; //첨부파일 하나 업로드 처리
    private List<MultipartFile> imageFiles; //이미지 파일 다중 업로드 처리
}
