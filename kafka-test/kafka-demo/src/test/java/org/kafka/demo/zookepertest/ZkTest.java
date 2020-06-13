package org.kafka.demo.zookepertest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Id;
import org.apache.zookeeper.data.Stat;
import org.apache.zookeeper.server.auth.DigestAuthenticationProvider;

public class ZkTest {
	
	public static void main(String[] args) {
		ZkTest zktest = new ZkTest();
		try {
			//zktest.zkCreate();
			zktest.zkRead();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private static final String zk_path = "/zktest";
	
	public void zkCreate() throws Exception{
		List<ACL> acls = new ArrayList<>(2);
		Id id1 = new Id("digest", DigestAuthenticationProvider.generateDigest("admin:admin123"));
		ACL acl1 = new ACL(ZooDefs.Perms.ALL, id1);
		
		Id id2 = new Id("digest", DigestAuthenticationProvider.generateDigest("guest:guest123"));
		ACL acl2 = new ACL(ZooDefs.Perms.READ, id2);

		acls.add(acl1);
		acls.add(acl2);

		ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 10000, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				System.out.println(event);
			}
		});
		
		zk.create(zk_path, "hello wrold".getBytes(), acls, CreateMode.PERSISTENT);
	}
	
	
	public void zkRead() throws Exception{
		ZooKeeper zk = new ZooKeeper("127.0.0.1:2181", 10000, new Watcher() {
			@Override
			public void process(WatchedEvent event) {
				System.out.println(event);
			}
		});
		zk.addAuthInfo("digest", "guest:guest123".getBytes());
		byte[] value = zk.getData(zk_path, null, new Stat());
		System.out.println(String.valueOf(value));
	}
	
	
}
