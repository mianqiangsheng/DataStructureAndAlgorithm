package picture.similarity.prepare;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//��ȡ�ļ��µ�����ͼƬ·�� ������print.txt
public class getPicture {
	
	
	public void find(FileWriter writer,String path,String reg) throws IOException{

		Pattern pat=Pattern.compile(reg);

		File file=new File(path);
		File[] arr=file.listFiles();
		
		for(int i=0;i<arr.length;i++){

			//�ж��Ƿ����ļ��У�����ǵĻ����ٵ���һ��find����
			if(arr[i].isDirectory()){
				find(writer, arr[i].getAbsolutePath(),reg);
			}
			Matcher mat=pat.matcher(arr[i].getAbsolutePath());
			//����������ʽ��Ѱ��ƥ����ļ�
			String str = arr[i].getAbsolutePath();
			if(mat.matches()){
				//���getAbsolutePath()��������һ��String���ļ�����·��
				System.out.println(str);
				writer.write(str+System.getProperty("line.separator"));   
			}
		}
	}

	public static void main(String[] args) throws IOException{
		String filename = "E:\\print.txt";
		File file = new File(filename);
	    if (file.exists()) {
	      	file.delete();
        }
	    FileWriter writer = new FileWriter("E:\\print.txt", true);  
		new getPicture().find(writer,"E:\\data", "\\S+\\.jpg");
		writer.close(); 
	}
}
