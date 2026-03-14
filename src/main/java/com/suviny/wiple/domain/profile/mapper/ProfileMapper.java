package com.suviny.wiple.domain.profile.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfileMapper {

    /**
     * 회원가입시 기본 프로필 정보를 생성한다.
     */
    void insertProfile(Long userId);
}