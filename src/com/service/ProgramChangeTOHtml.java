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
	// 关键字集合

	private Map<String, String> keywords = new HashMap<String, String>();
	// 特殊字符集合
	private Map<Character, String> straMap = new HashMap<Character, String>();
	// 类型关键字
	private String[] type;
	// 类及方法关键字
	private String[] method;
	List<String> conent = null;
	private InputStream in = Object.class
			.getResourceAsStream("/Program_name.properties");
	private Properties pro = new Properties();
	// 标志位，看是不是拿到了一个星号
	private int has_asterisk = 0;
	// 设置关键字颜色
	private String typecolor;
	// 设置标志(双引号)大小
	private String markscolor;
	// 设置注解颜色
	private String Annotationcolor;
	// 设置字体
	private String font;
	// 设置字体大小
	private String size;
	// 判断大括号并将其压入栈
	private Stack<Character> stack = new Stack<Character>();
	// 统计空格数目,初始化为2
	private int number = 2;
	private int row = 0;
	// 标志,判断是不是在同一行 1是，0不是
	private int same_line = 1;
	// 标记，判断某一行是否出现了分号，有的话要加到上一行,默认是没有，1、有 2、加到上一行，下一行换行
	private int is_no_semicolon = 0;
	// 具体某一行的代码
	private StringBuffer linetoHtml = new StringBuffer();

	// 属性，保存上一行代码的长度，供移位时用
	private int f_number = 0;

	// 属性，表示有没有#号,默认是没有
	private int jinghao = 0;

	// 方法，判断是否有匹配的大括号
	public boolean has_matching() {
		boolean b = false;
		if (stack.peek() == '{') {
			b = true;
		}
		return b;
	}

	// 方法，将行首为换行的那些行家空格
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

	// 方法，打印换行后的空格
	public void changeblank(int rowNum) {
		if (same_line == 1) {
			row++;
			linetoHtml.append("<br />");
			linetoHtml.append(drawblank(number, rowNum));
		} else if (is_no_semicolon == 0 && same_line == 0) {
			linetoHtml.append(drawblank(number, rowNum));
			same_line = 1;
		} else if (is_no_semicolon == 1 && same_line == 0) {
			// 此时处理没有分号的(如注解)
			row--;
			linetoHtml.append(drawblank(number, rowNum));
			// 标记变为0
			// is_no_semicolon = 0;
		}
	}

	// 方法，统计空格的个数
	public String drawblank(int allNumber, int rowNum) {
		String tempStr = "";
		String str = "";
		for (int i = 0; i < allNumber; i++) {
			tempStr += "&nbsp;";
		}
		if (rowNum > 0) {
			// 带行号
			str = "<font face=\"" + font + "\" size=\"" + size + "\">" + row
					+ tempStr + "</font>";
		} else {
			str = tempStr;
		}
		return str;
	}

	public void setFont(String type_color, String marks_color,
			String Annotation_color, String font1, String size1) {
		// 设置字体的颜色，大小，字体，初始化在配置文件里
		try {
			pro.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// // 设置关键字颜色
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
		// // 设置标记颜色
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
		// // 设置注解颜色
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
		// 设置字体
		/*
		 * String t1 = pro.getProperty("font"); try { t = new
		 * String(t1.getBytes("ISO8859-1"), "utf-8"); } catch
		 * (UnsupportedEncodingException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } String[] type1 = t.split(" "); for (int i = 0;
		 * i < type1.length; i++) { if (type1[i].equals(font1)) { font =
		 * type1[i]; break; } }
		 */

		// 设置字体大小
		t = pro.getProperty("size");
		String[] type1 = t.split(" ");
		for (int i = 0; i < type1.length; i++) {
			if (type1[i].equals(size1)) {
				size = type1[i];
				break;
			}
		}
	}

	// 初始化关键字，并拿到从文件读出来的内容,传文件类型并从配置文件中读出关键字
	public void init(String type_color, String marks_color,
			String Annotation_color, String font, String size, String suffix) {
		// 初始化，获取字体信息
		this.setFont(type_color, marks_color, Annotation_color, font, size);
		// 将要转化的特殊字符加入
		straMap.put(' ', "&nbsp;");
		straMap.put('<', "&lt;");
		straMap.put('&', "&amp;");
		straMap.put('>', "&gt;");
		// 将@放进去
		straMap.put('@', Annotationcolor);

		// 初始化关键字
		try {
			pro.load(in);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 过程变量，读取配置文件
		String t = pro.getProperty(suffix + "_type");

		type = t.split(" ");

		String m = pro.getProperty(suffix + "_method");
		method = m.split(" ");
		// 初始化字符关键字
		for (String s : type) {
			keywords.put(s, typecolor);
		}
		// 初始化类关键字
		for (String s : method) {
			keywords.put(s, typecolor);
		}
		// 拿到读出来的内容
		conent = super.getConent();
	}

	@Override
	public boolean changeProgramtoHtml(String type_color, String marks_color,
			String Annotation_color, String font, String size, String suffix) {
		// TODO Auto-generated method stub
		// 根据内容将java转化为html
		boolean b = false;
		init(type_color, marks_color, Annotation_color, font, size, suffix);
		List<String> cs = super.getChange();
		// 添加头文件
		cs
				.add("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
		cs.add("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		cs.add("<body>");
		for (String line : conent) {
			// System.out.println(line);
			String s = this.changToHtmlLine(line, 0);

			// 将linetoHtml清空
			linetoHtml.delete(0, linetoHtml.length());
			// 下一行
			same_line = 0;
			if (!s.equals("")) {
				if (jinghao == 1) {
					s += "<br />";
					jinghao = 0;
					cs.add(s);
					continue;
				}
				if (is_no_semicolon == 1) {
					// 同一行已经有分号
					s += "<br />";
					is_no_semicolon = 0;
				} else if (is_no_semicolon == 3) {
					// 此时要添加换行输出
					s = this.addBlank(s);
					is_no_semicolon = 0;
				} else if (has_asterisk == 1) {
					// 注释，要换行
					s += "<br />";
				} else // 后面分号要提前
				{
					is_no_semicolon = 2;
				}
			} else {
				row--;
			}
			// 添加到changetohtml里面
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
		// 添加头文件
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
					// 同一行已经有分号
					s += "<br />";
					is_no_semicolon = 0;
				} else if (is_no_semicolon == 3) {
					// 此时要添加换行输出
					s = "<br />" + s;
					s = this.addBlank(s);
					is_no_semicolon = 0;
				} else if (has_asterisk == 1) {
					// 注释，要换行
					s += "<br />";
				} else // 后面分号要提前
				{
					is_no_semicolon = 2;
				}
			} else {
				row--;
			}
			// 添加到changetohtml里面
			cs.add(s);
			f_number = line.length();
		}
		cs.add("</body>");
		super.setChange(cs);
		b = true;
		return b;
	}

	// flag：标志位.0:不显示行号,其他：显示行号
	public String changToHtmlLine(String line, int rowNum) {

		// 标志位，看是不是拿到了一个斜杠
		int has_onesprit = 0;

		// 标志位，看有没有第二个斜杠
		int has_secondsprit = 0;

		// 标志位，判断有没有转义字符
		int has_changeCode = 0;
		// 临时变量
		StringBuffer temp = new StringBuffer();
		// 判断，表示有没有双引号
		int marksflag = 0;

		// 判断是否最后有写入斜杠
		int over = 0;

		// 判断，表示有没有单引号
		int has_singelflag = 0;
		// 标志位，判断是不是Annotation
		int has_Annotation = 0;

		// 判断是有分号，并进行换行
		int is_semicolon = 0;

		// 判断大括号后面有没有特殊字符，要换行
		int after_hasword = 0;

		// 判断在最开始是否要忽略空格
		int has_blank = 0;
		// 判断有没有存在for，如果有，不用换行
		int has_for = 0;

		char[] chars = line.toCharArray();
		// 遍历每一行
		for (char c : chars) {

			if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')
					|| (c >= '0' && c <= '9') || (c == '#')) {
				// 注意'#'号的换行
				if (c == '#')
					jinghao = 1;
				if (has_onesprit == 1 && has_secondsprit == 0
						&& has_asterisk == 0) {
					// 没有两根斜杠
					linetoHtml.append("<font face=\"" + font + "\" size="
							+ size + ">" + "/" + "</font>");
					has_onesprit = 0;
					over = 0;
				} else if (c != '"' && has_changeCode == 1) {
					// 没有构成转义字符
					has_changeCode = 0;
				} else if (after_hasword == 1) {
					// 左边有大括号
					if (rowNum > 0) {
						// 换行row的值要加一
						row++;
						linetoHtml.append("<br />" + drawblank(number, rowNum));
						// 前面已经换行，后面不要换行
						if (is_no_semicolon == 1) {
							is_no_semicolon = 0;
						}
					} else {
						row++;
						linetoHtml.append("<br />" + drawblank(number, rowNum));
						// 前面已经换行，后面不要换行
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
					// 下面一行第一个字符不是分号，要换行
					linetoHtml.append("<br />");
					linetoHtml.append(this.drawblank(number, rowNum));
					has_blank = 1;
					// 注意状态清空
					is_no_semicolon = 0;
				}
				temp.append(c);
				// 开头有单词
				if (has_blank == 0 && is_no_semicolon != 2) {
					linetoHtml.append(drawblank(number, rowNum));
					has_blank = 1;
				} else if (has_blank == 0 && is_no_semicolon == 2
						&& jinghao == 1) {
					// 此时有'#'号，要换行
					linetoHtml.append("<br />");
					linetoHtml.append(drawblank(number, rowNum));
					has_blank = 1;
				} else if (has_blank == 0 && is_no_semicolon == 2) {
					// 此时要将分号提上去，故不用换行
					has_blank = 1;
				}

			} else {

				if (temp.length() > 0) {
					String color = keywords.get(temp.toString());
					// System.out.println(keywords.get(temp.toString()));
					if (color != null) {
						if (temp.toString().charAt(0) == '#') {
							// 注意c语言的关键字#
							jinghao = 1;
							if (row != 1 && has_blank != 1)
								linetoHtml.append(drawblank(number, rowNum));
						}
						if (temp.toString().equals("for") && has_onesprit == 0
								&& has_Annotation == 0 && has_asterisk == 0
								&& has_secondsprit == 0)
							// 此时存在for，要变1
							has_for = 1;
						// 关键字
						if (c == '/' && has_onesprit == 0 && has_asterisk == 0
								&& marksflag != 1 && has_singelflag == 0) {
							// 后面有一个斜杠,关键字后面最多只能有一个斜杠
							has_onesprit = 1;
							over = 1;
							linetoHtml.append("<font color=\"" + color
									+ "\" face=\"" + font + "\" size=\"" + size
									+ "\">" + "<strong>" + temp.toString()
									+ "</strong>" + "</font>");
						} else if (has_secondsprit == 1 && has_asterisk == 0) {
							// 此时有注释，代码要变色
							linetoHtml.append("<font color=\"#3F7F5F\" face=\""
									+ font + "\" size=\"" + size + "\">"
									+ "<strong>" + temp.toString() + c
									+ "</strong>" + "</font>");
						} else if (c != '*' && has_asterisk == 1
								&& has_secondsprit == 0) {
							/* 此时有星号注释，代码要变色 */
							linetoHtml.append("<font color=\"#3F7F5F\" face=\""
									+ font + "\" size=\"" + size + "\">"
									+ "<strong>" + temp.toString() + c
									+ "</strong>" + "</font>");
						} else if (c == '*' && has_asterisk == 1
								&& has_secondsprit == 0) {
							// 有了一个星号
							linetoHtml.append("<font color=\"#3F7F5F\" face=\""
									+ font + "\" size=\"" + size + "\">"
									+ "<strong>" + temp.toString() + c
									+ "</strong>" + "</font>");
							has_onesprit = 2;
						} else if (c == '@' && has_Annotation == 0
								&& marksflag == 0 && has_singelflag == 0) {
							// 添加注解
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
							// 有双引号且没有转义字符
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
								// 双引号结束
								linetoHtml.append("<font color=\"" + markscolor
										+ "\" face=\"" + font + "\" size=\""
										+ size + "\">" + temp.toString() + c
										+ "</font>");
								marksflag = 0;
							}
						} else if (c == '\'' && marksflag == 0) {
							// 有单引号
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
								// 单引号结束
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
							// 此时前面要换行
							is_no_semicolon = 1;
							// 此时遇到大括号，后面要缩进
							stack.push(c);
							// 标志位，有左大括号出现
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
							// 此时前面要换行
							is_no_semicolon = 1;
							// 右大括号出现
							if (has_matching()) {
								// 左括号出栈
								stack.pop();
								// 恢复原来值
								number -= 2;
								linetoHtml.append("<font color=\"" + typecolor
										+ "\" face=\"" + font + "\" size=\""
										+ size + "\">" + temp.toString()
										+ "</font>");
								linetoHtml.append("<font face=\"" + font
										+ "\" size=\"" + size + "\">" + c
										+ "</font>");
							} else {
								// 不匹配
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
							// 本行已经有分号
							is_no_semicolon = 1;
							// 前面有分号
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
									// 此时可能存在转义字符
									has_changeCode = 1;
								}
								linetoHtml.append("<font color=\"" + markscolor
										+ "\" face=\"" + font + "\" size="
										+ size + ">" + temp.toString() + c
										+ "</font>");
							} else if (has_singelflag == 1 && marksflag == 0) {
								// 在单引号里面，要变色
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
						// 前面不是关键字
						if (c == '/' && has_onesprit == 0 && has_asterisk == 0
								&& marksflag != 1 && has_singelflag == 0) {
							// 后面有一个斜杠,关键字后面最多只能有一个斜杠
							has_onesprit = 1;
							over = 1;
							linetoHtml
									.append("<font face=\"" + font + "\" size="
											+ size + ">" + "<strong>"
											+ temp.toString() + "</strong>"
											+ "</font>");
						} else if (has_secondsprit == 1 && has_asterisk == 0) {
							// 此时有注释，代码要变色
							linetoHtml.append("<font color=\"#3F7F5F\" face=\""
									+ font + "\" size=\"" + size + "\">"
									+ "<strong>" + temp.toString() + c
									+ "</strong>" + "</font>");
						} else if (c != '*' && has_asterisk == 1
								&& has_secondsprit == 0) {
							// 有星号注释，代码要变色
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
							// 有双引号
							marksflag = 1;
							linetoHtml.append("<font face=\"" + font
									+ "\" size=\"" + size + "\">"
									+ temp.toString() + "</font>");
							// 将双引号变色
							linetoHtml.append("<font color=\"" + markscolor
									+ "\" face=\"" + font + "\" size=\"" + size
									+ "\">" + c + "</font>");
						} else if (c == '\"' && marksflag == 1
								&& has_changeCode == 0) {
							// 双引号结尾
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
							// 有单引号
							linetoHtml.append("<font face=\"" + font
									+ "\" size=\"" + size + "\">"
									+ temp.toString() + "</font>");
							// 将单引号变色
							linetoHtml.append("<font color=\"" + markscolor
									+ "\" face=\"" + font + "\" size=\"" + size
									+ "\">" + c + "</font>");
							has_singelflag = 1;
						} else if (c == '\'' && has_singelflag == 1) {
							// 单引号结尾
							linetoHtml.append("<font color=\"" + markscolor
									+ "\" face=\"" + font + "\" size=\"" + size
									+ "\">" + temp.toString() + c + "</font>");
							has_singelflag = 0;
						} else if (c == '{' && has_Annotation == 0
								&& has_onesprit == 0 && has_secondsprit == 0
								&& has_singelflag == 0 && has_asterisk == 0
								&& marksflag == 0) {
							// 此时遇到大括号，后面要缩进
							// 此时前面要换行
							is_no_semicolon = 1;
							stack.push(c);
							// 标志位，有左大括号出现
							after_hasword = 1;
							number += 2;
							linetoHtml.append("<font face=\"" + font
									+ "\" size=\"" + size + "\">"
									+ temp.toString() + c + "</font>");
						} else if (c == '}' && has_Annotation == 0
								&& has_onesprit == 0 && has_secondsprit == 0
								&& has_singelflag == 0 && has_asterisk == 0
								&& marksflag == 0) {
							// 此时前面要换行
							is_no_semicolon = 1;
							// 右大括号出现
							if (has_matching()) {
								// 左括号出栈
								stack.pop();
								// 恢复原来值
								number -= 2;
								linetoHtml.append("<font face=\"" + font
										+ "\" size=\"" + size + "\">"
										+ temp.toString() + c + "</font>");
							} else {
								// 右括号不匹配
								linetoHtml.append("<font face=\"" + font
										+ "\" size=\"" + size + "\">"
										+ temp.toString() + c + "</font>");
							}
						} else if (c == ';' && after_hasword == 0
								&& is_semicolon == 0) {
							is_no_semicolon = 1;
							// 前面有分号
							is_semicolon = 1;
							linetoHtml.append("<font face=\"" + font
									+ "\" size=\"" + size + "\">"
									+ temp.toString() + c + "</font>");
						} else {
							if (marksflag == 1) {
								// 在双引号里面
								if (c == '\\' && has_changeCode == 0) {
									// 可能有转义字符
									has_changeCode = 1;
								}
								linetoHtml.append("<font color=\"" + markscolor
										+ "\" face=\"" + font + "\" size=\""
										+ size + "\">" + temp.toString() + c
										+ "</font>");
							} else if (has_singelflag == 1 && marksflag == 0) {
								// 在单引号里面
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
					// 将过程变量删掉，为下次做准备
					temp.delete(0, temp.length());
				} else {
					// 遇到特殊符号
					/*
					 * if(c == '#'){ jinghao = 1; if(row != 1)
					 * linetoHtml.append(drawblank(number, rowNum)); }
					 */
					if (c == ' ' && has_blank == 0) {
						// 前面有空格，不做处理
					} else if (c == '\t' && has_blank == 0) {
						// c的值为'\t',在开头忽略
					} else if (c == '/' && has_onesprit == 0
							&& has_asterisk == 0 && marksflag != 1
							&& has_singelflag == 0) {
						// 后面有一个斜杠,关键字后面最多只能有一个斜杠
						if (has_blank == 0) {
							linetoHtml.append(drawblank(number, rowNum));
							has_blank = 1;
						}
						has_onesprit = 1;
						over = 1;
					} else if (c == '/' && has_onesprit == 1
							&& has_asterisk == 0 && has_secondsprit == 0) {
						// 有两根斜杠，此时要变色
						if (has_blank == 0) {
							linetoHtml.append(drawblank(number, rowNum));
							has_blank = 1;
						}

						// 此时已经有了一行
						is_no_semicolon = 1;
						has_secondsprit = 1;
						linetoHtml.append("<font color=\"#3F7F5F\" face=\""
								+ font + "\" size=\"" + size + "\">" + "//"
								+ "</font>");
						over = 0;
					} else if (c != '/' && c != '*' && has_onesprit == 1
							&& has_asterisk == 0 && has_secondsprit == 0) {
						// 不存在两根斜杠
						linetoHtml.append("<font face=\"" + font + "\" size=\""
								+ size + "\">" + "/" + "</font>");
						has_onesprit = 0;

					} else if (has_secondsprit == 1 && has_asterisk == 0) {
						// 此时有注释，代码要变色
						linetoHtml.append("<font color=\"#3F7F5F\" face=\""
								+ font + "\" size=\"" + size + "\">"
								+ "<strong>" + c + "</strong>" + "</font>");
						over = 0;
					} else if (c == '*' && has_onesprit == 1
							&& has_secondsprit == 0 && has_asterisk == 0
							&& marksflag != 1 && has_singelflag == 0) {
						// 创建
						has_asterisk = 1;
						linetoHtml.append("<font color=\"#3F7F5F\" face=\""
								+ font + "\" size=\"" + size + "\">" + "/*"
								+ "</font>");
						over = 0;
					} else if (c == '/' && has_asterisk == 1
							&& has_onesprit != 2) {
						// 变色
						linetoHtml.append("<font color=\"#3F7F5F\" face=\""
								+ font + "\" size=\"" + size + "\">" + c
								+ "</font>");
						over = 0;
					} else if (c != '*' && has_asterisk == 1
							&& has_secondsprit == 0 && c != '/') {
						// 代码要变色
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
						// 可能可以销毁注释
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
						// 去掉注释
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
						// 此时需要下一行的符号换行
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
							// 不用加行号
							has_blank = 1;
						}
						// 有双引号
						marksflag = 1;
						// 将双引号变色
						linetoHtml.append("<font color=\"" + markscolor
								+ "\" face=\"" + font + "\" size=\"" + size
								+ "\">" + c + "</font>");
					} else if (c == '\"' && marksflag == 1
							&& has_changeCode == 0) {
						// 双引号结尾且没有转移字符
						linetoHtml.append("<font color=\"" + markscolor
								+ "\" face=\"" + font + "\" size=\"" + size
								+ "\">" + c + "</font>");
						marksflag = 0;
					} else if (marksflag == 1 && has_singelflag == 0) {
						// 双引号没有结尾
						if (c == '\\' && has_changeCode == 0) {
							// 可能有转义字符
							has_changeCode = 1;
						} else if (has_changeCode == 1 && c == '"') {
							// 已经有转义字符，此时不变色
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
						// 有单引号
						has_singelflag = 1;
						// 将单引号变色
						linetoHtml.append("<font color=\"" + markscolor
								+ "\" face=\"" + font + "\" size=\"" + size
								+ "\">" + c + "</font>");
					} else if (c == '\'' && has_singelflag == 1) {
						// 单引号结尾
						linetoHtml.append("<font color=\"" + markscolor
								+ "\" face=\"" + font + "\" size=\"" + size
								+ "\">" + c + "</font>");
						has_singelflag = 0;
					} else if (has_singelflag == 1 && marksflag == 0) {
						// 单引号结尾
						linetoHtml.append("<font color=\"" + markscolor
								+ "\" face=\"" + font + "\" size=\"" + size
								+ "\">" + c + "</font>");
					} else if (c == '{' && has_Annotation == 0
							&& has_onesprit == 0 && has_secondsprit == 0
							&& has_singelflag == 0 && has_asterisk == 0
							&& marksflag == 0) {
						// 此时前面要换行
						is_no_semicolon = 1;
						if (has_blank == 0) {
							linetoHtml.append(drawblank(number, rowNum));
							has_blank = 1;
						}
						// 此时遇到大括号，后面要缩进
						stack.push(c);
						// 标志位，有左大括号出现
						after_hasword = 1;
						number += 2;
						linetoHtml.append("<font face=\"" + font + "\" size=\""
								+ size + "\">" + c + "</font>");
					} else if (c == '}' && has_Annotation == 0
							&& has_onesprit == 0 && has_secondsprit == 0
							&& has_singelflag == 0 && has_asterisk == 0
							&& marksflag == 0) {

						// 此时要多加一行
						if (is_no_semicolon == 0 && has_blank == 0) {
							row++;
						} else if (is_no_semicolon == 0) {
							// 先有括号,后也是括号的情况
							linetoHtml.append("<br />");
							row += 2;
						}
						if (has_blank == 1) {
							// 同一行已经有值
							same_line = 1;
						}
						// 此时第一个位已被填好
						has_blank = 1;
						// 此时前面要换行
						is_no_semicolon = 1;
						// 右大括号出现
						if (has_matching()) {
							// 左括号出栈
							stack.pop();
							// 恢复原来值
							number -= 2;
							if (after_hasword == 1 || is_semicolon == 1) {
								// 前面有大括号或是引号，为同一行
								same_line = 1;
							}
							// 此时要换行
							this.changeblank(rowNum);
							linetoHtml.append("<font face=\"" + font
									+ "\" size=\"" + size + "\">" + c
									+ "</font>");
						} else {
							// 右括号不匹配
							linetoHtml.append("<font face=\"" + font
									+ "\" size=\"" + size + "\">" + c
									+ "</font>");
						}
					} else if (c == ';') {
						if (has_blank == 0 && is_no_semicolon != 2) {
							linetoHtml.append(drawblank(number, rowNum));
							has_blank = 1;
						} else if (has_blank == 1 && is_no_semicolon == 2) {
							// 此时是普通换行，状态为3
							is_no_semicolon = 3;
							linetoHtml.append("<font face=\"" + font
									+ "\" size=\"" + size + "\">" + c
									+ "</font><br />");
							break;
						}
						if (is_no_semicolon == 2 && has_blank == 0
								&& jinghao != 1) {
							// 判断，并将下面的分号提上来(要注意是第一个单词)
							linetoHtml.append("<font face=\"" + font
									+ "\" size=\"" + size + "\">" + c
									+ "</font><br />");
							// 已经有分号，要变回来
							is_no_semicolon = 0;
							row--;
						} else {
							// 前面有分号
							if ((after_hasword == 1 || is_semicolon == 1)
									&& has_for == 0) {
								// 有分号作为特殊字符
								// after_hasword = 0;
								// is_semicolon = 0;
								linetoHtml.append("<br />");
								row++;
								linetoHtml.append(drawblank(number, rowNum));
								linetoHtml.append("<font face=\"" + font
										+ "\" size=\"" + size + "\">" + c
										+ "</font>");
								// 又将其初始化为行首
								has_blank = 0;
							} else if ((after_hasword == 1 || is_semicolon == 1)
									&& has_for == 1) {
								// 此时前面有for
								linetoHtml.append("<font face=\"" + font
										+ "\" size=\"" + size + "\">" + c
										+ "</font>");
							} else {
								is_semicolon = 1;
								linetoHtml.append("<font face=\"" + font
										+ "\" size=\"" + size + "\">" + c
										+ "</font>");
								// 此时已经有分号
								is_no_semicolon = 1;
							}
						}
					} else {
						if (has_blank == 0 && is_no_semicolon != 2) {
							linetoHtml.append(drawblank(number, rowNum));
							has_blank = 1;
						} /*
						 * else if(c == '#'){ //C语言时对头文件的处理
						 * linetoHtml.append("<br />");
						 * linetoHtml.append(drawblank(number, rowNum));
						 * has_blank = 1; is_no_semicolon = 1; }
						 */
						linetoHtml.append("<font face=\"" + font + "\" size=\""
								+ size + "\">"
								+ (straMap.get(c) == null ? c : straMap.get(c))
								+ "</font>");
						// 前面有空格
						// has_blank = 1;
					}
				}
			}
		}
		if (!(temp.toString()).equals("")) {
			String color = keywords.get(temp.toString());
			if (color != null) {
				// 关键字
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
			// 最后的斜杠没有写进去
			linetoHtml.append("<font face=\"" + font + "\" size=\"" + size
					+ "\">" + "/" + "</font>");
		}
		return linetoHtml.toString();
	}
}