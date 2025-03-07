package s_lab.sichniy_andriy.portier_digital;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.springframework.web.multipart.MultipartFile;

public class ServiceUtils {

    private final static String SERVER_PATH = "/usr/local/tomcat/webapps/resources";
    private final static String IMAGES_FOLDER = "images";


    static public String saveImages(
            MultipartFile file,
            String ext,
            String folder
    ) throws IOException {
        File dest = checkDir(folder);
        byte[] bytes = file.getBytes();
        try(InputStream is = new ByteArrayInputStream(bytes)) {
            BufferedImage rgbImg = ImageIO.read(is);
            writeNewImage(rgbImg, 1260, 750,
                    dest.getAbsolutePath() + File.separator + file.getOriginalFilename() + "_large", ext);
            writeNewImage(rgbImg, 844, 600,
                    dest.getAbsolutePath() + File.separator + file.getOriginalFilename() + "_middle", ext);
            writeNewImage(rgbImg, 350, 260,
                    dest.getAbsolutePath() + File.separator + file.getOriginalFilename() + "_small", ext);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "/" + IMAGES_FOLDER + "/" + folder + "/" + file.getOriginalFilename();
    }

    static public String saveImage(
            MultipartFile file,
            String ext,
            String folder
    ) throws IOException {
        File dest = checkDir(folder);
        byte[] bytes = file.getBytes();

        try(InputStream is = new ByteArrayInputStream(bytes)) {
            BufferedImage rgbImg = ImageIO.read(is);
            writeNewImage(rgbImg, 844, 600,
                    dest.getAbsolutePath() + File.separator + file.getOriginalFilename(), ext);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return "/" + IMAGES_FOLDER + "/" + folder + "/" + file.getOriginalFilename();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static File checkDir(String folder) {
        String PATH_TO_FOLDER = SERVER_PATH + "/" + IMAGES_FOLDER + "/" + folder;
        File dest = new File(PATH_TO_FOLDER);
        System.out.println(dest.getAbsolutePath());
        if (!dest.exists()) {
            dest.mkdirs();
        }
        return dest;
    }

    private static void writeNewImage(
            BufferedImage image,
            int width, int height,
            String fullPath, String ext
    ) throws IOException {
        BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = newImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        System.out.println(ImageIO.write(newImage, ext, new File(fullPath + "." + ext)));
    }

}
