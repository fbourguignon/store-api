package com.store.integration.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RestResponsePage<T> extends PageImpl<T> {

    public RestResponsePage(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public RestResponsePage(List<T> content) {
        super(content);
    }

    /* PageImpl does not have an empty constructor and this was causing an issue for RestTemplate to cast the Rest API response
     * back to Page.
     */
    public RestResponsePage() {
        super(new ArrayList<T>());
    }
}
