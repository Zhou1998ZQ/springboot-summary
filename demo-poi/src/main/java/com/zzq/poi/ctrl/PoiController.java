package com.zzq.poi.ctrl;

import com.zzq.poi.service.PoiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class PoiController {

    private final PoiService poiService;

    @PostMapping("/read")
    public Object read(MultipartFile file) {

        return poiService.read(file);
    }

    @GetMapping("/download")
    public void download() {
        poiService.download();
    }
}
