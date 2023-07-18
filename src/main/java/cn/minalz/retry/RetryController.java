package cn.minalz.retry;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhouwei
 * @date 2023/7/18 15:48
 */
@AllArgsConstructor
@RestController
@RequestMapping("/retry")
public class RetryController {

    private final RetryServiceImpl retryService;

    @GetMapping("")
    public Object submit(){
        retryService.retry();
    }
}
