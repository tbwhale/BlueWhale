package com.bluewhale.common.util;


import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfCopy;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * PDF操作工具类
 *
 * @author curtin 2020/7/26 10:37 AM
 */
public class PDFUtil {

    /**
     * pdf文件后缀
     */
    private static final String PDF_FILE_SUFFIX = "pdf";
    /**
     * 点
     */
    private static final char DOT = '.';

    /**
     * 获取pdf文件的总页数
     *
     * @param sourceFile 文件路径
     * @return java.lang.Integer
     * @author curtin 2020/7/26 6:21 PM
     */
    public static int getPdfNumberOfPages(String sourceFile) {
        try {
            PdfReader pdfReader = new PdfReader(sourceFile);
            return pdfReader.getNumberOfPages();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 拆分PDF
     * @param
     * @return void
     * @author curtin 2020/7/26 10:53 PM
     */
    public static void splitPDF(String PDFPath) throws Exception {

        String userDir = System.getProperty("user.dir");//user.dir指定了当前的路径
        userDir = userDir + "/pdf/";
        String fileName = "2020-07-26";
        String sourceFile;
        if (StringUtils.isNotBlank(PDFPath)) {
            sourceFile = PDFPath;
        } else {
            sourceFile = userDir + fileName + DOT + PDF_FILE_SUFFIX;
        }

        //System.out.println("sourceFile: " + sourceFile);

        int pdfNumberOfPages = getPdfNumberOfPages(sourceFile);
        for (int i = 1; i <= pdfNumberOfPages; i++) {
            String targetFile = userDir + fileName + "-part-" + i + DOT + PDF_FILE_SUFFIX;
            copyPdf(sourceFile, targetFile, i + "-" + i);
            System.out.println("Split PDF successfully. File path:" + targetFile);
        }
    }


    /**
     * 复制pdf文档
     *
     * @param sourceFile 源文件
     * @param targetFile 目标文件
     * @param ranges     复制规则     "1-7"表示复制1到7页、"8-"表示复制从第八页之后到文档末尾
     */
    public static void copyPdf(String sourceFile, String targetFile, String ranges) throws Exception {
        PdfReader pdfReader = new PdfReader(sourceFile);
        PdfStamper pdfStamper = new PdfStamper(pdfReader, new FileOutputStream(targetFile));
        pdfReader.selectPages(ranges);
        pdfStamper.close();
    }

    /**
     * 多个PDF合并功能
     *
     * @param files    多个PDF的路径
     * @param savePath 生成的新PDF绝对路径
     */
    public static void mergePdfFiles(String[] files, String savePath) {
        if (files.length > 0) {
            try {
                Document document = new Document(new PdfReader(files[0]).getPageSize(1));
                PdfCopy copy = new PdfCopy(document, new FileOutputStream(savePath));
                document.open();
                for (String file : files) {
                    PdfReader reader = new PdfReader(file);
                    int n = reader.getNumberOfPages();
                    for (int j = 1; j <= n; j++) {
                        document.newPage();
                        PdfImportedPage page = copy.getImportedPage(reader, j);
                        copy.addPage(page);
                    }
                }
                document.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 递归获取目录下所有文件
     *
     * @param readPath 文件路径
     * @param files    读取的文件集合
     * @return java.util.List<java.io.File>
     * @author curtin 2020/7/26 9:31 PM
     */
    public static List<File> getFiles(String readPath, List<File> files) {
        File readFile = new File(readPath);
        if (readFile.isDirectory()) {
            File[] subFiles = readFile.listFiles();
            if (subFiles != null) {
                for (File file : subFiles) {
                    if (file.isDirectory()) {
                        getFiles(file.getAbsolutePath(), files);
                    } else {
                        if (PDF_FILE_SUFFIX.equals(StringHelper.unqualify(file.getName()))) {//文件后缀要是pdf文件
                            files.add(file);
                        }
                    }
                }
            }
        }
        return files;
    }

    /**
     * 获取目录下的所有文件(按最后修改时间排序)
     *
     * @param path 文件夹路径
     * @return java.util.List<java.io.File>
     * @author curtin 2020/7/26 9:42 PM
     */
    public static List<File> getFileSortBylastModified(String path) {
        List<File> files = getFiles(path, new ArrayList<>());
        if (files != null && files.size() > 0) {
            Collections.sort(files, new Comparator<File>() {
                @Override
                public int compare(File f1, File f2) {
                    //按修改时间升序 最新的排后面
                    if (f1.lastModified() > f2.lastModified()) {
                        return 1;
                    } else if (f1.lastModified() == f2.lastModified()) {
                        return 0;
                    } else {
                        return -1;
                    }
                }
            });
        }
        return files;
    }

    /**
     * 获取目录下的所有文件(按文件名称排序)
     *
     * @param path 文件夹路径
     * @return java.util.List<java.io.File>
     * @author curtin 2020/7/26 9:42 PM
     */
    public static List<File> getFileSortByFileName(String path) {
        List<File> files = getFiles(path, new ArrayList<>());
        if (files != null && files.size() > 0) {
            Collections.sort(files, new Comparator<File>() {
                @Override
                public int compare(File f1, File f2) {
                    //按文件名排序
                    return f1.getName().compareTo(f2.getName());
                }
            });
        }
        return files;
    }


    public static void main(String[] args) throws Exception {
        String userDir = System.getProperty("user.dir");//user.dir指定了当前的路径
        userDir = userDir + "/pdf/";

//        String sourceFile = "E://TESTPDF/2016-08-24.pdf";
//        String targetFile = "E://TESTPDF/2016-08-24-part1.pdf";
//        String targetFile1 = "E://TESTPDF/2016-08-24-part2.pdf";
//
//        copyPdf(sourceFile, targetFile, "1-10");
//        copyPdf(sourceFile, targetFile1, "11-");
//
//        String[] files = {targetFile, "E://TESTPDF/contents.pdf", targetFile1};
//        mergePdfFiles(files, "E://TESTPDF/2016-08-24-Add.pdf");



        String fileName = "2020-07-26";
        String readPath = userDir;

//        List<File> files = getFiles(readPath,new ArrayList<>());
        List<File> files = getFileSortByFileName(readPath);

        StringBuilder fileNameStr = new StringBuilder();
        for (File listFile : files) {

            String name = listFile.getName();
            //System.out.println(name);
            String qualifiedName = StringHelper.qualifier(name);
            if (!qualifiedName.contains("part")) {
                continue;
            }
            //System.out.println("===="+listFile.getAbsolutePath());//获取文件绝对路径
            fileNameStr.append(listFile.getAbsolutePath()).append(";");
        }
        String mergePdfFilePath = userDir + fileName + "-Add" + DOT + PDF_FILE_SUFFIX;
        mergePdfFiles(fileNameStr.toString().split(";"), mergePdfFilePath);

        System.out.println("Merge PDF successfully. File path:" + mergePdfFilePath);


    }

}
