package com.hxlm.health.web.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dengyang on 15/6/18.
 */

// 评估相关配置
public class EvaluateSttings {

    private static EvaluateSttings conclusionReportCategoryMap;

    public Map map;

    public Map getMap() { return map; }

    public static EvaluateSttings getInstance() {
        if (conclusionReportCategoryMap == null)
            conclusionReportCategoryMap = new EvaluateSttings();
        return conclusionReportCategoryMap;
    }

    private EvaluateSttings() {
        map = new HashMap();
//        map.put("video", "视频");
//        map.put("audio", "音频");
//        map.put("h5", "图文");
//        map.put("other", "其他");
//        map.put("pics", "画册");
        map.put("file", "文件");
    }

}