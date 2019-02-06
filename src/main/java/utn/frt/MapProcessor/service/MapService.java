package utn.frt.MapProcessor.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import utn.frt.MapProcessor.exception.ValidationException;
import utn.frt.MapProcessor.model.Point;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class MapService {

    public String process(MultipartFile file, List<Point> points) throws IOException, ValidationException {

        BufferedImage image = ImageIO.read(file.getInputStream());
        image.getGraphics();
        Graphics g = image.getGraphics();
        if (!validatePointList(image, points))
            throw new ValidationException("El punto X,Y debe estar dentro de la imagen");

        int i = 1;
        int ratio = image.getHeight() / 30;
        for (Point p : points) {
            g.setFont(new Font("default", Font.BOLD, 16));
            g.setColor(Color.green);
            if (p.getColor() == null)
                g.drawString("" + i, p.getX(), p.getY());
            else {
                g.setColor(getColor(p.getColor()));
                g.fillOval(p.getX(), p.getY(), ratio, ratio);
            }
            if (p.getName() != null)
                g.drawString("pet", p.getX(), p.getY());
            i++;
        }

        File f = new File("1.jpg");  //output file path
        ImageIO.write(image, "jpg", f);

        return "1.jpg";
    }

    private Color getColor(String color) {
        Color result = Color.BLACK;
        if ("red".equals(color)) {
            result = Color.red;
        } else if ("blue".equals(color)) {
            result = Color.blue;
        } else if ("green".equals(color)) {
            result = Color.green;
        }
        return result;
    }

    private boolean validatePointList(BufferedImage image, List<Point> points) {
        int height = image.getHeight();
        int width = image.getWidth();

        for (Point p : points) {
            if (!validatePoint(width, height, p))
                return false;
        }
        return true;
    }

    private boolean validatePoint(int width, int height, Point p) {
        int x = p.getX();
        int y = p.getY();

        if (x < 0 || y < 0)
            return false;

        return x < width && y < height;
    }
}
