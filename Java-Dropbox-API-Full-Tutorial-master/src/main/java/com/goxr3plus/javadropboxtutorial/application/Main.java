package main.java.com.goxr3plus.javadropboxtutorial.application;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.dropbox.core.DbxDownloader;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.files.UploadErrorException;
import com.dropbox.core.v2.users.FullAccount;
import com.dropbox.core.v2.users.DbxUserUsersRequests;

public class Main {
	  private static final String ACCESS_TOKEN = "sl.u.AFmsKA2LwvFmx9MKPgINnQfmKjoVHT0FsDzmNCjCN_iDrBNLeOJQXnQe5zYGqnxhn0UdmmU74tZmIUWwYYwMPee4-7R3aHlCCIpQCk33XI_hrycNX5u5sBpIQgFaINxxKOWzvmO9cZM4y8ORxCyf8OXtg5z3ZsnOzCphRn200grTOkAIcfJFZaSOCvC3Q3QEA0Os2DJuyMulem6vIMgmrMkoOo8IxV76l3aG5ZD9qMVxcm27DfqB5U_V618ZMQQai15jOATaufnHKE-9TcZA0tW1hYT3ORAL1bIsGPq_NERgGmvsMix9VZtpKCJL_Nr2hOhiI4ymOezeUXCpOufRAjgRrb5R-QETZ5UModQBKUo5ioFw6DhPEoxduTJ8L-dHu4Qdeefybs--AyRqPOXHjpEJQD5wg8Xepvhqu-iupemuE3iHmHw8tahAGnJWYPOZb1ncxJgIiM8n4iFyIeXbczA_ubysA5_WMluS_z6-uM26WN-kZYK2unPW0lim3-5bvAiVLWQcqwZ3Rm0A4laBvA_l186gtiKWKDVVtsvI8FCIAU8Vqzhpn9eAB7dNB-6jCkDvN6YWAYwfvvpHZtTRS6_BUMw6ioaAmYvdO30XS7a2xvoKiR9jp4mXY-y2bcwIWeqOfK56BJKYz5j7Rxp8OQdJyuFEwPPOluieQXfo1AMwXM6tdCTkCcx-4tTIVH9aHvyES1_CtoA66vHZMfi4XBrNszraLBPnPhzWOMNLnQJNgDwDvXdsnKyftMJOBItCIcpxgcP0npE64idKP_Kt3BPoF9u5cCKvIz5SdkUAB10agq7P3QkcheBeOuOjVjOLrFKtl8FzCG4CgB-RdR4S05l0Rt1zO2QAcxDVHZaf6TMIX9LvQVVWYuIVju6uomH4cdhKdL1A0uzuQ4IyTusul6huDgHwi08rrVOLt1CfgtwofTBvSPP-DW9-reqJNPomFILzRuWF1dU0z9JtOSZoIj4xsjTbzICeIxaf5_qKAUn19Zjgk84iWzqgV14WC0qrMnoQiFeHA0f-xmBgq9eg68Z4Kf78Z9Lx1Fv--osiihkPzs3GRI1Qa-e3IH_JdnlXRgYYNAPmUEUXM7vsT3NZrN9DVFIs4NHKMN7kBUYr_lMubVaB7wDfUL3UUOhaTqwahySBwMhcqqec8KUKQDZmzztqX3s4K0pBzHzL2wExRjE4pzdfSRqukfyUKAtiriXF2Nm7xcNPVKklUHRJbP8hCUYoQJ2rsXBlbotT0B81ZNaB3qyUT9VcJvA45PQ7C3BU36xRRKdH6ejk3jq_vrCT7FSUQrA8gTJxNhwqZojCy0G_3jsudcPgx1EFa9GrMoVvVvxPg2y3pYkeQJLsD0xo8NrG0OCViuMQIQIar24Lyc9SnB2LbXYskXSI1NRu87-XPTkYhYlIvgss5iluvShiMBNOsEkoVP4sf9vd336hoJUtgA";
	
	public static void main(String args[]) throws FileNotFoundException, IOException, UploadErrorException, DbxException {
		System.out.println("Hi");
		DbxRequestConfig config;
		config = new DbxRequestConfig("dropbox/messagerie");
		DbxClientV2 client;
		client = new DbxClientV2(config, ACCESS_TOKEN);
		
		try {
			
		
			FullAccount account;
			DbxUserUsersRequests r1 = client.users();
			account = r1.getCurrentAccount();
			System.out.println(account.getName().getDisplayName());
			
			// Get files and folder metadata from Dropbox root directory
			ListFolderResult result = client.files().listFolder("");
			while (true) {
				for (Metadata metadata : result.getEntries()) {
					System.out.println(metadata.getPathLower());
				}
				
				if (!result.getHasMore()) {
					break;
				}
				
				result = client.files().listFolderContinue(result.getCursor());
			}
			
		} catch (DbxException ex1) {
			ex1.printStackTrace();
		}
	

	      try (InputStream in = new FileInputStream("test.txt")) {
	          FileMetadata metadata = client.files().uploadBuilder("/test.txt")
	              .uploadAndFinish(in);
	      }
	
	      DbxDownloader<FileMetadata> downloader = client.files().download("/test.txt");
	       try {
	          FileOutputStream out = new FileOutputStream("test.txt");
	           downloader.download(out);
		           out.close();
	      } catch (DbxException ex) {
	          System.out.println(ex.getMessage());
	        }
	}
}
