package com.depromeet.threedollar.external.external.slack;

import com.depromeet.threedollar.external.external.slack.dto.request.PostSlackMessageRequest;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Primary
@Profile({"local", "local-will"})
@Component
public class LocalSlackApiCaller implements SlackApiCaller {

    @Override
    public void postMessage(PostSlackMessageRequest request) {

    }
}
