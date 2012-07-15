package com.service;

import java.io.*;
import java.util.*;

public abstract class ChangetoHtml {
	// �����࣬�����Ծ�������������ת��

	private List<String> conent = null;
	// ת�����html����
	private List<String> change = new ArrayList<String>();

	// �������ļ�ȡ�������ݷ���
	public boolean getSelectedFileContent(String fileName) {
		File file = new File(fileName);
		boolean b = false;
		if (!file.exists()) {
			// ����ļ������ڣ����ش���
			return b;
		} else {
			// �ļ�����
			/*Scanner scanner = null;
			try {
				scanner = new Scanner(file);
				conent = new ArrayList<String>();
				while (scanner.hasNextLine()) {
					String c = scanner.nextLine();
					// ��ӵ�conntent����
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
		// ���������ļ��Ƿ��Ѿ�����
		boolean b = false;
		File file = new File(fileName);
		if (file.exists()) {
			b = true;
		}
		return b;
	}

	public boolean save(String fileName) {
		// �����ļ��������λ�ã�ע�����Ҫ��ͬһ������
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

	// �����࣬���ݲ�ͬ����ת��Ϊhtml
	public abstract boolean changeProgramtoHtml(String type_color,
			String marks_color, String Annotation_color, String font,
			String size, String suffix);

	// �����࣬���ݲ�ͬ����ת��Ϊhtml,���к�
	public abstract boolean changeProgramtoHtmlWithRow(String type_color,
			String marks_color, String Annotation_color, String font,
			String size, String suffix);
}