package cl.rkstro.scoutTraductores;


public class ScoutTraductores {
	/**
	 * Utilidad para encontrar el indice en un arreglo
	 * a partir de uno de sus valores
	 * @param arr1 Array en donde buscar c
	 * @param c valor del arreglo que se busca
	 * @return indice en donde esta c en arr1
	 */
	private static Integer indexArray(String arr1[], String c){
		for (int i = 0; i < arr1.length; i++) {
			if( arr1[i].equals(c) )	return i;
		}
		return -1;
	}
	
	/**
	 * Traduce a morse y desde morse
	 * @param input texto a traducir
	 * @param opt opciones: encode y decode
	 * @return texto traducido segun opt
	 */
	public static String morse(String input, String opt){
		String key[] = {
			" ",".",",","?","'","!","/","(",")","&",	      // special chars
			":",";","=","+","-","_","\"","$","@",			  // special chars
			"0","1","2","3","4","5","6","7","8","9",		  // numbers
			"ä","å","ç","š","ð","ś","ł","é","ñ","ŝ","þ","ü",  // few non-latin letters
			"a","b","c","d","e","f","g","h","i","j","k","l","m", // letters
			"n","o","p","q","r","s","t","u","v","w","x","y","z"  // letters
		};
		
		String val[] = {
			"/",".-.-.-","--..--","..--..",".----.","-.-.--","-..-.","-.--.","-.--.-",".-...",  // special chars
			"---...","-.-.-.","-...-",".-.-.","-....-","..--.-",".-..-.","...-..-",".--.-.",    // special chars
			"-----",".----","..---","...--","....-",".....","-....","--...","---..","----.",    // numbers
			".-.-",".--.-","-.-..","----","..--.","...-...",".-..-","..-..","--.--","...-.",".--..","..--", // few non-latin letters
			".-","-...","-.-.","-..",".","..-.","--.","....","..",".---","-.-",".-..","--",	    // letters
			"-.","---",".--.","--.-",".-.","...","-","..-","...-",".--","-..-","-.--","--.."    // letters
		};
		
		String str = input.toLowerCase();
		str = str.replace('á', 'a'); str = str.replace('é', 'e');
		str = str.replace('í', 'i'); str = str.replace('ó', 'o');
		str = str.replace('ú', 'u');
		String res = "";
		
		if (str.length() == 0)
			return null;

		if (opt.equals("encode")){
			for (int i = 0; i < str.length(); i++) {
				Integer index = indexArray(key, Character.toString(str.charAt(i)));
				if(index>-1){
					res += val[index];
					if(!val[index].equals("/")) res += "/";
				}
			}
		} else if (opt.equals("decode")){
			String words[] = str.split("//");
			for (int i = 0; i < words.length; i++) {
				String letras[] = words[i].split("/");
				for (int y = 0; y < letras.length; y++) {
					Integer index = indexArray(val, letras[y]);
					if(index>-1)
						res += key[index];
				}
				res += " ";
			}
		} else {
			res = "error";
		}
		
		return res;
	}
	
	/**
	 * Traduce un texto a clave romana
	 * @param str texto a traducir
	 * @return texto traducido en romana
	 */
	public static String romana(String str){
		String txt = str.toLowerCase();
		txt = txt.replace('á', 'a'); txt = txt.replace('é', 'e');
		txt = txt.replace('í', 'i'); txt = txt.replace('ó', 'o');
		txt = txt.replace('ú', 'u');
		String out = "";
		
		String romanaSet[][] = {
			{  "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"},
			{  "I", "a", "b", "c", "d", "e", "f", "g", "h", "i"},
			{ "II", "j", "k", "l", "m", "n", "ñ", "o", "p", "q"},
			{"III", "r", "s", "t", "u", "v", "w", "x", "y", "z"}
		};
	
		for(int let=0; let<txt.length(); let++){
			Boolean flag = true;
			for(int j=1; j<4; j++){
				for(int i=1; i<10; i++){
					if(Character.toString(txt.charAt(let)).equals(romanaSet[j][i])) {
						out += romanaSet[0][i]+romanaSet[j][0]+'/';
						flag = false;
						break;
					}
				}
			}
			if(flag){
				if(txt.charAt(let)==' ') out += "/";
				else out += txt.charAt(let);
			}
		}
	
		return out;
	}

	/**
	 * Utilidad para generar diversos traductores a partir de un diccionario
	 * @param diccionario arreglo que contiene los caracteres a reemplazar
	 * @param str texto de entrada
	 * @return texto traducido segun el diccionario
	 */
	private static String abstractTranslate(String diccionario[], String str){
		String letters[] = {
			"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
			"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z",
			"0","1","2","3","4","5","6","7","8","9",
			"@"," ",".",",","¿","?",":","-","¡","!","ñ","Ñ","\n"
		};
		
		String res = "";
		for(int count = 0; count < str.length(); count++) {
			String daChar = Character.toString(str.charAt(count));
			for (int i = 0; i < letters.length; i++) {
				if (daChar.equals(letters[i])) {
					res += diccionario[i];
					break;
				}
			}
		}
		return res;
	}
	
