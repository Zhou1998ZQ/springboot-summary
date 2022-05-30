package com.zzq.poi.service;

import org.springframework.web.multipart.MultipartFile;



public interface PoiService {
    Object read(MultipartFile file);

    void download();
}
