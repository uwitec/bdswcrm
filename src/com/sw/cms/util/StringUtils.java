package com.sw.cms.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Utility class to peform common String manipulation algorithms.
 */
public class StringUtils {

    public static void main(String[] args) {

    }

    /**
     * Initialization lock for the whole class. Init's only happen once per
     * class load so this shouldn't be a bottleneck.
     */
    private static Object initLock = new Object() ;

    /**
     * Replaces all instances of oldString with newString in line.
     *
     * @param line the String to search to perform replacements on
     * @param oldString the String that should be replaced by newString
     * @param newString the String that will replace all instances of oldString
     *
     * @return a String will all instances of oldString replaced by newString
     */
    public static final String replace(String line, String oldString,
                                       String newString) {
        if (line == null) {
            return null ;
        }
        int i = 0 ;
        if ( (i = line.indexOf(oldString, i)) >= 0) {
            char[] line2 = line.toCharArray() ;
            char[] newString2 = newString.toCharArray() ;
            int oLength = oldString.length() ;
            StringBuffer buf = new StringBuffer(line2.length) ;
            buf.append(line2, 0, i).append(newString2) ;
            i += oLength ;
            int j = i ;
            while ( (i = line.indexOf(oldString, i)) > 0) {
                buf.append(line2, j, i - j).append(newString2) ;
                i += oLength ;
                j = i ;
            }
            buf.append(line2, j, line2.length - j) ;
            return buf.toString() ;
        }
        return line ;
    }

    /**
     * Replaces all instances of oldString with newString in line with the
     * added feature that matches of newString in oldString ignore case.
     *
     * @param line the String to search to perform replacements on
     * @param oldString the String that should be replaced by newString
     * @param newString the String that will replace all instances of oldString
     *
     * @return a String will all instances of oldString replaced by newString
     */
    public static final String replaceIgnoreCase(String line, String oldString,
                                                 String newString) {
        if (line == null) {
            return null ;
        }
        String lcLine = line.toLowerCase() ;
        String lcOldString = oldString.toLowerCase() ;
        int i = 0 ;
        if ( (i = lcLine.indexOf(lcOldString, i)) >= 0) {
            char[] line2 = line.toCharArray() ;
            char[] newString2 = newString.toCharArray() ;
            int oLength = oldString.length() ;
            StringBuffer buf = new StringBuffer(line2.length) ;
            buf.append(line2, 0, i).append(newString2) ;
            i += oLength ;
            int j = i ;
            while ( (i = lcLine.indexOf(lcOldString, i)) > 0) {
                buf.append(line2, j, i - j).append(newString2) ;
                i += oLength ;
                j = i ;
            }
            buf.append(line2, j, line2.length - j) ;
            return buf.toString() ;
        }
        return line ;
    }

    /**
     * Replaces all instances of oldString with newString in line.
     * The count Integer is updated with number of replaces.
     *
     * @param line the String to search to perform replacements on
     * @param oldString the String that should be replaced by newString
     * @param newString the String that will replace all instances of oldString
     *
     * @return a String will all instances of oldString replaced by newString
     */
    public static final String replace(String line, String oldString,
                                       String newString, int[] count) {
        if (line == null) {
            return null ;
        }
        int i = 0 ;
        if ( (i = line.indexOf(oldString, i)) >= 0) {
            int counter = 0 ;
            counter++ ;
            char[] line2 = line.toCharArray() ;
            char[] newString2 = newString.toCharArray() ;
            int oLength = oldString.length() ;
            StringBuffer buf = new StringBuffer(line2.length) ;
            buf.append(line2, 0, i).append(newString2) ;
            i += oLength ;
            int j = i ;
            while ( (i = line.indexOf(oldString, i)) > 0) {
                counter++ ;
                buf.append(line2, j, i - j).append(newString2) ;
                i += oLength ;
                j = i ;
            }
            buf.append(line2, j, line2.length - j) ;
            count[0] = counter ;
            return buf.toString() ;
        }
        return line ;
    }

