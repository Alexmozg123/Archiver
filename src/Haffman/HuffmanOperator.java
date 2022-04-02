package Haffman;

public class HuffmanOperator {
    private final byte ENCODING_TABLE_SIZE = 127;       //длина таблицы
    private String myString;                            //исходное сообщение
    private int[] freqArray;                            //частотаная таблица
    private String[] encodingArray;                     //кодировочная таблица
    private double ratio;                               //коэффициент сжатия


    public HuffmanOperator(HuffmanTree MainHuffmanTree) {//for compress
        //дерево Хаффмана (используется только для сжатия)

        myString = MainHuffmanTree.getOriginalString();

        encodingArray = MainHuffmanTree.getEncodingArray();

        freqArray = MainHuffmanTree.getFrequencyArray();
    }

    public HuffmanOperator() {}//for intermediate;

    //---------------------------------------compression-----------------------------------------------------------
    private String getCompressedString() {
        String compressed;
        StringBuilder intermediate = new StringBuilder();//промежуточная строка(без добавочных нулей)
        System.out.println("=============================Compression=======================");
        //displayEncodingArray();
        for (int i = 0; i < myString.length(); i++) {
            intermediate.append(encodingArray[myString.charAt(i)]);
        }
        //Мы не можем писать бит в файл. Поэтому нужно сделать длину сообщения кратной 8=>
        //нужно добавить нули в конец(можно 1, нет разницы)
        byte counter = 0;//количество добавленных в конец нулей (байта в полне хватит: 0<=counter<8<127)
        for (int length = intermediate.length(), delta = 8 - length % 8;
             counter < delta ; counter++) {//delta - количество добавленных нулей
            intermediate.append("0");
        }

        //склеить кол-во добавочных нулей в бинарном предаствлении и промежуточную строку
        compressed = String.format("%8s", Integer.toBinaryString(counter & 0xff))
                .replace(" ", "0") + intermediate;

        //идеализированный коэффициент
        setCompressionRatio();
        //System.out.println("===============================================================");
        return compressed;
    }

    private void setCompressionRatio() {//посчитать идеализированный коэффициент
        double sumA = 0, sumB = 0;//A-the original sum
        for (int i = 0; i < ENCODING_TABLE_SIZE; i++) {
            if (freqArray[i] != 0) {
                sumA += 8 * freqArray[i];
                sumB += encodingArray[i].length() * freqArray[i];
            }
        }
        ratio = sumA / sumB;
    }

    public byte[] getByteMsg() {//final compression
        StringBuilder compressedString = new StringBuilder(getCompressedString());
        byte[] compressedBytes = new byte[compressedString.length() / 8];
        for (int i = 0; i < compressedBytes.length; i++) {
            compressedBytes[i] = (byte) Integer.parseInt(compressedString.substring(i * 8, (i + 1) * 8), 2);
        }
        return compressedBytes;
    }
    //---------------------------------------end of compression--------------------------------------------------------
    //------------------------------------------------------------extract----------------------------------------------
    public String extract(String compressed, String[] newEncodingArray) {
        StringBuilder decompressed = new StringBuilder();
        StringBuilder current = new StringBuilder();
        StringBuilder delta = new StringBuilder();
        encodingArray = newEncodingArray;

        //displayEncodingArray();
        //получить кол-во вставленных нулей
        for (int i = 0; i < 8; i++)
            delta.append(compressed.charAt(i));
        int ADDED_ZEROES = Integer.parseInt(delta.toString(), 2);

        for (int i = 8, l = compressed.length() - ADDED_ZEROES; i < l; i++) {
            //i = 8, т.к. первым байтом у нас идет кол-во вставленных нулей
            current.append(compressed.charAt(i));
            for (int j = 0; j < ENCODING_TABLE_SIZE; j++) {
                if (current.toString().equals(encodingArray[j])) {//если совпало
                    decompressed.append((char) j);//то добавляем элемент
                    current = new StringBuilder();//и обнуляем текущую строку
                }
            }
        }

        return decompressed.toString();
    }

    public String getEncodingTable() {
        StringBuilder enc = new StringBuilder();
        for (int i = 0; i < encodingArray.length; i++) {
            if (freqArray[i] != 0)
                enc.append((char) i).append(encodingArray[i]).append('\n');
        }
        return enc.toString();
    }

    public double getCompressionRatio() {
        return ratio;
    }


    public void displayEncodingArray() {//для отладки
        System.out.println("======================Encoding table====================");
        for (int i = 0; i < ENCODING_TABLE_SIZE; i++) {
            //if (freqArray[i] != 0) {
            System.out.print((char)i + " ");
            System.out.println(encodingArray[i]);
            //}
        }
        System.out.println("========================================================");
    }
}
