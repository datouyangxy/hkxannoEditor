package com.xy.hkxannoeditor.core.annoManager;

import com.xy.hkxannoeditor.Const;
import com.xy.hkxannoeditor.core.ListExecutor.AnnoInserterRemover;
import com.xy.hkxannoeditor.entity.bo.FilePath;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Scanner;

public class SimpleReplacer extends FixManager {
    public SimpleReplacer(File root, List<FilePath> filePaths) {
        super(root, filePaths);
        System.out.println("starting replacer");
    }



    @Override
    public void fixAnno() throws IOException {
        Scanner input = new Scanner(System.in);
        ListIterator<FilePath> li = getFilePaths().listIterator();
        System.out.println("input anno keyword for the anno to be replaced");
        String oldAnno = input.next();
        System.out.println("intput replacer anno");
        String newAnno = input.next();
        while (li.hasNext()) {
            File txt = li.next().txt;
            AnnoInserterRemover fixer = new AnnoInserterRemover(txt, oldAnno, Const.COMBO_ANNO);
            if (fixer.replace(newAnno)) {
                System.out.println("successfully fixed:" + txt.getName());
            } else {
                System.out.println("failed to fix:" + txt.getName());
            }
        }
        System.out.println("Sucessfully replaced annos; keep replacing? [Y/N]");
        String command = input.next().toLowerCase(Locale.ROOT);
        if (command.equals("y")) {
            fixAnno();
        } else if (command.equals("n")) {
            return;
        }
    }

    public void batchFixAnno(String[] og, String[] rp) throws IOException {
        for (FilePath filePath : getFilePaths()) {
            File txt = filePath.txt;
            for (int i = 0; i < og.length; i++) {
                AnnoInserterRemover fixer = new AnnoInserterRemover(txt, og[i], Const.COMBO_ANNO);
                if (fixer.replace(rp[i])) {
                    System.out.println("successfully fixed:" + txt.getName());
                } else {
                    System.out.println("failed to fix:" + txt.getName());
                }
            }
        }
    }

}
