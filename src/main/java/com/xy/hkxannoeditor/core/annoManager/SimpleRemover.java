package com.xy.hkxannoeditor.core.annoManager;

import com.xy.hkxannoeditor.Const;
import com.xy.hkxannoeditor.core.ListExecutor.AnnoInserterRemover;
import com.xy.hkxannoeditor.entity.bo.FilePath;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Scanner;

public class SimpleRemover extends FixManager {
    public SimpleRemover(File root, List<FilePath> filePaths) {
        super(root, filePaths);
        System.out.println("start removing anno");
    }


    @Override
    public void fixAnno() throws IOException {
        System.out.println("\ninput anno keyword to be removed:");
        Scanner input = new Scanner(System.in);
        ListIterator<FilePath> li = getFilePaths().listIterator();
        if (input.hasNext()) {
            String kwd = input.next();
            while (li.hasNext()) {
                File txt = li.next().txt;
                AnnoInserterRemover fixer = new AnnoInserterRemover(txt, kwd, Const.COMBO_ANNO);
                if (fixer.remove()) {
                    System.out.println("successfully fixed:" + txt.getName());
                } else {
                    System.out.println("failed to fix:" + txt.getName());
                }
            }
        }
        System.out.println("Sucessfully removed annos; keep removing? [Y/N]");
        String command = input.next().toLowerCase(Locale.ROOT);
        if (command.equals("y")) {
            fixAnno();
        } else if (command.equals("n")) {
            return;
        }
    }

    public void BatchFixAnno(String[] args) throws FileNotFoundException {
        ListIterator<FilePath> li = getFilePaths().listIterator();
        for (String kwd : args) {
            while (li.hasNext()) {
                File txt = li.next().txt;
                AnnoInserterRemover fixer = new AnnoInserterRemover(txt, kwd, Const.COMBO_ANNO);
                if (fixer.remove()) {
                    System.out.println("successfully fixed:" + txt.getName());
                } else {
                    System.out.println("failed to fix:" + txt.getName());
                }
            }
        }
        System.out.println("Sucessfully removed annos.");
    }

}
