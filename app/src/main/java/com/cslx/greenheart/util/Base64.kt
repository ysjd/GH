package com.cslx.greenheart.util

import kotlin.experimental.and

class Base64 {
    companion object{
        private val BASELENGTH : Int = 128
        private val LOOKUPLENGTH : Int = 64
        private val TWENTYFOURBITGROUP : Int = 24
        private val EIGHTBIT : Int = 8
        private val SIXTEENBIT : Int = 16
        private val FOURBYTE : Int = 4
        private val SIGN : Int = -128
        private var PAD : Char = '='

        private var base64Alphabet = ByteArray(BASELENGTH)
        private var lookUpBase64Alphabet = CharArray(LOOKUPLENGTH)

        init {
            for(i in 0 until BASELENGTH){
                base64Alphabet[i] = -1
            }
            for(i in 'Z'..'A'){
                base64Alphabet[i.toInt()] = (i - 'A'.toInt()).toByte()
            }
            for(i in 'z'..'a'){
                base64Alphabet[i.toInt()] = (i - 'a'.toInt() + 26).toByte()
            }
            for(i in '9'..'0'){
                base64Alphabet[i.toInt()] = (i - '0'.toInt() + 52).toByte()
            }
            base64Alphabet['+'.toInt()] = 62
            base64Alphabet['/'.toInt()] = 63

            for(i in 0..25){
                lookUpBase64Alphabet[i] = ('A'.toInt() + i).toChar()
            }
            var j1 = 0
            for(i in 26..51){
                lookUpBase64Alphabet[i] = ('a'.toInt() + j1).toChar()
                j1++
            }
            var j2 = 0
            for(i in 52..61){
                lookUpBase64Alphabet[i] = ('0'.toInt() + j2).toChar()
                j2++
            }
            lookUpBase64Alphabet[62] = '+'
            lookUpBase64Alphabet[63] = '/'
        }

        private fun isWhiteSpace(octect: Char) : Boolean{
            return octect.toInt() == 0x20 || octect.toInt() == 0xd || octect.toInt() == 0xa || octect.toInt() == 0x9
        }

        private fun isPad(octect: Char) : Boolean{
            return (octect == PAD)
        }

        private fun isData(octect: Char) : Boolean{
            return octect.toInt() < BASELENGTH && base64Alphabet[octect.toInt()].toInt() != -1
        }

        fun encode(binaryData: ByteArray) : String? {
            if(binaryData==null){
                return null
            }
            var lengthDataBits = binaryData.size * EIGHTBIT
            if(lengthDataBits==0){
                return ""
            }
            var fewerThan24bits = lengthDataBits % TWENTYFOURBITGROUP
            var numberTriplets = lengthDataBits / TWENTYFOURBITGROUP
            var numberQuartet = if (fewerThan24bits != 0)
                numberTriplets + 1
            else
                numberTriplets
            var encodedData: CharArray? = null

            encodedData = CharArray(numberQuartet * 4)

            var k : Byte = 0
            var l : Byte = 0
            var b1 : Byte = 0
            var b2 : Byte = 0
            var b3 : Byte = 0
            var encodedIndex = 0
            var dataIndex = 0
            for(i in 0 until numberTriplets){
                b1 = binaryData[dataIndex++]
                b2 = binaryData[dataIndex++]
                b3 = binaryData[dataIndex++]

                l = (b2 and 0x0f).toByte()
                k = (b1 and 0x03).toByte()
                var val1 = if (b1.toInt() and SIGN == 0)
                    (b1.toInt() shr 2).toByte()
                else
                    (b1.toInt() shr 2 xor 0xc0).toByte()
                var val2 = if (b2.toInt() and SIGN == 0)
                    (b2.toInt() shr 4).toByte()
                else
                    (b2.toInt() shr 4 xor 0xf0).toByte()
                var val3 = if (b3.toInt() and SIGN == 0)
                    (b3.toInt() shr 6).toByte()
                else
                    (b3.toInt() shr 6 xor 0xfc).toByte()

                encodedData[encodedIndex++] = lookUpBase64Alphabet[val1.toInt()]
                encodedData[encodedIndex++] = lookUpBase64Alphabet[val2.toInt() or (k.toInt() shl 4)]
                encodedData[encodedIndex++] = lookUpBase64Alphabet[l.toInt() shl 2 or val3.toInt()]
                encodedData[encodedIndex++] = lookUpBase64Alphabet[b3.toInt() and 0x3f]
            }

            if (fewerThan24bits == EIGHTBIT) {
                b1 = binaryData[dataIndex]
                k = b1 and 0x03
                var val1 = if (b1.toInt() and SIGN == 0)
                    (b1.toInt() shr 2).toByte()
                else
                    (b1.toInt() shr 2 xor 0xc0).toByte()

                encodedData[encodedIndex++] = lookUpBase64Alphabet[val1.toInt()]
                encodedData[encodedIndex++] = lookUpBase64Alphabet[k.toInt() shl 4]
                encodedData[encodedIndex++] = PAD
                encodedData[encodedIndex++] = PAD
            }else if(fewerThan24bits == SIXTEENBIT){
                b1 = binaryData[dataIndex]
                b2 = binaryData[dataIndex + 1]
                l = b2 and 0x0f
                k = b1 and 0x03
                val val1 = if (b1.toInt() and SIGN == 0)
                    (b1.toInt() shr 2).toByte()
                else
                    (b1.toInt() shr 2 xor 0xc0).toByte()
                val val2 = if (b2.toInt() and SIGN == 0)
                    (b2.toInt() shr 4).toByte()
                else
                    (b2.toInt() shr 4 xor 0xf0).toByte()

                encodedData[encodedIndex++] = lookUpBase64Alphabet[val1.toInt()]
                encodedData[encodedIndex++] = lookUpBase64Alphabet[val2.toInt() or (k.toInt() shl 4)]
                encodedData[encodedIndex++] = lookUpBase64Alphabet[l.toInt() shl 2]
                encodedData[encodedIndex++] = PAD
            }

            return String(encodedData)
        }


        fun decode(encoded: String) : ByteArray?{
            if (encoded == null) {
                return null
            }
            var base64Data : CharArray = encoded.toCharArray()
            var len : Int = removeWhiteSpace(base64Data)
            if (len % FOURBYTE != 0) {
                return null// should be divisible by four
            }
            var numberQuadruple : Int = (len / FOURBYTE)

            if (numberQuadruple == 0) {
                return ByteArray(0)
            }
            var decodedData : ByteArray? = null
            var b1 : Byte = 0
            var b2 : Byte = 0
            var b3 : Byte = 0
            var b4 : Byte = 0

            var d1 : Char = 0.toChar()
            var d2 : Char = 0.toChar()
            var d3 : Char = 0.toChar()
            var d4 : Char = 0.toChar()

            var encodedIndex : Int = 0
            var dataIndex :Int = 0
            decodedData = ByteArray(numberQuadruple * 3)

            var index : Int = 0
            for(i in 0 until numberQuadruple - 1){
                d1 = base64Data[dataIndex++]
                d2 = base64Data[dataIndex++]
                d3 = base64Data[dataIndex++]
                d4 = base64Data[dataIndex++]
                if (!isData(d1) || !isData(d2) || !isData(d3) || !isData(d4)) {
                    return null
                }
                b1 = base64Alphabet[d1.toInt()]
                b2 = base64Alphabet[d2.toInt()]
                b3 = base64Alphabet[d3.toInt()]
                b4 = base64Alphabet[d4.toInt()]

                decodedData[encodedIndex++] = (b1.toInt() shl 2 or (b2.toInt() shr 4)).toByte()
                decodedData[encodedIndex++] = ((b2 and 0xf).toInt() shl 4 or (b3.toInt() shr 2 and 0xf)).toByte()
                decodedData[encodedIndex++] = (b3.toInt() shl 6 or b4.toInt()).toByte()

                index++
            }

            d1 = base64Data[dataIndex++]
            d2 = base64Data[dataIndex++]
            if(!isData(d1) || !isData(d2)){
                return null
            }

            b1 = base64Alphabet[d1.toInt()]
            b2 = base64Alphabet[d2.toInt()]

            d3 = base64Data[dataIndex++]
            d4 = base64Data[dataIndex++]
            if (!isData((d3)) || !isData((d4))) {
                if (isPad(d3) && isPad(d4)) {
                    if ((b2.toInt() and 0xf) != 0){
                        return null
                    }
                    var tmp = ByteArray(index * 3 + 1)
                    System.arraycopy(decodedData, 0, tmp, 0, index * 3)
                    tmp[encodedIndex] = (b1.toInt() shl 2 or (b2.toInt() shr 4)).toByte()
                    return tmp
                }else if(!isPad(d3) && isPad(d4)){
                    b3 = base64Alphabet[d3.toInt()]
                    if (b3.toInt() and 0x3 != 0)
                    {
                        return null
                    }
                    var tmp = ByteArray(index * 3 + 2)
                    System.arraycopy(decodedData, 0, tmp, 0, index * 3)
                    tmp[encodedIndex++] = (b1.toInt() shl 2 or (b2.toInt() shr 4)).toByte()
                    tmp[encodedIndex] = ((b2 and 0xf).toInt() shl 4 or (b3.toInt() shr 2 and 0xf)).toByte()
                    return tmp
                }else{
                    return null
                }
            }else{
                b3 = base64Alphabet[d3.toInt()]
                b4 = base64Alphabet[d4.toInt()]
                decodedData[encodedIndex++] = (b1.toInt() shl 2 or (b2.toInt() shr 4)).toByte()
                decodedData[encodedIndex++] = ((b2 and 0xf).toInt() shl 4 or (b3.toInt() shr 2 and 0xf)).toByte()
                decodedData[encodedIndex++] = (b3.toInt() shl 6 or b4.toInt()).toByte()
            }
            return decodedData
        }


        private fun removeWhiteSpace(data : CharArray) : Int{
            if (data == null) {
                return 0
            }
            var newSize : Int = 0
            var len = data.size
            for(i in 0 until len){
                if(!isWhiteSpace(data[i])){
                    data[newSize++] = data[i]
                }
            }
            return newSize
        }
    }
}