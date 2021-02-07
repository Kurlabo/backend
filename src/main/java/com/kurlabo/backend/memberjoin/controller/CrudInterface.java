package com.kurlabo.backend.memberjoin.controller;

import com.kurlabo.backend.memberjoin.model.network.Header;
import com.kurlabo.backend.memberjoin.model.network.request.UserApiRequest;
import com.kurlabo.backend.memberjoin.model.network.response.UserApiResponse;

public interface CrudInterface<Req, Res> {

    Header<Res> create(Header<Req> request);

    Header<Res> read(Long id);

    Header<Res> update(Header<Req> request);

    Header delete(Long id);
}
