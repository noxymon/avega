package web.id.noxymon.averageimage.v1.image;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import web.id.noxymon.averageimage.processes.ColorProcess;
import web.id.noxymon.averageimage.processes.FileProcess;
import web.id.noxymon.averageimage.utils.Constant;
import web.id.noxymon.averageimage.v1.image.models.AverageRGBResponse;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/image")
public class ImageController {

    @Autowired
    ColorProcess colorProcess;

    @Autowired
    FileProcess fileProcess;

    @PostMapping("/rgb/")
    public HttpEntity fileUpload(@RequestParam("file")MultipartFile file, @RequestParam("path") String savePath, @RequestParam("method") String method){
        File uploadedFile = fileProcess.saveFile(file, savePath);
        Map<String, Double> colorResult = new HashMap<>();
        AverageRGBResponse averageRGBResponse = new AverageRGBResponse();
        if(uploadedFile != null){
            switch (method){
                case Constant.Color.AVERAGE_METHOD_PROPERTY_VALUE:
                    colorResult = colorProcess.averageColor(uploadedFile);
                    break;
                default:
                    colorResult = colorProcess.averageColor(uploadedFile);
                    break;
            }

            averageRGBResponse.setRed(colorResult.get(Constant.Color.RED_PROPERTY_VALUE));
            averageRGBResponse.setGreen(colorResult.get(Constant.Color.GREEN_PROPERTY_VALUE));
            averageRGBResponse.setBlue(colorResult.get(Constant.Color.BLUE_PROPERTY_VALUE));
        }
        return new ResponseEntity(averageRGBResponse, HttpStatus.OK);
    }

    @GetMapping("/test/")
    public HttpEntity test(){
        return new ResponseEntity(null, HttpStatus.I_AM_A_TEAPOT);
    }

}
