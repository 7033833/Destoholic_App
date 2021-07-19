package com.example.destoholicstudent.util;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCreate {

    public static final String JPEG = ".jpeg";

    @NonNull
    public static String folderName = "Destoholic";

    public  static String CodiceFiscale = "CodiceFiscale";


    @NonNull
    private final File mainFolder;

    public FileCreate(Context context) {
//        mainFolder = new File(context.getFilesDir(), folderName);
        mainFolder = new File(Environment.getExternalStorageDirectory()
                + File.separator + folderName);
        if (!mainFolder.exists()) {
            mainFolder.mkdirs();
        }
    }

    @NonNull
    public String getMainfolder() {
        return mainFolder.getAbsolutePath();
    }

    @NonNull
    public File getDirectoryPath(String directory) {
        final File folder = new File(mainFolder
                + File.separator + directory);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        return folder;
    }

    @Nullable
    public String createPDFFile(String folderName, String fileName, byte[] bytes) {

        final File folder = new File(mainFolder
                + File.separator + folderName);

        if (!folder.exists()) {
            folder.mkdirs();
        }

        File file = new File(folder, fileName + ".pdf");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes);
            fos.flush();
            fos.close();
            return folder.getAbsolutePath();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