    /**
     * This method takes a string which may contain HTML tags (ie, &lt;b&gt;,
     * &lt;table&gt;, etc) and converts the '&lt'' and '&gt;' characters to
     * their HTML escape sequences.
     *
     * @param input the text to be converted.
     * @return the input string with the characters '&lt;' and '&gt;' replaced
     *  with their HTML escape sequences.
     */
    public static final String escapeHTMLTags(String input) {
        //Check if the string is null or zero length -- if so, return
        //what was sent in.
        if (input == null || input.length() == 0) {
//            return input;
            input = "" ;
        }
        //Use a StringBuffer in lieu of String concatenation -- it is
        //much more efficient this way.
        StringBuffer buf = new StringBuffer(input.length()) ;
        char ch = ' ' ;
        for (int i = 0 ; i < input.length() ; i++) {
            ch = input.charAt(i) ;
            if (ch == '<') {
                buf.append("&lt;") ;
            }
            else if (ch == '>') {
                buf.append("&gt;") ;
            }
            else {
                buf.append(ch) ;
            }
        }
        return buf.toString() ;
    }

    public static final String unescapeFromHTML(String input) {
        input = replace(input, "&lt;", "<") ;
        input = replace(input, "&gt;", ">") ;
        return input ;
    }

    public static final String unNewLine(String input) {
        return StringUtils.replace(StringUtils.replace(input, "<BR>", "\r\n"),
                                   "<BR>", "\n") ;
    }

    /**
     * Converts a line of text into an array of lower case words. Words are
     * delimited by the following characters: , .\r\n:/\+
     * <p>
     * In the future, this method should be changed to use a
     * BreakIterator.wordInstance(). That class offers much more fexibility.
     *
     * @param text a String of text to convert into an array of words
     * @return text broken up into an array of words.
     */
    public static final String[] toLowerCaseWordArray(String text) {
        if (text == null || text.length() == 0) {
            return new String[0] ;
        }
        StringTokenizer tokens = new StringTokenizer(text, " ,\r\n.:/\\+") ;
        String[] words = new String[tokens.countTokens()] ;
        for (int i = 0 ; i < words.length ; i++) {
            words[i] = tokens.nextToken().toLowerCase() ;
        }
        return words ;
    }

    /**
     * A list of some of the most common words. For searching and indexing, we
     * often want to filter out these words since they just confuse searches.
     * The list was not created scientifically so may be incomplete :)
     */
    private static final String[] commonWords = new String[] {
        "a", "and", "as", "at", "be", "do", "i", "if", "in", "is", "it", "so",
        "the", "to"
    } ;
    private static Map commonWordsMap = null ;

    /**
     * Returns a new String array with some of the most common English words
     * removed. The specific words removed are: a, and, as, at, be, do, i, if,
     * in, is, it, so, the, to
     */
    public static final String[] removeCommonWords(String[] words) {
        //See if common words map has been initialized. We don't statically
        //initialize it to save some memory. Even though this a small savings,
        //it adds up with hundreds of classes being loaded.
        if (commonWordsMap == null) {
            synchronized (initLock) {
                if (commonWordsMap == null) {
                    commonWordsMap = new HashMap() ;
                    for (int i = 0 ; i < commonWords.length ; i++) {
                        commonWordsMap.put(commonWords[i], commonWords[i]) ;
                    }
                }
            }
        }
        //Now, add all words that aren't in the common map to results
        ArrayList results = new ArrayList(words.length) ;
        for (int i = 0 ; i < words.length ; i++) {
            if (!commonWordsMap.containsKey(words[i])) {
                results.add(words[i]) ;
            }
        }
        return (String[]) results.toArray(new String[results.size()]) ;
    }

    /**
     * Pseudo-random number generator object for use with randomString().
     * The Random class is not considered to be cryptographically secure, so
     * only use these random Strings for low to medium security applications.
     */
    private static Random randGen = null ;

