package groundwork.fileControl.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/servlet/v2")
public class ServletFileControllerV2 {
    @Value("${file.dir}")
    private String fileDir;

    @GetMapping("/upload")
    public String newFile() {
        return "uploadForm";
    }

    @PostMapping("/upload")
    public String saveFileV2(HttpServletRequest req) throws ServletException, IOException {
        log.info("request={}", req);

        String itemName = req.getParameter("itemName");
        log.info("itemName={}", itemName);

        Collection<Part> parts = req.getParts();
        log.info("parts={},", parts);

        for (Part part : parts) {
            log.info("===== PART =====");
            log.info("name={}", part.getName());
            Collection<String> headerNames = part.getHeaderNames(); // 각 part도 header와 body로 구분됨
            for (String headerName : headerNames) {
                log.info("header {} : {}", headerName, part.getHeader(headerName));
            }

            // header data
            log.info("submittedFilename={}", part.getSubmittedFileName());
            log.info("size={}", part.getSize());

            // body data
            InputStream inputStream = part.getInputStream();
            String body = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);// binary <-> String : charset 정의 필요
            log.info("body={}", body); // 대용량 파일 업로드 테스트 시 log 양이 많기 때문에 주석처리

            // save file(client -> server)
            if (StringUtils.hasText(part.getSubmittedFileName())) {
                String fullPath = fileDir + part.getSubmittedFileName();
                log.info("파일 저장 fullPath={}", fullPath);
                part.write(fullPath);
            }


            inputStream.close();
        }

        return "uploadForm";
    }
}
