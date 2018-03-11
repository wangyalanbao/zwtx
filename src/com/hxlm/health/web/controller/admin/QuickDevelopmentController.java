package com.hxlm.health.web.controller.admin;

import com.hxlm.health.web.DateEditor;
import com.hxlm.health.web.Message;
import com.hxlm.health.web.entity.EntityAttribute;
import com.hxlm.health.web.entity.QuickDevelopment;
import com.hxlm.health.web.util.DBHelper;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zyr on 2017/02/20.
 *
 */
@Controller
@RequestMapping("/admin/quickDevelopment")
public class QuickDevelopmentController extends BaseController {

    private static final char UNDERLINE = '_';
    private static String sql = null;
    private static DBHelper db1 = null;
    private static ResultSet ret = null;
    private static List<String> entityList = new ArrayList<String>();
    private static String classPath = "";

    /**
     * 添加
     */
    @RequestMapping(value = "/quick", method = RequestMethod.GET)
    public String quick(ModelMap model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        if(!StringUtils.equals(request.getServerName(), "localhost")){
            model.addAttribute("content","该页面开发人员专用！");
            return ERROR_VIEW;
        }
        classPath = this.getClass().getResource("/").getPath();
        System.out.println(classPath.split("out")[0]);
        // entity文件路径
        String entityPath = classPath.split("out")[0] + "\\src\\com\\hxlm\\health\\web\\entity\\";
        File f = new File(entityPath);

        File fa[] = f.listFiles();
        for (int i = 0; i < fa.length; i++) {
            File fs = fa[i];
            if (!fs.isDirectory() && !StringUtils.equals(fs.getName(), "Sn")) {
                entityList.add(fs.getName().split(".java")[0]);
            }
        }
        model.addAttribute("entityList", entityList);
        return "/admin/quickDevelopment/quick";
    }

