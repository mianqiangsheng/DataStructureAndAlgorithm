package picture.similarity.user;


import org.opencv.core.Core;
import picture.similarity.opDB.Insert;
import picture.similarity.opDB.Select;
import picture.similarity.prepare.getColor;
import picture.similarity.prepare.getPicture;
import picture.similarity.prepare.getShape;
import picture.similarity.prepare.getTexture;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.Map.Entry;

public class main {
	private String filepic = null;//�����ȡ��ͼ��·��
	private String filepath = null;//�����ȡ���ļ�·��
	private String pathtxt = "I:\\path.txt";//����·��
	private String colortxt = "I:\\color.txt";//�����ȡ����ɫ����
	private String texturetxt = "I:\\texture.txt";//�����ȡ����������
	private String shapetxt = "I:\\shape.txt";//�����ȡ����״����
	JFrame frame = new JFrame("ͼ�����ϵͳ");
	private JLabel label_2;//ѡ���ͼƬ
	private JTextField texts;//ͼ��·��
	private JTextField text;//ͼƬ·��
	private JTextField text1;//ͼƬ·��
	private JTextField text2;//ͼƬ·��
	private JTextField text3;//ͼƬ·��
	JLabel[] img = new JLabel[12];
	JLabel[] imgpath = new JLabel[12];


	double[] color = new double[9];//����ͼ�����ɫ����
	double[] texture = new double[9];//����ͼ�����������
	double[] hu = new double[8];//����ͼ�����״����

