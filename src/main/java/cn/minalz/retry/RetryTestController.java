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
@RequestMapping("/retry/test")
public class RetryTestController {

    private final RetryTestService retryTestService;

    @GetMapping("/submit")
    public void submit() throws CustomRetryException {
        retryTestService.submit();
    }
}