    /**
     * Array of numbers and letters of mixed case. Numbers appear in the list
     * twice so that there is a more equal chance that a number will be picked.
         * We can use the array to get a random number or letter by picking a random
     * array index.
     */
    private static char[] numbersAndLetters = null ;

    /**
     * Returns a random String of numbers and letters of the specified length.
     * The method uses the Random class that is built-in to Java which is
     * suitable for low to medium grade security uses. This means that the
     * output is only pseudo random, i.e., each number is mathematically
     * generated so is not truly random.<p>
     *
         * For every character in the returned String, there is an equal chance that
     * it will be a letter or number. If a letter, there is an equal chance
     * that it will be lower or upper case.<p>
     *
         * The specified length must be at least one. If not, the method will return
     * null.
     *
     * @param length the desired length of the random String to return.
     * @return a random String of numbers and letters of the specified length.
     */
    public static final String randomString(int length) {
        if (length < 1) {
            return null ;
        }
        //Init of pseudo random number generator.
        if (randGen == null) {
            synchronized (initLock) {
                if (randGen == null) {
                    randGen = new Random() ;
                    //Also initialize the numbersAndLetters array
                    numbersAndLetters = ("0123456789abcdefghijklmnopqrstuvwxyz" +
                                         "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ").
                        toCharArray() ;
                }
            }
        }
        //Create a char buffer to put random letters and numbers in.
        char[] randBuffer = new char[length] ;
        for (int i = 0 ; i < randBuffer.length ; i++) {
            randBuffer[i] = numbersAndLetters[randGen.nextInt(71)] ;
        }
        return new String(randBuffer) ;
    }

    /**
     * Intelligently chops a String at a word boundary (whitespace) that occurs
     * at the specified index in the argument or before. However, if there is a
     * newline character before <code>length</code>, the String will be chopped
     * there. If no newline or whitespace is found in <code>string</code> up to
         * the index <code>length</code>, the String will chopped at <code>length</code>.
     * <p>
     * For example, chopAtWord("This is a nice String", 10) will return
     * "This is a" which is the first word boundary less than or equal to 10
     * characters into the original String.
     *
     * @param string the String to chop.
     * @param length the index in <code>string</code> to start looking for a
     *       whitespace boundary at.
     * @return a substring of <code>string</code> whose length is less than or
     *       equal to <code>length</code>, and that is chopped at whitespace.
     */
    public static final String chopAtWord(String string, int length) {
        if (string == null) {
            return string ;
        }

        char[] charArray = string.toCharArray() ;
        int sLength = string.length() ;
        if (length < sLength) {
            sLength = length ;
        }

        //First check if there is a newline character before length; if so,
        //chop word there.
        for (int i = 0 ; i < sLength - 1 ; i++) {
            //Windows
            if (charArray[i] == '\r' && charArray[i + 1] == '\n') {
                return string.substring(0, i) ;
            }
            //Unix
            else if (charArray[i] == '\n') {
                return string.substring(0, i) ;
            }
        }
        //Also check boundary case of Unix newline
        if (charArray[sLength - 1] == '\n') {
            return string.substring(0, sLength - 1) ;
        }

        //Done checking for newline, now see if the total string is less than
        //the specified chop point.
        if (string.length() < length) {
            return string ;
        }

        //No newline, so chop at the first whitespace.
        for (int i = length - 1 ; i > 0 ; i--) {
            if (charArray[i] == ' ') {
                return string.substring(0, i).trim() ;
            }
        }

        //Did not find word boundary so return original String chopped at
        //specified length.
        return string.substring(0, length) ;
    }

