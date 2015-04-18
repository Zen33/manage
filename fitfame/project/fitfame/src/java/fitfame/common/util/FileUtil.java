package fitfame.common.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipOutputStream;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;




/**
 * 处理文件、流操作
 * 
 *
 */
public class FileUtil {
	static final Log log = LogFactory.getLog(FileUtil.class);
	/**
	 * 保存头像 返回路径
	 */
	public static String SaveStringAsHeaderPic(String uid, byte [] picture, String picType) throws IOException
	{
		Random random = new Random();
		int r = random.nextInt(10000);
		String icon = "http://112.124.52.165:8080/resources/HeaderPicture/" + uid + "_" + r + "." + picType;
		String url = "/usr/local/tomcat/webapps/resources/HeaderPicture/"  + uid +  "_" + r + "." + picType;
		File f=new File(url); 
		if (f.exists()==false) 
		{  
			f.createNewFile();
		}  
		FileOutputStream fos=new FileOutputStream(f);  
		fos.write(picture); 
		fos.flush();  
		fos.close();
		return icon;
	}
	
	/**
	 * 保存头像 返回路径
	 */
	public static String SaveStringAsTopicPic(byte [] picture, String picType) throws IOException
	{
		UUID uuid = UUID.randomUUID(); 
		String icon = "http://112.124.52.165:8080/resources/topic/" + uuid + "." + picType;
		String url = "/usr/local/tomcat/webapps/resources/topic/"  + uuid +"." + picType;
		File f=new File(url); 
		if (f.exists()==false) 
		{  
			f.createNewFile();
		}  
		FileOutputStream fos=new FileOutputStream(f);  
		fos.write(picture); 
		fos.flush();  
		fos.close();
		return icon;
	}
	
	public static String SaveStringAsADPic(String id, byte [] picture, String picType) throws IOException
	{
		String icon = "http://112.124.52.165:8080/resources/AD/" + id + "." + picType;
		String url = "/usr/local/tomcat/webapps/resources/AD/"  + id + "." + picType;
		File f=new File(url); 
		if (f.exists()==false) 
		{  
			f.createNewFile();
		}  
		FileOutputStream fos=new FileOutputStream(f);  
		fos.write(picture); 
		fos.flush();  
		fos.close();
		return icon;
	}
	
	public static String SaveADFile(String id , File file, String Type) throws IOException
	{
		InputStream rtInStream;
		String icon = "http://112.124.52.165:8080/resources/AD/" +
		id + "." + Type.substring(6);
		String url = "/usr/local/tomcat/webapps/resources/AD/"  + id + "." + Type.substring(6);
		rtInStream = new FileInputStream(file);
		File outFile = new File(url);
		FileOutputStream fos = new FileOutputStream(outFile);
		byte buffer[] = new byte[1024];
		int temp = 0;
		while((temp = rtInStream.read(buffer)) != -1) {
			fos.write(buffer, 0, temp);
		}		
		
		fos.flush();  
		fos.close();
		return icon;
	}
	
	
	
	/**
	 * 把输入的字节流转化为String类型返回
	 * @param is 输入流InputStream
	 * @return
	 */
	public static String stream2String(InputStream is){
		BufferedReader in=null;
		try {
			in = new BufferedReader(new InputStreamReader(is,"utf-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		StringBuffer buffer = new StringBuffer();
		String line = "";
		try {
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
		} catch (IOException e) {
			//TODO 记录日志
		}
		return buffer.toString();
	}
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
			if (!src.exists() || src.getCanonicalPath().equals(dest.getCanonicalPath()))
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
				log.info(FileUtil.class.getSimpleName() + " cp src:" + src.getAbsolutePath() + " not exist");
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
			log.error(FileUtil.class.getName() + "#cp FileNotFoundException", e);
		} catch (IOException e) {
			log.error(FileUtil.class.getName() + "#cp IOException", e);
		} finally {
			try {
				if (in != null)
					in.close();
			} catch (IOException e) {
				log.error(FileUtil.class.getName() + "#cp close in IOException", e);
			}
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				log.error(FileUtil.class.getName() + "#cp close out IOException", e);
			}
		}
	}

	/**
	 * 判断给定的路径是否为目录
	 * @param dirpath
	 * @return
	 */
	public static boolean isDirectory(String dirpath) {
		File dir = new File(dirpath);
		return dir.isDirectory();
	}

	/**
	 * 列出某个目录下的文件(按照filter进行过滤,最多列出max个文件)
	 * @param dir
	 * @param filter
	 * @param max
	 * @return
	 */
	public static File[] listFiles(String dirpath, FilenameFilter filter, int max) {
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
				r = new BufferedReader(new FileReader(file));
				char[] buff = new char[4096];
				int read = 0;
				while ((read = r.read(buff)) != -1) {
					sb.append(buff, 0, read);
				}
			} catch (Exception e) {
				log.error(FileUtil.class.getName(), e);
			} finally {
				if (r != null) {
					try {
						r.close();
					} catch (IOException e) {
						log.error(FileUtil.class.getName(), e);
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
			log.error(FileUtil.class.getName(), e);
		} finally {
			if (w != null) {
				try {
					w.close();
				} catch (IOException e) {
					log.error(FileUtil.class.getName(), e);
				}
			}
		}
	}

	public static void writeContent(File f, String s, String decode, String encode) {
		if (StringUtils.isNotEmpty(decode) && StringUtils.isNotEmpty(encode) && !decode.equals(encode)) {
			OutputStream out = null;
			try {
				if (!f.getParentFile().exists())
					f.getParentFile().mkdirs();
				out = new FileOutputStream(f);
				String content = new String(s.getBytes(), decode);
				out.write(content.getBytes(encode));
			} catch (UnsupportedEncodingException e) {
				log.error(FileUtil.class.getSimpleName() + "#writeContent UnsupportedEncodingException", e);
			} catch (IOException e) {
				log.error(FileUtil.class.getSimpleName() + "#writeContent IOException", e);
			} finally {
				try {
					if (out != null)
						out.close();
				} catch (IOException e) {
					log.error(FileUtil.class.getSimpleName() + "#writeContent close io IOException", e);
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
	public static boolean zip(String fileName, String zipFileName, boolean delSrc) {
		File srcFile = new File(fileName);
		File zipFile = new File(zipFileName);
		if (!zipFile.getParentFile().exists()) {
			zipFile.getParentFile().mkdirs();
		}
		if (srcFile.isFile()) {
			try {
				FileInputStream is = new FileInputStream(fileName);
				FileOutputStream os = new FileOutputStream(zipFileName);
				CheckedOutputStream cs = new CheckedOutputStream(os, new Adler32());
				ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(cs));
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
}
