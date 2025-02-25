package s_lab.sichniy_andriy.portier_digital.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.springframework.web.multipart.MultipartFile;

public class ServiceUtils {

    private final static String SERVER_PATH = "/apache-tomcat-10.1.34/webapps";
    private final static String IMAGES_FOLDER = "images";


    static public String uploadImageOnServer(
            MultipartFile file,
            String name,
            String timestamp,
            String ext,
            String folder
    ) throws IOException {
        String PATH_TO_FOLDER = SERVER_PATH + "/" + IMAGES_FOLDER + "/" + folder;
        File dest = new File(PATH_TO_FOLDER);
        System.out.println(dest.getAbsolutePath());
        if (!dest.exists()) {
            dest.mkdirs();
        }

        byte[] bytes = file.getBytes();
        try(InputStream is = new ByteArrayInputStream(bytes)) {
            ImageIO.scanForPlugins();
            BufferedImage rgbImg = ImageIO.read(is);
            writeNewImage(rgbImg, 1260, 750, ext, dest, file.getOriginalFilename(), "large");
            writeNewImage(rgbImg, 844, 600, ext, dest, file.getOriginalFilename(), "middle");
            writeNewImage(rgbImg, 350, 260, ext, dest, file.getOriginalFilename(), "small");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "/" + IMAGES_FOLDER + "/" + folder + "/" + file.getOriginalFilename();
    }

    private static void writeNewImage(
            BufferedImage image,
            int width, int height,
            String ext, File dest,
            String imgName, String type) throws IOException {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = newImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        System.out.println(
                ImageIO.write(
                        newImage, ext, new File(dest.getAbsolutePath() + File.separator + imgName + "_" + type + "." + ext)
                )
        );
    }
}