    /**
     * Highlights words in a string. Words matching ignores case. The actual
     * higlighting method is specified with the start and end higlight tags.
     * Those might be beginning and ending HTML bold tags, or anything else.
     *
     * @param string the String to highlight words in.
     * @param words an array of words that should be highlighted in the string.
         * @param startHighlight the tag that should be inserted to start highlighting.
     * @param endHighlight the tag that should be inserted to end highlighting.
     * @return a new String with the specified words highlighted.
     */
    public static final String highlightWords(String string, String[] words,
                                              String startHighlight,
                                              String endHighlight) {
        if (string == null || words == null ||
            startHighlight == null || endHighlight == null) {
            return null ;
        }

        //Iterate through each word.
        for (int x = 0 ; x < words.length ; x++) {
            //we want to ignore case.
            String lcString = string.toLowerCase() ;
            //using a char [] is more efficient
            char[] string2 = string.toCharArray() ;
            String word = words[x].toLowerCase() ;

            //perform specialized replace logic
            int i = 0 ;
            if ( (i = lcString.indexOf(word, i)) >= 0) {
                int oLength = word.length() ;
                StringBuffer buf = new StringBuffer(string2.length) ;

                //we only want to highlight distinct words and not parts of
                //larger words. The method used below mostly solves this. There
                //are a few cases where it doesn't, but it's close enough.
                boolean startSpace = false ;
                char startChar = ' ' ;
                if (i - 1 > 0) {
                    startChar = string2[i - 1] ;
                    if (!Character.isLetter(startChar)) {
                        startSpace = true ;
                    }
                }
                boolean endSpace = false ;
                char endChar = ' ' ;
                if (i + oLength < string2.length) {
                    endChar = string2[i + oLength] ;
                    if (!Character.isLetter(endChar)) {
                        endSpace = true ;
                    }
                }
                if ( (startSpace && endSpace) || (i == 0 && endSpace)) {
                    buf.append(string2, 0, i) ;
                    if (startSpace && startChar == ' ') {
                        buf.append(startChar) ;
                    }
                    buf.append(startHighlight) ;
                    buf.append(string2, i, oLength).append(endHighlight) ;
                    if (endSpace && endChar == ' ') {
                        buf.append(endChar) ;
                    }
                }
                else {
                    buf.append(string2, 0, i) ;
                    buf.append(string2, i, oLength) ;
                }

                i += oLength ;
                int j = i ;
                while ( (i = lcString.indexOf(word, i)) > 0) {
                    startSpace = false ;
                    startChar = string2[i - 1] ;
                    if (!Character.isLetter(startChar)) {
                        startSpace = true ;
                    }

                    endSpace = false ;
                    if (i + oLength < string2.length) {
                        endChar = string2[i + oLength] ;
                        if (!Character.isLetter(endChar)) {
                            endSpace = true ;
                        }
                    }
                    if ( (startSpace && endSpace) ||
                        i + oLength == string2.length) {
                        buf.append(string2, j, i - j) ;
                        if (startSpace && startChar == ' ') {
                            buf.append(startChar) ;
                        }
                        buf.append(startHighlight) ;
                        buf.append(string2, i, oLength).append(endHighlight) ;
                        if (endSpace && endChar == ' ') {
                            buf.append(endChar) ;
                        }
                    }
                    else {
                        buf.append(string2, j, i - j) ;
                        buf.append(string2, i, oLength) ;
                    }
                    i += oLength ;
                    j = i ;
                }
                buf.append(string2, j, string2.length - j) ;
                string = buf.toString() ;
            }
        }
        return string ;
    }

    /**
     * Escapes all necessary characters in the String so that it can be used
     * in an XML doc.
     *
     * @param string the string to escape.
     * @return the string with appropriate characters escaped.
     */
    public static final String escapeForXML(String string) {
        //Check if the string is null or zero length -- if so, return
        //what was sent in.
        if (string == null || string.length() == 0) {
            return string ;
        }
        char[] sArray = string.toCharArray() ;
        StringBuffer buf = new StringBuffer(sArray.length) ;
        char ch ;
        for (int i = 0 ; i < sArray.length ; i++) {
            ch = sArray[i] ;
            if (ch == '<') {
                buf.append("&lt;") ;
            }
            else if (ch == '>') {
                buf.append("&gt;") ;
            }
            else if (ch == '"') {
                buf.append("&quot;") ;
            }
            else if (ch == '&') {
                buf.append("&amp;") ;
            }
            else {
                buf.append(ch) ;
            }
        }
        return buf.toString() ;
    }

