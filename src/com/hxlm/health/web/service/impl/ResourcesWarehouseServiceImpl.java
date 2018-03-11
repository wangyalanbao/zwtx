package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.Setting;
import com.hxlm.health.web.entity.ProductImage;
import com.hxlm.health.web.entity.ResourcesWarehouse;
import com.hxlm.health.web.plugin.StoragePlugin;
import com.hxlm.health.web.service.ResourcesWarehouseService;
import com.hxlm.health.web.util.FreemarkerUtils;
import com.hxlm.health.web.util.SettingUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.File;
import java.util.*;

/**
 * Created by guofeng on 2016/2/17.
 */

@Service("resourcesWarehouseServiceImpl")
public class ResourcesWarehouseServiceImpl  implements ResourcesWarehouseService, ServletContextAware {

    private ServletContext servletContext;

    @Resource(name = "taskExecutor")
    private TaskExecutor taskExecutor;
    @Resource
    private List<StoragePlugin> storagePlugins;

    public void setServletContext(ServletContext servletContext) { this.servletContext = servletContext; }

    /**
     * 添加图片处理任务
     *
     * @param sourcePath
     *            原图片上传路径
     * @param tempFile
     *            原临时文件
     * @param contentType
     *            原文件类型
     */
    private void addTask(final String sourcePath, final File tempFile, final String contentType) {
        try {
            taskExecutor.execute(new Runnable() {
                public void run() {
                    Collections.sort(storagePlugins);
                    for (StoragePlugin storagePlugin : storagePlugins) {
                        if (storagePlugin.getIsEnabled()) {
                            try {
                                storagePlugin.upload(sourcePath, tempFile, contentType);
                            } finally {
                                FileUtils.deleteQuietly(tempFile);
                            }
                            break;
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void build(ProductImage productImage) {
        MultipartFile multipartFile = productImage.getFile();
        if (multipartFile != null && !multipartFile.isEmpty()) {
            try {
                Setting setting = SettingUtils.get();
                Map<String, Object> model = new HashMap<String, Object>();
                model.put("uuid", UUID.randomUUID().toString());
                String uploadPath = FreemarkerUtils.process(setting.getImageUploadPath(), model);
                String uuid = UUID.randomUUID().toString();
                String sourcePath = uploadPath + uuid + "-source." + FilenameUtils.getExtension(multipartFile.getOriginalFilename());

                Collections.sort(storagePlugins);
                for (StoragePlugin storagePlugin : storagePlugins) {
                    if (storagePlugin.getIsEnabled()) {
                        File tempFile = new File(System.getProperty("java.io.tmpdir") + "/upload_" + UUID.randomUUID() + ".tmp");
                        if (!tempFile.getParentFile().exists()) {
                            tempFile.getParentFile().mkdirs();
                        }
                        multipartFile.transferTo(tempFile);
                        addTask(sourcePath, tempFile, multipartFile.getContentType());
                        productImage.setSource(storagePlugin.getUrl(sourcePath));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
