package groundwork.fileControl.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.io.IOException;
import java.util.Collection;

@Slf4j
@Controller
@RequestMapping("/servlet/v1")
public class ServletFileControllerV1 {
    @GetMapping("/upload")
    public String newFile() {
        return "uploadForm";
    }

    @PostMapping("/upload")
    public String saveFileV1(HttpServletRequest req) throws ServletException, IOException {
        log.info("request={}", req);

        String itemName = req.getParameter("itemName");
        log.info("itemName={}", itemName);

        Collection<Part> parts = req.getParts();
        log.info("parts={},", parts);

        return "uploadForm";
    }
}
