package web.id.noxymon.averageimage.processes;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class FileProcess {

    public File saveFile(MultipartFile file, String filePath){
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        File targetFile = null;
        try {
            if (file.isEmpty()) {
                throw new  RuntimeException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new RuntimeException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                targetFile = new File(filePath + filename);
                FileUtils.copyInputStreamToFile(inputStream, targetFile);
            }
            return targetFile;
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to store file " + filename, e);
        }
    }

}
