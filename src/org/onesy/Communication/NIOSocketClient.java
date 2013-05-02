package org.onesy.Communication;

import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

import org.onesy.ConfigureProcess.CfgBean;
import org.onesy.ConfigureProcess.CfgCenter;
import org.onesy.MsgProcessor.MsgBean;
import org.onesy.NodesManage.NodeDictionary;
import org.onesy.Orders.ConfirmOrder;
import org.onesy.Util.SPSDebugHelper;

public class NIOSocketClient {

	private static int CLIENT_PORT = CfgCenter.selfbean.PubPort;

	private static int SERVER_PORT = 0;

	/**
	 * 
	 * @param args
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public synchronized void pub(MsgBean msgBean) throws IOException {

		SocketChannel sc = SocketChannel.open();
		Selector sel = Selector.open();
		CfgBean responser = NodeDictionary.GetResponser((String) ConfirmOrder
				.GetKeyFromMsg(msgBean.Msg).getKey());
		try {
			sc.configureBlocking(false);
			sc.socket().setReuseAddress(true);
			sc.socket().bind(new InetSocketAddress(CLIENT_PORT));
			sc.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE
					| SelectionKey.OP_CONNECT);
			boolean done = false;
			String encoding = System.getProperty("file.encoding");
			Charset cs = Charset.forName(encoding);
			ByteBuffer buf = ByteBuffer.allocate(1024);
			while (!done) {
				sel.select();
				Iterator it = sel.selectedKeys().iterator();
				while (it.hasNext()) {
					SelectionKey key = (SelectionKey) it.next();
					it.remove();
					// 获取创建通道选择器事件键的套接字通道
					sc = (SocketChannel) key.channel();
					// 当前通道选择器产生连接已经准备就绪事件，并且客户端套接字
					// 通道尚未连接到服务端套接字通道
					if (key.isConnectable() && !sc.isConnected()) {
						InetAddress addr;
						if (msgBean.isRtn) {
							addr = InetAddress
									.getByName(msgBean.clientInfo.host);
							SERVER_PORT = msgBean.clientInfo.listenport;
						} else {
							addr = InetAddress.getByName(responser.host);
							SERVER_PORT = responser.SubPort;
						}
						// 客户端套接字通道向服务端套接字通道发起非阻塞连接
						System.err.println("发起链接：" + addr.getHostAddress());
						Thread.sleep(5);
						boolean success = sc.connect(new InetSocketAddress(
								addr, SERVER_PORT));
						// 如果客户端没有立即连接到服务端，则客户端完成非立即连接操作
						if (!success)
							sc.finishConnect();
					}
					// 如果通道选择器产生读取操作已准备好事件，且已经向通道写入数据
					if (key.isReadable()) {
						if (sc.read((ByteBuffer) buf.clear()) > 0) {
							// 从套接字通道中读取数据
							String response = cs
									.decode((ByteBuffer) buf.flip()).toString();
							System.out.println(response);
							sc.finishConnect();
							done = true;
						}
					}
					// 如果通道选择器产生写入操作已准备好事件，并且尚未想通道写入数据
					if (key.isWritable()) {
						// 向套接字通道中写入数据
						sc.write(ByteBuffer.wrap(new String(MsgBean
								.DecodeToStr(msgBean)
								+ CfgCenter.ConnectEND).getBytes()));
						/*
						 * 下面被注释的这么一句话告诉了我们一个真谛，有话，一次性说完
						 * sc.write(ByteBuffer.wrap(new
						 * String("\r\r\rEND\r\r\r") .getBytes()));
						 */
						sc.finishConnect();
						done = true;
					}
				}
			}
		} catch (BindException e) {
			// TODO: handle exception
			e.printStackTrace();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			SPSDebugHelper.Speaker("host : " + responser.host, 2);
			SPSDebugHelper.Speaker("port : " + responser.port, 2);
			SPSDebugHelper.Speaker("subport : " + responser.SubPort, 2);
			SPSDebugHelper.Speaker("pubport : " + responser.PubPort, 2);
			SPSDebugHelper.Speaker("sign : " + responser.sign, 2);
			SPSDebugHelper.Speaker("key" + msgBean.Msg, 2);
		} finally {
			sc.close();
			sel.close();
		}
	}
}
