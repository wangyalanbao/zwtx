/*
 * 
 * 
 * 
 */
package com.hxlm.health.web.service.impl;

import com.hxlm.health.web.Template;
import com.hxlm.health.web.dao.ProductDao;
import com.hxlm.health.web.entity.*;
import com.hxlm.health.web.service.StaticService;
import com.hxlm.health.web.service.TemplateService;
import com.hxlm.health.web.util.FreemarkerUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service - 静态化
 * 
 * 
 * 
 */
@Service("staticServiceImpl")
public class StaticServiceImpl implements StaticService, ServletContextAware {

	/** Sitemap最大地址数 */
	private static final Integer SITEMAP_MAX_SIZE = 40000;

	/** servletContext */
	private ServletContext servletContext;

	@Resource(name = "freeMarkerConfigurer")
	private FreeMarkerConfigurer freeMarkerConfigurer;
	@Resource(name = "templateServiceImpl")
	private TemplateService templateService;

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	@Transactional(readOnly = true)
	public int build(String templatePath, String staticPath, Map<String, Object> model) {
		Assert.hasText(templatePath);
		Assert.hasText(staticPath);

		FileOutputStream fileOutputStream = null;
		OutputStreamWriter outputStreamWriter = null;
		Writer writer = null;
		try {
			freemarker.template.Template template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath);
			File staticFile = new File(servletContext.getRealPath(staticPath));
			File staticDirectory = staticFile.getParentFile();
			if (!staticDirectory.exists()) {
				staticDirectory.mkdirs();
			}
			fileOutputStream = new FileOutputStream(staticFile);
			outputStreamWriter = new OutputStreamWriter(fileOutputStream, "UTF-8");
			writer = new BufferedWriter(outputStreamWriter);
			template.process(model, writer);
			writer.flush();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(writer);
			IOUtils.closeQuietly(outputStreamWriter);
			IOUtils.closeQuietly(fileOutputStream);
		}
		return 0;
	}

	@Transactional(readOnly = true)
	public int build(String templatePath, String staticPath) {
		return build(templatePath, staticPath, null);
	}



	@Transactional(readOnly = true)
	public int build(Product product) {
		Assert.notNull(product);

		delete(product);

		int buildCount = 0;
		if (product.getIsMarketable()) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("product", product);
			// web端商品详情静态化
			build(templateService.get("productContent").getTemplatePath(), product.getPath(), model);
			// pad端商品详情静态化
			build(templateService.get("padProductDetail").getTemplatePath(), product.getPadPath(), model);
			// 手机商品详情静态化
			buildCount += build(templateService.get("mobileProductDetail").getTemplatePath(), product.getMobilePath(), model);
		}
		return buildCount;
	}
	@Transactional(readOnly = true)
	public int build(MemberChild memberChild){
		Assert.notNull(memberChild);

		int buildCount = 0;

//			Map<String, Object> model = new HashMap<String, Object>();
//			model.put("memberChild", memberChild);
		buildCount += build("memberChild",memberChild.getName());

		return buildCount;

	}

	@Transactional(readOnly = true)
	public int buildIndex() {
		int buildCount = 0;
		// 原商城首页
		Template template = templateService.get("index");
		buildCount += build(template.getTemplatePath(), template.getStaticPath());
//		// 手机商城首页
//		template = templateService.get("mobileIndex");
//		buildCount += build(template.getTemplatePath(), template.getStaticPath());
//		// pad商城首页
//		template = templateService.get("padIndex");
//		buildCount += build(template.getTemplatePath(), template.getStaticPath());
		return buildCount;
	}

	@Transactional(readOnly = true)
	public int buildSitemap() {
		int buildCount = 0;
//		Template sitemapIndexTemplate = templateService.get("sitemapIndex");
//		Template sitemapTemplate = templateService.get("sitemap");
//		Map<String, Object> model = new HashMap<String, Object>();
//		List<String> staticPaths = new ArrayList<String>();
//		for (int step = 0, index = 0, first = 0, count = SITEMAP_MAX_SIZE;;) {
//			try {
//				model.put("index", index);
//				String templatePath = sitemapTemplate.getTemplatePath();
//				String staticPath = FreemarkerUtils.process(sitemapTemplate.getStaticPath(), model);
//				if (step == 0) {
//					List<Article> articles = articleDao.findList(first, count, null, null);
//					model.put("articles", articles);
//					if (articles.size() < count) {
//						step++;
//						first = 0;
//						count -= articles.size();
//					} else {
//						buildCount += build(templatePath, staticPath, model);
//						articleDao.clear();
//						articleDao.flush();
//						staticPaths.add(staticPath);
//						model.clear();
//						index++;
//						first += articles.size();
//						count = SITEMAP_MAX_SIZE;
//					}
//				} else if (step == 1) {
//					List<Product> products = productDao.findList(first, count, null, null);
//					model.put("products", products);
//					if (products.size() < count) {
//						step++;
//						first = 0;
//						count -= products.size();
//					} else {
//						buildCount += build(templatePath, staticPath, model);
//						productDao.clear();
//						productDao.flush();
//						staticPaths.add(staticPath);
//						model.clear();
//						index++;
//						first += products.size();
//						count = SITEMAP_MAX_SIZE;
//					}
//				}  else if (step == 3) {
//					List<Promotion> promotions = promotionDao.findList(first, count, null, null);
//					model.put("promotions", promotions);
//					buildCount += build(templatePath, staticPath, model);
////					promotionDao.clear();
////					promotionDao.flush();
//					staticPaths.add(staticPath);
//					if (promotions.size() < count) {
//						model.put("staticPaths", staticPaths);
//						buildCount += build(sitemapIndexTemplate.getTemplatePath(), sitemapIndexTemplate.getStaticPath(), model);
//						break;
//					} else {
//						model.clear();
//						index++;
//						first += promotions.size();
//						count = SITEMAP_MAX_SIZE;
//					}
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
		return buildCount;
	}

	@Transactional(readOnly = true)
	public int buildOther() {
		int buildCount = 0;
		Template shopCommonJsTemplate = templateService.get("shopCommonJs");
		Template adminCommonJsTemplate = templateService.get("adminCommonJs");
		buildCount += build(shopCommonJsTemplate.getTemplatePath(), shopCommonJsTemplate.getStaticPath());
		buildCount += build(adminCommonJsTemplate.getTemplatePath(), adminCommonJsTemplate.getStaticPath());
		return buildCount;
	}

	@Transactional(readOnly = true)
	public int buildAll() {
		int buildCount = 0;
//		for (int i = 0; i < articleDao.count(); i += 20) {
//			List<Article> articles = articleDao.findList(i, 20, null, null);
//			for (Article article : articles) {
//				buildCount += build(article);
//			}
//			articleDao.clear();
//		}
//		for (int i = 0; i < productDao.count(); i += 20) {
//			List<Product> products = productDao.findList(i, 20, null, null);
//			for (Product product : products) {
//				buildCount += build(product);
//			}
//			productDao.clear();
//		}
		buildIndex();
		buildSitemap();
		buildOther();
		return buildCount;
	}

	@Transactional(readOnly = true)
	public int delete(String staticPath) {
		Assert.hasText(staticPath);

		File staticFile = new File(servletContext.getRealPath(staticPath));
		if (staticFile.exists()) {
			staticFile.delete();
			return 1;
		}
		return 0;
	}



	@Transactional(readOnly = true)
	public int delete(Product product) {
		Assert.notNull(product);

		return delete(product.getPath());
	}


	@Transactional(readOnly = true)
	public int deleteIndex() {
		Template template = templateService.get("index");
		return delete(template.getStaticPath());
	}

	@Transactional(readOnly = true)
	public int deleteOther() {
		int deleteCount = 0;
		Template shopCommonJsTemplate = templateService.get("shopCommonJs");
		Template adminCommonJsTemplate = templateService.get("adminCommonJs");
		deleteCount += delete(shopCommonJsTemplate.getStaticPath());
		deleteCount += delete(adminCommonJsTemplate.getStaticPath());
		return deleteCount;
	}

}