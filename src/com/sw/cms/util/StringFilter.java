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
//                moto2 = moto2.replace('��','>');
//                moto2 = moto2.replace('��','<');
        return moto2 ;
    }
*/
/*
    public static String AreWoSore(String moto) {
        String moto2 = null ;
        try {
//			moto2 = new String( moto.getBytes("ISO8859-1"),"JISAutoDetect");
//Linux���ł́��ɂ���Ɖ����Ȃ��悤�ł��B
            moto2 = new String(moto.getBytes("ISO8859-1"), "MS932") ;

        }
        catch (Exception e) {
            ;
        }

        if (moto2 == null || moto == null) {
            return null ;
        }
         ;

        //�P�����Ȃ�ΊȒP�ɂ�����
        moto2 = moto2.replace('�|', '-') ;
        moto2 = moto2.replace('�`', '-') ;
        moto2 = moto2.replace('�a', '|') ;
        moto2 = moto2.replace('\uFFFD', '1') ;
        moto2 = moto2.replace('\uFFFD', '2') ;
        moto2 = moto2.replace('\uFFFD', '3') ;
        moto2 = moto2.replace('\uFFFD', '4') ;
        moto2 = moto2.replace('\uFFFD', '5') ;
        moto2 = moto2.replace('\uFFFD', '6') ;
        moto2 = moto2.replace('\uFFFD', '7') ;
        moto2 = moto2.replace('\uFFFD', '8') ;
        moto2 = moto2.replace('\uFFFD', '9') ;

        //�Q�����ȏ�ɕϊ�����̂́E�E�E
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

        moto2 = moto2.replace('\uFFFD', '��') ;
        moto2 = moto2.replace('\uFFFD', '��') ;
        moto2 = moto2.replace('\uFFFD', '��') ;
        moto2 = moto2.replace('\uFFFD', '��') ;
        moto2 = moto2.replace('\uFFFD', '�E') ;

//		moto2 = moto2.replace('\uFFFD', '');'
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
        moto2 = moto2.replace('\uFFFD', '��') ;
        moto2 = moto2.replace('\uFFFD', '��') ;
        moto2 = moto2.replace('\uFFFD', '��') ;
        moto2 = moto2.replace('\uFFFD', '��') ;

        //�Ή����镶���͂Ȃ񂾂낤�E�E�E
//		moto2 = moto2.replace('��', '');
//		moto2 = moto2.replace('��', '');
//		moto2 = moto2.replace('��', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('��', '');
//		moto2 = moto2.replace('��', '');
//		moto2 = moto2.replace('��', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('\uFFFD', '');
//		moto2 = moto2.replace('��', '');
//		moto2 = moto2.replace('��', '');
//		moto2 = moto2.replace('��', '');

// �G�X�P�[�v�V�[�P���X�̕ς��ɑS�p�ɂ��܂��B
        moto2 = moto2.replace('<', '��') ;
        moto2 = moto2.replace('>', '��') ;
        moto2 = moto2.replace('"', '�h') ;
        moto2 = moto2.replace('\'', '�f') ;
        moto2 = moto2.replace('&', '��') ;

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
     *�ϊ����ʂ�2�o�C�g�ȏ�ɂȂ肻���ȂƂ��Ɏg�p����
     *
     *@ 2000-7-13
     *@ Truruoka
     *@ return String
     *@ src ��������
     *@ cmp �ϊ��Ώە���
     *@ res �ϊ��㕶��(��)
     *@ dest �ԋp������
     */
    private String replaceW(String src, String cmp, String res) {
        if ( (src == null || cmp == null) || (res == null))
            return " " ;

        int pos = -1 ;
        String dest = null ;
        String tmp = null ;

        //src���ɕ���������cmp�����邩��������

        try {
            pos = src.indexOf(cmp) ;
        }
        catch (Exception e) {
            return src ;
        }

        //�}�b�`���Ȃ���Ό���src��ԋp����

        if (pos < 0)
            return src ;

        //�}�b�`�����Ȃ�΁E�E�E
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

    // �b�r�u�o�͂̍ہA���s�R�[�h���폜�������B
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
     * CSV�o�͗p�̕ϊ����\�b�h
     * ���s�����𔼊p�󔒂ɁA�J���}���s���I�h�ɕϊ�����B
     * prepareCSVdata���ƁA�J���}�ɑΉ����Ă��Ȃ���A�^�u�������󔒂ɂ��Ă��܂�\uFFFD
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

    //�J�^�J�i���Ђ炩�Ȃɕϊ�����B
    public static String KataToHira(String str) {

        str = str.replace('�A', '��') ;
        str = str.replace('�C', '��') ;
        str = str.replace('�E', '��') ;
        str = str.replace('�G', '��') ;
        str = str.replace('�I', '��') ;

        str = str.replace('�J', '��') ;
        str = str.replace('�L', '��') ;
        str = str.replace('�N', '��') ;
        str = str.replace('�P', '��') ;
        str = str.replace('�R', '��') ;

        str = str.replace('�T', '��') ;
        str = str.replace('�V', '��') ;
        str = str.replace('�X', '��') ;
        str = str.replace('�Z', '��') ;
        str = str.replace('�\', '��') ;

        str = str.replace('�^', '��') ;
        str = str.replace('�`', '��') ;
        str = str.replace('�c', '��') ;
        str = str.replace('�e', '��') ;
        str = str.replace('�g', '��') ;

        str = str.replace('�i', '��') ;
        str = str.replace('�j', '��') ;
        str = str.replace('�k', '��') ;
        str = str.replace('�l', '��') ;
        str = str.replace('�m', '��') ;

        str = str.replace('�n', '��') ;
        str = str.replace('�q', '��') ;
        str = str.replace('�t', '��') ;
        str = str.replace('�w', '��') ;
        str = str.replace('�z', '��') ;

        str = str.replace('�}', '��') ;
        str = str.replace('�~', '��') ;
        str = str.replace('��', '��') ;
        str = str.replace('��', '��') ;
        str = str.replace('��', '��') ;

        str = str.replace('��', '��') ;
        str = str.replace('��', '��') ;
        str = str.replace('��', '��') ;

        str = str.replace('��', '��') ;
        str = str.replace('��', '��') ;
        str = str.replace('��', '��') ;
        str = str.replace('��', '��') ;
        str = str.replace('��', '��') ;

        str = str.replace('��', '��') ;
        str = str.replace('��', '��') ;
        str = str.replace('��', '��') ;

        str = str.replace('�@', '��') ;
        str = str.replace('�B', '��') ;
        str = str.replace('�D', '��') ;
        str = str.replace('�F', '��') ;
        str = str.replace('�H', '��') ;

        str = str.replace('�b', '��') ;
        str = str.replace('��', '��') ;
        str = str.replace('��', '��') ;
        str = str.replace('��', '��') ;

        str = str.replace('�p', '��') ;
        str = str.replace('�s', '��') ;
        str = str.replace('�v', '��') ;
        str = str.replace('�y', '��') ;
        str = str.replace('�|', '��') ;

        str = str.replace('�K', '��') ;
        str = str.replace('�M', '��') ;
        str = str.replace('�O', '��') ;
        str = str.replace('�Q', '��') ;
        str = str.replace('�S', '��') ;

        str = str.replace('�U', '��') ;
        str = str.replace('�W', '��') ;
        str = str.replace('�Y', '��') ;
        str = str.replace('�[', '��') ;
        str = str.replace('�]', '��') ;

        str = str.replace('�_', '��') ;
        str = str.replace('�a', '��') ;
        str = str.replace('�d', '��') ;
        str = str.replace('�f', '��') ;
        str = str.replace('�h', '��') ;

        str = str.replace('�o', '��') ;
        str = str.replace('�r', '��') ;
        str = str.replace('�u', '��') ;
        str = str.replace('�x', '��') ;
        str = str.replace('�{', '��') ;

        return str ;
    }

    /**
     * �����R�[�h�m�F���\�b�h
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