    /**
     * 添加
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(QuickDevelopment quickDevelopment, RedirectAttributes redirectAttributes) {
        List<EntityAttribute> entityAttributeList = quickDevelopment.getEntityAttributes();

        // 对象名称
        String entityName = quickDevelopment.getName();
        if(StringUtils.isEmpty(entityName)){
            addFlashMessage(redirectAttributes, Message.warn("对象名称不能为空"));
            return "redirect:quick.jhtml";
        }
        if(entityList.contains(firstLetterToUpper(entityName))){
            addFlashMessage(redirectAttributes, Message.warn("该对象已存在"));
            return "redirect:quick.jhtml";
        }
        // 对象中文名称
        String chineseName = quickDevelopment.getChineseName();

        if(StringUtils.isNotEmpty(entityName)){
            entityName = firstLetterToLower(entityName);
        }
        entityName = StringUtils.trim(entityName);

        addEntity(entityName, chineseName, entityAttributeList);
        addDao(entityName, chineseName, entityAttributeList);
        addDaoImpl(entityName, chineseName, entityAttributeList);
        addService(entityName, chineseName, entityAttributeList);
        addServiceImpl(entityName, chineseName, entityAttributeList);
        addController(entityName, chineseName, entityAttributeList);
        addListFtl(entityName, chineseName, entityAttributeList);
        addAddFtl(entityName, chineseName, entityAttributeList);
        addEditFtl(entityName, chineseName, entityAttributeList);
//        editMainFtl(entityName, chineseName, entityAttributeList);

        if(count("admin:" + entityName) <= 0){
            addAuthority("admin:" + entityName);
        }
        addFlashMessage(redirectAttributes, Message.success("文件生成成功，请重启服务器"));
        return "redirect:quick.jhtml";
    }

    public static void main(String[] args) {


    }

    /**
     * 查看是否已插入权限
     * @param authority
     * @return
     */
    private static int count(String authority){
        sql = "select count(*) from lm_role_authority where role = '1' and authorities = '" +  authority + "'";//SQL语句
        System.out.println("sql===" + sql);
        db1 = new DBHelper(sql);//创建DBHelper对象
        int count = 0;
        try {
            ret = db1.pst.executeQuery();//执行语句，得到结果集
            while (ret.next()) {
                count = ret.getInt(1);
                System.out.println("count==" + count);
            }//显示数据
            ret.close();
            db1.close();//关闭连接
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 向数据库中增加权限
     * @param authority
     * @return
     */
    private static int addAuthority(String authority){
        sql = "insert into lm_role_authority values ("+ 1+", '"+authority+"')";//SQL语句
        System.out.println("sql===" + sql);
        db1 = new DBHelper(sql);//创建DBHelper对象
        int count = 0;
        try {
            db1.pst.execute();//执行语句
            ret.close();
            db1.close();//关闭连接
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }



    /**
     * 添加entity文件
     * @param name 名称
     * @param chineseName 中文名称
     * @param entityAttributeList 属性列表
     */
    private void addEntity(String name, String chineseName,  List<EntityAttribute> entityAttributeList){
        // entity文件路径
        String entityPath = classPath.split("out")[0]  + "\\src\\com\\hxlm\\health\\web\\entity\\";
        // entity模板文件路径
        String mouldPath =  classPath.split("out")[0] + "\\WebRoot\\upload\\quick\\";

        //获得模板文件
        File mouldFile = new File(mouldPath + "EntityMould.java");

        // 创建Dao文件（首字母大写）
        File targetFile = new File(entityPath + firstLetterToUpper(name)  + ".java");

        BufferedReader reader = null;
        //文件写出流
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(mouldFile), "UTF-8"));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile),"UTF-8"));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if(tempString.contains("开始增加属性")){
                    for(EntityAttribute entityAttribute : entityAttributeList){
                        writer.write("\t/** " + entityAttribute.getMemo() +" */");
                        writer.newLine();
                        writer.write("\tprivate " + entityAttribute.getType() + " " + entityAttribute.getName() + ";");
                        writer.newLine();
                        writer.write("");
                        writer.newLine();
                    }
                } else if(tempString.contains("开始增加get,set方法")){
                    for(EntityAttribute entityAttribute : entityAttributeList){
                        // get方法
                        writer.write("\t/**");
                        writer.newLine();
                        writer.write("\t * 获取" + entityAttribute.getMemo());
                        writer.newLine();
                        writer.write("\t * ");
                        writer.newLine();
                        writer.write("\t * @return " + entityAttribute.getMemo());
                        writer.newLine();
                        writer.write("\t */");
                        writer.newLine();
                        writer.write("\t@JsonProperty");
                        writer.newLine();
                        if(StringUtils.equals(entityAttribute.getType(), "String")){
                            if(entityAttribute.getIsRequired() != null && entityAttribute.getIsRequired()){
                                writer.write("\t@NotEmpty");
                                writer.newLine();
                            }
                            if(entityAttribute.getLength() != null && entityAttribute.getLength() > 0){
                                writer.write("\t@Length(max = " + entityAttribute.getLength()+")");
                                writer.newLine();
                            }
                            if(entityAttribute.getIsRequired() != null && entityAttribute.getIsRequired() && entityAttribute.getLength() != null && entityAttribute.getLength() > 0){
                                writer.write("\t@Column(nullable = false, length = "+entityAttribute.getLength()+")");
                                writer.newLine();
                            } else if(entityAttribute.getIsRequired() != null && entityAttribute.getIsRequired() && (entityAttribute.getLength() == null || entityAttribute.getLength() <= 0)){
                                writer.write("\t@Column(nullable = false)");
                                writer.newLine();
                            } else if((entityAttribute.getIsRequired() == null || !entityAttribute.getIsRequired()) && entityAttribute.getLength() != null && entityAttribute.getLength() > 0){
                                writer.write("\t@Column(length = "+entityAttribute.getLength()+")");
                                writer.newLine();
                            }
                        } else if(StringUtils.equals(entityAttribute.getType(), "int") || StringUtils.equals(entityAttribute.getType(), "Integer") ) {
                            writer.write("\t@Min(0)");
                            writer.newLine();
                            if(entityAttribute.getIsRequired() != null && entityAttribute.getIsRequired()){
                                writer.write("\t@Column(nullable = false)");
                                writer.newLine();
                            }
                        } else if(entityList.contains(entityAttribute.getType())){
                            writer.write("\t@ManyToOne(fetch = FetchType.LAZY)");
                            writer.newLine();
                            if(entityAttribute.getIsRequired() != null && entityAttribute.getIsRequired()){
                                writer.write("\t@JoinColumn(nullable = false)");
                                writer.newLine();
                            }
                        } else {
                            if(entityAttribute.getIsRequired() != null && entityAttribute.getIsRequired()){
                                writer.write("\t@Column(nullable = false)");
                                writer.newLine();
                            }
                        }
                        writer.write("\tpublic "+ entityAttribute.getType() +" " + nameToGet(entityAttribute.getName()) +"() {");
                        writer.newLine();
                        writer.write("\t\treturn "+ entityAttribute.getName() +";");
                        writer.newLine();
                        writer.write("\t}");
                        writer.newLine();
                        writer.write("");
                        writer.newLine();

                        // set方法
                        writer.write("\t/**");
                        writer.newLine();
                        writer.write("\t * 设置" + entityAttribute.getMemo());
                        writer.newLine();
                        writer.write("\t * ");
                        writer.newLine();
                        writer.write("\t * @param " + entityAttribute.getName());
                        writer.newLine();
                        writer.write("\t *            " + entityAttribute.getMemo());
                        writer.newLine();
                        writer.write("\t */");
                        writer.newLine();
                        writer.write("\tpublic void " + nameToSet(entityAttribute.getName()) + "("+entityAttribute.getType()+" "+entityAttribute.getName()+") {");
                        writer.newLine();
                        writer.write("\t\tthis."+entityAttribute.getName()+" = "+entityAttribute.getName()+";");
                        writer.newLine();
                        writer.write("\t}");
                        writer.newLine();
                        writer.write("");
                        writer.newLine();
                    }
                } else {
                    tempString = replace(tempString, name, chineseName);
                    writer.write(tempString);
                    writer.newLine();
                }

                writer.flush();
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            }
        }

    }

    /**
     * 添加dao文件
     * @param name 名称
     * @param chineseName 中文名称
     * @param entityAttributeList 属性列表
     */
    private void addDao(String name, String chineseName,  List<EntityAttribute> entityAttributeList){

        System.out.println(classPath.split("out")[0]);
        // dao文件路径
        String entityPath = classPath.split("out")[0] + "\\src\\com\\hxlm\\health\\web\\dao\\";
        // dao模板文件路径
        String mouldPath =  classPath.split("out")[0] + "\\WebRoot\\upload\\quick\\";

        //获得模板文件
        File mouldFile = new File(mouldPath + "DaoMould.java");

        // 创建Dao文件（首字母大写）
        File targetFile = new File(entityPath + firstLetterToUpper(name)  + "Dao.java");

        BufferedReader reader = null;
        //文件写出流
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(mouldFile), "UTF-8"));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile),"UTF-8"));
            String tempString = null;
            // 属性查询逗号链接
            String parameterString = "";
            for(EntityAttribute entityAttribute : entityAttributeList){
                if(entityAttribute.getIsSelect() != null && entityAttribute.getIsSelect()){
                    parameterString += entityAttribute.getType() + " " + entityAttribute.getName() + ", ";
                }
            }
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if(tempString.contains("按条件查询") && parameterString.length() > 0){
                    writer.write("    /**");
                    writer.newLine();
                    writer.write("     * 按条件查询");
                    writer.newLine();
                    for(EntityAttribute entityAttribute : entityAttributeList){
                        if(entityAttribute.getIsSelect() != null && entityAttribute.getIsSelect()){
                            writer.write("     * @param "+entityAttribute.getName()+"");
                            writer.newLine();
                            writer.write("     *          "+entityAttribute.getMemo()+"");
                            writer.newLine();
                        }
                    }
                    writer.write("     * @return");
                    writer.newLine();
                    writer.write(replace("     *          Page<TestMould>", name, chineseName));
                    writer.newLine();
                    writer.write("     */");
                    writer.newLine();
                    writer.write(replace("    Page<TestMould> findPage(" + parameterString + "Pageable pageable);", name, chineseName));
                    writer.newLine();
                } else if(tempString.contains("从这里引入包")){
                    for(EntityAttribute entityAttribute : entityAttributeList){
                        if(entityList.contains(firstLetterToUpper(entityAttribute.getName()))){
                            writer.write("import com.hxlm.health.web.entity."+firstLetterToUpper(entityAttribute.getName())+";");
                            writer.newLine();
                        }
                    }
                } else {
                    tempString = replace(tempString, name, chineseName);
                    writer.write(tempString);
                    writer.newLine();
                }
                writer.flush();
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            }
        }

    }

    /**
     * 添加daoImpl文件
     * @param name 名称
     * @param chineseName 中文名称
     * @param entityAttributeList 属性列表
     */
    private void addDaoImpl(String name, String chineseName, List<EntityAttribute> entityAttributeList){
        
        // DaoImpl文件路径
        String entityPath = classPath.split("out")[0] + "\\src\\com\\hxlm\\health\\web\\dao\\impl\\";
        // DaoImpl模板文件路径
        String mouldPath =  classPath.split("out")[0] + "\\WebRoot\\upload\\quick\\";

        //获得模板文件
        File mouldFile = new File(mouldPath + "DaoImplMould.java");

        // 创建Dao文件（首字母大写）
        File targetFile = new File(entityPath + firstLetterToUpper(name)  + "DaoImpl.java");

        BufferedReader reader = null;
        //文件写出流
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(mouldFile), "UTF-8"));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile),"UTF-8"));
            String tempString = null;
            // 属性查询逗号链接
            String parameterString = "";
            for(EntityAttribute entityAttribute : entityAttributeList){
                if(entityAttribute.getIsSelect() != null && entityAttribute.getIsSelect()){
                    parameterString += entityAttribute.getType() + " " + entityAttribute.getName() + ", ";
                }
            }
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if(tempString.contains("按条件查询") && parameterString.length() > 0){
                    writer.write("    /**");
                    writer.newLine();
                    writer.write("     * 按条件查询");
                    writer.newLine();
                    for(EntityAttribute entityAttribute : entityAttributeList){
                        if(entityAttribute.getIsSelect() != null && entityAttribute.getIsSelect()){
                            writer.write("     * @param "+entityAttribute.getName()+"");
                            writer.newLine();
                            writer.write("     *          "+entityAttribute.getMemo()+"");
                            writer.newLine();
                        }
                    }
                    writer.write("     * @return");
                    writer.newLine();
                    writer.write(replace("     *          Page<TestMould>", name, chineseName));
                    writer.newLine();
                    writer.write("     */");
                    writer.newLine();
                    writer.write(replace("    public Page<TestMould> findPage(" + parameterString + "Pageable pageable) {", name, chineseName));
                    writer.newLine();
                    writer.write("        // 创建安全查询，CriteriaBuilder为安全查询创建工厂");
                    writer.newLine();
                    writer.write("        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();");
                    writer.newLine();
                    writer.write("        // 创建安全查询的对象");
                    writer.newLine();
                    writer.write(replace("        CriteriaQuery<TestMould> criteriaQuery = criteriaBuilder.createQuery(TestMould.class);", name, chineseName));
                    writer.newLine();
                    writer.write("        // Root 定义查询的From子句中能出现的类型，它与SQL查询中的FROM子句类似");
                    writer.newLine();
                    writer.write(replace("        Root<TestMould> root = criteriaQuery.from(TestMould.class);", name, chineseName));
                    writer.newLine();
                    writer.write(replace("        criteriaQuery.select(root);", name, chineseName));
                    writer.newLine();
                    writer.write("        // Predicate 过滤条件");
                    writer.newLine();
                    writer.write("        Predicate restrictions = criteriaBuilder.conjunction();");
                    writer.newLine();
                    writer.write(tempString);
                    writer.newLine();
                    for(EntityAttribute entityAttribute : entityAttributeList){
                        if(entityAttribute.getIsSelect() != null && entityAttribute.getIsSelect()) {
                            if(StringUtils.equals("String", entityAttribute.getType())){
                                writer.write("       if(StringUtils.isNotEmpty("+ entityAttribute.getName() +")){");
                                writer.newLine();
                            } else {
                                writer.write("        if ("+ entityAttribute.getName() +" != null) {");
                                writer.newLine();
                            }
                            writer.write("            // 构建表查询条件");
                            writer.newLine();
                            writer.write("            restrictions = criteriaBuilder.and(restrictions, criteriaBuilder.equal(root.get(\""+ entityAttribute.getName() +"\"), "+ entityAttribute.getName() +"));");
                            writer.newLine();
                            writer.write("        }");
                            writer.newLine();
                        }
                    }

                    writer.write("        criteriaQuery.where(restrictions);");
                    writer.newLine();
                    writer.write("        return super.findPage(criteriaQuery, pageable);");
                    writer.newLine();
                    writer.write("    }");
                    writer.newLine();
                }  else if(tempString.contains("从这里引入包")){
                    for(EntityAttribute entityAttribute : entityAttributeList){
                        if(entityList.contains(firstLetterToUpper(entityAttribute.getName()))){
                            writer.write("import com.hxlm.health.web.entity."+firstLetterToUpper(entityAttribute.getName())+";");
                            writer.newLine();
                        }
                    }
                } else {
                    tempString = replace(tempString, name, chineseName);
                    writer.write(tempString);
                    writer.newLine();
                }
                writer.flush();
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            }
        }

    }

    /**
     * 添加service文件
     * @param name 名称
     * @param chineseName 中文名称
     * @param entityAttributeList 属性列表
     */
    private void addService(String name, String chineseName, List<EntityAttribute> entityAttributeList){
        
        // Service文件路径
        String entityPath = classPath.split("out")[0] + "\\src\\com\\hxlm\\health\\web\\service\\";
        // Service模板文件路径
        String mouldPath =  classPath.split("out")[0] + "\\WebRoot\\upload\\quick\\";

        //获得模板文件
        File mouldFile = new File(mouldPath + "ServiceMould.java");

        // 创建Dao文件（首字母大写）
        File targetFile = new File(entityPath + firstLetterToUpper(name)  + "Service.java");

        BufferedReader reader = null;
        //文件写出流
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(mouldFile), "UTF-8"));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile),"UTF-8"));
            String tempString = null;
            // 属性查询逗号链接
            String parameterString = "";
            for(EntityAttribute entityAttribute : entityAttributeList){
                if(entityAttribute.getIsSelect() != null && entityAttribute.getIsSelect()){
                    parameterString += entityAttribute.getType() + " " + entityAttribute.getName() + ", ";
                }
            }
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if(tempString.contains("按条件查询") && parameterString.length() > 0){
                    writer.write("    /**");
                    writer.newLine();
                    writer.write("     * 按条件查询");
                    writer.newLine();
                    for(EntityAttribute entityAttribute : entityAttributeList){
                        if(entityAttribute.getIsSelect() != null && entityAttribute.getIsSelect()){
                            writer.write("     * @param "+entityAttribute.getName()+"");
                            writer.newLine();
                            writer.write("     *          "+entityAttribute.getMemo()+"");
                            writer.newLine();
                        }
                    }
                    writer.write("     * @return");
                    writer.newLine();
                    writer.write(replace("     *          Page<TestMould>", name, chineseName));
                    writer.newLine();
                    writer.write("     */");
                    writer.newLine();
                    writer.write(replace("    Page<TestMould> findPage(" + parameterString + "Pageable pageable);", name, chineseName));
                    writer.newLine();
                }  else if(tempString.contains("从这里引入包")){
                    for(EntityAttribute entityAttribute : entityAttributeList){
                        if(entityList.contains(firstLetterToUpper(entityAttribute.getName()))){
                            writer.write("import com.hxlm.health.web.entity."+firstLetterToUpper(entityAttribute.getName())+";");
                            writer.newLine();
                        }
                    }
                } else {
                    tempString = replace(tempString, name, chineseName);
                    writer.write(tempString);
                    writer.newLine();
                }
                writer.flush();
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            }
        }

    }

    /**
     * 添加serviceImpl文件
     * @param name 名称
     * @param chineseName 中文名称
     * @param entityAttributeList 属性列表
     */
    private void addServiceImpl(String name, String chineseName, List<EntityAttribute> entityAttributeList){

        // ServiceImpl文件路径
        String entityPath = classPath.split("out")[0] + "\\src\\com\\hxlm\\health\\web\\service\\impl\\";
        // ServiceImpl模板文件路径
        String mouldPath =  classPath.split("out")[0] + "\\WebRoot\\upload\\quick\\";

        //获得模板文件
        File mouldFile = new File(mouldPath + "ServiceImplMould.java");

        // 创建Dao文件（首字母大写）
        File targetFile = new File(entityPath + firstLetterToUpper(name)  + "ServiceImpl.java");

        BufferedReader reader = null;
        //文件写出流
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(mouldFile), "UTF-8"));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile),"UTF-8"));
            String tempString = null;
            // 属性类型和名称逗号链接
            String parameterString = "";
            // 属性名称逗号链接
            String nameString = "";
            for(EntityAttribute entityAttribute : entityAttributeList){
                if(entityAttribute.getIsSelect() != null && entityAttribute.getIsSelect()){
                    parameterString += entityAttribute.getType() + " " + entityAttribute.getName() + ", ";
                    nameString += entityAttribute.getName() + ", ";
                }
            }
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if(tempString.contains("按条件查询") && parameterString.length() > 0){
                    writer.write("    /**");
                    writer.newLine();
                    writer.write("     * 按条件查询");
                    writer.newLine();
                    for(EntityAttribute entityAttribute : entityAttributeList){
                        if(entityAttribute.getIsSelect() != null && entityAttribute.getIsSelect()){
                            writer.write("     * @param "+entityAttribute.getName()+"");
                            writer.newLine();
                            writer.write("     *          "+entityAttribute.getMemo()+"");
                            writer.newLine();
                        }
                    }
                    writer.write("     * @return");
                    writer.newLine();
                    writer.write(replace("     *          Page<TestMould>", name, chineseName));
                    writer.newLine();
                    writer.write("     */");
                    writer.newLine();
                    writer.write("    @Transactional(readOnly = true)");
                    writer.newLine();
                    writer.write(replace("   public Page<TestMould> findPage(" + parameterString + "Pageable pageable){", name, chineseName));
                    writer.newLine();
                    writer.write(replace("        return testMouldDao.findPage("+ nameString +"pageable);", name, chineseName));
                    writer.newLine();
                    writer.write("    }");
                    writer.newLine();
                }  else if(tempString.contains("从这里引入包")){
                    for(EntityAttribute entityAttribute : entityAttributeList){
                        if(entityList.contains(firstLetterToUpper(entityAttribute.getName()))){
                            writer.write("import com.hxlm.health.web.entity."+firstLetterToUpper(entityAttribute.getName())+";");
                            writer.newLine();
                        }
                    }
                } else {
                    tempString = replace(tempString, name, chineseName);
                    writer.write(tempString);
                    writer.newLine();
                }
                writer.flush();
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            }
        }

    }

    /**
     * 添加controller文件
     * @param name 名称
     * @param chineseName 中文名称
     * @param entityAttributeList 属性列表
     */
    private void addController(String name, String chineseName, List<EntityAttribute> entityAttributeList){

        System.out.println(classPath.split("out")[0]);
        // Controller文件路径
        String entityPath = classPath.split("out")[0] + "\\src\\com\\hxlm\\health\\web\\controller\\admin\\";
        // Controller模板文件路径
        String mouldPath =  classPath.split("out")[0] + "\\WebRoot\\upload\\quick\\";

        //获得模板文件
        File mouldFile = new File(mouldPath + "ControllerMould.java");

        // 创建Dao文件（首字母大写）
        File targetFile = new File(entityPath + firstLetterToUpper(name)  + "Controller.java");

        BufferedReader reader = null;
        //文件写出流
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(mouldFile), "UTF-8"));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile),"UTF-8"));
            String tempString = null;
            // 属性类型和名称逗号链接
            String parameterString = "";
            // 属性名称逗号链接
            String nameString = "";
            for(EntityAttribute entityAttribute : entityAttributeList){
                if(entityAttribute.getIsSelect() != null && entityAttribute.getIsSelect()){
                    if(entityList.contains(firstLetterToUpper(entityAttribute.getName()))){
                        parameterString += "Long" + " " + entityAttribute.getName() + "Id, ";
                    } else {
                        parameterString += entityAttribute.getType() + " " + entityAttribute.getName() + ", ";
                    }
                    nameString += entityAttribute.getName() + ", ";
                }
            }
            // 属性类型名称逗号链接

            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if(tempString.contains("从这里注入")){
                    for(EntityAttribute entityAttribute : entityAttributeList){
                        if(entityList.contains(firstLetterToUpper(entityAttribute.getName()))){
                            writer.write("\t@Resource(name = \""+firstLetterToLower(entityAttribute.getName())+"ServiceImpl\")");
                            writer.newLine();
                            writer.write("\tprivate "+firstLetterToUpper(entityAttribute.getName())+"Service "+firstLetterToLower(entityAttribute.getName())+"Service;");
                            writer.newLine();
                        }
                    }

                }  else if(tempString.contains("从这里引入包")){
                    for(EntityAttribute entityAttribute : entityAttributeList){
                        if(entityList.contains(firstLetterToUpper(entityAttribute.getName()))){
                            writer.write("import com.hxlm.health.web.entity."+firstLetterToUpper(entityAttribute.getName())+";");
                            writer.newLine();
                            writer.write("import com.hxlm.health.web.service."+firstLetterToUpper(entityAttribute.getName())+"Service;");
                            writer.newLine();
                        }
                    }
                } else if(tempString.contains("Pageable pageable")){
                    tempString = tempString.replace("Pageable pageable", parameterString + "Pageable pageable");
                    writer.write(tempString);
                    writer.newLine();
                    continue;
                } else if(tempString.contains("筛选条件放入model")){
                    for(EntityAttribute entityAttribute : entityAttributeList){
                        if(entityAttribute.getIsSelect() != null && entityAttribute.getIsSelect()){
                            if(entityList.contains(firstLetterToUpper(entityAttribute.getName()))){
                                writer.write("        "+ firstLetterToUpper(entityAttribute.getName()) +" "+firstLetterToLower(entityAttribute.getName())
                                        + " = "+ firstLetterToLower(entityAttribute.getName()) +"Service.find("+ firstLetterToLower(entityAttribute.getName()) +"Id);");
                                writer.newLine();
                                writer.write("        model.addAttribute(\""+ firstLetterToLower(entityAttribute.getName()) +"\", "+ firstLetterToLower(entityAttribute.getName()) +");");
                                writer.newLine();
                            } else {
                                writer.write("        model.addAttribute(\""+firstLetterToLower(entityAttribute.getName())+"\", "+firstLetterToLower(entityAttribute.getName())+");");
                                writer.newLine();
                            }
                        }
                    }
                } else if(tempString.contains("查询结果放入page")){
                    writer.write(replace(tempString, name, chineseName).replace("pageable", nameString + "pageable"));
                    writer.newLine();
                    continue;
                } else if(tempString.contains("外键数据查询")){
                    for(EntityAttribute entityAttribute : entityAttributeList) {
                        if (entityList.contains(firstLetterToUpper(entityAttribute.getName()))) {
                            writer.write("        model.addAttribute(\""+firstLetterToLower(entityAttribute.getName()) +"List\", "+firstLetterToLower(entityAttribute.getName()) +"Service.findAll());");
                            writer.newLine();
                        }
                    }
                } else if(tempString.contains("获取外键对象")){
                    for(EntityAttribute entityAttribute : entityAttributeList) {
                        if (entityList.contains(firstLetterToUpper(entityAttribute.getName()))) {
                            writer.write("        "+ firstLetterToUpper(entityAttribute.getName()) +" "+firstLetterToLower(entityAttribute.getName())
                                    + " = "+ firstLetterToLower(entityAttribute.getName()) +"Service.find(Long.valueOf(request.getParameter(\""+ firstLetterToLower(entityAttribute.getName()) +"Id\")));");
                            writer.newLine();
                            writer.write(replace("        testMould.set"+firstLetterToUpper(entityAttribute.getName())+"("+firstLetterToLower(entityAttribute.getName())+");", name, chineseName));
                            writer.newLine();
                        }
                    }
                } else {
                    tempString = replace(tempString, name, chineseName);
                    writer.write(tempString);
                    writer.newLine();
                }
                writer.flush();
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            }
        }

    }

    /**
     * 添加list.ftl文件
     * @param name 名称
     * @param chineseName 中文名称
     * @param entityAttributeList 属性列表
     */
    private void addListFtl(String name, String chineseName,  List<EntityAttribute> entityAttributeList){

        System.out.println(classPath.split("out")[0]);
        // ftl文件路径
        String entityPath = classPath.split("out")[0] + "\\WebRoot\\WEB-INF\\template\\admin\\" + name + "\\";
        File path = new File(entityPath );
        if(!path.exists()){
            path.mkdirs();
        }
        // entity模板文件路径
        String mouldPath =  classPath.split("out")[0] + "\\WebRoot\\upload\\quick\\";

        //获得模板文件
        File mouldFile = new File(mouldPath + "listMould.ftl");

        // 创建list文件
        File targetFile = new File(entityPath + "list.ftl");

        BufferedReader reader = null;
        //文件写出流
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(mouldFile), "UTF-8"));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile),"UTF-8"));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if(tempString.contains("开始增加标题")){
                    for(EntityAttribute entityAttribute : entityAttributeList){
                        writer.write("            <th>");
                        writer.newLine();
                        writer.write("                <a href='javascript:;' class='sort' name='" + entityAttribute.getName() + "'>" + entityAttribute.getMemo() +"</a>");
                        writer.newLine();
                        writer.write("            </th>");
                        writer.newLine();
                    }
                } else if(tempString.contains("开始增加记录")){
                    for(EntityAttribute entityAttribute : entityAttributeList){
                        writer.write("            <td>");
                        writer.newLine();
                        writer.write("            ${"+ name +"."+ entityAttribute.getName()+"}");
                        writer.newLine();
                        writer.write("            </td>");
                        writer.newLine();
                    }
                } else {
                    tempString = replace(tempString, name, chineseName);
                    writer.write(tempString);
                    writer.newLine();
                }

                writer.flush();
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            }
        }

    }

    /**
     * 添加add.ftl文件
     * @param name 名称
     * @param chineseName 中文名称
     * @param entityAttributeList 属性列表
     */
    private void addAddFtl(String name, String chineseName,  List<EntityAttribute> entityAttributeList){

        System.out.println(classPath.split("out")[0]);
        // ftl文件路径
        String entityPath = classPath.split("out")[0] + "\\WebRoot\\WEB-INF\\template\\admin\\" + name + "\\";
        File path = new File(entityPath );
        if(!path.exists()){
            path.mkdirs();
        }
        // entity模板文件路径
        String mouldPath =  classPath.split("out")[0] + "\\WebRoot\\upload\\quick\\";

        //获得模板文件
        File mouldFile = new File(mouldPath + "addMould.ftl");

        // 创建list文件
        File targetFile = new File(entityPath + "add.ftl");

        BufferedReader reader = null;
        //文件写出流
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(mouldFile), "UTF-8"));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile),"UTF-8"));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if(tempString.contains("表单验证")){
                    writer.write(tempString);
                    writer.newLine();
                    writer.write("            $inputForm.validate({");
                    writer.newLine();
                    writer.write("                rules: {");
                    writer.newLine();
                    for(EntityAttribute entityAttribute_validate : entityAttributeList){
                        if(entityAttribute_validate.getIsRequired() != null && entityAttribute_validate.getIsRequired()){
                            writer.write("                    "+entityAttribute_validate.getName()+":\"required\",");
                            writer.newLine();
                        }
                    }
                    writer.write("                }");
                    writer.newLine();
                    writer.write("            });");
                    writer.newLine();

                } else if(tempString.contains("属性赋值")){
                    for(EntityAttribute entityAttribute : entityAttributeList){
                        writer.write("        <tr>");
                        writer.newLine();
                        writer.write("            <th>");
                        writer.newLine();
                        if(entityAttribute.getIsRequired() != null && entityAttribute.getIsRequired()){
                            writer.write("                <span class=\"requiredField\">*</span>");
                            writer.newLine();
                        }
                        writer.write("                " + entityAttribute.getMemo() + ":");
                        writer.newLine();
                        writer.write("            </th>");
                        writer.newLine();
                        writer.write("            <td>");
                        writer.newLine();
                        if(StringUtils.equals(entityAttribute.getType().trim(), "boolean")){
                            writer.write("                <input type='checkbox' name='"+ entityAttribute.getName() + "' value='true'  />");
                            writer.newLine();
                            writer.write("                <input type='hidden' name='_"+entityAttribute.getName()+"' value='false'  />");
                            writer.newLine();
                        } else {
                            writer.write("                <input type='text' id='" + entityAttribute.getName() + "' name='" + entityAttribute.getName() + "' class='text' maxlength='200' />");
                            writer.newLine();
                        }
                        writer.write("            </td>");
                        writer.newLine();
                        writer.write("        </tr>");
                        writer.newLine();
                    }
                } else {
                    tempString = replace(tempString, name, chineseName);
                    writer.write(tempString);
                    writer.newLine();
                }

                writer.flush();
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            }
        }

    }

    /**
     * 添加edit.ftl文件
     * @param name 名称
     * @param chineseName 中文名称
     * @param entityAttributeList 属性列表
     */
    private void addEditFtl(String name, String chineseName,  List<EntityAttribute> entityAttributeList){

        System.out.println(classPath.split("out")[0]);
        // ftl文件路径
        String entityPath = classPath.split("out")[0] + "\\WebRoot\\WEB-INF\\template\\admin\\" + name + "\\";
        File path = new File(entityPath );
        if(!path.exists()){
            path.mkdirs();
        }
        // entity模板文件路径
        String mouldPath =  classPath.split("out")[0] + "\\WebRoot\\upload\\quick\\";

        //获得模板文件
        File mouldFile = new File(mouldPath + "editMould.ftl");

        // 创建list文件
        File targetFile = new File(entityPath + "edit.ftl");

        BufferedReader reader = null;
        //文件写出流
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(mouldFile), "UTF-8"));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile),"UTF-8"));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                if(tempString.contains("表单验证")){
                    writer.write(tempString);
                    writer.newLine();
                    writer.write("            $inputForm.validate({");
                    writer.newLine();
                    writer.write("                rules: {");
                    writer.newLine();
                    for(EntityAttribute entityAttribute_validate : entityAttributeList){
                        if(entityAttribute_validate.getIsRequired() != null && entityAttribute_validate.getIsRequired()){
                            writer.write("                    "+entityAttribute_validate.getName()+":\"required\",");
                            writer.newLine();
                        }
                    }
                    writer.write("                }");
                    writer.newLine();
                    writer.write("            });");
                    writer.newLine();

                } else if(tempString.contains("属性赋值")){
                    for(EntityAttribute entityAttribute : entityAttributeList){
                        writer.write("        <tr>");
                        writer.newLine();
                        writer.write("            <th>");
                        writer.newLine();
                        writer.write("                " + entityAttribute.getMemo() + ":");
                        writer.newLine();
                        writer.write("            </th>");
                        writer.newLine();
                        writer.write("            <td>");
                        writer.newLine();
                        if(StringUtils.equals(entityAttribute.getType().trim(), "boolean")){
                            writer.write("                <input type='checkbox' name='"+entityAttribute.getName()+"' value='true'  [#if "+name+"."+entityAttribute.getName()+"]checked='checked' [/#if]>");
                            writer.newLine();
                            writer.write("                <input type='hidden' name='_"+entityAttribute.getName()+"' value='false'  />");
                            writer.newLine();
                        } else {
                            writer.write("                <input type='text' id='" + entityAttribute.getName() + "' name='" + entityAttribute.getName() + "' class='text' value='${" + name +"." + entityAttribute.getName() + "}' maxlength='200' />");
                            writer.newLine();
                        }
                        writer.write("            </td>");
                        writer.newLine();
                        writer.write("        </tr>");
                        writer.newLine();
                    }
                } else {
                    tempString = replace(tempString, name, chineseName);
                    writer.write(tempString);
                    writer.newLine();
                }

                writer.flush();
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            }
        }

    }

    /**
     * 修改main.ftl文件
     * @param name 名称
     * @param chineseName 中文名称
     * @param entityAttributeList 属性列表
     */
    private void editMainFtl(String name, String chineseName,  List<EntityAttribute> entityAttributeList){

        System.out.println(classPath.split("out")[0]);
        // ftl文件路径
        String entityPath = classPath.split("out")[0] + "\\WebRoot\\WEB-INF\\template\\admin\\common\\";

        // entity模板文件路径
        String mouldPath =  classPath.split("out")[0] + "\\WebRoot\\WEB-INF\\template\\admin\\common\\";

        //获得模板文件
        File mouldFile = new File(mouldPath + "main.ftl");

        // 创建main文件
        File targetFile = new File(entityPath + "main1.ftl");

        BufferedReader reader = null;
        //文件写出流
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(mouldFile), "UTF-8"));
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile),"UTF-8"));
            String tempString = null;
            // 一次读入一行，直到读入null为文件结束
            boolean flag = false;
            while ((tempString = reader.readLine()) != null) {
                if(tempString.contains("admin:&&&&&") && !tempString.contains("admin:"+name+"")){
                    tempString = tempString.replace("\"admin:&&&&&\"", "\"admin:"+name+"\", \"admin:&&&&&\"");
                    flag = true;
                } else if(tempString.contains("从这里添加菜单") && flag){
                    writer.write("                [@shiro.hasPermission name=\"admin:"+name+"\"]");
                    writer.newLine();
                    writer.write("                    <dd>");
                    writer.newLine();
                    writer.write("                        <a href=\"../"+name+"/list.jhtml\" target=\"iframe\">"+chineseName+"</a>");
                    writer.newLine();
                    writer.write("                    </dd>");
                    writer.newLine();
                    writer.write("                [/@shiro.hasPermission]");
                    writer.newLine();
                }
                writer.write(tempString);
                writer.newLine();
                writer.flush();
            }
            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e1) {
                }
            }
        }
        mouldFile.delete();
        File newFile = new File(targetFile.getParent() + File.separator + "main.ftl");
        targetFile.renameTo(newFile);
    }


    /**
     * 替换字符串内容
     * @param tempString 行内容
     * @param name  目标名称
     * @param chineseName 目标名称中文说明
     * @return
     */
    private static String replace(String tempString, String name, String chineseName){
        if(tempString.length() > 1){
            // Class名（首字母大写）
            String fileName = firstLetterToUpper(name);

            // 驼峰单词（首字母小写）
            String camelName = name.substring(0,1).toLowerCase()+name.substring(1);

            // 下划线单词
            String underlineName = camelToUnderline(camelName);

            if(tempString.contains("快捷模板")){
                // 替换中文名
                tempString = tempString.replace("快捷模板", chineseName);
            }
            if(tempString.contains("test_mould")){
                // 替换表名
                tempString = tempString.replace("test_mould", underlineName);
            }
            if(tempString.contains("TestMould")){
                // 替换Class名
                tempString = tempString.replace("TestMould", fileName);
            }
            if(tempString.contains("testMould")){
                // 替换Class名
                tempString = tempString.replace("testMould", camelName);
            }
            if(tempString.contains("testPerson")){
                // 替换作者名
                tempString = tempString.replace("testPerson", getCurrentRunningServerComputerName());
            }
            if(tempString.contains("createTime")){
                // 替换作者名
                tempString = tempString.replace("createTime", DateEditor.format(new Date()));
            }
        }
        return tempString;
    }

    /**
     * 驼峰格式字符串转换为下划线格式字符串
     *
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }


    /**
     * 获取get名称
     *
     * @param name
     * @return
     */
    public static String nameToGet(String name) {
        // 驼峰格式字符串
        name = underlineToCamel(name);
        // 首字母大写
        name = firstLetterToUpper(name);
        return "get" + name;
    }

    /**
     * 获取get名称
     *
     * @param name
     * @return
     */
    public static String nameToSet(String name) {
        // 驼峰格式字符串
        name = underlineToCamel(name);
        // 首字母大写
        name = firstLetterToUpper(name);
        return "set" + name;
    }

    /**
     * 下划线格式字符串转换为驼峰格式字符串
     *
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 获取计算机名称
     * @return
     */
    private static String getCurrentRunningServerComputerName () {
        // Windows
        String computerName = System.getenv().get("COMPUTERNAME");

        if (computerName == null) {
            // Linux
            String tempHostName = System.getenv().get("HOSTNAME");
            if (tempHostName != null && tempHostName.split("\\.").length > 0) {
                computerName = tempHostName.split("\\.")[0];
            } else {
                computerName = tempHostName;
            }
        }
        return computerName;
    }

    /**
     * 首字母大写
     * @param word
     * @return
     */
    private static String firstLetterToUpper(String word){
        if(StringUtils.isEmpty(word)){
            return word;
        } else {
            return word.substring(0,1).toUpperCase()+word.substring(1);
        }
    }

    /**
     * 首字母小写
     * @param word
     * @return
     */
    private static String firstLetterToLower(String word){
        if(StringUtils.isEmpty(word)){
            return word;
        } else {
            return word.substring(0,1).toLowerCase()+word.substring(1);
        }
    }

}
