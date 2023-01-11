package service.uritemplate;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UriTemplate {
    private final String url;

    public String getUri() {
        return String.format(url, "");
    }
    public String getUri(Object... params) {
        return String.format(url, params);
    }



}
