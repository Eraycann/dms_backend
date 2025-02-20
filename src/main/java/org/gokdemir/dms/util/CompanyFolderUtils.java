// src/main/java/org/gokdemir/dms/util/CompanyFolderUtils.java
package org.gokdemir.dms.util;

import org.gokdemir.dms.exception.BaseException;
import org.gokdemir.dms.exception.ErrorMessage;
import org.gokdemir.dms.exception.MessageType;

import java.io.File;

public class CompanyFolderUtils {

    public static String createCompanyFolder(String baseFolderPath, String companyName) {
        String companyFolderPath = baseFolderPath + File.separator + companyName;
        // Önce klasörün varlığını kontrol etmeden oluşturmaya çalışın.
        File companyFolder = new File(companyFolderPath);
        if (!companyFolder.exists()) {
            boolean created = companyFolder.mkdirs();
            if (!created) {
                throw new BaseException(new ErrorMessage(MessageType.FOLDER_CREATION_FAILED, companyName));
            }
        }

        return companyFolderPath;
    }


    public static String renameCompanyFolder(String currentFolderPath, String newFolderPath, String companyName) {
        validateFolderPath(currentFolderPath, companyName);

        if (!renameFolder(currentFolderPath, newFolderPath)) {
            throw new BaseException(new ErrorMessage(MessageType.FOLDER_RENAME_FAILED, companyName));
        }

        return newFolderPath;
    }

    public static void validateFolderPath(String folderPath, String companyName) {
        if (folderPath == null || folderPath.isBlank()) {
            throw new BaseException(new ErrorMessage(MessageType.FOLDER_NOT_FOUND, companyName));
        }

        File folder = new File(folderPath);
        if (!folder.exists()) {
            throw new BaseException(new ErrorMessage(MessageType.FOLDER_NOT_FOUND, companyName));
        }
    }

    public static boolean renameFolder(String currentPath, String newPath) {
        File currentFolder = new File(currentPath);
        File newFolder = new File(newPath);
        return currentFolder.renameTo(newFolder);
    }

    public static String getArchivedFolderPath(String currentFolderPath) {
        File currentFolder = new File(currentFolderPath);
        String parentDir = currentFolder.getParent();
        return parentDir + File.separator + "archived_" + currentFolder.getName();
    }

    public static String removeArchivedPrefix(String currentFolderPath) {
        File currentFolder = new File(currentFolderPath);
        String parentDir = currentFolder.getParent();
        return parentDir + File.separator + currentFolder.getName().replaceFirst("^archived_", "");
    }

    public static String renameCompanyFolderPath(String currentFolderPath, String oldCompanyName, String newCompanyName) {
        // Eski klasör yolunu doğrula
        validateFolderPath(currentFolderPath, oldCompanyName);

        // Yeni klasör yolunu oluştur
        String newFolderPath = currentFolderPath.replace(oldCompanyName, newCompanyName);

        // Eski klasörü yeni isme göre yeniden adlandır
        if (!renameFolder(currentFolderPath, newFolderPath)) {
            throw new BaseException(new ErrorMessage(MessageType.FOLDER_RENAME_FAILED, oldCompanyName));
        }

        return newFolderPath;
    }

}