    /**
     * Unescapes the String by converting XML escape sequences back into normal
     * characters.
     *
     * @param string the string to unescape.
     * @return the string with appropriate characters unescaped.
     */
    public static final String unescapeFromXML(String string) {
        string = replace(string, "&lt;", "<") ;
        string = replace(string, "&gt;", ">") ;
        string = replace(string, "&quot;", "\"") ;
        return replace(string, "&amp;", "&") ;
    }

    /**
     * 根据提供的标示符，分割字符串。
     *
     */
    public static final String[] explodeString(String str, String delim) {
        if (str == null || str.equals("")) {
            String[] retstr = new String[1] ;
            retstr[0] = "" ;
            return retstr ;
        }
        StringTokenizer st = new StringTokenizer(str, delim) ;
        String[] retstr = new String[st.countTokens()] ;
        int i = 0 ;
        while (st.hasMoreTokens()) {
            retstr[i] = st.nextToken() ;
            i++ ;
        }
        if (i == 0) {
            retstr[0] = str ;
        }
        return retstr ;
    }

    /**
     * 把srcStr中的escapeStr去掉
     * 例如：escapeStr("aaaa--bb--cc","--");
     * 将返回"aaaabbcc"
     */
    public static final String escapeStr(String srcStr, String escapeStr) {
        StringBuffer newStrBuf = new StringBuffer() ;
        int startIndex = 0 ;
        int endIndex = 0 ;
        while ( (endIndex = srcStr.indexOf(escapeStr, startIndex)) > 0) {
            newStrBuf.append(srcStr.substring(startIndex, endIndex)) ;
            startIndex = endIndex + escapeStr.length() ;
        }
        newStrBuf.append(srcStr.substring(startIndex)) ;
        return newStrBuf.toString() ;
    }

    public static final String nullToStr(String oldStr) {
        if (oldStr == null) {
            return "" ;
        }
        return oldStr.trim() ;
    }

    public static final String nullToStr(Date oldDate, String sFormat) {
        if (oldDate == null) {
            return "" ;
        }
        else {
            return DateComFunc.getDateFormat(oldDate, sFormat) ;
        }

    }

    public static final String nullToStr(BigDecimal oldData) {
        if (oldData == null) {
            return "" ;
        }
        else {
            return oldData.toString().trim() ;
        }

    }

    public static final String nullToStr(long oldData) {
        return new Long(oldData).toString().trim() ;
    }

    public static final String nullToStr(int oldData) {
        return new Integer(oldData).toString().trim() ;
    }

    public static final String nullToStr(double oldData) {
        return new Double(oldData).toString().trim() ;
    }


    public static final String nullToStr(Integer oldData) {
        if (oldData == null) {
            return "" ;
        }
        else {
            return oldData.toString().trim() ;
        }

    }

    public static final String nullToStr(Double oldData) {
        if (oldData == null) {
            return "" ;
        }
        else {
            return oldData.toString().trim() ;
        }

    }

    public static final String nullToStr(Object oldData) {
        if (oldData == null) {
            return "" ;
        }
        else {
            return oldData.toString().trim() ;
        }

    }
    
    public static final String nullToZero(Object oldData) {
        if (oldData == null) {
            return "0" ;
        }
        else {
            return oldData.toString().trim() ;
        }

    }

    /**
     * 日期转化成字符串
     * @param currdate
     * @param mode
     * @return
     */
    public static final String dateToString(java.util.Date currdate) {
        String returnDate = "" ;
        try {
            java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat(
                "yyyy-MM-dd") ;
            if (currdate == null)
                return returnDate ;
            else
                returnDate = simple.format(currdate) ;
        }
        catch (Exception ex) {

        }
        return returnDate ;

    }

