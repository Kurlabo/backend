package com.kurlabo.backend.memberjoin.controller.api;

import com.kurlabo.backend.memberjoin.controller.CrudInterface;
import com.kurlabo.backend.memberjoin.model.network.Header;
import com.kurlabo.backend.memberjoin.model.network.request.UserApiRequest;
import com.kurlabo.backend.memberjoin.model.network.response.UserApiResponse;
import com.kurlabo.backend.memberjoin.service.UserApiLogicService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/account/signup")
public class UserApiController implements CrudInterface<UserApiRequest, UserApiResponse> {

    @Autowired
    private UserApiLogicService userApiLogicService;

    @Override
    @PostMapping("") // /api/acount/singup
    public Header<UserApiResponse> create(@RequestBody Header<UserApiRequest> request) {
        log.info("{}", request);
        return userApiLogicService.create(request);
    }

    @Override
    @GetMapping("{id}") // /api/account/{id}
    public Header read(@PathVariable(name = "id") Long id) {
        log.info("read : {}", id);
        return userApiLogicService.read(id);
    }

    @Override
    @PutMapping("") // /api/acount/singup
    public Header<UserApiResponse> update(@RequestBody Header<UserApiRequest> request) {
        return null;
    }

    @Override
    @DeleteMapping("{id}") // /api/account/{id}
    public Header delete(@PathVariable Long id) {
        return null;
    }
}
