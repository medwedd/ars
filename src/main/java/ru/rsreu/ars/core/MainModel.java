package ru.rsreu.ars.core;

import com.puppycrawl.tools.checkstyle.Checkstyle;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.tutego.jrtf.Rtf;
import javafx.scene.control.CheckBoxTreeItem;

import java.io.*;
import java.util.List;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;

public class MainModel {
    private File file;

    private final static String template = "projects/template1.rtf";
    private static final String checkstyleConfiguration = "projects/PrutzkowConfiguration.xml";

    public void generateReport(Report report) {
        List<ZipEntry> fileEntries = ZIPHandler.getClassesEntry(file);
        report.setListing(ZIPHandler.getDataForTemplate(file, fileEntries));
        writeRtfFile(template, getUnzipDirectory(file.getName()) + ".rtf", report);
        deleteDirectory(new File(getUnzipDirectory(file.getName())));
    }


    public String checkstyle() throws FileNotFoundException, CheckstyleException {
        StringBuilder checkstyleResult = new StringBuilder();

        List<ZipEntry> fileEntries = ZIPHandler.getClassesEntry(file);
        for (ZipEntry entry : fileEntries) {
            if (entry.getName().contains(".java")) {
                String sourceFilePath = getUnzipDirectory(file.getName()) + "\\" + entry.getName();
                checkstyleResult.append(Checkstyle.start(sourceFilePath, checkstyleConfiguration));
            }
        }
        return checkstyleResult.toString();
    }

    private void writeRtfFile(String template, String ouputFileName, Report report) {
        FileInputStream fio = null;
        FileOutputStream fos = null;
        try {
            fio = new FileInputStream(template);
            fos = new FileOutputStream(ouputFileName);
            Rtf.template(fio).inject("Num", report.getNumber()).inject("Student", String.format("%s\n%s", report.getGroup(), report.getOwner()))
                    .inject("Code", report.getCheckstyleResult() + report.getListing()).out(fos);
            fio.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fio != null) {
                    fio.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (null != files) {
                for (File file1 : files) {
                    if (file1.isDirectory()) {
                        deleteDirectory(file1);
                    } else {
                        file1.delete();
                    }
                }
            }
        }
        return (directory.delete());
    }

    public String unzipFile(File file ){
        String unzipDirectory = getUnzipDirectory(file.getName());
        ZIPHandler.unZipIt(file.getAbsolutePath(), unzipDirectory);
        return unzipDirectory;
    }

    private String getUnzipDirectory(String fileName) {
        String[] unzip = fileName.split(Pattern.quote("\\"));
        return unzip[unzip.length - 1].replace(".zip", "");
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
