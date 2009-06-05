package com.sw.cms.util;

import java.util.* ;

public final class StringFilter {

/*
    public static String doJISAutoEncode(String moto) {
        if (moto == null)
            return null ;
        String moto2 = null ;
        try {
            moto2 = new String(moto.getBytes("ISO8859-1"), "JISAutoDetect") ;
        }
        catch (Exception e) {
            return moto ;
        }
//                moto2 = moto2.replace('＞','>');
//                moto2 = moto2.replace('＜','<');
        return moto2 ;
    }
*/
/*
    public static String AreWoSore(String moto) {
        String moto2 = null ;
        try {
//			moto2 = new String( moto.getBytes("ISO8859-1"),"JISAutoDetect");
//Linux環境では↓にすると化けないようです。
            moto2 = new String(moto.getBytes("ISO8859-1"), "MS932") ;

        }
        catch (Exception e) {
            ;
        }

        if (moto2 == null || moto == null) {
            return null ;
        }
         ;

        //１文字ならば簡単にいくが
        moto2 = moto2.replace('－', '-') ;
        moto2 = moto2.replace('～', '-') ;
        moto2 = moto2.replace('∥', '|') ;
        moto2 = moto2.replace('\uFFFD', '1') ;
        moto2 = moto2.replace('\uFFFD', '2') ;
        moto2 = moto2.replace('\uFFFD', '3') ;
        moto2 = moto2.replace('\uFFFD', '4') ;
        moto2 = moto2.replace('\uFFFD', '5') ;
        moto2 = moto2.replace('\uFFFD', '6') ;
        moto2 = moto2.replace('\uFFFD', '7') ;
        moto2 = moto2.replace('\uFFFD', '8') ;
        moto2 = moto2.replace('\uFFFD', '9') ;

        //２文字以上に変換するのは・・・
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');

        moto2 = moto2.replace('\uFFFD', '1') ;
        moto2 = moto2.replace('\uFFFD', '2') ;
        moto2 = moto2.replace('\uFFFD', '3') ;
        moto2 = moto2.replace('\uFFFD', '4') ;
        moto2 = moto2.replace('\uFFFD', '5') ;
        moto2 = moto2.replace('\uFFFD', '6') ;
        moto2 = moto2.replace('\uFFFD', '7') ;
        moto2 = moto2.replace('\uFFFD', '8') ;
        moto2 = moto2.replace('\uFFFD', '9') ;
//		moto2 = moto2.replace('\uFFFD', '');

//		moto2 = moto2.replace('\uFFFD', '1');
//		moto2 = moto2.replace('\uFFFD', '2');
//		moto2 = moto2.replace('\uFFFD', '3');
//		moto2 = moto2.replace('\uFFFD', '4');
//		moto2 = moto2.replace('\uFFFD', '5');
//		moto2 = moto2.replace('\uFFFD', '6');
//		moto2 = moto2.replace('\uFFFD', '7');
//		moto2 = moto2.replace('\uFFFD', '8');
//		moto2 = moto2.replace('\uFFFD', '9');
//		moto2 = moto2.replace('\uFFFD', '');

//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
        moto2 = moto2.replace('\uFFFD', 'm') ;
        moto2 = moto2.replace('\uFFFD', 'g') ;
        moto2 = moto2.replace('\uFFFD', 't') ;
        moto2 = moto2.replace('\uFFFD', 'a') ;
//		moto2 = moto2.replace('\uFFFD', '');
        moto2 = moto2.replace('\uFFFD', 'l') ;
        moto2 = moto2.replace('\uFFFD', 'W') ;
//		moto2 = moto2.replace('\uFFFD', '');
        moto2 = moto2.replace('\uFFFD', '$') ;
//		moto2 = moto2.replace('\uFFFD', '');
        moto2 = moto2.replace('\uFFFD', '%') ;
//		moto2 = moto2.replace('\uFFFD', '');
        moto2 = moto2.replace('\uFFFD', 'p') ;
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');

        moto2 = moto2.replace('\uFFFD', '上') ;
        moto2 = moto2.replace('\uFFFD', '中') ;
        moto2 = moto2.replace('\uFFFD', '下') ;
        moto2 = moto2.replace('\uFFFD', '左') ;
        moto2 = moto2.replace('\uFFFD', '右') ;

//		moto2 = moto2.replace('\uFFFD', '');'
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
        moto2 = moto2.replace('\uFFFD', '明') ;
        moto2 = moto2.replace('\uFFFD', '大') ;
        moto2 = moto2.replace('\uFFFD', '昭') ;
        moto2 = moto2.replace('\uFFFD', '平') ;

        //対応する文字はなんだろう・・・
//		moto2 = moto2.replace('≒', '');
//		moto2 = moto2.replace('≡', '');
//		moto2 = moto2.replace('∫', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('√', '');
//		moto2 = moto2.replace('⊥', '');
//		moto2 = moto2.replace('∠', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('∵', '');
//		moto2 = moto2.replace('∩', '');
//		moto2 = moto2.replace('∪', '');

// エスケープシーケンスの変わりに全角にします。
        moto2 = moto2.replace('<', '＜') ;
        moto2 = moto2.replace('>', '＞') ;
        moto2 = moto2.replace('"', '”') ;
        moto2 = moto2.replace('\'', '’') ;
        moto2 = moto2.replace('&', '＆') ;

        return moto2 ;
    }
*/
    public static String convertToAvoidGarbage(String moto) {
        String moto2 = null ;
        try {
            //moto2 = new String( moto.getBytes("ISO8859-1"),"MS932");
//          moto2 = moto ;
            moto2 = new String( moto.getBytes("ISO8859-1"),"gb2312");
        }
        catch (Exception e) {

        }

        return moto2 ;
    }

