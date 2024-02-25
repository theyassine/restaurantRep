package services;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import entite.Recette;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;


public class PdfExporter {

    public void exportToPdf(List<Recette> recipes, String outputPath, int targetRecipeId) {
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            // Adding recipes to the PDF
            for (Recette recipe : recipes) {
                if (recipe.getId() == targetRecipeId) {
                    document.add(new Paragraph("Title: " + recipe.getTitre()));
                    document.add(new Paragraph("Description: " + recipe.getDescription()));
                    document.add(new Paragraph("Ingredients: " + recipe.getIngredients()));
                    document.add(new Paragraph("Steps: " + recipe.getEtape()));
                    // Adding image if available
                    String imagePath = recipe.getImage();
                    if (imagePath != null && !imagePath.isEmpty()) {
                        Image img = Image.getInstance(imagePath);
                        document.add(img);
                    }

                    document.add(new Paragraph("----------------------------------------------"));
                    break;  // Stop after exporting the desired recipe
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }

        // Ouvrir automatiquement le PDF généré
        try {
            File file = new File(outputPath);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}