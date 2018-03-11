package com.hxlm.health.web.service;

import com.hxlm.health.web.entity.MemberImage;

/**
 * Created by delll on 2015/6/28.
 * service--MemberImage
 */
public interface MemberImageService {
    /**
     * 生成会员照片
     */
    void build(MemberImage memberImage);
}
