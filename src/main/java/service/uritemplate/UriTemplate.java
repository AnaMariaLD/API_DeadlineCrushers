package service.uritemplate;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UriTemplate {
    private final String url;

    public String getUri() {
        return String.format(url, "");
    }


}
