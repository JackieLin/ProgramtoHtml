package com.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Stack;

public class ProgramChangeTOHtml extends ChangetoHtml {
	// �ؼ��ּ���

	private Map<String, String> keywords = new HashMap<String, String>();
	// �����ַ�����
	private Map<Character, String> straMap = new HashMap<Character, String>();
	// ���͹ؼ���
	private String[] type;
	// �༰�����ؼ���
	private String[] method;
	List<String> conent = null;
	private InputStream in = Object.class
			.getResourceAsStream("/Program_name.properties");
	private Properties pro = new Properties();
	// ��־λ�����ǲ����õ���һ���Ǻ�
	private int has_asterisk = 0;
	// ���ùؼ�����ɫ
	private String typecolor;
	// ���ñ�־(˫����)��С
	private String markscolor;
	// ����ע����ɫ
	private String Annotationcolor;
	// ��������
	private String font;
	// ���������С
	private String size;
	// �жϴ����Ų�����ѹ��ջ
	private Stack<Character> stack = new Stack<Character>();
	// ͳ�ƿո���Ŀ,��ʼ��Ϊ2
	private int number = 2;
	private int row = 0;
	// ��־,�ж��ǲ�����ͬһ�� 1�ǣ�0����
	private int same_line = 1;
	// ��ǣ��ж�ĳһ���Ƿ�����˷ֺţ��еĻ�Ҫ�ӵ���һ��,Ĭ����û�У�1���� 2���ӵ���һ�У���һ�л���
	private int is_no_semicolon = 0;
	// ����ĳһ�еĴ���
	private StringBuffer linetoHtml = new StringBuffer();

	// ���ԣ�������һ�д���ĳ��ȣ�����λʱ��
	private int f_number = 0;

	// ���ԣ���ʾ��û��#��,Ĭ����û��
	private int jinghao = 0;

	// �������ж��Ƿ���ƥ��Ĵ�����
	public boolean has_matching() {
		boolean b = false;
		if (stack.peek() == '{') {
			b = true;
		}
		return b;
	}

	// ������������Ϊ���е���Щ�мҿո�
	public String addBlank(String line) {
		int j = 0;
		boolean b = false;
		StringBuffer strbu = new StringBuffer(line);
		if (strbu.substring(0, 6).equals("<br />")) {
			for (j = 7; j < strbu.length(); j++) {
				if (strbu.charAt(j) == '>') {
					if (strbu.charAt(j + 1) < '0' || strbu.charAt(j + 1) > '9') {
						b = true;
					}
					break;
				}
			}
			j++;
			if (b == true)
				for (int i = 0; i < f_number / 3; i++)
					strbu.insert(j, "&nbsp;");
		}
		return strbu.toString();
	}

	// ��������ӡ���к�Ŀո�
	public void changeblank(int rowNum) {
		if (same_line == 1) {
			row++;
			linetoHtml.append("<br />");
			linetoHtml.append(drawblank(number, rowNum));
		} else if (is_no_semicolon == 0 && same_line == 0) {
			linetoHtml.append(drawblank(number, rowNum));
			same_line = 1;
		} else if (is_no_semicolon == 1 && same_line == 0) {
			// ��ʱ����û�зֺŵ�(��ע��)
			row--;
			linetoHtml.append(drawblank(number, rowNum));
			// ��Ǳ�Ϊ0
			// is_no_semicolon = 0;
		}
	}

	// ������ͳ�ƿո�ĸ���
	public String drawblank(int allNumber, int rowNum) {
		String tempStr = "";
		String str = "";
		for (int i = 0; i < allNumber; i++) {
			tempStr += "&nbsp;";
		}
		if (rowNum > 0) {
			// ���к�
			str = "<font face=\"" + font + "\" size=\"" + size + "\">" + row
					+ tempStr + "</font>";
		} else {
			str = tempStr;
		}
		return str;
	}

