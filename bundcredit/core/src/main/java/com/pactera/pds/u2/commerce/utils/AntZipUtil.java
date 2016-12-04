package com.pactera.pds.u2.commerce.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;

/**
 * Ant Zip util class
 * 
 */
public class AntZipUtil {

	/**
	 * 调用org.apache.tools.zip实现解压缩，支持目录嵌套和中文名
	 * 
	 * @param zipFileName
	 *            要解压缩的文件
	 * @param outputDirectory
	 *            要解压到的目录
	 * @throws Exception
	 */
	public static ArrayList<String> unZip(String zipFileName,
			String outputDirectory) {
		ArrayList<String> allFileName = new ArrayList<String>();
		try {
			ZipFile zipFile = new ZipFile(zipFileName);
			Enumeration<ZipEntry> e = zipFile.getEntries();
			org.apache.tools.zip.ZipEntry zipEntry = null;
			createDirectory(outputDirectory, "");
			while (e.hasMoreElements()) {
				zipEntry = (org.apache.tools.zip.ZipEntry) e.nextElement();
				if (zipEntry.isDirectory()) {
					String name = zipEntry.getName();
					name = name.substring(0, name.length() - 1);
					File f = new File(outputDirectory + File.separator + name);
					f.mkdir();
				} else {
					String fileName = zipEntry.getName();
					fileName = fileName.replace('\\', '/');
					if (fileName.indexOf("/") != -1) {
						createDirectory(outputDirectory, fileName.substring(0,
								fileName.lastIndexOf("/")));
						fileName = fileName.substring(
								fileName.lastIndexOf("/") + 1,
								fileName.length());
					}

					File f = new File(outputDirectory + File.separator
							+ zipEntry.getName());

					f.createNewFile();
					InputStream in = zipFile.getInputStream(zipEntry);
					FileOutputStream out = new FileOutputStream(f);

					allFileName.add(f.getAbsolutePath());

					byte[] by = new byte[1024];
					int c;
					while ((c = in.read(by)) != -1) {
						out.write(by, 0, c);
					}
					out.close();
					in.close();
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return allFileName;
	}

	/**
	 * 创建目录
	 * 
	 * @param directory
	 *            父目录
	 * @param subDirectory
	 *            子目录
	 */
	private static void createDirectory(String directory, String subDirectory) {
		String dir[];
		File fl = new File(directory);
		try {
			if (subDirectory == "" && fl.exists() != true)
				fl.mkdir();
			else if (subDirectory != "") {
				dir = subDirectory.replace('\\', '/').split("/");
				for (int i = 0; i < dir.length; i++) {
					File subFile = new File(directory + File.separator + dir[i]);
					if (subFile.exists() == false)
						subFile.mkdir();
					directory += File.separator + dir[i];
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}