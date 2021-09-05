package com.depromeet.threedollar.external.external.slack;

import com.depromeet.threedollar.external.external.slack.dto.request.PostSlackMessageRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "slackClient", url = "https://hooks.slack.com/services", primary = false)
public interface SlackApiCaller {

    @PostMapping("${slack.token}")
    void postMessage(PostSlackMessageRequest request);

}
