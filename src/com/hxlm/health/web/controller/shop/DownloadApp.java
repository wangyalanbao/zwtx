package com.hxlm.health.web.controller.shop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by dengyang on 16/8/4.
 */

@Controller("shopDownloadAppController")
@RequestMapping("/download_app")
public class DownloadApp extends BaseController {

    @RequestMapping(value = "/down", method = RequestMethod.GET)
    public String down(ModelMap model) {

        return "h5/down";
    }
}