	/**
	 * Traduce un texto a Cenit Polar
	 * @param str texto a traducir
	 * @return texto traducido en cenit-polar
	 */
	public static String cenit(String str){
		String polar[] = {
			"i","b","p","d","o","f","g","h","a","j","k","n","m","l","e","c","q","t","s","r","u","v","w","x","y","z",
			"I","B","P","D","O","F","G","H","A","J","K","N","M","L","E","C","Q","T","S","R","U","V","W","X","Y","Z",
			"0","1","2","3","4","5","6","7","8","9",
			"@"," ",".",",","¿","?",":","-","¡","!","ñ","Ñ","\n"
		};
		return abstractTranslate(polar, str);
	}
	
	/**
	 * Traduce un texto a Murcielago
	 * @param str texto a traducir
	 * @return texto traducido en murcielago
	 */
	public static String murcielago(String str){
		String murcielago[] = {
			"7","b","3","d","5","f","8","h","4","j","k","6","0","n","9","p","q","2","s","t","1","v","w","x","y","z",
			"7","b","3","d","5","f","8","h","4","j","k","6","0","n","9","p","q","2","s","t","1","v","w","x","y","z",
			"m","u","r","c","i","e","l","a","g","o",
			"@"," ",".",",","¿","?",":","-","¡","!","ñ","Ñ","\n"
		};
		return abstractTranslate(murcielago, str);
	}
	
	/**
	 * Traduce un texto a Neumatico
	 * @param str texto a traducir
	 * @return texto traducido en neumatico
	 */
	public static String neumatico(String str){
		String neumatico[] = {
			"5","b","8","d","2","f","g","h","7","j","k","l","4","1","9","p","q","r","s","6","3","v","w","x","y","z",
			"5","B","8","D","2","F","G","H","7","J","K","L","4","1","9","P","Q","R","S","6","3","V","W","X","Y","Z",
			"0","N","E","U","M","A","T","I","C","O",
			"@"," ",".",",","¿","?",":","-","¡","!","ñ","Ñ","\n"
		};
		return abstractTranslate(neumatico, str);
	}
	
	
	/**
	 * Traduce un texto a plusOne
	 * @param str texto a traducir
	 * @return texto traducido en plusOne
	 */
	public static String plusOne(String str){
		String plusOne[] = {
			"b","c","d","e","f","g","h","i","j","k","l","m","n","ñ","p","q","r","s","t","u","v","w","x","y","z","a",
			"B","C","D","E","F","G","H","I","J","K","L","M","N","Ñ","P","Q","R","S","T","U","V","W","X","Y","Z","A",
			"0","1","2","3","4","5","6","7","8","9",
			"@"," ",".",",","¿","?",":","-","¡","!","o","O","\n"
		};
		return abstractTranslate(plusOne, str);
	}
	
	/**
	 * Traduce un texto a minusOne
	 * @param str texto a traducir
	 * @return texto traducido en minusOne
	 */
	public static String minusOne(String str){
		String minusOne[] = {
			"z","a","b","c","d","e","f","g","h","i","j","k","l","m","ñ","o","p","q","r","s","t","u","v","w","x","y",
			"Z","A","B","C","D","E","F","G","H","I","J","K","L","M","Ñ","O","P","Q","R","S","T","U","V","W","X","Y",
			"0","1","2","3","4","5","6","7","8","9",
			"@"," ",".",",","¿","?",":","-","¡","!","n","N","\n"
		};
		return abstractTranslate(minusOne, str);
	}
	
	/**
	 * Traduce un texto a sufamelico
	 * @param str texto a traducir
	 * @return texto traducido en sufamelico
	 */
	public static String sufamelico(String str){
		String sufamelico[] = {
			"f","b","o","d","m","a","g","h","l","j","k","i","e","n","c","p","q","r","u","t","s","v","w","x","y","z",
			"F","B","O","D","M","A","G","H","L","J","K","I","E","N","C","P","Q","R","U","T","S","V","W","X","Y","Z",
			"0","1","2","3","4","5","6","7","8","9",
			"@"," ",".",",","¿","?",":","-","¡","!","ñ","Ñ","\n"
		};
		return abstractTranslate(sufamelico, str);
	}
	
	/**
	 * Traduce un texto a Baden Powell
	 * @param str texto a traducir
	 * @return texto traducido en Baden Powell
	 */
	public static String badenPowell(String str){
		String bp[] = {
			"o","p","c","w","e","f","g","h","i","j","k","n","m","l","a","b","q","r","s","t","u","v","d","x","y","z",
			"O","P","C","W","E","F","G","H","I","J","K","N","M","L","A","B","Q","R","S","T","U","V","D","X","Y","Z",
			"0","1","2","3","4","5","6","7","8","9",
			"@"," ",".",",","¿","?",":","-","¡","!","ñ","Ñ","\n"
		};
		return abstractTranslate(bp, str);
	}
	
	/**
	 * Traduce un texto a Agujerito
	 * @param str texto a traducir
	 * @return texto traducido en Agujerito
	 */
	public static String agujerito(String str){
		String agujerito[] = {
			"1","b","c","d","5","f","2","h","7","4","k","l","m","n","9","p","q","6","s","8","3","v","w","x","y","z",
			"1","B","C","D","5","F","2","H","7","4","K","L","M","N","9","P","Q","6","S","8","3","V","W","X","Y","Z",
			"0","A","G","U","J","E","R","I","T","O",
			"@"," ",".",",","¿","?",":","-","¡","!","ñ","Ñ","\n"
		};
		return abstractTranslate(agujerito, str);
	}
	
}
