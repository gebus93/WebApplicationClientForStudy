package pl.gebickionline.exception;

import com.itextpdf.text.DocumentException;

/**
 * Created by Łukasz on 2015-12-29.
 */
public class PdfPriceListException extends RuntimeException {
    public PdfPriceListException(DocumentException e) {
        super(e);
    }
}