    /**
     *変換結果が2バイト以上になりそうなときに使用する
     *
     *@ 2000-7-13
     *@ Truruoka
     *@ return String
     *@ src 元文字列
     *@ cmp 変換対象文字
     *@ res 変換後文字(列)
     *@ dest 返却文字列
     */
    private String replaceW(String src, String cmp, String res) {
        if ( (src == null || cmp == null) || (res == null))
            return " " ;

        int pos = -1 ;
        String dest = null ;
        String tmp = null ;

        //src中に部分文字列cmpがあるか検査する

        try {
            pos = src.indexOf(cmp) ;
        }
        catch (Exception e) {
            return src ;
        }

        //マッチしなければ元のsrcを返却する

        if (pos < 0)
            return src ;

        //マッチしたならば・・・
        try {
            dest = src.substring(0, (pos - 1)) ;
            tmp = src.substring( (pos + 1), (src.length() - 1)) ;
        }
        catch (Exception e) {
            return src ;
        }
        dest = dest + res + tmp ;
        return dest ;
    }

    // ＣＳＶ出力の際、改行コードを削除したい。
    public static StringBuffer prepareCSVdata(String s) {

        if (s == null || s.length() == 0)
            return null ;

        StringTokenizer st = new StringTokenizer(s) ;
        StringBuffer sb = new StringBuffer() ;

        while (st.hasMoreTokens()) {
            sb.append(st.nextToken()) ;
            sb.append(" ") ;
        }
        return sb ;
    }

    /**
     * CSV出力用の変換メソッド
     * 改行文字を半角空白に、カンマをピリオドに変換する。
     * prepareCSVdataだと、カンマに対応していない上、タブ文字も空白にしてしまう\uFFFD
     * 2001/09/12 K.Sakai
     */
    public static String prepareCSVdata2(String s) {
        if (s == null) {
            return null ;
        }
        String result = s.replace('\n', ' ') ;
        result = result.replace(',', '.') ;
        return result ;
    }

