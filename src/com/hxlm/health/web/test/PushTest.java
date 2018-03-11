package com.hxlm.health.web.test;

import com.hxlm.health.web.CommonAttributes;
import com.hxlm.health.web.DateEditor;
import com.hxlm.health.web.Setting;
import com.hxlm.health.web.service.DeviceTokenService;
import com.hxlm.health.web.util.SettingUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javapns.back.PushNotificationManager;
import javapns.back.SSLConnectionHelper;
import javapns.data.Device;
import javapns.data.PayLoad;


/**
 * Created by dengyang on 15/11/25.
 */
public class PushTest {

    private static String host = "gateway.sandbox.push.apple.com";
    private static int port = 2195;

    public static void main(String[] args) throws Exception {

//        System.out.println(distance(116.6, 40.08, -0.37, 51.87));

        createExcel();

        Calendar loadCalendar1 = Calendar.getInstance();
        loadCalendar1.setTime(new Date());

        Calendar loadCalendar2 = Calendar.getInstance();
        loadCalendar2.setTime(new Date());
        loadCalendar2.add(Calendar.DAY_OF_MONTH,-2);
        System.out.println(loadCalendar1.getTime());
        System.out.println(loadCalendar2.getTime());
        System.out.println(DateEditor.daysBetween(loadCalendar1.getTime(), loadCalendar2.getTime()));
//        String deviceToken = "2a23762a 34002c81 1581d0fb dca7c56e 4c34fed7 b1cea4ab bff63bf7 2f7fd6be";//iphone手机获取的token
//        //String deviceToken2 = "0afc9bd8 3a386b7a b7e3bf49 3ccb94a2 488833ff 1846fb7d 7609075f c1568680";//iphone手机获取的token
//
//        List<String> deviceTokens = new ArrayList<String>();
//        deviceTokens.add(deviceToken);
//        //deviceTokens.add(deviceToken2);
//
//        String content = "这是一条用来测试的推送消息aaaaaaaaa!";//push的内容
//        String p12File = (new ClassPathResource(CommonAttributes.APS_DEVELOPMENT_PATH).getFile()).toString();//这里是一个.p12格式的文件路径，需要去apple官网申请一个
//        String p12FilePassword = "sihanghui";//此处注意导出的证书密码不能为空因为空密码会报错
//
//        PayLoad payLoad = new PayLoad();
//        payLoad.addAlert(content);//push的内容
//        payLoad.addBadge(1);//应用图标上小红圈上的数值
//        payLoad.addSound("default");//铃音
//
//        //添加字典
//        payLoad.addCustomDictionary("push_type", 1);
//        payLoad.addCustomDictionary("url", "www.baidu.com");
//
//        push2One(p12File, p12FilePassword, deviceToken, content, payLoad);//单个推送

    }

    public static void createExcel(){
        try {
            InputStream is = new FileInputStream("C:\\Users\\zyr\\Documents\\Tencent Files\\948864026\\FileRecv\\乘客信息表模版.xls");
            HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
            hssfWorkbook.setSheetName(0,"北京-->上海");
            HSSFSheet sheet = hssfWorkbook.getSheetAt(0);
            HSSFRow row = sheet.getRow(1);
            HSSFCell cell = row.getCell(2);
            cell.setCellValue("郭老师");

            cell = row.getCell(7);
            cell.setCellValue("110");

            for(int i=0;i<5;i++){
                row = sheet.getRow(8 + i);
                cell = row.getCell(0);
                cell.setCellValue(i + 1);
            }

            row = sheet.getRow(20);
            cell = row.getCell(3);
            cell.setCellValue("精密仪器");

            OutputStream out = new FileOutputStream("C:\\Users\\zyr\\Documents\\Tencent Files\\948864026\\FileRecv\\订单No.15111353792_16乘客信息表.xls");
            hssfWorkbook.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 向单个iphone手机推送消息.
     * @param deviceToken iphone手机获取的token
     * @param p12File .p12格式的文件路径
     * @param p12Pass .p12格式的文件密码
     * @param payLoad CustomDictionary字典组
     * @param content 推送内容
     */
    public static void push2One(String p12File, String p12Pass, String deviceToken, String content, PayLoad payLoad) throws Exception {

            PushNotificationManager pushManager = PushNotificationManager.getInstance();
            pushManager.addDevice("iphone", deviceToken);

            //链接到APNs
            pushManager.initializeConnection(host, port, p12File, p12Pass, SSLConnectionHelper.KEYSTORE_TYPE_PKCS12);

            //开始推送
            Device client = pushManager.getDevice("iphone");
            pushManager.sendNotification(client, payLoad);
            //断开链接
            pushManager.stopConnection();
            pushManager.removeDevice("iphone");
            System.out.println(" iphone push success!! content: " + content);
    }

    /**
     * 计算两个经纬度间的距离
     * @param lng1 经度1
     * @param lat1 纬度1
     * @param lng2 经度2
     * @param lat2 纬度2
     * @return
     */
    public static double distance(double lng1, double lat1, double lng2, double lat2){
        // 根据经纬度拉线计算两机场距离后附加的修正值
        Setting setting = SettingUtils.get();
        Float p_offset = setting.getpOffset();

        double a, b, R;
        R = 6371000; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (lng1 - lng2) * Math.PI / 180.0;
        // 距离
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2))*(1+0);
        return d;
    }



}
