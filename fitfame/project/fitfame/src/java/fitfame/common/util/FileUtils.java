package fitfame.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;

import java.util.Properties;
public class FileUtils {
	static final Log log = LogFactory.getLog(FileUtils.class);

	public static boolean exist(String path) {
		File file = new File(path);
		return file.exists();
	}

	public static Writer getFileWriter(String path) throws IOException {
		File file = new File(path);
		if (!file.getParentFile().exists())
			return null;
		return new FileWriter(path);
	}

	public static File getFile(String path) {
		return getFile(path, false);
	}

	public static File getFile(String path, boolean checkExist) {
		File f = new File(path);
		if (checkExist && !f.exists())
			throw new RuntimeException("File[" + path + "] not exist!");
		return f;
	}

	public static void mv(File src, File dest) {
		try {
			if (!src.exists()
					|| src.getCanonicalPath().equals(dest.getCanonicalPath()))
				return;
		} catch (IOException ex) {
		}
		src.renameTo(dest);
	}

	public static void rm(File f) {
		if (f.exists())
			f.delete();
	}

	public static void cp(String srcPath, String destPath) {
		cp(new File(srcPath), new File(destPath));
	}

	public static void cp(File src, File dest) {
		FileInputStream in = null;
		FileOutputStream out = null;
		if (!src.exists()) {
			if (log.isInfoEnabled())
				log.info(FileUtils.class.getSimpleName() + " cp src:"
						+ src.getAbsolutePath() + " not exist");
			return;
		}
		if (!dest.getParentFile().exists())
			dest.getParentFile().mkdirs();
		try {
			in = new FileInputStream(src);
			out = new FileOutputStream(dest);
			FileChannel inChannel = in.getChannel();
			FileChannel outChannel = out.getChannel();
			int write = 0;
			long read = inChannel.size();
			while (write < read) {
				write += inChannel.transferTo(write, read - write, outChannel);
			}
		} catch (FileNotFoundException e) {
			log.error(FileUtils.class.getName() + "#cp FileNotFoundException",
					e);
		} catch (IOException e) {
			log.error(FileUtils.class.getName() + "#cp IOException", e);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				log.error(FileUtils.class.getName()
						+ "#cp close in IOException", e);
			}
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				log.error(FileUtils.class.getName()
						+ "#cp close out IOException", e);
			}
		}
	}

	/**
	 * 判断给定的路径是否为目录
	 * 
	 * @param dirpath
	 * @return
	 */
	public static boolean isDirectory(String dirpath) {
		File dir = new File(dirpath);
		return dir.isDirectory();
	}

	/**
	 * 列出某个目录下的文件(按照filter进行过滤,最多列出max个文件)
	 * 
	 * @param dir
	 * @param filter
	 * @param max
	 * @return
	 */
	public static File[] listFiles(String dirpath, FilenameFilter filter,
			int max) {
		if (!isDirectory(dirpath))
			return null;
		File dir = new File(dirpath);
		File[] files = dir.listFiles(filter);
		if (ArrayUtils.isEmpty(files))
			return null;
		Arrays.sort(files);
		int end = max < files.length ? max : files.length;
		return (File[]) ArrayUtils.subarray(files, 0, end);
	}

	public static boolean mkdir(String dir) {
		File file = new File(dir);
		return mkdir(file);
	}

	public static boolean mkdir(File dir) {
		if (dir.exists() && dir.isDirectory())
			return true;
		return dir.mkdirs();
	}

	public static String getContent(File file) {
		StringBuffer sb = new StringBuffer();
		if (file.exists() && file.isFile()) {
			BufferedReader r = null;
			try {
				//r = new BufferedReader(new FileReader(file));
				r = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8")); 
				char[] buff = new char[4096];
				int read = 0;
				while ((read = r.read(buff)) != -1) {
					sb.append(buff, 0, read);
				}
			} catch (Exception e) {
				log.error(FileUtils.class.getName(), e);
			} finally {
				if (r != null) {
					try {
						r.close();
					} catch (IOException e) {
						log.error(FileUtils.class.getName(), e);
					}
				}
			}
		}
		return sb.toString();
	}

	public static void writeContent(File file, String s) {
		Writer w = null;
		try {
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			w = new FileWriter(file);
			w.write(s);
			w.flush();
		} catch (Exception e) {
			log.error(FileUtils.class.getName(), e);
		} finally {
			if (w != null) {
				try {
					w.close();
				} catch (IOException e) {
					log.error(FileUtils.class.getName(), e);
				}
			}
		}
	}

	public static void writeContent(File f, String s, String decode,
			String encode) {
		if (StringUtils.isNotEmpty(decode) && StringUtils.isNotEmpty(encode)
				&& !decode.equals(encode)) {
			OutputStream out = null;
			try {
				if (!f.getParentFile().exists())
					f.getParentFile().mkdirs();
				out = new FileOutputStream(f);
				String content = new String(s.getBytes(), decode);
				out.write(content.getBytes(encode));
			} catch (UnsupportedEncodingException e) {
				log.error(FileUtils.class.getSimpleName()
						+ "#writeContent UnsupportedEncodingException", e);
			} catch (IOException e) {
				log.error(FileUtils.class.getSimpleName()
						+ "#writeContent IOException", e);
			} finally {
				try {
					if (out != null)
						out.close();
				} catch (IOException e) {
					log.error(FileUtils.class.getSimpleName()
							+ "#writeContent close io IOException", e);
				}
			}
		} else {
			writeContent(f, s);
		}
		writeContent(f, s);
	}

	/**
	 * @param fileName
	 *            source file name
	 * @param zipFileName
	 *            zip file name
	 * @param delSrc
	 *            delete source file or not
	 * @return success or not
	 */
	public static boolean zip(String fileName, String zipFileName,
			boolean delSrc) {
		File srcFile = new File(fileName);
		File zipFile = new File(zipFileName);
		if (!zipFile.getParentFile().exists()) {
			zipFile.getParentFile().mkdirs();
		}
		if (srcFile.isFile()) {
			try {
				FileInputStream is = new FileInputStream(fileName);
				FileOutputStream os = new FileOutputStream(zipFileName);
				CheckedOutputStream cs = new CheckedOutputStream(os,
						new Adler32());
				ZipOutputStream out = new ZipOutputStream(
						new BufferedOutputStream(cs));
				out.putNextEntry(new java.util.zip.ZipEntry(srcFile.getName()));
				int b = -1;
				while ((b = is.read()) != -1) {
					out.write(b);
				}
				out.flush();
				out.close();
				is.close();
				if (delSrc) {
					srcFile.delete();
				}
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	/**
	 * 获取Properties文件中配置项的值
	 * @param filePath 文件路径
	 * @param paraKey	文件中的配置项
	 * @return	文件中的配置项的值
	 */
	public static String getPropertiesValue(String filePath,String paraKey){ 
		Properties props = new Properties(); 
		try { 
			InputStream ips = new BufferedInputStream(new FileInputStream(filePath)); 
			props.load(ips); 
			String value = props.getProperty(paraKey); 
			//System.out.println(paraKey+"="+value); 
			return value; 
		} catch (FileNotFoundException e) { 
			e.printStackTrace(); 
			return null; 
		} catch (IOException e) { 
			e.printStackTrace(); 
			return null; 
		}
	}
	
	/**
	 * 设置Properties文件中配置项的值	 
	 * @param filePath 文件路径
	 * @param paraKey 文件中的配置项
	 * @param paraValue 文件中的配置项的值
	 */
	
	public static void setPropertiesValue(String filePath,String paraKey,String paraValue){ 
		
		Properties prop = new Properties();
	     try {
	      InputStream fis = new FileInputStream(filePath);
	            //从输入流中读取属性列表（键和元素对）
	            prop.load(fis);
	            //调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
	            //强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
	            OutputStream fos = new FileOutputStream(filePath);
	            prop.setProperty(paraKey, paraValue);
	            //以适合使用 load 方法加载到 Properties 表中的格式，
	            //将此 Properties 表中的属性列表（键和元素对）写入输出流
	            prop.store(fos, "Update '" + paraKey + "' value");
	        } catch (IOException e) {
	         System.err.println("Visit "+filePath+" for updating "+paraKey+" value error");
	        }
	} 
	
	/**
	 * 获得当前配置文件路径
	 * @return
	 */
	public static String getConfigPath(){ 
		String spath="";
			
		spath = "/"+ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath("/")+"WEB-INF/";
	
		return spath;		
	} 
	/** 
     * 生成UTF-8文件. 
     * 如果文件内容中没有中文内容，则生成的文件为ANSI编码格式； 
     * 如果文件内容中有中文内容，则生成的文件为UTF-8编码格式。 
     * @param fileName 待生成的文件名（含完整路径） 
     * @param fileBody 文件内容 
     * @return 
     */  
    public static boolean writeUTFFile(String fileName,String fileBody){  
        FileOutputStream fos = null;  
        OutputStreamWriter osw = null;  
        try {  
            fos = new FileOutputStream(fileName);  
            osw = new OutputStreamWriter(fos, "UTF-8");  
            osw.write(fileBody);  
            return true;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return false;  
        }finally{  
            if(osw!=null){  
                try {  
                    osw.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if(fos!=null){  
                try {  
                    fos.close();  
                } catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }  
    }  
}
