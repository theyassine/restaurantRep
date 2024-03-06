package org.example.Service;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.example.entities.Recette;
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
                    Paragraph titreLabel = new Paragraph("Titre: ");
                    titreLabel.getFont().setColor(BaseColor.RED);
                    document.add(titreLabel);


                    document.add(new Paragraph(recipe.getTitre()));


                    Paragraph descriptionLabel = new Paragraph("Description: ");
                    descriptionLabel.getFont().setColor(BaseColor.RED);
                    document.add(descriptionLabel);


                    document.add(new Paragraph(recipe.getDescription()));
                    Paragraph ingredientsLabel = new Paragraph("Ingredients: ");
                    ingredientsLabel.getFont().setColor(BaseColor.RED);
                    document.add(ingredientsLabel);


                    String[] ingredientsArray = recipe.getIngredients().split(",");
                    for (String ingredient : ingredientsArray) {
                        // Adding each ingredient on a new line
                        document.add(new Paragraph("  " + ingredient.trim()));
                    }

                    Paragraph stepsLabel = new Paragraph("Steps: ");
                    stepsLabel.getFont().setColor(BaseColor.RED);
                    document.add(stepsLabel);


                    String[] stepsArray = recipe.getEtape().split("\n");
                    for (int i = 0; i < stepsArray.length; i++) {

                        document.add(new Paragraph("  Step " + (i + 1) + ": " + stepsArray[i].trim()));
                    }

                    String imagePath = recipe.getImage();
                    if (imagePath != null && !imagePath.isEmpty()) {
                        Image img = Image.getInstance(imagePath);
                        img.scaleAbsolute(200f, 150f);

                        document.add(img);
                    }

                    document.add(new Paragraph("----------------------------------------------"));
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }


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