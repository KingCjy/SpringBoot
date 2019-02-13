package com.kingcjy.main.controller;

import com.kingcjy.main.dto.SearchProductDTO;
import com.kingcjy.main.domain.ApiResult;
import com.kingcjy.main.domain.Pagenation;
import com.kingcjy.main.entity.UserEntity;
import com.kingcjy.main.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/product")
    public ResponseEntity<ApiResult<Page<SearchProductDTO>>> searchProducts(HttpServletRequest request, @PageableDefault Pageable pageable) {

        Pagenation<SearchProductDTO> pagenation = new Pagenation<>();

        pagenation.setCurrentPage(1);
        pagenation.setMaxPage(10);
        pagenation.setSize(10);
        pagenation.setList(new ArrayList<>());

        Page<UserEntity> page = userRepository.findAll(pageable);

        return ApiResult.getResponse(HttpStatus.OK, null);
    }
}
