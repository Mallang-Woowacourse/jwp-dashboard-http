package nextstep.jwp.handler;

import java.io.File;
import java.net.URL;
import nextstep.jwp.db.InMemoryUserRepository;
import nextstep.jwp.model.User;
import nextstep.jwp.util.FileUtil;
import org.apache.coyote.http11.Http11Processor;
import org.apache.coyote.http11.handler.RequestHandler;
import org.apache.coyote.http11.request.HttpRequest;
import org.apache.coyote.http11.request.URI;
import org.apache.coyote.http11.response.HttpResponse;
import org.apache.coyote.http11.response.HttpStatus;
import org.apache.coyote.http11.response.ResponseHeaders;
import org.apache.coyote.http11.response.StatusLine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoginRequestHandler implements RequestHandler {

    private static final ClassLoader classLoader = ClassLoader.getSystemClassLoader();
    private static final Logger log = LoggerFactory.getLogger(Http11Processor.class);

    @Override
    public HttpResponse handle(HttpRequest request) {
        URI uri = request.startLine().uri();
        URL resource = classLoader.getResource("static" + uri.path() + ".html");
        File file = new File(resource.getFile());
        String responseBody = FileUtil.readAll(file);
        User user = InMemoryUserRepository.findByAccount(uri.queryStrings().get("account"))
                .orElse(null);
        log.info("User={}", user);
        StatusLine statusLine = new StatusLine(HttpStatus.OK);
        ResponseHeaders responseHeaders = new ResponseHeaders();
        responseHeaders.put("Content-Type", "text/html;charset=utf-8");
        return new HttpResponse(statusLine, responseHeaders, responseBody);
    }
}
