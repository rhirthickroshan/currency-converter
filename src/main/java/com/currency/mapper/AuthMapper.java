package com.currency.mapper;


import com.currency.dto.RegisterRequest;
import com.currency.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthMapper {

    User toUser(RegisterRequest  registerRequest);

}