	public void setFont(String type_color, String marks_color,
			String Annotation_color, String font1, String size1) {
		// �����������ɫ����С�����壬��ʼ���������ļ���
		try {
			pro.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// // ���ùؼ�����ɫ
		// String t1 = pro.getProperty("type_color");
		// String t = null;
		// try {
		// t = new String(t1.getBytes("ISO8859-1"), "utf-8");
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// String[] type1 = t.split(" ");
		// for (int i = 0; i < type1.length; i++) {
		// if (type1[i].equals(type_color)) {
		// typecolor = type1[i + 1];
		// break;
		// }
		// }
		// // ���ñ����ɫ
		// t1 = pro.getProperty("marks_color");
		// try {
		// t = new String(t1.getBytes("ISO8859-1"), "utf-8");
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// type1 = t.split(" ");
		// for (int i = 0; i < type1.length; i++) {
		// if (type1[i].equals(marks_color)) {
		// markscolor = type1[i + 1];
		// break;
		// }
		// }
		// // ����ע����ɫ
		// t1 = pro.getProperty("Annotation_color");
		// try {
		// t = new String(t1.getBytes("ISO8859-1"), "utf-8");
		// } catch (UnsupportedEncodingException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// type1 = t.split(" ");
		// for (int i = 0; i < type1.length; i++) {
		// if (type1[i].equals(Annotation_color)) {
		// Annotationcolor = type1[i + 1];
		// break;
		// }
		// }

		typecolor = type_color;
		markscolor = marks_color;
		Annotationcolor = Annotation_color;
		font = font1;
		String t = null;
		// ��������
		/*
		 * String t1 = pro.getProperty("font"); try { t = new
		 * String(t1.getBytes("ISO8859-1"), "utf-8"); } catch
		 * (UnsupportedEncodingException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } String[] type1 = t.split(" "); for (int i = 0;
		 * i < type1.length; i++) { if (type1[i].equals(font1)) { font =
		 * type1[i]; break; } }
		 */

		// ���������С
		t = pro.getProperty("size");
		String[] type1 = t.split(" ");
		for (int i = 0; i < type1.length; i++) {
			if (type1[i].equals(size1)) {
				size = type1[i];
				break;
			}
		}
	}

	// ��ʼ���ؼ��֣����õ����ļ�������������,���ļ����Ͳ��������ļ��ж����ؼ���
	public void init(String type_color, String marks_color,
			String Annotation_color, String font, String size, String suffix) {
		// ��ʼ������ȡ������Ϣ
		this.setFont(type_color, marks_color, Annotation_color, font, size);
		// ��Ҫת���������ַ�����
		straMap.put(' ', "&nbsp;");
		straMap.put('<', "&lt;");
		straMap.put('&', "&amp;");
		straMap.put('>', "&gt;");
		// ��@�Ž�ȥ
		straMap.put('@', Annotationcolor);

		// ��ʼ���ؼ���
		try {
			pro.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ���̱�������ȡ�����ļ�
		String t = pro.getProperty(suffix + "_type");

		type = t.split(" ");

		String m = pro.getProperty(suffix + "_method");
		method = m.split(" ");
		// ��ʼ���ַ��ؼ���
		for (String s : type) {
			keywords.put(s, typecolor);
		}
		// ��ʼ����ؼ���
		for (String s : method) {
			keywords.put(s, typecolor);
		}
		// �õ�������������
		conent = super.getConent();
	}

	@Override
	public boolean changeProgramtoHtml(String type_color, String marks_color,
			String Annotation_color, String font, String size, String suffix) {
		// TODO Auto-generated method stub
		// �������ݽ�javaת��Ϊhtml
		boolean b = false;
		init(type_color, marks_color, Annotation_color, font, size, suffix);
		List<String> cs = super.getChange();
		// ���ͷ�ļ�
		cs
				.add("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		cs.add("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		cs.add("<body>");
		for (String line : conent) {
			// System.out.println(line);
			String s = this.changToHtmlLine(line, 0);

			// ��linetoHtml���
			linetoHtml.delete(0, linetoHtml.length());
			// ��һ��
			same_line = 0;
			if (!s.equals("")) {
				if (jinghao == 1) {
					s += "<br />";
					jinghao = 0;
					cs.add(s);
					continue;
				}
				if (is_no_semicolon == 1) {
					// ͬһ���Ѿ��зֺ�
					s += "<br />";
					is_no_semicolon = 0;
				} else if (is_no_semicolon == 3) {
					// ��ʱҪ��ӻ������
					s = this.addBlank(s);
					is_no_semicolon = 0;
				} else if (has_asterisk == 1) {
					// ע�ͣ�Ҫ����
					s += "<br />";
				} else // ����ֺ�Ҫ��ǰ
				{
					is_no_semicolon = 2;
				}
			} else {
				row--;
			}
			// ��ӵ�changetohtml����
			cs.add(s);
			f_number = line.length();
		}
		cs.add("</body>");
		super.setChange(cs);
		b = true;
		return b;
	}

	@Override
	public boolean changeProgramtoHtmlWithRow(String type_color,
			String marks_color, String Annotation_color, String font,
			String size, String suffix) {
		// TODO Auto-generated method stub
		boolean b = false;
		init(type_color, marks_color, Annotation_color, font, size, suffix);
		List<String> cs = super.getChange();
		// ���ͷ�ļ�
		cs
				.add("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		cs.add("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		cs.add("<body>");

		for (String line : conent) {
			// System.out.println(line);
			String s = this.changToHtmlLine(line, ++row);
			linetoHtml.delete(0, linetoHtml.length());
			same_line = 0;
			if (!s.equals("")) {
				if (jinghao == 1) {
					s += "<br />";
					jinghao = 0;
					cs.add(s);
					continue;
				}
				if (is_no_semicolon == 1) {
					// ͬһ���Ѿ��зֺ�
					s += "<br />";
					is_no_semicolon = 0;
				} else if (is_no_semicolon == 3) {
					// ��ʱҪ��ӻ������
					s = "<br />" + s;
					s = this.addBlank(s);
					is_no_semicolon = 0;
				} else if (has_asterisk == 1) {
					// ע�ͣ�Ҫ����
					s += "<br />";
				} else // ����ֺ�Ҫ��ǰ
				{
					is_no_semicolon = 2;
				}
			} else {
				row--;
			}
			// ��ӵ�changetohtml����
			cs.add(s);
			f_number = line.length();
		}
		cs.add("</body>");
		super.setChange(cs);
		b = true;
		return b;
	}

	// flag����־λ.0:����ʾ�к�,��������ʾ�к�
	public String changToHtmlLine(String line, int rowNum) {

		// ��־λ�����ǲ����õ���һ��б��
		int has_onesprit = 0;

		// ��־λ������û�еڶ���б��
		int has_secondsprit = 0;

		// ��־λ���ж���û��ת���ַ�
		int has_changeCode = 0;
		// ��ʱ����
		StringBuffer temp = new StringBuffer();
		// �жϣ���ʾ��û��˫����
		int marksflag = 0;

		// �ж��Ƿ������д��б��
		int over = 0;

		// �жϣ���ʾ��û�е�����
		int has_singelflag = 0;
		// ��־λ���ж��ǲ���Annotation
		int has_Annotation = 0;

		// �ж����зֺţ������л���
		int is_semicolon = 0;

		// �жϴ����ź�����û�������ַ���Ҫ����
		int after_hasword = 0;

		// �ж����ʼ�Ƿ�Ҫ���Կո�
		int has_blank = 0;
		// �ж���û�д���for������У����û���
		int has_for = 0;

		char[] chars = line.toCharArray();
		// ����ÿһ��
		for (char c : chars) {

			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
					|| (c >= '0' && c <= '9') || (c == '#')) {
				// ע��'#'�ŵĻ���
				if (c == '#')
					jinghao = 1;
				if (has_onesprit == 1 && has_secondsprit == 0
						&& has_asterisk == 0) {
					// û������б��
					linetoHtml.append("<font face=\"" + font + "\" size="
							+ size + ">" + "/" + "</font>");
					has_onesprit = 0;
					over = 0;
				} else if (c != '"' && has_changeCode == 1) {
					// û�й���ת���ַ�
					has_changeCode = 0;
				} else if (after_hasword == 1) {
					// ����д�����
					if (rowNum > 0) {
						// ����row��ֵҪ��һ
						row++;
						linetoHtml.append("<br />" + drawblank(number, rowNum));
						// ǰ���Ѿ����У����治Ҫ����
						if (is_no_semicolon == 1) {
							is_no_semicolon = 0;
						}
					} else {
						row++;
						linetoHtml.append("<br />" + drawblank(number, rowNum));
						// ǰ���Ѿ����У����治Ҫ����
						if (is_no_semicolon == 1) {
							is_no_semicolon = 0;
						}
					}
					after_hasword = 0;
				} else if (is_semicolon == 1 && has_for == 0) {
					if (rowNum > 0) {
						row++;
						linetoHtml.append("<br />" + drawblank(number, rowNum));
					} else {
						row++;
						linetoHtml.append("<br />" + drawblank(number, rowNum));
					}
					is_semicolon = 0;
				} else if (has_blank == 0 && is_no_semicolon == 2) {
					// ����һ�е�һ���ַ����Ƿֺţ�Ҫ����
					linetoHtml.append("<br />");
					linetoHtml.append(this.drawblank(number, rowNum));
					has_blank = 1;
					// ע��״̬���
					is_no_semicolon = 0;
				}
				temp.append(c);
				// ��ͷ�е���
				if (has_blank == 0 && is_no_semicolon != 2) {
					linetoHtml.append(drawblank(number, rowNum));
					has_blank = 1;
				} else if (has_blank == 0 && is_no_semicolon == 2
						&& jinghao == 1) {
					// ��ʱ��'#'�ţ�Ҫ����
					linetoHtml.append("<br />");
					linetoHtml.append(drawblank(number, rowNum));
					has_blank = 1;
				} else if (has_blank == 0 && is_no_semicolon == 2) {
					// ��ʱҪ���ֺ�����ȥ���ʲ��û���
					has_blank = 1;
				}

			} else {

				if (temp.length() > 0) {
					String color = keywords.get(temp.toString());
					// System.out.println(keywords.get(temp.toString()));
					if (color != null) {
						if (temp.toString().charAt(0) == '#') {
							// ע��c���ԵĹؼ���#
							jinghao = 1;
							if (row != 1 && has_blank != 1)
								linetoHtml.append(drawblank(number, rowNum));
						}
						if (temp.toString().equals("for") && has_onesprit == 0
								&& has_Annotation == 0 && has_asterisk == 0
								&& has_secondsprit == 0)
							// ��ʱ����for��Ҫ��1
							has_for = 1;
						// �ؼ���
						if (c == '/' && has_onesprit == 0 && has_asterisk == 0
								&& marksflag != 1 && has_singelflag == 0) {
							// ������һ��б��,�ؼ��ֺ������ֻ����һ��б��
							has_onesprit = 1;
							over = 1;
							linetoHtml.append("<font color=\"" + color
									+ "\" face=\"" + font + "\" size=\"" + size
									+ "\">" + "<strong>" + temp.toString()
									+ "</strong>" + "</font>");
						} else if (has_secondsprit == 1 && has_asterisk == 0) {
							// ��ʱ��ע�ͣ�����Ҫ��ɫ
							linetoHtml.append("<font color=\"#3F7F5F\" face=\""
									+ font + "\" size=\"" + size + "\">"
									+ "<strong>" + temp.toString() + c
									+ "</strong>" + "</font>");
						} else if (c != '*' && has_asterisk == 1
								&& has_secondsprit == 0) {
							/* ��ʱ���Ǻ�ע�ͣ�����Ҫ��ɫ */
							linetoHtml.append("<font color=\"#3F7F5F\" face=\""
									+ font + "\" size=\"" + size + "\">"
									+ "<strong>" + temp.toString() + c
									+ "</strong>" + "</font>");
						} else if (c == '*' && has_asterisk == 1
								&& has_secondsprit == 0) {
							// ����һ���Ǻ�
							linetoHtml.append("<font color=\"#3F7F5F\" face=\""
									+ font + "\" size=\"" + size + "\">"
									+ "<strong>" + temp.toString() + c
									+ "</strong>" + "</font>");
							has_onesprit = 2;
						} else if (c == '@' && has_Annotation == 0
								&& marksflag == 0 && has_singelflag == 0) {
							// ���ע��
							has_Annotation = 1;
							linetoHtml.append("<font color=\"" + color
									+ "\" face=\"" + font + "\" size=\"" + size
									+ "\">" + "<strong>" + temp.toString()
									+ "</strong>" + "</font>");
							linetoHtml.append("<font color=\"" + straMap.get(c)
									+ "\" face=\"" + font + "\" size=\"" + size
									+ "\">" + "<strong>" + c + "</strong>"
									+ "</font>");

						} else if (has_Annotation == 1) {
							linetoHtml.append("<font color=\""
									+ straMap.get('@') + "\" face=\"" + font
									+ "\" size=\"" + size + "\">" + "<strong>"
									+ temp.toString() + c + "</strong>"
									+ "</font>");
						} else if (c == '"' && has_singelflag == 0) {
							// ��˫������û��ת���ַ�
							if (marksflag == 0) {
								linetoHtml.append("<font color=\"" + color
										+ "\" face=\"" + font + "\" size=\""
										+ size + "\">" + "<strong>"
										+ temp.toString() + "</strong>"
										+ "</font>");
								linetoHtml.append("<font color=\"" + markscolor
										+ "\" face=\"" + font + "\" size=\""
										+ size + "\">" + c + "</font>");
								marksflag = 1;
							} else if (has_changeCode == 0) {
								// ˫���Ž���
								linetoHtml.append("<font color=\"" + markscolor
										+ "\" face=\"" + font + "\" size=\""
										+ size + "\">" + temp.toString() + c
										+ "</font>");
								marksflag = 0;
							}
						} else if (c == '\'' && marksflag == 0) {
							// �е�����
							if (has_singelflag == 0) {
								linetoHtml.append("<font color=\"" + color
										+ "\" face=\"" + font + "\" size=\""
										+ size + "\">" + "<strong>"
										+ temp.toString() + "</strong>"
										+ "</font>");
								linetoHtml.append("<font color=\"" + markscolor
										+ "\" face=\"" + font + "\" size=\""
										+ size + "\">" + c + "</font>");
								has_singelflag = 1;
							} else {
								// �����Ž���
								linetoHtml.append("<font color=\"" + markscolor
										+ "\" face=\"" + font + "\" size=\""
										+ size + "\">" + temp.toString() + c
										+ "</font>");
								has_singelflag = 0;
							}
						} else if (c == '{' && has_Annotation == 0
								&& has_onesprit == 0 && has_secondsprit == 0
								&& has_singelflag == 0 && has_asterisk == 0
								&& marksflag == 0) {
							// ��ʱǰ��Ҫ����
							is_no_semicolon = 1;
							// ��ʱ���������ţ�����Ҫ����
							stack.push(c);
							// ��־λ����������ų���
							after_hasword = 1;
							number += 2;
							linetoHtml.append("<font color=\"" + typecolor
									+ "\" face=\"" + font + "\" size=\"" + size
									+ "\">" + "<strong>" + temp.toString()
									+ "</strong>" + "</font>");
							linetoHtml.append("<font face=\"" + font
									+ "\" size=\"" + size + "\">" + c
									+ "</font>");

						} else if (c == '}' && has_Annotation == 0
								&& has_onesprit == 0 && has_secondsprit == 0
								&& has_singelflag == 0 && has_asterisk == 0
								&& marksflag == 0) {
							// ��ʱǰ��Ҫ����
							is_no_semicolon = 1;
							// �Ҵ����ų���
							if (has_matching()) {
								// �����ų�ջ
								stack.pop();
								// �ָ�ԭ��ֵ
								number -= 2;
								linetoHtml.append("<font color=\"" + typecolor
										+ "\" face=\"" + font + "\" size=\""
										+ size + "\">" + temp.toString()
										+ "</font>");
								linetoHtml.append("<font face=\"" + font
										+ "\" size=\"" + size + "\">" + c
										+ "</font>");
							} else {
								// ��ƥ��
								linetoHtml.append("<font color=\"" + typecolor
										+ "\" face=\"" + font + "\" size=\""
										+ size + "\">" + temp.toString()
										+ "</font>");
								linetoHtml.append("<font face=\"" + font
										+ "\" size=\"" + size + "\">" + c
										+ "</font>");
							}
						} else if (c == ';' && after_hasword == 0
								&& is_semicolon == 0) {
							// �����Ѿ��зֺ�
							is_no_semicolon = 1;
							// ǰ���зֺ�
							is_semicolon = 1;
							linetoHtml.append("<font color=\"" + typecolor
									+ "\" face=\"" + font + "\" size=\"" + size
									+ "\">" + "<strong>" + temp.toString()
									+ "</strong>" + "</font>");
							linetoHtml.append("<font face=\"" + font
									+ "\" size=\"" + size + "\">" + c
									+ "</font>");
						} else {
							if (marksflag == 1 && has_singelflag == 0) {
								if (c == '\\' && has_changeCode == 0) {
									// ��ʱ���ܴ���ת���ַ�
									has_changeCode = 1;
								}
								linetoHtml.append("<font color=\"" + markscolor
										+ "\" face=\"" + font + "\" size="
										+ size + ">" + temp.toString() + c
										+ "</font>");
							} else if (has_singelflag == 1 && marksflag == 0) {
								// �ڵ��������棬Ҫ��ɫ
								linetoHtml.append("<font color=\"" + markscolor
										+ "\" face=\"" + font + "\" size="
										+ size + ">" + temp.toString() + c
										+ "</font>");
							} else {
								linetoHtml.append("<font color=\"" + color
										+ "\" face=\"" + font + "\" size="
										+ size + ">" + "<strong>"
										+ temp.toString() + "</strong>"
										+ "</font>");
								if (straMap.get(c) != null) {
									linetoHtml.append("<font face=\"" + font
											+ "\" size=" + size + ">"
											+ "<strong>" + straMap.get(c)
											+ "</strong>" + "</font>");
								} else {
									linetoHtml.append("<font face=\"" + font
											+ "\" size=" + size + ">" + c
											+ "</font>");
								}
							}
						}
					} else {
						// ǰ�治�ǹؼ���
						if (c == '/' && has_onesprit == 0 && has_asterisk == 0
								&& marksflag != 1 && has_singelflag == 0) {
							// ������һ��б��,�ؼ��ֺ������ֻ����һ��б��
							has_onesprit = 1;
							over = 1;
							linetoHtml
									.append("<font face=\"" + font + "\" size="
											+ size + ">" + "<strong>"
											+ temp.toString() + "</strong>"
											+ "</font>");
						} else if (has_secondsprit == 1 && has_asterisk == 0) {
							// ��ʱ��ע�ͣ�����Ҫ��ɫ
							linetoHtml.append("<font color=\"#3F7F5F\" face=\""
									+ font + "\" size=\"" + size + "\">"
									+ "<strong>" + temp.toString() + c
									+ "</strong>" + "</font>");
						} else if (c != '*' && has_asterisk == 1
								&& has_secondsprit == 0) {
							// ���Ǻ�ע�ͣ�����Ҫ��ɫ
							linetoHtml.append("<font color=\"#3F7F5F\" face=\""
									+ font + "\" size=\"" + size + "\">"
									+ "<strong>" + temp.toString() + c
									+ "</strong>" + "</font>");
						} else if (c == '*' && has_asterisk == 1
								&& has_secondsprit == 0) {
							has_onesprit = 2;
							linetoHtml.append("<font color=\"#3F7F5F\" face=\""
									+ font + "\" size=\"" + size + "\">"
									+ "<strong>" + temp.toString() + c
									+ "</strong>" + "</font>");
						} else if (c == '"' && marksflag == 0
								&& has_singelflag == 0) {
							// ��˫����
							marksflag = 1;
							linetoHtml.append("<font face=\"" + font
									+ "\" size=\"" + size + "\">"
									+ temp.toString() + "</font>");
							// ��˫���ű�ɫ
							linetoHtml.append("<font color=\"" + markscolor
									+ "\" face=\"" + font + "\" size=\"" + size
									+ "\">" + c + "</font>");
						} else if (c == '\"' && marksflag == 1
								&& has_changeCode == 0) {
							// ˫���Ž�β
							linetoHtml.append("<font color=\"" + markscolor
									+ "\" face=\"" + font + "\" size=\"" + size
									+ "\">" + temp.toString() + c + "</font>");
							marksflag = 0;
						} else if (c == '@' && has_Annotation == 0
								&& marksflag == 0 && has_singelflag == 0) {
							has_Annotation = 1;
							linetoHtml.append("<font face=\"" + font
									+ "\" size=\"" + size + "\">"
									+ temp.toString() + "</font>");
							linetoHtml.append("<font color=" + straMap.get(c)
									+ " face=\"" + font + "\" size=\"" + size
									+ "\">" + c + "</font>");
						} else if (has_Annotation == 1) {
							linetoHtml.append("<font color=" + straMap.get('@')
									+ " face=\"" + font + "\" size=\"" + size
									+ "\">" + temp.toString() + c + "</font>");
						} else if (c == '\'' && marksflag == 0
								&& has_singelflag == 0) {
							// �е�����
							linetoHtml.append("<font face=\"" + font
									+ "\" size=\"" + size + "\">"
									+ temp.toString() + "</font>");
							// �������ű�ɫ
							linetoHtml.append("<font color=\"" + markscolor
									+ "\" face=\"" + font + "\" size=\"" + size
									+ "\">" + c + "</font>");
							has_singelflag = 1;
						} else if (c == '\'' && has_singelflag == 1) {
							// �����Ž�β
							linetoHtml.append("<font color=\"" + markscolor
									+ "\" face=\"" + font + "\" size=\"" + size
									+ "\">" + temp.toString() + c + "</font>");
							has_singelflag = 0;
						} else if (c == '{' && has_Annotation == 0
								&& has_onesprit == 0 && has_secondsprit == 0
								&& has_singelflag == 0 && has_asterisk == 0
								&& marksflag == 0) {
							// ��ʱ���������ţ�����Ҫ����
							// ��ʱǰ��Ҫ����
							is_no_semicolon = 1;
							stack.push(c);
							// ��־λ����������ų���
							after_hasword = 1;
							number += 2;
							linetoHtml.append("<font face=\"" + font
									+ "\" size=\"" + size + "\">"
									+ temp.toString() + c + "</font>");
						} else if (c == '}' && has_Annotation == 0
								&& has_onesprit == 0 && has_secondsprit == 0
								&& has_singelflag == 0 && has_asterisk == 0
								&& marksflag == 0) {
							// ��ʱǰ��Ҫ����
							is_no_semicolon = 1;
							// �Ҵ����ų���
							if (has_matching()) {
								// �����ų�ջ
								stack.pop();
								// �ָ�ԭ��ֵ
								number -= 2;
								linetoHtml.append("<font face=\"" + font
										+ "\" size=\"" + size + "\">"
										+ temp.toString() + c + "</font>");
							} else {
								// �����Ų�ƥ��
								linetoHtml.append("<font face=\"" + font
										+ "\" size=\"" + size + "\">"
										+ temp.toString() + c + "</font>");
							}
						} else if (c == ';' && after_hasword == 0
								&& is_semicolon == 0) {
							is_no_semicolon = 1;
							// ǰ���зֺ�
							is_semicolon = 1;
							linetoHtml.append("<font face=\"" + font
									+ "\" size=\"" + size + "\">"
									+ temp.toString() + c + "</font>");
						} else {
							if (marksflag == 1) {
								// ��˫��������
								if (c == '\\' && has_changeCode == 0) {
									// ������ת���ַ�
									has_changeCode = 1;
								}
								linetoHtml.append("<font color=\"" + markscolor
										+ "\" face=\"" + font + "\" size=\""
										+ size + "\">" + temp.toString() + c
										+ "</font>");
							} else if (has_singelflag == 1 && marksflag == 0) {
								// �ڵ���������
								linetoHtml.append("<font color=\"" + markscolor
										+ "\" face=\"" + font + "\" size=\""
										+ size + "\">" + temp.toString() + c
										+ "</font>");
							} else {
								linetoHtml.append("<font face=\""
										+ font
										+ "\" size=\""
										+ size
										+ "\">"
										+ temp.toString()
										+ (straMap.get(c) == null ? c : straMap
												.get(c)) + "</font>");
							}
						}
					}
					// �����̱���ɾ����Ϊ�´���׼��
					temp.delete(0, temp.length());
				} else {
					// �����������
					/*
					 * if(c == '#'){ jinghao = 1; if(row != 1)
					 * linetoHtml.append(drawblank(number, rowNum)); }
					 */
					if (c == ' ' && has_blank == 0) {
						// ǰ���пո񣬲�������
					} else if (c == '\t' && has_blank == 0) {
						// c��ֵΪ'\t',�ڿ�ͷ����
					} else if (c == '/' && has_onesprit == 0
							&& has_asterisk == 0 && marksflag != 1
							&& has_singelflag == 0) {
						// ������һ��б��,�ؼ��ֺ������ֻ����һ��б��
						if (has_blank == 0) {
							linetoHtml.append(drawblank(number, rowNum));
							has_blank = 1;
						}
						has_onesprit = 1;
						over = 1;
					} else if (c == '/' && has_onesprit == 1
							&& has_asterisk == 0 && has_secondsprit == 0) {
						// ������б�ܣ���ʱҪ��ɫ
						if (has_blank == 0) {
							linetoHtml.append(drawblank(number, rowNum));
							has_blank = 1;
						}

						// ��ʱ�Ѿ�����һ��
						is_no_semicolon = 1;
						has_secondsprit = 1;
						linetoHtml.append("<font color=\"#3F7F5F\" face=\""
								+ font + "\" size=\"" + size + "\">" + "//"
								+ "</font>");
						over = 0;
					} else if (c != '/' && c != '*' && has_onesprit == 1
							&& has_asterisk == 0 && has_secondsprit == 0) {
						// ����������б��
						linetoHtml.append("<font face=\"" + font + "\" size=\""
								+ size + "\">" + "/" + "</font>");
						has_onesprit = 0;

					} else if (has_secondsprit == 1 && has_asterisk == 0) {
						// ��ʱ��ע�ͣ�����Ҫ��ɫ
						linetoHtml.append("<font color=\"#3F7F5F\" face=\""
								+ font + "\" size=\"" + size + "\">"
								+ "<strong>" + c + "</strong>" + "</font>");
						over = 0;
					} else if (c == '*' && has_onesprit == 1
							&& has_secondsprit == 0 && has_asterisk == 0
							&& marksflag != 1 && has_singelflag == 0) {
						// ����
						has_asterisk = 1;
						linetoHtml.append("<font color=\"#3F7F5F\" face=\""
								+ font + "\" size=\"" + size + "\">" + "/*"
								+ "</font>");
						over = 0;
					} else if (c == '/' && has_asterisk == 1
							&& has_onesprit != 2) {
						// ��ɫ
						linetoHtml.append("<font color=\"#3F7F5F\" face=\""
								+ font + "\" size=\"" + size + "\">" + c
								+ "</font>");
						over = 0;
					} else if (c != '*' && has_asterisk == 1
							&& has_secondsprit == 0 && c != '/') {
						// ����Ҫ��ɫ
						if (has_blank == 0) {
							linetoHtml.append(drawblank(number, rowNum));
							has_blank = 1;
						}
						if (c != '@') {
							linetoHtml.append("<font color=\"#3F7F5F\" face=\""
									+ font
									+ "\" size=\""
									+ size
									+ "\">"
									+ (straMap.get(c) == null ? c : straMap
											.get(c)) + "</font>");
						} else {
							linetoHtml.append("<font color=\"#3F7F5F\" face=\""
									+ font + "\" size=\"" + size + "\">" + c
									+ "</font>");
						}
						over = 0;
						has_onesprit = 0;
					} else if (c == '*' && has_asterisk == 1) {
						// ���ܿ�������ע��
						if (has_blank == 0) {
							linetoHtml.append(drawblank(number, rowNum));
							has_blank = 1;
						}
						has_onesprit = 2;
						linetoHtml.append("<font color=\"#3F7F5F\" face=\""
								+ font + "\" size=\"" + size + "\">" + "*"
								+ "</font>");
					} else if (c == '/' && has_asterisk == 1
							&& has_onesprit == 2) {
						// ȥ��ע��
						has_asterisk = 0;
						has_onesprit = 0;
						linetoHtml.append("<font color=\"#3F7F5F\" face=\""
								+ font + "\" size=\"" + size + "\">" + "/"
								+ "</font>");
						is_no_semicolon = 1;
						over = 0;
					} else if (c == '@' && has_Annotation == 0
							&& marksflag == 0 && has_singelflag == 0) {
						if (has_blank == 0) {
							linetoHtml.append(drawblank(number, rowNum));
							has_blank = 1;
						} else {
							this.changeblank(rowNum);
						}
						// ��ʱ��Ҫ��һ�еķ��Ż���
						is_no_semicolon = 1;
						has_Annotation = 1;
						linetoHtml.append("<font color=\"" + straMap.get(c)
								+ "\" face=\"" + font + "\" size=\"" + size
								+ "\">" + c + "</font>");
						// System.out.println(keywords.get(c));
					} else if (has_Annotation == 1) {
						linetoHtml.append("<font color=" + straMap.get('@')
								+ " face=\"" + font + "\" size=\"" + size
								+ "\">" + c + "</font>");
					} else if (c == '\"' && marksflag == 0
							&& has_singelflag == 0) {
						if (has_blank == 0 && is_no_semicolon != 2) {
							linetoHtml.append(drawblank(number, rowNum));
							has_blank = 1;
						} else if (has_blank == 0 && is_no_semicolon == 2) {
							// ���ü��к�
							has_blank = 1;
						}
						// ��˫����
						marksflag = 1;
						// ��˫���ű�ɫ
						linetoHtml.append("<font color=\"" + markscolor
								+ "\" face=\"" + font + "\" size=\"" + size
								+ "\">" + c + "</font>");
					} else if (c == '\"' && marksflag == 1
							&& has_changeCode == 0) {
						// ˫���Ž�β��û��ת���ַ�
						linetoHtml.append("<font color=\"" + markscolor
								+ "\" face=\"" + font + "\" size=\"" + size
								+ "\">" + c + "</font>");
						marksflag = 0;
					} else if (marksflag == 1 && has_singelflag == 0) {
						// ˫����û�н�β
						if (c == '\\' && has_changeCode == 0) {
							// ������ת���ַ�
							has_changeCode = 1;
						} else if (has_changeCode == 1 && c == '"') {
							// �Ѿ���ת���ַ�����ʱ����ɫ
							has_changeCode = 0;
						}
						linetoHtml.append("<font color=\"" + markscolor
								+ "\" face=\"" + font + "\" size=\"" + size
								+ "\">" + c + "</font>");
					} else if (c == '\'' && has_singelflag == 0
							&& marksflag == 0) {
						if (has_blank == 0) {
							linetoHtml.append(drawblank(number, rowNum));
							has_blank = 1;
						}
						// �е�����
						has_singelflag = 1;
						// �������ű�ɫ
						linetoHtml.append("<font color=\"" + markscolor
								+ "\" face=\"" + font + "\" size=\"" + size
								+ "\">" + c + "</font>");
					} else if (c == '\'' && has_singelflag == 1) {
						// �����Ž�β
						linetoHtml.append("<font color=\"" + markscolor
								+ "\" face=\"" + font + "\" size=\"" + size
								+ "\">" + c + "</font>");
						has_singelflag = 0;
					} else if (has_singelflag == 1 && marksflag == 0) {
						// �����Ž�β
						linetoHtml.append("<font color=\"" + markscolor
								+ "\" face=\"" + font + "\" size=\"" + size
								+ "\">" + c + "</font>");
					} else if (c == '{' && has_Annotation == 0
							&& has_onesprit == 0 && has_secondsprit == 0
							&& has_singelflag == 0 && has_asterisk == 0
							&& marksflag == 0) {
						// ��ʱǰ��Ҫ����
						is_no_semicolon = 1;
						if (has_blank == 0) {
							linetoHtml.append(drawblank(number, rowNum));
							has_blank = 1;
						}
						// ��ʱ���������ţ�����Ҫ����
						stack.push(c);
						// ��־λ����������ų���
						after_hasword = 1;
						number += 2;
						linetoHtml.append("<font face=\"" + font + "\" size=\""
								+ size + "\">" + c + "</font>");
					} else if (c == '}' && has_Annotation == 0
							&& has_onesprit == 0 && has_secondsprit == 0
							&& has_singelflag == 0 && has_asterisk == 0
							&& marksflag == 0) {

						// ��ʱҪ���һ��
						if (is_no_semicolon == 0 && has_blank == 0) {
							row++;
						} else if (is_no_semicolon == 0) {
							// ��������,��Ҳ�����ŵ����
							linetoHtml.append("<br />");
							row += 2;
						}
						if (has_blank == 1) {
							// ͬһ���Ѿ���ֵ
							same_line = 1;
						}
						// ��ʱ��һ��λ�ѱ����
						has_blank = 1;
						// ��ʱǰ��Ҫ����
						is_no_semicolon = 1;
						// �Ҵ����ų���
						if (has_matching()) {
							// �����ų�ջ
							stack.pop();
							// �ָ�ԭ��ֵ
							number -= 2;
							if (after_hasword == 1 || is_semicolon == 1) {
								// ǰ���д����Ż������ţ�Ϊͬһ��
								same_line = 1;
							}
							// ��ʱҪ����
							this.changeblank(rowNum);
							linetoHtml.append("<font face=\"" + font
									+ "\" size=\"" + size + "\">" + c
									+ "</font>");
						} else {
							// �����Ų�ƥ��
							linetoHtml.append("<font face=\"" + font
									+ "\" size=\"" + size + "\">" + c
									+ "</font>");
						}
					} else if (c == ';') {
						if (has_blank == 0 && is_no_semicolon != 2) {
							linetoHtml.append(drawblank(number, rowNum));
							has_blank = 1;
						} else if (has_blank == 1 && is_no_semicolon == 2) {
							// ��ʱ����ͨ���У�״̬Ϊ3
							is_no_semicolon = 3;
							linetoHtml.append("<font face=\"" + font
									+ "\" size=\"" + size + "\">" + c
									+ "</font><br />");
							break;
						}
						if (is_no_semicolon == 2 && has_blank == 0
								&& jinghao != 1) {
							// �жϣ���������ķֺ�������(Ҫע���ǵ�һ������)
							linetoHtml.append("<font face=\"" + font
									+ "\" size=\"" + size + "\">" + c
									+ "</font><br />");
							// �Ѿ��зֺţ�Ҫ�����
							is_no_semicolon = 0;
							row--;
						} else {
							// ǰ���зֺ�
							if ((after_hasword == 1 || is_semicolon == 1)
									&& has_for == 0) {
								// �зֺ���Ϊ�����ַ�
								// after_hasword = 0;
								// is_semicolon = 0;
								linetoHtml.append("<br />");
								row++;
								linetoHtml.append(drawblank(number, rowNum));
								linetoHtml.append("<font face=\"" + font
										+ "\" size=\"" + size + "\">" + c
										+ "</font>");
								// �ֽ����ʼ��Ϊ����
								has_blank = 0;
							} else if ((after_hasword == 1 || is_semicolon == 1)
									&& has_for == 1) {
								// ��ʱǰ����for
								linetoHtml.append("<font face=\"" + font
										+ "\" size=\"" + size + "\">" + c
										+ "</font>");
							} else {
								is_semicolon = 1;
								linetoHtml.append("<font face=\"" + font
										+ "\" size=\"" + size + "\">" + c
										+ "</font>");
								// ��ʱ�Ѿ��зֺ�
								is_no_semicolon = 1;
							}
						}
					} else {
						if (has_blank == 0 && is_no_semicolon != 2) {
							linetoHtml.append(drawblank(number, rowNum));
							has_blank = 1;
						} /*
						 * else if(c == '#'){ //C����ʱ��ͷ�ļ��Ĵ���
						 * linetoHtml.append("<br />");
						 * linetoHtml.append(drawblank(number, rowNum));
						 * has_blank = 1; is_no_semicolon = 1; }
						 */
						linetoHtml.append("<font face=\"" + font + "\" size=\""
								+ size + "\">"
								+ (straMap.get(c) == null ? c : straMap.get(c))
								+ "</font>");
						// ǰ���пո�
						// has_blank = 1;
					}
				}
			}
		}
		if (!(temp.toString()).equals("")) {
			String color = keywords.get(temp.toString());
			if (color != null) {
				// �ؼ���
				linetoHtml.append("<font color=\"" + color + "\" face=\""
						+ font + "\" size=\"" + size + "\">" + "<strong>"
						+ temp.toString() + "</strong>" + "</font>");
			} else if (has_asterisk == 1) {
				linetoHtml.append("<font color=\"#3F7F5F\" face=\"" + font
						+ "\" size=\"" + size + "\">" + "<strong>"
						+ temp.toString() + "</strong>" + "</font>");
				over = 0;
			} else if (has_secondsprit == 1) {
				linetoHtml.append("<font color=\"#3F7F5F\" face=\"" + font
						+ "\" size=\"" + size + "\">" + "<strong>"
						+ temp.toString() + "</strong>" + "</font>");
				over = 0;
			} else if (has_Annotation == 1) {
				linetoHtml.append("<font color=" + straMap.get('@')
						+ " face=\"" + font + "\" size=\"" + size + "\">"
						+ temp.toString() + "</font>");
			} else {
				linetoHtml.append("<font face=\"" + font + "\" size=\"" + size
						+ "\">" + temp.toString() + "</font>");
			}
		}
		if (over == 1) {
			// ����б��û��д��ȥ
			linetoHtml.append("<font face=\"" + font + "\" size=\"" + size
					+ "\">" + "/" + "</font>");
		}
		return linetoHtml.toString();
	}
}