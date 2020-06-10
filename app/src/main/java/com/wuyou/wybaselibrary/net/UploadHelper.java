package com.wuyou.wybaselibrary.net;

import android.text.format.DateFormat;
import android.util.Log;

import com.tencent.cos.xml.CosXmlServiceConfig;
import com.tencent.cos.xml.CosXmlSimpleService;
import com.tencent.cos.xml.exception.CosXmlClientException;
import com.tencent.cos.xml.exception.CosXmlServiceException;
import com.tencent.cos.xml.model.object.PutObjectRequest;
import com.tencent.cos.xml.model.object.PutObjectResult;
import com.tencent.qcloud.core.auth.QCloudCredentialProvider;
import com.tencent.qcloud.core.auth.ShortTimeCredentialProvider;
import com.wuyou.wybaselibrary.application.BaseApplication;
import com.wuyou.wybaselibrary.utils.HashUtil;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static com.wuyou.wybaselibrary.Constant.BUCKET;
import static com.wuyou.wybaselibrary.Constant.REGION;
import static com.wuyou.wybaselibrary.Constant.SECERT_KRY;
import static com.wuyou.wybaselibrary.Constant.SECRET_ID;

public class UploadHelper {
    private static final String TAG = "OSSUtil";


    private static CosXmlSimpleService cosXmlSimpleService;

    static {
        // 创建 CosXmlServiceConfig 对象，根据需要修改默认的配置参数
        CosXmlServiceConfig serviceConfig = new CosXmlServiceConfig.Builder()
                .setRegion(REGION)
                .isHttps(true) // 使用 HTTPS 请求, 默认为 HTTP 请求
                .builder();
        QCloudCredentialProvider credentialProvider = new ShortTimeCredentialProvider(SECRET_ID,
                SECERT_KRY, 300);

        cosXmlSimpleService = new CosXmlSimpleService(BaseApplication.getBaseApplicationContext(), serviceConfig, credentialProvider);
    }


    private static String upload(String key, String filePath) {

        PutObjectRequest putObjectRequest = new PutObjectRequest(BUCKET, key, filePath);
        // 设置签名校验 Host，默认校验所有 Header
        Set<String> headerKeys = new HashSet<>();
        headerKeys.add("Host");
        putObjectRequest.setSignParamsAndHeaders(null, headerKeys);

        try {

            PutObjectResult putObjectResult = cosXmlSimpleService.putObject(putObjectRequest);
            Log.e(TAG, "uploadFile: " + putObjectResult.accessUrl);
            return putObjectResult.accessUrl;
        } catch (CosXmlClientException | CosXmlServiceException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 上传普通图片
     *
     * @param path 本地地址
     * @return 服务器地址
     */
    public static String uploadImage(String path) {
        String key = getImageKey(path);
        return upload(key, path);
    }

    /**
     * 上传头像
     *
     * @param path 本地地址
     * @return 服务器地址
     */
    public static String uploadAvatar(String path) {
        String key = getAvatarKey(path);
        return upload(key, path);
    }

    /**
     * 上传文件
     *
     * @param path 本地地址
     * @return 服务器地址
     */
    public static String uploadFile(String path) {
        String key = getFileKey(path);
        return upload(key, path);
    }

    /**
     * 分月存储，避免一个文件夹文件太多
     *
     * @return YYYY-MM
     */
    private static String getDataString() {
        return DateFormat.format("yyyyMM", new Date()).toString();
    }

    //image/201909/wuyou.jpg
    private static String getImageKey(String path) {
        String fileMd5 = HashUtil.getMD5String(new File(path));
        return String.format("image/%s/%s.jpg", getDataString(), fileMd5);

    }

    //avatar/201909/wuyou.jpg
    private static String getAvatarKey(String path) {
        String fileMd5 = HashUtil.getMD5String(new File(path));
        return String.format("avatar/%s/%s.jpg", getDataString(), fileMd5);

    }

    //avatar/201909/wuyou.xx
    private static String getFileKey(String path) {
        String fileMd5 = HashUtil.getMD5String(new File(path));
        String suffix = path.substring(path.lastIndexOf(".") + 1);
        return String.format("file/%s/%s."+suffix, getDataString(), fileMd5);

    }

}
