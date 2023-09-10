package nextstep.jwp.handler;

import static org.apache.catalina.servlet.response.HttpStatus.OK;

import nextstep.jwp.util.ResourceFileUtil;
import org.apache.catalina.servlet.AbstractController;
import org.apache.catalina.servlet.request.HttpRequest;
import org.apache.catalina.servlet.request.URI;
import org.apache.catalina.servlet.response.HttpResponse;
import org.apache.catalina.servlet.response.StatusLine;

public class StaticResourceRequestHandler extends AbstractController {

    @Override
    protected void doGet(HttpRequest request, HttpResponse response) {
        URI uri = request.requestLine().uri();
        String responseBody = ResourceFileUtil.readAll("static" + path(uri));
        response.setStatusLine(new StatusLine(OK));
        response.addHeader("Content-Type", contentType(uri) + "charset=utf-8");
        response.setMessageBody(responseBody);
    }

    private String path(URI uri) {
        if (uri.path().contains(".")) {
            return uri.path();
        } else {
            return uri.path() + ".html";
        }
    }

    private String contentType(URI uri) {
        if (uri.path().endsWith(".css")) {
            return "text/css;";
        }
        if (uri.path().endsWith(".js")) {
            return "text/javascript;";
        }
        if (uri.path().endsWith(".svg")) {
            return "image/svg+xml;";
        }
        return "text/html;";
    }
}
