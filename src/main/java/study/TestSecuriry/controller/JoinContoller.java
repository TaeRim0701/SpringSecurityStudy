package study.TestSecuriry.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import study.TestSecuriry.dto.JoinDto;
import study.TestSecuriry.service.JoinService;

@Controller
public class JoinContoller {

    @Autowired
    private JoinService joinService; //지금은 간편하게 하기 위해 필드 주입 방식 사용, 나중에는 생성자 주입 방식 사용

    @GetMapping("/join")
    public String joinP() {
        return "join";
    }

    @PostMapping("joinProc")
    public String joinProcess(JoinDto joinDto) {
        System.out.println(joinDto.getUsername());
        joinService.joinProcess(joinDto);

        return "redirect:/login";
    }

}
