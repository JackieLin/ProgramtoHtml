package com.service;

import java.io.*;
import java.util.*;

public abstract class ChangetoHtml {
	// 抽象类，用来对具体代码高亮进行转化

	private List<String> conent = null;
	// 转化后的html代码
	private List<String> change = new ArrayList<String>();

	// 对所传文件取出其内容返回
	public boolean getSelectedFileContent(String fileName) {
		File file = new File(fileName);
		boolean b = false;
		if (!file.exists()) {
			// 如果文件不存在，返回错误
			return b;
		} else {
			// 文件存在
			/*Scanner scanner = null;
			try {
				scanner = new Scanner(file);
				conent = new ArrayList<String>();
				while (scanner.hasNextLine()) {
					String c = scanner.nextLine();
					// 添加到conntent里面
					conent.add(c);
				}
				b = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if (scanner != null)
					scanner.close();
			}*/
				BufferedReader br = null;
				InputStreamReader isr = null;
				try {
					isr = new InputStreamReader(new FileInputStream(file));
					br = new BufferedReader(isr);
					conent = new ArrayList<String>();
					String c = br.readLine();
					while(c != null){
						conent.add(c);
						c = br.readLine();
					}
					b = true;
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{
					try {
						br.close();
						isr.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		return b;
	}

	public boolean isFileExist(String fileName) {
		// 看保存是文件是否已经存在
		boolean b = false;
		File file = new File(fileName);
		if (file.exists()) {
			b = true;
		}
		return b;
	}

	public boolean save(String fileName) {
		// 保存文件到具体的位置，注意对象要是同一个！！
		boolean b = false;
		try {
			PrintWriter pw = new PrintWriter(fileName);
			for (int i = 0; i < change.size(); i++) {
				pw.println(change.get(i));
			}
			b = true;
			pw.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

	public void setConent(List<String> conent) {
		this.conent = conent;
	}

	public List<String> getChange() {
		return change;
	}

	public void setChange(List<String> change) {
		this.change = change;
	}

	public List<String> getConent() {
		return conent;
	}

	// 抽象类，根据不同程序转化为html
	public abstract boolean changeProgramtoHtml(String type_color,
			String marks_color, String Annotation_color, String font,
			String size, String suffix);

	// 抽象类，根据不同程序转化为html,带行号
	public abstract boolean changeProgramtoHtmlWithRow(String type_color,
			String marks_color, String Annotation_color, String font,
			String size, String suffix);
}