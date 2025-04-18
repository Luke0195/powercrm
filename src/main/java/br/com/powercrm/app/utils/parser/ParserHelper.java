package br.com.powercrm.app.utils.parser;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class ParserHelper {

    private ParserHelper(){}


    public static<T> Page<T> parseListToPage(List<T> data, int page, int size){
        int start = Math.min(page * size, data.size());
        int end = Math.min(start + size, data.size());
        List<T> subList = data.subList(start, end);
        Pageable pageable = PageRequest.of(page, size);
        return new PageImpl<>(subList, pageable, data.size());
    }
}