    /**
     *
     * @param currdate
     * @return
     */
    public static final String timesToString(java.util.Date currdate) {
        String returnDate = "" ;
        try {
            java.text.SimpleDateFormat simple = new java.text.SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss") ;
            if (currdate == null)
                return returnDate ;
            else
                returnDate = simple.format(currdate) ;
        }
        catch (Exception ex) {

        }
        return returnDate ;

    }

    public static final String LenToString(String oldData, int len) {
        String result = "" ;
        if (oldData == null)
            oldData = "" ;
        result = oldData ;
        for (int i = 0 ; i < (len - oldData.length()) ; i++) {
            result = "0" + result ;
        }
        return result ;
    }

    /**
 * 产生在start和end之间的num个随机整数，返回值中。
 * 创建日期：(2001-7-30 8:50:23)
 * @return java.util.Hashtable
 * @param start int 起始点
 * @param end int   结束点
 * @param num int  生成个数
 */
    public String randomStr(int start, int end, int num){
       String result="";
       for (int i = 0; i < num; i++){
          double sru = Math.random() * end;
          int tag = Math.round((float) sru);
          if (tag < start){
             i--;
          } else{
             result = result + tag;
          }
       }
       return result;
    }
    /**
     * 输出UTF8
     * @param s
     * @return
     */
    public static String toUTF8String(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                }
                catch (Exception ex) {
                    System.out.println(ex);
                    b = new byte[0];
                }
                for (int j = 0; j < b.length; j++) {
                    int k = b[j];
                    if (k < 0) {
                        k += 256;
                    }
                    sb.append("%" + Integer.toHexString(k).
                              toUpperCase());
                }
            }
        }
        return sb.toString();
    }

    public static int isArrayExist(String name,String[] str){
        int result=-1;
        if(str!=null){
            for(int i=0;i<str.length ;i++){
                if(name.equals(str[i])){
                    result=i;
                    break;
                }
            }

        }
        return result;
    }

    public static int byteLen(String str){
        if (str == null || str.equals("")) {
            return 0;
        }
        byte[] bt = str.getBytes();
        return bt.length;
    }


    public static String SplitByByteLen(String str,int byteLen) {
        if (str == null || str.equals("")) {
            return "";
        }

        byte[] bytes = str.getBytes();

        if (bytes.length <= byteLen)
            return str;

        char[] chars = new String(bytes, 0, byteLen).toCharArray();
        if (chars.length == 0)
            return new String(bytes, 0, byteLen - 1);

        return new String(bytes, 0, byteLen);
    }

    public static String insertStringByByteLen(String str,int byteLen,String insertStr) {
        String returnStr = "";
        if (str == null || str.equals("")) {
            return "";
        }
        String tempStr = str;
        byte[] bytes = str.getBytes();
        while (bytes.length != 0) {
            String splitStr = SplitByByteLen(tempStr, byteLen);
            tempStr = tempStr.substring(splitStr.length());
            bytes = tempStr.getBytes();
            returnStr = returnStr + splitStr + insertStr;
        }
        return returnStr;
    }
    
    /**
     * 判断字符数组中是否存在某字符串
     * @return
     */
    public static boolean isStrInArray(String[] array,String str){
    	boolean is = false;
    	
    	if(array != null && array.length>0){
    		for(int i=0;i<array.length;i++){
    			if(array[i].equals(str)){
    				is = true;
    				break;
    			}
    		}
    	}
    	
    	return is;
    }
    
    
    /**
     * 把空字符串 传唤为NULL
     * @param o
     * @return
     */
    public static Object strToNull(Object o)
    {
    	if(o.toString().trim().equals(""))
    	{
    		return null;
    	}
    	else
    	{
    		return o;
    	}
    }    

}