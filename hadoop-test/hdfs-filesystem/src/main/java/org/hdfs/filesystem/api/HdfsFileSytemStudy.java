package org.hdfs.filesystem.api;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.BlockLocation;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileChecksum;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;
import org.apache.hadoop.hdfs.DistributedFileSystem;
import org.apache.hadoop.hdfs.protocol.DatanodeInfo;

/**
 * 
 * @author chent
 *
 */
public class HdfsFileSytemStudy {
	
	// core-site.xml指定的访问地址
	private static String hdfsUri = "hdfs://localhost:9000";
	private static FileSystem hdfs;
	private static Configuration conf;
	
	static {
		 conf=new Configuration();
		// chent为电脑的超级用户
        try {
			hdfs=FileSystem.get(new URI(hdfsUri), conf, "chent");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws Exception {
		//testCreateFile();
		//deleteFile();
		//readFile();
		listFiles();
		//queryPosition();
	}
	
	/**
	 * 执行完后，可以通过 hadoop fs -cat /tian/ming.txt查看内容
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws URISyntaxException
	 */
	public static void testCreateFile() throws IOException, InterruptedException, URISyntaxException {
        byte[] buff="hello hadoop world!\n".getBytes();
        Path path=new Path("/tian/ming.txt");
        FSDataOutputStream outputStream=hdfs.create(path);
        outputStream.write(buff,0,buff.length);
	}
	
	/**
	 * 删除文件夹或文件
	 * @throws IOException 
	 * @throws IllegalArgumentException 
	 */
	public static void deleteFile() throws IllegalArgumentException, IOException{
		Path path = new Path("/user/");
		if(hdfs.exists(path)){
			hdfs.delete(path, true);// 循环删除文件夹
		}else{
			System.out.println(path.getName() + " is not exists");
		}
	}
	
	
	/**
	 * 相当于 hadoop fs -cat /tian/test.txt
	 * @throws IOException
	 */
	public static void readFile() throws IOException{
		Path path = new Path("/tian/test.txt");
		
		if(hdfs.isFile(path)){
			ByteBuffer buf = ByteBuffer.allocate(1024);
			FSDataInputStream file = hdfs.open(path);
			int read = 0;
			while((read = file.read(buf)) != -1){
				System.out.print(new String(buf.array()));
				buf.clear();
			};
		}
	}
	
	/**
	 * 展示列表文件
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static void listFiles() throws FileNotFoundException, IOException{
		Path path = new Path("/");
		/**
		 *  获取其路径下的所有子文件夹或文件
		 *  FileStatus{path=hdfs://localhost:9000/tian; isDirectory=true; modification_time=1573626399644; access_time=0; owner=chent; group=supergroup; permission=rwxr-xr-x; isSymlink=false}
		 */
		FileStatus[] listStatus = hdfs.listStatus(path);
		for (FileStatus fileStatus : listStatus) {
			System.out.println(fileStatus);
		}
		
		/**
		 *  展示所有的文件(循环搜索)
		 *  LocatedFileStatus{path=hdfs://localhost:9000/tian/ming.txt; isDirectory=false; length=20; replication=3; blocksize=134217728; modification_time=1573626399993; access_time=1573626399644; owner=chent; group=supergroup; permission=rw-r--r--; isSymlink=false}
		 *	LocatedFileStatus{path=hdfs://localhost:9000/tian/test.txt; isDirectory=false; length=365; replication=1; blocksize=134217728; modification_time=1573622845550; access_time=1573627295365; owner=chent; group=supergroup; permission=rw-r--r--; isSymlink=false}
		 */
		RemoteIterator<LocatedFileStatus> listFiles = hdfs.listFiles(path, true);
		LocatedFileStatus next = null;
		while(listFiles.hasNext()){
			next = listFiles.next();
			System.out.println(next);
		}
	}

	
	/**
	 * 获取文件属性
	 * @throws IOException
	 */
	public static void queryPosition() throws IOException{
		Path path = new Path("/tian/test.txt");
		FileStatus fileStatus = hdfs.getFileStatus(path);
		
		// 获取文件所在集群位置
		BlockLocation[] fileBlockLocations = hdfs.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
		for (BlockLocation blockLocation : fileBlockLocations) {
			System.out.println(blockLocation);  //0,120,hadoop
		}
		
		// 获取checksum
		FileChecksum fileChecksum = hdfs.getFileChecksum(path);
		//MD5-of-0MD5-of-512CRC32C:884c4e06bf9c140d33612f260bbf1e25
		System.out.println(fileChecksum);
		
		// 获取集群中的所有节点信息
		DistributedFileSystem dfs = (DistributedFileSystem)hdfs;
		DatanodeInfo[] dataNodeStats = dfs.getDataNodeStats();
		for (DatanodeInfo datanodeInfo : dataNodeStats) {
			System.out.println(datanodeInfo);//127.0.0.1:50010
		}
	}

	
	
}
