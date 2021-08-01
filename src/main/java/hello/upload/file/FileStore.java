package hello.upload.file;

import hello.upload.domain.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 파일 저장과 관련된 업무 처리를 하는 클래스 선언
 */
@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    /**
     * 파일 저장 경로 생성
     *
     * @param filename
     * @return
     */
    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    /**
     * 여러 개 파일 업로드
     *
     * @param multipartFiles
     * @return
     * @throws IOException
     */
    public List<UploadFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadFile> storeFileResult = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }

        return storeFileResult;
    }

    /**
     * 파일 하나 업로드
     *
     * @param multipartFile MultipartFile을 사용해서 파일을 저장 후 UloadFile를 반환
     * @return
     */
    public UploadFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createFileName(originalFilename);

        multipartFile.transferTo(new File(getFullPath(storeFileName)));

        return new UploadFile(originalFilename, storeFileName);
    }

    /**
     * 서버에 저장하는 파일명 생성 메소드
     * "image.png" -> "51041c62-86e4-4274-801d-614a7d994edb.png"
     *
     * @param originalFilename
     * @return
     */
    private String createFileName(String originalFilename) {
        String ext = extractExt(originalFilename); //확장자 추출
        String uuid = UUID.randomUUID().toString();

        return uuid + "." + ext;
    }

    /**
     * 확장자 추출 메소드
     *
     * @param originalFilename
     * @return
     */
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
