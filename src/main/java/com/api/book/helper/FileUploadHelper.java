package com.api.book.helper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper { 
    //Static path
    // public final String Upload_Dir="D:\\Spring\\bootrestbook\\src\\main\\resources\\static\\image";
        //Dynamic path
        //ClassPathResource -> to get the path of the file in the classpath
    public final String Upload_Dir= new ClassPathResource("static/image/").getFile().getAbsolutePath();
    public FileUploadHelper() throws IOException {  //-> constructor to handle IOException for getFile() method
    }

    //windows => \\, Linux => /

    public boolean uploadFile(MultipartFile file){

        boolean f=false;
        try{

            // InputStream is = file.getInputStream();
            // byte data[] = new byte[is.available()];
            // is.read(data);
            // //write (output)
            // FileOutputStream fos = new FileOutputStream(Upload_Dir+"\\"+file.getOriginalFilename());
            // fos.write(data);
            // fos.flush();
            // fos.close(); -> alternate

            Files.copy(file.getInputStream(), Paths.get(Upload_Dir+File.separator+file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);

            f=true;

        }catch(Exception e){
            e.printStackTrace();
        }

        return f;
    }
}
