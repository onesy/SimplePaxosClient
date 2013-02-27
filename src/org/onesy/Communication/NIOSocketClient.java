package org.onesy.Communication;

import java.io.IOException;
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

public class NIOSocketClient {

	private static int CLIENT_PORT = CfgCenter.selfbean.PubPort;

	private static int SERVER_PORT = 0;

	public void sub(MsgBean msgBean) throws IOException {
		CfgBean cfgBean = NodeDictionary.GetCfgBean(msgBean.sign);
		SERVER_PORT = cfgBean.SubPort;
		SocketChannel sc = SocketChannel.open();
		Selector sel = Selector.open();
		try {
			sc.configureBlocking(false);
			sc.socket().bind(new InetSocketAddress(CLIENT_PORT));
			sc.register(sel, SelectionKey.OP_READ | SelectionKey.OP_WRITE
					| SelectionKey.OP_CONNECT);
			int i = 0;
			boolean written = false;
			boolean done = false;
			String encoding = System.getProperty("file.encoding");
			Charset cs = Charset.forName(encoding);
			ByteBuffer buf = ByteBuffer.allocate(16);
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
						InetAddress addr = InetAddress.getByName(null);
						// 客户端套接字通道向服务端套接字通道发起非阻塞连接
						boolean success = sc.connect(new InetSocketAddress(
								addr, SERVER_PORT));
						// 如果客户端没有立即连接到服务端，则客户端完成非立即连接操作
						if (!success)
							sc.finishConnect();
					}
					// 如果通道选择器产生读取操作已准备好事件，且已经向通道写入数据
					if (key.isReadable() && written) {
						if (sc.read((ByteBuffer) buf.clear()) > 0) {
							written = false;
							// 从套接字通道中读取数据
							String response = cs
									.decode((ByteBuffer) buf.flip()).toString();
							// System.out.println(response);
							if (response.indexOf(CfgCenter.ConnectEND) != -1)
								done = true;
						}
					}
					// 如果通道选择器产生写入操作已准备好事件，并且尚未想通道写入数据
					if (key.isWritable() && !written) {
						// 向套接字通道中写入数据
						// TODO 更改响应内容
						String msg = MsgBean.DecodeToStr(msgBean)
								+ CfgCenter.ConnectEND;
						sc.write(ByteBuffer
								.wrap(new String(msg + "\n")
										.getBytes()));
						sc.write(ByteBuffer.wrap(new String(
								CfgCenter.ConnectEND).getBytes()));
						written = true;
						i++;
					}
				}
			}
		} finally {
			sc.close();
			sel.close();
		}
	}

}
