package com.xy.hkxannoeditor.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class ResourcesUtil {

    private final ResourcePatternResolver resourceResolver;

    public ResourcesUtil() {
        this.resourceResolver = new PathMatchingResourcePatternResolver(getClass().getClassLoader());
    }

    /**
     * 返回路径下所有class
     *
     * @param rootPath        根路径
     * @param locationPattern 位置表达式
     * @return
     * @throws IOException
     */
    public List<String> list(String rootPath, String locationPattern) throws IOException {
        Resource[] resources = resourceResolver.getResources("classpath*:" + rootPath + locationPattern + "/**/*.class");
        List<String> resourcePaths = new ArrayList<>();
        for (Resource resource : resources) {
            resourcePaths.add(preserveSubpackageName(resource.getURL(), rootPath));
        }
        return resourcePaths;
    }

    private String preserveSubpackageName(URL url, String rootPath) {
        String path = url.getPath();
        // return path.substring(path.indexOf(rootPath));
        return path.substring(path.indexOf(rootPath), path.lastIndexOf(".class")).replace("/", ".");
    }

    /**
     * 返回包下所有的类
     *
     * @param packagePathList 包名全路径集合
     * @param classWithPath   返回全路径开关 true 自动带上包名 false 只返回类名
     * @return List<String> 包下所有的类
     */
    public static List<String> getPackageClasses(List<String> packagePathList, boolean classWithPath) {
        List<String> result = new ArrayList<>();
        for (String packagePath : packagePathList) {
            System.out.println("start print!");
            System.out.println(packagePath);
            List<String> classNames = getClassName(packagePath);
            String path = classWithPath ? packagePath + "." : "";
            for (String className : classNames) {
                // className:com.example.myFirstProject.enums.SexEnum
                result.add(path + className.substring(className.lastIndexOf(".") + 1));
            }
        }
        return result;
    }

    /**
     * 获取该报名全路径下的所有class全路径集合
     *
     * @param packageName 包名全路径
     * @return
     */
    private static List<String> getClassName(String packageName) {
        String filePath = ClassLoader.getSystemResource("").getPath() + packageName.replace(".", "\\");
        return getClassName(filePath, null);
    }

    /**
     * 获取filePath文件夹下的所有class的全路径集合
     *
     * @param filePath
     * @param className
     * @return
     */
    private static List<String> getClassName(String filePath, List<String> className) {
        List<String> myClassName = new ArrayList<>();
        File file = new File(filePath);
        File[] childFiles = file.listFiles();
        if (childFiles != null) {
            for (File childFile : childFiles) {
                if (childFile.isDirectory()) {
                    // 递归获取该文件夹下的子文件夹里的所有文件
                    myClassName.addAll(getClassName(childFile.getPath(), myClassName));
                } else {
                    String childFilePath = childFile.getPath();
                    // childFilePath:
                    // D:\workspace-git\springbootlearning\target\classes\com\example\myFirstProject\enums\SexEnum.class
                    childFilePath = childFilePath.substring(childFilePath.indexOf("\\classes") + 9, childFilePath.lastIndexOf("."));
                    childFilePath = childFilePath.replace("\\", ".");
                    myClassName.add(childFilePath);
                }
            }
        }
        return myClassName;
    }

    public List<String> listClassNames(String rootPath, String locationPattern, boolean recursion) throws IOException {
        String classPattern = "/*.class";
        if (recursion)
            classPattern = "/**/*.class";

        Resource[] resources = this.resourceResolver.getResources("classpath*:" + rootPath + locationPattern + classPattern);
        List<String> resourcePaths = new ArrayList<>();
        for (Resource resource : resources) {
            resourcePaths.add(preserveSubpackageName(resource.getURL(), rootPath));
        }
        return resourcePaths;
    }
}
