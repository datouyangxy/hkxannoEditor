package com.xy.hkxannoeditor.core;

import com.xy.hkxannoeditor.entity.bo.FilePath;
import com.xy.hkxannoeditor.entity.bo.HkxFile;
import javafx.scene.control.TreeView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.xy.hkxannoeditor.EditorApplication.DUMP_COMMAND;
import static com.xy.hkxannoeditor.EditorApplication.UPDATE_COMMAND;

public abstract class AnnoManager {
    private final File root;
    private final List<HkxFile> hkxFileList = new ArrayList<>();

    protected AnnoManager(File root) {
        this.root = root;
    }

    public File getRoot() {
        return root;
    }

    public List<HkxFile> getHkxFileList() {
        return hkxFileList;
    }

    public void dumpAnno(HkxFile file) {
        try {
            FilePath path = file.getPath();
            String command = String.format(DUMP_COMMAND, path.txt.getPath(), path.hkx.getPath());
            if (Runtime.getRuntime().exec(command).waitFor() == 0) {
                System.out.println("dumped: " + path.txt.getPath().split("\\.")[0] + ".txt");
            }
        } catch (IOException | InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    public void RecDir(File root) {
        File[] fileList = root.listFiles();

        if (fileList == null || fileList.length == 0) {
            System.out.println("Error: No animation found in /animation folder. Dump failed.");
            return;
        }

        for (File file : fileList) {
            if (file.isDirectory()) {
                RecDir(file);
            } else if (file.isFile()) {
                String fileName = file.getName();
                if (fileName.toLowerCase().contains(".hkx")) {
                    FilePath filePath = new FilePath(file, new File(file.getPath().split("\\.")[0] + ".txt"));
                    HkxFile hkxFile = new HkxFile(filePath);
                    hkxFileList.add(hkxFile);
                }
            }
        }
    }

    /**
     * update fixed annos to txt files.
     */
    public void updateAnno() {
        System.out.println("\nupdating annos");
        for (HkxFile file : hkxFileList) {
            try {
                FilePath path = file.getPath();
                File txt = path.txt;
                File hkx = path.hkx;
                String command = String.format(UPDATE_COMMAND, txt.getPath(), hkx.getPath());
                System.out.println("updated: " + hkx.getName());
                if (Runtime.getRuntime().exec(command).waitFor() == 0) {
                }
            } catch (IOException | InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
        System.out.println("update complete");
    }

    public TreeView createFileTree() {

        return null;
    }
}