	private void picture_prepare() {

	}
	private void init() {
		frame.setBounds(100, 100, 1700, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel label_db = new JLabel("����ͼ��:");
		label_db.setFont(new Font("����", Font.PLAIN, 15));
		label_db.setBounds(15, 15, 70, 25);
		frame.add(label_db);

		texts = new JTextField();
		texts.setBounds(90, 15, 170, 25);
		frame.add(texts);
		texts.setColumns(10);

		JButton brows = new JButton("���");
		brows.setFont(new Font("����", Font.PLAIN, 15));
		brows.setBounds(260, 15, 70, 25);
		frame.add(brows);
		//������ť����
		brows.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				TanChuang1();
			}
		});

		JButton jz = new JButton("����");
		jz.setFont(new Font("����", Font.PLAIN, 15));
		jz.setBounds(330, 15, 70, 25);
		frame.add(jz);
		//������ť����
		jz.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					JiaZai();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JLabel label = new JLabel("ѡ���ļ�:");
		label.setFont(new Font("����", Font.PLAIN, 15));
		label.setBounds(15, 50, 70, 25);
		frame.add(label);

		text = new JTextField();
		text.setBounds(90, 50, 170, 25);
		frame.add(text);
		text.setColumns(10);

		JButton brow = new JButton("���");
		brow.setFont(new Font("����", Font.PLAIN, 15));
		brow.setBounds(260, 50, 70, 25);
		frame.add(brow);
		//������ť����
		brow.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					TanChuang();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JLabel label_1 = new JLabel("��ǰͼ��:");
		label_1.setFont(new Font("����", Font.PLAIN, 15));
		label_1.setBounds(15, 85, 80, 25);
		frame.add(label_1);

		label_2 = new JLabel("");
		label_2.setBounds(30, 135, 300, 200);
		frame.add(label_2);

		for(int i=0;i<12;i++) {
			img[i] = new JLabel();
			imgpath[i] = new JLabel();
		}
		JLabel label1 = new JLabel("����������:");
		label1.setFont(new Font("����", Font.PLAIN, 15));
		label1.setBounds(15, 385, 100, 25);
		frame.add(label1);

		JButton b1 = new JButton("������ɫ����");
		b1.setFont(new Font("����", Font.PLAIN, 15));
		b1.setBounds(100, 435, 130, 30);
		frame.add(b1);
		//��ɫ������ť����
		b1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					color();//������ɫ����
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});

		JButton b2 = new JButton("�����������");
		b2.setFont(new Font("����", Font.PLAIN, 15));
		b2.setBounds(100, 485, 130, 30);
		frame.add(b2);
		//���������ť����
		b2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					texture();
				} catch (Exception e1) {
					e1.printStackTrace();
				}//�����������
			}
		});



		JButton b3 = new JButton("������״����");
		b3.setFont(new Font("����", Font.PLAIN, 15));
		b3.setBounds(100, 535, 130, 30);
		frame.add(b3);
		//���������ť����
		b3.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					shape();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//�����������
			}
		});

		JLabel zong = new JLabel("�ۺϼ���(����������ռȨ��):");
		zong.setFont(new Font("����", Font.PLAIN, 15));
		zong.setBounds(15, 610, 210, 25);
		frame.add(zong);

		JLabel zong1 = new JLabel("��ɫȨ��(0.00-1.00):");
		zong1.setFont(new Font("����", Font.PLAIN, 15));
		zong1.setBounds(60, 650, 180, 25);
		frame.add(zong1);
		text1 = new JTextField();
		text1.setBounds(220, 650, 50, 25);
		frame.add(text1);
		text1.setColumns(10);

		JLabel zong2 = new JLabel("����Ȩ��(0.00-1.00):");
		zong2.setFont(new Font("����", Font.PLAIN, 15));
		zong2.setBounds(60, 690, 180, 25);
		frame.add(zong2);
		text2 = new JTextField();
		text2.setBounds(220, 690, 50, 25);
		frame.add(text2);
		text2.setColumns(10);

		JLabel zong3 = new JLabel("��״Ȩ��(0.00-1.00):");
		zong3.setFont(new Font("����", Font.PLAIN, 15));
		zong3.setBounds(60, 730, 180, 25);
		frame.add(zong3);
		text3 = new JTextField();
		text3.setBounds(220, 730, 50, 25);
		frame.add(text3);
		text3.setColumns(10);

		JButton b4 = new JButton("�ۺϼ���");
		b4.setFont(new Font("����", Font.PLAIN, 15));
		b4.setBounds(100, 780, 130, 30);
		frame.add(b4);
		//�ۺϼ�����ť����
		b4.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try {
					zonghe();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}//�����ۺϼ���
			}
		});

		frame.setVisible(true);
	}
	public String TanChuang1(){
		JFileChooser chooser=new JFileChooser("E:\\");
		chooser.setDialogTitle("��ѡ��ͼ��");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//����ѡ���
		int returnVal=chooser.showOpenDialog(null);
		//���ѡ�����ļ�
		if(JFileChooser.APPROVE_OPTION==returnVal){
			filepic=chooser.getSelectedFile().toString();//��ȡ��ѡ�ļ�·��
			texts.setText(filepic);//��·��ֵд��textField��
			return filepic;
		}
		else{
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		     JOptionPane.showMessageDialog(null, "δѡ��ͼ��","��ʾ",JOptionPane.PLAIN_MESSAGE);
			System.out.println("��δѡ��ͼ��");
			return null;
		}
	}
	//����ͼ��
	public void JiaZai() throws Exception{
		//�ҵ�ͼ��������jpg�ļ�
		File file = new File(pathtxt);
	    if (file.exists()) {
	      	file.delete();
        }
	    File file1 = new File(colortxt);
	    if (file1.exists()) {
	      	file1.delete();
        }
	    File file2 = new File(texturetxt);
	    if (file2.exists()) {
	      	file2.delete();
        }
		File file3 = new File(shapetxt);
	    if (file3.exists()) {
	      	file3.delete();
        }
	    FileWriter writer = new FileWriter(pathtxt, true);
		new getPicture().find(writer,filepic, "\\S+\\.jpg");
		writer.close();
		//��ȡ�����ļ����������ļ�
		getColor get1 = new getColor();
		getTexture get2 = new getTexture();
		getShape get3 = new getShape();
		//��ɫ����
		ArrayList<String> paths = new ArrayList<String>();//·��
		paths = get1.getPath(pathtxt);
		int length = paths.size();
		for(int i=0;i<length;i++) {
			get1.save_feature(get1.color_HSV_msv(paths.get(i)),colortxt);
		}
		//��������

		for(int i=0;i<length;i++) {
			System.out.println(paths.get(i));
			get2.save_feature(get2.texture(paths.get(i)),texturetxt);
		}
		//��״����

		for(int i=0;i<length;i++) {
			System.out.println(paths.get(i));
			get3.save_feature(get3.shape(paths.get(i)),shapetxt);
		}
		//������д�����ݿ�
		Insert in = new Insert();
		ArrayList<String> SQL = in.readFile(pathtxt);
		SQL = in.pathInsertsql(SQL);
        in.insertdb(SQL,"path");
		SQL = in.readFile(colortxt);
		SQL = in.colorInsertsql(SQL);
        in.insertdb(SQL,"color");
        SQL = in.readFile(texturetxt);
        SQL = in.textureInsertsql(SQL);
        in.insertdb(SQL,"texture");
        SQL = in.readFile(shapetxt);
        SQL = in.shapeInsertsql(SQL);
        in.insertdb(SQL,"shape");
	}
	public String TanChuang() throws Exception{
		JFileChooser chooser=new JFileChooser("E:\\data");
		chooser.setDialogTitle("��ѡ��ͼƬ�ļ�");
		//����Ϊֻ��ѡ��ͼƬ�ļ�
		FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg", "jpg");
		chooser.setFileFilter(filter);
		//����ѡ���
		int returnVal=chooser.showOpenDialog(null);
		//���ѡ�����ļ�
		if(JFileChooser.APPROVE_OPTION==returnVal){
			filepath=chooser.getSelectedFile().toString();//��ȡ��ѡ�ļ�·��
			text.setText(filepath);//��·��ֵд��textField��
			//�ڵ�ǰ�������ʾ��ѡͼ��
			ImageIcon sourceimg=new ImageIcon(filepath);
			int width=sourceimg.getIconWidth();
			int height=sourceimg.getIconHeight();
			label_2.setIcon(sourceimg);
			return filepath;
		}
		else{
			System.out.println("��δѡ���ļ�");
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		     JOptionPane.showMessageDialog(null, "δѡ���ļ�","��ʾ",JOptionPane.PLAIN_MESSAGE);
			return null;
		}
	}
	//������ɫ����
	public void color() throws Exception {
		if(filepath == null) {
			 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		     JOptionPane.showMessageDialog(null, "��δѡ���ļ�","��ʾ",JOptionPane.PLAIN_MESSAGE);
		     return ;
		}
		color = getColor.color_HSV_msv(filepath);
		Map<Integer,Double> map = new TreeMap<Integer,Double>();//���ƶ�
		Select select = new Select();
		ArrayList<double[]> colors = new ArrayList();
		colors = select.selectfeature("color");
		int num = colors.size();
		double d = 0;
		for(int i=0;i<num;i++) {
			d = Compare_color(color,colors.get(i));
			map.put(i, d);
		}
		ArrayList<Entry<Integer, Double>> entryArrayList=new ArrayList<>(map.entrySet());
        Collections.sort(entryArrayList, new Comparator<Entry<Integer, Double>>() {
            public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());//��value��С��������
            }
        });
        int[] best = new int[12];//��ƥ���12����Ƭid
        for(int i=0;i<12;i++) {
        	Entry<Integer,Double> p = entryArrayList.get(i);
        	best[i] = p.getKey();
        }
        ArrayList<String> paths = new ArrayList();
        paths = select.selectpath(select.pathSelectsql(best));
        /*for(int i=0;i<12;i++) {
        	System.out.println(paths.get(i));
        }*/


        int i=0;
        for(int y=70;y<700;y+=240){
        	for(int x=400;x<1600;x+=320){
        		if(i<12){
        			ImageIcon sourceimg1=new ImageIcon(paths.get(i));
	        		img[i].setBounds(x, y, 300, 200);
	        		img[i].setIcon(sourceimg1);
	        		imgpath[i].setText(i+1+":"+paths.get(i));
	        		imgpath[i].setFont(new Font("����", Font.PLAIN, 12));
	        		imgpath[i].setBounds(x, y+205, 300, 20);

	        		frame.add(img[i]);
	        		frame.add(imgpath[i]);
	        		i++;
        		}
        	}
        }
	}
	//�����������
	public void texture() throws Exception {
		if(filepath == null) {
			 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		     JOptionPane.showMessageDialog(null, "��δѡ���ļ�","��ʾ",JOptionPane.PLAIN_MESSAGE);
		     return ;
		}
		texture = getTexture.texture(filepath);
		Map<Integer,Double> map = new TreeMap<Integer,Double>();//���ƶ�
		Select select = new Select();
		ArrayList<double[]> texs = new ArrayList();
		texs = select.selectfeature("texture");
		int num = texs.size();
		double d = 0;
		for(int i=0;i<num;i++) {
			d = cos_similar(texture,texs.get(i));
			map.put(i, d);
		}
		ArrayList<Entry<Integer, Double>> entryArrayList=new ArrayList<>(map.entrySet());
        Collections.sort(entryArrayList, new Comparator<Entry<Integer, Double>>() {
            public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
                return o2.getValue().compareTo(o1.getValue());//��value�Ӵ�С����
            }
        });
        int[] best = new int[12];//��ƥ���12����Ƭid
        for(int i=0;i<12;i++) {
        	Entry<Integer,Double> p = entryArrayList.get(i);
        	best[i] = p.getKey();
        }
        ArrayList<String> paths = new ArrayList();
        paths = select.selectpath(select.pathSelectsql(best));
        /*for(int i=0;i<12;i++) {
        	System.out.println(paths.get(i));
        }*/
        int i=0;
        for(int y=70;y<700;y+=240){
        	for(int x=400;x<1600;x+=320){
        		if(i<12){
        			ImageIcon sourceimg1=new ImageIcon(paths.get(i));
	        		img[i].setBounds(x, y, 300, 200);
	        		img[i].setIcon(sourceimg1);
	        		imgpath[i].setText(i+1+":"+paths.get(i));
	        		imgpath[i].setFont(new Font("����", Font.PLAIN, 12));
	        		imgpath[i].setBounds(x, y+205, 300, 20);

	        		frame.add(img[i]);
	        		frame.add(imgpath[i]);
	        		i++;
        		}
        	}
        }

	}
	//基于形状检索
		public void shape() throws Exception {
			if(filepath == null) {
				 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			     JOptionPane.showMessageDialog(null, "��δѡ���ļ�","��ʾ",JOptionPane.PLAIN_MESSAGE);
			     return ;
			}
			hu = new getShape().shape(filepath);
			Map<Integer,Double> map = new TreeMap<Integer,Double>();//相似度
			Select select = new Select();
			ArrayList<double[]> shapes = new ArrayList();
			shapes = select.selectfeature("shape");
			int num = shapes.size();
			double d = 0;
			for(int i=0;i<num;i++) {
				d = cos_similar(hu,shapes.get(i));
				map.put(i, d);
			}
			ArrayList<Entry<Integer, Double>> entryArrayList=new ArrayList<>(map.entrySet());
	        Collections.sort(entryArrayList, new Comparator<Entry<Integer, Double>>() {
	            public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
	                return o2.getValue().compareTo(o1.getValue());//��value�Ӵ�С����
	            }
	        });
	        int[] best = new int[12];//��ƥ���12����Ƭid
	        for(int i=0;i<12;i++) {
	        	Entry<Integer,Double> p = entryArrayList.get(i);
	        	best[i] = p.getKey();
	        }
	        ArrayList<String> paths = new ArrayList();
	        paths = select.selectpath(select.pathSelectsql(best));
	        /*for(int i=0;i<12;i++) {
	        	System.out.println(paths.get(i));
	        }*/
	        int i=0;
	        for(int y=70;y<700;y+=240){
	        	for(int x=400;x<1600;x+=320){
	        		if(i<12){
	        			ImageIcon sourceimg1=new ImageIcon(paths.get(i));
		        		img[i].setBounds(x, y, 300, 200);
		        		img[i].setIcon(sourceimg1);
		        		imgpath[i].setText(i+1+":"+paths.get(i));
		        		imgpath[i].setFont(new Font("����", Font.PLAIN, 12));
		        		imgpath[i].setBounds(x, y+205, 300, 20);

		        		frame.add(img[i]);
		        		frame.add(imgpath[i]);
		        		i++;
	        		}
	        	}
	        }

	}
		//�ۺ�
		public void zonghe() throws Exception {
			if(filepath == null) {
				 UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			     JOptionPane.showMessageDialog(null, "��δѡ���ļ�","��ʾ",JOptionPane.PLAIN_MESSAGE);
			     return ;
			}
			color = getColor.color_HSV_msv(filepath);
			texture = getTexture.texture(filepath);
			hu = new getShape().shape(filepath);
			Map<Integer,Double> map = new TreeMap<Integer,Double>();//���ƶ�
			Select select = new Select();
			ArrayList<double[]> colors = new ArrayList();
			colors = select.selectfeature("color");
			ArrayList<double[]> textures = new ArrayList();
			textures = select.selectfeature("texture");
			ArrayList<double[]> shapes = new ArrayList();
			shapes = select.selectfeature("shape");
			int num = shapes.size();
			double d1 = 0,d2 = 0,d3 = 0,d = 0;;
		    String s1 = text1.getText();
		    String s2 = text2.getText();
		    String s3 = text3.getText();
		    if(s1.isEmpty() || s2.isEmpty() || s3.isEmpty() ) {
		    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			     JOptionPane.showMessageDialog(null, "��δ���������ռ��","��ʾ",JOptionPane.PLAIN_MESSAGE);
			     return ;
		    }
		    double b1 = new Double(s1);
		    double b2 = new Double(s2);
		    double b3 = new Double(s3);
			for(int i=0;i<num;i++) {
				d1 = cos_similar(color,colors.get(i));
				d2 = cos_similar(texture,textures.get(i));
				d3 = cos_similar(hu,shapes.get(i));
				d = d1*b1+d2*b2+d3*b3;
				map.put(i, d);
			}
			ArrayList<Entry<Integer, Double>> entryArrayList=new ArrayList<>(map.entrySet());
	        Collections.sort(entryArrayList, new Comparator<Entry<Integer, Double>>() {
	            public int compare(Entry<Integer, Double> o1, Entry<Integer, Double> o2) {
	                return o2.getValue().compareTo(o1.getValue());//��value�Ӵ�С����
	            }
	        });
	        int[] best = new int[12];//��ƥ���12����Ƭid
	        for(int i=0;i<12;i++) {
	        	Entry<Integer,Double> p = entryArrayList.get(i);
	        	best[i] = p.getKey();
	        }
	        ArrayList<String> paths = new ArrayList();
	        paths = select.selectpath(select.pathSelectsql(best));
	        /*for(int i=0;i<12;i++) {
	        	System.out.println(paths.get(i));
	        }*/
	        int i=0;
	        for(int y=70;y<700;y+=240){
	        	for(int x=400;x<1600;x+=320){
	        		if(i<12){
	        			ImageIcon sourceimg1=new ImageIcon(paths.get(i));
		        		img[i].setBounds(x, y, 300, 200);
		        		img[i].setIcon(sourceimg1);
		        		imgpath[i].setText(i+1+":"+paths.get(i));
		        		imgpath[i].setFont(new Font("����", Font.PLAIN, 12));
		        		imgpath[i].setBounds(x, y+205, 300, 20);

		        		frame.add(img[i]);
		        		frame.add(imgpath[i]);
		        		i++;
	        		}
	        	}
	        }

	}

	//�Ƚ�����ֵ ŷ�Ͼ���
    public double Compare_color(double[] a,double[] b) {
	    double D,sum=0;
	    for(int i=0;i<9;i++) {
		    sum = sum + Math.pow((a[i]-b[i]), 2);
	    }
	    D = Math.pow(sum, 0.5);
	    return D;
    }
   //用余弦定理求匹配图片与数据库中图片的相似度
  	public double cos_similar(double[] daicha, double[] kuzhi) {
  		double cosvalue=1,fenzi=0,fenmu1=0,fenmu2=0;
  		for (int i=0;i<kuzhi.length;i++) {
  			fenzi+=daicha[i]*kuzhi[i];
  			fenmu1+=daicha[i]*daicha[i];
  			fenmu2+=kuzhi[i]*kuzhi[i];
  		}
  		fenmu1=Math.sqrt(fenmu1);
  		fenmu2=Math.sqrt(fenmu2);
  		cosvalue=fenzi/(fenmu1*fenmu2);
  		return cosvalue;
  	}
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.loadLibrary( Core.NATIVE_LIBRARY_NAME );
		new main().init();
	}

}
