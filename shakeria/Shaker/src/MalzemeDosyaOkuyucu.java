import java.io.*;
import java.util.*;


public class MalzemeDosyaOkuyucu {
	public static List<Kokteyl> kokteylleriOku(String dosyaAdi){
	 List<Kokteyl> kokteyller = new ArrayList<>();
	

		try(BufferedReader br = new BufferedReader(new FileReader(dosyaAdi))){
			String satir;
			while ((satir= br.readLine()) != null) {
				if(satir.trim().isEmpty())continue;
				
				String[] parcalar = satir.split(":");
				if (parcalar.length != 2) continue;
				
				String kokteylAdi = parcalar[0].trim();
				String[] malzemeAdlari= parcalar[1].split(",");
				List<Malzeme> malzemeler = new ArrayList<>();
				for (String malzemeAdi : malzemeAdlari) {
					malzemeler.add(new Malzeme(malzemeAdi.trim()));
				}
				
		Kokteyl kokteyl = new Kokteyl(kokteylAdi, malzemeler);
		kokteyller.add(kokteyl);
			}
		}catch (IOException e) {
			e.printStackTrace();
		}
		return kokteyller;
	}

public static List<Malzeme> malzemeDosyasiniOku(String dosyaAdi) {
	List<Malzeme> malzemeler = new ArrayList<>();
	try (BufferedReader br = new BufferedReader (new FileReader(dosyaAdi))){
		String satir;
		while ((satir = br.readLine()) != null) {
			if (!satir.trim().isEmpty()) {
				malzemeler.add(new Malzeme(satir.trim()));
			}
		}
	} catch (IOException e) {
		e.printStackTrace();
		}
	return malzemeler;
	}
	}



