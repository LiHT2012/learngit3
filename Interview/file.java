/**
java.io.File
new File("path"):path可为文件夹名，也可到具体文件名
*/
File fileFolder = new File("/home/dbc-intern5/file/view/list");
for (File file : fileFolder.listiles()) {
	//上述path为文件夹，可获取其下文件
  StringBuffer stringBuffer = new StringBuffer();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line;
		do {
			line = bufferedReader.readLine();
			if (null != line) {
				stringBuffer.append(line);
			}

		} while (null == line);
		bufferedReader.close();
		String json = stringBuffer.toString();
}

File basicDir = new File("dircfg/" + dirId);
/**
java.io.File.mkdirs():
Creates the directory named by this abstract pathname, including any necessary but nonexistent parent directories.
Note that if this operation fails it may have succeeded in creating some of the necessary parent directories.
*/
basicDir.mkdirs();
File oldCfg = new File("dircfg/" + dirId + "/old.json");
/**
  java.io.PrintWriter:Prints formatted representations of objects to a text-output stream. This class implements all of the print methods found in PrintStream.
   It does not contain methods for writing raw bytes, for which a program should use unencoded byte streams.

   File java.io.File.getAbsoluteFile():Returns the absolute form of this abstract pathname. Equivalent to new File(this.getAbsolutePath).
*/
PrintWriter out = new PrintWriter(oldCfg.getAbsoluteFile());
try {
  /**
  void java.io.PrintWriter.println(String x):Prints a String and then terminates the line. This method behaves as though it invokes print(String) and then println().
  */
	out.println(cfgStr);
} finally {
	out.close();
}
/**
PrintWriter.write()方法，本身不会写入换行符，如果用write()写入了信息，在另一端如果用readLine()方法。由于读不到换行符，意味中读不到结束标记，然后由于IO流是阻塞式的，所以程序就是一直卡在那里不动了。
原因即为缺少回车标识。如果在写入的时候加上“\r\n”,就可以解决这个问题了。而println()就自动加上了换行符了。
*/

字节流对应的类应该是InputStream和OutputStream，而在我们实际开发中，我们应该根据不同的媒介类型选用相应的子类来处理。
下面我们就用字节流来操作文件媒介：

public static void writeByteToFile() throws IOException{
        String hello= new String( "hello word!");
         byte[] byteArray= hello.getBytes();
        File file= new File( "d:/test.txt");
         //因为是用字节流来写媒介，所以对应的是OutputStream
         //又因为媒介对象是文件，所以用到子类是FileOutputStream
        OutputStream os= new FileOutputStream( file);
         os.write( byteArray);
         os.close();
  }
