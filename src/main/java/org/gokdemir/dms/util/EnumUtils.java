package org.gokdemir.dms.util;

import org.gokdemir.dms.enums.DocumentCategory;
import org.gokdemir.dms.exception.BaseException;
import org.gokdemir.dms.exception.ErrorMessage;
import org.gokdemir.dms.exception.MessageType;

public class EnumUtils {

    private EnumUtils() {} // Private constructor to prevent instantiation

    public static DocumentCategory parseDocumentCategory(String categoryStr) {
        if (categoryStr == null || categoryStr.trim().isEmpty()) {
            throw new BaseException(new ErrorMessage(
                    MessageType.DOCUMENT_CATEGORY_INVALID, "Document category must be provided (GELEN/GIDEN)."));
        }
        try {
            return DocumentCategory.valueOf(categoryStr.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new BaseException(new ErrorMessage(
                    MessageType.DOCUMENT_CATEGORY_INVALID, "Invalid document category provided. Must be GELEN or GIDEN."));
        }
    }
}
