package utils;


/**
 * Created by ivankhaliavka on 11/23/16.
 */
public class Utils {

    public static String getRelativePathToFileWithClass(String fileName) {
        if (fileName.equals("")) {
            return "";
        }
        String uploadFileName = Utils.class.getResource("/").getFile();
        int indexOfClasses = uploadFileName.lastIndexOf("target");
        uploadFileName = uploadFileName.substring(1, indexOfClasses);
        uploadFileName = uploadFileName.concat(fileName);
        if(isOS("mac")){
            uploadFileName = "/" + uploadFileName.replace("\\", "/");
        }else{
            uploadFileName = uploadFileName.replace("/", "\\");
        }
        return uploadFileName;
    }

    public static void sleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean isOS(String osName) {
        if (System.getProperty("os.name").toLowerCase().contains(osName)) {
            return true;
        } else {
            return false;
        }
    }
}
