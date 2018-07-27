package cn.yunyunhei.legutestdemo;

import android.text.TextUtils;

import net.lingala.zip4j.io.ZipInputStream;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.unzip.UnzipUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by test on 2018/7/26.
 *
 * @author test
 */

public class ZipUtil {

    public static void unZipResCustomDir(File zipFile, String folderPath) throws Exception {
        net.lingala.zip4j.core.ZipFile zFile = new net.lingala.zip4j.core.ZipFile(zipFile);
        File destDir = new File(folderPath);
        if (!destDir.exists()) {
            destDir.mkdir();
        }

        if (zFile.isEncrypted()) {// 设置密码
            zFile.setPassword("legutest".toCharArray());
        }

        List fileHeaderList = zFile.getFileHeaders();
        String firstDirName = "";
        for (int i = 0; i < fileHeaderList.size(); i++) {
            FileHeader fileHeader = (FileHeader) fileHeaderList.get(i);
            if (fileHeader != null) {
                if (fileHeader.isDirectory()) {
//                    outFile.mkdirs();
                    if (i == 0) {
                        firstDirName = fileHeader.getFileName();
                    }
                    continue;
                }
                String fileName = fileHeader.getFileName();
                if (!TextUtils.isEmpty(firstDirName)) {
                    fileName = fileName.replace(firstDirName, "");
                }
                String outFilePath = folderPath + "/" + fileName;
                File outFile = new File(outFilePath);


                File parentDir = outFile.getParentFile();
                if (!parentDir.exists()) {
                    parentDir.mkdirs();
                }
                ZipInputStream is = zFile.getInputStream(fileHeader);
                OutputStream os = new FileOutputStream(outFile);

                int readLen = -1;
                byte[] buff = new byte[4096];

                while ((readLen = is.read(buff)) != -1) {
                    os.write(buff, 0, readLen);
                }

                os.close();
                os = null;
                is.close();
                is = null;

                UnzipUtil.applyFileAttributes(fileHeader, outFile);
            }
        }
    }

}
