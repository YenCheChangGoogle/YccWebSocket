package YccStudio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class YccMavenCheck {
    static int seq=0;
    
    public static boolean toDo(java.io.File dir) {
        
        try {
            java.io.File[] fs=dir.listFiles();
            for(java.io.File f:fs) {
                
                if(f.getName().equals(".locks") || f.getName().equals(".cache") || f.getAbsolutePath().indexOf(".locks")!=-1 || f.getAbsolutePath().indexOf(".cache")!=-1) {
                    continue;
                }
                
                if(f.isDirectory()) {
                    
                    boolean noDir=true; //此目錄內是否無子目錄
                    java.io.File ff[]=f.listFiles();
                    for(java.io.File fff:ff) {
                        if(fff.isDirectory()) {
                            noDir=false;
                            break;
                        }
                    }
                    
                    long theFileSize=0;
                    boolean isOK=false;
                    String theFileName="";
                    String theFilePath="";
                    
                    boolean isValidPom=false;
                    boolean isValidJar=false;
                    if(noDir) { //此目錄內是否無子目錄
                        int check=0;
                        for(java.io.File fff:ff) {
                            
                            //boolean b=false;
                            //if(f.getAbsolutePath().equals("C:/Users/016356/.m2/repository/com/mchange/mchange-commons-java/0.2.11")) {
                            //    System.out.println("此下載的偵錯 "+f.getAbsolutePath());
                            //    b=true;
                            //}
                            
                            if(fff.getName().substring(fff.getName().length()-4).equals(".pom")) {
                                if(isValidPom) System.out.println("[警告] 有兩個以上的POM檔案"+fff.getAbsolutePath());
                                
                                java.io.FileInputStream fis=new java.io.FileInputStream(fff);
                                java.io.InputStreamReader r=new java.io.InputStreamReader(fis);
                                java.io.BufferedReader bis=new java.io.BufferedReader(r, 8192);
                                while(true) {
                                    String line=null;
                                    try { line=bis.readLine(); }catch(Exception ex) { break; }
                                    if(line==null) break;
                                
                                    //if(fff.getName().equals("calendar-2.1.0.pom")) {
                                    //    System.out.println("##########");
                                    //    System.out.println(line);
                                    //    System.out.println("##########");
                                    //}
                                    
                                    if(line.indexOf("<project")!=-1) {
                                        isValidPom=true;
                                        break;
                                    }
                                }
                                bis.close();
                                r.close();
                                fis.close();
                                                                
                                if(isValidPom) check++;
                                
                                //if(b) System.out.println(fff.getName()+" "+check);
                            }
                            else if(fff.getName().substring(fff.getName().length()-4).equals(".jar")) {
                                if(fff.getName().indexOf("source")==-1 && (fff.getName().indexOf("javadoc")==-1)) {
                                    if(isValidJar) System.out.println("[警告] 有兩個以上的JAR檔案"+fff.getAbsolutePath());                                
                                    isValidJar=isValidZip(fff.getAbsolutePath());
                                    if(isValidJar) check++;                                    
                                }
                                else if(!isValidZip(fff.getAbsolutePath())){
                                    fff.delete(); //刪掉錯誤的JAR
                                    System.out.println("[執行] 刪掉錯誤的檔案"+fff.getAbsolutePath());
                                }
                                //if(b) System.out.println(fff.getName()+" "+check+" "+isValidJar);
                            }
                            
                            if(fff.getName().indexOf(".lastUpdated")!=-1 || fff.getName().indexOf(".sha1")!=-1 || fff.getName().indexOf(".properties")!=-1) {
                                fff.delete(); //刪掉錯誤的檔案
                                System.out.println("[執行] 刪掉錯誤的檔案"+fff.getAbsolutePath());
                            }
                            else {
                                
                            }
                            
                            if(check==2) {
                                isOK=true;
                                break;
                            }
                            else {

                            }
                        }
                        
                        //if(check==1 && isValidPom) { isOK=true; break; }
                        //else if(check==1 && isValidJar) { System.out.println("[警告] check="+check+" isValidPom="+isValidPom+", isValidJar="+isValidJar+" "+f.getAbsolutePath());  }
                        //else if(isValidPom && isValidJar) {}
                        //else System.out.println("[警告] check="+check+" isValidPom="+isValidPom+", isValidJar="+isValidJar+" "+f.getAbsolutePath());
                    }
                    else {
                        toDo(f);
                    }
                    
                    if(isOK) {
                        //System.out.println("此下載無問題的"+theFilePath+" 大小是"+theFileSize);
                    }
                    else {
                        //此目錄內是否無子目錄
                        if(noDir) {                            
                            if(
                            f.getName().equals(".locks") || 
                            f.getName().equals(".cache") || 
                            f.getAbsolutePath().indexOf(".locks")!=-1 ||
                            f.getAbsolutePath().indexOf(".cache")!=-1
                            ) { }
                            else {
                                String[]entries = f.list();
                                boolean removeDir=false;
                                for(String s: entries){
                                    java.io.File currentFile = new java.io.File(f.getPath(), s);
                                    
                                    if(!isValidPom && !isValidJar) {
                                        System.out.println("("+(seq++)+") 此下載是有問題的"+f.getAbsolutePath()+" isValidPom="+isValidPom+" isValidJar="+isValidJar);
                                        currentFile.delete(); //沒有有效的POM與JAR的時候
                                        removeDir=true;
                                    }
                                }
                                if(removeDir) {
                                    f.delete();
                                }
                            }
                        }
                        else {
                            
                        }
                    }
                }
                else if(f.isFile()) {
                    
                }
                
            }
        }
        catch(Exception e) {
            return false;
        }
    
        return true;
    }
    
    //檢驗是否是有效的壓縮檔案
    public static boolean isValidZip(String zipFilePath) {
        FileInputStream fis;
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                int len;
                while ((len = zis.read(buffer)) > 0) {
                
                }                
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();
            fis.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }    
    
    //解壓縮
    public static void unzip(String zipFilePath, String destDir) {
        File dir = new File(destDir);
        // create output directory if it doesn't exist
        if(!dir.exists()) dir.mkdirs();
        FileInputStream fis;
        //buffer for read and write data to file
        byte[] buffer = new byte[1024];
        try {
            fis = new FileInputStream(zipFilePath);
            ZipInputStream zis = new ZipInputStream(fis);
            ZipEntry ze = zis.getNextEntry();
            while(ze != null){
                String fileName = ze.getName();
                
                File newFile = new File(destDir + File.separator + fileName);
                System.out.println("解壓縮檔案 "+newFile.getAbsolutePath());
                if(newFile.getName().split("\\.").length>2 || newFile.getName().indexOf(".")==-1) {
                    
                    Path path = Paths.get(newFile.getAbsolutePath());
                    try { Files.createDirectories(path); }catch(Exception ex) {
                        System.out.println("[建立目錄發生異常] "+newFile.getAbsolutePath());
                        throw ex;
                    }
                    
                    zis.closeEntry();
                    ze = zis.getNextEntry();
                    
                    continue;
                }
                
                File tmp=new File(newFile.getParent());
                FileOutputStream fos = new FileOutputStream(newFile);
                int len;
                while ((len = zis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
                }
                fos.close();
                
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            //close last ZipEntry
            zis.closeEntry();
            zis.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
        
    public static void main(String[] args) {
        //java.io.File dir=new java.io.File("C:/Users/016356/.m2/repository");
        java.io.File dir=new java.io.File("/Users/yenchechang/.m2/repository");
        
        YccMavenCheck.toDo(dir);
        
        //System.out.println(YccMavenCheck.isValidZip("C:/Users/016356/.m2/repository/org/apache/httpcomponents/httpcore/4.2-alpha2/httpcore-4.2-alpha2.jar"));
        
        //YccMavenCheck.unzip("C:\\Users\\016356\\.m2\\repository\\org\\apache\\httpcomponents\\httpcore\\4.2-alpha2\\httpcore-4.2-alpha2.jar", "C:\\\\Users\\\\016356\\\\.m2\\\\repository\\\\org\\\\apache\\\\httpcomponents\\\\httpcore\\\\4.2-alpha2");
        
        //String s="a.b";
        //StringTokenizer st=new StringTokenizer(s, ".");
        //while(st.hasMoreElements()) {
        //    System.out.println(">> "+st.nextElement());
        //}
        
        //String s="C:\\Users\\016356\\.m2\\repository\\org\\apache\\httpcomponents\\httpcore\\4.2-alpha2\\META-INF\\maven\\org.apache.httpcomponents,";
        //System.out.println(s.split("\\.").length+" "+Arrays.asList(s.split("\\.")));
    }

}