    //カタカナをひらかなに変換する。
    public static String KataToHira(String str) {

        str = str.replace('ア', 'あ') ;
        str = str.replace('イ', 'い') ;
        str = str.replace('ウ', 'う') ;
        str = str.replace('エ', 'え') ;
        str = str.replace('オ', 'お') ;

        str = str.replace('カ', 'か') ;
        str = str.replace('キ', 'き') ;
        str = str.replace('ク', 'く') ;
        str = str.replace('ケ', 'け') ;
        str = str.replace('コ', 'こ') ;

        str = str.replace('サ', 'さ') ;
        str = str.replace('シ', 'し') ;
        str = str.replace('ス', 'す') ;
        str = str.replace('セ', 'せ') ;
        str = str.replace('ソ', 'そ') ;

        str = str.replace('タ', 'た') ;
        str = str.replace('チ', 'ち') ;
        str = str.replace('ツ', 'つ') ;
        str = str.replace('テ', 'て') ;
        str = str.replace('ト', 'と') ;

        str = str.replace('ナ', 'な') ;
        str = str.replace('ニ', 'に') ;
        str = str.replace('ヌ', 'ぬ') ;
        str = str.replace('ネ', 'ね') ;
        str = str.replace('ノ', 'の') ;

        str = str.replace('ハ', 'は') ;
        str = str.replace('ヒ', 'ひ') ;
        str = str.replace('フ', 'ふ') ;
        str = str.replace('ヘ', 'へ') ;
        str = str.replace('ホ', 'ほ') ;

        str = str.replace('マ', 'ま') ;
        str = str.replace('ミ', 'み') ;
        str = str.replace('ム', 'む') ;
        str = str.replace('メ', 'め') ;
        str = str.replace('モ', 'も') ;

        str = str.replace('ヤ', 'や') ;
        str = str.replace('ユ', 'ゆ') ;
        str = str.replace('ヨ', 'よ') ;

        str = str.replace('ラ', 'ら') ;
        str = str.replace('リ', 'り') ;
        str = str.replace('ル', 'る') ;
        str = str.replace('レ', 'れ') ;
        str = str.replace('ロ', 'ろ') ;

        str = str.replace('ワ', 'わ') ;
        str = str.replace('ヲ', 'を') ;
        str = str.replace('ン', 'ん') ;

        str = str.replace('ァ', 'ぁ') ;
        str = str.replace('ィ', 'ぃ') ;
        str = str.replace('ゥ', 'ぅ') ;
        str = str.replace('ェ', 'ぇ') ;
        str = str.replace('ォ', 'ぉ') ;

        str = str.replace('ッ', 'っ') ;
        str = str.replace('ャ', 'ゃ') ;
        str = str.replace('ュ', 'ゅ') ;
        str = str.replace('ョ', 'ょ') ;

        str = str.replace('パ', 'ぱ') ;
        str = str.replace('ピ', 'ぴ') ;
        str = str.replace('プ', 'ぷ') ;
        str = str.replace('ペ', 'ぺ') ;
        str = str.replace('ポ', 'ぽ') ;

        str = str.replace('ガ', 'が') ;
        str = str.replace('ギ', 'ぎ') ;
        str = str.replace('グ', 'ぐ') ;
        str = str.replace('ゲ', 'げ') ;
        str = str.replace('ゴ', 'ご') ;

        str = str.replace('ザ', 'ざ') ;
        str = str.replace('ジ', 'じ') ;
        str = str.replace('ズ', 'ず') ;
        str = str.replace('ゼ', 'ぜ') ;
        str = str.replace('ゾ', 'ぞ') ;

        str = str.replace('ダ', 'だ') ;
        str = str.replace('ヂ', 'ぢ') ;
        str = str.replace('ヅ', 'づ') ;
        str = str.replace('デ', 'で') ;
        str = str.replace('ド', 'ど') ;

        str = str.replace('バ', 'ば') ;
        str = str.replace('ビ', 'び') ;
        str = str.replace('ブ', 'ぶ') ;
        str = str.replace('ベ', 'べ') ;
        str = str.replace('ボ', 'ぼ') ;

        return str ;
    }

    /**
     * 文字コード確認メソッド
     */
    public static String dump(String str) {
        StringBuffer sb = new StringBuffer() ;
        char[] buf = str.toCharArray() ;
        sb.append("dump(" + str + "): ") ;
        for (int i = 0 ; i < buf.length ; i++) {
            sb.append(Integer.toString(buf[i], 16) + " ") ;
        }
        return new String(sb) ;
    }

    /**
     *  filter for string, it will turn char(')and (")to "\'"
     */
    public static String DealWithNote(String inputStr) {
        StringBuffer sb = new StringBuffer() ;
        for (int i = 0 ; i < inputStr.length() ; i++) {
            char onechar = inputStr.charAt(i) ;
            if (onechar == '\'' || onechar == '\"') {
                char[] change = {
                    '\\', '\''} ;
                sb.append(change) ;
            }
            else if (onechar == '\\') {
                char[] change = {
                    '\\', '\\'} ;
                sb.append(change) ;
            }
            else
                sb.append(onechar) ;
        }

        return new String(sb) ;
    }

    /**
     *
     */
    public static String delComma(String price) {
        StringBuffer sb = new StringBuffer() ;
        for (int i = 0 ; i < price.length() ; i++) {
            char check = price.charAt(i) ;
            if (check != ',') {
                sb.append(check) ;
            }
        }
        return sb.toString() ;
    }

}