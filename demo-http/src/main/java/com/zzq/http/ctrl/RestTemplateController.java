package com.zzq.http.ctrl;

import cn.hutool.json.JSONUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequiredArgsConstructor
public class RestTemplateController {

    private final RestTemplate restTemplate;

    @GetMapping("/test1/{name}")
    public ResponseEntity<String> test1(@PathVariable String name) {

        return ResponseEntity.ok(name);
    }

    @GetMapping("/test2")
    public ResponseEntity<String> test2(@RequestParam String name) {

        return ResponseEntity.ok(name);
    }

    @PostMapping("/test3")
    public ResponseEntity<Object> test3(@RequestBody Map<String, String> map) {
        return ResponseEntity.ok(map);
    }

    @PostMapping("/test4")
    public ResponseEntity<String> test4(MultipartFile file) {

        return ResponseEntity.ok(file.getOriginalFilename());
    }


    @GetMapping("/get1")
    public ResponseEntity<String> get1() {
        String url = "http://localhost:8080/test1/{name}";

        Map<String, String> varParams = new HashMap<>();
        varParams.put("name", "zzq");

        return restTemplate.exchange(url, HttpMethod.GET, null, String.class, varParams);
    }


    @GetMapping("/get2")
    public ResponseEntity<String> get2() {
        String url = "http://localhost:8080/test2";

        Map<String, String> varParams = new HashMap<>();
        varParams.put("name", "zzq");

        return restTemplate.exchange(url + "?name={name}", HttpMethod.GET, null, String.class, varParams);
    }

    @GetMapping("/get3")
    public ResponseEntity<Object> get3() {
        String url = "http://localhost:8080/test3";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> map = new HashMap<>();
        map.put("name", "zzq");
        map.put("age", "18");

        HttpEntity<String> requestEntity = new HttpEntity<>(JSONUtil.toJsonStr(map), headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, Object.class);
    }

    @PostMapping("/get4")
    public ResponseEntity<String> get4(MultipartFile multipartFile) throws IOException {
        String url = "http://localhost:8080/test4";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> map = new LinkedMultiValueMap<>();

        InputStreamResource fileResource = new InputStreamResource(multipartFile.getInputStream()) {
            @Override
            public long contentLength() {
                return multipartFile.getSize();
            }

            @Override
            public String getFilename() {
                return multipartFile.getOriginalFilename();
            }
        };

        map.add("file", fileResource);
        HttpEntity<Object> requestEntity = new HttpEntity<>(map, headers);

        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }


}

