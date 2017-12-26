## Background
In our daily work we develop some utils from day to day, in this case there tended to be a lot of utils with similar 
functions if the util was not properly defined. Here are some basic rules if we need to develop a util for others to 
use in the team.
 + Hide the implementation
 + Use the parent class or interface as the parameters
 + Use overload to provide more functions
 + Use static import

## Hide the implementation
Don't call the third party utils in your code, create your own util instead even through your implementation has only
 one line to call the third party util. We do so, because we can modify the self defined util once in case of 
 modifications. Otherwise, you need to modify all the calls because you can't change the behavior of the third party 
 utils.
    public class UtilExample {
    
      private static String DEFAULT_CHARSET = "UTF-8";
    
      public static boolean isEmpty(CharSequence sequence) {//Use parent class or interface as much as possible
        return StringUtils.isEmpty(sequence);
      }
    
      public static boolean isEmptyCollection(Collection<?> collection) {//Use Collection instead ArrayList, List etc
        return collection == null || collection.size() == 0;
      }
    
      public static List<String> readFile2List(String fileName) throws IOException{
        return readFile2List(fileName, DEFAULT_CHARSET);
      }
    
      public static List<String> readFile2List(File file) throws IOException {
        return readFile2List(file, DEFAULT_CHARSET);
      }
    
      public static List<String> readFile2List(String fileName, String charSet) throws IOException {
        return readFile2List(new File(fileName), charSet);
      }
    
      public static List<String> readFile2List(File fileName, String charSet) throws IOException{
        return readFile2List(new FileInputStream(fileName), charSet);
      }
    
      public static List<String> readFile2List(InputStream inputStream) throws IOException {
        return readFile2List(inputStream, DEFAULT_CHARSET);
      }
    
      public static List<String> readFile2List(InputStream fileInputStream, String charSet) throws IOException {
        ArrayList<String> strings = new ArrayList<>();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream, charSet));
        String temp;
        while ( (temp = bufferedReader.readLine()) != null) {
          strings.add(temp);
        }
        return strings;
      }
    }