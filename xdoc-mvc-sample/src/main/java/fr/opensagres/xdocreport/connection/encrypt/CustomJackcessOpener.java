package fr.opensagres.xdocreport.connection.encrypt;

import java.io.File;
import java.io.IOException;

import net.ucanaccess.jdbc.JackcessOpenerInterface;

import com.healthmarketscience.jackcess.CryptCodecProvider;
import com.healthmarketscience.jackcess.Database;
import com.healthmarketscience.jackcess.DatabaseBuilder;

public class CustomJackcessOpener implements JackcessOpenerInterface {

	@Override
	public Database open(File file, String arg1) throws IOException {
//		CryptCodecProvider cryptProvider = new CryptCodecProvider("legado23");
		DatabaseBuilder dbd =new DatabaseBuilder(file);
	       dbd.setAutoSync(false);
	       dbd.setCodecProvider(new CryptCodecProvider("legado23"));
	       dbd.setReadOnly(false);
	       return dbd.open();
	}

}
