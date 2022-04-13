package com.xy.hkxannoeditor.core.annoManager;

import com.xy.hkxannoeditor.Const;
import com.xy.hkxannoeditor.core.ListExecutor.AnnoInserterRemover;
import com.xy.hkxannoeditor.entity.bo.FilePath;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ListIterator;


public class EnemyBrutalizer extends FixManager {

    public EnemyBrutalizer(File root, List<FilePath> filePaths) {
        super(root, filePaths);
        System.out.println("start chaining Skysa light combos");
    }

    @Override
    public void fixAnno() throws IOException {
        ListIterator<FilePath> li = getFilePaths().listIterator();
        while (li.hasNext()) {
            File txt = li.next().txt;
            AnnoInserterRemover fixer = new AnnoInserterRemover(txt, Const.ATTACK_WIN_ANNO, Const.COMBO_ANNO);
            if (fixer.insertAfter()) {
                System.out.println("successfully fixed:" + txt.getName());
            } else {
                li.remove();
                System.out.println("failed to fix:" + txt.getName());
            }
        }
    }

}