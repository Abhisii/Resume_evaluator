package com.resumechecker.resumeevaluator.utility;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Component
public class PdfParserUtil {

    public String extractTextFromPdf(MultipartFile file) throws IOException {
        try (InputStream inputStream = file.getInputStream();
             PDDocument document = PDDocument.load(inputStream)) {

            PDFTextStripper stripper = new PDFTextStripper();
            return stripper.getText(document).toLowerCase();
        } catch (IOException e) {
            throw new IOException("Failed to extract text from PDF", e);
        }
    }

    public boolean containsKeyword(String resumeText, String keyword) {
        // Split the keywords by comma and check if any keyword is found in resume text
        String[] keywords = keyword.toLowerCase().split(",");
        for (String k : keywords) {
            if (resumeText.contains(k.trim())) {
                return true;
            }
        }
        return false;
    }
}